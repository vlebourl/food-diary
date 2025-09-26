package com.fooddiary.data.analysis

import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.CorrelationPattern
import com.fooddiary.data.database.entities.TriggerPattern
import com.fooddiary.data.models.*
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.*

/**
 * Analyzes correlations between food entries and symptom events
 */
@Singleton
class CorrelationAnalyzer @Inject constructor() {

    /**
     * Analyze correlations between food entries and symptom events
     */
    fun analyzeCorrelations(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        maxTimeWindowHours: Int = 24
    ): List<CorrelationPattern> {
        val correlations = mutableListOf<CorrelationPattern>()

        for (symptom in symptomEvents) {
            val potentialTriggers = findPotentialTriggers(symptom, foodEntries, maxTimeWindowHours)

            for ((food, timeOffset) in potentialTriggers) {
                val strength = calculateCorrelationStrength(food, symptom, foodEntries, symptomEvents)
                val confidence = calculateConfidenceLevel(strength, getOccurrenceCount(food, symptom, foodEntries, symptomEvents))

                if (strength > 0.3f) { // Only keep meaningful correlations
                    correlations.add(
                        CorrelationPattern(
                            foodId = food.id,
                            symptomId = symptom.id,
                            correlationStrength = strength,
                            confidenceLevel = confidence,
                            timeOffsetHours = timeOffset,
                            occurrenceCount = getOccurrenceCount(food, symptom, foodEntries, symptomEvents)
                        )
                    )
                }
            }
        }

        return correlations.sortedByDescending { it.correlationStrength }
    }

    /**
     * Calculate statistical correlation between specific food and symptom
     */
    fun calculateFoodSymptomCorrelation(
        foodName: String,
        symptomType: SymptomType,
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): Float {
        val relevantFoodEntries = foodEntries.filter { entry ->
            entry.foods.any { food -> food.lowercase().contains(foodName.lowercase()) }
        }

        val relevantSymptomEvents = symptomEvents.filter { it.symptomType == symptomType }

        if (relevantFoodEntries.isEmpty() || relevantSymptomEvents.isEmpty()) return 0f

        // Calculate correlation coefficient
        return calculateCorrelationCoefficient(relevantFoodEntries, relevantSymptomEvents)
    }

    /**
     * Identify potential trigger foods for a symptom
     */
    fun identifyTriggerFoods(
        symptom: SymptomEvent,
        allFoodEntries: List<FoodEntry>,
        lookbackHours: Int = 24
    ): List<Pair<FoodEntry, Float>> {
        val cutoffTime = symptom.timestamp.minus(lookbackHours.toLong(), ChronoUnit.HOURS)
        val potentialTriggers = allFoodEntries.filter {
            it.timestamp.isAfter(cutoffTime) && it.timestamp.isBefore(symptom.timestamp)
        }

        return potentialTriggers.map { food ->
            val timeDiff = Duration.between(food.timestamp, symptom.timestamp).toHours()
            val proximityScore = calculateProximityScore(timeDiff)
            val severityWeight = symptom.severity / 10f
            val score = proximityScore * severityWeight

            food to score
        }.sortedByDescending { it.second }
    }

    /**
     * Calculate severity trend for a symptom type
     */
    fun calculateSeverityTrend(
        symptomType: SymptomType,
        symptomEvents: List<SymptomEvent>,
        daysPeriod: Int = 30
    ): SeverityTrend {
        val relevantEvents = symptomEvents
            .filter { it.symptomType == symptomType }
            .filter {
                val daysSinceEvent = ChronoUnit.DAYS.between(it.timestamp, Instant.now())
                daysSinceEvent <= daysPeriod
            }
            .sortedBy { it.timestamp }

        if (relevantEvents.isEmpty()) {
            return SeverityTrend(
                symptomType = symptomType,
                averageSeverity = 0f,
                trend = TrendDirection.STABLE,
                trendStrength = 0f,
                dataPoints = emptyList()
            )
        }

        val dataPoints = relevantEvents.map {
            SeverityDataPoint(it.timestamp, it.severity.toFloat())
        }

        val averageSeverity = relevantEvents.map { it.severity }.average().toFloat()
        val trend = calculateTrendDirection(dataPoints)
        val trendStrength = calculateTrendStrength(dataPoints)

        return SeverityTrend(
            symptomType = symptomType,
            averageSeverity = averageSeverity,
            trend = trend,
            trendStrength = trendStrength,
            dataPoints = dataPoints
        )
    }

    /**
     * Analyze symptom patterns and clustering
     */
    fun analyzeSymptomPatterns(symptomEvents: List<SymptomEvent>): SymptomPatternAnalysis {
        val patterns = mutableMapOf<SymptomType, List<SymptomEvent>>()

        symptomEvents.groupBy { it.symptomType }.forEach { (type, events) ->
            patterns[type] = events.sortedBy { it.timestamp }
        }

        val clusters = identifySymptomClusters(symptomEvents)
        val peakTimes = identifyPeakTimes(symptomEvents)
        val frequencyAnalysis = calculateFrequencyAnalysis(symptomEvents)

        return SymptomPatternAnalysis(
            patterns = patterns,
            clusters = clusters,
            peakTimes = peakTimes,
            frequencyAnalysis = frequencyAnalysis
        )
    }

    // Private helper methods

    private fun findPotentialTriggers(
        symptom: SymptomEvent,
        foodEntries: List<FoodEntry>,
        maxTimeWindowHours: Int
    ): List<Pair<FoodEntry, Int>> {
        val cutoffTime = symptom.timestamp.minus(maxTimeWindowHours.toLong(), ChronoUnit.HOURS)

        return foodEntries
            .filter { it.timestamp.isAfter(cutoffTime) && it.timestamp.isBefore(symptom.timestamp) }
            .map { food ->
                val timeOffset = Duration.between(food.timestamp, symptom.timestamp).toHours().toInt()
                food to timeOffset
            }
            .sortedBy { it.second }
    }

    private fun calculateCorrelationStrength(
        food: FoodEntry,
        symptom: SymptomEvent,
        allFoodEntries: List<FoodEntry>,
        allSymptomEvents: List<SymptomEvent>
    ): Float {
        val timeProximity = calculateTimeProximity(food, symptom)
        val foodFrequency = calculateFoodFrequency(food, allFoodEntries)
        val symptomSeverity = symptom.severity / 10f
        val occurrenceFrequency = getOccurrenceCount(food, symptom, allFoodEntries, allSymptomEvents) / 10f

        // Weighted combination of factors
        return (timeProximity * 0.3f + foodFrequency * 0.2f + symptomSeverity * 0.3f + occurrenceFrequency * 0.2f)
            .coerceIn(0f, 1f)
    }

    private fun calculateTimeProximity(food: FoodEntry, symptom: SymptomEvent): Float {
        val hoursDiff = Duration.between(food.timestamp, symptom.timestamp).toHours()
        return when {
            hoursDiff <= 2 -> 1.0f
            hoursDiff <= 4 -> 0.8f
            hoursDiff <= 8 -> 0.6f
            hoursDiff <= 12 -> 0.4f
            hoursDiff <= 24 -> 0.2f
            else -> 0.1f
        }
    }

    private fun calculateFoodFrequency(food: FoodEntry, allFoodEntries: List<FoodEntry>): Float {
        val totalEntries = allFoodEntries.size
        if (totalEntries == 0) return 0f

        val matchingFoods = allFoodEntries.count { entry ->
            entry.foods.any { entryFood ->
                food.foods.any { targetFood ->
                    entryFood.lowercase().contains(targetFood.lowercase())
                }
            }
        }

        return (matchingFoods.toFloat() / totalEntries).coerceIn(0f, 1f)
    }

    private fun getOccurrenceCount(
        food: FoodEntry,
        symptom: SymptomEvent,
        allFoodEntries: List<FoodEntry>,
        allSymptomEvents: List<SymptomEvent>
    ): Int {
        var count = 0

        for (entry in allFoodEntries) {
            if (entry.foods.any { entryFood ->
                food.foods.any { targetFood ->
                    entryFood.lowercase().contains(targetFood.lowercase())
                }
            }) {
                // Look for symptoms within 24 hours after this food entry
                val symptomsAfter = allSymptomEvents.count { symptomEvent ->
                    symptomEvent.symptomType == symptom.symptomType &&
                    symptomEvent.timestamp.isAfter(entry.timestamp) &&
                    symptomEvent.timestamp.isBefore(entry.timestamp.plus(24, ChronoUnit.HOURS))
                }
                if (symptomsAfter > 0) count++
            }
        }

        return count
    }

    private fun calculateConfidenceLevel(strength: Float, occurrences: Int): ConfidenceLevel {
        return when {
            strength >= 0.8f && occurrences >= 5 -> ConfidenceLevel.VERY_HIGH
            strength >= 0.6f && occurrences >= 3 -> ConfidenceLevel.HIGH
            strength >= 0.4f && occurrences >= 2 -> ConfidenceLevel.MEDIUM
            else -> ConfidenceLevel.LOW
        }
    }

    private fun calculateCorrelationCoefficient(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): Float {
        // Simplified Pearson correlation coefficient
        if (foodEntries.isEmpty() || symptomEvents.isEmpty()) return 0f

        val foodTimes = foodEntries.map { it.timestamp.epochSecond.toDouble() }
        val symptomTimes = symptomEvents.map { it.timestamp.epochSecond.toDouble() }

        // For simplicity, we'll use occurrence frequency
        val n = minOf(foodTimes.size, symptomTimes.size)
        if (n < 2) return 0f

        val foodFreq = foodTimes.take(n)
        val symptomFreq = symptomTimes.take(n)

        val meanFood = foodFreq.average()
        val meanSymptom = symptomFreq.average()

        val numerator = foodFreq.zip(symptomFreq) { f, s -> (f - meanFood) * (s - meanSymptom) }.sum()
        val denomFood = sqrt(foodFreq.sumOf { (it - meanFood).pow(2) })
        val denomSymptom = sqrt(symptomFreq.sumOf { (it - meanSymptom).pow(2) })

        return if (denomFood * denomSymptom == 0.0) 0f
               else (numerator / (denomFood * denomSymptom)).toFloat().absoluteValue
    }

    private fun calculateProximityScore(hoursDiff: Long): Float {
        return when {
            hoursDiff <= 1 -> 1.0f
            hoursDiff <= 3 -> 0.8f
            hoursDiff <= 6 -> 0.6f
            hoursDiff <= 12 -> 0.4f
            hoursDiff <= 24 -> 0.2f
            else -> 0.1f
        }
    }

    private fun calculateTrendDirection(dataPoints: List<SeverityDataPoint>): TrendDirection {
        if (dataPoints.size < 2) return TrendDirection.STABLE

        val recentPoints = dataPoints.takeLast(7) // Last week
        val olderPoints = dataPoints.dropLast(7).takeLast(7)

        if (olderPoints.isEmpty()) return TrendDirection.STABLE

        val recentAvg = recentPoints.map { it.severity }.average()
        val olderAvg = olderPoints.map { it.severity }.average()

        return when {
            recentAvg > olderAvg + 0.5 -> TrendDirection.INCREASING
            recentAvg < olderAvg - 0.5 -> TrendDirection.DECREASING
            else -> TrendDirection.STABLE
        }
    }

    private fun calculateTrendStrength(dataPoints: List<SeverityDataPoint>): Float {
        if (dataPoints.size < 2) return 0f

        val values = dataPoints.map { it.severity.toDouble() }
        val n = values.size
        val xValues = (1..n).map { it.toDouble() }

        // Calculate linear regression slope
        val xMean = xValues.average()
        val yMean = values.average()

        val numerator = xValues.zip(values) { x, y -> (x - xMean) * (y - yMean) }.sum()
        val denominator = xValues.sumOf { (it - xMean).pow(2) }

        return if (denominator == 0.0) 0f else abs(numerator / denominator).toFloat()
    }

    private fun identifySymptomClusters(symptomEvents: List<SymptomEvent>): List<SymptomCluster> {
        // Simplified clustering based on time proximity
        val clusters = mutableListOf<SymptomCluster>()
        val sortedEvents = symptomEvents.sortedBy { it.timestamp }

        var currentCluster = mutableListOf<SymptomEvent>()
        var lastTimestamp: Instant? = null

        for (event in sortedEvents) {
            if (lastTimestamp == null || Duration.between(lastTimestamp, event.timestamp).toHours() <= 6) {
                currentCluster.add(event)
            } else {
                if (currentCluster.size >= 2) {
                    clusters.add(SymptomCluster(
                        events = currentCluster.toList(),
                        startTime = currentCluster.first().timestamp,
                        endTime = currentCluster.last().timestamp,
                        averageSeverity = currentCluster.map { it.severity }.average().toFloat()
                    ))
                }
                currentCluster = mutableListOf(event)
            }
            lastTimestamp = event.timestamp
        }

        // Don't forget the last cluster
        if (currentCluster.size >= 2) {
            clusters.add(SymptomCluster(
                events = currentCluster.toList(),
                startTime = currentCluster.first().timestamp,
                endTime = currentCluster.last().timestamp,
                averageSeverity = currentCluster.map { it.severity }.average().toFloat()
            ))
        }

        return clusters
    }

    private fun identifyPeakTimes(symptomEvents: List<SymptomEvent>): Map<Int, Float> {
        val hourlyCount = symptomEvents.groupBy {
            it.timestamp.atZone(java.time.ZoneId.systemDefault()).hour
        }.mapValues { (_, events) ->
            events.map { it.severity }.average().toFloat()
        }

        return hourlyCount
    }

    private fun calculateFrequencyAnalysis(symptomEvents: List<SymptomEvent>): Map<SymptomType, Int> {
        return symptomEvents.groupBy { it.symptomType }.mapValues { it.value.size }
    }
}

// Supporting data classes

data class SeverityTrend(
    val symptomType: SymptomType,
    val averageSeverity: Float,
    val trend: TrendDirection,
    val trendStrength: Float,
    val dataPoints: List<SeverityDataPoint>
)

data class SeverityDataPoint(
    val timestamp: Instant,
    val severity: Float
)

enum class TrendDirection {
    INCREASING,
    DECREASING,
    STABLE
}

data class SymptomPatternAnalysis(
    val patterns: Map<SymptomType, List<SymptomEvent>>,
    val clusters: List<SymptomCluster>,
    val peakTimes: Map<Int, Float>, // Hour of day to average severity
    val frequencyAnalysis: Map<SymptomType, Int>
)

data class SymptomCluster(
    val events: List<SymptomEvent>,
    val startTime: Instant,
    val endTime: Instant,
    val averageSeverity: Float
)