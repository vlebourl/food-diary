package com.fooddiary.presentation.ui.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.data.repository.SymptomEventRepository
import com.fooddiary.data.repository.CorrelationRepository
import com.fooddiary.data.repository.TriggerPatternRepository
import com.fooddiary.data.repository.EnvironmentalContextRepository
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.TriggerPattern
import com.fooddiary.data.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

/**
 * ViewModel for Analytics screen with chart data processing and insights
 * Manages analytics dashboard, time range selection, and data visualization
 */
@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val foodEntryRepository: FoodEntryRepository,
    private val symptomEventRepository: SymptomEventRepository,
    private val correlationRepository: CorrelationRepository,
    private val triggerPatternRepository: TriggerPatternRepository,
    private val environmentalContextRepository: EnvironmentalContextRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnalyticsUiState())
    val uiState: StateFlow<AnalyticsUiState> = _uiState.asStateFlow()

    init {
        loadAnalytics()
    }

    /**
     * Load analytics data for current time range
     */
    fun loadAnalytics() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)

                val timeRange = _uiState.value.selectedTimeRange
                val (startDate, endDate) = calculateDateRange(timeRange)

                // Collect data from repositories
                combine(
                    foodEntryRepository.getAllByDateRange(startDate, endDate),
                    symptomEventRepository.getAllByDateRange(startDate, endDate),
                    triggerPatternRepository.getHighConfidence(),
                    environmentalContextRepository.getByDateRange(startDate, endDate)
                ) { foodEntries, symptomEvents, triggerPatterns, environmentalData ->

                    val chartData = generateChartData(foodEntries, symptomEvents, environmentalData, timeRange)
                    val statistics = calculateStatistics(foodEntries, symptomEvents, environmentalData)
                    val insights = generateInsights(foodEntries, symptomEvents, triggerPatterns)
                    val correlations = generateCorrelationData(foodEntries, symptomEvents)

                    AnalyticsData(
                        chartData = chartData,
                        statistics = statistics,
                        insights = insights,
                        correlations = correlations
                    )
                }.collect { analyticsData ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        chartData = analyticsData.chartData,
                        statistics = analyticsData.statistics,
                        insights = analyticsData.insights,
                        correlationData = analyticsData.correlations
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error loading analytics"
                )
            }
        }
    }

    /**
     * Change the time range for analytics
     */
    fun changeTimeRange(timeRange: TimeRange) {
        _uiState.value = _uiState.value.copy(selectedTimeRange = timeRange)
        loadAnalytics()
    }

    /**
     * Refresh insights and data
     */
    fun refreshInsights() {
        loadAnalytics()
    }

    /**
     * Export analytics data
     */
    fun exportData(format: ExportFormat): String? {
        return try {
            val currentState = _uiState.value
            when (format) {
                ExportFormat.CSV -> exportToCSV(currentState)
                ExportFormat.PDF -> exportToPDF(currentState)
                ExportFormat.JSON -> exportToJSON(currentState)
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Get correlation data for specific food-symptom pairs
     */
    fun getCorrelations(foodId: String?, symptomType: SymptomType?): List<CorrelationData> {
        return _uiState.value.correlationData.filter { correlation ->
            (foodId == null || correlation.foodId == foodId) &&
            (symptomType == null || correlation.symptomType == symptomType)
        }
    }

    /**
     * Select specific chart type to display
     */
    fun selectChartType(chartType: ChartType) {
        _uiState.value = _uiState.value.copy(selectedChartType = chartType)
    }

    /**
     * Toggle chart metric visibility
     */
    fun toggleMetric(metric: AnalyticsMetric, visible: Boolean) {
        val currentMetrics = _uiState.value.visibleMetrics.toMutableSet()
        if (visible) {
            currentMetrics.add(metric)
        } else {
            currentMetrics.remove(metric)
        }
        _uiState.value = _uiState.value.copy(visibleMetrics = currentMetrics)
    }

    /**
     * Calculate date range based on TimeRange enum
     */
    private fun calculateDateRange(timeRange: TimeRange): Pair<LocalDate, LocalDate> {
        val endDate = LocalDate.now()
        val startDate = when (timeRange) {
            TimeRange.LAST_7_DAYS -> endDate.minusDays(7)
            TimeRange.LAST_30_DAYS -> endDate.minusDays(30)
            TimeRange.LAST_90_DAYS -> endDate.minusDays(90)
            TimeRange.LAST_6_MONTHS -> endDate.minusMonths(6)
            TimeRange.LAST_YEAR -> endDate.minusYears(1)
        }
        return Pair(startDate, endDate)
    }

    /**
     * Generate chart data for visualization
     */
    private fun generateChartData(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        environmentalData: List<com.fooddiary.data.database.entities.EnvironmentalContext>,
        timeRange: TimeRange
    ): Map<ChartType, ChartData> {
        val chartData = mutableMapOf<ChartType, ChartData>()

        // Symptom Frequency Chart
        val symptomFrequency = symptomEvents.groupBy { it.type }
            .mapValues { it.value.size }
        chartData[ChartType.SYMPTOM_FREQUENCY] = ChartData(
            labels = symptomFrequency.keys.map { it.name },
            values = symptomFrequency.values.map { it.toFloat() },
            type = ChartType.SYMPTOM_FREQUENCY
        )

        // Symptom Severity Timeline
        val severityTimeline = symptomEvents.groupBy {
            it.timestamp.truncatedTo(ChronoUnit.DAYS).toString()
        }.mapValues { entry ->
            entry.value.map { it.severity }.average().toFloat()
        }
        chartData[ChartType.SYMPTOM_SEVERITY] = ChartData(
            labels = severityTimeline.keys.toList(),
            values = severityTimeline.values.toList(),
            type = ChartType.SYMPTOM_SEVERITY
        )

        // Food Categories
        val foodCategories = foodEntries.flatMap { it.foods }
            .groupBy { getFoodCategory(it) }
            .mapValues { it.value.size }
        chartData[ChartType.FOOD_CATEGORIES] = ChartData(
            labels = foodCategories.keys.map { it.name },
            values = foodCategories.values.map { it.toFloat() },
            type = ChartType.FOOD_CATEGORIES
        )

        // Meal Distribution
        val mealDistribution = foodEntries.groupBy { it.mealType }
            .mapValues { it.value.size }
        chartData[ChartType.MEAL_DISTRIBUTION] = ChartData(
            labels = mealDistribution.keys.map { it.name },
            values = mealDistribution.values.map { it.toFloat() },
            type = ChartType.MEAL_DISTRIBUTION
        )

        return chartData
    }

    /**
     * Calculate summary statistics
     */
    private fun calculateStatistics(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        environmentalData: List<com.fooddiary.data.database.entities.EnvironmentalContext>
    ): Map<String, StatisticValue> {
        val stats = mutableMapOf<String, StatisticValue>()

        // Total entries
        stats["total_food_entries"] = StatisticValue(
            value = foodEntries.size.toFloat(),
            label = "Food Entries",
            change = 0f // TODO: Calculate percentage change from previous period
        )

        stats["total_symptoms"] = StatisticValue(
            value = symptomEvents.size.toFloat(),
            label = "Symptom Events",
            change = 0f
        )

        // Average severity
        val avgSeverity = symptomEvents.map { it.severity }.average()
        stats["avg_severity"] = StatisticValue(
            value = if (avgSeverity.isNaN()) 0f else avgSeverity.toFloat(),
            label = "Avg Severity",
            change = 0f
        )

        // Most common symptom
        val mostCommonSymptom = symptomEvents.groupBy { it.type }
            .maxByOrNull { it.value.size }?.key
        stats["most_common_symptom"] = StatisticValue(
            value = symptomEvents.count { it.type == mostCommonSymptom }.toFloat(),
            label = mostCommonSymptom?.name ?: "None",
            change = 0f
        )

        return stats
    }

    /**
     * Generate insights from data patterns
     */
    private suspend fun generateInsights(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        triggerPatterns: List<TriggerPattern>
    ): List<AnalyticsInsight> {
        val insights = mutableListOf<AnalyticsInsight>()

        // Pattern-based insights
        triggerPatterns.forEach { pattern ->
            insights.add(
                AnalyticsInsight(
                    title = "Trigger Pattern Detected",
                    description = "High correlation found between ${pattern.foodName} and ${pattern.symptomType.name}",
                    severity = InsightSeverity.HIGH,
                    recommendation = "Consider avoiding ${pattern.foodName} or consulting with a healthcare provider",
                    confidence = pattern.confidence
                )
            )
        }

        // Frequency insights
        if (symptomEvents.isNotEmpty()) {
            val recentSymptoms = symptomEvents.filter {
                it.timestamp.isAfter(java.time.Instant.now().minus(7, ChronoUnit.DAYS))
            }
            if (recentSymptoms.size > symptomEvents.size * 0.7) {
                insights.add(
                    AnalyticsInsight(
                        title = "Increased Symptom Activity",
                        description = "You've experienced more symptoms in the past week compared to your average",
                        severity = InsightSeverity.MEDIUM,
                        recommendation = "Review your recent food choices and consider tracking environmental factors",
                        confidence = 0.8
                    )
                )
            }
        }

        // Meal timing insights
        val lateNightMeals = foodEntries.count {
            val hour = it.timestamp.atZone(java.time.ZoneId.systemDefault()).hour
            hour >= 22 || hour <= 6
        }
        if (lateNightMeals > foodEntries.size * 0.2) {
            insights.add(
                AnalyticsInsight(
                    title = "Late Night Eating Pattern",
                    description = "You frequently eat late at night, which may affect digestion",
                    severity = InsightSeverity.LOW,
                    recommendation = "Try to finish eating at least 3 hours before bedtime",
                    confidence = 0.6
                )
            )
        }

        return insights
    }

    /**
     * Generate correlation data for display
     */
    private suspend fun generateCorrelationData(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<CorrelationData> {
        val correlations = correlationRepository.calculateCorrelations(
            foodEntries.map { it.id },
            symptomEvents.map { it.type }.distinct()
        )

        return correlations.map { correlation ->
            val foodEntry = foodEntries.find { it.id == correlation.foodEntryId }
            CorrelationData(
                foodId = correlation.foodEntryId,
                foodName = foodEntry?.foods?.joinToString(", ") ?: "Unknown",
                symptomType = correlation.symptomType,
                strength = correlation.strength,
                confidence = correlation.confidence,
                occurrences = correlation.occurrences
            )
        }
    }

    /**
     * Export data to CSV format
     */
    private fun exportToCSV(state: AnalyticsUiState): String {
        val csv = StringBuilder()
        csv.appendLine("Analytics Export - ${LocalDate.now()}")
        csv.appendLine("Time Range,${state.selectedTimeRange}")
        csv.appendLine("")

        // Statistics
        csv.appendLine("Statistics")
        csv.appendLine("Metric,Value,Label")
        state.statistics.forEach { (key, stat) ->
            csv.appendLine("$key,${stat.value},${stat.label}")
        }

        return csv.toString()
    }

    /**
     * Export data to PDF format (placeholder)
     */
    private fun exportToPDF(state: AnalyticsUiState): String {
        // TODO: Implement PDF export
        return "PDF export not implemented yet"
    }

    /**
     * Export data to JSON format
     */
    private fun exportToJSON(state: AnalyticsUiState): String {
        // TODO: Implement JSON export with proper serialization
        return "{\"export\": \"json format not implemented\"}"
    }

    /**
     * Get food category for categorization
     */
    private fun getFoodCategory(foodName: String): FoodCategory {
        // Simple categorization logic - in real app this would use FODMAP database
        return when {
            foodName.lowercase().contains("apple") || foodName.lowercase().contains("banana") -> FoodCategory.FRUITS
            foodName.lowercase().contains("bread") || foodName.lowercase().contains("rice") -> FoodCategory.GRAINS
            foodName.lowercase().contains("chicken") || foodName.lowercase().contains("beef") -> FoodCategory.PROTEIN
            foodName.lowercase().contains("milk") || foodName.lowercase().contains("cheese") -> FoodCategory.DAIRY
            else -> FoodCategory.OTHER
        }
    }
}

/**
 * UI State for Analytics screen
 */
data class AnalyticsUiState(
    val isLoading: Boolean = true,
    val selectedTimeRange: TimeRange = TimeRange.LAST_30_DAYS,
    val selectedChartType: ChartType = ChartType.SYMPTOM_FREQUENCY,
    val chartData: Map<ChartType, ChartData> = emptyMap(),
    val statistics: Map<String, StatisticValue> = emptyMap(),
    val insights: List<AnalyticsInsight> = emptyList(),
    val correlationData: List<CorrelationData> = emptyList(),
    val visibleMetrics: Set<AnalyticsMetric> = setOf(
        AnalyticsMetric.SYMPTOM_FREQUENCY,
        AnalyticsMetric.SEVERITY_TRENDS,
        AnalyticsMetric.FOOD_CORRELATIONS
    ),
    val error: String? = null
)

/**
 * Internal data class for combining analytics data
 */
private data class AnalyticsData(
    val chartData: Map<ChartType, ChartData>,
    val statistics: Map<String, StatisticValue>,
    val insights: List<AnalyticsInsight>,
    val correlations: List<CorrelationData>
)

/**
 * Time range options for analytics
 */
enum class TimeRange {
    LAST_7_DAYS,
    LAST_30_DAYS,
    LAST_90_DAYS,
    LAST_6_MONTHS,
    LAST_YEAR
}

/**
 * Chart types available in analytics
 */
enum class ChartType {
    SYMPTOM_FREQUENCY,
    SYMPTOM_SEVERITY,
    FOOD_CATEGORIES,
    MEAL_DISTRIBUTION,
    CORRELATION_HEATMAP
}

/**
 * Analytics metrics that can be toggled
 */
enum class AnalyticsMetric {
    SYMPTOM_FREQUENCY,
    SEVERITY_TRENDS,
    FOOD_CORRELATIONS,
    MEAL_TIMING,
    ENVIRONMENTAL_FACTORS
}

/**
 * Export format options
 */
enum class ExportFormat {
    CSV, PDF, JSON
}

/**
 * Chart data structure
 */
data class ChartData(
    val labels: List<String>,
    val values: List<Float>,
    val type: ChartType
)

/**
 * Statistic value with change indicator
 */
data class StatisticValue(
    val value: Float,
    val label: String,
    val change: Float // Percentage change from previous period
)

/**
 * Analytics insight with recommendations
 */
data class AnalyticsInsight(
    val title: String,
    val description: String,
    val severity: InsightSeverity,
    val recommendation: String,
    val confidence: Double
)

/**
 * Insight severity levels
 */
enum class InsightSeverity {
    LOW, MEDIUM, HIGH
}

/**
 * Correlation data for display
 */
data class CorrelationData(
    val foodId: String,
    val foodName: String,
    val symptomType: SymptomType,
    val strength: Double,
    val confidence: Double,
    val occurrences: Int
)