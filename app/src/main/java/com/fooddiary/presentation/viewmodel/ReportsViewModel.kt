package com.fooddiary.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooddiary.data.database.entities.TriggerPattern
import com.fooddiary.data.models.SymptomType
import com.fooddiary.data.repository.TriggerPatternRepository
import com.fooddiary.data.repository.SymptomEventRepository
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.domain.usecase.GenerateReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val triggerPatternRepository: TriggerPatternRepository,
    private val symptomEventRepository: SymptomEventRepository,
    private val foodEntryRepository: FoodEntryRepository,
    private val generateReportUseCase: GenerateReportUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportsUiState())
    val uiState: StateFlow<ReportsUiState> = _uiState.asStateFlow()

    private val _selectedTimeRange = MutableStateFlow(TimeRange.LAST_30_DAYS)
    val selectedTimeRange: StateFlow<TimeRange> = _selectedTimeRange.asStateFlow()

    private val _selectedReportType = MutableStateFlow(ReportType.COMPREHENSIVE)
    val selectedReportType: StateFlow<ReportType> = _selectedReportType.asStateFlow()

    val availableReportTypes = listOf(
        ReportType.COMPREHENSIVE,
        ReportType.FOOD_PATTERNS,
        ReportType.SYMPTOM_SUMMARY,
        ReportType.WEEKLY_OVERVIEW,
        ReportType.MEDICAL_SUMMARY
    )

    init {
        loadReportData()
        observeSelectionChanges()
    }

    private fun observeSelectionChanges() {
        viewModelScope.launch {
            combine(
                selectedTimeRange,
                selectedReportType
            ) { timeRange, reportType ->
                Pair(timeRange, reportType)
            }.collect { (timeRange, reportType) ->
                loadReportData(timeRange, reportType)
            }
        }
    }

    fun updateTimeRange(timeRange: TimeRange) {
        _selectedTimeRange.value = timeRange
    }

    fun updateReportType(reportType: ReportType) {
        _selectedReportType.value = reportType
    }

    fun generateReport() {
        _uiState.value = _uiState.value.copy(isGenerating = true)

        viewModelScope.launch {
            try {
                val timeRange = _selectedTimeRange.value
                val reportType = _selectedReportType.value
                val (startDate, endDate) = timeRange.getDateRange()

                val reportData = generateReportUseCase(
                    startDate = startDate,
                    endDate = endDate,
                    reportType = reportType
                )

                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    generatedReport = reportData,
                    lastGeneratedTime = System.currentTimeMillis()
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    errorMessage = e.message ?: "Failed to generate report"
                )
            }
        }
    }

    fun shareReport() {
        viewModelScope.launch {
            try {
                val reportData = _uiState.value.generatedReport
                if (reportData != null) {
                    _uiState.value = _uiState.value.copy(
                        shareableReportText = formatReportForSharing(reportData)
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to prepare report for sharing"
                )
            }
        }
    }

    fun exportReport(format: ExportFormat) {
        _uiState.value = _uiState.value.copy(isExporting = true)

        viewModelScope.launch {
            try {
                val reportData = _uiState.value.generatedReport
                if (reportData != null) {
                    val exportResult = when (format) {
                        ExportFormat.PDF -> generatePdfReport(reportData)
                        ExportFormat.CSV -> generateCsvReport(reportData)
                        ExportFormat.TEXT -> generateTextReport(reportData)
                    }

                    _uiState.value = _uiState.value.copy(
                        isExporting = false,
                        exportedFilePath = exportResult.filePath,
                        exportSuccessMessage = "Report exported as ${format.displayName}"
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isExporting = false,
                        errorMessage = "No report data available to export"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isExporting = false,
                    errorMessage = e.message ?: "Failed to export report"
                )
            }
        }
    }

    private fun loadReportData(
        timeRange: TimeRange = _selectedTimeRange.value,
        reportType: ReportType = _selectedReportType.value
    ) {
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

                val significantPatterns = triggerPatternRepository.getStatisticallySignificant().first()

                val reportPreview = generateReportPreview(
                    totalSymptoms,
                    totalFoodEntries,
                    significantPatterns,
                    timeRange,
                    reportType
                )

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    totalSymptoms = totalSymptoms,
                    totalFoodEntries = totalFoodEntries,
                    significantPatternsCount = significantPatterns.size,
                    reportPreview = reportPreview,
                    timeRangeDescription = timeRange.description,
                    reportTypeDescription = reportType.description
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to load report data"
                )
            }
        }
    }

    private fun generateReportPreview(
        totalSymptoms: Int,
        totalFoodEntries: Int,
        significantPatterns: List<TriggerPattern>,
        timeRange: TimeRange,
        reportType: ReportType
    ): String {
        return buildString {
            appendLine("${reportType.displayName} Report")
            appendLine("Period: ${timeRange.description}")
            appendLine("Generated: ${LocalDate.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))}")
            appendLine()
            appendLine("Summary:")
            appendLine("• Total food entries: $totalFoodEntries")
            appendLine("• Total symptoms recorded: $totalSymptoms")
            appendLine("• Significant patterns identified: ${significantPatterns.size}")

            if (significantPatterns.isNotEmpty()) {
                appendLine()
                appendLine("Key Findings:")
                significantPatterns.take(3).forEach { pattern ->
                    val correlation = (pattern.correlationStrength * 100).toInt()
                    appendLine("• ${pattern.foodName} → ${pattern.symptomType.displayName} ($correlation% correlation)")
                }

                if (significantPatterns.size > 3) {
                    appendLine("• ... and ${significantPatterns.size - 3} more patterns")
                }
            }

            appendLine()
            appendLine("Generate full report to view detailed analysis and recommendations.")
        }
    }

    private fun formatReportForSharing(reportData: ReportData): String {
        return buildString {
            appendLine("Food Diary Report")
            appendLine("================")
            appendLine()
            appendLine("Period: ${reportData.timeRange}")
            appendLine("Generated: ${reportData.generatedDate}")
            appendLine()
            appendLine("SUMMARY")
            appendLine("-------")
            appendLine("Food Entries: ${reportData.totalFoodEntries}")
            appendLine("Symptoms: ${reportData.totalSymptoms}")
            appendLine("Patterns Found: ${reportData.significantPatterns.size}")
            appendLine()

            if (reportData.significantPatterns.isNotEmpty()) {
                appendLine("SIGNIFICANT PATTERNS")
                appendLine("-------------------")
                reportData.significantPatterns.forEach { pattern ->
                    appendLine("${pattern.foodName} → ${pattern.symptomType.displayName}")
                    appendLine("  Correlation: ${(pattern.correlationStrength * 100).toInt()}%")
                    appendLine("  Occurrences: ${pattern.occurrences}")
                    appendLine("  Avg. time to onset: ${pattern.averageTimeOffsetMinutes} minutes")
                    appendLine()
                }
            }

            appendLine("Report generated by Food Diary app")
        }
    }

    private suspend fun generatePdfReport(reportData: ReportData): ExportResult {
        // PDF generation would be implemented here
        // For now, return a placeholder result
        return ExportResult(
            filePath = "/storage/emulated/0/Documents/food_diary_report.pdf",
            success = true
        )
    }

    private suspend fun generateCsvReport(reportData: ReportData): ExportResult {
        // CSV generation would be implemented here
        return ExportResult(
            filePath = "/storage/emulated/0/Documents/food_diary_data.csv",
            success = true
        )
    }

    private suspend fun generateTextReport(reportData: ReportData): ExportResult {
        // Text report generation would be implemented here
        return ExportResult(
            filePath = "/storage/emulated/0/Documents/food_diary_report.txt",
            success = true
        )
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null,
            exportSuccessMessage = null
        )
    }

    fun clearGeneratedReport() {
        _uiState.value = _uiState.value.copy(
            generatedReport = null,
            shareableReportText = null,
            exportedFilePath = null
        )
    }
}

data class ReportsUiState(
    val isLoading: Boolean = false,
    val isGenerating: Boolean = false,
    val isExporting: Boolean = false,
    val totalSymptoms: Int = 0,
    val totalFoodEntries: Int = 0,
    val significantPatternsCount: Int = 0,
    val reportPreview: String = "",
    val timeRangeDescription: String = "Last 30 days",
    val reportTypeDescription: String = "Comprehensive Report",
    val generatedReport: ReportData? = null,
    val shareableReportText: String? = null,
    val exportedFilePath: String? = null,
    val lastGeneratedTime: Long? = null,
    val exportSuccessMessage: String? = null,
    val errorMessage: String? = null
) {
    val hasData: Boolean
        get() = totalSymptoms > 0 || totalFoodEntries > 0

    val canGenerateReport: Boolean
        get() = hasData && !isLoading && !isGenerating

    val canExportReport: Boolean
        get() = generatedReport != null && !isExporting

    val lastGeneratedFormatted: String?
        get() = lastGeneratedTime?.let {
            val formatter = DateTimeFormatter.ofPattern("MMM dd, HH:mm")
            java.time.Instant.ofEpochMilli(it)
                .atZone(java.time.ZoneId.systemDefault())
                .format(formatter)
        }
}

enum class ReportType(
    val displayName: String,
    val description: String
) {
    COMPREHENSIVE("Comprehensive Report", "Complete analysis with all patterns and recommendations"),
    FOOD_PATTERNS("Food Patterns", "Focus on food triggers and correlations"),
    SYMPTOM_SUMMARY("Symptom Summary", "Overview of symptom frequency and severity"),
    WEEKLY_OVERVIEW("Weekly Overview", "Week-by-week breakdown of patterns"),
    MEDICAL_SUMMARY("Medical Summary", "Concise report suitable for healthcare providers")
}

enum class ExportFormat(val displayName: String) {
    PDF("PDF Document"),
    CSV("CSV Data"),
    TEXT("Text File")
}

data class ReportData(
    val timeRange: String,
    val generatedDate: String,
    val totalFoodEntries: Int,
    val totalSymptoms: Int,
    val significantPatterns: List<TriggerPattern>,
    val reportType: ReportType,
    val detailedAnalysis: String
)

data class ExportResult(
    val filePath: String,
    val success: Boolean,
    val errorMessage: String? = null
)