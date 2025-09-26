package com.fooddiary.data.analytics

import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.CorrelationPattern
import com.fooddiary.data.models.*
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.*

/**
 * Statistical analysis engine for food diary data
 */
@Singleton
class StatisticsEngine @Inject constructor() {

    /**
     * Calculate comprehensive statistics for all data
     */
    fun calculateStatistics(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        correlations: List<CorrelationPattern>
    ): AnalyticsStatistics {
        return AnalyticsStatistics(
            totalEntries = foodEntries.size + symptomEvents.size,
            totalFoodEntries = foodEntries.size,
            totalSymptomEvents = symptomEvents.size,
            totalCorrelations = correlations.size,
            averageSeverity = calculateAverageSeverity(symptomEvents),
            mostCommonSymptom = findMostCommonSymptom(symptomEvents),
            mostCommonMealType = findMostCommonMealType(foodEntries),
            averageEntriesPerDay = calculateAverageEntriesPerDay(foodEntries, symptomEvents),
            symptomFreeStreak = calculateSymptomFreeStreak(symptomEvents),
            highRiskFoods = identifyHighRiskFoods(correlations),
            weeklyTrends = calculateWeeklyTrends(symptomEvents),
            topTriggerFoods = identifyTopTriggerFoods(correlations)
        )
    }

    /**
     * Calculate statistical correlations between foods and symptoms
     */
    fun calculateCorrelations(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<StatisticalCorrelation> {
        val correlations = mutableListOf<StatisticalCorrelation>()

        // Group foods by unique names
        val uniqueFoods = foodEntries.flatMap { it.foods }.distinct()

        for (food in uniqueFoods) {
            for (symptomType in SymptomType.values()) {
                val correlation = calculateFoodSymptomCorrelation(
                    food = food,
                    symptomType = symptomType,
                    foodEntries = foodEntries,
                    symptomEvents = symptomEvents
                )

                if (correlation.correlationCoefficient > 0.3f) {
                    correlations.add(correlation)
                }
            }
        }

        return correlations.sortedByDescending { it.correlationCoefficient }
    }

    /**
     * Calculate trigger patterns with statistical significance
     */
    fun getTriggerPatterns(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<TriggerStatistics> {
        val patterns = mutableListOf<TriggerStatistics>()
        val uniqueFoods = foodEntries.flatMap { it.foods }.distinct()

        for (food in uniqueFoods) {
            val statistics = calculateTriggerStatistics(food, foodEntries, symptomEvents)
            if (statistics.isStatisticallySignificant) {
                patterns.add(statistics)
            }
        }

        return patterns.sortedByDescending { it.riskScore }
    }

    /**
     * Generate insights from statistical analysis
     */
    fun generateInsights(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        correlations: List<CorrelationPattern>
    ): List<AnalyticsInsight> {
        val insights = mutableListOf<AnalyticsInsight>()

        // Severity trend insights
        val severityTrend = calculateSeverityTrend(symptomEvents)
        if (severityTrend.isSignificant) {
            insights.add(
                AnalyticsInsight(
                    type = InsightType.SEVERITY_TREND,
                    title = "Symptom Severity Trend",
                    description = severityTrend.description,
                    confidence = severityTrend.confidence,
                    actionable = severityTrend.actionable,
                    priority = InsightPriority.MEDIUM
                )
            )
        }

        // Frequency insights
        val frequencyInsight = analyzeFrequencyPatterns(symptomEvents)
        if (frequencyInsight.isSignificant) {
            insights.add(
                AnalyticsInsight(
                    type = InsightType.FREQUENCY_PATTERN,
                    title = "Symptom Frequency Pattern",
                    description = frequencyInsight.description,
                    confidence = frequencyInsight.confidence,
                    actionable = frequencyInsight.actionable,
                    priority = InsightPriority.HIGH
                )
            )
        }

        // Correlation insights
        val strongCorrelations = correlations.filter { it.correlationStrength > 0.7f }
        for (correlation in strongCorrelations.take(3)) {
            insights.add(
                AnalyticsInsight(
                    type = InsightType.STRONG_CORRELATION,
                    title = "Strong Food-Symptom Correlation",
                    description = "Strong correlation detected between food and symptom (${(correlation.correlationStrength * 100).toInt()}% confidence)",
                    confidence = correlation.correlationStrength,
                    actionable = true,
                    priority = InsightPriority.HIGH
                )
            )
        }

        return insights.sortedByDescending { it.priority.ordinal }
    }

    /**
     * Predict risk scores for foods
     */
    fun predictRisk(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        targetFoods: List<String>
    ): Map<String, RiskPrediction> {
        return targetFoods.associateWith { food ->
            calculateRiskPrediction(food, foodEntries, symptomEvents)
        }
    }

    /**
     * Analyze trends over time
     */
    fun analyzeTrends(
        symptomEvents: List<SymptomEvent>,
        periodDays: Int = 90
    ): TrendAnalysis {
        val cutoffTime = Instant.now().minus(periodDays.toLong(), ChronoUnit.DAYS)
        val recentEvents = symptomEvents.filter { it.timestamp.isAfter(cutoffTime) }

        val weeklyData = groupByWeeks(recentEvents)
        val trendDirection = calculateTrendDirection(weeklyData)
        val seasonality = detectSeasonality(recentEvents)

        return TrendAnalysis(
            direction = trendDirection,
            strength = calculateTrendStrength(weeklyData),
            seasonality = seasonality,
            weeklyAverages = weeklyData.mapValues { it.value.map { event -> event.severity }.average().toFloat() },
            prediction = predictNextPeriod(weeklyData)
        )
    }

    // Private helper methods

    private fun calculateAverageSeverity(symptomEvents: List<SymptomEvent>): Float {
        return if (symptomEvents.isNotEmpty()) {
            symptomEvents.map { it.severity }.average().toFloat()
        } else 0f
    }

    private fun findMostCommonSymptom(symptomEvents: List<SymptomEvent>): SymptomType? {
        return symptomEvents.groupBy { it.symptomType }
            .maxByOrNull { it.value.size }?.key
    }

    private fun findMostCommonMealType(foodEntries: List<FoodEntry>): MealType? {
        return foodEntries.groupBy { it.mealType }
            .maxByOrNull { it.value.size }?.key
    }

    private fun calculateAverageEntriesPerDay(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): Float {
        val allEntries = foodEntries.map { it.timestamp } + symptomEvents.map { it.timestamp }
        if (allEntries.isEmpty()) return 0f

        val oldestEntry = allEntries.minOrNull() ?: return 0f
        val newestEntry = allEntries.maxOrNull() ?: return 0f
        val daysDiff = ChronoUnit.DAYS.between(oldestEntry, newestEntry).coerceAtLeast(1)

        return allEntries.size.toFloat() / daysDiff
    }

    private fun calculateSymptomFreeStreak(symptomEvents: List<SymptomEvent>): Int {
        val sortedEvents = symptomEvents.sortedByDescending { it.timestamp }
        if (sortedEvents.isEmpty()) return 0

        val latestEvent = sortedEvents.first()
        return ChronoUnit.DAYS.between(latestEvent.timestamp, Instant.now()).toInt()
    }

    private fun identifyHighRiskFoods(correlations: List<CorrelationPattern>): List<String> {
        return correlations
            .filter { it.correlationStrength > 0.7f && it.occurrenceCount >= 3 }
            .map { "Food ID ${it.foodId}" } // Simplified - would need food name lookup
            .distinct()
            .take(5)
    }

    private fun calculateWeeklyTrends(symptomEvents: List<SymptomEvent>): Map<Int, Float> {
        return symptomEvents.groupBy {
            it.timestamp.atZone(java.time.ZoneId.systemDefault()).dayOfWeek.value
        }.mapValues { (_, events) ->
            events.map { it.severity }.average().toFloat()
        }
    }

    private fun identifyTopTriggerFoods(correlations: List<CorrelationPattern>): List<String> {
        return correlations
            .filter { it.correlationStrength > 0.6f }
            .sortedByDescending { it.correlationStrength }
            .map { "Food ID ${it.foodId}" } // Simplified
            .take(10)
    }

    private fun calculateFoodSymptomCorrelation(
        food: String,
        symptomType: SymptomType,
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): StatisticalCorrelation {
        val foodOccurrences = foodEntries.filter { entry ->
            entry.foods.any { it.lowercase().contains(food.lowercase()) }
        }

        val symptomOccurrences = symptomEvents.filter { it.symptomType == symptomType }

        val correlatedEvents = findCorrelatedEvents(foodOccurrences, symptomOccurrences)
        val coefficient = calculatePearsonCorrelation(foodOccurrences, correlatedEvents)
        val pValue = calculatePValue(coefficient, correlatedEvents.size)

        return StatisticalCorrelation(
            food = food,
            symptomType = symptomType,
            correlationCoefficient = coefficient,
            pValue = pValue,
            sampleSize = correlatedEvents.size,
            isSignificant = pValue < 0.05f && coefficient > 0.3f
        )
    }

    private fun calculateTriggerStatistics(
        food: String,
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): TriggerStatistics {
        val foodOccurrences = foodEntries.filter { entry ->
            entry.foods.any { it.lowercase().contains(food.lowercase()) }
        }

        var totalCorrelations = 0
        var totalSeverity = 0f
        val symptomTypeCounts = mutableMapOf<SymptomType, Int>()

        for (foodEntry in foodOccurrences) {
            val followingSymptoms = symptomEvents.filter { symptom ->
                symptom.timestamp.isAfter(foodEntry.timestamp) &&
                symptom.timestamp.isBefore(foodEntry.timestamp.plus(24, ChronoUnit.HOURS))
            }

            totalCorrelations += followingSymptoms.size
            totalSeverity += followingSymptoms.sumOf { it.severity }

            followingSymptoms.forEach { symptom ->
                symptomTypeCounts[symptom.symptomType] =
                    symptomTypeCounts.getOrDefault(symptom.symptomType, 0) + 1
            }
        }

        val averageSeverity = if (totalCorrelations > 0) totalSeverity / totalCorrelations else 0f
        val riskScore = calculateRiskScore(totalCorrelations, averageSeverity, foodOccurrences.size)

        return TriggerStatistics(
            foodName = food,
            occurrences = foodOccurrences.size,
            correlatedSymptoms = totalCorrelations,
            averageSeverity = averageSeverity,
            riskScore = riskScore,
            symptomBreakdown = symptomTypeCounts,
            isStatisticallySignificant = totalCorrelations >= 3 && averageSeverity > 5f
        )
    }

    private fun findCorrelatedEvents(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<SymptomEvent> {
        return symptomEvents.filter { symptom ->
            foodEntries.any { food ->
                symptom.timestamp.isAfter(food.timestamp) &&
                symptom.timestamp.isBefore(food.timestamp.plus(24, ChronoUnit.HOURS))
            }
        }
    }

    private fun calculatePearsonCorrelation(
        foodEntries: List<FoodEntry>,
        correlatedSymptoms: List<SymptomEvent>
    ): Float {
        if (foodEntries.size < 2 || correlatedSymptoms.size < 2) return 0f

        // Simplified correlation calculation
        val foodTimes = foodEntries.map { it.timestamp.epochSecond.toDouble() }
        val symptomTimes = correlatedSymptoms.map { it.timestamp.epochSecond.toDouble() }

        val n = minOf(foodTimes.size, symptomTimes.size)
        if (n < 2) return 0f

        val xValues = foodTimes.take(n)
        val yValues = symptomTimes.take(n)

        val meanX = xValues.average()
        val meanY = yValues.average()

        val numerator = xValues.zip(yValues) { x, y -> (x - meanX) * (y - meanY) }.sum()
        val denomX = sqrt(xValues.sumOf { (it - meanX).pow(2) })
        val denomY = sqrt(yValues.sumOf { (it - meanY).pow(2) })

        return if (denomX * denomY == 0.0) 0f else (numerator / (denomX * denomY)).toFloat()
    }

    private fun calculatePValue(correlation: Float, sampleSize: Int): Float {
        if (sampleSize < 3) return 1f

        val t = correlation * sqrt((sampleSize - 2) / (1 - correlation.pow(2)))
        // Simplified p-value calculation
        return when {
            abs(t) > 2.58 -> 0.01f // 99% confidence
            abs(t) > 1.96 -> 0.05f // 95% confidence
            abs(t) > 1.64 -> 0.1f  // 90% confidence
            else -> 0.5f
        }
    }

    private fun calculateRiskScore(
        correlations: Int,
        averageSeverity: Float,
        totalOccurrences: Int
    ): Float {
        val frequency = if (totalOccurrences > 0) correlations.toFloat() / totalOccurrences else 0f
        return (frequency * 0.4f + (averageSeverity / 10f) * 0.6f).coerceIn(0f, 1f)
    }

    private fun calculateSeverityTrend(symptomEvents: List<SymptomEvent>): InsightData {
        val recentEvents = symptomEvents.sortedBy { it.timestamp }.takeLast(30)
        if (recentEvents.size < 10) {
            return InsightData(false, "", 0f, false)
        }

        val firstHalf = recentEvents.take(recentEvents.size / 2)
        val secondHalf = recentEvents.drop(recentEvents.size / 2)

        val firstAvg = firstHalf.map { it.severity }.average()
        val secondAvg = secondHalf.map { it.severity }.average()

        val change = secondAvg - firstAvg
        val significance = abs(change) > 1.0

        return InsightData(
            isSignificant = significance,
            description = when {
                change > 1 -> "Symptom severity has increased significantly over time"
                change < -1 -> "Symptom severity has decreased significantly over time"
                else -> "Symptom severity has remained stable"
            },
            confidence = minOf(0.9f, abs(change.toFloat()) / 5f),
            actionable = significance
        )
    }

    private fun analyzeFrequencyPatterns(symptomEvents: List<SymptomEvent>): InsightData {
        val dailyCount = symptomEvents.groupBy {
            it.timestamp.atZone(java.time.ZoneId.systemDefault()).toLocalDate()
        }.mapValues { it.value.size }

        val average = dailyCount.values.average()
        val variance = dailyCount.values.map { (it - average).pow(2) }.average()

        return InsightData(
            isSignificant = variance > 2,
            description = if (variance > 2) {
                "Significant variation in daily symptom frequency detected"
            } else {
                "Symptom frequency is relatively consistent"
            },
            confidence = minOf(0.9f, variance.toFloat() / 10f),
            actionable = variance > 2
        )
    }

    private fun calculateRiskPrediction(
        food: String,
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): RiskPrediction {
        val triggerStats = calculateTriggerStatistics(food, foodEntries, symptomEvents)

        return RiskPrediction(
            riskScore = triggerStats.riskScore,
            confidence = if (triggerStats.occurrences >= 5) 0.8f else 0.4f,
            expectedSeverity = triggerStats.averageSeverity,
            timeToSymptoms = 4f, // Simplified - average 4 hours
            recommendation = when {
                triggerStats.riskScore > 0.7f -> "Avoid this food"
                triggerStats.riskScore > 0.4f -> "Consume with caution"
                else -> "Low risk food"
            }
        )
    }

    private fun groupByWeeks(symptomEvents: List<SymptomEvent>): Map<Int, List<SymptomEvent>> {
        return symptomEvents.groupBy { event ->
            val date = event.timestamp.atZone(java.time.ZoneId.systemDefault()).toLocalDate()
            date.get(java.time.temporal.WeekFields.ISO.weekOfYear())
        }
    }

    private fun calculateTrendDirection(weeklyData: Map<Int, List<SymptomEvent>>): TrendDirection {
        val weeks = weeklyData.keys.sorted()
        if (weeks.size < 3) return TrendDirection.STABLE

        val averages = weeks.map { week ->
            weeklyData[week]?.map { it.severity }?.average()?.toFloat() ?: 0f
        }

        val firstThird = averages.take(averages.size / 3).average()
        val lastThird = averages.takeLast(averages.size / 3).average()

        return when {
            lastThird > firstThird + 0.5 -> TrendDirection.INCREASING
            lastThird < firstThird - 0.5 -> TrendDirection.DECREASING
            else -> TrendDirection.STABLE
        }
    }

    private fun calculateTrendStrength(weeklyData: Map<Int, List<SymptomEvent>>): Float {
        val averages = weeklyData.values.map { events ->
            events.map { it.severity }.average().toFloat()
        }

        if (averages.size < 2) return 0f

        val variance = averages.map { (it - averages.average()).pow(2) }.average()
        return sqrt(variance).toFloat()
    }

    private fun detectSeasonality(symptomEvents: List<SymptomEvent>): Float {
        val monthlyAverages = symptomEvents.groupBy { event ->
            event.timestamp.atZone(java.time.ZoneId.systemDefault()).monthValue
        }.mapValues { (_, events) ->
            events.map { it.severity }.average().toFloat()
        }

        if (monthlyAverages.size < 3) return 0f

        val overallAverage = monthlyAverages.values.average()
        val variance = monthlyAverages.values.map { (it - overallAverage).pow(2) }.average()

        return sqrt(variance).toFloat()
    }

    private fun predictNextPeriod(weeklyData: Map<Int, List<SymptomEvent>>): Float {
        val averages = weeklyData.values.map { events ->
            events.map { it.severity }.average().toFloat()
        }

        return if (averages.isNotEmpty()) averages.takeLast(3).average().toFloat() else 0f
    }
}

// Supporting data classes

data class AnalyticsStatistics(
    val totalEntries: Int,
    val totalFoodEntries: Int,
    val totalSymptomEvents: Int,
    val totalCorrelations: Int,
    val averageSeverity: Float,
    val mostCommonSymptom: SymptomType?,
    val mostCommonMealType: MealType?,
    val averageEntriesPerDay: Float,
    val symptomFreeStreak: Int,
    val highRiskFoods: List<String>,
    val weeklyTrends: Map<Int, Float>,
    val topTriggerFoods: List<String>
)

data class StatisticalCorrelation(
    val food: String,
    val symptomType: SymptomType,
    val correlationCoefficient: Float,
    val pValue: Float,
    val sampleSize: Int,
    val isSignificant: Boolean
)

data class TriggerStatistics(
    val foodName: String,
    val occurrences: Int,
    val correlatedSymptoms: Int,
    val averageSeverity: Float,
    val riskScore: Float,
    val symptomBreakdown: Map<SymptomType, Int>,
    val isStatisticallySignificant: Boolean
)

data class AnalyticsInsight(
    val type: InsightType,
    val title: String,
    val description: String,
    val confidence: Float,
    val actionable: Boolean,
    val priority: InsightPriority
)

data class RiskPrediction(
    val riskScore: Float,
    val confidence: Float,
    val expectedSeverity: Float,
    val timeToSymptoms: Float, // hours
    val recommendation: String
)

data class TrendAnalysis(
    val direction: TrendDirection,
    val strength: Float,
    val seasonality: Float,
    val weeklyAverages: Map<Int, Float>,
    val prediction: Float
)

data class InsightData(
    val isSignificant: Boolean,
    val description: String,
    val confidence: Float,
    val actionable: Boolean
)

enum class InsightType {
    SEVERITY_TREND,
    FREQUENCY_PATTERN,
    STRONG_CORRELATION,
    TRIGGER_IDENTIFICATION,
    SEASONAL_PATTERN
}

enum class InsightPriority {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}

enum class TrendDirection {
    INCREASING,
    DECREASING,
    STABLE
}