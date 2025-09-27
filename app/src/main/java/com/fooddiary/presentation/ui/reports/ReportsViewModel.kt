package com.fooddiary.presentation.ui.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooddiary.data.repository.ReportsRepository
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.data.repository.SymptomEventRepository
import com.fooddiary.data.repository.CorrelationRepository
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * ViewModel for Reports screen with generation and export functionality
 * Manages medical report creation, export formats, and sharing capabilities
 */
@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val reportsRepository: ReportsRepository,
    private val foodEntryRepository: FoodEntryRepository,
    private val symptomEventRepository: SymptomEventRepository,
    private val correlationRepository: CorrelationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportsUiState())
    val uiState: StateFlow<ReportsUiState> = _uiState.asStateFlow()

    init {
        loadHistory()
    }

    /**
     * Generate a new report for the specified date range and type
     */
    fun generateReport(
        reportType: ReportType,
        startDate: LocalDate,
        endDate: LocalDate,
        includeCorrelations: Boolean = true,
        includeInsights: Boolean = true
    ) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    isGenerating = true,
                    generationProgress = 0f,
                    error = null
                )

                // Update progress
                updateProgress(0.1f, "Collecting food entries...")

                // Collect data
                val foodEntries = foodEntryRepository.getAllByDateRange(startDate, endDate).first()
                updateProgress(0.3f, "Collecting symptom data...")

                val symptomEvents = symptomEventRepository.getAllByDateRange(startDate, endDate).first()
                updateProgress(0.5f, "Analyzing correlations...")

                val correlations = if (includeCorrelations) {
                    correlationRepository.calculateCorrelations(
                        foodEntries.map { it.id },
                        symptomEvents.map { it.type }.distinct()
                    )
                } else emptyList()

                updateProgress(0.7f, "Generating report...")

                // Create report
                val report = createReport(
                    reportType = reportType,
                    startDate = startDate,
                    endDate = endDate,
                    foodEntries = foodEntries,
                    symptomEvents = symptomEvents,
                    correlations = correlations,
                    includeInsights = includeInsights
                )

                updateProgress(0.9f, "Saving report...")

                // Save report
                val reportId = reportsRepository.saveReport(report)

                updateProgress(1.0f, "Report generated successfully!")

                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    generationProgress = 0f,
                    progressMessage = null,
                    currentReport = report.copy(id = reportId),
                    message = "Report generated successfully"
                )

                // Refresh history
                loadHistory()

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    generationProgress = 0f,
                    progressMessage = null,
                    error = "Failed to generate report: ${e.message}"
                )
            }
        }
    }

    /**
     * Export report to specified format
     */
    fun exportToFormat(report: MedicalReport, format: ExportFormat): String? {
        return try {
            _uiState.value = _uiState.value.copy(isExporting = true)

            val exportedPath = when (format) {
                ExportFormat.PDF -> exportToPDF(report)
                ExportFormat.CSV -> exportToCSV(report)
                ExportFormat.JSON -> exportToJSON(report)
                ExportFormat.DOCX -> exportToDOCX(report)
            }

            _uiState.value = _uiState.value.copy(
                isExporting = false,
                message = "Report exported to $format format"
            )

            exportedPath
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isExporting = false,
                error = "Failed to export report: ${e.message}"
            )
            null
        }
    }

    /**
     * Share report via system sharing
     */
    fun shareReport(report: MedicalReport, format: ExportFormat) {
        viewModelScope.launch {
            try {
                val exportedPath = exportToFormat(report, format)
                if (exportedPath != null) {
                    // Trigger system sharing intent (handled by Fragment/Activity)
                    _uiState.value = _uiState.value.copy(
                        shareRequest = ShareRequest(exportedPath, format)
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to share report: ${e.message}"
                )
            }
        }
    }

    /**
     * Load report history
     */
    fun loadHistory() {
        viewModelScope.launch {
            try {
                reportsRepository.getAllReports().collect { reports ->
                    _uiState.value = _uiState.value.copy(
                        reportHistory = reports.sortedByDescending { it.createdDate }
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load report history: ${e.message}"
                )
            }
        }
    }

    /**
     * Delete a report from history
     */
    fun deleteReport(reportId: String) {
        viewModelScope.launch {
            try {
                reportsRepository.deleteReport(reportId)
                _uiState.value = _uiState.value.copy(
                    message = "Report deleted successfully"
                )
                loadHistory()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to delete report: ${e.message}"
                )
            }
        }
    }

    /**
     * Load an existing report for viewing
     */
    fun loadReport(reportId: String) {
        viewModelScope.launch {
            try {
                val report = reportsRepository.getReportById(reportId)
                _uiState.value = _uiState.value.copy(currentReport = report)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load report: ${e.message}"
                )
            }
        }
    }

    /**
     * Clear current error or message
     */
    fun clearMessage() {
        _uiState.value = _uiState.value.copy(error = null, message = null)
    }

    /**
     * Clear share request after handling
     */
    fun clearShareRequest() {
        _uiState.value = _uiState.value.copy(shareRequest = null)
    }

    /**
     * Update generation progress
     */
    private fun updateProgress(progress: Float, message: String) {
        _uiState.value = _uiState.value.copy(
            generationProgress = progress,
            progressMessage = message
        )
    }

    /**
     * Create a medical report from collected data
     */
    private suspend fun createReport(
        reportType: ReportType,
        startDate: LocalDate,
        endDate: LocalDate,
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        correlations: List<FoodCorrelation>,
        includeInsights: Boolean
    ): MedicalReport {

        val summary = ReportSummary(
            totalFoodEntries = foodEntries.size,
            totalSymptomEvents = symptomEvents.size,
            averageSeverity = if (symptomEvents.isNotEmpty()) {
                symptomEvents.map { it.severity }.average().toFloat()
            } else 0f,
            mostCommonSymptom = symptomEvents.groupBy { it.type }
                .maxByOrNull { it.value.size }?.key,
            identifiedTriggers = correlations.filter { it.strength > 0.5 }.size
        )

        val insights = if (includeInsights) {
            generateReportInsights(foodEntries, symptomEvents, correlations)
        } else emptyList()

        return MedicalReport(
            id = "", // Will be set when saved
            type = reportType,
            title = "${reportType.displayName} Report",
            dateRange = DateRange(startDate, endDate),
            createdDate = LocalDate.now(),
            summary = summary,
            foodEntries = foodEntries,
            symptomEvents = symptomEvents,
            correlations = correlations,
            insights = insights,
            recommendations = generateRecommendations(correlations, insights)
        )
    }

    /**
     * Generate insights for the report
     */
    private fun generateReportInsights(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        correlations: List<FoodCorrelation>
    ): List<ReportInsight> {
        val insights = mutableListOf<ReportInsight>()

        // Strong correlations
        correlations.filter { it.strength > 0.7 }.forEach { correlation ->
            insights.add(
                ReportInsight(
                    title = "Strong Food-Symptom Correlation",
                    description = "High correlation (${String.format("%.1f", correlation.strength * 100)}%) between food entries and ${correlation.symptomType.name}",
                    severity = ReportInsight.Severity.HIGH,
                    evidence = "Based on ${correlation.occurrences} occurrences"
                )
            )
        }

        // Symptom patterns
        val symptomsByDay = symptomEvents.groupBy {
            it.timestamp.atZone(java.time.ZoneId.systemDefault()).dayOfWeek
        }
        val mostSymptomaticDay = symptomsByDay.maxByOrNull { it.value.size }
        if (mostSymptomaticDay != null && mostSymptomaticDay.value.size > symptomEvents.size * 0.3) {
            insights.add(
                ReportInsight(
                    title = "Weekly Pattern Detected",
                    description = "${mostSymptomaticDay.key} shows the highest symptom frequency",
                    severity = ReportInsight.Severity.MEDIUM,
                    evidence = "${mostSymptomaticDay.value.size} symptoms on ${mostSymptomaticDay.key}s"
                )
            )
        }

        // Meal timing patterns
        val lateNightMeals = foodEntries.count {
            val hour = it.timestamp.atZone(java.time.ZoneId.systemDefault()).hour
            hour >= 22 || hour <= 6
        }
        if (lateNightMeals > foodEntries.size * 0.2) {
            insights.add(
                ReportInsight(
                    title = "Late Night Eating Pattern",
                    description = "Frequent late night eating may contribute to digestive symptoms",
                    severity = ReportInsight.Severity.MEDIUM,
                    evidence = "$lateNightMeals out of ${foodEntries.size} meals eaten late at night"
                )
            )
        }

        return insights
    }

    /**
     * Generate recommendations based on insights
     */
    private fun generateRecommendations(
        correlations: List<FoodCorrelation>,
        insights: List<ReportInsight>
    ): List<String> {
        val recommendations = mutableListOf<String>()

        // Strong trigger recommendations
        correlations.filter { it.strength > 0.7 }.forEach { correlation ->
            recommendations.add(
                "Consider eliminating or reducing trigger foods associated with ${correlation.symptomType.name}"
            )
        }

        // General recommendations
        if (insights.any { it.title.contains("Late Night") }) {
            recommendations.add("Try to finish eating at least 3 hours before bedtime")
        }

        if (correlations.isNotEmpty()) {
            recommendations.add("Continue tracking to identify additional patterns")
            recommendations.add("Consult with a healthcare provider to discuss these findings")
        }

        // Default recommendations
        if (recommendations.isEmpty()) {
            recommendations.addAll(listOf(
                "Continue consistent food and symptom tracking",
                "Consider consulting with a registered dietitian",
                "Maintain regular meal times and portion sizes"
            ))
        }

        return recommendations
    }

    /**
     * Export report to PDF format
     */
    private fun exportToPDF(report: MedicalReport): String {
        // TODO: Implement actual PDF export using a PDF library
        val fileName = "report_${report.type.name.lowercase()}_${LocalDate.now()}.pdf"
        return "/path/to/exports/$fileName"
    }

    /**
     * Export report to CSV format
     */
    private fun exportToCSV(report: MedicalReport): String {
        val csv = StringBuilder()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        // Header
        csv.appendLine("Food Diary Report - ${report.title}")
        csv.appendLine("Generated: ${LocalDate.now().format(formatter)}")
        csv.appendLine("Period: ${report.dateRange.startDate.format(formatter)} to ${report.dateRange.endDate.format(formatter)}")
        csv.appendLine("")

        // Summary
        csv.appendLine("Summary")
        csv.appendLine("Total Food Entries,${report.summary.totalFoodEntries}")
        csv.appendLine("Total Symptom Events,${report.summary.totalSymptomEvents}")
        csv.appendLine("Average Severity,${String.format("%.1f", report.summary.averageSeverity)}")
        csv.appendLine("Most Common Symptom,${report.summary.mostCommonSymptom?.name ?: "None"}")
        csv.appendLine("")

        // Food entries
        csv.appendLine("Food Entries")
        csv.appendLine("Date,Time,Meal Type,Foods,Portions")
        report.foodEntries.forEach { entry ->
            csv.appendLine("${entry.timestamp.atZone(java.time.ZoneId.systemDefault()).toLocalDate().format(formatter)}," +
                    "${entry.timestamp.atZone(java.time.ZoneId.systemDefault()).toLocalTime()}," +
                    "${entry.mealType.name},\"${entry.foods.joinToString("; ")}\",\"${entry.portions.joinToString("; ")}\"")
        }

        csv.appendLine("")

        // Symptom events
        csv.appendLine("Symptom Events")
        csv.appendLine("Date,Time,Type,Severity,Duration")
        report.symptomEvents.forEach { event ->
            csv.appendLine("${event.timestamp.atZone(java.time.ZoneId.systemDefault()).toLocalDate().format(formatter)}," +
                    "${event.timestamp.atZone(java.time.ZoneId.systemDefault()).toLocalTime()}," +
                    "${event.type.name},${event.severity},${event.duration ?: ""}")
        }

        val fileName = "report_${report.type.name.lowercase()}_${LocalDate.now()}.csv"
        // TODO: Actually write to file system
        return "/path/to/exports/$fileName"
    }

    /**
     * Export report to JSON format
     */
    private fun exportToJSON(report: MedicalReport): String {
        // TODO: Implement actual JSON serialization
        val fileName = "report_${report.type.name.lowercase()}_${LocalDate.now()}.json"
        return "/path/to/exports/$fileName"
    }

    /**
     * Export report to DOCX format
     */
    private fun exportToDOCX(report: MedicalReport): String {
        // TODO: Implement actual DOCX export
        val fileName = "report_${report.type.name.lowercase()}_${LocalDate.now()}.docx"
        return "/path/to/exports/$fileName"
    }
}

/**
 * UI State for Reports screen
 */
data class ReportsUiState(
    val isGenerating: Boolean = false,
    val isExporting: Boolean = false,
    val generationProgress: Float = 0f,
    val progressMessage: String? = null,
    val currentReport: MedicalReport? = null,
    val reportHistory: List<MedicalReport> = emptyList(),
    val shareRequest: ShareRequest? = null,
    val message: String? = null,
    val error: String? = null
)

/**
 * Share request data class
 */
data class ShareRequest(
    val filePath: String,
    val format: ExportFormat
)

/**
 * Export format options
 */
enum class ExportFormat {
    PDF, CSV, JSON, DOCX
}

/**
 * Report types available
 */
enum class ReportType {
    COMPREHENSIVE,
    SYMPTOM_FOCUSED,
    CORRELATION_ANALYSIS,
    MEDICAL_SUMMARY;

    val displayName: String
        get() = when (this) {
            COMPREHENSIVE -> "Comprehensive Report"
            SYMPTOM_FOCUSED -> "Symptom-Focused Report"
            CORRELATION_ANALYSIS -> "Correlation Analysis"
            MEDICAL_SUMMARY -> "Medical Summary"
        }
}

/**
 * Medical report data class
 */
data class MedicalReport(
    val id: String,
    val type: ReportType,
    val title: String,
    val dateRange: DateRange,
    val createdDate: LocalDate,
    val summary: ReportSummary,
    val foodEntries: List<FoodEntry>,
    val symptomEvents: List<SymptomEvent>,
    val correlations: List<FoodCorrelation>,
    val insights: List<ReportInsight>,
    val recommendations: List<String>
)

/**
 * Date range data class
 */
data class DateRange(
    val startDate: LocalDate,
    val endDate: LocalDate
)

/**
 * Report summary data class
 */
data class ReportSummary(
    val totalFoodEntries: Int,
    val totalSymptomEvents: Int,
    val averageSeverity: Float,
    val mostCommonSymptom: SymptomType?,
    val identifiedTriggers: Int
)

/**
 * Report insight data class
 */
data class ReportInsight(
    val title: String,
    val description: String,
    val severity: Severity,
    val evidence: String
) {
    enum class Severity {
        LOW, MEDIUM, HIGH
    }
}