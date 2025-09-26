package com.fooddiary.data.repository

import com.fooddiary.data.database.dao.FoodEntryDao
import com.fooddiary.data.database.dao.SymptomEventDao
import com.fooddiary.data.database.dao.ReportDataDao
import com.fooddiary.data.database.dao.CorrelationPatternDao
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.ReportData
import com.fooddiary.data.database.entities.CorrelationPattern
import com.fooddiary.data.models.*
import com.fooddiary.data.reports.ReportFormatter
import com.fooddiary.data.reports.DataExporter
import com.fooddiary.data.reports.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for report generation and export functionality
 */
@Singleton
class ReportsRepository @Inject constructor(
    private val foodEntryDao: FoodEntryDao,
    private val symptomEventDao: SymptomEventDao,
    private val reportDataDao: ReportDataDao,
    private val correlationPatternDao: CorrelationPatternDao,
    private val reportFormatter: ReportFormatter,
    private val dataExporter: DataExporter
) {

    /**
     * Generate a comprehensive medical report
     */
    suspend fun generateReport(
        reportType: ReportTypes.ReportType,
        startDate: LocalDate,
        endDate: LocalDate,
        format: ReportTypes.ReportFormat = ReportTypes.ReportFormat.PDF,
        patientName: String = "Patient"
    ): Result<String> {
        return try {
            val reportContent = when (reportType) {
                ReportTypes.ReportType.MEDICAL -> generateMedicalReport(startDate, endDate, format, patientName)
                ReportTypes.ReportType.SYMPTOM_SUMMARY -> generateSymptomSummaryReport(startDate, endDate, format)
                ReportTypes.ReportType.FOOD_DIARY -> generateFoodDiaryReport(startDate, endDate, format)
                ReportTypes.ReportType.CORRELATION_ANALYSIS -> generateCorrelationReport(startDate, endDate, format)
                ReportTypes.ReportType.TRIGGER_FOODS -> generateTriggerFoodsReport(startDate, endDate, format)
                ReportTypes.ReportType.WEEKLY_SUMMARY -> generateWeeklySummaryReport(startDate, endDate, format)
                ReportTypes.ReportType.CUSTOM -> generateCustomReport(startDate, endDate, format)
            }

            // Save report metadata
            val reportData = ReportData.create(
                reportType = reportType,
                format = format,
                generatedContent = reportContent,
                patientName = patientName,
                startDate = startDate,
                endDate = endDate
            )

            val reportId = reportDataDao.insert(reportData)

            Result.success("Report generated with ID: $reportId")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export data to JSON format
     */
    suspend fun exportToJson(
        startDate: LocalDate? = null,
        endDate: LocalDate? = null,
        filePath: String
    ): Result<String> {
        return try {
            val foodEntries = getFilteredFoodEntries(startDate, endDate)
            val symptomEvents = getFilteredSymptomEvents(startDate, endDate)
            val correlations = correlationPatternDao.getAll()

            dataExporter.exportToJson(foodEntries, symptomEvents, correlations, filePath)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export data to CSV format
     */
    suspend fun exportToCsv(
        startDate: LocalDate? = null,
        endDate: LocalDate? = null,
        filePath: String
    ): Result<String> {
        return try {
            val foodEntries = getFilteredFoodEntries(startDate, endDate)
            dataExporter.exportToCsv(foodEntries, filePath)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get report history
     */
    fun getReportHistory(): Flow<List<ReportData>> {
        return reportDataDao.getAllFlow().map { reports ->
            reports.sortedByDescending { it.generatedAt }
        }
    }

    /**
     * Share a report
     */
    suspend fun shareReport(
        reportId: String,
        shareMethod: ShareMethod
    ): Result<String> {
        return try {
            val reportData = reportDataDao.getById(reportId)
                ?: return Result.failure(Exception("Report not found"))

            when (shareMethod) {
                ShareMethod.EMAIL -> shareViaEmail(reportData)
                ShareMethod.SECURE_LINK -> generateSecureLink(reportData)
                ShareMethod.HEALTHCARE_PORTAL -> uploadToHealthcarePortal(reportData)
                ShareMethod.EXPORT_FILE -> exportReportToFile(reportData)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Create a custom report with specific configuration
     */
    suspend fun createCustomReport(
        config: ReportConfiguration
    ): Result<String> {
        return try {
            val foodEntries = getFilteredFoodEntries(config.startDate, config.endDate)
            val symptomEvents = getFilteredSymptomEvents(config.startDate, config.endDate)
            val correlations = correlationPatternDao.getAll()

            val customData = CustomReportData(
                foodEntries = foodEntries,
                symptomEvents = symptomEvents,
                correlations = correlations,
                recommendations = generateRecommendations(correlations)
            )

            val reportContent = reportFormatter.formatCustomReport(
                reportConfig = config,
                data = customData,
                format = ReportTypes.ReportFormat.HTML
            )

            val reportData = ReportData.create(
                reportType = ReportTypes.ReportType.CUSTOM,
                format = ReportTypes.ReportFormat.HTML,
                generatedContent = reportContent,
                patientName = "Patient", // Would come from config
                startDate = config.startDate,
                endDate = config.endDate
            )

            val reportId = reportDataDao.insert(reportData)
            Result.success("Custom report created with ID: $reportId")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Schedule automatic report generation
     */
    suspend fun scheduleReport(
        reportType: ReportTypes.ReportType,
        frequency: ReportFrequency,
        format: ReportTypes.ReportFormat = ReportTypes.ReportFormat.PDF,
        recipients: List<String> = emptyList()
    ): Result<String> {
        return try {
            val nextGenerationTime = calculateNextGenerationTime(frequency)

            val scheduledReport = ReportData.create(
                reportType = reportType,
                format = format,
                generatedContent = "", // Will be populated when generated
                patientName = "Patient",
                startDate = LocalDate.now().minusDays(30),
                endDate = LocalDate.now(),
                isScheduled = true,
                nextGenerationTime = nextGenerationTime
            )

            val reportId = reportDataDao.insert(scheduledReport)
            Result.success("Report scheduled with ID: $reportId")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Delete a report
     */
    suspend fun deleteReport(reportId: String): Result<Unit> {
        return try {
            val deleted = reportDataDao.deleteById(reportId)
            if (deleted > 0) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Report not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get report statistics
     */
    suspend fun getReportStatistics(): ReportStatistics {
        val totalReports = reportDataDao.getCount()
        val reportsThisMonth = reportDataDao.getCountSince(
            Instant.now().minus(30, ChronoUnit.DAYS)
        )
        val sharedReports = reportDataDao.getSharedReports().size
        val storageUsed = reportDataDao.getTotalStorageUsed() ?: 0L

        return ReportStatistics(
            totalReports = totalReports,
            reportsThisMonth = reportsThisMonth,
            sharedReports = sharedReports,
            storageUsedBytes = storageUsed,
            averageReportsPerMonth = calculateAverageReportsPerMonth()
        )
    }

    /**
     * Process scheduled reports
     */
    suspend fun processScheduledReports(): List<String> {
        val scheduledReports = reportDataDao.getScheduledReports(Instant.now())
        val processedReports = mutableListOf<String>()

        for (report in scheduledReports) {
            try {
                // Generate the actual report content
                val reportContent = when (report.reportType) {
                    ReportTypes.ReportType.WEEKLY_SUMMARY -> {
                        val endDate = LocalDate.now()
                        val startDate = endDate.minusDays(7)
                        generateWeeklySummaryReport(startDate, endDate, report.format)
                    }
                    ReportTypes.ReportType.MEDICAL -> {
                        val endDate = LocalDate.now()
                        val startDate = endDate.minusDays(30)
                        generateMedicalReport(startDate, endDate, report.format, report.patientName)
                    }
                    else -> {
                        val endDate = LocalDate.now()
                        val startDate = endDate.minusDays(30)
                        generateSymptomSummaryReport(startDate, endDate, report.format)
                    }
                }

                // Update the report with generated content
                val updatedReport = report.copy(
                    generatedContent = reportContent,
                    generatedAt = Instant.now(),
                    nextGenerationTime = calculateNextGenerationTime(ReportFrequency.WEEKLY) // Default
                )

                reportDataDao.update(updatedReport)
                processedReports.add(report.id)
            } catch (e: Exception) {
                // Log error but continue with other reports
                continue
            }
        }

        return processedReports
    }

    /**
     * Clean up old reports
     */
    suspend fun cleanupOldReports(olderThanDays: Int = 90): Int {
        val cutoffTime = Instant.now().minus(olderThanDays.toLong(), ChronoUnit.DAYS)
        return reportDataDao.deleteOldReports(cutoffTime)
    }

    // Private helper methods

    private suspend fun generateMedicalReport(
        startDate: LocalDate,
        endDate: LocalDate,
        format: ReportTypes.ReportFormat,
        patientName: String
    ): String {
        val foodEntries = getFilteredFoodEntries(startDate, endDate)
        val symptomEvents = getFilteredSymptomEvents(startDate, endDate)
        val correlations = correlationPatternDao.getAll()

        val strongCorrelations = correlations.filter { it.correlationStrength > 0.7f }
        val recommendations = generateRecommendations(strongCorrelations)

        val reportData = MedicalReportData(
            patientName = patientName,
            startDate = startDate,
            endDate = endDate,
            generatedAt = Instant.now(),
            totalSymptoms = symptomEvents.size,
            averageSeverity = if (symptomEvents.isNotEmpty()) {
                symptomEvents.map { it.severity }.average().toFloat()
            } else 0f,
            mostCommonSymptom = symptomEvents.groupBy { it.symptomType }
                .maxByOrNull { it.value.size }?.key,
            strongCorrelations = strongCorrelations,
            recommendations = recommendations
        )

        return reportFormatter.formatMedicalReport(reportData, format)
    }

    private suspend fun generateSymptomSummaryReport(
        startDate: LocalDate,
        endDate: LocalDate,
        format: ReportTypes.ReportFormat
    ): String {
        val symptomEvents = getFilteredSymptomEvents(startDate, endDate)

        val statistics = com.fooddiary.data.analytics.AnalyticsStatistics(
            totalEntries = symptomEvents.size,
            totalFoodEntries = 0,
            totalSymptomEvents = symptomEvents.size,
            totalCorrelations = 0,
            averageSeverity = if (symptomEvents.isNotEmpty()) {
                symptomEvents.map { it.severity }.average().toFloat()
            } else 0f,
            mostCommonSymptom = symptomEvents.groupBy { it.symptomType }
                .maxByOrNull { it.value.size }?.key,
            mostCommonMealType = null,
            averageEntriesPerDay = 0f,
            symptomFreeStreak = 0,
            highRiskFoods = emptyList(),
            weeklyTrends = emptyMap(),
            topTriggerFoods = emptyList()
        )

        return reportFormatter.formatSymptomSummary(symptomEvents, statistics, format)
    }

    private suspend fun generateFoodDiaryReport(
        startDate: LocalDate,
        endDate: LocalDate,
        format: ReportTypes.ReportFormat
    ): String {
        val foodEntries = getFilteredFoodEntries(startDate, endDate)
        return reportFormatter.formatFoodDiaryExport(foodEntries, format)
    }

    private suspend fun generateCorrelationReport(
        startDate: LocalDate,
        endDate: LocalDate,
        format: ReportTypes.ReportFormat
    ): String {
        val correlations = correlationPatternDao.getAll()
        return reportFormatter.formatCorrelationReport(correlations, format)
    }

    private suspend fun generateTriggerFoodsReport(
        startDate: LocalDate,
        endDate: LocalDate,
        format: ReportTypes.ReportFormat
    ): String {
        val correlations = correlationPatternDao.getAll()
        val highRiskCorrelations = correlations.filter {
            it.correlationStrength > 0.6f && it.occurrenceCount >= 3
        }
        return reportFormatter.formatCorrelationReport(highRiskCorrelations, format)
    }

    private suspend fun generateWeeklySummaryReport(
        startDate: LocalDate,
        endDate: LocalDate,
        format: ReportTypes.ReportFormat
    ): String {
        // Similar to symptom summary but focused on weekly data
        return generateSymptomSummaryReport(startDate, endDate, format)
    }

    private suspend fun generateCustomReport(
        startDate: LocalDate,
        endDate: LocalDate,
        format: ReportTypes.ReportFormat
    ): String {
        // Generate a basic custom report with all sections
        val config = ReportConfiguration(
            title = "Custom Report",
            startDate = startDate,
            endDate = endDate,
            sections = listOf(
                ReportTypes.ReportSection.SUMMARY,
                ReportTypes.ReportSection.CORRELATIONS,
                ReportTypes.ReportSection.RECOMMENDATIONS
            )
        )

        val foodEntries = getFilteredFoodEntries(startDate, endDate)
        val symptomEvents = getFilteredSymptomEvents(startDate, endDate)
        val correlations = correlationPatternDao.getAll()

        val customData = CustomReportData(
            foodEntries = foodEntries,
            symptomEvents = symptomEvents,
            correlations = correlations,
            recommendations = generateRecommendations(correlations)
        )

        return reportFormatter.formatCustomReport(config, customData, format)
    }

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

    private fun generateRecommendations(correlations: List<CorrelationPattern>): List<String> {
        val recommendations = mutableListOf<String>()

        val strongCorrelations = correlations.filter { it.correlationStrength > 0.7f }
        if (strongCorrelations.isNotEmpty()) {
            recommendations.add("Consider eliminating high-risk foods identified in the correlation analysis")
            recommendations.add("Monitor symptoms closely when consuming trigger foods")
            recommendations.add("Keep detailed food and symptom logs for better pattern identification")
        }

        if (correlations.any { it.timeOffsetHours <= 2 }) {
            recommendations.add("Pay attention to immediate food reactions within 2 hours of eating")
        }

        if (correlations.size < 5) {
            recommendations.add("Continue tracking food and symptoms to identify more patterns")
        }

        recommendations.add("Consult with a healthcare provider to discuss these findings")

        return recommendations
    }

    private suspend fun shareViaEmail(reportData: ReportData): Result<String> {
        // Mock implementation - would integrate with email service
        reportDataDao.markAsShared(reportData.id, Instant.now())
        return Result.success("Report shared via email")
    }

    private suspend fun generateSecureLink(reportData: ReportData): Result<String> {
        // Mock implementation - would generate secure sharing link
        val linkId = UUID.randomUUID().toString()
        reportDataDao.markAsShared(reportData.id, Instant.now())
        return Result.success("https://secure.fooddiary.app/report/$linkId")
    }

    private suspend fun uploadToHealthcarePortal(reportData: ReportData): Result<String> {
        // Mock implementation - would upload to healthcare provider portal
        reportDataDao.markAsShared(reportData.id, Instant.now())
        return Result.success("Report uploaded to healthcare portal")
    }

    private suspend fun exportReportToFile(reportData: ReportData): Result<String> {
        val fileName = "report_${reportData.id}_${System.currentTimeMillis()}.${reportData.format.extension}"
        val filePath = "/tmp/$fileName"

        try {
            File(filePath).writeText(reportData.generatedContent)
            return Result.success(filePath)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    private fun calculateNextGenerationTime(frequency: ReportFrequency): Instant {
        val now = Instant.now()
        return when (frequency) {
            ReportFrequency.DAILY -> now.plus(1, ChronoUnit.DAYS)
            ReportFrequency.WEEKLY -> now.plus(7, ChronoUnit.DAYS)
            ReportFrequency.MONTHLY -> now.plus(30, ChronoUnit.DAYS)
            ReportFrequency.QUARTERLY -> now.plus(90, ChronoUnit.DAYS)
        }
    }

    private suspend fun calculateAverageReportsPerMonth(): Float {
        val thirtyDaysAgo = Instant.now().minus(30, ChronoUnit.DAYS)
        val recentCount = reportDataDao.getCountSince(thirtyDaysAgo)
        return recentCount.toFloat() // Simplified calculation
    }
}

// Supporting data classes and enums

enum class ShareMethod {
    EMAIL,
    SECURE_LINK,
    HEALTHCARE_PORTAL,
    EXPORT_FILE
}

enum class ReportFrequency {
    DAILY,
    WEEKLY,
    MONTHLY,
    QUARTERLY
}

data class ReportStatistics(
    val totalReports: Int,
    val reportsThisMonth: Int,
    val sharedReports: Int,
    val storageUsedBytes: Long,
    val averageReportsPerMonth: Float
)

// Extension property for format file extensions
val ReportTypes.ReportFormat.extension: String
    get() = when (this) {
        ReportTypes.ReportFormat.PDF -> "pdf"
        ReportTypes.ReportFormat.HTML -> "html"
        ReportTypes.ReportFormat.TEXT -> "txt"
        ReportTypes.ReportFormat.JSON -> "json"
    }