package com.fooddiary.presentation.ui.analytics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.data.repository.SymptomEventRepository
import com.fooddiary.data.repository.CorrelationRepository
import com.fooddiary.data.repository.TriggerPatternRepository
import com.fooddiary.data.repository.EnvironmentalContextRepository
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.TriggerPattern
import com.fooddiary.data.models.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * Comprehensive TDD unit tests for AnalyticsViewModel
 * Tests chart data processing, statistics calculation, insights generation, and correlation analysis
 *
 * THESE TESTS WILL FAIL initially because AnalyticsViewModel doesn't exist yet (TDD approach)
 */
@ExperimentalCoroutinesApi
class AnalyticsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var foodEntryRepository: FoodEntryRepository

    @MockK
    private lateinit var symptomEventRepository: SymptomEventRepository

    @MockK
    private lateinit var correlationRepository: CorrelationRepository

    @MockK
    private lateinit var triggerPatternRepository: TriggerPatternRepository

    @MockK
    private lateinit var environmentalContextRepository: EnvironmentalContextRepository

    private lateinit var analyticsViewModel: AnalyticsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        // Setup default mock behaviors
        every { foodEntryRepository.getAllByDateRange(any(), any()) } returns flowOf(emptyList())
        every { symptomEventRepository.getAllByDateRange(any(), any()) } returns flowOf(emptyList())
        coEvery { correlationRepository.calculateCorrelations(any(), any()) } returns emptyList()
        every { triggerPatternRepository.getHighConfidence() } returns flowOf(emptyList())
        every { environmentalContextRepository.getByDateRange(any(), any()) } returns flowOf(emptyList())

        analyticsViewModel = AnalyticsViewModel(
            foodEntryRepository,
            symptomEventRepository,
            correlationRepository,
            triggerPatternRepository,
            environmentalContextRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `initial state should have default time range and loading status`() {
        // Given - fresh ViewModel instance

        // When - accessing initial state
        val initialState = analyticsViewModel.uiState.value

        // Then
        assertEquals("Default time range should be 30 days", TimeRange.LAST_30_DAYS, initialState.selectedTimeRange)
        assertTrue("Should be loading initially", initialState.isLoading)
        assertTrue("Chart data should be empty initially", initialState.chartData.isEmpty())
        assertTrue("Statistics should be empty initially", initialState.statistics.isEmpty())
        assertTrue("Insights should be empty initially", initialState.insights.isEmpty())
        assertEquals("Error should be null initially", null, initialState.error)
    }

    @Test
    fun `loadAnalytics should process data and update all analytics sections`() = runTest {
        // Given
        val timeRange = TimeRange.LAST_7_DAYS
        val startDate = LocalDate.now().minusDays(7)
        val endDate = LocalDate.now()

        val mockFoodEntries = listOf(
            createMockFoodEntry("apple", startDate, MealType.BREAKFAST),
            createMockFoodEntry("bread", startDate.plusDays(1), MealType.LUNCH),
            createMockFoodEntry("milk", startDate.plusDays(2), MealType.DINNER)
        )

        val mockSymptomEvents = listOf(
            createMockSymptomEvent(SymptomType.BLOATING, startDate.plusDays(1), 6),
            createMockSymptomEvent(SymptomType.PAIN, startDate.plusDays(2), 4)
        )

        val mockCorrelations = listOf(
            createMockSymptomFoodCorrelation(SymptomType.BLOATING, "bread", 0.8f),
            createMockSymptomFoodCorrelation(SymptomType.PAIN, "milk", 0.7f)
        )

        every { foodEntryRepository.getAllByDateRange(startDate, endDate) } returns flowOf(mockFoodEntries)
        every { symptomEventRepository.getAllByDateRange(startDate, endDate) } returns flowOf(mockSymptomEvents)
        coEvery { correlationRepository.calculateCorrelations(startDate, endDate) } returns mockCorrelations

        // When
        analyticsViewModel.loadAnalytics(timeRange)
        advanceUntilIdle()

        // Then
        val finalState = analyticsViewModel.uiState.value
        assertFalse("Should not be loading", finalState.isLoading)
        assertEquals("Time range should be updated", timeRange, finalState.selectedTimeRange)
        assertTrue("Should have chart data", finalState.chartData.isNotEmpty())
        assertTrue("Should have statistics", finalState.statistics.isNotEmpty())
        assertEquals("Error should be null", null, finalState.error)
    }

    @Test
    fun `loadAnalytics should handle repository errors gracefully`() = runTest {
        // Given
        val timeRange = TimeRange.LAST_30_DAYS
        val testException = RuntimeException("Analytics processing error")

        every { foodEntryRepository.getAllByDateRange(any(), any()) } throws testException

        // When
        analyticsViewModel.loadAnalytics(timeRange)
        advanceUntilIdle()

        // Then
        val finalState = analyticsViewModel.uiState.value
        assertFalse("Loading should be complete", finalState.isLoading)
        assertTrue("Chart data should be empty on error", finalState.chartData.isEmpty())
        assertEquals("Error should be set", "Analytics processing error", finalState.error)
    }

    @Test
    fun `changeTimeRange should reload analytics with new date range`() = runTest {
        // Given - initial data loaded
        val initialTimeRange = TimeRange.LAST_7_DAYS
        val newTimeRange = TimeRange.LAST_30_DAYS

        // Setup mocks for different time ranges
        every { foodEntryRepository.getAllByDateRange(any(), any()) } returns flowOf(emptyList())
        every { symptomEventRepository.getAllByDateRange(any(), any()) } returns flowOf(emptyList())
        coEvery { correlationRepository.calculateCorrelations(any(), any()) } returns emptyList()

        analyticsViewModel.loadAnalytics(initialTimeRange)
        advanceUntilIdle()

        // When
        analyticsViewModel.changeTimeRange(newTimeRange)
        advanceUntilIdle()

        // Then
        val finalState = analyticsViewModel.uiState.value
        assertEquals("Time range should be updated", newTimeRange, finalState.selectedTimeRange)
        assertFalse("Should not be loading", finalState.isLoading)
    }

    @Test
    fun `refreshInsights should regenerate insights based on current data`() = runTest {
        // Given - analytics data loaded
        val mockTriggerPatterns = listOf(
            createMockTriggerPattern("garlic", SymptomType.BLOATING, 0.9f),
            createMockTriggerPattern("dairy", SymptomType.CRAMPING, 0.8f)
        )

        every { triggerPatternRepository.getHighConfidence() } returns flowOf(mockTriggerPatterns)

        // When
        analyticsViewModel.refreshInsights()
        advanceUntilIdle()

        // Then
        val finalState = analyticsViewModel.uiState.value
        assertTrue("Should have insights generated", finalState.insights.isNotEmpty())
        assertTrue("Should include trigger pattern insights",
            finalState.insights.any { it.type == InsightType.TRIGGER_PATTERN })
    }

    @Test
    fun `exportData should prepare data for export in specified format`() = runTest {
        // Given - analytics data available
        val mockStatistics = listOf(
            createMockStatistic("Total Food Entries", "45"),
            createMockStatistic("Total Symptoms", "12"),
            createMockStatistic("Average Symptom Severity", "4.2")
        )

        analyticsViewModel.loadAnalytics(TimeRange.LAST_30_DAYS)
        advanceUntilIdle()

        // When
        val exportResult = analyticsViewModel.exportData(ExportFormat.CSV)
        advanceUntilIdle()

        // Then
        assertNotNull("Export result should not be null", exportResult)
        assertTrue("Export should be successful", exportResult)

        val finalState = analyticsViewModel.uiState.value
        assertEquals("Should show export success message", "Data exported successfully", finalState.message)
    }

    @Test
    fun `getCorrelations should return correlation data for specific symptom type`() = runTest {
        // Given
        val targetSymptom = SymptomType.BLOATING
        val mockCorrelations = listOf(
            createMockSymptomFoodCorrelation(targetSymptom, "onion", 0.9f),
            createMockSymptomFoodCorrelation(targetSymptom, "wheat", 0.7f),
            createMockSymptomFoodCorrelation(targetSymptom, "apple", 0.3f)
        )

        every { correlationRepository.getCorrelationsForSymptom(any()) } returns flowOf(mockCorrelations)

        // When
        val correlations = analyticsViewModel.getCorrelations(targetSymptom)
        advanceUntilIdle()

        // Then
        assertEquals("Should return correlations for symptom", 3, correlations.size)
        assertEquals("Should order by correlation strength", "onion", correlations[0].foodName)
        assertTrue("Should filter significant correlations only",
            correlations.all { it.correlationStrength >= 0.3f })
    }

    @Test
    fun `generateChartData should create appropriate chart data for different chart types`() = runTest {
        // Given
        val mockSymptomEvents = listOf(
            createMockSymptomEvent(SymptomType.BLOATING, LocalDate.now().minusDays(6), 7),
            createMockSymptomEvent(SymptomType.BLOATING, LocalDate.now().minusDays(4), 5),
            createMockSymptomEvent(SymptomType.PAIN, LocalDate.now().minusDays(3), 6),
            createMockSymptomEvent(SymptomType.BLOATING, LocalDate.now().minusDays(1), 8)
        )

        every { symptomEventRepository.getAllByDateRange(any(), any()) } returns flowOf(mockSymptomEvents)

        // When
        analyticsViewModel.loadAnalytics(TimeRange.LAST_7_DAYS)
        advanceUntilIdle()

        // Then
        val finalState = analyticsViewModel.uiState.value
        val chartData = finalState.chartData

        assertTrue("Should have symptom frequency chart",
            chartData.any { it.type == ChartType.SYMPTOM_FREQUENCY })
        assertTrue("Should have severity trend chart",
            chartData.any { it.type == ChartType.SEVERITY_TREND })
        assertTrue("Chart data should have proper data points",
            chartData.all { it.dataPoints.isNotEmpty() })
    }

    @Test
    fun `calculateTrends should identify upward and downward trends`() = runTest {
        // Given - data with clear trends
        val symptomEventsWithTrend = listOf(
            createMockSymptomEvent(SymptomType.BLOATING, LocalDate.now().minusDays(10), 3),
            createMockSymptomEvent(SymptomType.BLOATING, LocalDate.now().minusDays(8), 4),
            createMockSymptomEvent(SymptomType.BLOATING, LocalDate.now().minusDays(6), 6),
            createMockSymptomEvent(SymptomType.BLOATING, LocalDate.now().minusDays(4), 7),
            createMockSymptomEvent(SymptomType.BLOATING, LocalDate.now().minusDays(2), 8)
        )

        every { symptomEventRepository.getAllByDateRange(any(), any()) } returns flowOf(symptomEventsWithTrend)

        // When
        analyticsViewModel.loadAnalytics(TimeRange.LAST_14_DAYS)
        advanceUntilIdle()

        // Then
        val finalState = analyticsViewModel.uiState.value
        assertTrue("Should identify upward trend in symptoms",
            finalState.insights.any {
                it.type == InsightType.TREND_ANALYSIS &&
                it.description.contains("increasing")
            })
    }

    @Test
    fun `getMostProblematicFoods should return foods with highest correlation scores`() = runTest {
        // Given
        val mockCorrelations = listOf(
            createMockSymptomFoodCorrelation(SymptomType.BLOATING, "garlic", 0.95f),
            createMockSymptomFoodCorrelation(SymptomType.PAIN, "dairy", 0.88f),
            createMockSymptomFoodCorrelation(SymptomType.CRAMPING, "wheat", 0.75f),
            createMockSymptomFoodCorrelation(SymptomType.NAUSEA, "apple", 0.42f)
        )

        coEvery { correlationRepository.calculateCorrelations(any(), any()) } returns mockCorrelations

        // When
        analyticsViewModel.loadAnalytics(TimeRange.LAST_30_DAYS)
        advanceUntilIdle()

        val problematicFoods = analyticsViewModel.getMostProblematicFoods(3)

        // Then
        assertEquals("Should return top 3 problematic foods", 3, problematicFoods.size)
        assertEquals("First should be garlic", "garlic", problematicFoods[0].foodName)
        assertEquals("Second should be dairy", "dairy", problematicFoods[1].foodName)
        assertEquals("Third should be wheat", "wheat", problematicFoods[2].foodName)
    }

    @Test
    fun `getSymptomPatterns should identify temporal patterns in symptoms`() = runTest {
        // Given - symptoms with temporal patterns (morning symptoms)
        val morningSymptoms = listOf(
            createMockSymptomEventWithTime(SymptomType.NAUSEA, LocalDate.now().minusDays(5), 8, 0), // 8 AM
            createMockSymptomEventWithTime(SymptomType.NAUSEA, LocalDate.now().minusDays(3), 7, 30), // 7:30 AM
            createMockSymptomEventWithTime(SymptomType.NAUSEA, LocalDate.now().minusDays(1), 8, 15) // 8:15 AM
        )

        every { symptomEventRepository.getAllByDateRange(any(), any()) } returns flowOf(morningSymptoms)

        // When
        analyticsViewModel.loadAnalytics(TimeRange.LAST_7_DAYS)
        advanceUntilIdle()

        // Then
        val finalState = analyticsViewModel.uiState.value
        assertTrue("Should identify time-of-day patterns",
            finalState.insights.any {
                it.type == InsightType.TEMPORAL_PATTERN &&
                it.description.contains("morning")
            })
    }

    @Test
    fun `filterAnalyticsBySymptom should filter data to specific symptom type`() = runTest {
        // Given - mixed symptom data
        val mixedSymptoms = listOf(
            createMockSymptomEvent(SymptomType.BLOATING, LocalDate.now().minusDays(3), 6),
            createMockSymptomEvent(SymptomType.PAIN, LocalDate.now().minusDays(2), 7),
            createMockSymptomEvent(SymptomType.BLOATING, LocalDate.now().minusDays(1), 5)
        )

        every { symptomEventRepository.getAllByDateRange(any(), any()) } returns flowOf(mixedSymptoms)

        analyticsViewModel.loadAnalytics(TimeRange.LAST_7_DAYS)
        advanceUntilIdle()

        // When
        analyticsViewModel.filterAnalyticsBySymptom(SymptomType.BLOATING)
        advanceUntilIdle()

        // Then
        val finalState = analyticsViewModel.uiState.value
        assertEquals("Should filter to bloating only", SymptomType.BLOATING, finalState.selectedSymptomFilter)
        assertTrue("Chart data should reflect filter",
            finalState.chartData.all { chart ->
                chart.dataPoints.all { point ->
                    point.label?.contains("BLOATING", ignoreCase = true) != false
                }
            })
    }

    @Test
    fun `clearFilters should reset all applied filters and reload full data`() = runTest {
        // Given - filtered data
        analyticsViewModel.loadAnalytics(TimeRange.LAST_7_DAYS)
        analyticsViewModel.filterAnalyticsBySymptom(SymptomType.BLOATING)
        advanceUntilIdle()

        // When
        analyticsViewModel.clearFilters()
        advanceUntilIdle()

        // Then
        val finalState = analyticsViewModel.uiState.value
        assertEquals("Symptom filter should be cleared", null, finalState.selectedSymptomFilter)
        assertTrue("Should reload full dataset", finalState.chartData.isNotEmpty())
    }

    // Helper methods for creating mock data
    private fun createMockFoodEntry(
        name: String,
        date: LocalDate,
        mealType: MealType
    ): FoodEntry {
        return FoodEntry(
            id = "food-$name-$date",
            timestamp = date.atStartOfDay().toInstant(java.time.ZoneOffset.UTC),
            mealType = mealType,
            foods = listOf(name),
            portions = listOf("1 serving"),
            notes = null,
            isDeleted = false
        )
    }

    private fun createMockSymptomEvent(
        type: SymptomType,
        date: LocalDate,
        severity: Int
    ): SymptomEvent {
        return SymptomEvent(
            id = "symptom-$type-$date",
            timestamp = date.atStartOfDay().toInstant(java.time.ZoneOffset.UTC),
            type = type,
            severity = severity,
            duration = null,
            triggerFoodId = null,
            notes = null,
            isDeleted = false
        )
    }

    private fun createMockSymptomEventWithTime(
        type: SymptomType,
        date: LocalDate,
        hour: Int,
        minute: Int
    ): SymptomEvent {
        return SymptomEvent(
            id = "symptom-$type-$date-$hour-$minute",
            timestamp = date.atTime(hour, minute).toInstant(java.time.ZoneOffset.UTC),
            type = type,
            severity = 5,
            duration = null,
            triggerFoodId = null,
            notes = null,
            isDeleted = false
        )
    }

    private fun createMockSymptomFoodCorrelation(
        symptomType: SymptomType,
        foodName: String,
        strength: Float
    ): SymptomFoodCorrelation {
        return SymptomFoodCorrelation(
            symptomType = symptomType,
            foodName = foodName,
            correlationStrength = strength,
            occurrences = (strength * 10).toInt(),
            confidence = if (strength > 0.7f) AnalysisConfidence.HIGH else AnalysisConfidence.MEDIUM,
            averageTimeDifference = 120,
            lastOccurrence = Instant.now()
        )
    }

    private fun createMockTriggerPattern(
        foodName: String,
        symptomType: SymptomType,
        confidence: Float
    ): TriggerPattern {
        return TriggerPattern(
            id = "pattern-$foodName-$symptomType",
            foodName = foodName,
            symptomType = symptomType,
            confidence = confidence,
            occurrences = (confidence * 20).toInt(),
            averageDelay = 120,
            lastUpdated = Instant.now()
        )
    }

    private fun createMockStatistic(name: String, value: String): AnalyticsStatistic {
        return AnalyticsStatistic(
            name = name,
            value = value,
            trend = null,
            changePercentage = null
        )
    }
}

// Data classes that AnalyticsViewModel should use (these will need to be implemented)
data class AnalyticsUiState(
    val isLoading: Boolean = false,
    val selectedTimeRange: TimeRange = TimeRange.LAST_30_DAYS,
    val selectedSymptomFilter: SymptomType? = null,
    val chartData: List<ChartData> = emptyList(),
    val statistics: List<AnalyticsStatistic> = emptyList(),
    val insights: List<AnalyticsInsight> = emptyList(),
    val error: String? = null,
    val message: String? = null
)

enum class TimeRange {
    LAST_7_DAYS, LAST_14_DAYS, LAST_30_DAYS, LAST_90_DAYS, CUSTOM
}

data class ChartData(
    val type: ChartType,
    val title: String,
    val dataPoints: List<DataPoint>,
    val xAxisLabel: String,
    val yAxisLabel: String
)

enum class ChartType {
    SYMPTOM_FREQUENCY, SEVERITY_TREND, CORRELATION_HEATMAP, TIME_OF_DAY_ANALYSIS
}

data class DataPoint(
    val x: Float,
    val y: Float,
    val label: String? = null,
    val color: String? = null
)

data class AnalyticsStatistic(
    val name: String,
    val value: String,
    val trend: Trend? = null,
    val changePercentage: Float? = null
)

enum class Trend {
    INCREASING, DECREASING, STABLE
}

data class AnalyticsInsight(
    val type: InsightType,
    val title: String,
    val description: String,
    val severity: InsightSeverity,
    val actionRecommendation: String? = null
)

enum class InsightType {
    TRIGGER_PATTERN, TREND_ANALYSIS, TEMPORAL_PATTERN, CORRELATION_DISCOVERY
}

enum class InsightSeverity {
    INFO, WARNING, CRITICAL
}

enum class ExportFormat {
    CSV, JSON, PDF
}