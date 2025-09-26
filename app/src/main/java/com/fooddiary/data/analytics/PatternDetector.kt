package com.fooddiary.data.analytics

import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.EnvironmentalContext
import com.fooddiary.data.models.*
import java.time.Instant
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs

/**
 * Pattern detection engine for identifying recurring patterns in food diary data
 */
@Singleton
class PatternDetector @Inject constructor() {

    /**
     * Detect temporal patterns in symptom occurrence
     */
    fun detectTemporalPatterns(symptomEvents: List<SymptomEvent>): List<TemporalPattern> {
        val patterns = mutableListOf<TemporalPattern>()

        // Daily time patterns
        patterns.addAll(detectDailyTimePatterns(symptomEvents))

        // Weekly patterns
        patterns.addAll(detectWeeklyPatterns(symptomEvents))

        // Monthly patterns
        patterns.addAll(detectMonthlyPatterns(symptomEvents))

        return patterns.sortedByDescending { it.confidence }
    }

    /**
     * Detect clustering patterns in symptoms
     */
    fun detectClusterPatterns(
        symptomEvents: List<SymptomEvent>,
        maxGapHours: Int = 24
    ): List<ClusterPattern> {
        val sortedEvents = symptomEvents.sortedBy { it.timestamp }
        val clusters = mutableListOf<ClusterPattern>()

        var currentCluster = mutableListOf<SymptomEvent>()
        var lastTimestamp: Instant? = null

        for (event in sortedEvents) {
            if (lastTimestamp == null ||
                ChronoUnit.HOURS.between(lastTimestamp, event.timestamp) <= maxGapHours) {
                currentCluster.add(event)
            } else {
                if (currentCluster.size >= 2) {
                    clusters.add(createClusterPattern(currentCluster))
                }
                currentCluster = mutableListOf(event)
            }
            lastTimestamp = event.timestamp
        }

        // Don't forget the last cluster
        if (currentCluster.size >= 2) {
            clusters.add(createClusterPattern(currentCluster))
        }

        return clusters.sortedByDescending { it.intensity }
    }

    /**
     * Detect food combination patterns that trigger symptoms
     */
    fun detectFoodCombinationPatterns(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): List<CombinationPattern> {
        val patterns = mutableListOf<CombinationPattern>()
        val foodCombinations = mutableMapOf<Set<String>, MutableList<FoodEntry>>()

        // Group entries by food combinations
        for (entry in foodEntries) {
            val foodSet = entry.foods.toSet()
            if (foodSet.size >= 2) { // Only consider combinations
                foodCombinations.getOrPut(foodSet) { mutableListOf() }.add(entry)
            }
        }

        // Analyze each combination for symptom correlation
        for ((combination, entries) in foodCombinations) {
            if (entries.size >= 3) { // Need multiple occurrences
                val pattern = analyzeCombinationPattern(combination, entries, symptomEvents)
                if (pattern.riskScore > 0.4f) {
                    patterns.add(pattern)
                }
            }
        }

        return patterns.sortedByDescending { it.riskScore }
    }

    /**
     * Detect environmental patterns affecting symptoms
     */
    fun detectEnvironmentalPatterns(
        symptomEvents: List<SymptomEvent>,
        environmentalContexts: List<EnvironmentalContext>
    ): List<EnvironmentalPattern> {
        val patterns = mutableListOf<EnvironmentalPattern>()

        // Group symptoms by environmental context
        val contextToSymptoms = mutableMapOf<String, MutableList<SymptomEvent>>()

        for (context in environmentalContexts) {
            val relatedSymptoms = symptomEvents.filter { symptom ->
                abs(ChronoUnit.HOURS.between(context.timestamp, symptom.timestamp)) <= 6
            }

            if (relatedSymptoms.isNotEmpty()) {
                val key = "${context.location}_${context.weather}_${context.stressLevel}"
                contextToSymptoms.getOrPut(key) { mutableListOf() }.addAll(relatedSymptoms)
            }
        }

        // Analyze patterns
        for ((contextKey, symptoms) in contextToSymptoms) {
            if (symptoms.size >= 3) {
                val pattern = createEnvironmentalPattern(contextKey, symptoms)
                if (pattern.correlation > 0.5f) {
                    patterns.add(pattern)
                }
            }
        }

        return patterns.sortedByDescending { it.correlation }
    }

    /**
     * Detect cyclical patterns (weekly, monthly, seasonal)
     */
    fun detectCyclicalPatterns(
        symptomEvents: List<SymptomEvent>,
        minimumCycles: Int = 3
    ): List<CyclicalPattern> {
        val patterns = mutableListOf<CyclicalPattern>()

        // Weekly cycles
        val weeklyPattern = detectWeeklyCycle(symptomEvents)
        if (weeklyPattern.cycleStrength > 0.6f) {
            patterns.add(weeklyPattern)
        }

        // Monthly cycles
        val monthlyPattern = detectMonthlyCycle(symptomEvents)
        if (monthlyPattern.cycleStrength > 0.6f) {
            patterns.add(monthlyPattern)
        }

        return patterns.sortedByDescending { it.cycleStrength }
    }

    /**
     * Detect severity escalation patterns
     */
    fun detectEscalationPatterns(symptomEvents: List<SymptomEvent>): List<EscalationPattern> {
        val patterns = mutableListOf<EscalationPattern>()
        val sortedEvents = symptomEvents.sortedBy { it.timestamp }

        // Look for escalating sequences
        var currentSequence = mutableListOf<SymptomEvent>()

        for (i in sortedEvents.indices) {
            val event = sortedEvents[i]

            if (currentSequence.isEmpty() ||
                (event.severity > currentSequence.last().severity &&
                 ChronoUnit.HOURS.between(currentSequence.last().timestamp, event.timestamp) <= 48)) {
                currentSequence.add(event)
            } else {
                if (currentSequence.size >= 3) {
                    patterns.add(createEscalationPattern(currentSequence))
                }
                currentSequence = mutableListOf(event)
            }
        }

        // Don't forget the last sequence
        if (currentSequence.size >= 3) {
            patterns.add(createEscalationPattern(currentSequence))
        }

        return patterns.filter { it.severityIncrease > 2f }
            .sortedByDescending { it.severityIncrease }
    }

    // Private helper methods

    private fun detectDailyTimePatterns(symptomEvents: List<SymptomEvent>): List<TemporalPattern> {
        val hourlyOccurrences = symptomEvents.groupBy { event ->
            event.timestamp.atZone(java.time.ZoneId.systemDefault()).hour
        }

        val patterns = mutableListOf<TemporalPattern>()
        val averagePerHour = symptomEvents.size.toFloat() / 24f

        for ((hour, events) in hourlyOccurrences) {
            if (events.size > averagePerHour * 1.5f) { // 50% above average
                patterns.add(
                    TemporalPattern(
                        type = PatternType.DAILY_TIME,
                        description = "Symptoms frequently occur around ${formatHour(hour)}",
                        timeRange = hour to (hour + 1) % 24,
                        occurrences = events.size,
                        averageSeverity = events.map { it.severity }.average().toFloat(),
                        confidence = minOf(0.9f, events.size / averagePerHour / 5f)
                    )
                )
            }
        }

        return patterns
    }

    private fun detectWeeklyPatterns(symptomEvents: List<SymptomEvent>): List<TemporalPattern> {
        val dayOfWeekOccurrences = symptomEvents.groupBy { event ->
            event.timestamp.atZone(java.time.ZoneId.systemDefault()).dayOfWeek.value
        }

        val patterns = mutableListOf<TemporalPattern>()
        val averagePerDay = symptomEvents.size.toFloat() / 7f

        for ((dayOfWeek, events) in dayOfWeekOccurrences) {
            if (events.size > averagePerDay * 1.3f) {
                patterns.add(
                    TemporalPattern(
                        type = PatternType.WEEKLY,
                        description = "Symptoms frequently occur on ${getDayName(dayOfWeek)}",
                        timeRange = dayOfWeek to dayOfWeek,
                        occurrences = events.size,
                        averageSeverity = events.map { it.severity }.average().toFloat(),
                        confidence = minOf(0.8f, events.size / averagePerDay / 3f)
                    )
                )
            }
        }

        return patterns
    }

    private fun detectMonthlyPatterns(symptomEvents: List<SymptomEvent>): List<TemporalPattern> {
        val dayOfMonthOccurrences = symptomEvents.groupBy { event ->
            event.timestamp.atZone(java.time.ZoneId.systemDefault()).dayOfMonth
        }

        val patterns = mutableListOf<TemporalPattern>()
        val averagePerDay = symptomEvents.size.toFloat() / 30f

        for ((dayOfMonth, events) in dayOfMonthOccurrences) {
            if (events.size > averagePerDay * 2f) {
                patterns.add(
                    TemporalPattern(
                        type = PatternType.MONTHLY,
                        description = "Symptoms frequently occur on day $dayOfMonth of the month",
                        timeRange = dayOfMonth to dayOfMonth,
                        occurrences = events.size,
                        averageSeverity = events.map { it.severity }.average().toFloat(),
                        confidence = minOf(0.7f, events.size / averagePerDay / 4f)
                    )
                )
            }
        }

        return patterns
    }

    private fun createClusterPattern(cluster: List<SymptomEvent>): ClusterPattern {
        val startTime = cluster.first().timestamp
        val endTime = cluster.last().timestamp
        val duration = ChronoUnit.HOURS.between(startTime, endTime)
        val averageSeverity = cluster.map { it.severity }.average().toFloat()
        val intensity = cluster.size.toFloat() / duration.coerceAtLeast(1)

        return ClusterPattern(
            startTime = startTime,
            endTime = endTime,
            events = cluster,
            duration = duration,
            intensity = intensity,
            averageSeverity = averageSeverity,
            symptomTypes = cluster.map { it.symptomType }.distinct()
        )
    }

    private fun analyzeCombinationPattern(
        combination: Set<String>,
        entries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>
    ): CombinationPattern {
        var correlatedSymptoms = 0
        var totalSeverity = 0f

        for (entry in entries) {
            val followingSymptoms = symptomEvents.filter { symptom ->
                symptom.timestamp.isAfter(entry.timestamp) &&
                symptom.timestamp.isBefore(entry.timestamp.plus(24, ChronoUnit.HOURS))
            }

            correlatedSymptoms += followingSymptoms.size
            totalSeverity += followingSymptoms.sumOf { it.severity }
        }

        val averageSeverity = if (correlatedSymptoms > 0) totalSeverity / correlatedSymptoms else 0f
        val riskScore = (correlatedSymptoms.toFloat() / entries.size) * (averageSeverity / 10f)

        return CombinationPattern(
            foods = combination.toList(),
            occurrences = entries.size,
            correlatedSymptoms = correlatedSymptoms,
            averageSeverity = averageSeverity,
            riskScore = riskScore
        )
    }

    private fun createEnvironmentalPattern(
        contextKey: String,
        symptoms: List<SymptomEvent>
    ): EnvironmentalPattern {
        val parts = contextKey.split("_")
        val location = parts.getOrNull(0) ?: "Unknown"
        val weather = parts.getOrNull(1) ?: "Unknown"
        val stressLevel = parts.getOrNull(2)?.toIntOrNull() ?: 0

        val averageSeverity = symptoms.map { it.severity }.average().toFloat()
        val correlation = minOf(0.9f, symptoms.size / 10f)

        return EnvironmentalPattern(
            location = location,
            weather = weather,
            stressLevel = stressLevel,
            occurrences = symptoms.size,
            averageSeverity = averageSeverity,
            correlation = correlation,
            symptomTypes = symptoms.map { it.symptomType }.distinct()
        )
    }

    private fun detectWeeklyCycle(symptomEvents: List<SymptomEvent>): CyclicalPattern {
        val weeklyData = symptomEvents.groupBy { event ->
            val date = event.timestamp.atZone(java.time.ZoneId.systemDefault()).toLocalDate()
            date.get(java.time.temporal.WeekFields.ISO.weekOfYear())
        }

        val averages = weeklyData.values.map { events ->
            events.map { it.severity }.average().toFloat()
        }

        val cycleStrength = if (averages.size >= 4) {
            calculateCycleStrength(averages)
        } else 0f

        return CyclicalPattern(
            cycleType = CycleType.WEEKLY,
            period = 7f,
            cycleStrength = cycleStrength,
            peakValues = findPeaks(averages),
            description = "Weekly symptom cycle detected"
        )
    }

    private fun detectMonthlyCycle(symptomEvents: List<SymptomEvent>): CyclicalPattern {
        val monthlyData = symptomEvents.groupBy { event ->
            event.timestamp.atZone(java.time.ZoneId.systemDefault()).monthValue
        }

        val averages = monthlyData.values.map { events ->
            events.map { it.severity }.average().toFloat()
        }

        val cycleStrength = if (averages.size >= 6) {
            calculateCycleStrength(averages)
        } else 0f

        return CyclicalPattern(
            cycleType = CycleType.MONTHLY,
            period = 30f,
            cycleStrength = cycleStrength,
            peakValues = findPeaks(averages),
            description = "Monthly symptom cycle detected"
        )
    }

    private fun createEscalationPattern(sequence: List<SymptomEvent>): EscalationPattern {
        val firstSeverity = sequence.first().severity.toFloat()
        val lastSeverity = sequence.last().severity.toFloat()
        val severityIncrease = lastSeverity - firstSeverity

        val duration = ChronoUnit.HOURS.between(
            sequence.first().timestamp,
            sequence.last().timestamp
        )

        return EscalationPattern(
            events = sequence,
            startSeverity = firstSeverity,
            endSeverity = lastSeverity,
            severityIncrease = severityIncrease,
            duration = duration,
            escalationRate = severityIncrease / duration.coerceAtLeast(1).toFloat()
        )
    }

    private fun calculateCycleStrength(values: List<Float>): Float {
        if (values.size < 3) return 0f

        val mean = values.average().toFloat()
        val variance = values.map { (it - mean) * (it - mean) }.average()

        return minOf(0.9f, kotlin.math.sqrt(variance).toFloat() / mean)
    }

    private fun findPeaks(values: List<Float>): List<Int> {
        val peaks = mutableListOf<Int>()

        for (i in 1 until values.size - 1) {
            if (values[i] > values[i - 1] && values[i] > values[i + 1]) {
                peaks.add(i)
            }
        }

        return peaks
    }

    private fun formatHour(hour: Int): String {
        return when (hour) {
            0 -> "midnight"
            12 -> "noon"
            in 1..11 -> "${hour} AM"
            else -> "${hour - 12} PM"
        }
    }

    private fun getDayName(dayOfWeek: Int): String {
        return when (dayOfWeek) {
            1 -> "Monday"
            2 -> "Tuesday"
            3 -> "Wednesday"
            4 -> "Thursday"
            5 -> "Friday"
            6 -> "Saturday"
            7 -> "Sunday"
            else -> "Unknown"
        }
    }
}

// Supporting data classes

data class TemporalPattern(
    val type: PatternType,
    val description: String,
    val timeRange: Pair<Int, Int>,
    val occurrences: Int,
    val averageSeverity: Float,
    val confidence: Float
)

data class ClusterPattern(
    val startTime: Instant,
    val endTime: Instant,
    val events: List<SymptomEvent>,
    val duration: Long, // hours
    val intensity: Float, // events per hour
    val averageSeverity: Float,
    val symptomTypes: List<SymptomType>
)

data class CombinationPattern(
    val foods: List<String>,
    val occurrences: Int,
    val correlatedSymptoms: Int,
    val averageSeverity: Float,
    val riskScore: Float
)

data class EnvironmentalPattern(
    val location: String,
    val weather: String,
    val stressLevel: Int,
    val occurrences: Int,
    val averageSeverity: Float,
    val correlation: Float,
    val symptomTypes: List<SymptomType>
)

data class CyclicalPattern(
    val cycleType: CycleType,
    val period: Float, // in days
    val cycleStrength: Float,
    val peakValues: List<Int>,
    val description: String
)

data class EscalationPattern(
    val events: List<SymptomEvent>,
    val startSeverity: Float,
    val endSeverity: Float,
    val severityIncrease: Float,
    val duration: Long, // hours
    val escalationRate: Float // severity increase per hour
)

enum class PatternType {
    DAILY_TIME,
    WEEKLY,
    MONTHLY,
    SEASONAL,
    FOOD_COMBINATION,
    ENVIRONMENTAL
}

enum class CycleType {
    WEEKLY,
    MONTHLY,
    SEASONAL
}