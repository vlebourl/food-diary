package com.fooddiary.domain.analysis

import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.models.SymptomType
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeWindowAnalyzer @Inject constructor() {

    /**
     * Analyze optimal time windows for detecting food-symptom correlations
     */
    fun analyzeOptimalTimeWindows(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        foodName: String,
        symptomType: SymptomType,
        maxWindowHours: Int = 48
    ): TimeWindowAnalysisResult {

        val relevantFoodEntries = foodEntries.filter { it.foodName == foodName }
        val relevantSymptoms = symptomEvents.filter { it.symptomType == symptomType }

        val windowResults = mutableListOf<WindowResult>()

        // Test different window sizes from 1 to maxWindowHours
        for (windowHours in 1..maxWindowHours) {
            val windowMs = windowHours * 60 * 60 * 1000L
            var correlatedPairs = 0
            var falsePositives = 0
            val timeOffsets = mutableListOf<Long>()

            // Count correlations within this window
            for (foodEntry in relevantFoodEntries) {
                val symptomsInWindow = relevantSymptoms.filter { symptom ->
                    val timeDiff = symptom.timestamp.toEpochMilli() - foodEntry.timestamp.toEpochMilli()
                    timeDiff in 0..windowMs
                }

                if (symptomsInWindow.isNotEmpty()) {
                    correlatedPairs++
                    // Record time offsets for symptoms in this window
                    symptomsInWindow.forEach { symptom ->
                        timeOffsets.add(symptom.timestamp.toEpochMilli() - foodEntry.timestamp.toEpochMilli())
                    }
                } else {
                    // This food entry didn't correlate with symptoms in this window
                    falsePositives++
                }
            }

            val precision = if (correlatedPairs + falsePositives > 0) {
                correlatedPairs.toDouble() / (correlatedPairs + falsePositives)
            } else 0.0

            val avgTimeOffset = if (timeOffsets.isNotEmpty()) {
                timeOffsets.average() / (1000 * 60) // Convert to minutes
            } else 0.0

            windowResults.add(
                WindowResult(
                    windowSizeHours = windowHours,
                    correlatedPairs = correlatedPairs,
                    precision = precision,
                    averageTimeOffsetMinutes = avgTimeOffset,
                    standardDeviation = calculateStandardDeviation(timeOffsets.map { it / (1000 * 60) })
                )
            )
        }

        // Find optimal window (highest precision with reasonable correlation count)
        val optimalWindow = windowResults
            .filter { it.correlatedPairs >= 3 } // Minimum correlation requirement
            .maxByOrNull { it.precision }
            ?: windowResults.maxByOrNull { it.correlatedPairs }
            ?: WindowResult(24, 0, 0.0, 0.0, 0.0)

        return TimeWindowAnalysisResult(
            foodName = foodName,
            symptomType = symptomType,
            optimalWindowHours = optimalWindow.windowSizeHours,
            optimalPrecision = optimalWindow.precision,
            averageOnsetTimeMinutes = optimalWindow.averageTimeOffsetMinutes,
            onsetTimeVariability = optimalWindow.standardDeviation,
            allWindowResults = windowResults,
            recommendations = generateTimeWindowRecommendations(optimalWindow, windowResults)
        )
    }

    /**
     * Analyze eating patterns and timing correlations
     */
    fun analyzeEatingPatterns(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): EatingPatternAnalysis {

        val mealTimeDistribution = analyzeMealTimeDistribution(foodEntries)
        val symptomTimeDistribution = analyzeSymptomTimeDistribution(symptomEvents)
        val timeOfDayCorrelations = analyzeTimeOfDayCorrelations(foodEntries, symptomEvents)
        val eatingSpeedAnalysis = analyzeEatingSpeedCorrelations(foodEntries, symptomEvents)

        return EatingPatternAnalysis(
            mealTimeDistribution = mealTimeDistribution,
            symptomTimeDistribution = symptomTimeDistribution,
            timeOfDayCorrelations = timeOfDayCorrelations,
            eatingSpeedAnalysis = eatingSpeedAnalysis,
            recommendations = generateEatingPatternRecommendations(
                mealTimeDistribution, symptomTimeDistribution, timeOfDayCorrelations
            )
        )
    }

    /**
     * Identify trigger food timing patterns
     */
    fun analyzeTriggerTiming(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        confirmedTriggers: List<String>
    ): TriggerTimingAnalysis {

        val triggerPatterns = mutableMapOf<String, TriggerTimingPattern>()

        for (triggerFood in confirmedTriggers) {
            val foodEpisodes = foodEntries.filter { it.foodName == triggerFood }
            val timingData = mutableListOf<TriggerEpisode>()

            for (foodEntry in foodEpisodes) {
                val relatedSymptoms = symptomEvents.filter { symptom ->
                    val timeDiff = symptom.timestamp.toEpochMilli() - foodEntry.timestamp.toEpochMilli()
                    timeDiff in 0..(24 * 60 * 60 * 1000L) // 24-hour window
                }

                if (relatedSymptoms.isNotEmpty()) {
                    val firstSymptom = relatedSymptoms.minByOrNull { it.timestamp }!!
                    val onsetMinutes = (firstSymptom.timestamp.toEpochMilli() -
                                      foodEntry.timestamp.toEpochMilli()) / (1000 * 60)

                    timingData.add(
                        TriggerEpisode(
                            foodTimestamp = foodEntry.timestamp,
                            symptomOnsetMinutes = onsetMinutes,
                            maxSeverity = relatedSymptoms.maxOfOrNull { it.severity } ?: 0,
                            symptomDurationMinutes = relatedSymptoms.mapNotNull { it.durationMinutes }.maxOrNull() ?: 0,
                            mealType = foodEntry.mealType,
                            portions = foodEntry.portions
                        )
                    )
                }
            }

            if (timingData.isNotEmpty()) {
                triggerPatterns[triggerFood] = TriggerTimingPattern(
                    foodName = triggerFood,
                    episodes = timingData,
                    averageOnsetMinutes = timingData.map { it.symptomOnsetMinutes }.average(),
                    onsetRange = timingData.map { it.symptomOnsetMinutes }.let {
                        Pair(it.minOrNull() ?: 0.0, it.maxOrNull() ?: 0.0)
                    },
                    mostCommonOnsetWindow = calculateMostCommonOnsetWindow(timingData),
                    severityCorrelation = calculateSeverityPortionCorrelation(timingData)
                )
            }
        }

        return TriggerTimingAnalysis(
            triggerPatterns = triggerPatterns,
            overallPatterns = analyzeOverallTimingPatterns(triggerPatterns.values.toList()),
            recommendations = generateTriggerTimingRecommendations(triggerPatterns)
        )
    }

    /**
     * Analyze meal spacing and its effect on symptoms
     */
    fun analyzeMealSpacing(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): MealSpacingAnalysis {

        val sortedEntries = foodEntries.sortedBy { it.timestamp }
        val spacingData = mutableListOf<MealSpacingData>()

        for (i in 1 until sortedEntries.size) {
            val previousMeal = sortedEntries[i - 1]
            val currentMeal = sortedEntries[i]
            val spacingHours = ChronoUnit.HOURS.between(
                previousMeal.timestamp, currentMeal.timestamp
            ).toDouble()

            // Find symptoms between these meals
            val symptomsInBetween = symptomEvents.filter { symptom ->
                symptom.timestamp.isAfter(previousMeal.timestamp) &&
                symptom.timestamp.isBefore(currentMeal.timestamp)
            }

            // Find symptoms after current meal (within 4 hours)
            val symptomsAfter = symptomEvents.filter { symptom ->
                val timeDiff = symptom.timestamp.toEpochMilli() - currentMeal.timestamp.toEpochMilli()
                timeDiff in 0..(4 * 60 * 60 * 1000L)
            }

            spacingData.add(
                MealSpacingData(
                    spacingHours = spacingHours,
                    symptomsInBetween = symptomsInBetween.size,
                    symptomsAfter = symptomsAfter.size,
                    averageSeverityAfter = symptomsAfter.map { it.severity }.average().takeIf { !it.isNaN() } ?: 0.0,
                    previousMealType = previousMeal.mealType,
                    currentMealType = currentMeal.mealType
                )
            )
        }

        val optimalSpacingRanges = identifyOptimalSpacingRanges(spacingData)

        return MealSpacingAnalysis(
            spacingData = spacingData,
            averageSpacing = spacingData.map { it.spacingHours }.average(),
            optimalSpacingRanges = optimalSpacingRanges,
            tooCloseThreshold = calculateTooCloseThreshold(spacingData),
            tooFarThreshold = calculateTooFarThreshold(spacingData),
            recommendations = generateMealSpacingRecommendations(spacingData, optimalSpacingRanges)
        )
    }

    private fun analyzeMealTimeDistribution(foodEntries: List<FoodEntry>): Map<Int, Int> {
        return foodEntries.groupBy {
            it.timestamp.atZone(ZoneId.systemDefault()).hour
        }.mapValues { it.value.size }
    }

    private fun analyzeSymptomTimeDistribution(symptomEvents: List<SymptomEvent>): Map<Int, Int> {
        return symptomEvents.groupBy {
            it.timestamp.atZone(ZoneId.systemDefault()).hour
        }.mapValues { it.value.size }
    }

    private fun analyzeTimeOfDayCorrelations(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<TimeOfDayCorrelation> {
        val correlations = mutableListOf<TimeOfDayCorrelation>()

        for (hour in 0..23) {
            val mealsInHour = foodEntries.filter {
                it.timestamp.atZone(ZoneId.systemDefault()).hour == hour
            }

            val symptomsInFollowingHours = symptomEvents.filter { symptom ->
                val mealHour = symptom.timestamp.atZone(ZoneId.systemDefault()).hour
                mealHour in (hour + 1)..(hour + 4) % 24
            }

            val correlation = if (mealsInHour.isNotEmpty()) {
                symptomsInFollowingHours.size.toDouble() / mealsInHour.size
            } else 0.0

            correlations.add(
                TimeOfDayCorrelation(
                    hour = hour,
                    mealCount = mealsInHour.size,
                    symptomCount = symptomsInFollowingHours.size,
                    correlationRatio = correlation
                )
            )
        }

        return correlations
    }

    private fun analyzeEatingSpeedCorrelations(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): EatingSpeedCorrelation {
        val speedGroups = foodEntries.groupBy { it.eatingSpeed ?: "Unknown" }
        val correlations = mutableMapOf<String, Double>()

        for ((speed, entries) in speedGroups) {
            val symptomsAfterFastEating = entries.sumOf { entry ->
                symptomEvents.count { symptom ->
                    val timeDiff = symptom.timestamp.toEpochMilli() - entry.timestamp.toEpochMilli()
                    timeDiff in 0..(4 * 60 * 60 * 1000L) // 4 hours
                }
            }

            correlations[speed] = if (entries.isNotEmpty()) {
                symptomsAfterFastEating.toDouble() / entries.size
            } else 0.0
        }

        return EatingSpeedCorrelation(correlations)
    }

    private fun calculateStandardDeviation(values: List<Double>): Double {
        if (values.isEmpty()) return 0.0
        val mean = values.average()
        val variance = values.map { (it - mean) * (it - mean) }.average()
        return kotlin.math.sqrt(variance)
    }

    private fun calculateMostCommonOnsetWindow(episodes: List<TriggerEpisode>): String {
        val windows = episodes.map { episode ->
            when {
                episode.symptomOnsetMinutes < 60 -> "0-1h"
                episode.symptomOnsetMinutes < 120 -> "1-2h"
                episode.symptomOnsetMinutes < 240 -> "2-4h"
                episode.symptomOnsetMinutes < 480 -> "4-8h"
                else -> "8h+"
            }
        }
        return windows.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key ?: "Unknown"
    }

    private fun calculateSeverityPortionCorrelation(episodes: List<TriggerEpisode>): Double {
        if (episodes.size < 3) return 0.0

        val portions = episodes.map { it.portions }
        val severities = episodes.map { it.maxSeverity.toDouble() }

        val n = episodes.size
        val sumX = portions.sum()
        val sumY = severities.sum()
        val sumXY = portions.zip(severities) { x, y -> x * y }.sum()
        val sumX2 = portions.map { it * it }.sum()
        val sumY2 = severities.map { it * it }.sum()

        val numerator = n * sumXY - sumX * sumY
        val denominator = kotlin.math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY))

        return if (denominator == 0.0) 0.0 else numerator / denominator
    }

    private fun analyzeOverallTimingPatterns(patterns: List<TriggerTimingPattern>): OverallTimingPatterns {
        val allOnsetTimes = patterns.flatMap { pattern ->
            pattern.episodes.map { it.symptomOnsetMinutes }
        }

        return OverallTimingPatterns(
            averageOnsetMinutes = allOnsetTimes.average(),
            medianOnsetMinutes = allOnsetTimes.sorted()[allOnsetTimes.size / 2],
            mostCommonWindow = calculateMostCommonWindow(allOnsetTimes),
            variabilityScore = calculateStandardDeviation(allOnsetTimes)
        )
    }

    private fun calculateMostCommonWindow(onsetTimes: List<Double>): String {
        val windows = onsetTimes.map { time ->
            when {
                time < 30 -> "0-30min"
                time < 60 -> "30-60min"
                time < 120 -> "1-2h"
                time < 240 -> "2-4h"
                time < 480 -> "4-8h"
                else -> "8h+"
            }
        }
        return windows.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key ?: "Unknown"
    }

    private fun identifyOptimalSpacingRanges(spacingData: List<MealSpacingData>): List<SpacingRange> {
        val lowSymptomSpacing = spacingData.filter { it.symptomsAfter <= 1 }
        val groupedByRange = lowSymptomSpacing.groupBy { spacing ->
            when {
                spacing.spacingHours < 2 -> "< 2h"
                spacing.spacingHours < 4 -> "2-4h"
                spacing.spacingHours < 6 -> "4-6h"
                spacing.spacingHours < 8 -> "6-8h"
                else -> "8h+"
            }
        }

        return groupedByRange.map { (range, data) ->
            SpacingRange(
                range = range,
                averageSymptoms = data.map { it.symptomsAfter }.average(),
                sampleSize = data.size
            )
        }.sortedBy { it.averageSymptoms }
    }

    private fun calculateTooCloseThreshold(spacingData: List<MealSpacingData>): Double {
        val highSymptomSpacing = spacingData
            .filter { it.symptomsAfter >= 2 }
            .map { it.spacingHours }
        return highSymptomSpacing.minOrNull() ?: 1.0
    }

    private fun calculateTooFarThreshold(spacingData: List<MealSpacingData>): Double {
        val highSymptomSpacing = spacingData
            .filter { it.symptomsInBetween >= 1 }
            .map { it.spacingHours }
        return highSymptomSpacing.maxOrNull() ?: 8.0
    }

    private fun generateTimeWindowRecommendations(
        optimalWindow: WindowResult,
        allResults: List<WindowResult>
    ): List<String> {
        val recommendations = mutableListOf<String>()

        recommendations.add("Optimal tracking window: ${optimalWindow.windowSizeHours} hours")

        when {
            optimalWindow.averageTimeOffsetMinutes < 60 ->
                recommendations.add("Symptoms typically appear within 1 hour - monitor closely after eating")
            optimalWindow.averageTimeOffsetMinutes < 240 ->
                recommendations.add("Symptoms typically appear within 2-4 hours after eating")
            else ->
                recommendations.add("Delayed symptom onset (4+ hours) - consider other factors")
        }

        if (optimalWindow.standardDeviation > 120) {
            recommendations.add("High variability in symptom timing - individual responses may vary")
        }

        return recommendations
    }

    private fun generateEatingPatternRecommendations(
        mealTimes: Map<Int, Int>,
        symptomTimes: Map<Int, Int>,
        correlations: List<TimeOfDayCorrelation>
    ): List<String> {
        val recommendations = mutableListOf<String>()

        val highCorrelationHours = correlations.filter { it.correlationRatio > 1.5 }
        if (highCorrelationHours.isNotEmpty()) {
            recommendations.add("Consider avoiding meals during: ${highCorrelationHours.map { "${it.hour}:00" }.joinToString(", ")}")
        }

        val lowCorrelationHours = correlations.filter { it.correlationRatio < 0.5 && it.mealCount > 0 }
        if (lowCorrelationHours.isNotEmpty()) {
            recommendations.add("Better tolerated meal times: ${lowCorrelationHours.map { "${it.hour}:00" }.joinToString(", ")}")
        }

        return recommendations
    }

    private fun generateTriggerTimingRecommendations(
        patterns: Map<String, TriggerTimingPattern>
    ): List<String> {
        val recommendations = mutableListOf<String>()

        patterns.forEach { (food, pattern) ->
            recommendations.add("${food}: Expect symptoms ${pattern.averageOnsetMinutes.toInt()} min after eating (${pattern.mostCommonOnsetWindow})")

            if (pattern.severityCorrelation > 0.5) {
                recommendations.add("${food}: Smaller portions may reduce symptom severity")
            }
        }

        return recommendations
    }

    private fun generateMealSpacingRecommendations(
        spacingData: List<MealSpacingData>,
        optimalRanges: List<SpacingRange>
    ): List<String> {
        val recommendations = mutableListOf<String>()

        val bestRange = optimalRanges.minByOrNull { it.averageSymptoms }
        bestRange?.let {
            recommendations.add("Optimal meal spacing: ${it.range}")
        }

        val averageSpacing = spacingData.map { it.spacingHours }.average()
        if (averageSpacing < 3) {
            recommendations.add("Consider spacing meals at least 3-4 hours apart")
        }

        return recommendations
    }
}

// Data classes for time window analysis results
data class TimeWindowAnalysisResult(
    val foodName: String,
    val symptomType: SymptomType,
    val optimalWindowHours: Int,
    val optimalPrecision: Double,
    val averageOnsetTimeMinutes: Double,
    val onsetTimeVariability: Double,
    val allWindowResults: List<WindowResult>,
    val recommendations: List<String>
)

data class WindowResult(
    val windowSizeHours: Int,
    val correlatedPairs: Int,
    val precision: Double,
    val averageTimeOffsetMinutes: Double,
    val standardDeviation: Double
)

data class EatingPatternAnalysis(
    val mealTimeDistribution: Map<Int, Int>,
    val symptomTimeDistribution: Map<Int, Int>,
    val timeOfDayCorrelations: List<TimeOfDayCorrelation>,
    val eatingSpeedAnalysis: EatingSpeedCorrelation,
    val recommendations: List<String>
)

data class TimeOfDayCorrelation(
    val hour: Int,
    val mealCount: Int,
    val symptomCount: Int,
    val correlationRatio: Double
)

data class EatingSpeedCorrelation(
    val correlationsBySpeed: Map<String, Double>
)

data class TriggerTimingAnalysis(
    val triggerPatterns: Map<String, TriggerTimingPattern>,
    val overallPatterns: OverallTimingPatterns,
    val recommendations: List<String>
)

data class TriggerTimingPattern(
    val foodName: String,
    val episodes: List<TriggerEpisode>,
    val averageOnsetMinutes: Double,
    val onsetRange: Pair<Double, Double>,
    val mostCommonOnsetWindow: String,
    val severityCorrelation: Double
)

data class TriggerEpisode(
    val foodTimestamp: Instant,
    val symptomOnsetMinutes: Double,
    val maxSeverity: Int,
    val symptomDurationMinutes: Int,
    val mealType: String,
    val portions: Double
)

data class OverallTimingPatterns(
    val averageOnsetMinutes: Double,
    val medianOnsetMinutes: Double,
    val mostCommonWindow: String,
    val variabilityScore: Double
)

data class MealSpacingAnalysis(
    val spacingData: List<MealSpacingData>,
    val averageSpacing: Double,
    val optimalSpacingRanges: List<SpacingRange>,
    val tooCloseThreshold: Double,
    val tooFarThreshold: Double,
    val recommendations: List<String>
)

data class MealSpacingData(
    val spacingHours: Double,
    val symptomsInBetween: Int,
    val symptomsAfter: Int,
    val averageSeverityAfter: Double,
    val previousMealType: String,
    val currentMealType: String
)

data class SpacingRange(
    val range: String,
    val averageSymptoms: Double,
    val sampleSize: Int
)