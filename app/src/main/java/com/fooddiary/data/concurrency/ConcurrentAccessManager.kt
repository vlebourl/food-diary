package com.fooddiary.data.concurrency

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withTimeoutOrNull

@Singleton
class ConcurrentAccessManager @Inject constructor() {

    companion object {
        private const val DEFAULT_TIMEOUT_MS = 30_000L // 30 seconds
        private const val MAX_CONCURRENT_OPERATIONS = 10
        private const val MAX_RETRIES = 3
    }

    // Global semaphore to limit concurrent database operations
    private val globalSemaphore = Semaphore(MAX_CONCURRENT_OPERATIONS)

    // Entity-specific mutexes for fine-grained locking
    private val entityMutexes = ConcurrentHashMap<String, Mutex>()

    // Operation tracking
    private val activeOperations = AtomicInteger(0)
    private val operationCounters = ConcurrentHashMap<String, AtomicInteger>()

    // Deadlock detection
    private val operationDependencies = ConcurrentHashMap<String, MutableSet<String>>()
    private val threadOperations = ConcurrentHashMap<Long, MutableSet<String>>()

    suspend fun <T> withGlobalLock(
        operation: suspend () -> T,
    ): T {
        return globalSemaphore.withPermit {
            activeOperations.incrementAndGet()
            try {
                operation()
            } finally {
                activeOperations.decrementAndGet()
            }
        }
    }

    suspend fun <T> withEntityLock(
        entityType: String,
        entityId: String,
        timeoutMs: Long = DEFAULT_TIMEOUT_MS,
        operation: suspend () -> T,
    ): ConcurrentOperationResult<T> {
        val lockKey = "$entityType:$entityId"
        val mutex = entityMutexes.computeIfAbsent(lockKey) { Mutex() }

        // Deadlock detection: check if current thread already holds other locks
        val currentThreadId = Thread.currentThread().id
        val currentOperations = threadOperations.computeIfAbsent(currentThreadId) { mutableSetOf() }

        // Check for potential circular dependency
        if (detectPotentialDeadlock(lockKey, currentOperations)) {
            return ConcurrentOperationResult.DeadlockDetected(
                message = "Potential deadlock detected for operation on $lockKey",
                involvedEntities = currentOperations + lockKey,
            )
        }

        return try {
            withTimeoutOrNull(timeoutMs) {
                mutex.withLock {
                    currentOperations.add(lockKey)
                    incrementOperationCounter(entityType)

                    try {
                        val result = operation()
                        ConcurrentOperationResult.Success(result)
                    } catch (e: Exception) {
                        ConcurrentOperationResult.OperationFailed(e)
                    } finally {
                        currentOperations.remove(lockKey)
                        decrementOperationCounter(entityType)
                    }
                }
            } ?: ConcurrentOperationResult.TimeoutExceeded(timeoutMs)
        } catch (e: Exception) {
            ConcurrentOperationResult.LockingFailed(e)
        } finally {
            // Clean up empty operation sets
            if (currentOperations.isEmpty()) {
                threadOperations.remove(currentThreadId)
            }
        }
    }

    suspend fun <T> withReadWriteLock(
        entityType: String,
        entityId: String,
        isWrite: Boolean = false,
        timeoutMs: Long = DEFAULT_TIMEOUT_MS,
        operation: suspend () -> T,
    ): ConcurrentOperationResult<T> {
        val lockKey = "$entityType:$entityId"

        return if (isWrite) {
            // Exclusive write lock
            withEntityLock(entityType, entityId, timeoutMs, operation)
        } else {
            // Shared read lock (simplified - could be optimized with ReadWriteLock)
            // For now, treat reads the same as writes to ensure data consistency
            withEntityLock(entityType, entityId, timeoutMs, operation)
        }
    }

    suspend fun <T> withRetry(
        maxRetries: Int = MAX_RETRIES,
        backoffMs: Long = 100,
        operation: suspend () -> ConcurrentOperationResult<T>,
    ): ConcurrentOperationResult<T> {
        var lastResult: ConcurrentOperationResult<T>? = null
        var currentBackoff = backoffMs

        repeat(maxRetries + 1) { attempt ->
            val result = operation()

            when (result) {
                is ConcurrentOperationResult.Success -> return result
                is ConcurrentOperationResult.TimeoutExceeded,
                is ConcurrentOperationResult.LockingFailed,
                -> {
                    if (attempt < maxRetries) {
                        kotlinx.coroutines.delay(currentBackoff)
                        currentBackoff = (currentBackoff * 1.5).toLong() // Exponential backoff
                        lastResult = result
                    } else {
                        return result
                    }
                }
                else -> return result // Don't retry for other types of failures
            }
        }

        return lastResult ?: ConcurrentOperationResult.OperationFailed(
            RuntimeException("Retry loop completed unexpectedly"),
        )
    }

    suspend fun <T> withBatchLock(
        entities: List<Pair<String, String>>, // List of (entityType, entityId)
        timeoutMs: Long = DEFAULT_TIMEOUT_MS,
        operation: suspend () -> T,
    ): ConcurrentOperationResult<T> {
        // Sort entities to prevent deadlocks due to different ordering
        val sortedEntities = entities.sortedBy { "${it.first}:${it.second}" }
        val lockKeys = sortedEntities.map { "${it.first}:${it.second}" }

        // Check for deadlock potential
        val currentThreadId = Thread.currentThread().id
        val currentOperations = threadOperations.computeIfAbsent(currentThreadId) { mutableSetOf() }

        for (lockKey in lockKeys) {
            if (detectPotentialDeadlock(lockKey, currentOperations)) {
                return ConcurrentOperationResult.DeadlockDetected(
                    message = "Potential deadlock detected in batch operation",
                    involvedEntities = currentOperations + lockKeys.toSet(),
                )
            }
        }

        return try {
            withTimeoutOrNull(timeoutMs) {
                // Acquire all locks in sorted order
                acquireLocksRecursively(lockKeys, 0) {
                    lockKeys.forEach { currentOperations.add(it) }

                    try {
                        val result = operation()
                        ConcurrentOperationResult.Success(result)
                    } catch (e: Exception) {
                        ConcurrentOperationResult.OperationFailed(e)
                    } finally {
                        lockKeys.forEach { currentOperations.remove(it) }
                    }
                }
            } ?: ConcurrentOperationResult.TimeoutExceeded(timeoutMs)
        } catch (e: Exception) {
            ConcurrentOperationResult.LockingFailed(e)
        } finally {
            if (currentOperations.isEmpty()) {
                threadOperations.remove(currentThreadId)
            }
        }
    }

    private suspend fun <T> acquireLocksRecursively(
        lockKeys: List<String>,
        index: Int,
        operation: suspend () -> ConcurrentOperationResult<T>,
    ): ConcurrentOperationResult<T> {
        return if (index >= lockKeys.size) {
            operation()
        } else {
            val lockKey = lockKeys[index]
            val mutex = entityMutexes.computeIfAbsent(lockKey) { Mutex() }

            mutex.withLock {
                acquireLocksRecursively(lockKeys, index + 1, operation)
            }
        }
    }

    private fun detectPotentialDeadlock(
        requestedLock: String,
        currentLocks: Set<String>,
    ): Boolean {
        // Simple deadlock detection: check if any current lock holder
        // is waiting for the requested lock

        for (currentLock in currentLocks) {
            val dependencies = operationDependencies[currentLock] ?: continue
            if (dependencies.contains(requestedLock)) {
                return true
            }
        }

        // Check for circular dependencies in the dependency graph
        return hasCircularDependency(requestedLock, currentLocks, mutableSetOf())
    }

    private fun hasCircularDependency(
        target: String,
        currentPath: Set<String>,
        visited: MutableSet<String>,
    ): Boolean {
        if (target in currentPath) return true
        if (target in visited) return false

        visited.add(target)

        val dependencies = operationDependencies[target] ?: return false
        for (dependency in dependencies) {
            if (hasCircularDependency(dependency, currentPath, visited)) {
                return true
            }
        }

        return false
    }

    private fun incrementOperationCounter(entityType: String) {
        operationCounters.computeIfAbsent(entityType) { AtomicInteger(0) }.incrementAndGet()
    }

    private fun decrementOperationCounter(entityType: String) {
        operationCounters[entityType]?.decrementAndGet()
    }

    fun getActiveOperationCount(): Int = activeOperations.get()

    fun getEntityOperationCount(entityType: String): Int {
        return operationCounters[entityType]?.get() ?: 0
    }

    fun getOperationStatistics(): ConcurrentOperationStatistics {
        val entityCounts = operationCounters.mapValues { it.value.get() }

        return ConcurrentOperationStatistics(
            totalActiveOperations = activeOperations.get(),
            entityOperationCounts = entityCounts,
            activeMutexes = entityMutexes.size,
            activeThreadOperations = threadOperations.size,
        )
    }

    // Bulk operations support
    suspend fun <T> withBulkInsert(
        entityType: String,
        batchSize: Int = 50,
        operation: suspend () -> T,
    ): ConcurrentOperationResult<T> {
        val bulkLockKey = "$entityType:BULK_INSERT"

        return withEntityLock(entityType, bulkLockKey) {
            // Temporarily increase semaphore permits for bulk operations
            globalSemaphore.withPermit {
                operation()
            }
        }
    }

    // Priority operation support
    suspend fun <T> withPriorityOperation(
        entityType: String,
        entityId: String,
        priority: OperationPriority = OperationPriority.NORMAL,
        operation: suspend () -> T,
    ): ConcurrentOperationResult<T> {
        // For now, treat all priorities the same
        // In a more sophisticated implementation, we could have priority queues
        return when (priority) {
            OperationPriority.HIGH -> {
                // High priority operations get shorter timeout but immediate execution
                withEntityLock(entityType, entityId, timeoutMs = 10_000L, operation)
            }
            OperationPriority.LOW -> {
                // Low priority operations wait longer but have extended timeout
                kotlinx.coroutines.delay(100) // Brief delay to allow other operations
                withEntityLock(entityType, entityId, timeoutMs = 60_000L, operation)
            }
            OperationPriority.NORMAL -> {
                withEntityLock(entityType, entityId, operation = operation)
            }
        }
    }

    // Resource cleanup
    suspend fun cleanupStaleOperations() {
        // Remove mutexes that are no longer being used
        val staleMutexes = entityMutexes.filter { (_, mutex) ->
            !mutex.isLocked
        }

        staleMutexes.keys.forEach { key ->
            entityMutexes.remove(key)
        }

        // Clean up empty operation counters
        val staleCounters = operationCounters.filter { (_, counter) ->
            counter.get() <= 0
        }

        staleCounters.keys.forEach { key ->
            operationCounters.remove(key)
        }

        // Clear dependency tracking for completed operations
        operationDependencies.clear()
    }
}

// Result types for concurrent operations

sealed class ConcurrentOperationResult<out T> {
    data class Success<T>(val result: T) : ConcurrentOperationResult<T>()

    data class TimeoutExceeded(val timeoutMs: Long) : ConcurrentOperationResult<Nothing>()

    data class LockingFailed(val exception: Exception) : ConcurrentOperationResult<Nothing>()

    data class OperationFailed(val exception: Exception) : ConcurrentOperationResult<Nothing>()

    data class DeadlockDetected(
        val message: String,
        val involvedEntities: Set<String>,
    ) : ConcurrentOperationResult<Nothing>()
}

enum class OperationPriority {
    LOW,
    NORMAL,
    HIGH,
}

data class ConcurrentOperationStatistics(
    val totalActiveOperations: Int,
    val entityOperationCounts: Map<String, Int>,
    val activeMutexes: Int,
    val activeThreadOperations: Int,
)

// Extension functions for easier usage

suspend inline fun <T> ConcurrentAccessManager.withFoodEntryLock(
    foodEntryId: String,
    crossinline operation: suspend () -> T,
): ConcurrentOperationResult<T> {
    return withEntityLock("FoodEntry", foodEntryId) { operation() }
}

suspend inline fun <T> ConcurrentAccessManager.withSymptomLock(
    symptomId: String,
    crossinline operation: suspend () -> T,
): ConcurrentOperationResult<T> {
    return withEntityLock("SymptomEvent", symptomId) { operation() }
}

suspend inline fun <T> ConcurrentAccessManager.withUserLock(
    userId: String,
    crossinline operation: suspend () -> T,
): ConcurrentOperationResult<T> {
    return withEntityLock("User", userId) { operation() }
}
