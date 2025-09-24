package com.fooddiary.domain.analysis

import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.TriggerPattern
import com.fooddiary.data.models.SymptomType
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.*

@Singleton
class CorrelationEngine @Inject constructor() {

    /**
     * Calculate Pearson correlation coefficient between food consumption and symptom occurrence
     * @param foodEntries List of food entries for the analysis period
     * @param symptomEvents List of symptom events for the analysis period
     * @param foodName The specific food to analyze
     * @param symptomType The specific symptom type to analyze
     * @param timeWindowHours Maximum hours after eating to consider symptoms related
     * @return Correlation coefficient between -1 and 1, or null if insufficient data
     */
    fun calculatePearsonCorrelation(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        foodName: String,
        symptomType: SymptomType,
        timeWindowHours: Int = 24
    ): CorrelationResult? {

        val relevantFoodEntries = foodEntries.filter { it.foodName == foodName }
        val relevantSymptoms = symptomEvents.filter { it.symptomType == symptomType }

        if (relevantFoodEntries.size < 5 || relevantSymptoms.isEmpty()) {
            return null // Insufficient data
        }

        // Create time-based correlation pairs
        val dataPoints = mutableListOf<Pair<Double, Double>>()
        val timeWindowMs = timeWindowHours * 60 * 60 * 1000L

        // For each day, calculate food exposure and symptom occurrence
        val allDays = (foodEntries + symptomEvents.map {
            FoodEntry(foodName = "", portions = 0.0, portionUnit = "",
                     mealType = "", ingredients = emptyList(), timestamp = it.timestamp)
        }).map {
            it.timestamp.atZone(java.time.ZoneId.systemDefault()).toLocalDate()
        }.distinct().sorted()

        for (day in allDays) {
            val dayStart = day.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()
            val dayEnd = day.plusDays(1).atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()

            // Food exposure on this day (binary: 1 if consumed, 0 if not)
            val foodExposure = if (relevantFoodEntries.any {
                it.timestamp >= dayStart && it.timestamp < dayEnd
            }) 1.0 else 0.0

            // Symptom occurrence within time window of food consumption
            val symptomOccurrence = if (foodExposure == 1.0) {
                val dayFoodEntries = relevantFoodEntries.filter {
                    it.timestamp >= dayStart && it.timestamp < dayEnd
                }
                val hasRelatedSymptoms = dayFoodEntries.any { foodEntry ->
                    relevantSymptoms.any { symptom ->
                        val timeDiff = symptom.timestamp.toEpochMilli() - foodEntry.timestamp.toEpochMilli()
                        timeDiff in 0..timeWindowMs
                    }
                }
                if (hasRelatedSymptoms) 1.0 else 0.0
            } else {
                // Check if symptoms occurred without food exposure
                if (relevantSymptoms.any {
                    it.timestamp >= dayStart && it.timestamp < dayEnd
                }) 1.0 else 0.0
            }

            dataPoints.add(Pair(foodExposure, symptomOccurrence))
        }

        if (dataPoints.size < 5) return null

        // Calculate Pearson correlation
        val n = dataPoints.size
        val sumX = dataPoints.sumOf { it.first }
        val sumY = dataPoints.sumOf { it.second }
        val sumXY = dataPoints.sumOf { it.first * it.second }
        val sumX2 = dataPoints.sumOf { it.first * it.first }
        val sumY2 = dataPoints.sumOf { it.second * it.second }

        val numerator = n * sumXY - sumX * sumY
        val denominator = sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY))

        val correlation = if (denominator == 0.0) 0.0 else numerator / denominator

        // Calculate p-value (simplified approximation)
        val tStatistic = if (correlation == 0.0) 0.0 else
            correlation * sqrt((n - 2) / (1 - correlation * correlation))
        val pValue = calculatePValue(abs(tStatistic), n - 2)

        // Calculate confidence intervals
        val confidence95 = calculateConfidenceInterval(correlation, n, 0.95)

        return CorrelationResult(
            correlation = correlation,
            pValue = pValue,
            sampleSize = n,
            confidenceInterval95 = confidence95,
            isStatisticallySignificant = pValue < 0.05 && abs(correlation) >= 0.6,
            strength = getCorrelationStrength(abs(correlation))
        )
    }

    /**
     * Calculate chi-square test for independence between food consumption and symptom occurrence
     */
    fun calculateChiSquare(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        foodName: String,
        symptomType: SymptomType,
        timeWindowHours: Int = 24
    ): ChiSquareResult? {

        val timeWindowMs = timeWindowHours * 60 * 60 * 1000L
        val relevantFoodEntries = foodEntries.filter { it.foodName == foodName }
        val relevantSymptoms = symptomEvents.filter { it.symptomType == symptomType }

        // Create 2x2 contingency table
        var foodWithSymptom = 0
        var foodWithoutSymptom = 0
        var noFoodWithSymptom = 0
        var noFoodWithoutSymptom = 0

        // Get all unique days
        val allDays = (foodEntries + symptomEvents.map {
            FoodEntry(foodName = "", portions = 0.0, portionUnit = "",
                     mealType = "", ingredients = emptyList(), timestamp = it.timestamp)
        }).map {
            it.timestamp.atZone(java.time.ZoneId.systemDefault()).toLocalDate()
        }.distinct()

        for (day in allDays) {
            val dayStart = day.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()
            val dayEnd = day.plusDays(1).atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()

            val hadFood = relevantFoodEntries.any {
                it.timestamp >= dayStart && it.timestamp < dayEnd
            }

            val hadSymptom = if (hadFood) {
                // Check for symptoms within time window after eating
                relevantFoodEntries.filter {
                    it.timestamp >= dayStart && it.timestamp < dayEnd
                }.any { foodEntry ->
                    relevantSymptoms.any { symptom ->
                        val timeDiff = symptom.timestamp.toEpochMilli() - foodEntry.timestamp.toEpochMilli()
                        timeDiff in 0..timeWindowMs
                    }
                }
            } else {
                // Check for symptoms on days without food
                relevantSymptoms.any {
                    it.timestamp >= dayStart && it.timestamp < dayEnd
                }
            }

            when {
                hadFood && hadSymptom -> foodWithSymptom++
                hadFood && !hadSymptom -> foodWithoutSymptom++
                !hadFood && hadSymptom -> noFoodWithSymptom++
                !hadFood && !hadSymptom -> noFoodWithoutSymptom++
            }
        }

        val total = foodWithSymptom + foodWithoutSymptom + noFoodWithSymptom + noFoodWithoutSymptom
        if (total < 10) return null // Insufficient data

        // Calculate expected frequencies
        val totalFood = foodWithSymptom + foodWithoutSymptom
        val totalNoFood = noFoodWithSymptom + noFoodWithoutSymptom
        val totalSymptom = foodWithSymptom + noFoodWithSymptom
        val totalNoSymptom = foodWithoutSymptom + noFoodWithoutSymptom

        val expectedFoodSymptom = (totalFood * totalSymptom).toDouble() / total
        val expectedFoodNoSymptom = (totalFood * totalNoSymptom).toDouble() / total
        val expectedNoFoodSymptom = (totalNoFood * totalSymptom).toDouble() / total
        val expectedNoFoodNoSymptom = (totalNoFood * totalNoSymptom).toDouble() / total

        // Calculate chi-square statistic
        val chiSquare =
            ((foodWithSymptom - expectedFoodSymptom).pow(2) / expectedFoodSymptom) +
            ((foodWithoutSymptom - expectedFoodNoSymptom).pow(2) / expectedFoodNoSymptom) +
            ((noFoodWithSymptom - expectedNoFoodSymptom).pow(2) / expectedNoFoodSymptom) +
            ((noFoodWithoutSymptom - expectedNoFoodNoSymptom).pow(2) / expectedNoFoodNoSymptom)

        val degreesOfFreedom = 1
        val pValue = calculateChiSquarePValue(chiSquare, degreesOfFreedom)

        return ChiSquareResult(
            chiSquareStatistic = chiSquare,
            pValue = pValue,
            degreesOfFreedom = degreesOfFreedom,
            contingencyTable = ContingencyTable(
                foodWithSymptom, foodWithoutSymptom,
                noFoodWithSymptom, noFoodWithoutSymptom
            ),
            isStatisticallySignificant = pValue < 0.05
        )
    }

    /**
     * Generate comprehensive correlation analysis for a food-symptom pair
     */
    fun analyzeCorrelation(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        foodName: String,
        symptomType: SymptomType,
        timeWindowHours: Int = 24
    ): CorrelationAnalysis? {

        val pearson = calculatePearsonCorrelation(
            foodEntries, symptomEvents, foodName, symptomType, timeWindowHours
        )

        val chiSquare = calculateChiSquare(
            foodEntries, symptomEvents, foodName, symptomType, timeWindowHours
        )

        if (pearson == null && chiSquare == null) return null

        // Calculate additional metrics
        val relevantFoodEntries = foodEntries.filter { it.foodName == foodName }
        val relevantSymptoms = symptomEvents.filter { it.symptomType == symptomType }
        val timeWindowMs = timeWindowHours * 60 * 60 * 1000L

        val relatedSymptoms = mutableListOf<SymptomEvent>()
        val timeOffsets = mutableListOf<Int>()

        for (foodEntry in relevantFoodEntries) {
            for (symptom in relevantSymptoms) {
                val timeDiff = symptom.timestamp.toEpochMilli() - foodEntry.timestamp.toEpochMilli()
                if (timeDiff in 0..timeWindowMs) {
                    relatedSymptoms.add(symptom)
                    timeOffsets.add((timeDiff / (1000 * 60)).toInt()) // Convert to minutes
                }
            }
        }

        val averageTimeOffset = if (timeOffsets.isNotEmpty()) {
            timeOffsets.average().toInt()
        } else 0

        val averageSeverity = if (relatedSymptoms.isNotEmpty()) {
            relatedSymptoms.map { it.severity }.average()
        } else 0.0

        return CorrelationAnalysis(
            foodName = foodName,
            symptomType = symptomType,
            pearsonResult = pearson,
            chiSquareResult = chiSquare,
            occurrences = relatedSymptoms.size,
            averageTimeOffsetMinutes = averageTimeOffset,
            averageSeverity = averageSeverity,
            isStatisticallySignificant = (pearson?.isStatisticallySignificant == true) ||
                                       (chiSquare?.isStatisticallySignificant == true),
            recommendedAction = getRecommendedAction(pearson, chiSquare, relatedSymptoms.size)
        )
    }

    private fun calculatePValue(tStatistic: Double, degreesOfFreedom: Int): Double {
        // Simplified p-value calculation using t-distribution approximation
        return when {
            abs(tStatistic) > 2.576 -> 0.01  // 99% confidence
            abs(tStatistic) > 1.96 -> 0.05   // 95% confidence
            abs(tStatistic) > 1.645 -> 0.10  // 90% confidence
            else -> 0.5
        }
    }

    private fun calculateChiSquarePValue(chiSquare: Double, df: Int): Double {
        // Simplified chi-square p-value calculation
        return when {
            chiSquare > 6.635 -> 0.01  // p < 0.01
            chiSquare > 3.841 -> 0.05  // p < 0.05
            chiSquare > 2.706 -> 0.10  // p < 0.10
            else -> 0.5
        }
    }

    private fun calculateConfidenceInterval(correlation: Double, n: Int, confidence: Double): Pair<Double, Double> {
        // Fisher's z-transformation for confidence intervals
        val z = 0.5 * ln((1 + correlation) / (1 - correlation))
        val se = 1.0 / sqrt(n - 3.0)
        val zCritical = if (confidence == 0.95) 1.96 else 1.645

        val lowerZ = z - zCritical * se
        val upperZ = z + zCritical * se

        val lower = (exp(2 * lowerZ) - 1) / (exp(2 * lowerZ) + 1)
        val upper = (exp(2 * upperZ) - 1) / (exp(2 * upperZ) + 1)

        return Pair(lower, upper)
    }

    private fun getCorrelationStrength(correlation: Double): CorrelationStrength {
        return when {
            correlation >= 0.8 -> CorrelationStrength.VERY_STRONG
            correlation >= 0.6 -> CorrelationStrength.STRONG
            correlation >= 0.4 -> CorrelationStrength.MODERATE
            correlation >= 0.2 -> CorrelationStrength.WEAK
            else -> CorrelationStrength.VERY_WEAK
        }
    }

    private fun getRecommendedAction(
        pearson: CorrelationResult?,
        chiSquare: ChiSquareResult?,
        occurrences: Int
    ): RecommendedAction {
        val isSignificant = (pearson?.isStatisticallySignificant == true) ||
                           (chiSquare?.isStatisticallySignificant == true)
        val strongCorrelation = pearson?.let { abs(it.correlation) >= 0.6 } == true

        return when {
            isSignificant && strongCorrelation && occurrences >= 10 -> RecommendedAction.ELIMINATE
            isSignificant && occurrences >= 5 -> RecommendedAction.REDUCE
            occurrences >= 3 -> RecommendedAction.MONITOR
            else -> RecommendedAction.CONTINUE
        }
    }
}

data class CorrelationResult(
    val correlation: Double,
    val pValue: Double,
    val sampleSize: Int,
    val confidenceInterval95: Pair<Double, Double>,
    val isStatisticallySignificant: Boolean,
    val strength: CorrelationStrength
)

data class ChiSquareResult(
    val chiSquareStatistic: Double,
    val pValue: Double,
    val degreesOfFreedom: Int,
    val contingencyTable: ContingencyTable,
    val isStatisticallySignificant: Boolean
)

data class ContingencyTable(
    val foodWithSymptom: Int,
    val foodWithoutSymptom: Int,
    val noFoodWithSymptom: Int,
    val noFoodWithoutSymptom: Int
)

data class CorrelationAnalysis(
    val foodName: String,
    val symptomType: SymptomType,
    val pearsonResult: CorrelationResult?,
    val chiSquareResult: ChiSquareResult?,
    val occurrences: Int,
    val averageTimeOffsetMinutes: Int,
    val averageSeverity: Double,
    val isStatisticallySignificant: Boolean,
    val recommendedAction: RecommendedAction
)

enum class CorrelationStrength {
    VERY_WEAK, WEAK, MODERATE, STRONG, VERY_STRONG
}

enum class RecommendedAction {
    CONTINUE, MONITOR, REDUCE, ELIMINATE
}