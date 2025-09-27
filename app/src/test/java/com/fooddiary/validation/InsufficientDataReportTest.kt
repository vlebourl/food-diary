package com.fooddiary.validation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fooddiary.data.repository.ReportsRepository
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.data.repository.SymptomRepository
import com.fooddiary.data.repository.CorrelationRepository
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.Symptom
import com.fooddiary.data.models.*
import com.fooddiary.presentation.ui.reports.ReportsViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.*
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
 * T058: Test report generation with insufficient data handling
 * Tests graceful degradation per FR-006 when data is minimal
 */
@ExperimentalCoroutinesApi
class InsufficientDataReportTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var reportsRepository: ReportsRepository

    @MockK
    private lateinit var foodEntryRepository: FoodEntryRepository

    @MockK
    private lateinit var symptomRepository: SymptomRepository

    @MockK
    private lateinit var correlationRepository: CorrelationRepository

    private lateinit var reportsViewModel: ReportsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        // Mock minimal data by default
        every { foodEntryRepository.getEntriesInDateRange(any(), any()) } returns flowOf(emptyList())
        every { symptomRepository.getEntriesInDateRange(any(), any()) } returns flowOf(emptyList())
        coEvery { correlationRepository.generateCorrelations(any(), any()) } returns emptyList()

        reportsViewModel = ReportsViewModel(
            reportsRepository,
            foodEntryRepository,
            symptomRepository,
            correlationRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `should generate report with placeholder messages when no data exists`() = runTest {
        // Given - completely empty database
        every { foodEntryRepository.getEntriesInDateRange(any(), any()) } returns flowOf(emptyList())
        every { symptomRepository.getEntriesInDateRange(any(), any()) } returns flowOf(emptyList())
        coEvery { reportsRepository.generateReport(any(), any()) } returns Report(
            id = "empty-report",
            dateRange = DateRange(LocalDate.now().minusDays(7), LocalDate.now()),
            summary = ReportSummary(
                totalFoodEntries = 0,
                totalSymptoms = 0,
                correlationsFound = 0,
                dataQuality = DataQuality.INSUFFICIENT
            ),
            sections = listOf(
                ReportSection.FoodSummary(
                    content = "No food entries recorded during this period. Start logging meals to see dietary patterns.",
                    hasData = false
                ),
                ReportSection.SymptomAnalysis(
                    content = "No symptoms recorded during this period. Log symptoms to identify potential triggers.",
                    hasData = false
                ),
                ReportSection.Correlations(
                    content = "Insufficient data for correlation analysis. Need at least 3 food entries and 3 symptoms.",
                    hasData = false
                )
            )
        )

        // When - generating report with no data
        reportsViewModel.generateReport(LocalDate.now().minusDays(7), LocalDate.now())
        advanceUntilIdle()

        // Then
        val state = reportsViewModel.uiState.value
        assertNotNull("Should generate report even with no data", state.currentReport)
        assertEquals("Should indicate insufficient data", DataQuality.INSUFFICIENT, state.currentReport!!.summary.dataQuality)

        val foodSection = state.currentReport!!.sections.find { it is ReportSection.FoodSummary } as ReportSection.FoodSummary
        assertFalse("Food section should indicate no data", foodSection.hasData)
        assertTrue("Should show placeholder message for food section",
            foodSection.content.contains("No food entries recorded"))

        val symptomSection = state.currentReport!!.sections.find { it is ReportSection.SymptomAnalysis } as ReportSection.SymptomAnalysis
        assertFalse("Symptom section should indicate no data", symptomSection.hasData)
        assertTrue("Should show placeholder message for symptoms",
            symptomSection.content.contains("No symptoms recorded"))
    }

    @Test
    fun `should handle minimal data scenario with few entries`() = runTest {
        // Given - minimal data (1 food entry, 1 symptom)
        val singleFoodEntry = createTestFoodEntry(foods = listOf("Apple"))
        val singleSymptom = createTestSymptom(type = SymptomType.BLOATING)

        every { foodEntryRepository.getEntriesInDateRange(any(), any()) } returns flowOf(listOf(singleFoodEntry))
        every { symptomRepository.getEntriesInDateRange(any(), any()) } returns flowOf(listOf(singleSymptom))
        coEvery { reportsRepository.generateReport(any(), any()) } returns Report(
            id = "minimal-report",
            dateRange = DateRange(LocalDate.now().minusDays(7), LocalDate.now()),
            summary = ReportSummary(
                totalFoodEntries = 1,
                totalSymptoms = 1,
                correlationsFound = 0,
                dataQuality = DataQuality.MINIMAL
            ),
            sections = listOf(
                ReportSection.FoodSummary(
                    content = "Only 1 food entry recorded. More entries needed for pattern analysis.",
                    hasData = true,
                    entries = listOf(singleFoodEntry)
                ),
                ReportSection.SymptomAnalysis(
                    content = "Only 1 symptom recorded. More symptoms needed for trend analysis.",
                    hasData = true,
                    symptoms = listOf(singleSymptom)
                ),
                ReportSection.Correlations(
                    content = "Insufficient data for reliable correlation analysis. Minimum 3 entries of each type needed.",
                    hasData = false
                )
            )
        )

        // When - generating report with minimal data
        reportsViewModel.generateReport(LocalDate.now().minusDays(7), LocalDate.now())
        advanceUntilIdle()

        // Then
        val state = reportsViewModel.uiState.value
        assertNotNull("Should generate report with minimal data", state.currentReport)
        assertEquals("Should indicate minimal data quality", DataQuality.MINIMAL, state.currentReport!!.summary.dataQuality)
        assertEquals("Should show correct entry count", 1, state.currentReport!!.summary.totalFoodEntries)
        assertEquals("Should show correct symptom count", 1, state.currentReport!!.summary.totalSymptoms)
        assertEquals("Should show no correlations", 0, state.currentReport!!.summary.correlationsFound)
    }

    @Test
    fun `should provide helpful guidance messages for insufficient data`() = runTest {
        // Given - various insufficient data scenarios
        coEvery { reportsRepository.generateReport(any(), any()) } returns Report(
            id = "guidance-report",
            dateRange = DateRange(LocalDate.now().minusDays(7), LocalDate.now()),
            summary = ReportSummary(
                totalFoodEntries = 2,
                totalSymptoms = 0,
                correlationsFound = 0,
                dataQuality = DataQuality.INSUFFICIENT
            ),
            guidance = ReportGuidance(
                recommendations = listOf(
                    "Start logging symptoms to identify potential food triggers",
                    "Aim for at least 1 week of consistent food logging",
                    "Record portion sizes for more accurate analysis"
                ),
                minimumDataNeeded = MinimumDataRequirement(
                    foodEntries = 7,
                    symptoms = 3,
                    timeRange = 7 // days
                ),
                currentProgress = DataProgress(
                    foodEntriesProgress = 2.0f / 7.0f, // 2 out of 7 needed
                    symptomsProgress = 0.0f / 3.0f, // 0 out of 3 needed
                    timeRangeProgress = 1.0f // 7 days covered
                )
            )
        )

        // When - generating report with guidance
        reportsViewModel.generateReport(LocalDate.now().minusDays(7), LocalDate.now())
        advanceUntilIdle()

        // Then
        val state = reportsViewModel.uiState.value
        assertNotNull("Should provide guidance", state.currentReport!!.guidance)
        assertEquals("Should show 3 recommendations", 3, state.currentReport!!.guidance!!.recommendations.size)
        assertTrue("Should suggest symptom logging",
            state.currentReport!!.guidance!!.recommendations.any { it.contains("logging symptoms") })

        val progress = state.currentReport!!.guidance!!.currentProgress
        assertEquals("Should show food entries progress", 2.0f / 7.0f, progress.foodEntriesProgress, 0.01f)
        assertEquals("Should show symptoms progress", 0.0f, progress.symptomsProgress, 0.01f)
    }

    @Test
    fun `should handle mixed data availability scenarios`() = runTest {
        // Given - good food data but no symptoms
        val foodEntries = listOf(
            createTestFoodEntry(foods = listOf("Breakfast")),
            createTestFoodEntry(foods = listOf("Lunch")),
            createTestFoodEntry(foods = listOf("Dinner"))
        )

        every { foodEntryRepository.getEntriesInDateRange(any(), any()) } returns flowOf(foodEntries)
        every { symptomRepository.getEntriesInDateRange(any(), any()) } returns flowOf(emptyList())
        coEvery { reportsRepository.generateReport(any(), any()) } returns Report(
            id = "mixed-report",
            dateRange = DateRange(LocalDate.now().minusDays(7), LocalDate.now()),
            summary = ReportSummary(
                totalFoodEntries = 3,
                totalSymptoms = 0,
                correlationsFound = 0,
                dataQuality = DataQuality.PARTIAL
            ),
            sections = listOf(
                ReportSection.FoodSummary(
                    content = "Good food logging data available. Shows consistent meal patterns.",
                    hasData = true,
                    entries = foodEntries
                ),
                ReportSection.SymptomAnalysis(
                    content = "No symptoms recorded. Start logging symptoms to identify potential food triggers.",
                    hasData = false
                ),
                ReportSection.Correlations(
                    content = "Cannot analyze food-symptom correlations without symptom data.",
                    hasData = false
                )
            )
        )

        // When - generating report with mixed data
        reportsViewModel.generateReport(LocalDate.now().minusDays(7), LocalDate.now())
        advanceUntilIdle()

        // Then
        val state = reportsViewModel.uiState.value
        assertEquals("Should indicate partial data quality", DataQuality.PARTIAL, state.currentReport!!.summary.dataQuality)

        val foodSection = state.currentReport!!.sections[0] as ReportSection.FoodSummary
        assertTrue("Food section should have data", foodSection.hasData)
        assertEquals("Should show all food entries", 3, foodSection.entries.size)

        val symptomSection = state.currentReport!!.sections[1] as ReportSection.SymptomAnalysis
        assertFalse("Symptom section should have no data", symptomSection.hasData)
        assertTrue("Should suggest symptom logging",
            symptomSection.content.contains("Start logging symptoms"))
    }

    @Test
    fun `should show data requirements for meaningful analysis`() = runTest {
        // Given - report generation with data requirements
        coEvery { reportsRepository.getDataRequirements() } returns DataRequirements(
            minimumFoodEntries = 7,
            minimumSymptoms = 3,
            minimumTimeRangeDays = 7,
            recommendedFoodEntries = 21,
            recommendedSymptoms = 10,
            recommendedTimeRangeDays = 30
        )

        // When - checking data requirements
        reportsViewModel.checkDataRequirements()
        advanceUntilIdle()

        // Then
        val state = reportsViewModel.uiState.value
        assertNotNull("Should show data requirements", state.dataRequirements)
        assertEquals("Should show minimum food entries", 7, state.dataRequirements!!.minimumFoodEntries)
        assertEquals("Should show minimum symptoms", 3, state.dataRequirements!!.minimumSymptoms)
        assertEquals("Should show recommended entries", 21, state.dataRequirements!!.recommendedFoodEntries)
    }

    @Test
    fun `should handle short time period reports gracefully`() = runTest {
        // Given - report for very short period (1 day)
        val startDate = LocalDate.now()
        val endDate = LocalDate.now()

        coEvery { reportsRepository.generateReport(startDate, endDate) } returns Report(
            id = "short-period-report",
            dateRange = DateRange(startDate, endDate),
            summary = ReportSummary(
                totalFoodEntries = 2,
                totalSymptoms = 1,
                correlationsFound = 0,
                dataQuality = DataQuality.INSUFFICIENT
            ),
            timeRangeWarning = "Single day reports have limited analytical value. Consider extending to at least 1 week.",
            sections = listOf(
                ReportSection.FoodSummary(
                    content = "2 food entries for today. Daily snapshots don't show patterns.",
                    hasData = true
                ),
                ReportSection.SymptomAnalysis(
                    content = "1 symptom recorded today. Need longer period for trend analysis.",
                    hasData = true
                ),
                ReportSection.Correlations(
                    content = "Single day insufficient for correlation analysis.",
                    hasData = false
                )
            )
        )

        // When - generating short period report
        reportsViewModel.generateReport(startDate, endDate)
        advanceUntilIdle()

        // Then
        val state = reportsViewModel.uiState.value
        assertNotNull("Should show time range warning", state.currentReport!!.timeRangeWarning)
        assertTrue("Should warn about single day limitation",
            state.currentReport!!.timeRangeWarning!!.contains("Single day reports"))
    }

    @Test
    fun `should provide export option even with insufficient data`() = runTest {
        // Given - report with minimal data
        val minimalReport = Report(
            id = "minimal-export",
            dateRange = DateRange(LocalDate.now().minusDays(7), LocalDate.now()),
            summary = ReportSummary(
                totalFoodEntries = 1,
                totalSymptoms = 1,
                correlationsFound = 0,
                dataQuality = DataQuality.MINIMAL
            )
        )

        coEvery { reportsRepository.generateReport(any(), any()) } returns minimalReport
        coEvery { reportsRepository.exportReport(any(), any()) } returns ExportResult.Success(
            filePath = "/tmp/minimal_report.pdf",
            includesDisclaimer = true,
            disclaimerText = "This report contains minimal data and may not provide meaningful insights."
        )

        // When - exporting minimal data report
        reportsViewModel.generateReport(LocalDate.now().minusDays(7), LocalDate.now())
        advanceUntilIdle()

        val exportResult = reportsViewModel.exportReport(ExportFormat.PDF)
        advanceUntilIdle()

        // Then
        assertTrue("Should allow export even with minimal data", exportResult is ExportResult.Success)
        val successResult = exportResult as ExportResult.Success
        assertTrue("Should include disclaimer for minimal data", successResult.includesDisclaimer)
        assertTrue("Should warn about limited insights",
            successResult.disclaimerText.contains("minimal data"))
    }

    @Test
    fun `should suggest data collection strategies for better reports`() = runTest {
        // Given - insufficient data with improvement suggestions
        coEvery { reportsRepository.generateReport(any(), any()) } returns Report(
            id = "improvement-suggestions",
            dateRange = DateRange(LocalDate.now().minusDays(7), LocalDate.now()),
            summary = ReportSummary(
                totalFoodEntries = 2,
                totalSymptoms = 0,
                correlationsFound = 0,
                dataQuality = DataQuality.INSUFFICIENT
            ),
            improvementSuggestions = ImprovementSuggestions(
                dataCollection = listOf(
                    "Log meals immediately after eating for better accuracy",
                    "Record portion sizes to improve analysis quality",
                    "Note symptoms within 2-4 hours of meals for better correlation"
                ),
                consistency = listOf(
                    "Aim for daily food logging for at least 2 weeks",
                    "Track symptoms even when mild (severity 1-2)"
                ),
                accuracy = listOf(
                    "Include ingredient details for prepared foods",
                    "Note cooking methods (fried, grilled, raw)"
                )
            )
        )

        // When - generating report with improvement suggestions
        reportsViewModel.generateReport(LocalDate.now().minusDays(7), LocalDate.now())
        advanceUntilIdle()

        // Then
        val state = reportsViewModel.uiState.value
        assertNotNull("Should provide improvement suggestions", state.currentReport!!.improvementSuggestions)

        val suggestions = state.currentReport!!.improvementSuggestions!!
        assertFalse("Should have data collection suggestions", suggestions.dataCollection.isEmpty())
        assertFalse("Should have consistency suggestions", suggestions.consistency.isEmpty())
        assertFalse("Should have accuracy suggestions", suggestions.accuracy.isEmpty())

        assertTrue("Should suggest immediate logging",
            suggestions.dataCollection.any { it.contains("immediately after eating") })
        assertTrue("Should suggest daily logging",
            suggestions.consistency.any { it.contains("daily food logging") })
    }

    // Helper methods
    private fun createTestFoodEntry(
        id: String = "test-entry",
        timestamp: Instant = Instant.now(),
        foods: List<String> = listOf("Test Food"),
        mealType: MealType = MealType.BREAKFAST
    ) = FoodEntry(
        id = id,
        timestamp = timestamp,
        mealType = mealType,
        foods = foods,
        portions = listOf("1 serving"),
        notes = null,
        isDeleted = false
    )

    private fun createTestSymptom(
        id: String = "test-symptom",
        timestamp: Instant = Instant.now(),
        type: SymptomType = SymptomType.BLOATING,
        severity: Int = 5
    ) = Symptom(
        id = id,
        timestamp = timestamp,
        type = type,
        severity = severity,
        duration = 30,
        triggerFoodId = null,
        notes = null,
        isDeleted = false
    )
}

// Supporting data classes for reports
data class Report(
    val id: String,
    val dateRange: DateRange,
    val summary: ReportSummary,
    val sections: List<ReportSection> = emptyList(),
    val guidance: ReportGuidance? = null,
    val timeRangeWarning: String? = null,
    val improvementSuggestions: ImprovementSuggestions? = null
)

data class ReportSummary(
    val totalFoodEntries: Int,
    val totalSymptoms: Int,
    val correlationsFound: Int,
    val dataQuality: DataQuality
)

sealed class ReportSection {
    data class FoodSummary(
        val content: String,
        val hasData: Boolean,
        val entries: List<FoodEntry> = emptyList()
    ) : ReportSection()

    data class SymptomAnalysis(
        val content: String,
        val hasData: Boolean,
        val symptoms: List<Symptom> = emptyList()
    ) : ReportSection()

    data class Correlations(
        val content: String,
        val hasData: Boolean,
        val correlations: List<String> = emptyList()
    ) : ReportSection()
}

data class ReportGuidance(
    val recommendations: List<String>,
    val minimumDataNeeded: MinimumDataRequirement,
    val currentProgress: DataProgress
)

data class MinimumDataRequirement(
    val foodEntries: Int,
    val symptoms: Int,
    val timeRange: Int // days
)

data class DataProgress(
    val foodEntriesProgress: Float,
    val symptomsProgress: Float,
    val timeRangeProgress: Float
)

data class DataRequirements(
    val minimumFoodEntries: Int,
    val minimumSymptoms: Int,
    val minimumTimeRangeDays: Int,
    val recommendedFoodEntries: Int,
    val recommendedSymptoms: Int,
    val recommendedTimeRangeDays: Int
)

data class ImprovementSuggestions(
    val dataCollection: List<String>,
    val consistency: List<String>,
    val accuracy: List<String>
)

data class DateRange(
    val startDate: LocalDate,
    val endDate: LocalDate
)

enum class DataQuality {
    INSUFFICIENT, MINIMAL, PARTIAL, GOOD, EXCELLENT
}

sealed class ExportResult {
    data class Success(
        val filePath: String,
        val includesDisclaimer: Boolean,
        val disclaimerText: String
    ) : ExportResult()

    data class Error(val message: String) : ExportResult()
}

enum class ExportFormat {
    PDF, CSV, JSON
}