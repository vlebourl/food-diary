package com.fooddiary.data.analytics

import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.CorrelationPattern
import com.fooddiary.data.models.*
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Generates actionable insights from food diary data analysis
 */
@Singleton
class InsightGenerator @Inject constructor() {

    /**
     * Generate comprehensive insights from all data sources
     */
    fun generateInsights(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        correlations: List<CorrelationPattern>,
        patterns: List<TemporalPattern>
    ): List<HealthInsight> {
        val insights = mutableListOf<HealthInsight>()

        // Trigger food insights
        insights.addAll(generateTriggerFoodInsights(correlations))

        // Temporal pattern insights
        insights.addAll(generateTemporalInsights(patterns, symptomEvents))

        // Severity trend insights
        insights.addAll(generateSeverityInsights(symptomEvents))

        // Dietary pattern insights
        insights.addAll(generateDietaryInsights(foodEntries, symptomEvents))

        // Improvement insights
        insights.addAll(generateImprovementInsights(symptomEvents))

        return insights.sortedByDescending { it.priority.ordinal }
    }

    /**
     * Generate personalized recommendations
     */
    fun generateRecommendations(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        correlations: List<CorrelationPattern>
    ): List<HealthRecommendation> {
        val recommendations = mutableListOf<HealthRecommendation>()

        // Food elimination recommendations
        recommendations.addAll(generateEliminationRecommendations(correlations))

        // Dietary balance recommendations
        recommendations.addAll(generateDietaryBalanceRecommendations(foodEntries))

        // Lifestyle recommendations
        recommendations.addAll(generateLifestyleRecommendations(symptomEvents))

        // Monitoring recommendations
        recommendations.addAll(generateMonitoringRecommendations(foodEntries, symptomEvents))

        return recommendations.sortedByDescending { it.urgency.ordinal }
    }

    /**
     * Detect clusters of related symptoms or foods
     */
    fun detectClusters(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<DataCluster> {
        val clusters = mutableListOf<DataCluster>()

        // Symptom clusters
        clusters.addAll(detectSymptomClusters(symptomEvents))

        // Food combination clusters
        clusters.addAll(detectFoodClusters(foodEntries, symptomEvents))

        return clusters.sortedByDescending { it.strength }
    }

    // Private helper methods

    private fun generateTriggerFoodInsights(correlations: List<CorrelationPattern>): List<HealthInsight> {
        val insights = mutableListOf<HealthInsight>()

        val strongCorrelations = correlations.filter {
            it.correlationStrength > 0.7f && it.occurrenceCount >= 3
        }

        for (correlation in strongCorrelations.take(3)) {
            insights.add(
                HealthInsight(
                    type = InsightType.STRONG_CORRELATION,
                    title = "High Risk Food Identified",
                    description = "Strong correlation detected between food and symptoms with ${(correlation.correlationStrength * 100).toInt()}% confidence. This food consistently triggers symptoms within ${correlation.timeOffsetHours} hours.",
                    priority = InsightPriority.HIGH,
                    confidence = correlation.correlationStrength,
                    actionable = true,
                    relatedData = mapOf(
                        "foodId" to correlation.foodId.toString(),
                        "symptomId" to correlation.symptomId.toString(),
                        "occurrences" to correlation.occurrenceCount.toString()
                    ),
                    recommendations = listOf(
                        "Consider eliminating this food temporarily",
                        "Monitor symptoms when consuming this food",
                        "Try smaller portions if elimination isn't possible"
                    )
                )
            )
        }

        return insights
    }

    private fun generateTemporalInsights(
        patterns: List<TemporalPattern>,
        symptomEvents: List<SymptomEvent>
    ): List<HealthInsight> {
        val insights = mutableListOf<HealthInsight>()

        for (pattern in patterns.filter { it.confidence > 0.6f }) {
            insights.add(
                HealthInsight(
                    type = InsightType.FREQUENCY_PATTERN,
                    title = "Time Pattern Detected",
                    description = pattern.description + ". Average severity during this time is ${String.format("%.1f", pattern.averageSeverity)}.",
                    priority = if (pattern.confidence > 0.8f) InsightPriority.HIGH else InsightPriority.MEDIUM,
                    confidence = pattern.confidence,
                    actionable = true,
                    relatedData = mapOf(
                        "patternType" to pattern.type.toString(),
                        "occurrences" to pattern.occurrences.toString(),
                        "averageSeverity" to pattern.averageSeverity.toString()
                    ),
                    recommendations = generateTemporalRecommendations(pattern)
                )
            )
        }

        return insights
    }

    private fun generateSeverityInsights(symptomEvents: List<SymptomEvent>): List<HealthInsight> {
        val insights = mutableListOf<HealthInsight>()

        if (symptomEvents.size < 10) return insights

        val recentEvents = symptomEvents.filter {
            ChronoUnit.DAYS.between(it.timestamp, Instant.now()) <= 30
        }.sortedBy { it.timestamp }

        if (recentEvents.size < 5) return insights

        val firstHalf = recentEvents.take(recentEvents.size / 2)
        val secondHalf = recentEvents.drop(recentEvents.size / 2)

        val firstAvg = firstHalf.map { it.severity }.average()
        val secondAvg = secondHalf.map { it.severity }.average()

        val change = secondAvg - firstAvg

        if (kotlin.math.abs(change) > 1.0) {
            insights.add(
                HealthInsight(
                    type = InsightType.SEVERITY_TREND,
                    title = if (change > 0) "Worsening Symptoms" else "Improving Symptoms",
                    description = if (change > 0) {
                        "Your symptoms have worsened over the past month with an average increase of ${String.format("%.1f", change)} points."
                    } else {
                        "Your symptoms have improved over the past month with an average decrease of ${String.format("%.1f", kotlin.math.abs(change))} points."
                    },
                    priority = if (change > 0) InsightPriority.HIGH else InsightPriority.MEDIUM,
                    confidence = minOf(0.9f, kotlin.math.abs(change).toFloat() / 5f),
                    actionable = true,
                    relatedData = mapOf(
                        "severityChange" to change.toString(),
                        "recentAverage" to secondAvg.toString(),
                        "previousAverage" to firstAvg.toString()
                    ),
                    recommendations = if (change > 0) {
                        listOf(
                            "Review recent dietary changes",
                            "Consider consulting with a healthcare provider",
                            "Focus on identifying new trigger foods"
                        )
                    } else {
                        listOf(
                            "Continue current dietary approach",
                            "Document what changes have been working",
                            "Consider gradually reintroducing eliminated foods"
                        )
                    }
                )
            )
        }

        return insights
    }

    private fun generateDietaryInsights(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<HealthInsight> {
        val insights = mutableListOf<HealthInsight>()

        // Analyze meal timing patterns
        val mealTiming = analyzeMealTiming(foodEntries, symptomEvents)
        if (mealTiming.isNotEmpty()) {
            insights.addAll(mealTiming)
        }

        // Analyze food diversity
        val diversityInsight = analyzeFoodDiversity(foodEntries)
        if (diversityInsight != null) {
            insights.add(diversityInsight)
        }

        return insights
    }

    private fun generateImprovementInsights(symptomEvents: List<SymptomEvent>): List<HealthInsight> {
        val insights = mutableListOf<HealthInsight>()

        // Symptom-free streaks
        val currentStreak = calculateCurrentSymptomFreeStreak(symptomEvents)
        if (currentStreak > 3) {
            insights.add(
                HealthInsight(
                    type = InsightType.FREQUENCY_PATTERN,
                    title = "Symptom-Free Streak",
                    description = "You've been symptom-free for $currentStreak days! This is a positive sign that your dietary management is working.",
                    priority = InsightPriority.MEDIUM,
                    confidence = 0.9f,
                    actionable = false,
                    relatedData = mapOf("streakDays" to currentStreak.toString()),
                    recommendations = listOf(
                        "Continue your current dietary approach",
                        "Document what you've been doing differently",
                        "Consider this a baseline for future comparison"
                    )
                )
            )
        }

        return insights
    }

    private fun generateEliminationRecommendations(
        correlations: List<CorrelationPattern>
    ): List<HealthRecommendation> {
        val recommendations = mutableListOf<HealthRecommendation>()

        val highRiskFoods = correlations.filter {
            it.correlationStrength > 0.6f && it.occurrenceCount >= 2
        }.sortedByDescending { it.correlationStrength }.take(5)

        if (highRiskFoods.isNotEmpty()) {
            recommendations.add(
                HealthRecommendation(
                    type = RecommendationType.ELIMINATION_DIET,
                    title = "Try Elimination Diet",
                    description = "Consider eliminating high-risk foods for 2-4 weeks to see if symptoms improve. Reintroduce them one at a time.",
                    urgency = RecommendationUrgency.HIGH,
                    difficulty = RecommendationDifficulty.MODERATE,
                    expectedBenefit = "60-80% symptom reduction",
                    timeframe = "2-4 weeks",
                    steps = listOf(
                        "Eliminate identified trigger foods completely",
                        "Monitor symptoms for 2 weeks",
                        "Reintroduce foods one at a time every 3 days",
                        "Document any symptom changes"
                    ),
                    contraindications = listOf(
                        "Pregnancy or breastfeeding",
                        "History of eating disorders",
                        "Nutrient deficiency concerns"
                    )
                )
            )
        }

        return recommendations
    }

    private fun generateDietaryBalanceRecommendations(
        foodEntries: List<FoodEntry>
    ): List<HealthRecommendation> {
        val recommendations = mutableListOf<HealthRecommendation>()

        val foodFrequency = foodEntries.flatMap { it.foods }
            .groupBy { it }
            .mapValues { it.value.size }

        val varietyScore = calculateFoodVarietyScore(foodFrequency)

        if (varietyScore < 0.5f) {
            recommendations.add(
                HealthRecommendation(
                    type = RecommendationType.DIETARY_BALANCE,
                    title = "Increase Food Variety",
                    description = "Your diet shows limited variety. Increasing food diversity can improve gut health and nutrient intake.",
                    urgency = RecommendationUrgency.MEDIUM,
                    difficulty = RecommendationDifficulty.EASY,
                    expectedBenefit = "Improved nutrition and gut health",
                    timeframe = "2-3 weeks",
                    steps = listOf(
                        "Try one new food each week",
                        "Rotate protein sources",
                        "Include different colored vegetables",
                        "Experiment with new herbs and spices"
                    ),
                    contraindications = listOf(
                        "Known food allergies",
                        "Active elimination diet phase"
                    )
                )
            )
        }

        return recommendations
    }

    private fun generateLifestyleRecommendations(
        symptomEvents: List<SymptomEvent>
    ): List<HealthRecommendation> {
        val recommendations = mutableListOf<HealthRecommendation>()

        val avgSeverity = symptomEvents.map { it.severity }.average()

        if (avgSeverity > 6.0) {
            recommendations.add(
                HealthRecommendation(
                    type = RecommendationType.LIFESTYLE,
                    title = "Stress Management",
                    description = "High symptom severity may be exacerbated by stress. Consider stress reduction techniques.",
                    urgency = RecommendationUrgency.HIGH,
                    difficulty = RecommendationDifficulty.MODERATE,
                    expectedBenefit = "20-40% symptom reduction",
                    timeframe = "4-6 weeks",
                    steps = listOf(
                        "Practice daily meditation or mindfulness",
                        "Ensure adequate sleep (7-9 hours)",
                        "Regular gentle exercise",
                        "Consider stress management counseling"
                    ),
                    contraindications = emptyList()
                )
            )
        }

        return recommendations
    }

    private fun generateMonitoringRecommendations(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<HealthRecommendation> {
        val recommendations = mutableListOf<HealthRecommendation>()

        val entryFrequency = calculateEntryFrequency(foodEntries, symptomEvents)

        if (entryFrequency < 0.7f) { // Less than 70% of days have entries
            recommendations.add(
                HealthRecommendation(
                    type = RecommendationType.MONITORING,
                    title = "Improve Tracking Consistency",
                    description = "More consistent tracking will provide better insights into your trigger patterns.",
                    urgency = RecommendationUrgency.MEDIUM,
                    difficulty = RecommendationDifficulty.EASY,
                    expectedBenefit = "Better pattern identification",
                    timeframe = "2-3 weeks",
                    steps = listOf(
                        "Set daily reminders for food tracking",
                        "Track immediately after eating",
                        "Use quick entry templates",
                        "Record symptoms as they occur"
                    ),
                    contraindications = emptyList()
                )
            )
        }

        return recommendations
    }

    private fun detectSymptomClusters(symptomEvents: List<SymptomEvent>): List<DataCluster> {
        val clusters = mutableListOf<DataCluster>()

        val symptomsByType = symptomEvents.groupBy { it.symptomType }

        for ((type, events) in symptomsByType) {
            if (events.size >= 5) {
                val avgSeverity = events.map { it.severity }.average().toFloat()
                val timeSpread = if (events.size > 1) {
                    val sorted = events.sortedBy { it.timestamp }
                    ChronoUnit.DAYS.between(sorted.first().timestamp, sorted.last().timestamp)
                } else 0L

                val strength = calculateClusterStrength(events.size, avgSeverity, timeSpread)

                clusters.add(
                    DataCluster(
                        type = ClusterType.SYMPTOM_TYPE,
                        description = "Cluster of ${type.displayName} symptoms",
                        items = events.map { it.toString() },
                        strength = strength,
                        metrics = mapOf(
                            "averageSeverity" to avgSeverity.toString(),
                            "count" to events.size.toString(),
                            "timeSpreadDays" to timeSpread.toString()
                        )
                    )
                )
            }
        }

        return clusters
    }

    private fun detectFoodClusters(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<DataCluster> {
        val clusters = mutableListOf<DataCluster>()

        // Find frequently co-occurring foods
        val foodCombinations = mutableMapOf<Set<String>, MutableList<FoodEntry>>()

        for (entry in foodEntries) {
            if (entry.foods.size >= 2) {
                for (combination in entry.foods.combinations(2)) {
                    val key = combination.toSet()
                    foodCombinations.getOrPut(key) { mutableListOf() }.add(entry)
                }
            }
        }

        for ((combination, entries) in foodCombinations) {
            if (entries.size >= 3) {
                val correlatedSymptoms = entries.flatMap { entry ->
                    symptomEvents.filter { symptom ->
                        symptom.timestamp.isAfter(entry.timestamp) &&
                        symptom.timestamp.isBefore(entry.timestamp.plus(24, ChronoUnit.HOURS))
                    }
                }

                val strength = calculateFoodClusterStrength(entries.size, correlatedSymptoms.size)

                if (strength > 0.4f) {
                    clusters.add(
                        DataCluster(
                            type = ClusterType.FOOD_COMBINATION,
                            description = "Frequently combined foods: ${combination.joinToString(", ")}",
                            items = combination.toList(),
                            strength = strength,
                            metrics = mapOf(
                                "occurrences" to entries.size.toString(),
                                "correlatedSymptoms" to correlatedSymptoms.size.toString()
                            )
                        )
                    )
                }
            }
        }

        return clusters
    }

    // Helper methods for recommendations

    private fun generateTemporalRecommendations(pattern: TemporalPattern): List<String> {
        return when (pattern.type) {
            PatternType.DAILY_TIME -> listOf(
                "Avoid eating large meals 2-3 hours before this time",
                "Consider stress reduction techniques during this period",
                "Monitor sleep patterns around this time"
            )
            PatternType.WEEKLY -> listOf(
                "Plan lighter meals for this day",
                "Reduce stress activities on this day",
                "Consider meal prep to avoid trigger foods"
            )
            else -> listOf(
                "Monitor dietary patterns during this period",
                "Consider keeping a detailed log during these times"
            )
        }
    }

    private fun analyzeMealTiming(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<HealthInsight> {
        val insights = mutableListOf<HealthInsight>()

        // Late eating pattern
        val lateEatingCount = foodEntries.count { entry ->
            val hour = entry.timestamp.atZone(java.time.ZoneId.systemDefault()).hour
            hour >= 21 // After 9 PM
        }

        if (lateEatingCount > foodEntries.size * 0.2) { // More than 20% late eating
            val avgSeverityAfterLateEating = calculateAvgSeverityAfterLateEating(foodEntries, symptomEvents)

            if (avgSeverityAfterLateEating > 5.0f) {
                insights.add(
                    HealthInsight(
                        type = InsightType.FREQUENCY_PATTERN,
                        title = "Late Eating Pattern",
                        description = "You frequently eat late in the evening, which may contribute to digestive symptoms.",
                        priority = InsightPriority.MEDIUM,
                        confidence = 0.7f,
                        actionable = true,
                        relatedData = mapOf(
                            "lateEatingPercentage" to "${(lateEatingCount.toFloat() / foodEntries.size * 100).toInt()}%",
                            "avgSeverityAfter" to avgSeverityAfterLateEating.toString()
                        ),
                        recommendations = listOf(
                            "Try to finish eating by 7-8 PM",
                            "Have lighter dinners",
                            "Allow 3+ hours between last meal and bedtime"
                        )
                    )
                )
            }
        }

        return insights
    }

    private fun analyzeFoodDiversity(foodEntries: List<FoodEntry>): HealthInsight? {
        val uniqueFoods = foodEntries.flatMap { it.foods }.distinct()
        val totalMeals = foodEntries.size

        if (totalMeals < 10) return null

        val diversityScore = uniqueFoods.size.toFloat() / totalMeals

        return if (diversityScore < 0.3f) {
            HealthInsight(
                type = InsightType.DIETARY_BALANCE,
                title = "Limited Food Variety",
                description = "Your diet shows limited variety with only ${uniqueFoods.size} unique foods across $totalMeals meals.",
                priority = InsightPriority.LOW,
                confidence = 0.8f,
                actionable = true,
                relatedData = mapOf(
                    "uniqueFoods" to uniqueFoods.size.toString(),
                    "totalMeals" to totalMeals.toString(),
                    "diversityScore" to diversityScore.toString()
                ),
                recommendations = listOf(
                    "Gradually introduce new safe foods",
                    "Rotate between different protein sources",
                    "Try different cooking methods for familiar foods"
                )
            )
        } else null
    }

    private fun calculateCurrentSymptomFreeStreak(symptomEvents: List<SymptomEvent>): Int {
        val sortedEvents = symptomEvents.sortedByDescending { it.timestamp }
        if (sortedEvents.isEmpty()) return 0

        val latestEvent = sortedEvents.first()
        return ChronoUnit.DAYS.between(latestEvent.timestamp, Instant.now()).toInt()
    }

    private fun calculateFoodVarietyScore(foodFrequency: Map<String, Int>): Float {
        val totalFoods = foodFrequency.values.sum()
        val uniqueFoods = foodFrequency.size

        return if (totalFoods > 0) uniqueFoods.toFloat() / totalFoods else 0f
    }

    private fun calculateEntryFrequency(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): Float {
        val allEntries = foodEntries.map { it.timestamp } + symptomEvents.map { it.timestamp }
        if (allEntries.isEmpty()) return 0f

        val uniqueDays = allEntries.map {
            it.atZone(java.time.ZoneId.systemDefault()).toLocalDate()
        }.distinct()

        val oldestEntry = allEntries.minOrNull()
        val newestEntry = allEntries.maxOrNull()

        return if (oldestEntry != null && newestEntry != null) {
            val totalDays = ChronoUnit.DAYS.between(oldestEntry, newestEntry).coerceAtLeast(1)
            uniqueDays.size.toFloat() / totalDays
        } else 0f
    }

    private fun calculateClusterStrength(count: Int, avgSeverity: Float, timeSpreadDays: Long): Float {
        val frequency = minOf(1f, count / 20f) // Normalize to max 20 occurrences
        val severity = avgSeverity / 10f // Normalize to 0-1
        val recency = if (timeSpreadDays > 0) {
            1f / (timeSpreadDays / 30f).coerceAtLeast(1f) // More recent = higher strength
        } else 1f

        return (frequency + severity + recency) / 3f
    }

    private fun calculateFoodClusterStrength(occurrences: Int, correlatedSymptoms: Int): Float {
        val frequency = minOf(1f, occurrences / 10f)
        val correlation = if (occurrences > 0) correlatedSymptoms.toFloat() / occurrences else 0f

        return (frequency + correlation) / 2f
    }

    private fun calculateAvgSeverityAfterLateEating(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): Float {
        val lateEntries = foodEntries.filter { entry ->
            val hour = entry.timestamp.atZone(java.time.ZoneId.systemDefault()).hour
            hour >= 21
        }

        val symptomsAfterLateEating = lateEntries.flatMap { entry ->
            symptomEvents.filter { symptom ->
                symptom.timestamp.isAfter(entry.timestamp) &&
                symptom.timestamp.isBefore(entry.timestamp.plus(12, ChronoUnit.HOURS))
            }
        }

        return if (symptomsAfterLateEating.isNotEmpty()) {
            symptomsAfterLateEating.map { it.severity }.average().toFloat()
        } else 0f
    }

    // Extension function for combinations
    private fun <T> List<T>.combinations(size: Int): List<List<T>> {
        if (size > this.size) return emptyList()
        if (size == 1) return this.map { listOf(it) }

        val result = mutableListOf<List<T>>()
        for (i in 0..this.size - size) {
            val head = this[i]
            val tail = this.subList(i + 1, this.size).combinations(size - 1)
            for (combination in tail) {
                result.add(listOf(head) + combination)
            }
        }
        return result
    }
}

// Supporting data classes

data class HealthInsight(
    val type: InsightType,
    val title: String,
    val description: String,
    val priority: InsightPriority,
    val confidence: Float,
    val actionable: Boolean,
    val relatedData: Map<String, String>,
    val recommendations: List<String>
)

data class HealthRecommendation(
    val type: RecommendationType,
    val title: String,
    val description: String,
    val urgency: RecommendationUrgency,
    val difficulty: RecommendationDifficulty,
    val expectedBenefit: String,
    val timeframe: String,
    val steps: List<String>,
    val contraindications: List<String>
)

data class DataCluster(
    val type: ClusterType,
    val description: String,
    val items: List<String>,
    val strength: Float,
    val metrics: Map<String, String>
)

enum class RecommendationType {
    ELIMINATION_DIET,
    DIETARY_BALANCE,
    LIFESTYLE,
    MONITORING,
    MEDICAL_CONSULTATION
}

enum class RecommendationUrgency {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}

enum class RecommendationDifficulty {
    EASY,
    MODERATE,
    HARD,
    VERY_HARD
}

enum class ClusterType {
    SYMPTOM_TYPE,
    FOOD_COMBINATION,
    TEMPORAL,
    SEVERITY
}