package com.fooddiary.domain.analysis

import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.models.SymptomType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.*

@Singleton
class PatternLearningEngine @Inject constructor() {

    suspend fun analyzeEatingPatterns(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        analysisStartDate: LocalDate,
        analysisEndDate: LocalDate
    ): PatternAnalysisResult = withContext(Dispatchers.Default) {

        val habits = identifyEatingHabits(foodEntries)
        val weeklyPatterns = analyzeWeeklyPatterns(foodEntries, symptomEvents)
        val seasonalPatterns = analyzeSeasonalPatterns(foodEntries, symptomEvents)
        val dailyRhythms = analyzeDailyRhythms(foodEntries, symptomEvents)
        val adaptiveTriggers = identifyAdaptiveTriggers(foodEntries, symptomEvents)
        val behavioralPatterns = analyzeBehavioralPatterns(foodEntries, symptomEvents)

        PatternAnalysisResult(
            eatingHabits = habits,
            weeklyPatterns = weeklyPatterns,
            seasonalPatterns = seasonalPatterns,
            dailyRhythms = dailyRhythms,
            adaptiveTriggers = adaptiveTriggers,
            behavioralPatterns = behavioralPatterns,
            analysisPeriod = ChronoUnit.DAYS.between(analysisStartDate, analysisEndDate).toInt(),
            confidenceScore = calculateOverallConfidence(habits, weeklyPatterns, seasonalPatterns)
        )
    }

    private fun identifyEatingHabits(foodEntries: List<FoodEntry>): List<EatingHabit> {
        val habits = mutableListOf<EatingHabit>()

        // Meal timing patterns
        val mealTimes = foodEntries.groupBy { it.mealType }
            .mapValues { (_, entries) ->
                entries.map {
                    it.timestamp.atZone(java.time.ZoneId.systemDefault()).hour
                }.groupingBy { it }.eachCount()
            }

        mealTimes.forEach { (mealType, hourCounts) ->
            val mostCommonHour = hourCounts.maxByOrNull { it.value }?.key
            val consistency = hourCounts.values.maxOrNull()?.toDouble() ?: 0.0 / hourCounts.values.sum()

            if (mostCommonHour != null && consistency > 0.3) {
                habits.add(EatingHabit(
                    type = EatingHabitType.MEAL_TIMING,
                    description = "$mealType typically consumed at ${mostCommonHour}:00",
                    frequency = consistency.toFloat(),
                    confidence = min(1.0f, (consistency * 2).toFloat()),
                    mealType = mealType,
                    averageTime = mostCommonHour
                ))
            }
        }

        // Food preferences
        val foodFrequency = foodEntries.groupingBy { it.foodName }.eachCount()
        val totalEntries = foodEntries.size

        foodFrequency.forEach { (foodName, count) ->
            val frequency = count.toDouble() / totalEntries
            if (frequency > 0.05) { // Appears in >5% of entries
                habits.add(EatingHabit(
                    type = EatingHabitType.FOOD_PREFERENCE,
                    description = "Frequently consumes $foodName",
                    frequency = frequency.toFloat(),
                    confidence = min(1.0f, (frequency * 3).toFloat()),
                    foodName = foodName
                ))
            }
        }

        // Portion size patterns
        val portionPatterns = foodEntries.groupBy { it.foodName }
            .mapValues { (_, entries) ->
                entries.map { it.portions }.average()
            }

        portionPatterns.forEach { (foodName, avgPortion) ->
            if (avgPortion > 2.0) {
                habits.add(EatingHabit(
                    type = EatingHabitType.PORTION_SIZE,
                    description = "Tends to eat large portions of $foodName (avg: ${String.format("%.1f", avgPortion)})",
                    frequency = 1.0f,
                    confidence = 0.7f,
                    foodName = foodName,
                    averagePortions = avgPortion
                ))
            }
        }

        return habits.sortedByDescending { it.confidence }
    }

    private fun analyzeWeeklyPatterns(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<WeeklyPattern> {
        val patterns = mutableListOf<WeeklyPattern>()

        // Group entries by day of week
        val foodByDay = foodEntries.groupBy {
            it.timestamp.atZone(java.time.ZoneId.systemDefault()).dayOfWeek
        }

        val symptomsByDay = symptomEvents.groupBy {
            it.timestamp.atZone(java.time.ZoneId.systemDefault()).dayOfWeek
        }

        DayOfWeek.values().forEach { dayOfWeek ->
            val dayFoodEntries = foodByDay[dayOfWeek] ?: emptyList()
            val daySymptoms = symptomsByDay[dayOfWeek] ?: emptyList()

            // Analyze food patterns for this day
            val commonFoods = dayFoodEntries.groupingBy { it.foodName }
                .eachCount()
                .filter { it.value > 1 }

            commonFoods.forEach { (foodName, count) ->
                val totalEntriesForDay = dayFoodEntries.size
                if (totalEntriesForDay > 0) {
                    val frequency = count.toDouble() / totalEntriesForDay
                    if (frequency > 0.3) {
                        patterns.add(WeeklyPattern(
                            dayOfWeek = dayOfWeek,
                            patternType = WeeklyPatternType.FOOD_CONSUMPTION,
                            description = "$foodName commonly consumed on ${dayOfWeek.name}",
                            frequency = frequency.toFloat(),
                            confidence = min(1.0f, (frequency * 2).toFloat()),
                            foodName = foodName
                        ))
                    }
                }
            }

            // Analyze symptom patterns for this day
            val symptomSeverity = daySymptoms.map { it.severity }.takeIf { it.isNotEmpty() }?.average()
            if (symptomSeverity != null && symptomSeverity > 5.0) {
                patterns.add(WeeklyPattern(
                    dayOfWeek = dayOfWeek,
                    patternType = WeeklyPatternType.SYMPTOM_FREQUENCY,
                    description = "Higher symptom severity on ${dayOfWeek.name} (avg: ${String.format("%.1f", symptomSeverity)})",
                    frequency = daySymptoms.size.toFloat() / symptomEvents.size,
                    confidence = min(1.0f, (symptomSeverity / 10.0).toFloat()),
                    averageSeverity = symptomSeverity
                ))
            }
        }

        return patterns.sortedByDescending { it.confidence }
    }

    private fun analyzeSeasonalPatterns(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<SeasonalPattern> {
        val patterns = mutableListOf<SeasonalPattern>()

        // Group by month to detect seasonal patterns
        val foodByMonth = foodEntries.groupBy {
            it.timestamp.atZone(java.time.ZoneId.systemDefault()).monthValue
        }

        val symptomsByMonth = symptomEvents.groupBy {
            it.timestamp.atZone(java.time.ZoneId.systemDefault()).monthValue
        }

        // Analyze seasonal food preferences
        foodByMonth.forEach { (month, entries) ->
            val monthFoods = entries.groupingBy { it.foodName }.eachCount()
            val totalForMonth = entries.size

            monthFoods.forEach { (foodName, count) ->
                val monthlyFrequency = count.toDouble() / totalForMonth
                val overallFrequency = foodEntries.count { it.foodName == foodName }.toDouble() / foodEntries.size

                if (monthlyFrequency > overallFrequency * 1.5) { // 50% higher than average
                    patterns.add(SeasonalPattern(
                        month = month,
                        season = getSeason(month),
                        patternType = SeasonalPatternType.FOOD_PREFERENCE,
                        description = "$foodName consumption increases in ${getMonthName(month)}",
                        relativeIncrease = (monthlyFrequency / overallFrequency).toFloat(),
                        confidence = min(1.0f, (monthlyFrequency * 2).toFloat()),
                        foodName = foodName
                    ))
                }
            }
        }

        // Analyze seasonal symptom patterns
        symptomsByMonth.forEach { (month, symptoms) ->
            val avgSeverity = symptoms.map { it.severity }.average()
            val overallAvgSeverity = symptomEvents.map { it.severity }.average()

            if (avgSeverity > overallAvgSeverity * 1.3) { // 30% higher than average
                patterns.add(SeasonalPattern(
                    month = month,
                    season = getSeason(month),
                    patternType = SeasonalPatternType.SYMPTOM_SEVERITY,
                    description = "Increased symptom severity in ${getMonthName(month)}",
                    relativeIncrease = (avgSeverity / overallAvgSeverity).toFloat(),
                    confidence = min(1.0f, (symptoms.size.toFloat() / 10)),
                    averageSeverity = avgSeverity
                ))
            }
        }

        return patterns.sortedByDescending { it.confidence }
    }

    private fun analyzeDailyRhythms(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<DailyRhythm> {
        val rhythms = mutableListOf<DailyRhythm>()

        // Analyze eating schedule patterns
        val mealTimings = foodEntries.groupBy { it.mealType }
            .mapValues { (_, entries) ->
                entries.map {
                    it.timestamp.atZone(java.time.ZoneId.systemDefault()).hour
                }
            }

        mealTimings.forEach { (mealType, hours) ->
            val avgHour = hours.average()
            val stdDev = calculateStandardDeviation(hours.map { it.toDouble() })
            val consistency = 1.0 / (1.0 + stdDev) // Lower std dev = higher consistency

            rhythms.add(DailyRhythm(
                rhythmType = DailyRhythmType.MEAL_TIMING,
                description = "$mealType typically at ${String.format("%.1f", avgHour)}:00",
                averageTime = avgHour.toInt(),
                consistency = consistency.toFloat(),
                mealType = mealType,
                standardDeviation = stdDev
            ))
        }

        // Analyze symptom onset rhythms
        val symptomHours = symptomEvents.map {
            it.timestamp.atZone(java.time.ZoneId.systemDefault()).hour
        }

        if (symptomHours.isNotEmpty()) {
            val symptomsByHour = symptomHours.groupingBy { it }.eachCount()
            val peakHour = symptomsByHour.maxByOrNull { it.value }?.key

            if (peakHour != null) {
                val peakCount = symptomsByHour[peakHour] ?: 0
                val totalSymptoms = symptomEvents.size
                val peakPercentage = peakCount.toDouble() / totalSymptoms

                if (peakPercentage > 0.2) { // More than 20% of symptoms occur at this hour
                    rhythms.add(DailyRhythm(
                        rhythmType = DailyRhythmType.SYMPTOM_ONSET,
                        description = "Symptoms commonly occur around ${peakHour}:00",
                        averageTime = peakHour,
                        consistency = peakPercentage.toFloat(),
                        peakHour = peakHour,
                        peakPercentage = peakPercentage.toFloat()
                    ))
                }
            }
        }

        return rhythms.sortedByDescending { it.consistency }
    }

    private fun identifyAdaptiveTriggers(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<AdaptiveTrigger> {
        val triggers = mutableListOf<AdaptiveTrigger>()

        // Analyze food combinations that trigger symptoms
        val foodCombinations = identifyFoodCombinations(foodEntries)

        foodCombinations.forEach { combination ->
            val relatedSymptoms = findSymptomsAfterFoodCombination(combination, symptomEvents)

            if (relatedSymptoms.isNotEmpty()) {
                val avgSeverity = relatedSymptoms.map { it.severity }.average()
                val avgOnset = relatedSymptoms.map {
                    ChronoUnit.MINUTES.between(combination.timestamp, it.timestamp)
                }.average()

                if (avgSeverity > 5.0) { // Only consider moderate+ symptoms
                    triggers.add(AdaptiveTrigger(
                        triggerType = AdaptiveTriggerType.FOOD_COMBINATION,
                        description = "Combination of ${combination.foods.joinToString(" + ")} may trigger symptoms",
                        foods = combination.foods,
                        averageOnsetMinutes = avgOnset.toInt(),
                        averageSeverity = avgSeverity,
                        occurrences = relatedSymptoms.size,
                        confidence = calculateTriggerConfidence(relatedSymptoms.size, avgSeverity),
                        learningScore = 0.8f // High learning score for combination triggers
                    ))
                }
            }
        }

        // Analyze contextual triggers (time-based, environment-based)
        val contextualTriggers = identifyContextualTriggers(foodEntries, symptomEvents)
        triggers.addAll(contextualTriggers)

        return triggers.sortedByDescending { it.confidence }
    }

    private fun analyzeBehavioralPatterns(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<BehavioralPattern> {
        val patterns = mutableListOf<BehavioralPattern>()

        // Analyze eating speed patterns (estimated from portion sizes and timing)
        val mealDurations = estimateMealDurations(foodEntries)

        mealDurations.forEach { (mealType, avgDuration) ->
            if (avgDuration < 15) { // Fast eating
                val fastMealSymptoms = findSymptomsAfterFastEating(foodEntries, symptomEvents, mealType)

                if (fastMealSymptoms.isNotEmpty()) {
                    patterns.add(BehavioralPattern(
                        patternType = BehavioralPatternType.EATING_SPEED,
                        description = "Fast $mealType eating (avg ${avgDuration}min) may correlate with symptoms",
                        frequency = fastMealSymptoms.size.toFloat() / symptomEvents.size,
                        confidence = min(1.0f, fastMealSymptoms.size.toFloat() / 10),
                        mealType = mealType,
                        averageDuration = avgDuration
                    ))
                }
            }
        }

        // Analyze stress eating patterns (estimated from irregular timing)
        val stressEatingPatterns = identifyStressEatingPatterns(foodEntries, symptomEvents)
        patterns.addAll(stressEatingPatterns)

        return patterns.sortedByDescending { it.confidence }
    }

    // Helper methods
    private fun getSeason(month: Int): String {
        return when (month) {
            12, 1, 2 -> "Winter"
            3, 4, 5 -> "Spring"
            6, 7, 8 -> "Summer"
            9, 10, 11 -> "Fall"
            else -> "Unknown"
        }
    }

    private fun getMonthName(month: Int): String {
        val months = arrayOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
        return if (month in 1..12) months[month - 1] else "Unknown"
    }

    private fun calculateStandardDeviation(values: List<Double>): Double {
        val mean = values.average()
        val variance = values.map { (it - mean).pow(2) }.average()
        return sqrt(variance)
    }

    private fun calculateOverallConfidence(
        habits: List<EatingHabit>,
        weeklyPatterns: List<WeeklyPattern>,
        seasonalPatterns: List<SeasonalPattern>
    ): Float {
        val habitConfidence = habits.map { it.confidence }.takeIf { it.isNotEmpty() }?.average() ?: 0.0
        val weeklyConfidence = weeklyPatterns.map { it.confidence }.takeIf { it.isNotEmpty() }?.average() ?: 0.0
        val seasonalConfidence = seasonalPatterns.map { it.confidence }.takeIf { it.isNotEmpty() }?.average() ?: 0.0

        return ((habitConfidence + weeklyConfidence + seasonalConfidence) / 3.0).toFloat()
    }

    private fun identifyFoodCombinations(foodEntries: List<FoodEntry>): List<FoodCombination> {
        // Group entries by meals (same day, close time)
        val combinations = mutableListOf<FoodCombination>()

        val sortedEntries = foodEntries.sortedBy { it.timestamp }

        for (i in sortedEntries.indices) {
            val entry = sortedEntries[i]
            val nearbyEntries = sortedEntries.filter { other ->
                val timeDiff = abs(ChronoUnit.MINUTES.between(entry.timestamp, other.timestamp))
                timeDiff <= 30 && other != entry // Within 30 minutes
            }

            if (nearbyEntries.isNotEmpty()) {
                val allFoods = (listOf(entry) + nearbyEntries).map { it.foodName }.distinct()
                if (allFoods.size > 1) {
                    combinations.add(FoodCombination(
                        foods = allFoods,
                        timestamp = entry.timestamp,
                        mealType = entry.mealType
                    ))
                }
            }
        }

        return combinations.distinctBy { it.foods.sorted() }
    }

    private fun findSymptomsAfterFoodCombination(
        combination: FoodCombination,
        symptoms: List<SymptomEvent>
    ): List<SymptomEvent> {
        return symptoms.filter { symptom ->
            val timeDiff = ChronoUnit.HOURS.between(combination.timestamp, symptom.timestamp)
            timeDiff in 0..24 // Within 24 hours after eating
        }
    }

    private fun calculateTriggerConfidence(occurrences: Int, averageSeverity: Double): Float {
        val frequencyScore = min(1.0, occurrences.toDouble() / 5.0) // More occurrences = higher confidence
        val severityScore = min(1.0, averageSeverity / 10.0) // Higher severity = higher confidence
        return ((frequencyScore + severityScore) / 2.0).toFloat()
    }

    private fun identifyContextualTriggers(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<AdaptiveTrigger> {
        // Placeholder for contextual trigger analysis
        // Could analyze patterns like "eating late at night", "weekend overeating", etc.
        return emptyList()
    }

    private fun estimateMealDurations(foodEntries: List<FoodEntry>): Map<String, Int> {
        // Estimate meal duration based on meal complexity and portion size
        return foodEntries.groupBy { it.mealType }
            .mapValues { (_, entries) ->
                entries.map { entry ->
                    val baseTime = 10 // Base eating time
                    val portionMultiplier = (entry.portions * 3).toInt()
                    val ingredientMultiplier = entry.ingredients.size * 2
                    baseTime + portionMultiplier + ingredientMultiplier
                }.average().toInt()
            }
    }

    private fun findSymptomsAfterFastEating(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        mealType: String
    ): List<SymptomEvent> {
        val fastMeals = foodEntries.filter { it.mealType == mealType && it.portions > 2.0 }

        return symptomEvents.filter { symptom ->
            fastMeals.any { meal ->
                val timeDiff = ChronoUnit.HOURS.between(meal.timestamp, symptom.timestamp)
                timeDiff in 0..4 // Within 4 hours of fast eating
            }
        }
    }

    private fun identifyStressEatingPatterns(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<BehavioralPattern> {
        // Placeholder for stress eating pattern analysis
        // Could analyze patterns like irregular timing, comfort foods, etc.
        return emptyList()
    }
}

// Data classes for pattern analysis results

data class PatternAnalysisResult(
    val eatingHabits: List<EatingHabit>,
    val weeklyPatterns: List<WeeklyPattern>,
    val seasonalPatterns: List<SeasonalPattern>,
    val dailyRhythms: List<DailyRhythm>,
    val adaptiveTriggers: List<AdaptiveTrigger>,
    val behavioralPatterns: List<BehavioralPattern>,
    val analysisPeriod: Int,
    val confidenceScore: Float
)

data class EatingHabit(
    val type: EatingHabitType,
    val description: String,
    val frequency: Float,
    val confidence: Float,
    val mealType: String? = null,
    val foodName: String? = null,
    val averageTime: Int? = null,
    val averagePortions: Double? = null
)

data class WeeklyPattern(
    val dayOfWeek: DayOfWeek,
    val patternType: WeeklyPatternType,
    val description: String,
    val frequency: Float,
    val confidence: Float,
    val foodName: String? = null,
    val averageSeverity: Double? = null
)

data class SeasonalPattern(
    val month: Int,
    val season: String,
    val patternType: SeasonalPatternType,
    val description: String,
    val relativeIncrease: Float,
    val confidence: Float,
    val foodName: String? = null,
    val averageSeverity: Double? = null
)

data class DailyRhythm(
    val rhythmType: DailyRhythmType,
    val description: String,
    val averageTime: Int,
    val consistency: Float,
    val mealType: String? = null,
    val peakHour: Int? = null,
    val peakPercentage: Float? = null,
    val standardDeviation: Double? = null
)

data class AdaptiveTrigger(
    val triggerType: AdaptiveTriggerType,
    val description: String,
    val foods: List<String>,
    val averageOnsetMinutes: Int,
    val averageSeverity: Double,
    val occurrences: Int,
    val confidence: Float,
    val learningScore: Float
)

data class BehavioralPattern(
    val patternType: BehavioralPatternType,
    val description: String,
    val frequency: Float,
    val confidence: Float,
    val mealType: String? = null,
    val averageDuration: Int? = null
)

data class FoodCombination(
    val foods: List<String>,
    val timestamp: java.time.Instant,
    val mealType: String
)

// Enums for pattern types
enum class EatingHabitType {
    MEAL_TIMING, FOOD_PREFERENCE, PORTION_SIZE, INGREDIENT_PREFERENCE
}

enum class WeeklyPatternType {
    FOOD_CONSUMPTION, SYMPTOM_FREQUENCY, MEAL_TIMING, PORTION_VARIATION
}

enum class SeasonalPatternType {
    FOOD_PREFERENCE, SYMPTOM_SEVERITY, MEAL_FREQUENCY, TRIGGER_SENSITIVITY
}

enum class DailyRhythmType {
    MEAL_TIMING, SYMPTOM_ONSET, HUNGER_PATTERNS, DIGESTIVE_RHYTHM
}

enum class AdaptiveTriggerType {
    FOOD_COMBINATION, TIMING_TRIGGER, STRESS_TRIGGER, ENVIRONMENTAL_TRIGGER
}

enum class BehavioralPatternType {
    EATING_SPEED, STRESS_EATING, SOCIAL_EATING, EMOTIONAL_EATING
}