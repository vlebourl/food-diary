package com.fooddiary.data.repository

import com.fooddiary.data.database.dao.SymptomEventDao
import com.fooddiary.data.database.dao.FoodEntryDao
import com.fooddiary.data.database.dao.CorrelationPatternDao
import com.fooddiary.data.database.dao.TriggerPatternDao
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.CorrelationPattern
import com.fooddiary.data.models.*
import com.fooddiary.data.analysis.CorrelationAnalyzer
import com.fooddiary.data.analysis.SeverityTrend
import com.fooddiary.data.analysis.SymptomPatternAnalysis
import com.fooddiary.data.analysis.SymptomCluster
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for symptom tracking and correlation analysis
 */
@Singleton
class SymptomRepository @Inject constructor(
    private val symptomEventDao: SymptomEventDao,
    private val foodEntryDao: FoodEntryDao,
    private val correlationPatternDao: CorrelationPatternDao,
    private val triggerPatternDao: TriggerPatternDao,
    private val correlationAnalyzer: CorrelationAnalyzer
) {

    /**
     * Log a new symptom event
     */
    suspend fun logSymptom(
        symptomType: SymptomType,
        severity: Int,
        duration: Duration? = null,
        notes: String? = null,
        timestamp: Instant = Instant.now()
    ): Result<Long> {
        return try {
            val symptomEvent = SymptomEvent.create(
                symptomType = symptomType,
                severity = severity,
                timestamp = timestamp,
                duration = duration,
                notes = notes
            )

            val insertedId = symptomEventDao.insert(symptomEvent)

            // Trigger correlation analysis in background
            analyzeCorrelationsForNewSymptom(symptomEvent)

            Result.success(insertedId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Update an existing symptom event
     */
    suspend fun updateSymptom(symptom: SymptomEvent): Result<Unit> {
        return try {
            val existingSymptom = symptomEventDao.getById(symptom.id)
                ?: return Result.failure(Exception("Symptom event not found"))

            val updatedSymptom = symptom.copy(modifiedAt = Instant.now())
            symptomEventDao.update(updatedSymptom)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Soft delete a symptom event
     */
    suspend fun deleteSymptom(symptomId: Long): Result<Unit> {
        return try {
            val existingSymptom = symptomEventDao.getById(symptomId)
                ?: return Result.failure(Exception("Symptom event not found"))

            val deletedSymptom = existingSymptom.softDelete()
            symptomEventDao.update(deletedSymptom)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Find correlations between symptoms and foods
     */
    suspend fun findCorrelations(
        symptomType: SymptomType? = null,
        minConfidence: Float = 0.5f,
        maxTimeWindowHours: Int = 24
    ): List<CorrelationPattern> {
        val allFoodEntries = foodEntryDao.getAllSync()
        val symptomEvents = if (symptomType != null) {
            symptomEventDao.getBySymptomType(symptomType)
        } else {
            symptomEventDao.getAllSync()
        }

        val correlations = correlationAnalyzer.analyzeCorrelations(
            foodEntries = allFoodEntries,
            symptomEvents = symptomEvents,
            maxTimeWindowHours = maxTimeWindowHours
        )

        return correlations.filter { it.correlationStrength >= minConfidence }
    }

    /**
     * Get severity trends for symptom types
     */
    suspend fun getSeverityTrends(
        symptomType: SymptomType,
        daysPeriod: Int = 30
    ): SeverityTrend {
        val symptomEvents = symptomEventDao.getAllSync()
        return correlationAnalyzer.calculateSeverityTrend(
            symptomType = symptomType,
            symptomEvents = symptomEvents,
            daysPeriod = daysPeriod
        )
    }

    /**
     * Analyze symptom patterns and clustering
     */
    suspend fun analyzePatterns(daysPeriod: Int = 30): SymptomPatternAnalysis {
        val cutoffTime = Instant.now().minus(daysPeriod.toLong(), ChronoUnit.DAYS)
        val recentSymptoms = symptomEventDao.getAllSync().filter {
            it.timestamp.isAfter(cutoffTime)
        }

        return correlationAnalyzer.analyzeSymptomPatterns(recentSymptoms)
    }

    /**
     * Cluster symptoms by time and severity
     */
    suspend fun clusterSymptoms(
        timeWindowHours: Int = 6,
        minClusterSize: Int = 2
    ): List<SymptomCluster> {
        val allSymptoms = symptomEventDao.getAllSync()
        val patterns = correlationAnalyzer.analyzeSymptomPatterns(allSymptoms)

        return patterns.clusters.filter { it.events.size >= minClusterSize }
    }

    /**
     * Get symptom statistics for a time period
     */
    suspend fun getSymptomStatistics(daysPeriod: Int = 30): SymptomStatistics {
        val cutoffTime = Instant.now().minus(daysPeriod.toLong(), ChronoUnit.DAYS)
        val recentSymptoms = symptomEventDao.getAllSync().filter {
            it.timestamp.isAfter(cutoffTime)
        }

        val totalSymptoms = recentSymptoms.size
        val averageSeverity = if (recentSymptoms.isNotEmpty()) {
            recentSymptoms.map { it.severity }.average().toFloat()
        } else 0f

        val symptomFrequency = recentSymptoms.groupBy { it.symptomType }
            .mapValues { it.value.size }

        val mostCommonSymptom = symptomFrequency.maxByOrNull { it.value }?.key
        val averageDailySymptoms = totalSymptoms.toFloat() / daysPeriod

        val clusters = clusterSymptoms()
        val trends = symptomFrequency.keys.associateWith { symptomType ->
            getSeverityTrends(symptomType, daysPeriod)
        }

        return SymptomStatistics(
            totalSymptoms = totalSymptoms,
            averageSeverity = averageSeverity,
            mostCommonSymptom = mostCommonSymptom,
            symptomFrequency = symptomFrequency,
            averageDailySymptoms = averageDailySymptoms,
            symptomClusters = clusters,
            severityTrends = trends,
            periodDays = daysPeriod
        )
    }

    /**
     * Get potential trigger foods for symptoms
     */
    suspend fun getTriggerFoods(
        symptomType: SymptomType,
        minOccurrences: Int = 2
    ): List<TriggerFoodAnalysis> {
        val symptomEvents = symptomEventDao.getBySymptomType(symptomType)
        val allFoodEntries = foodEntryDao.getAllSync()

        val triggerAnalysis = mutableMapOf<String, TriggerFoodStats>()

        for (symptom in symptomEvents) {
            val triggers = correlationAnalyzer.identifyTriggerFoods(
                symptom = symptom,
                allFoodEntries = allFoodEntries
            )

            triggers.forEach { (food, score) ->
                food.foods.forEach { foodName ->
                    val stats = triggerAnalysis.getOrDefault(foodName, TriggerFoodStats())
                    triggerAnalysis[foodName] = stats.copy(
                        occurrences = stats.occurrences + 1,
                        totalScore = stats.totalScore + score,
                        severitySum = stats.severitySum + symptom.severity
                    )
                }
            }
        }

        return triggerAnalysis.entries
            .filter { it.value.occurrences >= minOccurrences }
            .map { (foodName, stats) ->
                TriggerFoodAnalysis(
                    foodName = foodName,
                    occurrences = stats.occurrences,
                    averageScore = stats.totalScore / stats.occurrences,
                    averageSeverity = stats.severitySum / stats.occurrences,
                    riskLevel = calculateRiskLevel(stats)
                )
            }
            .sortedByDescending { it.averageScore }
    }

    /**
     * Get symptom events flow for reactive UI
     */
    fun getSymptomEvents(): Flow<List<SymptomEvent>> {
        return symptomEventDao.getAll().map { events ->
            events.filter { !it.isDeleted }
        }
    }

    /**
     * Get symptoms for a specific date range
     */
    fun getSymptomsInRange(
        startDate: java.time.LocalDate,
        endDate: java.time.LocalDate
    ): Flow<List<SymptomEvent>> {
        return symptomEventDao.getAll().map { events ->
            events.filter { event ->
                val eventDate = event.timestamp.atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                !event.isDeleted &&
                !eventDate.isBefore(startDate) &&
                !eventDate.isAfter(endDate)
            }
        }
    }

    /**
     * Search symptoms by notes or type
     */
    suspend fun searchSymptoms(query: String): List<SymptomEvent> {
        val allSymptoms = symptomEventDao.getAllSync()

        return allSymptoms.filter { symptom ->
            symptom.symptomType.displayName.lowercase().contains(query.lowercase()) ||
            symptom.notes?.lowercase()?.contains(query.lowercase()) == true
        }
    }

    // Private helper methods

    private suspend fun analyzeCorrelationsForNewSymptom(symptom: SymptomEvent) {
        try {
            val recentFoodEntries = foodEntryDao.getCorrelationData(
                symptom.timestamp.minus(48, ChronoUnit.HOURS)
            )

            val triggers = correlationAnalyzer.identifyTriggerFoods(
                symptom = symptom,
                allFoodEntries = recentFoodEntries,
                lookbackHours = 24
            )

            // Update correlation patterns for high-scoring triggers
            triggers.filter { it.second > 0.6f }.forEach { (food, score) ->
                updateCorrelationPattern(food, symptom, score)
            }
        } catch (e: Exception) {
            // Log error but don't fail the symptom creation
        }
    }

    private suspend fun updateCorrelationPattern(
        food: FoodEntry,
        symptom: SymptomEvent,
        score: Float
    ) {
        val existingCorrelations = correlationPatternDao.getAll()
        val existingCorrelation = existingCorrelations.find {
            it.foodId == food.id && it.symptomId == symptom.id
        }

        if (existingCorrelation != null) {
            // Update existing correlation
            val updatedCorrelation = existingCorrelation.copy(
                correlationStrength = maxOf(existingCorrelation.correlationStrength, score),
                occurrenceCount = existingCorrelation.occurrenceCount + 1,
                calculatedAt = Instant.now()
            )
            correlationPatternDao.update(updatedCorrelation)
        } else {
            // Create new correlation
            val timeOffset = Duration.between(food.timestamp, symptom.timestamp).toHours().toInt()
            val confidence = when {
                score >= 0.8f -> ConfidenceLevel.HIGH
                score >= 0.6f -> ConfidenceLevel.MEDIUM
                else -> ConfidenceLevel.LOW
            }

            val newCorrelation = CorrelationPattern.create(
                foodId = food.id,
                symptomId = symptom.id,
                correlationStrength = score,
                confidenceLevel = confidence,
                timeOffsetHours = timeOffset,
                occurrenceCount = 1
            )
            correlationPatternDao.insert(newCorrelation)
        }
    }

    private fun calculateRiskLevel(stats: TriggerFoodStats): RiskLevel {
        val averageScore = stats.totalScore / stats.occurrences
        val averageSeverity = stats.severitySum / stats.occurrences

        return when {
            averageScore >= 0.8f && averageSeverity >= 7f -> RiskLevel.HIGH
            averageScore >= 0.6f && averageSeverity >= 5f -> RiskLevel.MEDIUM
            averageScore >= 0.4f || averageSeverity >= 3f -> RiskLevel.LOW
            else -> RiskLevel.MINIMAL
        }
    }
}

// Supporting data classes

data class SymptomStatistics(
    val totalSymptoms: Int,
    val averageSeverity: Float,
    val mostCommonSymptom: SymptomType?,
    val symptomFrequency: Map<SymptomType, Int>,
    val averageDailySymptoms: Float,
    val symptomClusters: List<SymptomCluster>,
    val severityTrends: Map<SymptomType, SeverityTrend>,
    val periodDays: Int
)

data class TriggerFoodAnalysis(
    val foodName: String,
    val occurrences: Int,
    val averageScore: Float,
    val averageSeverity: Float,
    val riskLevel: RiskLevel
)

data class TriggerFoodStats(
    val occurrences: Int = 0,
    val totalScore: Float = 0f,
    val severitySum: Float = 0f
)

enum class RiskLevel {
    MINIMAL,
    LOW,
    MEDIUM,
    HIGH;

    val displayName: String
        get() = when (this) {
            MINIMAL -> "Minimal Risk"
            LOW -> "Low Risk"
            MEDIUM -> "Medium Risk"
            HIGH -> "High Risk"
        }

    val colorCode: String
        get() = when (this) {
            MINIMAL -> "#4CAF50" // Green
            LOW -> "#8BC34A"    // Light Green
            MEDIUM -> "#FF9800" // Orange
            HIGH -> "#F44336"   // Red
        }
}