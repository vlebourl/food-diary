package com.fooddiary.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fooddiary.data.database.dao.FoodEntryDao
import com.fooddiary.data.database.dao.SymptomEventDao
import com.fooddiary.data.database.dao.ReportDataDao
import com.fooddiary.data.database.dao.CorrelationPatternDao
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.ReportData
import com.fooddiary.data.database.entities.CorrelationPattern
import com.fooddiary.data.models.*
import com.fooddiary.data.export.ReportFormatter
import com.fooddiary.data.export.DataExporter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * TDD Tests for ReportsRepository - THESE WILL FAIL INITIALLY
 *
 * Test coverage:
 * - Medical report generation with multiple formats
 * - Data export functionality (JSON, CSV, PDF)
 * - Report sharing and distribution
 * - Historical report management
 * - Custom report configurations
 * - HIPAA compliant data handling
 */
class ReportsRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var foodEntryDao: FoodEntryDao

    @MockK
    private lateinit var symptomEventDao: SymptomEventDao

    @MockK
    private lateinit var reportDataDao: ReportDataDao

    @MockK
    private lateinit var correlationPatternDao: CorrelationPatternDao

    @MockK
    private lateinit var reportFormatter: ReportFormatter

    @MockK
    private lateinit var dataExporter: DataExporter

    private lateinit var repository: ReportsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        // This will fail initially since ReportsRepository doesn't exist yet
        repository = ReportsRepository(
            foodEntryDao = foodEntryDao,
            symptomEventDao = symptomEventDao,
            reportDataDao = reportDataDao,
            correlationPatternDao = correlationPatternDao,
            reportFormatter = reportFormatter,
            dataExporter = dataExporter
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `generateReport should create comprehensive medical report`() = runTest {
        // Arrange
        val startDate = LocalDate.now().minusDays(30)
        val endDate = LocalDate.now()
        val format = ReportFormat.PDF
        val sections = listOf(
            ReportSection.SUMMARY,
            ReportSection.FOOD_DIARY,
            ReportSection.SYMPTOM_TRACKING,
            ReportSection.CORRELATIONS
        )

        val foodEntries = listOf(
            createTestFoodEntry(foods = listOf("Dairy", "Bread")),
            createTestFoodEntry(foods = listOf("Rice", "Chicken"))
        )
        val symptoms = listOf(
            createTestSymptomEvent(symptomType = SymptomType.BLOATING, severity = 6)
        )
        val correlations = listOf(
            createTestCorrelationPattern()
        )

        val generatedReport = MedicalReport(
            id = "report-123",
            title = "Food Diary Medical Report",
            startDate = startDate,
            endDate = endDate,
            format = format,
            sections = sections,
            generatedAt = Instant.now(),
            patientId = "patient-456",
            summary = ReportSummary(
                totalDays = 30,
                foodEntries = 2,
                symptomEvents = 1,
                identifiedTriggers = 1,
                overallTrend = "Improving"
            ),
            filePath = "/reports/medical_report_123.pdf",
            fileSize = 1024000L,
            isShared = false
        )

        coEvery { foodEntryDao.getEntriesInDateRange(startDate, endDate) } returns flowOf(foodEntries)
        coEvery { symptomEventDao.getEventsInDateRange(startDate, endDate) } returns flowOf(symptoms)
        coEvery { correlationPatternDao.getCorrelationsInDateRange(startDate, endDate) } returns correlations
        coEvery { reportFormatter.generateReport(any(), any(), any(), any()) } returns generatedReport
        coEvery { reportDataDao.insert(any()) } returns 123L

        // Act
        val result = repository.generateReport(startDate, endDate, format, sections)

        // Assert
        assertTrue("Should successfully generate report", result.isSuccess)

        val report = result.getOrNull()
        assertNotNull("Should return generated report", report)
        assertEquals("Should have correct format", format, report!!.format)
        assertEquals("Should include all sections", sections.size, report.sections.size)
        assertEquals("Should have correct date range", startDate, report.startDate)
        assertTrue("Should have file path", report.filePath.isNotEmpty())

        coVerify { reportFormatter.generateReport(foodEntries, symptoms, correlations, any()) }
        coVerify { reportDataDao.insert(any()) }
    }

    @Test
    fun `generateReport should handle empty data gracefully`() = runTest {
        // Arrange
        val startDate = LocalDate.now().minusDays(7)
        val endDate = LocalDate.now()

        coEvery { foodEntryDao.getEntriesInDateRange(startDate, endDate) } returns flowOf(emptyList())
        coEvery { symptomEventDao.getEventsInDateRange(startDate, endDate) } returns flowOf(emptyList())
        coEvery { correlationPatternDao.getCorrelationsInDateRange(startDate, endDate) } returns emptyList()

        val emptyDataReport = MedicalReport(
            id = "empty-report",
            title = "Empty Data Report",
            startDate = startDate,
            endDate = endDate,
            format = ReportFormat.JSON,
            sections = listOf(ReportSection.SUMMARY),
            generatedAt = Instant.now(),
            patientId = "patient-456",
            summary = ReportSummary(
                totalDays = 7,
                foodEntries = 0,
                symptomEvents = 0,
                identifiedTriggers = 0,
                overallTrend = "Insufficient data"
            ),
            filePath = "/reports/empty_report.json",
            fileSize = 256L,
            isShared = false
        )

        coEvery { reportFormatter.generateReport(emptyList(), emptyList(), emptyList(), any()) } returns emptyDataReport
        coEvery { reportDataDao.insert(any()) } returns 456L

        // Act
        val result = repository.generateReport(
            startDate, endDate, ReportFormat.JSON, listOf(ReportSection.SUMMARY)
        )

        // Assert
        assertTrue("Should handle empty data", result.isSuccess)

        val report = result.getOrNull()
        assertEquals("Should indicate no food entries", 0, report!!.summary.foodEntries)
        assertEquals("Should indicate no symptoms", 0, report.summary.symptomEvents)
        assertEquals("Should indicate insufficient data", "Insufficient data", report.summary.overallTrend)
    }

    @Test
    fun `exportToJson should create structured JSON export`() = runTest {
        // Arrange
        val startDate = LocalDate.now().minusDays(14)
        val endDate = LocalDate.now()

        val foodEntries = listOf(createTestFoodEntry())
        val symptoms = listOf(createTestSymptomEvent())

        val exportData = JsonExportData(
            metadata = ExportMetadata(
                exportDate = Instant.now(),
                dateRange = DateRange(startDate, endDate),
                totalRecords = 2,
                version = "1.0"
            ),
            foodEntries = foodEntries.map { it.toExportFormat() },
            symptoms = symptoms.map { it.toExportFormat() },
            correlations = emptyList()
        )

        val exportPath = "/exports/food_diary_export.json"

        coEvery { foodEntryDao.getEntriesInDateRange(startDate, endDate) } returns flowOf(foodEntries)
        coEvery { symptomEventDao.getEventsInDateRange(startDate, endDate) } returns flowOf(symptoms)
        coEvery { dataExporter.exportToJson(any()) } returns exportPath

        // Act
        val result = repository.exportToJson(startDate, endDate)

        // Assert
        assertTrue("Should successfully export to JSON", result.isSuccess)
        assertEquals("Should return export file path", exportPath, result.getOrNull())

        coVerify { dataExporter.exportToJson(match<JsonExportData> { data ->
            data.foodEntries.isNotEmpty() && data.symptoms.isNotEmpty()
        }) }
    }

    @Test
    fun `exportToCsv should create CSV files for food entries and symptoms`() = runTest {
        // Arrange
        val startDate = LocalDate.now().minusDays(7)
        val endDate = LocalDate.now()

        val foodEntries = listOf(
            createTestFoodEntry(foods = listOf("Apple", "Banana")),
            createTestFoodEntry(foods = listOf("Rice", "Chicken"))
        )
        val symptoms = listOf(
            createTestSymptomEvent(symptomType = SymptomType.BLOATING),
            createTestSymptomEvent(symptomType = SymptomType.ABDOMINAL_PAIN)
        )

        val csvExport = CsvExportResult(
            foodEntriesFile = "/exports/food_entries.csv",
            symptomsFile = "/exports/symptoms.csv",
            summaryFile = "/exports/summary.csv",
            totalRecords = 4
        )

        coEvery { foodEntryDao.getEntriesInDateRange(startDate, endDate) } returns flowOf(foodEntries)
        coEvery { symptomEventDao.getEventsInDateRange(startDate, endDate) } returns flowOf(symptoms)
        coEvery { dataExporter.exportToCsv(any(), any()) } returns csvExport

        // Act
        val result = repository.exportToCsv(startDate, endDate)

        // Assert
        assertTrue("Should successfully export to CSV", result.isSuccess)

        val exportResult = result.getOrNull()
        assertNotNull("Should return CSV export result", exportResult)
        assertTrue("Should create food entries file", exportResult!!.foodEntriesFile.isNotEmpty())
        assertTrue("Should create symptoms file", exportResult.symptomsFile.isNotEmpty())
        assertEquals("Should export all records", 4, exportResult.totalRecords)

        coVerify { dataExporter.exportToCsv(foodEntries, symptoms) }
    }

    @Test
    fun `getReportHistory should return paginated report history`() = runTest {
        // Arrange
        val reportHistory = listOf(
            ReportMetadata(
                id = "report-1",
                title = "Weekly Report",
                generatedAt = Instant.now().minus(1, ChronoUnit.DAYS),
                format = ReportFormat.PDF,
                fileSize = 500000L,
                isShared = true
            ),
            ReportMetadata(
                id = "report-2",
                title = "Monthly Analysis",
                generatedAt = Instant.now().minus(7, ChronoUnit.DAYS),
                format = ReportFormat.JSON,
                fileSize = 128000L,
                isShared = false
            )
        )

        coEvery { reportDataDao.getReportHistory(limit = 10, offset = 0) } returns flowOf(reportHistory)

        // Act
        val history = repository.getReportHistory(page = 0, pageSize = 10).first()

        // Assert
        assertEquals("Should return report history", 2, history.size)

        val recentReport = history.first()
        assertEquals("Should be most recent report", "report-1", recentReport.id)
        assertEquals("Should show PDF format", ReportFormat.PDF, recentReport.format)
        assertTrue("Should indicate shared status", recentReport.isShared)

        coVerify { reportDataDao.getReportHistory(limit = 10, offset = 0) }
    }

    @Test
    fun `shareReport should enable sharing with secure access controls`() = runTest {
        // Arrange
        val reportId = "report-123"
        val shareConfig = ShareConfiguration(
            shareType = ShareType.HEALTHCARE_PROVIDER,
            recipientId = "doctor-456",
            expirationDate = Instant.now().plus(30, ChronoUnit.DAYS),
            accessLevel = AccessLevel.READ_ONLY,
            requiresPassword = true,
            allowDownload = true
        )

        val existingReport = createTestReportMetadata(id = reportId)
        val shareResult = ShareResult(
            shareId = "share-789",
            shareUrl = "https://secure.fooddiary.com/shared/share-789",
            accessCode = "ABC123",
            expiresAt = shareConfig.expirationDate!!
        )

        coEvery { reportDataDao.getById(reportId) } returns existingReport
        coEvery { dataExporter.createSecureShare(any(), any()) } returns shareResult
        coEvery { reportDataDao.updateShareStatus(reportId, true) } returns 1

        // Act
        val result = repository.shareReport(reportId, shareConfig)

        // Assert
        assertTrue("Should successfully share report", result.isSuccess)

        val share = result.getOrNull()
        assertNotNull("Should return share result", share)
        assertTrue("Should provide secure URL", share!!.shareUrl.startsWith("https://"))
        assertNotNull("Should provide access code", share.accessCode)

        coVerify { dataExporter.createSecureShare(existingReport, shareConfig) }
        coVerify { reportDataDao.updateShareStatus(reportId, true) }
    }

    @Test
    fun `shareReport should fail for non-existent report`() = runTest {
        // Arrange
        val nonExistentReportId = "non-existent-report"

        coEvery { reportDataDao.getById(nonExistentReportId) } returns null

        // Act
        val result = repository.shareReport(
            nonExistentReportId,
            ShareConfiguration(shareType = ShareType.FAMILY_MEMBER)
        )

        // Assert
        assertTrue("Should fail for non-existent report", result.isFailure)
        assertEquals("Should return not found error",
            "Report not found", result.exceptionOrNull()?.message)

        coVerify { reportDataDao.getById(nonExistentReportId) }
        coVerify(exactly = 0) { dataExporter.createSecureShare(any(), any()) }
    }

    @Test
    fun `deleteReport should remove report and associated files`() = runTest {
        // Arrange
        val reportId = "report-to-delete"
        val reportMetadata = createTestReportMetadata(
            id = reportId,
            filePath = "/reports/report_to_delete.pdf"
        )

        coEvery { reportDataDao.getById(reportId) } returns reportMetadata
        coEvery { dataExporter.deleteFile(any()) } returns true
        coEvery { reportDataDao.delete(reportId) } returns 1

        // Act
        val result = repository.deleteReport(reportId)

        // Assert
        assertTrue("Should successfully delete report", result.isSuccess)

        coVerify { dataExporter.deleteFile("/reports/report_to_delete.pdf") }
        coVerify { reportDataDao.delete(reportId) }
    }

    @Test
    fun `createCustomReport should generate report with user-defined sections and filters`() = runTest {
        // Arrange
        val customConfig = CustomReportConfiguration(
            title = "Trigger Food Analysis",
            dateRange = DateRange(LocalDate.now().minusDays(60), LocalDate.now()),
            sections = listOf(ReportSection.CORRELATIONS, ReportSection.INSIGHTS),
            filters = ReportFilters(
                symptomTypes = listOf(SymptomType.BLOATING, SymptomType.ABDOMINAL_PAIN),
                severityRange = SeverityRange(min = 5, max = 10),
                foodCategories = listOf("Dairy", "Gluten"),
                excludeDeleted = true
            ),
            format = ReportFormat.PDF,
            includeCharts = true,
            includeSummary = true
        )

        val filteredData = FilteredReportData(
            foodEntries = listOf(createTestFoodEntry(foods = listOf("Dairy"))),
            symptoms = listOf(createTestSymptomEvent(severity = 7)),
            correlations = listOf(createTestCorrelationPattern())
        )

        val customReport = MedicalReport(
            id = "custom-report-123",
            title = customConfig.title,
            startDate = customConfig.dateRange.start,
            endDate = customConfig.dateRange.end,
            format = customConfig.format,
            sections = customConfig.sections,
            generatedAt = Instant.now(),
            patientId = "patient-456",
            summary = ReportSummary(
                totalDays = 60,
                foodEntries = 1,
                symptomEvents = 1,
                identifiedTriggers = 1,
                overallTrend = "Analysis focused on high-severity symptoms"
            ),
            filePath = "/reports/custom_trigger_analysis.pdf",
            fileSize = 2048000L,
            isShared = false
        )

        coEvery { dataExporter.filterDataByConfiguration(any()) } returns filteredData
        coEvery { reportFormatter.generateCustomReport(any(), any()) } returns customReport
        coEvery { reportDataDao.insert(any()) } returns 789L

        // Act
        val result = repository.createCustomReport(customConfig)

        // Assert
        assertTrue("Should successfully create custom report", result.isSuccess)

        val report = result.getOrNull()
        assertEquals("Should have custom title", "Trigger Food Analysis", report!!.title)
        assertEquals("Should include selected sections", 2, report.sections.size)
        assertTrue("Should focus on correlations", report.sections.contains(ReportSection.CORRELATIONS))

        coVerify { dataExporter.filterDataByConfiguration(customConfig) }
        coVerify { reportFormatter.generateCustomReport(filteredData, customConfig) }
    }

    @Test
    fun `validateReportIntegrity should check report file integrity`() = runTest {
        // Arrange
        val reportId = "report-integrity-check"
        val reportMetadata = createTestReportMetadata(
            id = reportId,
            filePath = "/reports/integrity_check.pdf",
            fileSize = 1024000L
        )

        val integrityResult = FileIntegrityResult(
            isValid = true,
            actualFileSize = 1024000L,
            expectedFileSize = 1024000L,
            checksumValid = true,
            lastModified = Instant.now().minus(1, ChronoUnit.HOURS)
        )

        coEvery { reportDataDao.getById(reportId) } returns reportMetadata
        coEvery { dataExporter.validateFileIntegrity(any()) } returns integrityResult

        // Act
        val result = repository.validateReportIntegrity(reportId)

        // Assert
        assertTrue("Should validate successfully", result.isSuccess)

        val integrity = result.getOrNull()
        assertTrue("Should confirm file validity", integrity!!.isValid)
        assertTrue("Should confirm checksum", integrity.checksumValid)
        assertEquals("Should match expected size", 1024000L, integrity.actualFileSize)

        coVerify { dataExporter.validateFileIntegrity("/reports/integrity_check.pdf") }
    }

    // Test helper methods
    private fun createTestFoodEntry(
        foods: List<String> = listOf("Test Food"),
        timestamp: Instant = Instant.now()
    ) = FoodEntry(
        id = kotlin.random.Random.nextLong(1, 1000),
        timestamp = timestamp,
        mealType = MealType.BREAKFAST,
        foods = foods,
        portions = foods.associateWith { "1 serving" },
        notes = null
    )

    private fun createTestSymptomEvent(
        symptomType: SymptomType = SymptomType.ABDOMINAL_PAIN,
        severity: Int = 5,
        timestamp: Instant = Instant.now()
    ) = SymptomEvent(
        id = kotlin.random.Random.nextLong(1, 1000),
        timestamp = timestamp,
        symptomType = symptomType,
        severity = severity,
        duration = null,
        notes = null
    )

    private fun createTestCorrelationPattern() = CorrelationPattern(
        id = kotlin.random.Random.nextLong(1, 1000),
        foodId = 1L,
        symptomId = 2L,
        correlationStrength = 0.75f,
        confidenceLevel = ConfidenceLevel.HIGH,
        timeOffsetHours = 2,
        occurrenceCount = 5,
        isActive = true
    )

    private fun createTestReportMetadata(
        id: String = "test-report",
        filePath: String = "/reports/test_report.pdf"
    ) = ReportMetadata(
        id = id,
        title = "Test Report",
        generatedAt = Instant.now(),
        format = ReportFormat.PDF,
        fileSize = 1024000L,
        isShared = false
    )
}

/**
 * Supporting data classes and extensions that would be defined in the repository
 */
data class MedicalReport(
    val id: String,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val format: ReportFormat,
    val sections: List<ReportSection>,
    val generatedAt: Instant,
    val patientId: String,
    val summary: ReportSummary,
    val filePath: String,
    val fileSize: Long,
    val isShared: Boolean
)

data class ReportSummary(
    val totalDays: Int,
    val foodEntries: Int,
    val symptomEvents: Int,
    val identifiedTriggers: Int,
    val overallTrend: String
)

data class ReportMetadata(
    val id: String,
    val title: String,
    val generatedAt: Instant,
    val format: ReportFormat,
    val fileSize: Long,
    val isShared: Boolean
)

data class JsonExportData(
    val metadata: ExportMetadata,
    val foodEntries: List<FoodEntryExport>,
    val symptoms: List<SymptomExport>,
    val correlations: List<CorrelationExport>
)

data class ExportMetadata(
    val exportDate: Instant,
    val dateRange: DateRange,
    val totalRecords: Int,
    val version: String
)

data class DateRange(
    val start: LocalDate,
    val end: LocalDate
)

data class CsvExportResult(
    val foodEntriesFile: String,
    val symptomsFile: String,
    val summaryFile: String,
    val totalRecords: Int
)

data class ShareConfiguration(
    val shareType: ShareType,
    val recipientId: String? = null,
    val expirationDate: Instant? = null,
    val accessLevel: AccessLevel = AccessLevel.READ_ONLY,
    val requiresPassword: Boolean = false,
    val allowDownload: Boolean = true
)

data class ShareResult(
    val shareId: String,
    val shareUrl: String,
    val accessCode: String,
    val expiresAt: Instant?
)

data class CustomReportConfiguration(
    val title: String,
    val dateRange: DateRange,
    val sections: List<ReportSection>,
    val filters: ReportFilters,
    val format: ReportFormat,
    val includeCharts: Boolean,
    val includeSummary: Boolean
)

data class ReportFilters(
    val symptomTypes: List<SymptomType>? = null,
    val severityRange: SeverityRange? = null,
    val foodCategories: List<String>? = null,
    val excludeDeleted: Boolean = true
)

data class SeverityRange(
    val min: Int,
    val max: Int
)

data class FilteredReportData(
    val foodEntries: List<FoodEntry>,
    val symptoms: List<SymptomEvent>,
    val correlations: List<CorrelationPattern>
)

data class FileIntegrityResult(
    val isValid: Boolean,
    val actualFileSize: Long,
    val expectedFileSize: Long,
    val checksumValid: Boolean,
    val lastModified: Instant
)

// Extension functions for export formats
fun FoodEntry.toExportFormat() = FoodEntryExport(
    id = id.toString(),
    timestamp = timestamp.toString(),
    mealType = mealType.name,
    foods = foods,
    portions = portions,
    notes = notes
)

fun SymptomEvent.toExportFormat() = SymptomExport(
    id = id.toString(),
    timestamp = timestamp.toString(),
    symptomType = symptomType.name,
    severity = severity,
    duration = duration?.toString(),
    notes = notes
)

data class FoodEntryExport(
    val id: String,
    val timestamp: String,
    val mealType: String,
    val foods: List<String>,
    val portions: Map<String, String>,
    val notes: String?
)

data class SymptomExport(
    val id: String,
    val timestamp: String,
    val symptomType: String,
    val severity: Int,
    val duration: String?,
    val notes: String?
)

data class CorrelationExport(
    val foodId: String,
    val symptomId: String,
    val strength: Float,
    val confidence: String
)

enum class ReportFormat {
    JSON, CSV, PDF, EXCEL
}

enum class ReportSection {
    SUMMARY, FOOD_DIARY, SYMPTOM_TRACKING, CORRELATIONS, INSIGHTS, RECOMMENDATIONS
}

enum class ShareType {
    HEALTHCARE_PROVIDER, FAMILY_MEMBER, RESEARCH, PERSONAL_BACKUP
}

enum class AccessLevel {
    READ_ONLY, FULL_ACCESS
}