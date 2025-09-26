package com.fooddiary.data.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fooddiary.data.database.FoodDiaryDatabase
import com.fooddiary.data.database.entities.ReportData
import com.fooddiary.data.database.entities.ReportType
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@RunWith(AndroidJUnit4::class)
class ReportDataDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: FoodDiaryDatabase
    private lateinit var reportDataDao: MedicalReportDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FoodDiaryDatabase::class.java
        ).allowMainThreadQueries().build()

        reportDataDao = database.medicalReportDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insert_shouldInsertReportData() = runTest {
        // Arrange
        val reportData = createTestReportData()

        // Act
        val insertedId = reportDataDao.insert(reportData)

        // Assert
        assertTrue("Inserted ID should be greater than 0", insertedId > 0)

        val retrieved = reportDataDao.getById(insertedId)
        assertNotNull("Retrieved report should not be null", retrieved)
        assertEquals("Report type should match", reportData.reportType, retrieved!!.reportType)
        assertEquals("Start date should match", reportData.startDate, retrieved.startDate)
        assertEquals("End date should match", reportData.endDate, retrieved.endDate)
        assertEquals("Total entries should match", reportData.totalEntries, retrieved.totalEntries)
    }

    @Test
    fun insertAll_shouldInsertMultipleReportData() = runTest {
        // Arrange
        val reports = listOf(
            createTestReportData(reportType = ReportType.WEEKLY),
            createTestReportData(reportType = ReportType.MONTHLY),
            createTestReportData(reportType = ReportType.CUSTOM_RANGE)
        )

        // Act
        val insertedIds = reportDataDao.insertAll(reports)

        // Assert
        assertEquals("Should insert all 3 reports", 3, insertedIds.size)
        insertedIds.forEach { id ->
            assertTrue("Each inserted ID should be greater than 0", id > 0)
        }

        val allReports = reportDataDao.getAll()
        assertEquals("Should retrieve all 3 reports", 3, allReports.size)
    }

    @Test
    fun update_shouldUpdateReportData() = runTest {
        // Arrange
        val originalReport = createTestReportData()
        val insertedId = reportDataDao.insert(originalReport)
        val updatedReport = originalReport.copy(
            id = insertedId,
            totalEntries = 25,
            totalSymptoms = 8,
            averageSeverity = 6.5f,
            reportData = "Updated analysis data"
        )

        // Act
        val updateCount = reportDataDao.update(updatedReport)

        // Assert
        assertEquals("Should update 1 report", 1, updateCount)

        val retrieved = reportDataDao.getById(insertedId)
        assertNotNull("Updated report should exist", retrieved)
        assertEquals("Total entries should be updated", 25, retrieved!!.totalEntries)
        assertEquals("Total symptoms should be updated", 8, retrieved.totalSymptoms)
        assertEquals("Average severity should be updated", 6.5f, retrieved.averageSeverity, 0.001f)
        assertEquals("Report data should be updated", "Updated analysis data", retrieved.reportData)
    }

    @Test
    fun getById_shouldReturnCorrectReportData() = runTest {
        // Arrange
        val report1 = createTestReportData(reportType = ReportType.WEEKLY)
        val report2 = createTestReportData(reportType = ReportType.MONTHLY)

        val id1 = reportDataDao.insert(report1)
        val id2 = reportDataDao.insert(report2)

        // Act
        val retrieved1 = reportDataDao.getById(id1)
        val retrieved2 = reportDataDao.getById(id2)
        val nonExistent = reportDataDao.getById(999L)

        // Assert
        assertNotNull("First report should exist", retrieved1)
        assertNotNull("Second report should exist", retrieved2)
        assertNull("Non-existent report should be null", nonExistent)

        assertEquals("First report type should match", ReportType.WEEKLY, retrieved1!!.reportType)
        assertEquals("Second report type should match", ReportType.MONTHLY, retrieved2!!.reportType)
    }

    @Test
    fun getAll_shouldReturnAllReportData() = runTest {
        // Arrange
        val reports = listOf(
            createTestReportData(reportType = ReportType.WEEKLY),
            createTestReportData(reportType = ReportType.MONTHLY),
            createTestReportData(reportType = ReportType.CUSTOM_RANGE)
        )
        reports.forEach { reportDataDao.insert(it) }

        // Act
        val allReports = reportDataDao.getAll()

        // Assert
        assertEquals("Should return all 3 reports", 3, allReports.size)

        val reportTypes = allReports.map { it.reportType }
        assertTrue("Should contain weekly report", reportTypes.contains(ReportType.WEEKLY))
        assertTrue("Should contain monthly report", reportTypes.contains(ReportType.MONTHLY))
        assertTrue("Should contain custom range report", reportTypes.contains(ReportType.CUSTOM_RANGE))
    }

    @Test
    fun getReportsByType_shouldReturnReportsOfSpecificType() = runTest {
        // Arrange
        val weeklyReport1 = createTestReportData(
            reportType = ReportType.WEEKLY,
            startDate = LocalDate.now().minusWeeks(1),
            endDate = LocalDate.now()
        )
        val weeklyReport2 = createTestReportData(
            reportType = ReportType.WEEKLY,
            startDate = LocalDate.now().minusWeeks(2),
            endDate = LocalDate.now().minusWeeks(1)
        )
        val monthlyReport = createTestReportData(
            reportType = ReportType.MONTHLY,
            startDate = LocalDate.now().minusMonths(1),
            endDate = LocalDate.now()
        )

        reportDataDao.insert(weeklyReport1)
        reportDataDao.insert(weeklyReport2)
        reportDataDao.insert(monthlyReport)

        // Act
        val weeklyReports = reportDataDao.getReportsByType(ReportType.WEEKLY)
        val monthlyReports = reportDataDao.getReportsByType(ReportType.MONTHLY)
        val customReports = reportDataDao.getReportsByType(ReportType.CUSTOM_RANGE)

        // Assert
        assertEquals("Should find 2 weekly reports", 2, weeklyReports.size)
        assertEquals("Should find 1 monthly report", 1, monthlyReports.size)
        assertEquals("Should find 0 custom reports", 0, customReports.size)

        weeklyReports.forEach { report ->
            assertEquals("All results should be weekly reports", ReportType.WEEKLY, report.reportType)
        }
    }

    @Test
    fun getReportsByDateRange_shouldReturnReportsWithinDateRange() = runTest {
        // Arrange
        val baseDate = LocalDate.now()
        val recentReport = createTestReportData(
            startDate = baseDate.minusDays(5),
            endDate = baseDate.minusDays(1),
            generatedAt = Instant.now().minus(2, ChronoUnit.DAYS)
        )
        val oldReport = createTestReportData(
            startDate = baseDate.minusDays(20),
            endDate = baseDate.minusDays(15),
            generatedAt = Instant.now().minus(15, ChronoUnit.DAYS)
        )
        val veryOldReport = createTestReportData(
            startDate = baseDate.minusDays(35),
            endDate = baseDate.minusDays(30),
            generatedAt = Instant.now().minus(30, ChronoUnit.DAYS)
        )

        reportDataDao.insert(recentReport)
        reportDataDao.insert(oldReport)
        reportDataDao.insert(veryOldReport)

        // Act
        val recentReports = reportDataDao.getReportsByDateRange(
            startDate = baseDate.minusDays(10),
            endDate = baseDate
        )
        val allRecentReports = reportDataDao.getReportsByDateRange(
            startDate = baseDate.minusDays(25),
            endDate = baseDate
        )

        // Assert
        assertEquals("Should find 1 recent report", 1, recentReports.size)
        assertEquals("Should find 2 reports in extended range", 2, allRecentReports.size)

        assertTrue("Recent report should be within date range",
            recentReports.first().endDate.isAfter(baseDate.minusDays(10)))
    }

    @Test
    fun getReportsByGenerationTime_shouldReturnReportsGeneratedWithinTimeRange() = runTest {
        // Arrange
        val baseTime = Instant.now()
        val recentReport = createTestReportData(generatedAt = baseTime.minus(1, ChronoUnit.HOURS))
        val oldReport = createTestReportData(generatedAt = baseTime.minus(5, ChronoUnit.HOURS))
        val veryOldReport = createTestReportData(generatedAt = baseTime.minus(10, ChronoUnit.HOURS))

        reportDataDao.insert(recentReport)
        reportDataDao.insert(oldReport)
        reportDataDao.insert(veryOldReport)

        // Act
        val recentlyGenerated = reportDataDao.getReportsByGenerationTime(
            since = baseTime.minus(3, ChronoUnit.HOURS)
        )
        val allRecent = reportDataDao.getRecentReports(2)

        // Assert
        assertEquals("Should find 2 recently generated reports", 2, recentlyGenerated.size)
        assertEquals("Should return 2 most recent reports", 2, allRecent.size)

        recentlyGenerated.forEach { report ->
            assertTrue("Report should be generated within time range",
                report.generatedAt.isAfter(baseTime.minus(3, ChronoUnit.HOURS)))
        }
    }

    @Test
    fun getReportsByAverageSeverity_shouldReturnReportsWithinSeverityRange() = runTest {
        // Arrange
        val lowSeverityReport = createTestReportData(averageSeverity = 2.5f)
        val mediumSeverityReport = createTestReportData(averageSeverity = 5.5f)
        val highSeverityReport = createTestReportData(averageSeverity = 8.5f)

        reportDataDao.insert(lowSeverityReport)
        reportDataDao.insert(mediumSeverityReport)
        reportDataDao.insert(highSeverityReport)

        // Act
        val lowToMedium = reportDataDao.getReportsByAverageSeverity(2.0f, 6.0f)
        val highSeverity = reportDataDao.getReportsByAverageSeverity(8.0f, 10.0f)

        // Assert
        assertEquals("Should find 2 low-medium severity reports", 2, lowToMedium.size)
        assertEquals("Should find 1 high severity report", 1, highSeverity.size)

        lowToMedium.forEach { report ->
            assertTrue("Report should be within severity range", report.averageSeverity in 2.0f..6.0f)
        }

        assertEquals("High severity report should match", 8.5f, highSeverity.first().averageSeverity, 0.001f)
    }

    @Test
    fun getReportsByTriggerFoods_shouldReturnReportsContainingSpecificTriggerFoods() = runTest {
        // Arrange
        val dairyReport = createTestReportData(
            topTriggerFoods = listOf("Dairy", "Gluten", "Spicy Food")
        )
        val glutenReport = createTestReportData(
            topTriggerFoods = listOf("Gluten", "Nuts")
        )
        val noTriggerReport = createTestReportData(
            topTriggerFoods = emptyList()
        )

        reportDataDao.insert(dairyReport)
        reportDataDao.insert(glutenReport)
        reportDataDao.insert(noTriggerReport)

        // Act
        val dairyReports = reportDataDao.getReportsByTriggerFood("Dairy")
        val glutenReports = reportDataDao.getReportsByTriggerFood("Gluten")
        val nutsReports = reportDataDao.getReportsByTriggerFood("Nuts")
        val nonExistentReports = reportDataDao.getReportsByTriggerFood("NonExistent")

        // Assert
        assertEquals("Should find 1 report with Dairy", 1, dairyReports.size)
        assertEquals("Should find 2 reports with Gluten", 2, glutenReports.size)
        assertEquals("Should find 1 report with Nuts", 1, nutsReports.size)
        assertEquals("Should find 0 reports with non-existent trigger", 0, nonExistentReports.size)

        assertTrue("Dairy report should contain Dairy", dairyReports.first().topTriggerFoods.contains("Dairy"))
        glutenReports.forEach { report ->
            assertTrue("All results should contain Gluten", report.topTriggerFoods.contains("Gluten"))
        }
    }

    @Test
    fun getReportStatistics_shouldReturnAggregatedStatistics() = runTest {
        // Arrange
        val reports = listOf(
            createTestReportData(totalEntries = 10, totalSymptoms = 5, averageSeverity = 4.0f),
            createTestReportData(totalEntries = 20, totalSymptoms = 8, averageSeverity = 6.0f),
            createTestReportData(totalEntries = 15, totalSymptoms = 3, averageSeverity = 5.0f)
        )
        reports.forEach { reportDataDao.insert(it) }

        // Act
        val statistics = reportDataDao.getReportStatistics()

        // Assert
        assertNotNull("Statistics should not be null", statistics)
        assertEquals("Should have 3 total reports", 3, statistics!!.totalReports)
        assertEquals("Should have correct total entries", 45, statistics.totalEntries)
        assertEquals("Should have correct total symptoms", 16, statistics.totalSymptoms)
        assertEquals("Should have correct average severity", 5.0f, statistics.averageSeverity, 0.1f)
    }

    @Test
    fun delete_shouldPermanentlyRemoveReport() = runTest {
        // Arrange
        val report = createTestReportData()
        val insertedId = reportDataDao.insert(report)

        // Act
        val deleteCount = reportDataDao.delete(report.copy(id = insertedId))

        // Assert
        assertEquals("Should delete 1 report", 1, deleteCount)

        val retrieved = reportDataDao.getById(insertedId)
        assertNull("Report should not exist after deletion", retrieved)
    }

    @Test
    fun deleteById_shouldRemoveReportById() = runTest {
        // Arrange
        val report = createTestReportData()
        val insertedId = reportDataDao.insert(report)

        // Act
        val deleteCount = reportDataDao.deleteById(insertedId)

        // Assert
        assertEquals("Should delete 1 report", 1, deleteCount)

        val retrieved = reportDataDao.getById(insertedId)
        assertNull("Report should not exist after deletion", retrieved)
    }

    @Test
    fun deleteOldReports_shouldRemoveReportsOlderThanCutoff() = runTest {
        // Arrange
        val baseTime = Instant.now()
        val recentReport = createTestReportData(generatedAt = baseTime.minus(5, ChronoUnit.DAYS))
        val oldReport = createTestReportData(generatedAt = baseTime.minus(40, ChronoUnit.DAYS))
        val veryOldReport = createTestReportData(generatedAt = baseTime.minus(70, ChronoUnit.DAYS))

        reportDataDao.insert(recentReport)
        reportDataDao.insert(oldReport)
        reportDataDao.insert(veryOldReport)

        // Act
        val cutoffTime = baseTime.minus(30, ChronoUnit.DAYS)
        val deleteCount = reportDataDao.deleteOldReports(cutoffTime)

        // Assert
        assertEquals("Should delete 2 old reports", 2, deleteCount)

        val remainingReports = reportDataDao.getAll()
        assertEquals("Should have 1 remaining report", 1, remainingReports.size)
        assertTrue("Remaining report should be recent",
            remainingReports.first().generatedAt.isAfter(cutoffTime))
    }

    @Test
    fun deleteReportsByType_shouldRemoveReportsOfSpecificType() = runTest {
        // Arrange
        val weeklyReport = createTestReportData(reportType = ReportType.WEEKLY)
        val monthlyReport = createTestReportData(reportType = ReportType.MONTHLY)
        val customReport = createTestReportData(reportType = ReportType.CUSTOM_RANGE)

        reportDataDao.insert(weeklyReport)
        reportDataDao.insert(monthlyReport)
        reportDataDao.insert(customReport)

        // Act
        val deleteCount = reportDataDao.deleteReportsByType(ReportType.WEEKLY)

        // Assert
        assertEquals("Should delete 1 weekly report", 1, deleteCount)

        val remainingReports = reportDataDao.getAll()
        assertEquals("Should have 2 remaining reports", 2, remainingReports.size)

        val remainingTypes = remainingReports.map { it.reportType }
        assertFalse("Should not contain weekly reports", remainingTypes.contains(ReportType.WEEKLY))
        assertTrue("Should contain monthly report", remainingTypes.contains(ReportType.MONTHLY))
        assertTrue("Should contain custom report", remainingTypes.contains(ReportType.CUSTOM_RANGE))
    }

    @Test
    fun entity_validation_shouldEnforceConstraints() {
        // Test entity validation rules

        // Valid report should work
        val validReport = createTestReportData()
        assertNotNull("Valid report should be created", validReport)

        // Test date constraint
        try {
            createTestReportData(
                startDate = LocalDate.now(),
                endDate = LocalDate.now().minusDays(1)
            )
            fail("Should throw exception for start date after end date")
        } catch (e: IllegalArgumentException) {
            assertTrue("Should contain date validation message",
                e.message?.contains("Start date must be before or equal to end date") == true)
        }

        // Test negative total entries constraint
        try {
            createTestReportData(totalEntries = -1)
            fail("Should throw exception for negative total entries")
        } catch (e: IllegalArgumentException) {
            assertTrue("Should contain total entries validation message",
                e.message?.contains("Total entries must be non-negative") == true)
        }

        // Test negative total symptoms constraint
        try {
            createTestReportData(totalSymptoms = -1)
            fail("Should throw exception for negative total symptoms")
        } catch (e: IllegalArgumentException) {
            assertTrue("Should contain total symptoms validation message",
                e.message?.contains("Total symptoms must be non-negative") == true)
        }

        // Test average severity constraint
        try {
            createTestReportData(averageSeverity = 11.0f)
            fail("Should throw exception for average severity > 10")
        } catch (e: IllegalArgumentException) {
            assertTrue("Should contain average severity validation message",
                e.message?.contains("Average severity must be between 0 and 10") == true)
        }

        try {
            createTestReportData(averageSeverity = -1.0f)
            fail("Should throw exception for negative average severity")
        } catch (e: IllegalArgumentException) {
            assertTrue("Should contain average severity validation message",
                e.message?.contains("Average severity must be between 0 and 10") == true)
        }
    }

    @Test
    fun entity_createMethod_shouldCreateValidReport() {
        // Test the companion object create method
        val startDate = LocalDate.now().minusWeeks(1)
        val endDate = LocalDate.now()
        val topTriggerFoods = listOf("Dairy", "Gluten")
        val symptomFrequency = mapOf("Abdominal Pain" to "5", "Bloating" to "3")

        val report = ReportData.create(
            reportType = ReportType.WEEKLY,
            startDate = startDate,
            endDate = endDate,
            totalEntries = 15,
            totalSymptoms = 8,
            topTriggerFoods = topTriggerFoods,
            symptomFrequency = symptomFrequency,
            averageSeverity = 5.5f,
            reportData = "Test analysis data"
        )

        assertNotNull("Created report should not be null", report)
        assertEquals("Report type should match", ReportType.WEEKLY, report.reportType)
        assertEquals("Start date should match", startDate, report.startDate)
        assertEquals("End date should match", endDate, report.endDate)
        assertEquals("Total entries should match", 15, report.totalEntries)
        assertEquals("Total symptoms should match", 8, report.totalSymptoms)
        assertEquals("Top trigger foods should match", topTriggerFoods, report.topTriggerFoods)
        assertEquals("Symptom frequency should match", symptomFrequency, report.symptomFrequency)
        assertEquals("Average severity should match", 5.5f, report.averageSeverity, 0.001f)
        assertEquals("Report data should match", "Test analysis data", report.reportData)
        assertTrue("Generated time should be recent",
            report.generatedAt.isAfter(Instant.now().minus(1, ChronoUnit.MINUTES)))
    }

    private fun createTestReportData(
        reportType: ReportType = ReportType.WEEKLY,
        startDate: LocalDate = LocalDate.now().minusWeeks(1),
        endDate: LocalDate = LocalDate.now(),
        totalEntries: Int = 10,
        totalSymptoms: Int = 5,
        topTriggerFoods: List<String> = listOf("Dairy", "Gluten"),
        symptomFrequency: Map<String, String> = mapOf("Abdominal Pain" to "3", "Bloating" to "2"),
        averageSeverity: Float = 5.0f,
        reportData: String = "Test analysis data",
        generatedAt: Instant = Instant.now()
    ) = ReportData.create(
        reportType = reportType,
        startDate = startDate,
        endDate = endDate,
        totalEntries = totalEntries,
        totalSymptoms = totalSymptoms,
        topTriggerFoods = topTriggerFoods,
        symptomFrequency = symptomFrequency,
        averageSeverity = averageSeverity,
        reportData = reportData
    ).copy(generatedAt = generatedAt)
}