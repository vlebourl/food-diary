package com.fooddiary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fooddiary.data.database.converters.InstantConverter
import com.fooddiary.data.models.SymptomType
import java.time.Instant
import java.util.*

@Entity(tableName = "trigger_patterns")
@TypeConverters(InstantConverter::class)
data class TriggerPattern(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val foodName: String,
    val symptomType: SymptomType,
    val correlationStrength: Float, // 0.0 to 1.0 (spec requires ≥0.6 for significance)
    val averageTimeOffsetMinutes: Int, // Time between consumption and symptom
    val occurrences: Int, // Must be ≥10 for statistical significance (FR-013)
    val confidence: Float, // Confidence interval (spec requires ≥95%)
    val lastCalculated: Long,
    val pValue: Float? = null, // Statistical significance (spec requires <0.05)
    val standardDeviation: Float? = null,
    val minTimeOffset: Int? = null,
    val maxTimeOffset: Int? = null
) {
    init {
        require(correlationStrength in 0f..1f) {
            "Correlation strength must be between 0.0 and 1.0"
        }
        require(confidence in 0f..1f) {
            "Confidence must be between 0.0 and 1.0"
        }
        require(occurrences > 0) {
            "Occurrences must be positive"
        }
        require(averageTimeOffsetMinutes >= 0) {
            "Time offset must be non-negative"
        }
        pValue?.let { p ->
            require(p in 0f..1f) {
                "P-value must be between 0.0 and 1.0"
            }
        }
    }

    companion object {
        fun create(
            foodName: String,
            symptomType: SymptomType,
            correlationStrength: Float,
            averageTimeOffsetMinutes: Int,
            occurrences: Int,
            confidence: Float,
            pValue: Float? = null,
            standardDeviation: Float? = null,
            minTimeOffset: Int? = null,
            maxTimeOffset: Int? = null
        ) = TriggerPattern(
            foodName = foodName,
            symptomType = symptomType,
            correlationStrength = correlationStrength,
            averageTimeOffsetMinutes = averageTimeOffsetMinutes,
            occurrences = occurrences,
            confidence = confidence,
            lastCalculated = System.currentTimeMillis(),
            pValue = pValue,
            standardDeviation = standardDeviation,
            minTimeOffset = minTimeOffset,
            maxTimeOffset = maxTimeOffset
        )
    }

    /**
     * Determines if this pattern meets the statistical requirements from spec:
     * - Minimum 10 data points (FR-013)
     * - Correlation coefficient ≥0.6 (FR-016)
     * - Confidence interval ≥95% (FR-016)
     * - P-value <0.05 for significance (FR-013)
     */
    val isStatisticallySignificant: Boolean
        get() = occurrences >= 10 &&
                correlationStrength >= 0.6f &&
                confidence >= 0.95f &&
                (pValue?.let { it < 0.05f } ?: false)

    val isHighConfidence: Boolean
        get() = correlationStrength >= 0.7f && confidence >= 0.85f

    val averageTimeOffsetHours: Float
        get() = averageTimeOffsetMinutes / 60f

    fun update(
        correlationStrength: Float? = null,
        averageTimeOffsetMinutes: Int? = null,
        occurrences: Int? = null,
        confidence: Float? = null,
        pValue: Float? = null,
        standardDeviation: Float? = null,
        minTimeOffset: Int? = null,
        maxTimeOffset: Int? = null
    ): TriggerPattern = copy(
        correlationStrength = correlationStrength ?: this.correlationStrength,
        averageTimeOffsetMinutes = averageTimeOffsetMinutes ?: this.averageTimeOffsetMinutes,
        occurrences = occurrences ?: this.occurrences,
        confidence = confidence ?: this.confidence,
        pValue = pValue ?: this.pValue,
        standardDeviation = standardDeviation ?: this.standardDeviation,
        minTimeOffset = minTimeOffset ?: this.minTimeOffset,
        maxTimeOffset = maxTimeOffset ?: this.maxTimeOffset,
        lastCalculated = System.currentTimeMillis()
    )
}