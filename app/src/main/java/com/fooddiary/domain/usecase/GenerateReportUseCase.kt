package com.fooddiary.domain.usecase

import com.fooddiary.data.database.entities.MedicalReport
import com.fooddiary.data.repository.*
import com.fooddiary.presentation.viewmodel.ReportData
import com.fooddiary.presentation.viewmodel.ReportType
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GenerateReportUseCase @Inject constructor(
    private val foodEntryRepository: FoodEntryRepository,
    private val symptomEventRepository: SymptomEventRepository,
    private val triggerPatternRepository: TriggerPatternRepository,
    private val environmentalContextRepository: EnvironmentalContextRepository,
    private val medicalReportRepository: MedicalReportRepository
) {
    suspend operator fun invoke(
        startDate: LocalDate,
        endDate: LocalDate,
        reportType: ReportType
    ): Result<ReportData> {
        return try {
            val startInstant = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
            val endInstant = endDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant()

            // Gather data for the report
            val foodEntries = foodEntryRepository.getByDateRange(startInstant, endInstant).first()
            val symptomEvents = symptomEventRepository.getByDateRange(startInstant, endInstant).first()
            val significantPatterns = triggerPatternRepository.getStatisticallySignificant().first()
            val environmentalContexts = environmentalContextRepository.getByDateRange(startDate, endDate).first()

            // Generate report content based on type
            val detailedAnalysis = when (reportType) {
                ReportType.COMPREHENSIVE -> generateComprehensiveReport(
                    foodEntries, symptomEvents, significantPatterns, environmentalContexts
                )
                ReportType.FOOD_PATTERNS -> generateFoodPatternsReport(
                    foodEntries, significantPatterns
                )
                ReportType.SYMPTOM_SUMMARY -> generateSymptomSummaryReport(
                    symptomEvents, significantPatterns
                )
                ReportType.WEEKLY_OVERVIEW -> generateWeeklyOverviewReport(
                    startDate, endDate, foodEntries, symptomEvents
                )
                ReportType.MEDICAL_SUMMARY -> generateMedicalSummaryReport(
                    foodEntries, symptomEvents, significantPatterns, environmentalContexts
                )
            }

            val reportData = ReportData(
                timeRange = "${startDate.format(DateTimeFormatter.ISO_LOCAL_DATE)} to ${endDate.format(DateTimeFormatter.ISO_LOCAL_DATE)}",
                generatedDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
                totalFoodEntries = foodEntries.size,
                totalSymptoms = symptomEvents.size,
                significantPatterns = significantPatterns,
                reportType = reportType,
                detailedAnalysis = detailedAnalysis
            )

            // Save medical report if it's a medical summary
            if (reportType == ReportType.MEDICAL_SUMMARY) {
                val medicalReport = MedicalReport(
                    generatedDate = LocalDate.now(),
                    startDate = startDate,
                    endDate = endDate,
                    reportType = reportType.name,
                    reportContent = detailedAnalysis,
                    significantPatternsCount = significantPatterns.size,
                    totalSymptomEvents = symptomEvents.size,
                    totalFoodEntries = foodEntries.size
                )
                medicalReportRepository.insert(medicalReport)
            }

            Result.success(reportData)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun generateComprehensiveReport(
        foodEntries: List<com.fooddiary.data.database.entities.FoodEntry>,
        symptomEvents: List<com.fooddiary.data.database.entities.SymptomEvent>,
        patterns: List<com.fooddiary.data.database.entities.TriggerPattern>,
        contexts: List<com.fooddiary.data.database.entities.EnvironmentalContext>
    ): String {
        return buildString {
            appendLine("COMPREHENSIVE FOOD DIARY ANALYSIS")
            appendLine("=" .repeat(50))
            appendLine()

            // Summary Statistics
            appendLine("SUMMARY STATISTICS")
            appendLine("-".repeat(30))
            appendLine("Total Food Entries: ${foodEntries.size}")
            appendLine("Total Symptom Events: ${symptomEvents.size}")
            appendLine("Unique Foods Consumed: ${foodEntries.map { it.foodName }.distinct().size}")
            appendLine("Significant Patterns Identified: ${patterns.size}")
            appendLine()

            // Symptom Frequency
            appendLine("SYMPTOM FREQUENCY")
            appendLine("-".repeat(30))
            val symptomFrequency = symptomEvents.groupingBy { it.symptomType }.eachCount()
            symptomFrequency.entries.sortedByDescending { it.value }.forEach { (type, count) ->
                appendLine("${type.displayName}: $count occurrences")
            }
            appendLine()

            // Average Severity by Symptom
            appendLine("AVERAGE SEVERITY BY SYMPTOM")
            appendLine("-".repeat(30))
            symptomEvents.groupBy { it.symptomType }.forEach { (type, events) ->
                val avgSeverity = events.map { it.severity }.average()
                appendLine("${type.displayName}: ${"%.1f".format(avgSeverity)}/10")
            }
            appendLine()

            // Significant Food-Symptom Correlations
            if (patterns.isNotEmpty()) {
                appendLine("SIGNIFICANT FOOD-SYMPTOM CORRELATIONS")
                appendLine("-".repeat(30))
                patterns.sortedByDescending { it.correlationStrength }.take(10).forEach { pattern ->
                    appendLine("${pattern.foodName} → ${pattern.symptomType.displayName}")
                    appendLine("  Correlation: ${(pattern.correlationStrength * 100).toInt()}%")
                    appendLine("  Occurrences: ${pattern.occurrences}")
                    appendLine("  Avg. time to onset: ${pattern.averageTimeOffsetMinutes} minutes")
                    appendLine()
                }
            }

            // Environmental Factors
            if (contexts.isNotEmpty()) {
                appendLine("ENVIRONMENTAL FACTORS")
                appendLine("-".repeat(30))
                val avgStress = contexts.mapNotNull { it.stressLevel }.average()
                val avgSleep = contexts.mapNotNull { it.sleepHours }.average()
                appendLine("Average Stress Level: ${"%.1f".format(avgStress)}/10")
                appendLine("Average Sleep Hours: ${"%.1f".format(avgSleep)} hours")
                appendLine()
            }

            // Recommendations
            appendLine("RECOMMENDATIONS")
            appendLine("-".repeat(30))
            if (patterns.isNotEmpty()) {
                appendLine("Consider eliminating or reducing intake of:")
                patterns.filter { it.correlationStrength >= 0.7f }
                    .map { it.foodName }
                    .distinct()
                    .take(5)
                    .forEach { food ->
                        appendLine("• $food")
                    }
            }
            appendLine()
            appendLine("Note: Consult with a healthcare provider before making significant dietary changes.")
        }
    }

    private fun generateFoodPatternsReport(
        foodEntries: List<com.fooddiary.data.database.entities.FoodEntry>,
        patterns: List<com.fooddiary.data.database.entities.TriggerPattern>
    ): String {
        return buildString {
            appendLine("FOOD PATTERNS ANALYSIS")
            appendLine("=".repeat(50))
            appendLine()

            // Most consumed foods
            appendLine("MOST FREQUENTLY CONSUMED FOODS")
            appendLine("-".repeat(30))
            foodEntries.groupingBy { it.foodName }
                .eachCount()
                .entries
                .sortedByDescending { it.value }
                .take(10)
                .forEach { (food, count) ->
                    appendLine("$food: $count times")
                }
            appendLine()

            // Meal type distribution
            appendLine("MEAL TYPE DISTRIBUTION")
            appendLine("-".repeat(30))
            foodEntries.groupingBy { it.mealType }
                .eachCount()
                .forEach { (type, count) ->
                    appendLine("$type: $count entries")
                }
            appendLine()

            // Problem foods
            if (patterns.isNotEmpty()) {
                appendLine("IDENTIFIED TRIGGER FOODS")
                appendLine("-".repeat(30))
                patterns.groupBy { it.foodName }
                    .mapValues { (_, foodPatterns) ->
                        foodPatterns.map { it.correlationStrength }.average()
                    }
                    .entries
                    .sortedByDescending { it.value }
                    .forEach { (food, avgCorrelation) ->
                        val risk = when {
                            avgCorrelation >= 0.8 -> "HIGH RISK"
                            avgCorrelation >= 0.6 -> "MODERATE RISK"
                            else -> "LOW RISK"
                        }
                        appendLine("$food: $risk (${(avgCorrelation * 100).toInt()}% correlation)")
                    }
            }
        }
    }

    private fun generateSymptomSummaryReport(
        symptomEvents: List<com.fooddiary.data.database.entities.SymptomEvent>,
        patterns: List<com.fooddiary.data.database.entities.TriggerPattern>
    ): String {
        return buildString {
            appendLine("SYMPTOM SUMMARY REPORT")
            appendLine("=".repeat(50))
            appendLine()

            appendLine("SYMPTOM STATISTICS")
            appendLine("-".repeat(30))
            appendLine("Total Symptom Events: ${symptomEvents.size}")
            appendLine("Average Severity: ${"%.1f".format(symptomEvents.map { it.severity }.average())}/10")
            appendLine()

            // Symptom breakdown
            appendLine("SYMPTOM BREAKDOWN")
            appendLine("-".repeat(30))
            symptomEvents.groupBy { it.symptomType }.forEach { (type, events) ->
                appendLine("${type.displayName}:")
                appendLine("  Count: ${events.size}")
                appendLine("  Avg. Severity: ${"%.1f".format(events.map { it.severity }.average())}/10")
                val withDuration = events.filter { it.durationMinutes != null }
                if (withDuration.isNotEmpty()) {
                    appendLine("  Avg. Duration: ${withDuration.mapNotNull { it.durationMinutes }.average().toInt()} minutes")
                }
                appendLine()
            }

            // Bristol Scale analysis for bowel symptoms
            val bristolEvents = symptomEvents.filter { it.bristolScale != null }
            if (bristolEvents.isNotEmpty()) {
                appendLine("BRISTOL STOOL CHART ANALYSIS")
                appendLine("-".repeat(30))
                bristolEvents.groupingBy { it.bristolScale!! }
                    .eachCount()
                    .entries
                    .sortedBy { it.key }
                    .forEach { (scale, count) ->
                        appendLine("Type $scale: $count occurrences")
                    }
            }
        }
    }

    private fun generateWeeklyOverviewReport(
        startDate: LocalDate,
        endDate: LocalDate,
        foodEntries: List<com.fooddiary.data.database.entities.FoodEntry>,
        symptomEvents: List<com.fooddiary.data.database.entities.SymptomEvent>
    ): String {
        return buildString {
            appendLine("WEEKLY OVERVIEW REPORT")
            appendLine("=".repeat(50))
            appendLine()

            var currentDate = startDate
            while (currentDate <= endDate) {
                val weekStart = currentDate
                val weekEnd = minOf(currentDate.plusDays(6), endDate)

                val weekStartInstant = weekStart.atStartOfDay(ZoneId.systemDefault()).toInstant()
                val weekEndInstant = weekEnd.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()

                val weekFoods = foodEntries.filter {
                    it.timestamp >= weekStartInstant && it.timestamp < weekEndInstant
                }
                val weekSymptoms = symptomEvents.filter {
                    it.timestamp >= weekStartInstant && it.timestamp < weekEndInstant
                }

                appendLine("Week: ${weekStart.format(DateTimeFormatter.ofPattern("MMM dd"))} - ${weekEnd.format(DateTimeFormatter.ofPattern("MMM dd"))}")
                appendLine("-".repeat(30))
                appendLine("Food Entries: ${weekFoods.size}")
                appendLine("Symptom Events: ${weekSymptoms.size}")
                if (weekSymptoms.isNotEmpty()) {
                    appendLine("Avg. Symptom Severity: ${"%.1f".format(weekSymptoms.map { it.severity }.average())}/10")
                }
                appendLine()

                currentDate = currentDate.plusWeeks(1)
            }
        }
    }

    private fun generateMedicalSummaryReport(
        foodEntries: List<com.fooddiary.data.database.entities.FoodEntry>,
        symptomEvents: List<com.fooddiary.data.database.entities.SymptomEvent>,
        patterns: List<com.fooddiary.data.database.entities.TriggerPattern>,
        contexts: List<com.fooddiary.data.database.entities.EnvironmentalContext>
    ): String {
        return buildString {
            appendLine("MEDICAL SUMMARY FOR HEALTHCARE PROVIDER")
            appendLine("=".repeat(50))
            appendLine()

            appendLine("PATIENT TRACKING SUMMARY")
            appendLine("-".repeat(30))
            appendLine("Tracking Period: ${foodEntries.size} days with food entries")
            appendLine("Total Food Entries: ${foodEntries.size}")
            appendLine("Total Symptom Events: ${symptomEvents.size}")
            appendLine()

            appendLine("PRIMARY SYMPTOMS")
            appendLine("-".repeat(30))
            symptomEvents.groupingBy { it.symptomType }
                .eachCount()
                .entries
                .sortedByDescending { it.value }
                .take(5)
                .forEach { (type, count) ->
                    val avgSeverity = symptomEvents
                        .filter { it.symptomType == type }
                        .map { it.severity }
                        .average()
                    appendLine("${type.displayName}: $count events (avg. severity: ${"%.1f".format(avgSeverity)}/10)")
                }
            appendLine()

            appendLine("STATISTICALLY SIGNIFICANT FOOD TRIGGERS")
            appendLine("-".repeat(30))
            patterns.filter { it.isStatisticallySignificant }
                .sortedByDescending { it.correlationStrength }
                .take(10)
                .forEach { pattern ->
                    appendLine("${pattern.foodName} → ${pattern.symptomType.displayName}")
                    appendLine("  Statistical Significance: p < 0.05")
                    appendLine("  Correlation Strength: ${(pattern.correlationStrength * 100).toInt()}%")
                    appendLine("  Sample Size: ${pattern.occurrences} occurrences")
                    appendLine()
                }

            if (contexts.isNotEmpty()) {
                appendLine("RELEVANT ENVIRONMENTAL FACTORS")
                appendLine("-".repeat(30))
                val avgStress = contexts.mapNotNull { it.stressLevel }.average()
                val avgSleep = contexts.mapNotNull { it.sleepHours }.average()
                val menstruationDays = contexts.count { it.isMenstruating == true }

                appendLine("Average Stress Level: ${"%.1f".format(avgStress)}/10")
                appendLine("Average Sleep Duration: ${"%.1f".format(avgSleep)} hours")
                if (menstruationDays > 0) {
                    appendLine("Days with Menstruation: $menstruationDays")
                }
            }

            appendLine()
            appendLine("CLINICAL NOTES")
            appendLine("-".repeat(30))
            appendLine("This report is generated from patient-tracked data.")
            appendLine("Statistical correlations suggest potential food triggers but require clinical validation.")
            appendLine("Consider IBS diagnostic criteria and additional testing as appropriate.")
        }
    }
}