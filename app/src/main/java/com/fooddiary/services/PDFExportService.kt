package com.fooddiary.services

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.ParcelFileDescriptor
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.models.SymptomType
import com.fooddiary.domain.analysis.CorrelationAnalysis
import com.fooddiary.domain.analysis.TriggerPattern
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class PDFExportService @Inject constructor(
    private val context: Context
) {

    suspend fun exportAnalyticsReport(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        correlations: List<CorrelationAnalysis>,
        triggerPatterns: List<TriggerPattern>,
        startDate: LocalDate,
        endDate: LocalDate,
        outputFile: File
    ): Result<File> = withContext(Dispatchers.IO) {
        try {
            val htmlContent = generateAnalyticsHTML(
                foodEntries, symptomEvents, correlations, triggerPatterns, startDate, endDate
            )

            val pdfFile = convertHTMLToPDF(htmlContent, outputFile)
            Result.success(pdfFile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun exportFoodDiary(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        startDate: LocalDate,
        endDate: LocalDate,
        outputFile: File
    ): Result<File> = withContext(Dispatchers.IO) {
        try {
            val htmlContent = generateFoodDiaryHTML(foodEntries, symptomEvents, startDate, endDate)
            val pdfFile = convertHTMLToPDF(htmlContent, outputFile)
            Result.success(pdfFile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun exportMedicalReport(
        correlations: List<CorrelationAnalysis>,
        triggerPatterns: List<TriggerPattern>,
        patientInfo: PatientInfo,
        startDate: LocalDate,
        endDate: LocalDate,
        outputFile: File
    ): Result<File> = withContext(Dispatchers.IO) {
        try {
            val htmlContent = generateMedicalReportHTML(
                correlations, triggerPatterns, patientInfo, startDate, endDate
            )
            val pdfFile = convertHTMLToPDF(htmlContent, outputFile)
            Result.success(pdfFile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun generateAnalyticsHTML(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        correlations: List<CorrelationAnalysis>,
        triggerPatterns: List<TriggerPattern>,
        startDate: LocalDate,
        endDate: LocalDate
    ): String {
        val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")

        return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <title>Food Diary Analytics Report</title>
            <style>
                body { font-family: Arial, sans-serif; margin: 20px; color: #333; }
                .header { text-align: center; border-bottom: 2px solid #4CAF50; padding-bottom: 20px; }
                .section { margin: 30px 0; }
                .section h2 { color: #4CAF50; border-bottom: 1px solid #ddd; padding-bottom: 10px; }
                .summary-table { width: 100%; border-collapse: collapse; margin: 20px 0; }
                .summary-table th, .summary-table td {
                    border: 1px solid #ddd; padding: 12px; text-align: left;
                }
                .summary-table th { background-color: #f8f9fa; }
                .correlation-item {
                    background: #f8f9fa; border-radius: 8px; padding: 15px; margin: 10px 0;
                }
                .correlation-strong { border-left: 5px solid #ff5722; }
                .correlation-moderate { border-left: 5px solid #ff9800; }
                .correlation-weak { border-left: 5px solid #ffeb3b; }
                .symptom-timeline { margin: 20px 0; }
                .timeline-item {
                    display: flex; padding: 10px; border-bottom: 1px solid #eee;
                }
                .timeline-date { font-weight: bold; width: 120px; }
                .timeline-content { flex: 1; }
                @media print { body { margin: 0; } .no-print { display: none; } }
            </style>
        </head>
        <body>
            <div class="header">
                <h1>Food Diary Analytics Report</h1>
                <p>Period: ${startDate.format(dateFormatter)} - ${endDate.format(dateFormatter)}</p>
                <p>Generated on: ${LocalDate.now().format(dateFormatter)}</p>
            </div>

            <div class="section">
                <h2>Summary Statistics</h2>
                <table class="summary-table">
                    <tr><th>Metric</th><th>Value</th></tr>
                    <tr><td>Total Food Entries</td><td>${foodEntries.size}</td></tr>
                    <tr><td>Total Symptom Events</td><td>${symptomEvents.size}</td></tr>
                    <tr><td>Significant Correlations Found</td><td>${correlations.count { it.isStatisticallySignificant }}</td></tr>
                    <tr><td>Trigger Patterns Identified</td><td>${triggerPatterns.size}</td></tr>
                    <tr><td>Most Common Symptom</td><td>${getMostCommonSymptom(symptomEvents)}</td></tr>
                    <tr><td>Analysis Period (Days)</td><td>${java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate)}</td></tr>
                </table>
            </div>

            <div class="section">
                <h2>Significant Food-Symptom Correlations</h2>
                ${generateCorrelationsHTML(correlations)}
            </div>

            <div class="section">
                <h2>Identified Trigger Patterns</h2>
                ${generateTriggersHTML(triggerPatterns)}
            </div>

            <div class="section">
                <h2>Symptom Timeline</h2>
                ${generateSymptomTimelineHTML(symptomEvents.sortedBy { it.timestamp })}
            </div>

            <div class="section">
                <h2>Recommendations</h2>
                ${generateRecommendationsHTML(correlations, triggerPatterns)}
            </div>
        </body>
        </html>
        """.trimIndent()
    }

    private fun generateFoodDiaryHTML(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        startDate: LocalDate,
        endDate: LocalDate
    ): String {
        val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
        val entriesByDate = foodEntries.groupBy {
            it.timestamp.atZone(java.time.ZoneId.systemDefault()).toLocalDate()
        }
        val symptomsByDate = symptomEvents.groupBy {
            it.timestamp.atZone(java.time.ZoneId.systemDefault()).toLocalDate()
        }

        return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <title>Food Diary Report</title>
            <style>
                body { font-family: Arial, sans-serif; margin: 20px; color: #333; }
                .header { text-align: center; border-bottom: 2px solid #4CAF50; padding-bottom: 20px; }
                .day-entry { margin: 20px 0; border: 1px solid #ddd; border-radius: 8px; padding: 15px; }
                .day-header { font-size: 18px; font-weight: bold; color: #4CAF50; margin-bottom: 10px; }
                .meal-section { margin: 15px 0; }
                .meal-header { font-weight: bold; color: #666; margin-bottom: 5px; }
                .food-item { margin: 5px 0; padding: 8px; background: #f8f9fa; border-radius: 4px; }
                .symptom-section { margin: 15px 0; padding: 10px; background: #fff3cd; border-radius: 4px; }
                .symptom-item { margin: 5px 0; }
                .severity-indicator { display: inline-block; width: 10px; height: 10px; border-radius: 50%; margin-right: 5px; }
                .severity-low { background-color: #28a745; }
                .severity-medium { background-color: #ffc107; }
                .severity-high { background-color: #dc3545; }
            </style>
        </head>
        <body>
            <div class="header">
                <h1>Food Diary</h1>
                <p>Period: ${startDate.format(dateFormatter)} - ${endDate.format(dateFormatter)}</p>
            </div>

            ${generateDailyEntriesHTML(entriesByDate, symptomsByDate)}
        </body>
        </html>
        """.trimIndent()
    }

    private fun generateMedicalReportHTML(
        correlations: List<CorrelationAnalysis>,
        triggerPatterns: List<TriggerPattern>,
        patientInfo: PatientInfo,
        startDate: LocalDate,
        endDate: LocalDate
    ): String {
        val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")

        return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <title>Medical Report - Food Symptom Analysis</title>
            <style>
                body { font-family: 'Times New Roman', serif; margin: 20px; color: #000; }
                .letterhead { text-align: center; border-bottom: 3px solid #000; padding-bottom: 20px; margin-bottom: 30px; }
                .patient-info { margin: 20px 0; }
                .clinical-section { margin: 30px 0; }
                .clinical-section h2 { color: #000; text-decoration: underline; }
                .findings-table { width: 100%; border-collapse: collapse; margin: 15px 0; }
                .findings-table th, .findings-table td { border: 1px solid #000; padding: 8px; }
                .findings-table th { background-color: #f0f0f0; }
                .clinical-note { font-style: italic; margin: 15px 0; }
                .recommendation { background: #f8f8f8; padding: 15px; margin: 15px 0; border-left: 4px solid #000; }
                .signature-section { margin-top: 50px; }
            </style>
        </head>
        <body>
            <div class="letterhead">
                <h1>CLINICAL ANALYSIS REPORT</h1>
                <h2>Food-Symptom Correlation Study</h2>
            </div>

            <div class="patient-info">
                <p><strong>Patient:</strong> ${patientInfo.name}</p>
                <p><strong>Date of Birth:</strong> ${patientInfo.dateOfBirth}</p>
                <p><strong>Analysis Period:</strong> ${startDate.format(dateFormatter)} - ${endDate.format(dateFormatter)}</p>
                <p><strong>Report Generated:</strong> ${LocalDate.now().format(dateFormatter)}</p>
            </div>

            <div class="clinical-section">
                <h2>CLINICAL FINDINGS</h2>
                ${generateClinicalFindingsHTML(correlations, triggerPatterns)}
            </div>

            <div class="clinical-section">
                <h2>STATISTICAL ANALYSIS</h2>
                ${generateStatisticalAnalysisHTML(correlations)}
            </div>

            <div class="clinical-section">
                <h2>CLINICAL RECOMMENDATIONS</h2>
                ${generateClinicalRecommendationsHTML(correlations, triggerPatterns)}
            </div>

            <div class="signature-section">
                <p>This report was generated by automated analysis of patient-reported food and symptom data.</p>
                <p><em>Please consult with a healthcare professional for medical interpretation.</em></p>
            </div>
        </body>
        </html>
        """.trimIndent()
    }

    private suspend fun convertHTMLToPDF(htmlContent: String, outputFile: File): File = withContext(Dispatchers.Main) {
        suspendCancellableCoroutine { continuation ->
            val webView = WebView(context)

            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    // Create print attributes
                    val printAttributes = PrintAttributes.Builder()
                        .setMediaSize(PrintAttributes.MediaSize.NA_LETTER)
                        .setResolution(PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                        .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                        .build()

                    // Create print adapter
                    val printAdapter = webView.createPrintDocumentAdapter("FoodDiaryReport")

                    try {
                        // Convert to PDF using print framework
                        val pfd = ParcelFileDescriptor.open(outputFile, ParcelFileDescriptor.MODE_CREATE or ParcelFileDescriptor.MODE_WRITE_ONLY)

                        printAdapter.onLayout(
                            null, printAttributes, null,
                            object : PrintDocumentAdapter.LayoutResultCallback() {
                                override fun onLayoutFinished(info: android.print.PrintDocumentInfo?, changed: Boolean) {
                                    printAdapter.onWrite(
                                        arrayOf(android.print.PageRange.ALL_PAGES),
                                        pfd,
                                        null,
                                        object : PrintDocumentAdapter.WriteResultCallback() {
                                            override fun onWriteFinished(pages: Array<out android.print.PageRange>?) {
                                                try {
                                                    pfd.close()
                                                    continuation.resume(outputFile)
                                                } catch (e: Exception) {
                                                    continuation.resumeWithException(e)
                                                }
                                            }

                                            override fun onWriteFailed(error: CharSequence?) {
                                                try {
                                                    pfd.close()
                                                } catch (ignored: Exception) {}
                                                continuation.resumeWithException(Exception("PDF write failed: $error"))
                                            }
                                        }
                                    )
                                }

                                override fun onLayoutFailed(error: CharSequence?) {
                                    continuation.resumeWithException(Exception("PDF layout failed: $error"))
                                }
                            },
                            null
                        )
                    } catch (e: Exception) {
                        continuation.resumeWithException(e)
                    }
                }
            }

            webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)
        }
    }

    // Helper methods for HTML generation
    private fun generateCorrelationsHTML(correlations: List<CorrelationAnalysis>): String {
        if (correlations.isEmpty()) return "<p>No significant correlations found.</p>"

        return correlations.take(10).joinToString("") { correlation ->
            val strengthClass = when {
                kotlin.math.abs(correlation.correlationCoefficient) >= 0.7 -> "correlation-strong"
                kotlin.math.abs(correlation.correlationCoefficient) >= 0.4 -> "correlation-moderate"
                else -> "correlation-weak"
            }

            """
            <div class="correlation-item $strengthClass">
                <h3>${correlation.foodName} → ${correlation.symptomType.name}</h3>
                <p><strong>Correlation Strength:</strong> ${String.format("%.3f", correlation.correlationCoefficient)}</p>
                <p><strong>Statistical Significance:</strong> ${if (correlation.isStatisticallySignificant) "Yes (p < 0.05)" else "No"}</p>
                <p><strong>Average Onset Time:</strong> ${correlation.averageTimeOffsetMinutes} minutes</p>
                <p><strong>Occurrences:</strong> ${correlation.occurrences}</p>
            </div>
            """
        }
    }

    private fun generateTriggersHTML(triggers: List<TriggerPattern>): String {
        if (triggers.isEmpty()) return "<p>No trigger patterns identified.</p>"

        return triggers.joinToString("") { trigger ->
            """
            <div class="correlation-item">
                <h3>${trigger.foodName}</h3>
                <p><strong>Confidence:</strong> ${String.format("%.1f", trigger.significance * 100)}%</p>
                <p><strong>Triggers:</strong> ${trigger.symptomType.name}</p>
                <p><strong>Recommendation:</strong> ${trigger.recommendedAction}</p>
            </div>
            """
        }
    }

    private fun generateSymptomTimelineHTML(symptoms: List<SymptomEvent>): String {
        return symptoms.take(20).joinToString("") { symptom ->
            val severityClass = when {
                symptom.severity >= 8 -> "severity-high"
                symptom.severity >= 4 -> "severity-medium"
                else -> "severity-low"
            }

            """
            <div class="timeline-item">
                <div class="timeline-date">${symptom.timestamp.atZone(java.time.ZoneId.systemDefault()).toLocalDate()}</div>
                <div class="timeline-content">
                    <span class="severity-indicator $severityClass"></span>
                    <strong>${symptom.symptomType.name}</strong> (Severity: ${symptom.severity}/10)
                    ${if (symptom.notes.isNotBlank()) " - ${symptom.notes}" else ""}
                </div>
            </div>
            """
        }
    }

    private fun generateRecommendationsHTML(correlations: List<CorrelationAnalysis>, triggers: List<TriggerPattern>): String {
        val recommendations = mutableListOf<String>()

        // Strong correlation recommendations
        correlations.filter {
            it.isStatisticallySignificant && kotlin.math.abs(it.correlationCoefficient) >= 0.6
        }.forEach { correlation ->
            recommendations.add("Consider eliminating or reducing ${correlation.foodName} to manage ${correlation.symptomType.name} symptoms.")
        }

        // Trigger pattern recommendations
        triggers.forEach { trigger ->
            when (trigger.recommendedAction) {
                "ELIMINATE" -> recommendations.add("Strongly consider eliminating ${trigger.foodName} from your diet.")
                "REDUCE" -> recommendations.add("Consider reducing consumption of ${trigger.foodName}.")
                "MONITOR" -> recommendations.add("Continue monitoring reactions to ${trigger.foodName}.")
            }
        }

        if (recommendations.isEmpty()) {
            recommendations.add("Continue tracking food and symptoms to identify patterns.")
            recommendations.add("Maintain a varied diet while monitoring symptom responses.")
        }

        return recommendations.joinToString("") { "<p>• $it</p>" }
    }

    private fun generateDailyEntriesHTML(
        entriesByDate: Map<LocalDate, List<FoodEntry>>,
        symptomsByDate: Map<LocalDate, List<SymptomEvent>>
    ): String {
        val allDates = (entriesByDate.keys + symptomsByDate.keys).sorted()
        val dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy")

        return allDates.joinToString("") { date ->
            val dayEntries = entriesByDate[date] ?: emptyList()
            val daySymptoms = symptomsByDate[date] ?: emptyList()

            """
            <div class="day-entry">
                <div class="day-header">${date.format(dateFormatter)}</div>

                ${if (dayEntries.isNotEmpty()) {
                    dayEntries.groupBy { it.mealType }.entries.joinToString("") { (mealType, meals) ->
                        """
                        <div class="meal-section">
                            <div class="meal-header">$mealType</div>
                            ${meals.joinToString("") { meal ->
                                """
                                <div class="food-item">
                                    <strong>${meal.foodName}</strong> - ${meal.portions} ${meal.portionUnit}
                                    ${if (meal.ingredients.isNotEmpty()) "<br><em>Ingredients: ${meal.ingredients.joinToString(", ")}</em>" else ""}
                                </div>
                                """
                            }}
                        </div>
                        """
                    }
                } else ""}

                ${if (daySymptoms.isNotEmpty()) {
                    """
                    <div class="symptom-section">
                        <strong>Symptoms:</strong>
                        ${daySymptoms.joinToString("") { symptom ->
                            val severityClass = when {
                                symptom.severity >= 8 -> "severity-high"
                                symptom.severity >= 4 -> "severity-medium"
                                else -> "severity-low"
                            }
                            """
                            <div class="symptom-item">
                                <span class="severity-indicator $severityClass"></span>
                                ${symptom.symptomType.name} (${symptom.severity}/10)
                                ${if (symptom.notes.isNotBlank()) " - ${symptom.notes}" else ""}
                            </div>
                            """
                        }}
                    </div>
                    """
                } else ""}
            </div>
            """
        }
    }

    private fun generateClinicalFindingsHTML(correlations: List<CorrelationAnalysis>, triggers: List<TriggerPattern>): String {
        return """
        <table class="findings-table">
            <thead>
                <tr><th>Finding</th><th>Statistical Evidence</th><th>Clinical Significance</th></tr>
            </thead>
            <tbody>
                ${correlations.filter { it.isStatisticallySignificant }.joinToString("") { correlation ->
                    """
                    <tr>
                        <td>${correlation.foodName} → ${correlation.symptomType.name}</td>
                        <td>r=${String.format("%.3f", correlation.correlationCoefficient)}, p<0.05</td>
                        <td>${if (kotlin.math.abs(correlation.correlationCoefficient) >= 0.7) "High" else "Moderate"}</td>
                    </tr>
                    """
                }}
            </tbody>
        </table>
        """
    }

    private fun generateStatisticalAnalysisHTML(correlations: List<CorrelationAnalysis>): String {
        val significantCount = correlations.count { it.isStatisticallySignificant }
        val strongCount = correlations.count { kotlin.math.abs(it.correlationCoefficient) >= 0.7 }

        return """
        <p class="clinical-note">
            Analysis identified $significantCount statistically significant correlations (p<0.05)
            from ${correlations.size} food-symptom pairs examined. Of these, $strongCount showed
            strong correlation coefficients (|r|≥0.7).
        </p>
        """
    }

    private fun generateClinicalRecommendationsHTML(correlations: List<CorrelationAnalysis>, triggers: List<TriggerPattern>): String {
        return """
        <div class="recommendation">
            <h3>Elimination Diet Protocol</h3>
            <p>Based on statistical analysis, consider systematic elimination of foods with
            correlation coefficients ≥0.6 and p<0.05.</p>
        </div>
        <div class="recommendation">
            <h3>Monitoring Protocol</h3>
            <p>Continue symptom tracking during elimination phases. Reintroduce foods
            individually with 72-hour monitoring periods.</p>
        </div>
        """
    }

    private fun getMostCommonSymptom(symptoms: List<SymptomEvent>): String {
        return symptoms.groupBy { it.symptomType }
            .maxByOrNull { it.value.size }?.key?.name ?: "None"
    }

    data class PatientInfo(
        val name: String,
        val dateOfBirth: String,
        val gender: String? = null,
        val medicalRecordNumber: String? = null
    )
}