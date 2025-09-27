package com.fooddiary.data.repository

import com.fooddiary.data.database.dao.FoodEntryDao
import com.fooddiary.data.database.dao.SymptomEventDao
import com.fooddiary.data.database.dao.CorrelationPatternDao
import com.fooddiary.data.database.dao.TriggerPatternDao
import com.fooddiary.data.database.dao.EnvironmentalContextDao
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.CorrelationPattern
import com.fooddiary.data.analytics.StatisticsEngine
import com.fooddiary.data.analytics.PatternDetector
import com.fooddiary.data.analytics.InsightGenerator
import com.fooddiary.data.analytics.*
import com.fooddiary.data.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for analytics and statistical analysis of food diary data
 */
@Singleton
class AnalyticsRepository @Inject constructor(
    private val foodEntryDao: FoodEntryDao,
    private val symptomEventDao: SymptomEventDao,
    private val correlationPatternDao: CorrelationPatternDao,
    private val triggerPatternDao: TriggerPatternDao,
    private val environmentalContextDao: EnvironmentalContextDao,
    private val statisticsEngine: StatisticsEngine,
    private val patternDetector: PatternDetector,
    private val insightGenerator: InsightGenerator
) {

    /**
     * Calculate comprehensive statistics for all data
     */
    suspend fun calculateCorrelations(
        startDate: LocalDate? = null,
        endDate: LocalDate? = null
    ): List<StatisticalCorrelation> {
        val foodEntries = getFilteredFoodEntries(startDate, endDate)
        val symptomEvents = getFilteredSymptomEvents(startDate, endDate)

        return statisticsEngine.calculateCorrelations(foodEntries, symptomEvents)
    }

    /**
     * Get comprehensive analytics statistics
     */
    suspend fun getStatistics(periodDays: Int = 90): AnalyticsStatistics {
        val cutoffTime = Instant.now().minus(periodDays.toLong(), ChronoUnit.DAYS)

        val foodEntries = foodEntryDao.getAllSync().filter { it.timestamp.isAfter(cutoffTime) }
        val symptomEvents = symptomEventDao.getAllSync().filter { it.timestamp.isAfter(cutoffTime) }
        val correlations = correlationPatternDao.getAll()

        return statisticsEngine.calculateStatistics(foodEntries, symptomEvents, correlations)
    }

    /**
     * Get trigger patterns with statistical analysis
     */
    suspend fun getTriggerPatterns(
        minOccurrences: Int = 3,
        minConfidence: Float = 0.6f
    ): List<TriggerStatistics> {
        val foodEntries = foodEntryDao.getAllSync()
        val symptomEvents = symptomEventDao.getAllSync()

        return statisticsEngine.getTriggerPatterns(foodEntries, symptomEvents)
            .filter { it.occurrences >= minOccurrences && it.riskScore >= minConfidence }
    }

    /**
     * Generate actionable insights from all data
     */
    suspend fun generateInsights(periodDays: Int = 90): List<HealthInsight> {
        val cutoffTime = Instant.now().minus(periodDays.toLong(), ChronoUnit.DAYS)

        val foodEntries = foodEntryDao.getAllSync().filter { it.timestamp.isAfter(cutoffTime) }
        val symptomEvents = symptomEventDao.getAllSync().filter { it.timestamp.isAfter(cutoffTime) }
        val correlations = correlationPatternDao.getAll()

        // Detect patterns
        val temporalPatterns = patternDetector.detectTemporalPatterns(symptomEvents)

        return insightGenerator.generateInsights(
            foodEntries = foodEntries,
            symptomEvents = symptomEvents,
            correlations = correlations,
            patterns = temporalPatterns
        )
    }

    /**
     * Predict risk for consuming specific foods
     */
    suspend fun predictRisk(foods: List<String>): Map<String, RiskPrediction> {
        val foodEntries = foodEntryDao.getAllSync()
        val symptomEvents = symptomEventDao.getAllSync()

        return statisticsEngine.predictRisk(foodEntries, symptomEvents, foods)
    }

    /**
     * Analyze trends over time
     */
    suspend fun analyzeTrends(periodDays: Int = 90): TrendAnalysis {
        val cutoffTime = Instant.now().minus(periodDays.toLong(), ChronoUnit.DAYS)
        val symptomEvents = symptomEventDao.getAllSync()
            .filter { it.timestamp.isAfter(cutoffTime) }

        return statisticsEngine.analyzeTrends(symptomEvents, periodDays)
    }

    /**
     * Get personalized recommendations
     */
    suspend fun getRecommendations(): List<HealthRecommendation> {
        val foodEntries = foodEntryDao.getAllSync()
        val symptomEvents = symptomEventDao.getAllSync()
        val correlations = correlationPatternDao.getAll()

        return insightGenerator.generateRecommendations(
            foodEntries = foodEntries,
            symptomEvents = symptomEvents,
            correlations = correlations
        )
    }

    /**
     * Detect data clusters and patterns
     */
    suspend fun detectClusters(): List<DataCluster> {
        val foodEntries = foodEntryDao.getAllSync()
        val symptomEvents = symptomEventDao.getAllSync()

        return insightGenerator.detectClusters(foodEntries, symptomEvents)
    }

    /**
     * Get temporal patterns in symptom occurrence
     */
    suspend fun getTemporalPatterns(): List<TemporalPattern> {
        val symptomEvents = symptomEventDao.getAllSync()
        return patternDetector.detectTemporalPatterns(symptomEvents)
    }

    /**
     * Get symptom clustering patterns
     */
    suspend fun getClusterPatterns(maxGapHours: Int = 24): List<ClusterPattern> {
        val symptomEvents = symptomEventDao.getAllSync()
        return patternDetector.detectClusterPatterns(symptomEvents, maxGapHours)
    }

    /**
     * Get food combination patterns that trigger symptoms
     */
    suspend fun getFoodCombinationPatterns(): List<CombinationPattern> {
        val foodEntries = foodEntryDao.getAllSync()
        val symptomEvents = symptomEventDao.getAllSync()

        return patternDetector.detectFoodCombinationPatterns(foodEntries, symptomEvents)
    }

    /**
     * Get environmental patterns affecting symptoms
     */
    suspend fun getEnvironmentalPatterns(): List<EnvironmentalPattern> {
        val symptomEvents = symptomEventDao.getAllSync()
        val environmentalContexts = environmentalContextDao.getAllSync()

        return patternDetector.detectEnvironmentalPatterns(symptomEvents, environmentalContexts)
    }

    /**
     * Detect cyclical patterns (weekly, monthly, seasonal)
     */
    suspend fun getCyclicalPatterns(): List<CyclicalPattern> {
        val symptomEvents = symptomEventDao.getAllSync()
        return patternDetector.detectCyclicalPatterns(symptomEvents)
    }

    /**
     * Get severity escalation patterns
     */
    suspend fun getEscalationPatterns(): List<EscalationPattern> {
        val symptomEvents = symptomEventDao.getAllSync()
        return patternDetector.detectEscalationPatterns(symptomEvents)
    }

    /**
     * Get comprehensive analytics dashboard data
     */
    fun getAnalyticsDashboard(): Flow<AnalyticsDashboard> {
        return combine(
            foodEntryDao.getAll(),
            symptomEventDao.getAll()
        ) { foodEntries, symptomEvents ->
            val recentFoodEntries = foodEntries.filter {
                ChronoUnit.DAYS.between(it.timestamp, Instant.now()) <= 30
            }
            val recentSymptomEvents = symptomEvents.filter {
                ChronoUnit.DAYS.between(it.timestamp, Instant.now()) <= 30
            }

            AnalyticsDashboard(
                totalEntries = recentFoodEntries.size + recentSymptomEvents.size,
                symptomFreeStreak = calculateSymptomFreeStreak(recentSymptomEvents),
                averageSeverity = if (recentSymptomEvents.isNotEmpty()) {
                    recentSymptomEvents.map { it.severity }.average().toFloat()
                } else 0f,
                mostCommonSymptom = findMostCommonSymptom(recentSymptomEvents),
                weeklyTrend = calculateWeeklyTrend(recentSymptomEvents),
                topTriggerFoods = emptyList(), // Would need correlation analysis
                riskScore = calculateOverallRiskScore(recentSymptomEvents),
                improvementScore = calculateImprovementScore(symptomEvents)
            )
        }
    }

    /**
     * Export analytics data for external analysis
     */
    suspend fun exportAnalyticsData(
        format: ExportFormat,
        startDate: LocalDate? = null,
        endDate: LocalDate? = null
    ): Result<String> {
        return try {
            val foodEntries = getFilteredFoodEntries(startDate, endDate)
            val symptomEvents = getFilteredSymptomEvents(startDate, endDate)
            val correlations = correlationPatternDao.getAll()
            val statistics = statisticsEngine.calculateStatistics(foodEntries, symptomEvents, correlations)

            val exportData = AnalyticsExportData(
                statistics = statistics,
                correlations = statisticsEngine.calculateCorrelations(foodEntries, symptomEvents),
                insights = generateInsights(90),
                trends = analyzeTrends(90)
            )

            val exportContent = when (format) {
                ExportFormat.JSON -> exportToJson(exportData)
                ExportFormat.CSV -> exportToCsv(exportData)
                ExportFormat.PDF -> exportToPdf(exportData)
            }

            Result.success(exportContent)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Perform batch analysis for multiple time periods
     */
    suspend fun performBatchAnalysis(periods: List<AnalysisPeriod>): List<PeriodAnalysis> {
        return periods.map { period ->
            val startDate = period.startDate
            val endDate = period.endDate

            val foodEntries = getFilteredFoodEntries(startDate, endDate)
            val symptomEvents = getFilteredSymptomEvents(startDate, endDate)
            val correlations = correlationPatternDao.getAll()

            val statistics = statisticsEngine.calculateStatistics(foodEntries, symptomEvents, correlations)
            val temporalPatterns = patternDetector.detectTemporalPatterns(symptomEvents)

            PeriodAnalysis(
                period = period,
                statistics = statistics,
                patterns = temporalPatterns,
                insights = insightGenerator.generateInsights(
                    foodEntries, symptomEvents, correlations, temporalPatterns
                )
            )
        }
    }

    // Private helper methods

    private suspend fun getFilteredFoodEntries(startDate: LocalDate?, endDate: LocalDate?): List<FoodEntry> {
        val allEntries = foodEntryDao.getAllSync()

        return if (startDate != null || endDate != null) {
            allEntries.filter { entry ->
                val entryDate = entry.timestamp.atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                (startDate == null || !entryDate.isBefore(startDate)) &&
                (endDate == null || !entryDate.isAfter(endDate))
            }
        } else {
            allEntries
        }
    }

    private suspend fun getFilteredSymptomEvents(startDate: LocalDate?, endDate: LocalDate?): List<SymptomEvent> {
        val allEvents = symptomEventDao.getAllSync()

        return if (startDate != null || endDate != null) {
            allEvents.filter { event ->
                val eventDate = event.timestamp.atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                (startDate == null || !eventDate.isBefore(startDate)) &&
                (endDate == null || !eventDate.isAfter(endDate))
            }
        } else {
            allEvents
        }
    }

    private fun calculateSymptomFreeStreak(symptomEvents: List<SymptomEvent>): Int {
        val sortedEvents = symptomEvents.sortedByDescending { it.timestamp }
        return if (sortedEvents.isNotEmpty()) {
            ChronoUnit.DAYS.between(sortedEvents.first().timestamp, Instant.now()).toInt()
        } else 0
    }

    private fun findMostCommonSymptom(symptomEvents: List<SymptomEvent>): SymptomType? {
        return symptomEvents.groupBy { it.symptomType }
            .maxByOrNull { it.value.size }?.key
    }

    private fun calculateWeeklyTrend(symptomEvents: List<SymptomEvent>): Float {
        val recentWeek = symptomEvents.filter {
            ChronoUnit.DAYS.between(it.timestamp, Instant.now()) <= 7
        }
        val previousWeek = symptomEvents.filter {
            val days = ChronoUnit.DAYS.between(it.timestamp, Instant.now())
            days > 7 && days <= 14
        }

        val recentAvg = if (recentWeek.isNotEmpty()) {
            recentWeek.map { it.severity }.average().toFloat()
        } else 0f

        val previousAvg = if (previousWeek.isNotEmpty()) {
            previousWeek.map { it.severity }.average().toFloat()
        } else 0f

        return recentAvg - previousAvg
    }

    private fun calculateOverallRiskScore(symptomEvents: List<SymptomEvent>): Float {
        if (symptomEvents.isEmpty()) return 0f

        val avgSeverity = symptomEvents.map { it.severity }.average().toFloat()
        val frequency = symptomEvents.size / 30f // Normalized to per day over 30 days

        return ((avgSeverity / 10f) * 0.7f + (frequency / 5f) * 0.3f).coerceIn(0f, 1f)
    }

    private fun calculateImprovementScore(allSymptomEvents: List<SymptomEvent>): Float {
        if (allSymptomEvents.size < 10) return 0f

        val sortedEvents = allSymptomEvents.sortedBy { it.timestamp }
        val firstQuarter = sortedEvents.take(sortedEvents.size / 4)
        val lastQuarter = sortedEvents.takeLast(sortedEvents.size / 4)

        val earlyAvg = firstQuarter.map { it.severity }.average().toFloat()
        val recentAvg = lastQuarter.map { it.severity }.average().toFloat()

        val improvement = earlyAvg - recentAvg // Positive means improvement
        return (improvement / 10f).coerceIn(-1f, 1f) // Normalized to -1 to 1
    }

    private fun exportToJson(data: AnalyticsExportData): String {
        // Simplified JSON export - would use actual JSON library in production
        return """
            {
                "statistics": ${data.statistics},
                "correlations": ${data.correlations},
                "insights": ${data.insights},
                "trends": ${data.trends}
            }
        """.trimIndent()
    }

    private fun exportToCsv(data: AnalyticsExportData): String {
        // Simplified CSV export
        return "Type,Value\nTotal Entries,${data.statistics.totalEntries}\nAverage Severity,${data.statistics.averageSeverity}"
    }

    private fun exportToPdf(data: AnalyticsExportData): String {
        // Would generate PDF content in production
        return "PDF export not implemented"
    }
}

// Supporting data classes

data class AnalyticsDashboard(
    val totalEntries: Int,
    val symptomFreeStreak: Int,
    val averageSeverity: Float,
    val mostCommonSymptom: SymptomType?,
    val weeklyTrend: Float, // Positive means worsening
    val topTriggerFoods: List<String>,
    val riskScore: Float, // 0-1 scale
    val improvementScore: Float // -1 to 1, positive means improving
)

data class AnalyticsExportData(
    val statistics: AnalyticsStatistics,
    val correlations: List<StatisticalCorrelation>,
    val insights: List<HealthInsight>,
    val trends: TrendAnalysis
)

data class AnalysisPeriod(
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate
)

data class PeriodAnalysis(
    val period: AnalysisPeriod,
    val statistics: AnalyticsStatistics,
    val patterns: List<TemporalPattern>,
    val insights: List<HealthInsight>
)

enum class ExportFormat {
    JSON,
    CSV,
    PDF
}