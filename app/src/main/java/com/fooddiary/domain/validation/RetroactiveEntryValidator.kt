package com.fooddiary.domain.validation

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetroactiveEntryValidator @Inject constructor() {

    companion object {
        const val MAX_RETROACTIVE_DAYS = 30
        const val ACCURACY_WARNING_DAYS = 7
        const val CONFIDENCE_WARNING_DAYS = 3
    }

    data class ValidationResult(
        val isValid: Boolean,
        val validationLevel: ValidationLevel,
        val daysBack: Long,
        val accuracyScore: Float,
        val warnings: List<ValidationWarning>
    )

    enum class ValidationLevel {
        HIGH_CONFIDENCE,    // 0-3 days
        MEDIUM_CONFIDENCE,  // 4-7 days
        LOW_CONFIDENCE,     // 8-30 days
        REJECTED           // >30 days
    }

    enum class ValidationWarning {
        ACCURACY_DEGRADATION,
        MEMORY_RELIABILITY,
        TOO_OLD,
        WEEKEND_EFFECT,
        HOLIDAY_EFFECT
    }

    fun validateRetroactiveEntry(entryTimestamp: Instant): ValidationResult {
        val now = Instant.now()
        val daysBack = ChronoUnit.DAYS.between(
            entryTimestamp.atZone(ZoneId.systemDefault()).toLocalDate(),
            now.atZone(ZoneId.systemDefault()).toLocalDate()
        )

        return when {
            daysBack > MAX_RETROACTIVE_DAYS -> ValidationResult(
                isValid = false,
                validationLevel = ValidationLevel.REJECTED,
                daysBack = daysBack,
                accuracyScore = 0.0f,
                warnings = listOf(ValidationWarning.TOO_OLD)
            )

            daysBack <= CONFIDENCE_WARNING_DAYS -> ValidationResult(
                isValid = true,
                validationLevel = ValidationLevel.HIGH_CONFIDENCE,
                daysBack = daysBack,
                accuracyScore = calculateAccuracyScore(daysBack, entryTimestamp),
                warnings = generateWarnings(daysBack, entryTimestamp)
            )

            daysBack <= ACCURACY_WARNING_DAYS -> ValidationResult(
                isValid = true,
                validationLevel = ValidationLevel.MEDIUM_CONFIDENCE,
                daysBack = daysBack,
                accuracyScore = calculateAccuracyScore(daysBack, entryTimestamp),
                warnings = generateWarnings(daysBack, entryTimestamp)
            )

            else -> ValidationResult(
                isValid = true,
                validationLevel = ValidationLevel.LOW_CONFIDENCE,
                daysBack = daysBack,
                accuracyScore = calculateAccuracyScore(daysBack, entryTimestamp),
                warnings = generateWarnings(daysBack, entryTimestamp)
            )
        }
    }

    private fun calculateAccuracyScore(daysBack: Long, entryTimestamp: Instant): Float {
        // Accuracy degrades over time following research on memory reliability
        val baseAccuracy = when {
            daysBack == 0L -> 0.95f
            daysBack <= 1 -> 0.90f
            daysBack <= 3 -> 0.80f
            daysBack <= 7 -> 0.65f
            daysBack <= 14 -> 0.45f
            daysBack <= 30 -> 0.25f
            else -> 0.0f
        }

        // Apply weekend effect (people tend to remember weekend meals less accurately)
        val dayOfWeek = entryTimestamp.atZone(ZoneId.systemDefault()).dayOfWeek.value
        val weekendPenalty = if (dayOfWeek == 6 || dayOfWeek == 7) 0.1f else 0.0f

        return (baseAccuracy - weekendPenalty).coerceAtLeast(0.0f)
    }

    private fun generateWarnings(daysBack: Long, entryTimestamp: Instant): List<ValidationWarning> {
        val warnings = mutableListOf<ValidationWarning>()

        if (daysBack >= ACCURACY_WARNING_DAYS) {
            warnings.add(ValidationWarning.ACCURACY_DEGRADATION)
        }

        if (daysBack >= CONFIDENCE_WARNING_DAYS) {
            warnings.add(ValidationWarning.MEMORY_RELIABILITY)
        }

        val dayOfWeek = entryTimestamp.atZone(ZoneId.systemDefault()).dayOfWeek.value
        if (dayOfWeek == 6 || dayOfWeek == 7) {
            warnings.add(ValidationWarning.WEEKEND_EFFECT)
        }

        return warnings
    }

    fun getValidationMessage(result: ValidationResult): String {
        return when (result.validationLevel) {
            ValidationLevel.HIGH_CONFIDENCE ->
                "Entry is recent (${result.daysBack} day${if (result.daysBack == 1L) "" else "s"} ago) with high accuracy."

            ValidationLevel.MEDIUM_CONFIDENCE ->
                "Entry is ${result.daysBack} days old. Some details might be less accurate."

            ValidationLevel.LOW_CONFIDENCE ->
                "Entry is ${result.daysBack} days old. Accuracy may be significantly reduced."

            ValidationLevel.REJECTED ->
                "Entry is too old (${result.daysBack} days). Maximum allowed: $MAX_RETROACTIVE_DAYS days."
        }
    }

    fun getWarningMessages(warnings: List<ValidationWarning>): List<String> {
        return warnings.map { warning ->
            when (warning) {
                ValidationWarning.ACCURACY_DEGRADATION ->
                    "Memory accuracy decreases significantly after ${ACCURACY_WARNING_DAYS} days"

                ValidationWarning.MEMORY_RELIABILITY ->
                    "Consider providing additional context or notes to improve reliability"

                ValidationWarning.TOO_OLD ->
                    "Entry exceeds maximum allowed retroactive period"

                ValidationWarning.WEEKEND_EFFECT ->
                    "Weekend entries may have reduced accuracy due to irregular eating patterns"

                ValidationWarning.HOLIDAY_EFFECT ->
                    "Holiday periods may affect memory accuracy for food details"
            }
        }
    }

    fun shouldRequireAdditionalVerification(result: ValidationResult): Boolean {
        return result.validationLevel == ValidationLevel.LOW_CONFIDENCE ||
                result.warnings.contains(ValidationWarning.ACCURACY_DEGRADATION)
    }

    fun getRecommendedActions(result: ValidationResult): List<String> {
        val actions = mutableListOf<String>()

        when (result.validationLevel) {
            ValidationLevel.HIGH_CONFIDENCE -> {
                // No special actions needed
            }

            ValidationLevel.MEDIUM_CONFIDENCE -> {
                actions.add("Consider adding more detailed notes about the meal")
                actions.add("Include estimated portion sizes if uncertain")
            }

            ValidationLevel.LOW_CONFIDENCE -> {
                actions.add("Add detailed notes about what you remember")
                actions.add("Mark uncertain details clearly")
                actions.add("Consider consulting photos or receipts from that day")
            }

            ValidationLevel.REJECTED -> {
                actions.add("Entry cannot be added due to age limit")
                actions.add("Consider starting fresh tracking going forward")
            }
        }

        if (result.warnings.contains(ValidationWarning.WEEKEND_EFFECT)) {
            actions.add("Pay extra attention to meal timing and portion accuracy")
        }

        return actions
    }
}