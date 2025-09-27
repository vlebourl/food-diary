package com.fooddiary.presentation.ui.reports

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fooddiary.data.repository.MedicalReportRepository
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.data.repository.SymptomEventRepository
import com.fooddiary.data.repository.CorrelationRepository
import com.fooddiary.data.database.entities.MedicalReport
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
import java.io.File

/**
 * Comprehensive TDD unit tests for ReportsViewModel
 * Tests report generation, export functionality, sharing capabilities, and report management
 *
 * THESE TESTS WILL FAIL initially because ReportsViewModel doesn't exist yet (TDD approach)
 */
@ExperimentalCoroutinesApi
class ReportsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var medicalReportRepository: MedicalReportRepository

    @MockK
    private lateinit var foodEntryRepository: FoodEntryRepository

    @MockK
    private lateinit var symptomEventRepository: SymptomEventRepository

    @MockK
    private lateinit var correlationRepository: CorrelationRepository

    private lateinit var reportsViewModel: ReportsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        // Setup default mock behaviors
        every { medicalReportRepository.getAll() } returns flowOf(emptyList())
        coEvery { medicalReportRepository.generateReport(any(), any(), any(), any()) } returns
            createMockMedicalReport("test-report", ReportFormat.PDF)
        coEvery { medicalReportRepository.exportReport(any()) } returns "/path/to/exported/report.pdf"

        reportsViewModel = ReportsViewModel(
            medicalReportRepository,
            foodEntryRepository,
            symptomEventRepository,
            correlationRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `initial state should have default values and loading report history`() = runTest {
        // Given - fresh ViewModel instance

        // When - accessing initial state
        val initialState = reportsViewModel.uiState.value

        // Then
        assertEquals("Start date should be 30 days ago", LocalDate.now().minusDays(30), initialState.startDate)
        assertEquals("End date should be today", LocalDate.now(), initialState.endDate)
        assertEquals("Default format should be PDF", ReportFormat.PDF, initialState.selectedFormat)
        assertTrue("Should include all sections by default", initialState.selectedSections.containsAll(
            listOf(ReportSection.SUMMARY, ReportSection.FOOD_LOG, ReportSection.SYMPTOMS, ReportSection.CORRELATIONS)
        ))
        assertTrue("Should be loading report history initially", initialState.isLoadingHistory)
        assertFalse("Should not be generating report initially", initialState.isGenerating)
        assertTrue("Report history should be empty initially", initialState.reportHistory.isEmpty())
    }

    @Test
    fun `generateReport should create report with selected parameters and update state`() = runTest {
        // Given
        val startDate = LocalDate.of(2024, 1, 1)
        val endDate = LocalDate.of(2024, 1, 31)
        val format = ReportFormat.PDF
        val sections = listOf(ReportSection.SUMMARY, ReportSection.FOOD_LOG)
        val generatedReport = createMockMedicalReport("generated-report", format)

        coEvery {
            medicalReportRepository.generateReport(startDate, endDate, format, sections)
        } returns generatedReport

        reportsViewModel.updateDateRange(startDate, endDate)
        reportsViewModel.updateSelectedFormat(format)
        reportsViewModel.updateSelectedSections(sections)

        // When
        val result = reportsViewModel.generateReport()
        advanceUntilIdle()

        // Then
        assertTrue("Report generation should be successful", result)
        coVerify(exactly = 1) {
            medicalReportRepository.generateReport(startDate, endDate, format, sections)
        }

        val finalState = reportsViewModel.uiState.value
        assertFalse("Should not be generating anymore", finalState.isGenerating)
        assertEquals("Should show success message", "Report generated successfully", finalState.message)
        assertNotNull("Current report should be set", finalState.currentReport)
        assertEquals("Current report ID should match", generatedReport.id, finalState.currentReport!!.id)
    }

    @Test
    fun `generateReport should handle generation errors gracefully`() = runTest {
        // Given
        val testException = RuntimeException("Report generation failed")
        coEvery {
            medicalReportRepository.generateReport(any(), any(), any(), any())
        } throws testException

        // When
        val result = reportsViewModel.generateReport()
        advanceUntilIdle()

        // Then
        assertFalse("Report generation should fail", result)

        val finalState = reportsViewModel.uiState.value
        assertFalse("Should not be generating", finalState.isGenerating)
        assertEquals("Should show error message", "Report generation failed", finalState.error)
        assertEquals("Current report should remain null", null, finalState.currentReport)
    }

    @Test
    fun `exportToFormat should export current report to specified format`() = runTest {
        // Given - current report available
        val currentReport = createMockMedicalReport("export-report", ReportFormat.PDF)
        val expectedFilePath = "/storage/reports/export-report.pdf"

        coEvery { medicalReportRepository.getById("export-report") } returns currentReport
        coEvery { medicalReportRepository.exportReport("export-report") } returns expectedFilePath

        // Set up current report in state
        reportsViewModel.generateReport()
        advanceUntilIdle()

        // When
        val exportPath = reportsViewModel.exportToFormat(ReportFormat.PDF)
        advanceUntilIdle()

        // Then
        assertEquals("Should return export file path", expectedFilePath, exportPath)

        val finalState = reportsViewModel.uiState.value
        assertEquals("Should show export success message", "Report exported to $expectedFilePath", finalState.message)
        assertFalse("Should not be exporting anymore", finalState.isExporting)
    }

    @Test
    fun `exportToFormat should handle export errors`() = runTest {
        // Given
        val testException = RuntimeException("Export failed")
        coEvery { medicalReportRepository.exportReport(any()) } throws testException

        // When
        val exportPath = reportsViewModel.exportToFormat(ReportFormat.CSV)
        advanceUntilIdle()

        // Then
        assertEquals("Should return null on failure", null, exportPath)

        val finalState = reportsViewModel.uiState.value
        assertEquals("Should show error message", "Export failed", finalState.error)
        assertFalse("Should not be exporting", finalState.isExporting)
    }

    @Test
    fun `shareReport should prepare report for sharing`() = runTest {
        // Given - current report available
        val currentReport = createMockMedicalReport("share-report", ReportFormat.PDF)
        val exportPath = "/storage/reports/share-report.pdf"

        coEvery { medicalReportRepository.exportReport("share-report") } returns exportPath

        // Setup current report
        reportsViewModel.generateReport()
        advanceUntilIdle()

        // When
        val shareData = reportsViewModel.shareReport()
        advanceUntilIdle()

        // Then
        assertNotNull("Share data should not be null", shareData)
        assertEquals("Share file path should match export path", exportPath, shareData!!.filePath)
        assertEquals("Share MIME type should be PDF", "application/pdf", shareData.mimeType)
        assertTrue("Share title should contain report info", shareData.title.contains("Medical Report"))
    }

    @Test
    fun `shareReport should handle sharing when no current report available`() = runTest {
        // Given - no current report

        // When
        val shareData = reportsViewModel.shareReport()
        advanceUntilIdle()

        // Then
        assertEquals("Share data should be null", null, shareData)

        val finalState = reportsViewModel.uiState.value
        assertEquals("Should show no report error", "No report available to share", finalState.error)
    }

    @Test
    fun `loadHistory should populate report history from repository`() = runTest {
        // Given
        val historicalReports = listOf(
            createMockMedicalReport("report-1", ReportFormat.PDF),
            createMockMedicalReport("report-2", ReportFormat.CSV),
            createMockMedicalReport("report-3", ReportFormat.JSON)
        )

        every { medicalReportRepository.getAll() } returns flowOf(historicalReports)

        // When
        reportsViewModel.loadHistory()
        advanceUntilIdle()

        // Then
        val finalState = reportsViewModel.uiState.value
        assertEquals("Should load all historical reports", 3, finalState.reportHistory.size)
        assertFalse("Should not be loading history anymore", finalState.isLoadingHistory)
        assertEquals("Reports should be ordered by creation date",
            "report-3", finalState.reportHistory[0].id) // Most recent first
    }

    @Test
    fun `deleteReport should remove report from history and repository`() = runTest {
        // Given - reports in history
        val reportToDelete = createMockMedicalReport("delete-me", ReportFormat.PDF)
        val remainingReport = createMockMedicalReport("keep-me", ReportFormat.CSV)

        every { medicalReportRepository.getAll() } returns
            flowOf(listOf(reportToDelete, remainingReport)) andThen
            flowOf(listOf(remainingReport))

        coEvery { medicalReportRepository.deleteReport("delete-me") } just Runs

        reportsViewModel.loadHistory()
        advanceUntilIdle()

        // When
        val result = reportsViewModel.deleteReport("delete-me")
        advanceUntilIdle()

        // Then
        assertTrue("Deletion should be successful", result)
        coVerify(exactly = 1) { medicalReportRepository.deleteReport("delete-me") }

        val finalState = reportsViewModel.uiState.value
        assertEquals("Should have one less report in history", 1, finalState.reportHistory.size)
        assertEquals("Remaining report should be the one we kept", "keep-me", finalState.reportHistory[0].id)
    }

    @Test
    fun `deleteReport should handle deletion errors`() = runTest {
        // Given
        val testException = RuntimeException("Deletion failed")
        coEvery { medicalReportRepository.deleteReport(any()) } throws testException

        // When
        val result = reportsViewModel.deleteReport("non-existent")
        advanceUntilIdle()

        // Then
        assertFalse("Deletion should fail", result)

        val finalState = reportsViewModel.uiState.value
        assertEquals("Should show error message", "Deletion failed", finalState.error)
    }

    @Test
    fun `updateDateRange should update start and end dates`() {
        // Given
        val newStartDate = LocalDate.of(2024, 2, 1)
        val newEndDate = LocalDate.of(2024, 2, 28)

        // When
        reportsViewModel.updateDateRange(newStartDate, newEndDate)

        // Then
        val state = reportsViewModel.uiState.value
        assertEquals("Start date should be updated", newStartDate, state.startDate)
        assertEquals("End date should be updated", newEndDate, state.endDate)
    }

    @Test
    fun `updateDateRange should validate date range and show error for invalid range`() {
        // Given - invalid date range (end before start)
        val invalidStartDate = LocalDate.of(2024, 3, 1)
        val invalidEndDate = LocalDate.of(2024, 2, 28)

        // When
        reportsViewModel.updateDateRange(invalidStartDate, invalidEndDate)

        // Then
        val state = reportsViewModel.uiState.value
        assertEquals("Should show validation error", "End date must be after start date", state.error)
        // Dates should not be updated with invalid values
    }

    @Test
    fun `updateSelectedFormat should change report format`() {
        // When
        reportsViewModel.updateSelectedFormat(ReportFormat.CSV)

        // Then
        val state = reportsViewModel.uiState.value
        assertEquals("Format should be updated", ReportFormat.CSV, state.selectedFormat)
    }

    @Test
    fun `updateSelectedSections should update included report sections`() {
        // Given
        val newSections = listOf(ReportSection.SYMPTOMS, ReportSection.CORRELATIONS)

        // When
        reportsViewModel.updateSelectedSections(newSections)

        // Then
        val state = reportsViewModel.uiState.value
        assertEquals("Sections should be updated", newSections, state.selectedSections)
    }

    @Test
    fun `previewReport should generate preview data without saving`() = runTest {
        // Given
        val previewData = createMockReportPreview()
        coEvery { medicalReportRepository.generateReport(any(), any(), any(), any()) } returns
            createMockMedicalReport("preview-report", ReportFormat.PDF)

        // When
        val preview = reportsViewModel.previewReport()
        advanceUntilIdle()

        // Then
        assertNotNull("Preview should not be null", preview)
        assertTrue("Preview should contain summary", preview!!.sections.any { it.type == ReportSection.SUMMARY })

        val state = reportsViewModel.uiState.value
        assertEquals("Preview should be available in state", preview, state.reportPreview)
    }

    @Test
    fun `getReportProgress should return generation progress percentage`() = runTest {
        // Given - report generation in progress
        reportsViewModel.generateReport()

        // When - simulate progress updates
        val initialProgress = reportsViewModel.getReportProgress()
        // Simulate some progress
        val midProgress = reportsViewModel.getReportProgress()

        // Then
        assertTrue("Initial progress should be >= 0", initialProgress >= 0)
        assertTrue("Progress should be <= 100", midProgress <= 100)
    }

    @Test
    fun `validateReportParameters should check for valid report configuration`() {
        // Given - invalid configuration (no sections selected)
        reportsViewModel.updateSelectedSections(emptyList())

        // When
        val isValid = reportsViewModel.validateReportParameters()

        // Then
        assertFalse("Should be invalid with no sections", isValid)

        val state = reportsViewModel.uiState.value
        assertEquals("Should show validation error", "At least one section must be selected", state.error)
    }

    @Test
    fun `clearError should reset error state`() {
        // Given - error state
        reportsViewModel.updateDateRange(
            LocalDate.of(2024, 3, 1),
            LocalDate.of(2024, 2, 28)
        ) // Invalid range

        // When
        reportsViewModel.clearError()

        // Then
        val state = reportsViewModel.uiState.value
        assertEquals("Error should be cleared", null, state.error)
    }

    // Helper methods for creating mock data
    private fun createMockMedicalReport(id: String, format: ReportFormat): MedicalReport {
        return MedicalReport(
            id = id,
            title = "Medical Report - ${format.name}",
            format = format,
            startDate = LocalDate.now().minusDays(30),
            endDate = LocalDate.now(),
            sections = listOf(ReportSection.SUMMARY, ReportSection.FOOD_LOG),
            filePath = "/storage/reports/$id.${format.name.lowercase()}",
            createdAt = Instant.now(),
            fileSize = 1024000L // 1MB
        )
    }

    private fun createMockReportPreview(): ReportPreview {
        return ReportPreview(
            title = "Preview Report",
            sections = listOf(
                ReportPreviewSection(
                    type = ReportSection.SUMMARY,
                    title = "Summary",
                    content = "Report summary content..."
                ),
                ReportPreviewSection(
                    type = ReportSection.FOOD_LOG,
                    title = "Food Log",
                    content = "Food entries content..."
                )
            ),
            estimatedPages = 15,
            estimatedFileSize = 2048000L
        )
    }
}

// Data classes that ReportsViewModel should use (these will need to be implemented)
data class ReportsUiState(
    val startDate: LocalDate = LocalDate.now().minusDays(30),
    val endDate: LocalDate = LocalDate.now(),
    val selectedFormat: ReportFormat = ReportFormat.PDF,
    val selectedSections: List<ReportSection> = listOf(
        ReportSection.SUMMARY,
        ReportSection.FOOD_LOG,
        ReportSection.SYMPTOMS,
        ReportSection.CORRELATIONS
    ),
    val isGenerating: Boolean = false,
    val isExporting: Boolean = false,
    val isLoadingHistory: Boolean = true,
    val currentReport: MedicalReport? = null,
    val reportHistory: List<MedicalReport> = emptyList(),
    val reportPreview: ReportPreview? = null,
    val generationProgress: Int = 0,
    val error: String? = null,
    val message: String? = null
)

enum class ReportFormat {
    PDF, CSV, JSON;

    val displayName: String
        get() = when (this) {
            PDF -> "PDF Document"
            CSV -> "CSV Spreadsheet"
            JSON -> "JSON Data"
        }

    val mimeType: String
        get() = when (this) {
            PDF -> "application/pdf"
            CSV -> "text/csv"
            JSON -> "application/json"
        }

    val fileExtension: String
        get() = name.lowercase()
}

enum class ReportSection {
    SUMMARY, FOOD_LOG, SYMPTOMS, CORRELATIONS, ENVIRONMENTAL, MEDICATIONS;

    val displayName: String
        get() = when (this) {
            SUMMARY -> "Summary & Overview"
            FOOD_LOG -> "Food Entries"
            SYMPTOMS -> "Symptom Events"
            CORRELATIONS -> "Food-Symptom Correlations"
            ENVIRONMENTAL -> "Environmental Factors"
            MEDICATIONS -> "Medications & Supplements"
        }
}

data class ReportShareData(
    val filePath: String,
    val mimeType: String,
    val title: String,
    val description: String
)

data class ReportPreview(
    val title: String,
    val sections: List<ReportPreviewSection>,
    val estimatedPages: Int,
    val estimatedFileSize: Long
)

data class ReportPreviewSection(
    val type: ReportSection,
    val title: String,
    val content: String
)