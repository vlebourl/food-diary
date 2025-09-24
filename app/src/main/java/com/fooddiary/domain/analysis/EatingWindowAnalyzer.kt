package com.fooddiary.domain.analysis

import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.models.SymptomType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.*

@Singleton
class EatingWindowAnalyzer @Inject constructor() {

    suspend fun analyzeOptimalEatingWindows(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        analysisStartDate: LocalDate,
        analysisEndDate: LocalDate
    ): EatingWindowAnalysisResult = withContext(Dispatchers.Default) {

        val optimalWindows = identifyOptimalEatingWindows(foodEntries, symptomEvents)
        val eatingSpeedCorrelations = analyzeEatingSpeedCorrelations(foodEntries, symptomEvents)
        val mealTimingPatterns = analyzeMealTimingPatterns(foodEntries, symptomEvents)
        val fastingBenefits = analyzeFastingPeriods(foodEntries, symptomEvents)
        val circadianAlignment = analyzeCircadianAlignment(foodEntries, symptomEvents)

        EatingWindowAnalysisResult(
            optimalWindows = optimalWindows,
            eatingSpeedCorrelations = eatingSpeedCorrelations,
            mealTimingPatterns = mealTimingPatterns,
            fastingBenefits = fastingBenefits,
            circadianAlignment = circadianAlignment,
            analysisConfidence = calculateAnalysisConfidence(foodEntries, symptomEvents),
            recommendations = generateRecommendations(optimalWindows, eatingSpeedCorrelations, mealTimingPatterns)
        )
    }

    private fun identifyOptimalEatingWindows(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<OptimalEatingWindow> {
        val windows = mutableListOf<OptimalEatingWindow>()

        // Group entries by day
        val entriesByDay = foodEntries.groupBy { entry ->
            entry.timestamp.atZone(ZoneId.systemDefault()).toLocalDate()
        }

        val symptomsByDay = symptomEvents.groupBy { symptom ->
            symptom.timestamp.atZone(ZoneId.systemDefault()).toLocalDate()
        }

        // Analyze different eating window patterns
        val commonWindows = listOf(
            EatingWindowPattern("Early Bird", 6, 18), // 6 AM - 6 PM
            EatingWindowPattern("Standard", 7, 19),   // 7 AM - 7 PM
            EatingWindowPattern("Late Start", 9, 19), // 9 AM - 7 PM
            EatingWindowPattern("16:8 IF", 12, 20),   // 12 PM - 8 PM (16:8 Intermittent Fasting)
            EatingWindowPattern("14:10 IF", 10, 20),  // 10 AM - 8 PM (14:10 IF)
        )

        commonWindows.forEach { pattern ->
            val windowAnalysis = analyzeWindowPattern(
                pattern, entriesByDay, symptomsByDay
            )
            if (windowAnalysis.adherenceRate > 0.3) { // At least 30% adherence
                windows.add(windowAnalysis)
            }
        }

        return windows.sortedByDescending { it.benefitScore }
    }

    private fun analyzeWindowPattern(
        pattern: EatingWindowPattern,
        entriesByDay: Map<LocalDate, List<FoodEntry>>,
        symptomsByDay: Map<LocalDate, List<SymptomEvent>>
    ): OptimalEatingWindow {
        var adherentDays = 0
        var totalSymptomSeverity = 0.0
        var totalSymptomCount = 0
        val adherentDaysData = mutableListOf<DayAnalysis>()

        entriesByDay.forEach { (date, dayEntries) ->
            val eatingHours = dayEntries.map { entry ->
                entry.timestamp.atZone(ZoneId.systemDefault()).hour
            }

            val firstMeal = eatingHours.minOrNull() ?: continue
            val lastMeal = eatingHours.maxOrNull() ?: continue

            // Check if this day adheres to the pattern
            val adheres = firstMeal >= pattern.startHour && lastMeal <= pattern.endHour

            if (adheres) {
                adherentDays++

                // Calculate symptom metrics for adherent days
                val daySymptoms = symptomsByDay[date] ?: emptyList()
                val daySymptomSeverity = daySymptoms.map { it.severity }.average().takeIf { !it.isNaN() } ?: 0.0

                totalSymptomSeverity += daySymptomSeverity
                totalSymptomCount += daySymptoms.size

                adherentDaysData.add(DayAnalysis(
                    date = date,
                    firstMealHour = firstMeal,
                    lastMealHour = lastMeal,
                    eatingWindowHours = lastMeal - firstMeal,
                    symptomCount = daySymptoms.size,
                    averageSeverity = daySymptomSeverity
                ))
            }
        }

        val adherenceRate = adherentDays.toFloat() / entriesByDay.size
        val averageSymptomSeverity = if (adherentDays > 0) totalSymptomSeverity / adherentDays else 0.0
        val averageSymptomCount = if (adherentDays > 0) totalSymptomCount.toFloat() / adherentDays else 0f

        // Calculate benefit score (lower is better for symptoms)
        val benefitScore = calculateBenefitScore(
            adherenceRate, averageSymptomSeverity, averageSymptomCount, adherentDaysData
        )

        return OptimalEatingWindow(
            pattern = pattern,
            adherenceRate = adherenceRate,
            averageSymptomSeverity = averageSymptomSeverity,
            averageSymptomFrequency = averageSymptomCount,
            benefitScore = benefitScore,
            adherentDays = adherentDays,
            totalAnalyzedDays = entriesByDay.size,
            windowConsistency = calculateWindowConsistency(adherentDaysData),
            recommendations = generateWindowRecommendations(pattern, adherentDaysData)
        )
    }

    private fun analyzeEatingSpeedCorrelations(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<EatingSpeedCorrelation> {
        val correlations = mutableListOf<EatingSpeedCorrelation>()

        // Estimate eating speed based on portion size and meal complexity
        val mealsWithSpeed = foodEntries.map { entry ->
            val estimatedSpeed = estimateEatingSpeed(entry)
            MealWithSpeed(entry, estimatedSpeed)
        }

        // Group by speed categories
        val speedCategories = listOf(
            SpeedCategory("Very Fast", 0f, 3f),
            SpeedCategory("Fast", 3f, 8f),
            SpeedCategory("Moderate", 8f, 15f),
            SpeedCategory("Slow", 15f, 30f)
        )

        speedCategories.forEach { category ->
            val categoryMeals = mealsWithSpeed.filter { meal ->
                meal.estimatedEatingTime >= category.minMinutes && meal.estimatedEatingTime < category.maxMinutes
            }

            if (categoryMeals.size >= 5) { // Minimum sample size
                val relatedSymptoms = findSymptomsAfterMeals(categoryMeals.map { it.entry }, symptomEvents)

                val correlation = EatingSpeedCorrelation(
                    speedCategory = category.name,
                    averageEatingTime = categoryMeals.map { it.estimatedEatingTime }.average(),
                    mealCount = categoryMeals.size,
                    relatedSymptoms = relatedSymptoms.size,
                    averageSymptomSeverity = relatedSymptoms.map { it.severity }.average().takeIf { !it.isNaN() } ?: 0.0,
                    averageOnsetTime = calculateAverageOnsetTime(categoryMeals.map { it.entry }, relatedSymptoms),
                    correlationStrength = calculateSpeedCorrelationStrength(categoryMeals, relatedSymptoms),
                    confidence = min(1.0, categoryMeals.size.toDouble() / 20.0) // Higher confidence with more data
                )

                correlations.add(correlation)
            }
        }

        return correlations.sortedByDescending { it.correlationStrength }
    }

    private fun analyzeMealTimingPatterns(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<MealTimingPattern> {
        val patterns = mutableListOf<MealTimingPattern>()

        // Analyze by hour of day
        val entriesByHour = foodEntries.groupBy { entry ->
            entry.timestamp.atZone(ZoneId.systemDefault()).hour
        }

        (0..23).forEach { hour ->
            val hourEntries = entriesByHour[hour] ?: emptyList()

            if (hourEntries.size >= 3) { // Minimum occurrences
                val relatedSymptoms = findSymptomsAfterMeals(hourEntries, symptomEvents)

                val pattern = MealTimingPattern(
                    timeOfDay = formatHour(hour),
                    hour = hour,
                    mealCount = hourEntries.size,
                    averageSymptomSeverity = relatedSymptoms.map { it.severity }.average().takeIf { !it.isNaN() } ?: 0.0,
                    symptomFrequency = relatedSymptoms.size.toFloat() / hourEntries.size,
                    mostCommonSymptoms = findMostCommonSymptoms(relatedSymptoms),
                    riskLevel = calculateTimingRiskLevel(hourEntries, relatedSymptoms),
                    recommendations = generateTimingRecommendations(hour, relatedSymptoms)
                )

                patterns.add(pattern)
            }
        }

        return patterns.sortedBy { it.hour }
    }

    private fun analyzeFastingPeriods(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<FastingPeriodAnalysis> {
        val fastingAnalyses = mutableListOf<FastingPeriodAnalysis>()

        // Group entries by day and analyze gaps between meals
        val entriesByDay = foodEntries.groupBy { entry ->
            entry.timestamp.atZone(ZoneId.systemDefault()).toLocalDate()
        }

        val symptomsByDay = symptomEvents.groupBy { symptom ->
            symptom.timestamp.atZone(ZoneId.systemDefault()).toLocalDate()
        }

        entriesByDay.forEach { (date, dayEntries) ->
            val sortedEntries = dayEntries.sortedBy { it.timestamp }

            // Find overnight fasting period (last meal to first meal next day)
            val lastMealTime = sortedEntries.last().timestamp
            val nextDay = date.plusDays(1)
            val nextDayEntries = entriesByDay[nextDay]?.sortedBy { it.timestamp }

            nextDayEntries?.firstOrNull()?.let { firstNextMeal ->
                val fastingHours = Duration.between(lastMealTime, firstNextMeal.timestamp).toHours()

                if (fastingHours >= 8) { // Minimum fasting period to analyze
                    val daySymptoms = symptomsByDay[date] ?: emptyList()
                    val nextDaySymptoms = symptomsByDay[nextDay] ?: emptyList()

                    val analysis = FastingPeriodAnalysis(
                        date = date,
                        fastingHours = fastingHours,
                        lastMealTime = lastMealTime,
                        firstMealTime = firstNextMeal.timestamp,
                        symptomsDuringFasting = daySymptoms.filter { symptom ->
                            symptom.timestamp.isAfter(lastMealTime) &&
                            symptom.timestamp.isBefore(firstNextMeal.timestamp)
                        }.size,
                        symptomsAfterBreaking = nextDaySymptoms.filter { symptom ->
                            Duration.between(firstNextMeal.timestamp, symptom.timestamp).toHours() <= 4
                        }.size,
                        fastingBenefit = calculateFastingBenefit(daySymptoms, nextDaySymptoms, fastingHours)
                    )

                    fastingAnalyses.add(analysis)
                }
            }
        }

        return fastingAnalyses.sortedByDescending { it.fastingBenefit }
    }

    private fun analyzeCircadianAlignment(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): CircadianAlignmentAnalysis {
        // Analyze alignment with natural circadian rhythms
        val idealEatingWindows = mapOf(
            "Morning" to 6..10,    // Cortisol peak, good digestion
            "Midday" to 11..14,    // Peak digestive fire
            "Evening" to 15..18,   // Still good digestion
            "Late" to 19..22       // Declining digestion
        )

        val alignmentScores = mutableMapOf<String, Double>()
        val symptomsByWindow = mutableMapOf<String, List<SymptomEvent>>()

        idealEatingWindows.forEach { (windowName, hourRange) ->
            val windowEntries = foodEntries.filter { entry ->
                val hour = entry.timestamp.atZone(ZoneId.systemDefault()).hour
                hour in hourRange
            }

            val windowSymptoms = findSymptomsAfterMeals(windowEntries, symptomEvents)
            symptomsByWindow[windowName] = windowSymptoms

            // Calculate alignment score (higher is better)
            val score = if (windowEntries.isNotEmpty()) {
                val symptomRate = windowSymptoms.size.toDouble() / windowEntries.size
                val severityPenalty = windowSymptoms.map { it.severity }.average().takeIf { !it.isNaN() } ?: 0.0

                // Score decreases with more symptoms and higher severity
                max(0.0, 10.0 - symptomRate * 5.0 - severityPenalty)
            } else {
                0.0
            }

            alignmentScores[windowName] = score
        }

        return CircadianAlignmentAnalysis(
            alignmentScores = alignmentScores,
            bestWindow = alignmentScores.maxByOrNull { it.value }?.key ?: "Unknown",
            worstWindow = alignmentScores.minByOrNull { it.value }?.key ?: "Unknown",
            overallAlignment = alignmentScores.values.average(),
            recommendations = generateCircadianRecommendations(alignmentScores, symptomsByWindow)
        )
    }

    // Helper methods

    private fun estimateEatingSpeed(entry: FoodEntry): Float {
        // Estimate eating time based on food complexity and portion size
        val baseTime = 5f // minutes
        val portionMultiplier = entry.portions.toFloat() * 2f
        val complexityMultiplier = entry.ingredients.size * 1.5f

        val mealTypeMultiplier = when (entry.mealType.lowercase()) {
            "snack" -> 0.5f
            "breakfast" -> 1.0f
            "lunch" -> 1.2f
            "dinner" -> 1.5f
            else -> 1.0f
        }

        return baseTime + portionMultiplier + complexityMultiplier * mealTypeMultiplier
    }

    private fun findSymptomsAfterMeals(
        meals: List<FoodEntry>,
        symptoms: List<SymptomEvent>,
        timeWindowHours: Long = 4
    ): List<SymptomEvent> {
        return symptoms.filter { symptom ->
            meals.any { meal ->
                val timeDiff = Duration.between(meal.timestamp, symptom.timestamp).toHours()
                timeDiff in 0..timeWindowHours
            }
        }
    }

    private fun calculateBenefitScore(
        adherenceRate: Float,
        averageSymptomSeverity: Double,
        averageSymptomCount: Float,
        adherentDays: List<DayAnalysis>
    ): Double {
        // Higher adherence rate is better, lower symptoms are better
        val adherenceScore = adherenceRate * 40.0
        val severityScore = max(0.0, 30.0 - averageSymptomSeverity * 3.0)
        val frequencyScore = max(0.0, 20.0 - averageSymptomCount * 5.0)
        val consistencyScore = if (adherentDays.isNotEmpty()) {
            val consistency = calculateWindowConsistency(adherentDays)
            consistency * 10.0
        } else 0.0

        return adherenceScore + severityScore + frequencyScore + consistencyScore
    }

    private fun calculateWindowConsistency(adherentDays: List<DayAnalysis>): Double {
        if (adherentDays.size <= 1) return 0.0

        val windowSizes = adherentDays.map { it.eatingWindowHours }
        val meanWindow = windowSizes.average()
        val variance = windowSizes.map { (it - meanWindow).pow(2) }.average()
        val stdDev = sqrt(variance)

        // Consistency is inverse of standard deviation (normalized)
        return max(0.0, 1.0 - (stdDev / meanWindow))
    }

    private fun calculateSpeedCorrelationStrength(
        meals: List<MealWithSpeed>,
        symptoms: List<SymptomEvent>
    ): Double {
        if (meals.isEmpty()) return 0.0

        val symptomRate = symptoms.size.toDouble() / meals.size
        val avgSeverity = symptoms.map { it.severity }.average().takeIf { !it.isNaN() } ?: 0.0

        // Correlation strength based on symptom occurrence and severity
        return min(1.0, symptomRate * 0.5 + (avgSeverity / 10.0) * 0.5)
    }

    private fun calculateAverageOnsetTime(
        meals: List<FoodEntry>,
        symptoms: List<SymptomEvent>
    ): Double {
        val onsetTimes = mutableListOf<Long>()

        meals.forEach { meal ->
            symptoms.forEach { symptom ->
                val timeDiff = Duration.between(meal.timestamp, symptom.timestamp).toMinutes()
                if (timeDiff in 0..240) { // Within 4 hours
                    onsetTimes.add(timeDiff)
                }
            }
        }

        return if (onsetTimes.isNotEmpty()) onsetTimes.average() else 0.0
    }

    private fun findMostCommonSymptoms(symptoms: List<SymptomEvent>): List<SymptomType> {
        return symptoms.groupingBy { it.symptomType }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
            .take(3)
            .map { it.first }
    }

    private fun calculateTimingRiskLevel(
        entries: List<FoodEntry>,
        symptoms: List<SymptomEvent>
    ): String {
        val symptomRate = if (entries.isNotEmpty()) symptoms.size.toFloat() / entries.size else 0f
        val avgSeverity = symptoms.map { it.severity }.average().takeIf { !it.isNaN() } ?: 0.0

        return when {
            symptomRate > 0.7 || avgSeverity > 7 -> "High"
            symptomRate > 0.4 || avgSeverity > 5 -> "Moderate"
            symptomRate > 0.2 || avgSeverity > 3 -> "Low"
            else -> "Very Low"
        }
    }

    private fun calculateFastingBenefit(
        daySymptoms: List<SymptomEvent>,
        nextDaySymptoms: List<SymptomEvent>,
        fastingHours: Long
    ): Double {
        val daySeverity = daySymptoms.map { it.severity }.average().takeIf { !it.isNaN() } ?: 0.0
        val nextDaySeverity = nextDaySymptoms.map { it.severity }.average().takeIf { !it.isNaN() } ?: 0.0

        // Benefit calculation: longer fasting + symptom improvement
        val fastingBonus = min(5.0, fastingHours.toDouble() / 3.0) // Up to 5 points for 15+ hour fast
        val symptomImprovement = max(0.0, daySeverity - nextDaySeverity)

        return fastingBonus + symptomImprovement
    }

    private fun calculateAnalysisConfidence(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): Double {
        val dataPoints = foodEntries.size + symptomEvents.size
        val timeSpan = if (foodEntries.isNotEmpty()) {
            Duration.between(
                foodEntries.minOf { it.timestamp },
                foodEntries.maxOf { it.timestamp }
            ).toDays()
        } else 0L

        // Confidence based on data quantity and time span
        val dataScore = min(0.5, dataPoints.toDouble() / 100.0)
        val timeScore = min(0.5, timeSpan.toDouble() / 30.0) // 30 days for max score

        return dataScore + timeScore
    }

    private fun generateRecommendations(
        optimalWindows: List<OptimalEatingWindow>,
        speedCorrelations: List<EatingSpeedCorrelation>,
        timingPatterns: List<MealTimingPattern>
    ): List<String> {
        val recommendations = mutableListOf<String>()

        // Window recommendations
        optimalWindows.firstOrNull()?.let { bestWindow ->
            recommendations.add("Consider eating within a ${bestWindow.pattern.name} window (${bestWindow.pattern.startHour}:00-${bestWindow.pattern.endHour}:00) for optimal digestion")
        }

        // Speed recommendations
        speedCorrelations.find { it.correlationStrength < 0.3 }?.let { bestSpeed ->
            recommendations.add("Eating at a ${bestSpeed.speedCategory.lowercase()} pace may help reduce symptoms")
        }

        // Timing recommendations
        timingPatterns.filter { it.riskLevel == "High" }.forEach { pattern ->
            recommendations.add("Avoid eating around ${pattern.timeOfDay} - this timing is associated with increased symptoms")
        }

        if (recommendations.isEmpty()) {
            recommendations.add("Continue current eating patterns - no significant timing-related triggers identified")
        }

        return recommendations
    }

    private fun generateWindowRecommendations(
        pattern: EatingWindowPattern,
        adherentDays: List<DayAnalysis>
    ): List<String> {
        val recommendations = mutableListOf<String>()

        if (adherentDays.isNotEmpty()) {
            val avgSeverity = adherentDays.map { it.averageSeverity }.average()
            if (avgSeverity < 4.0) {
                recommendations.add("This eating window appears beneficial for your symptoms")
            }

            val consistency = calculateWindowConsistency(adherentDays)
            if (consistency < 0.7) {
                recommendations.add("Try to maintain more consistent meal timing within your eating window")
            }
        }

        return recommendations
    }

    private fun generateTimingRecommendations(hour: Int, symptoms: List<SymptomEvent>): List<String> {
        val recommendations = mutableListOf<String>()

        if (symptoms.isNotEmpty()) {
            val avgSeverity = symptoms.map { it.severity }.average()
            if (avgSeverity > 6.0) {
                recommendations.add("Consider avoiding meals at ${formatHour(hour)}")
            } else if (avgSeverity > 4.0) {
                recommendations.add("Be cautious with portion size and food choices at ${formatHour(hour)}")
            }
        }

        return recommendations
    }

    private fun generateCircadianRecommendations(
        alignmentScores: Map<String, Double>,
        symptomsByWindow: Map<String, List<SymptomEvent>>
    ): List<String> {
        val recommendations = mutableListOf<String>()

        val bestWindow = alignmentScores.maxByOrNull { it.value }?.key
        val worstWindow = alignmentScores.minByOrNull { it.value }?.key

        bestWindow?.let {
            recommendations.add("Your best eating window appears to be $it - consider having larger meals during this time")
        }

        worstWindow?.let { window ->
            val symptoms = symptomsByWindow[window] ?: emptyList()
            if (symptoms.isNotEmpty()) {
                recommendations.add("Consider reducing meal size or avoiding complex foods during $window time")
            }
        }

        return recommendations
    }

    private fun formatHour(hour: Int): String {
        return when {
            hour == 0 -> "12:00 AM"
            hour < 12 -> "${hour}:00 AM"
            hour == 12 -> "12:00 PM"
            else -> "${hour - 12}:00 PM"
        }
    }
}

// Data classes for eating window analysis

data class EatingWindowAnalysisResult(
    val optimalWindows: List<OptimalEatingWindow>,
    val eatingSpeedCorrelations: List<EatingSpeedCorrelation>,
    val mealTimingPatterns: List<MealTimingPattern>,
    val fastingBenefits: List<FastingPeriodAnalysis>,
    val circadianAlignment: CircadianAlignmentAnalysis,
    val analysisConfidence: Double,
    val recommendations: List<String>
)

data class OptimalEatingWindow(
    val pattern: EatingWindowPattern,
    val adherenceRate: Float,
    val averageSymptomSeverity: Double,
    val averageSymptomFrequency: Float,
    val benefitScore: Double,
    val adherentDays: Int,
    val totalAnalyzedDays: Int,
    val windowConsistency: Double,
    val recommendations: List<String>
)

data class EatingWindowPattern(
    val name: String,
    val startHour: Int,
    val endHour: Int
) {
    val windowLength: Int get() = endHour - startHour
}

data class EatingSpeedCorrelation(
    val speedCategory: String,
    val averageEatingTime: Double,
    val mealCount: Int,
    val relatedSymptoms: Int,
    val averageSymptomSeverity: Double,
    val averageOnsetTime: Double,
    val correlationStrength: Double,
    val confidence: Double
)

data class MealTimingPattern(
    val timeOfDay: String,
    val hour: Int,
    val mealCount: Int,
    val averageSymptomSeverity: Double,
    val symptomFrequency: Float,
    val mostCommonSymptoms: List<SymptomType>,
    val riskLevel: String,
    val recommendations: List<String>
)

data class FastingPeriodAnalysis(
    val date: LocalDate,
    val fastingHours: Long,
    val lastMealTime: Instant,
    val firstMealTime: Instant,
    val symptomsDuringFasting: Int,
    val symptomsAfterBreaking: Int,
    val fastingBenefit: Double
)

data class CircadianAlignmentAnalysis(
    val alignmentScores: Map<String, Double>,
    val bestWindow: String,
    val worstWindow: String,
    val overallAlignment: Double,
    val recommendations: List<String>
)

private data class DayAnalysis(
    val date: LocalDate,
    val firstMealHour: Int,
    val lastMealHour: Int,
    val eatingWindowHours: Int,
    val symptomCount: Int,
    val averageSeverity: Double
)

private data class MealWithSpeed(
    val entry: FoodEntry,
    val estimatedEatingTime: Float
)

private data class SpeedCategory(
    val name: String,
    val minMinutes: Float,
    val maxMinutes: Float
)