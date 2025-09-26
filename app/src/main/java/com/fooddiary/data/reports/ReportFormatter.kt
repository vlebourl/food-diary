package com.fooddiary.data.reports

import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.CorrelationPattern
import com.fooddiary.data.database.entities.ReportData
import com.fooddiary.data.models.*
import com.fooddiary.data.analytics.AnalyticsStatistics
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Formats report data into various output formats
 */
@Singleton
class ReportFormatter @Inject constructor() {

    /**
     * Format a comprehensive medical report
     */
    fun formatMedicalReport(
        reportData: MedicalReportData,
        format: ReportTypes.ReportFormat
    ): String {
        return when (format) {
            ReportTypes.ReportFormat.PDF -> formatMedicalReportAsPdf(reportData)
            ReportTypes.ReportFormat.HTML -> formatMedicalReportAsHtml(reportData)
            ReportTypes.ReportFormat.TEXT -> formatMedicalReportAsText(reportData)
            ReportTypes.ReportFormat.JSON -> formatMedicalReportAsJson(reportData)
        }
    }

    /**
     * Format a symptom summary report
     */
    fun formatSymptomSummary(
        symptomEvents: List<SymptomEvent>,
        statistics: AnalyticsStatistics,
        format: ReportTypes.ReportFormat
    ): String {
        val summaryData = SymptomSummaryData(
            totalSymptoms = symptomEvents.size,
            averageSeverity = statistics.averageSeverity,
            mostCommonSymptom = statistics.mostCommonSymptom,
            symptomBreakdown = symptomEvents.groupBy { it.symptomType }.mapValues { it.value.size },
            timeRange = getTimeRange(symptomEvents),
            severityDistribution = calculateSeverityDistribution(symptomEvents)
        )

        return when (format) {
            ReportTypes.ReportFormat.PDF -> formatSymptomSummaryAsPdf(summaryData)
            ReportTypes.ReportFormat.HTML -> formatSymptomSummaryAsHtml(summaryData)
            ReportTypes.ReportFormat.TEXT -> formatSymptomSummaryAsText(summaryData)
            ReportTypes.ReportFormat.JSON -> formatSymptomSummaryAsJson(summaryData)
        }
    }

    /**
     * Format a food diary export
     */
    fun formatFoodDiaryExport(
        foodEntries: List<FoodEntry>,
        format: ReportTypes.ReportFormat
    ): String {
        return when (format) {
            ReportTypes.ReportFormat.PDF -> formatFoodDiaryAsPdf(foodEntries)
            ReportTypes.ReportFormat.HTML -> formatFoodDiaryAsHtml(foodEntries)
            ReportTypes.ReportFormat.TEXT -> formatFoodDiaryAsText(foodEntries)
            ReportTypes.ReportFormat.JSON -> formatFoodDiaryAsJson(foodEntries)
        }
    }

    /**
     * Format correlation analysis report
     */
    fun formatCorrelationReport(
        correlations: List<CorrelationPattern>,
        format: ReportTypes.ReportFormat
    ): String {
        val reportData = CorrelationReportData(
            strongCorrelations = correlations.filter { it.correlationStrength > 0.7f },
            mediumCorrelations = correlations.filter { it.correlationStrength in 0.4f..0.7f },
            totalCorrelations = correlations.size,
            highConfidenceCount = correlations.count { it.confidenceLevel == ConfidenceLevel.HIGH }
        )

        return when (format) {
            ReportTypes.ReportFormat.PDF -> formatCorrelationReportAsPdf(reportData)
            ReportTypes.ReportFormat.HTML -> formatCorrelationReportAsHtml(reportData)
            ReportTypes.ReportFormat.TEXT -> formatCorrelationReportAsText(reportData)
            ReportTypes.ReportFormat.JSON -> formatCorrelationReportAsJson(reportData)
        }
    }

    /**
     * Format custom report based on configuration
     */
    fun formatCustomReport(
        reportConfig: ReportConfiguration,
        data: CustomReportData,
        format: ReportTypes.ReportFormat
    ): String {
        return when (format) {
            ReportTypes.ReportFormat.PDF -> formatCustomReportAsPdf(reportConfig, data)
            ReportTypes.ReportFormat.HTML -> formatCustomReportAsHtml(reportConfig, data)
            ReportTypes.ReportFormat.TEXT -> formatCustomReportAsText(reportConfig, data)
            ReportTypes.ReportFormat.JSON -> formatCustomReportAsJson(reportConfig, data)
        }
    }

    // PDF formatting methods

    private fun formatMedicalReportAsPdf(data: MedicalReportData): String {
        return buildString {
            appendLine("<!DOCTYPE html><html><head>")
            appendLine("<style>")
            appendLine("body { font-family: Arial, sans-serif; margin: 40px; }")
            appendLine("h1, h2 { color: #333; }")
            appendLine("table { border-collapse: collapse; width: 100%; }")
            appendLine("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }")
            appendLine("th { background-color: #f2f2f2; }")
            appendLine("</style>")
            appendLine("</head><body>")

            appendLine("<h1>Medical Report - Food Symptom Analysis</h1>")
            appendLine("<p><strong>Patient:</strong> ${data.patientName}</p>")
            appendLine("<p><strong>Report Period:</strong> ${formatDate(data.startDate)} to ${formatDate(data.endDate)}</p>")
            appendLine("<p><strong>Generated:</strong> ${formatTimestamp(data.generatedAt)}</p>")

            appendLine("<h2>Executive Summary</h2>")
            appendLine("<p>Total Symptoms: ${data.totalSymptoms}</p>")
            appendLine("<p>Average Severity: ${String.format("%.1f", data.averageSeverity)}/10</p>")
            appendLine("<p>Most Common Symptom: ${data.mostCommonSymptom?.displayName ?: "N/A"}</p>")

            if (data.strongCorrelations.isNotEmpty()) {
                appendLine("<h2>High-Risk Food Correlations</h2>")
                appendLine("<table>")
                appendLine("<tr><th>Food ID</th><th>Symptom ID</th><th>Strength</th><th>Confidence</th></tr>")
                data.strongCorrelations.forEach { correlation ->
                    appendLine("<tr>")
                    appendLine("<td>${correlation.foodId}</td>")
                    appendLine("<td>${correlation.symptomId}</td>")
                    appendLine("<td>${String.format("%.2f", correlation.correlationStrength)}</td>")
                    appendLine("<td>${correlation.confidenceLevel}</td>")
                    appendLine("</tr>")
                }
                appendLine("</table>")
            }

            appendLine("<h2>Recommendations</h2>")
            appendLine("<ul>")
            data.recommendations.forEach { recommendation ->
                appendLine("<li>$recommendation</li>")
            }
            appendLine("</ul>")

            appendLine("</body></html>")
        }
    }

    private fun formatSymptomSummaryAsPdf(data: SymptomSummaryData): String {
        return buildString {
            appendLine("<!DOCTYPE html><html><head><title>Symptom Summary</title></head><body>")
            appendLine("<h1>Symptom Summary Report</h1>")
            appendLine("<p>Total Symptoms: ${data.totalSymptoms}</p>")
            appendLine("<p>Average Severity: ${String.format("%.1f", data.averageSeverity)}</p>")
            appendLine("<p>Most Common: ${data.mostCommonSymptom?.displayName ?: "N/A"}</p>")
            appendLine("<p>Time Range: ${data.timeRange}</p>")

            appendLine("<h2>Symptom Breakdown</h2>")
            appendLine("<table border='1'>")
            appendLine("<tr><th>Symptom Type</th><th>Count</th></tr>")
            data.symptomBreakdown.forEach { (type, count) ->
                appendLine("<tr><td>${type.displayName}</td><td>$count</td></tr>")
            }
            appendLine("</table>")

            appendLine("</body></html>")
        }
    }

    private fun formatFoodDiaryAsPdf(foodEntries: List<FoodEntry>): String {
        return buildString {
            appendLine("<!DOCTYPE html><html><head><title>Food Diary Export</title></head><body>")
            appendLine("<h1>Food Diary Export</h1>")
            appendLine("<p>Total Entries: ${foodEntries.size}</p>")

            appendLine("<table border='1'>")
            appendLine("<tr><th>Date</th><th>Time</th><th>Meal Type</th><th>Foods</th><th>Notes</th></tr>")

            foodEntries.sortedBy { it.timestamp }.forEach { entry ->
                val dateTime = entry.timestamp.atZone(java.time.ZoneId.systemDefault())
                appendLine("<tr>")
                appendLine("<td>${dateTime.toLocalDate()}</td>")
                appendLine("<td>${dateTime.toLocalTime()}</td>")
                appendLine("<td>${entry.mealType}</td>")
                appendLine("<td>${entry.foods.joinToString(", ")}</td>")
                appendLine("<td>${entry.notes ?: ""}</td>")
                appendLine("</tr>")
            }

            appendLine("</table>")
            appendLine("</body></html>")
        }
    }

    private fun formatCorrelationReportAsPdf(data: CorrelationReportData): String {
        return buildString {
            appendLine("<!DOCTYPE html><html><head><title>Correlation Analysis</title></head><body>")
            appendLine("<h1>Food-Symptom Correlation Analysis</h1>")
            appendLine("<p>Total Correlations: ${data.totalCorrelations}</p>")
            appendLine("<p>High Confidence: ${data.highConfidenceCount}</p>")

            if (data.strongCorrelations.isNotEmpty()) {
                appendLine("<h2>Strong Correlations (>70%)</h2>")
                appendLine("<table border='1'>")
                appendLine("<tr><th>Food ID</th><th>Symptom ID</th><th>Strength</th><th>Occurrences</th></tr>")
                data.strongCorrelations.forEach { correlation ->
                    appendLine("<tr>")
                    appendLine("<td>${correlation.foodId}</td>")
                    appendLine("<td>${correlation.symptomId}</td>")
                    appendLine("<td>${String.format("%.2f", correlation.correlationStrength)}</td>")
                    appendLine("<td>${correlation.occurrenceCount}</td>")
                    appendLine("</tr>")
                }
                appendLine("</table>")
            }

            appendLine("</body></html>")
        }
    }

    private fun formatCustomReportAsPdf(config: ReportConfiguration, data: CustomReportData): String {
        return buildString {
            appendLine("<!DOCTYPE html><html><head><title>${config.title}</title></head><body>")
            appendLine("<h1>${config.title}</h1>")
            appendLine("<p>Generated: ${formatTimestamp(Instant.now())}</p>")

            config.sections.forEach { section ->
                when (section) {
                    ReportTypes.ReportSection.SUMMARY -> {
                        appendLine("<h2>Summary</h2>")
                        appendLine("<p>Total Food Entries: ${data.foodEntries.size}</p>")
                        appendLine("<p>Total Symptom Events: ${data.symptomEvents.size}</p>")
                    }
                    ReportTypes.ReportSection.CORRELATIONS -> {
                        appendLine("<h2>Correlations</h2>")
                        appendLine("<p>Strong correlations found: ${data.correlations.count { it.correlationStrength > 0.7f }}</p>")
                    }
                    ReportTypes.ReportSection.TRENDS -> {
                        appendLine("<h2>Trends</h2>")
                        appendLine("<p>Analysis period: ${config.startDate} to ${config.endDate}</p>")
                    }
                    ReportTypes.ReportSection.RECOMMENDATIONS -> {
                        appendLine("<h2>Recommendations</h2>")
                        appendLine("<ul>")
                        data.recommendations.forEach { rec ->
                            appendLine("<li>$rec</li>")
                        }
                        appendLine("</ul>")
                    }
                }
            }

            appendLine("</body></html>")
        }
    }

    // HTML formatting methods (similar to PDF but without HTML wrapper)
    private fun formatMedicalReportAsHtml(data: MedicalReportData): String = formatMedicalReportAsPdf(data)
    private fun formatSymptomSummaryAsHtml(data: SymptomSummaryData): String = formatSymptomSummaryAsPdf(data)
    private fun formatFoodDiaryAsHtml(foodEntries: List<FoodEntry>): String = formatFoodDiaryAsPdf(foodEntries)
    private fun formatCorrelationReportAsHtml(data: CorrelationReportData): String = formatCorrelationReportAsPdf(data)
    private fun formatCustomReportAsHtml(config: ReportConfiguration, data: CustomReportData): String =
        formatCustomReportAsPdf(config, data)

    // Text formatting methods
    private fun formatMedicalReportAsText(data: MedicalReportData): String {
        return buildString {
            appendLine("MEDICAL REPORT - FOOD SYMPTOM ANALYSIS")
            appendLine("=" .repeat(50))
            appendLine("Patient: ${data.patientName}")
            appendLine("Period: ${formatDate(data.startDate)} to ${formatDate(data.endDate)}")
            appendLine("Generated: ${formatTimestamp(data.generatedAt)}")
            appendLine()

            appendLine("EXECUTIVE SUMMARY")
            appendLine("-".repeat(20))
            appendLine("Total Symptoms: ${data.totalSymptoms}")
            appendLine("Average Severity: ${String.format("%.1f", data.averageSeverity)}/10")
            appendLine("Most Common Symptom: ${data.mostCommonSymptom?.displayName ?: "N/A"}")
            appendLine()

            if (data.strongCorrelations.isNotEmpty()) {
                appendLine("HIGH-RISK FOOD CORRELATIONS")
                appendLine("-".repeat(30))
                data.strongCorrelations.forEach { correlation ->
                    appendLine("Food ID ${correlation.foodId} -> Symptom ID ${correlation.symptomId}")
                    appendLine("  Strength: ${String.format("%.2f", correlation.correlationStrength)}")
                    appendLine("  Confidence: ${correlation.confidenceLevel}")
                    appendLine()
                }
            }

            appendLine("RECOMMENDATIONS")
            appendLine("-".repeat(20))
            data.recommendations.forEachIndexed { index, rec ->
                appendLine("${index + 1}. $rec")
            }
        }
    }

    private fun formatSymptomSummaryAsText(data: SymptomSummaryData): String {
        return buildString {
            appendLine("SYMPTOM SUMMARY REPORT")
            appendLine("=" .repeat(30))
            appendLine("Total Symptoms: ${data.totalSymptoms}")
            appendLine("Average Severity: ${String.format("%.1f", data.averageSeverity)}")
            appendLine("Most Common: ${data.mostCommonSymptom?.displayName ?: "N/A"}")
            appendLine("Time Range: ${data.timeRange}")
            appendLine()

            appendLine("SYMPTOM BREAKDOWN")
            appendLine("-".repeat(20))
            data.symptomBreakdown.forEach { (type, count) ->
                appendLine("${type.displayName}: $count")
            }
        }
    }

    private fun formatFoodDiaryAsText(foodEntries: List<FoodEntry>): String {
        return buildString {
            appendLine("FOOD DIARY EXPORT")
            appendLine("=" .repeat(25))
            appendLine("Total Entries: ${foodEntries.size}")
            appendLine()

            foodEntries.sortedBy { it.timestamp }.forEach { entry ->
                val dateTime = entry.timestamp.atZone(java.time.ZoneId.systemDefault())
                appendLine("${dateTime.toLocalDate()} ${dateTime.toLocalTime()} - ${entry.mealType}")
                appendLine("Foods: ${entry.foods.joinToString(", ")}")
                entry.notes?.let { appendLine("Notes: $it") }
                appendLine()
            }
        }
    }

    private fun formatCorrelationReportAsText(data: CorrelationReportData): String {
        return buildString {
            appendLine("CORRELATION ANALYSIS REPORT")
            appendLine("=" .repeat(35))
            appendLine("Total Correlations: ${data.totalCorrelations}")
            appendLine("High Confidence: ${data.highConfidenceCount}")
            appendLine()

            if (data.strongCorrelations.isNotEmpty()) {
                appendLine("STRONG CORRELATIONS (>70%)")
                appendLine("-".repeat(30))
                data.strongCorrelations.forEach { correlation ->
                    appendLine("Food ID ${correlation.foodId} -> Symptom ID ${correlation.symptomId}")
                    appendLine("  Strength: ${String.format("%.2f", correlation.correlationStrength)}")
                    appendLine("  Occurrences: ${correlation.occurrenceCount}")
                    appendLine()
                }
            }
        }
    }

    private fun formatCustomReportAsText(config: ReportConfiguration, data: CustomReportData): String {
        return buildString {
            appendLine(config.title.uppercase())
            appendLine("=" .repeat(config.title.length))
            appendLine("Generated: ${formatTimestamp(Instant.now())}")
            appendLine()

            config.sections.forEach { section ->
                when (section) {
                    ReportTypes.ReportSection.SUMMARY -> {
                        appendLine("SUMMARY")
                        appendLine("-".repeat(10))
                        appendLine("Food Entries: ${data.foodEntries.size}")
                        appendLine("Symptom Events: ${data.symptomEvents.size}")
                        appendLine()
                    }
                    ReportTypes.ReportSection.CORRELATIONS -> {
                        appendLine("CORRELATIONS")
                        appendLine("-".repeat(15))
                        appendLine("Strong correlations: ${data.correlations.count { it.correlationStrength > 0.7f }}")
                        appendLine()
                    }
                    ReportTypes.ReportSection.TRENDS -> {
                        appendLine("TRENDS")
                        appendLine("-".repeat(10))
                        appendLine("Period: ${config.startDate} to ${config.endDate}")
                        appendLine()
                    }
                    ReportTypes.ReportSection.RECOMMENDATIONS -> {
                        appendLine("RECOMMENDATIONS")
                        appendLine("-".repeat(20))
                        data.recommendations.forEachIndexed { index, rec ->
                            appendLine("${index + 1}. $rec")
                        }
                        appendLine()
                    }
                }
            }
        }
    }

    // JSON formatting methods
    private fun formatMedicalReportAsJson(data: MedicalReportData): String {
        return """
        {
            "type": "medical_report",
            "patient_name": "${data.patientName}",
            "start_date": "${data.startDate}",
            "end_date": "${data.endDate}",
            "generated_at": "${data.generatedAt}",
            "summary": {
                "total_symptoms": ${data.totalSymptoms},
                "average_severity": ${data.averageSeverity},
                "most_common_symptom": "${data.mostCommonSymptom?.name ?: "null"}"
            },
            "strong_correlations": [
                ${data.strongCorrelations.joinToString(",\n") { correlation ->
                    """
                    {
                        "food_id": ${correlation.foodId},
                        "symptom_id": ${correlation.symptomId},
                        "strength": ${correlation.correlationStrength},
                        "confidence": "${correlation.confidenceLevel}"
                    }
                    """.trimIndent()
                }}
            ],
            "recommendations": [
                ${data.recommendations.joinToString(",\n") { "\"$it\"" }}
            ]
        }
        """.trimIndent()
    }

    private fun formatSymptomSummaryAsJson(data: SymptomSummaryData): String {
        return """
        {
            "type": "symptom_summary",
            "total_symptoms": ${data.totalSymptoms},
            "average_severity": ${data.averageSeverity},
            "most_common_symptom": "${data.mostCommonSymptom?.name ?: "null"}",
            "time_range": "${data.timeRange}",
            "symptom_breakdown": {
                ${data.symptomBreakdown.entries.joinToString(",\n") { (type, count) ->
                    "\"${type.name}\": $count"
                }}
            }
        }
        """.trimIndent()
    }

    private fun formatFoodDiaryAsJson(foodEntries: List<FoodEntry>): String {
        return """
        {
            "type": "food_diary_export",
            "total_entries": ${foodEntries.size},
            "entries": [
                ${foodEntries.joinToString(",\n") { entry ->
                    """
                    {
                        "id": ${entry.id},
                        "timestamp": "${entry.timestamp}",
                        "meal_type": "${entry.mealType}",
                        "foods": [${entry.foods.joinToString(",") { "\"$it\"" }}],
                        "notes": "${entry.notes ?: ""}"
                    }
                    """.trimIndent()
                }}
            ]
        }
        """.trimIndent()
    }

    private fun formatCorrelationReportAsJson(data: CorrelationReportData): String {
        return """
        {
            "type": "correlation_report",
            "total_correlations": ${data.totalCorrelations},
            "high_confidence_count": ${data.highConfidenceCount},
            "strong_correlations": [
                ${data.strongCorrelations.joinToString(",\n") { correlation ->
                    """
                    {
                        "food_id": ${correlation.foodId},
                        "symptom_id": ${correlation.symptomId},
                        "strength": ${correlation.correlationStrength},
                        "occurrences": ${correlation.occurrenceCount}
                    }
                    """.trimIndent()
                }}
            ]
        }
        """.trimIndent()
    }

    private fun formatCustomReportAsJson(config: ReportConfiguration, data: CustomReportData): String {
        return """
        {
            "type": "custom_report",
            "title": "${config.title}",
            "generated_at": "${Instant.now()}",
            "config": {
                "start_date": "${config.startDate}",
                "end_date": "${config.endDate}",
                "sections": [${config.sections.joinToString(",") { "\"${it.name}\"" }}]
            },
            "data": {
                "food_entries_count": ${data.foodEntries.size},
                "symptom_events_count": ${data.symptomEvents.size},
                "correlations_count": ${data.correlations.size}
            }
        }
        """.trimIndent()
    }

    // Helper methods

    private fun getTimeRange(symptomEvents: List<SymptomEvent>): String {
        if (symptomEvents.isEmpty()) return "No data"

        val sorted = symptomEvents.sortedBy { it.timestamp }
        val start = sorted.first().timestamp.atZone(java.time.ZoneId.systemDefault()).toLocalDate()
        val end = sorted.last().timestamp.atZone(java.time.ZoneId.systemDefault()).toLocalDate()

        return "${formatDate(start)} to ${formatDate(end)}"
    }

    private fun calculateSeverityDistribution(symptomEvents: List<SymptomEvent>): Map<IntRange, Int> {
        return mapOf(
            1..3 to symptomEvents.count { it.severity in 1..3 },
            4..6 to symptomEvents.count { it.severity in 4..6 },
            7..10 to symptomEvents.count { it.severity in 7..10 }
        )
    }

    private fun formatDate(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }

    private fun formatTimestamp(timestamp: Instant): String {
        return timestamp.atZone(java.time.ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }
}

// Supporting data classes

data class MedicalReportData(
    val patientName: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val generatedAt: Instant,
    val totalSymptoms: Int,
    val averageSeverity: Float,
    val mostCommonSymptom: SymptomType?,
    val strongCorrelations: List<CorrelationPattern>,
    val recommendations: List<String>
)

data class SymptomSummaryData(
    val totalSymptoms: Int,
    val averageSeverity: Float,
    val mostCommonSymptom: SymptomType?,
    val symptomBreakdown: Map<SymptomType, Int>,
    val timeRange: String,
    val severityDistribution: Map<IntRange, Int>
)

data class CorrelationReportData(
    val strongCorrelations: List<CorrelationPattern>,
    val mediumCorrelations: List<CorrelationPattern>,
    val totalCorrelations: Int,
    val highConfidenceCount: Int
)

data class ReportConfiguration(
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val sections: List<ReportTypes.ReportSection>,
    val includeCharts: Boolean = false,
    val includeRawData: Boolean = false
)

data class CustomReportData(
    val foodEntries: List<FoodEntry>,
    val symptomEvents: List<SymptomEvent>,
    val correlations: List<CorrelationPattern>,
    val recommendations: List<String>
)