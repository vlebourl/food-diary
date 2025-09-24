package com.fooddiary.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooddiary.data.database.entities.TriggerPattern
import com.fooddiary.data.models.SymptomType
import com.fooddiary.data.repository.TriggerPatternRepository
import com.fooddiary.data.repository.SymptomEventRepository
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.domain.usecase.CalculateCorrelationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val triggerPatternRepository: TriggerPatternRepository,
    private val symptomEventRepository: SymptomEventRepository,
    private val foodEntryRepository: FoodEntryRepository,
    private val calculateCorrelationsUseCase: CalculateCorrelationsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnalyticsUiState())
    val uiState: StateFlow<AnalyticsUiState> = _uiState.asStateFlow()

    private val _selectedTimeRange = MutableStateFlow(TimeRange.LAST_30_DAYS)
    val selectedTimeRange: StateFlow<TimeRange> = _selectedTimeRange.asStateFlow()

    val statisticallySignificantPatterns = triggerPatternRepository.getStatisticallySignificant()
    val highConfidencePatterns = triggerPatternRepository.getHighConfidence()
    val allPatterns = triggerPatternRepository.getAll()

    init {
        loadAnalyticsData()
        observeTimeRangeChanges()
    }

    private fun observeTimeRangeChanges() {
        viewModelScope.launch {
            selectedTimeRange.collect { timeRange ->
                loadAnalyticsData(timeRange)
            }
        }
    }

    fun updateTimeRange(timeRange: TimeRange) {
        _selectedTimeRange.value = timeRange
    }

    fun refreshAnalytics() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val timeRange = _selectedTimeRange.value
                val (startDate, endDate) = timeRange.getDateRange()

                // Recalculate correlations
                calculateCorrelationsUseCase(startDate, endDate)

                // Reload analytics data
                loadAnalyticsData(timeRange)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    lastUpdated = System.currentTimeMillis()
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to refresh analytics"
                )
            }
        }
    }

    private fun loadAnalyticsData(timeRange: TimeRange = _selectedTimeRange.value) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                val (startDate, endDate) = timeRange.getDateRange()

                // Get summary statistics
                val totalSymptoms = symptomEventRepository.getCountByDateRange(
                    startDate.toString(),
                    endDate.toString()
                ).first()

                val totalFoodEntries = foodEntryRepository.getCountByDateRange(
                    startDate.toString(),
                    endDate.toString()
                ).first()

                val patternStats = getPatternStatistics()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    totalSymptoms = totalSymptoms,
                    totalFoodEntries = totalFoodEntries,
                    statisticallySignificantCount = patternStats.significantCount,
                    highConfidenceCount = patternStats.highConfidenceCount,
                    totalPatternsCount = patternStats.totalCount,
                    timeRangeDescription = timeRange.description
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to load analytics data"
                )
            }
        }
    }

    private suspend fun getPatternStatistics(): PatternStatistics {
        return try {
            val significantCount = triggerPatternRepository.getSignificantCount()
            val allPatterns = triggerPatternRepository.getCount()
            val highConfidenceCount = triggerPatternRepository.getHighConfidence().first().size

            PatternStatistics(
                significantCount = significantCount,
                highConfidenceCount = highConfidenceCount,
                totalCount = allPatterns
            )
        } catch (e: Exception) {
            PatternStatistics(0, 0, 0)
        }
    }

    fun getPatternsBySymptomType(symptomType: SymptomType): Flow<List<TriggerPattern>> {
        return triggerPatternRepository.getBySymptomType(symptomType)
    }

    fun getPatternsByFood(foodName: String): Flow<List<TriggerPattern>> {
        return triggerPatternRepository.getByFood(foodName)
    }

    fun getTopTriggerFoods(limit: Int = 10): Flow<List<TriggerFoodSummary>> {
        return triggerPatternRepository.getAll().map { patterns ->
            patterns
                .filter { it.isStatisticallySignificant }
                .groupBy { it.foodName }
                .map { (foodName, foodPatterns) ->
                    TriggerFoodSummary(
                        foodName = foodName,
                        symptomTypes = foodPatterns.map { it.symptomType }.distinct(),
                        averageCorrelation = foodPatterns.map { it.correlationStrength }.average().toFloat(),
                        totalOccurrences = foodPatterns.sumOf { it.occurrences },
                        averageTimeToOnset = foodPatterns.map { it.averageTimeOffsetMinutes }.average().toInt()
                    )
                }
                .sortedByDescending { it.averageCorrelation }
                .take(limit)
        }
    }

    fun getTopSymptomTriggers(limit: Int = 10): Flow<List<SymptomTriggerSummary>> {
        return triggerPatternRepository.getAll().map { patterns ->
            patterns
                .filter { it.isStatisticallySignificant }
                .groupBy { it.symptomType }
                .map { (symptomType, symptomPatterns) ->
                    SymptomTriggerSummary(
                        symptomType = symptomType,
                        triggerFoods = symptomPatterns.map { it.foodName }.distinct(),
                        averageCorrelation = symptomPatterns.map { it.correlationStrength }.average().toFloat(),
                        totalOccurrences = symptomPatterns.sumOf { it.occurrences },
                        averageSeverity = 6.5f // Would need to calculate from actual symptom events
                    )
                }
                .sortedByDescending { it.averageCorrelation }
                .take(limit)
        }
    }

    fun getSymptomFrequencyData(): Flow<List<SymptomFrequencyData>> {
        return symptomEventRepository.getSymptomFrequency().map { frequencyMap ->
            frequencyMap.map { (symptomType, count) ->
                SymptomFrequencyData(
                    symptomType = symptomType,
                    count = count,
                    percentage = 0f // Would calculate based on total symptoms
                )
            }.sortedByDescending { it.count }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

data class AnalyticsUiState(
    val isLoading: Boolean = false,
    val totalSymptoms: Int = 0,
    val totalFoodEntries: Int = 0,
    val statisticallySignificantCount: Int = 0,
    val highConfidenceCount: Int = 0,
    val totalPatternsCount: Int = 0,
    val timeRangeDescription: String = "Last 30 days",
    val lastUpdated: Long? = null,
    val errorMessage: String? = null
) {
    val hasData: Boolean
        get() = totalSymptoms > 0 || totalFoodEntries > 0

    val hasSignificantPatterns: Boolean
        get() = statisticallySignificantCount > 0

    val significanceRate: Float
        get() = if (totalPatternsCount > 0) {
            statisticallySignificantCount.toFloat() / totalPatternsCount
        } else 0f

    val lastUpdatedFormatted: String?
        get() = lastUpdated?.let {
            val formatter = DateTimeFormatter.ofPattern("MMM dd, HH:mm")
            java.time.Instant.ofEpochMilli(it)
                .atZone(java.time.ZoneId.systemDefault())
                .format(formatter)
        }
}

enum class TimeRange(val description: String) {
    LAST_7_DAYS("Last 7 days"),
    LAST_30_DAYS("Last 30 days"),
    LAST_3_MONTHS("Last 3 months"),
    LAST_6_MONTHS("Last 6 months"),
    ALL_TIME("All time");

    fun getDateRange(): Pair<LocalDate, LocalDate> {
        val endDate = LocalDate.now()
        val startDate = when (this) {
            LAST_7_DAYS -> endDate.minusDays(7)
            LAST_30_DAYS -> endDate.minusDays(30)
            LAST_3_MONTHS -> endDate.minusMonths(3)
            LAST_6_MONTHS -> endDate.minusMonths(6)
            ALL_TIME -> LocalDate.of(2000, 1, 1) // Far back start date
        }
        return Pair(startDate, endDate)
    }
}

data class PatternStatistics(
    val significantCount: Int,
    val highConfidenceCount: Int,
    val totalCount: Int
)

data class TriggerFoodSummary(
    val foodName: String,
    val symptomTypes: List<SymptomType>,
    val averageCorrelation: Float,
    val totalOccurrences: Int,
    val averageTimeToOnset: Int // minutes
) {
    val riskLevel: String
        get() = when {
            averageCorrelation >= 0.8f -> "High Risk"
            averageCorrelation >= 0.6f -> "Moderate Risk"
            else -> "Low Risk"
        }

    val timeToOnsetFormatted: String
        get() = when {
            averageTimeToOnset < 60 -> "${averageTimeToOnset}m"
            averageTimeToOnset % 60 == 0 -> "${averageTimeToOnset / 60}h"
            else -> "${averageTimeToOnset / 60}h ${averageTimeToOnset % 60}m"
        }
}

data class SymptomTriggerSummary(
    val symptomType: SymptomType,
    val triggerFoods: List<String>,
    val averageCorrelation: Float,
    val totalOccurrences: Int,
    val averageSeverity: Float
) {
    val severityLevel: String
        get() = when {
            averageSeverity >= 7f -> "Severe"
            averageSeverity >= 5f -> "Moderate"
            averageSeverity >= 3f -> "Mild"
            else -> "Very Mild"
        }
}

data class SymptomFrequencyData(
    val symptomType: SymptomType,
    val count: Int,
    val percentage: Float
)