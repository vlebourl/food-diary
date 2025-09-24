package com.fooddiary.domain.usecase

import com.fooddiary.data.database.entities.TriggerPattern
import com.fooddiary.data.models.SymptomType
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.data.repository.SymptomEventRepository
import com.fooddiary.data.repository.TriggerPatternRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.sqrt

class CalculateCorrelationsUseCase @Inject constructor(
    private val foodEntryRepository: FoodEntryRepository,
    private val symptomEventRepository: SymptomEventRepository,
    private val triggerPatternRepository: TriggerPatternRepository
) {
    suspend operator fun invoke(
        startDate: LocalDate,
        endDate: LocalDate,
        minOccurrences: Int = 10,
        minCorrelation: Float = 0.6f,
        maxTimeWindowHours: Int = 24
    ): Result<List<TriggerPattern>> {
        return try {
            val startInstant = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
            val endInstant = endDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant()

            // Get all food entries and symptoms in the date range
            val foodEntries = foodEntryRepository.getByDateRange(startInstant, endInstant).first()
            val symptomEvents = symptomEventRepository.getByDateRange(startInstant, endInstant).first()

            val patterns = mutableListOf<TriggerPattern>()

            // Group symptoms by type
            val symptomsByType = symptomEvents.groupBy { it.symptomType }

            // For each food item, calculate correlation with symptoms
            for (foodEntry in foodEntries) {
                for ((symptomType, symptoms) in symptomsByType) {
                    // Find symptoms that occurred within time window after eating
                    val relatedSymptoms = symptoms.filter { symptom ->
                        val timeDiff = symptom.timestamp.toEpochMilli() - foodEntry.timestamp.toEpochMilli()
                        timeDiff in 0..(maxTimeWindowHours * 60 * 60 * 1000)
                    }

                    if (relatedSymptoms.size >= minOccurrences) {
                        // Calculate correlation strength
                        val correlation = calculateCorrelationStrength(
                            foodEntry.foodName,
                            symptomType,
                            foodEntries,
                            symptomEvents,
                            maxTimeWindowHours
                        )

                        if (correlation >= minCorrelation) {
                            val avgTimeOffset = relatedSymptoms.map { symptom ->
                                ((symptom.timestamp.toEpochMilli() - foodEntry.timestamp.toEpochMilli()) / (1000 * 60)).toInt()
                            }.average().toInt()

                            val pattern = TriggerPattern(
                                foodName = foodEntry.foodName,
                                symptomType = symptomType,
                                correlationStrength = correlation,
                                occurrences = relatedSymptoms.size,
                                averageTimeOffsetMinutes = avgTimeOffset,
                                confidenceLevel = calculateConfidenceLevel(relatedSymptoms.size, correlation),
                                isStatisticallySignificant = isStatisticallySignificant(relatedSymptoms.size, correlation),
                                lastOccurrenceDate = relatedSymptoms.maxByOrNull { it.timestamp }?.timestamp?.toString() ?: ""
                            )

                            patterns.add(pattern)

                            // Save or update pattern in database
                            val existingPattern = triggerPatternRepository.getByFood(foodEntry.foodName).first()
                                .find { it.symptomType == symptomType }

                            if (existingPattern != null) {
                                triggerPatternRepository.update(pattern.copy(id = existingPattern.id))
                            } else {
                                triggerPatternRepository.insert(pattern)
                            }
                        }
                    }
                }
            }

            // Clean up weak patterns
            triggerPatternRepository.deleteWeakPatterns(minOccurrences, minCorrelation)

            Result.success(patterns)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun calculateCorrelationStrength(
        foodName: String,
        symptomType: SymptomType,
        allFoodEntries: List<com.fooddiary.data.database.entities.FoodEntry>,
        allSymptoms: List<com.fooddiary.data.database.entities.SymptomEvent>,
        maxTimeWindowHours: Int
    ): Float {
        // Calculate Pearson correlation coefficient
        val foodOccurrences = allFoodEntries.filter { it.foodName == foodName }
        val symptomOccurrences = allSymptoms.filter { it.symptomType == symptomType }

        if (foodOccurrences.isEmpty() || symptomOccurrences.isEmpty()) {
            return 0f
        }

        // Create binary vectors for correlation calculation
        val timeSlots = mutableListOf<Pair<Boolean, Boolean>>() // (hasFood, hasSymptom)

        // Group by time windows
        for (foodEntry in foodOccurrences) {
            val windowEnd = foodEntry.timestamp.toEpochMilli() + (maxTimeWindowHours * 60 * 60 * 1000)
            val hasSymptom = symptomOccurrences.any { symptom ->
                symptom.timestamp.toEpochMilli() in foodEntry.timestamp.toEpochMilli()..windowEnd
            }
            timeSlots.add(Pair(true, hasSymptom))
        }

        // Calculate correlation
        val n = timeSlots.size.toDouble()
        val sumX = timeSlots.count { it.first }.toDouble()
        val sumY = timeSlots.count { it.second }.toDouble()
        val sumXY = timeSlots.count { it.first && it.second }.toDouble()
        val sumX2 = sumX // Since X is binary, sum(X^2) = sum(X)
        val sumY2 = sumY // Since Y is binary, sum(Y^2) = sum(Y)

        val numerator = n * sumXY - sumX * sumY
        val denominator = sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY))

        return if (denominator == 0.0) 0f else (numerator / denominator).toFloat().coerceIn(0f, 1f)
    }

    private fun calculateConfidenceLevel(occurrences: Int, correlation: Float): Float {
        // Confidence increases with more occurrences and stronger correlation
        val occurrenceWeight = minOf(occurrences / 30f, 1f) // Max out at 30 occurrences
        val correlationWeight = correlation

        return (occurrenceWeight * 0.4f + correlationWeight * 0.6f)
    }

    private fun isStatisticallySignificant(occurrences: Int, correlation: Float): Boolean {
        // Simplified statistical significance check
        // In real implementation, would use proper statistical tests (e.g., Chi-square, t-test)
        return occurrences >= 10 && correlation >= 0.6f && calculatePValue(occurrences, correlation) < 0.05f
    }

    private fun calculatePValue(occurrences: Int, correlation: Float): Float {
        // Simplified p-value calculation
        // In real implementation, would use proper statistical distribution
        val tStatistic = correlation * sqrt((occurrences - 2) / (1 - correlation * correlation))

        // Using a simplified approximation for p-value
        return when {
            abs(tStatistic) > 2.576 -> 0.01f  // 99% confidence
            abs(tStatistic) > 1.96 -> 0.05f   // 95% confidence
            abs(tStatistic) > 1.645 -> 0.10f  // 90% confidence
            else -> 0.5f
        }
    }
}