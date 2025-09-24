package com.fooddiary.data.concurrency;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\n\b\u0007\u0018\u0000 F2\u00020\u0001:\u0001FB\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002JT\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0011\"\u0004\b\u0000\u0010\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u00142\u0006\u0010\u0015\u001a\u00020\u00162\"\u0010\u0017\u001a\u001e\b\u0001\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00120\u00110\u0019\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0018H\u0082@\u00a2\u0006\u0002\u0010\u001aJ\u000e\u0010\u001b\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0010\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u0007H\u0002J\u001e\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u00072\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00070$H\u0002J\u0006\u0010%\u001a\u00020\u0016J\u000e\u0010&\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u0007J\u0006\u0010\'\u001a\u00020(J,\u0010)\u001a\u00020!2\u0006\u0010*\u001a\u00020\u00072\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00070$2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00070\rH\u0002J\u0010\u0010-\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u0007H\u0002J\\\u0010.\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0011\"\u0004\b\u0000\u0010\u00122\u0018\u0010/\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007000\u00142\b\b\u0002\u00101\u001a\u00020\u000f2\u001c\u0010\u0017\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00120\u0019\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0018H\u0086@\u00a2\u0006\u0002\u00102JJ\u00103\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0011\"\u0004\b\u0000\u0010\u00122\u0006\u0010\u001f\u001a\u00020\u00072\b\b\u0002\u00104\u001a\u00020\u00162\u001c\u0010\u0017\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00120\u0019\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0018H\u0086@\u00a2\u0006\u0002\u00105JR\u00106\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0011\"\u0004\b\u0000\u0010\u00122\u0006\u0010\u001f\u001a\u00020\u00072\u0006\u00107\u001a\u00020\u00072\b\b\u0002\u00101\u001a\u00020\u000f2\u001c\u0010\u0017\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00120\u0019\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0018H\u0086@\u00a2\u0006\u0002\u00108J2\u00109\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u00122\u001c\u0010\u0017\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00120\u0019\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0018H\u0086@\u00a2\u0006\u0002\u0010:JR\u0010;\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0011\"\u0004\b\u0000\u0010\u00122\u0006\u0010\u001f\u001a\u00020\u00072\u0006\u00107\u001a\u00020\u00072\b\b\u0002\u0010<\u001a\u00020=2\u001c\u0010\u0017\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00120\u0019\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0018H\u0086@\u00a2\u0006\u0002\u0010>J\\\u0010?\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0011\"\u0004\b\u0000\u0010\u00122\u0006\u0010\u001f\u001a\u00020\u00072\u0006\u00107\u001a\u00020\u00072\b\b\u0002\u0010@\u001a\u00020!2\b\b\u0002\u00101\u001a\u00020\u000f2\u001c\u0010\u0017\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00120\u0019\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0018H\u0086@\u00a2\u0006\u0002\u0010AJR\u0010B\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0011\"\u0004\b\u0000\u0010\u00122\b\b\u0002\u0010C\u001a\u00020\u00162\b\b\u0002\u0010D\u001a\u00020\u000f2\"\u0010\u0017\u001a\u001e\b\u0001\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00120\u00110\u0019\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0018H\u0086@\u00a2\u0006\u0002\u0010ER\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00040\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\f\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\r0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\r0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006G"}, d2 = {"Lcom/fooddiary/data/concurrency/ConcurrentAccessManager;", "", "()V", "activeOperations", "Ljava/util/concurrent/atomic/AtomicInteger;", "entityMutexes", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lkotlinx/coroutines/sync/Mutex;", "globalSemaphore", "Lkotlinx/coroutines/sync/Semaphore;", "operationCounters", "operationDependencies", "", "threadOperations", "", "acquireLocksRecursively", "Lcom/fooddiary/data/concurrency/ConcurrentOperationResult;", "T", "lockKeys", "", "index", "", "operation", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "(Ljava/util/List;ILkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cleanupStaleOperations", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "decrementOperationCounter", "entityType", "detectPotentialDeadlock", "", "requestedLock", "currentLocks", "", "getActiveOperationCount", "getEntityOperationCount", "getOperationStatistics", "Lcom/fooddiary/data/concurrency/ConcurrentOperationStatistics;", "hasCircularDependency", "target", "currentPath", "visited", "incrementOperationCounter", "withBatchLock", "entities", "Lkotlin/Pair;", "timeoutMs", "(Ljava/util/List;JLkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withBulkInsert", "batchSize", "(Ljava/lang/String;ILkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withEntityLock", "entityId", "(Ljava/lang/String;Ljava/lang/String;JLkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withGlobalLock", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withPriorityOperation", "priority", "Lcom/fooddiary/data/concurrency/OperationPriority;", "(Ljava/lang/String;Ljava/lang/String;Lcom/fooddiary/data/concurrency/OperationPriority;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withReadWriteLock", "isWrite", "(Ljava/lang/String;Ljava/lang/String;ZJLkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withRetry", "maxRetries", "backoffMs", "(IJLkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_release"})
public final class ConcurrentAccessManager {
    private static final long DEFAULT_TIMEOUT_MS = 30000L;
    private static final int MAX_CONCURRENT_OPERATIONS = 10;
    private static final int MAX_RETRIES = 3;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.sync.Semaphore globalSemaphore = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, kotlinx.coroutines.sync.Mutex> entityMutexes = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.atomic.AtomicInteger activeOperations = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, java.util.concurrent.atomic.AtomicInteger> operationCounters = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, java.util.Set<java.lang.String>> operationDependencies = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.Long, java.util.Set<java.lang.String>> threadOperations = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.fooddiary.data.concurrency.ConcurrentAccessManager.Companion Companion = null;
    
    @javax.inject.Inject()
    public ConcurrentAccessManager() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final <T extends java.lang.Object>java.lang.Object withGlobalLock(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> operation, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super T> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final <T extends java.lang.Object>java.lang.Object withEntityLock(@org.jetbrains.annotations.NotNull()
    java.lang.String entityType, @org.jetbrains.annotations.NotNull()
    java.lang.String entityId, long timeoutMs, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> operation, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.concurrency.ConcurrentOperationResult<? extends T>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final <T extends java.lang.Object>java.lang.Object withReadWriteLock(@org.jetbrains.annotations.NotNull()
    java.lang.String entityType, @org.jetbrains.annotations.NotNull()
    java.lang.String entityId, boolean isWrite, long timeoutMs, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> operation, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.concurrency.ConcurrentOperationResult<? extends T>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final <T extends java.lang.Object>java.lang.Object withRetry(int maxRetries, long backoffMs, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super com.fooddiary.data.concurrency.ConcurrentOperationResult<? extends T>>, ? extends java.lang.Object> operation, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.concurrency.ConcurrentOperationResult<? extends T>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final <T extends java.lang.Object>java.lang.Object withBatchLock(@org.jetbrains.annotations.NotNull()
    java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> entities, long timeoutMs, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> operation, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.concurrency.ConcurrentOperationResult<? extends T>> $completion) {
        return null;
    }
    
    private final <T extends java.lang.Object>java.lang.Object acquireLocksRecursively(java.util.List<java.lang.String> lockKeys, int index, kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super com.fooddiary.data.concurrency.ConcurrentOperationResult<? extends T>>, ? extends java.lang.Object> operation, kotlin.coroutines.Continuation<? super com.fooddiary.data.concurrency.ConcurrentOperationResult<? extends T>> $completion) {
        return null;
    }
    
    private final boolean detectPotentialDeadlock(java.lang.String requestedLock, java.util.Set<java.lang.String> currentLocks) {
        return false;
    }
    
    private final boolean hasCircularDependency(java.lang.String target, java.util.Set<java.lang.String> currentPath, java.util.Set<java.lang.String> visited) {
        return false;
    }
    
    private final void incrementOperationCounter(java.lang.String entityType) {
    }
    
    private final void decrementOperationCounter(java.lang.String entityType) {
    }
    
    public final int getActiveOperationCount() {
        return 0;
    }
    
    public final int getEntityOperationCount(@org.jetbrains.annotations.NotNull()
    java.lang.String entityType) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.concurrency.ConcurrentOperationStatistics getOperationStatistics() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final <T extends java.lang.Object>java.lang.Object withBulkInsert(@org.jetbrains.annotations.NotNull()
    java.lang.String entityType, int batchSize, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> operation, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.concurrency.ConcurrentOperationResult<? extends T>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final <T extends java.lang.Object>java.lang.Object withPriorityOperation(@org.jetbrains.annotations.NotNull()
    java.lang.String entityType, @org.jetbrains.annotations.NotNull()
    java.lang.String entityId, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.concurrency.OperationPriority priority, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> operation, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.concurrency.ConcurrentOperationResult<? extends T>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object cleanupStaleOperations(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/fooddiary/data/concurrency/ConcurrentAccessManager$Companion;", "", "()V", "DEFAULT_TIMEOUT_MS", "", "MAX_CONCURRENT_OPERATIONS", "", "MAX_RETRIES", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}