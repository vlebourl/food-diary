package com.fooddiary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import androidx.room.ForeignKey
import androidx.room.Index
import com.fooddiary.data.database.converters.InstantConverter
import com.fooddiary.data.database.converters.DurationConverter
import com.fooddiary.data.models.ConfidenceLevel
import java.time.Instant
import java.time.Duration

@Entity(
    tableName = "correlation_patterns",
    foreignKeys = [
        ForeignKey(
            entity = FoodEntry::class,
            parentColumns = ["id"],
            childColumns = ["foodId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SymptomEvent::class,
            parentColumns = ["id"],
            childColumns = ["symptomId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["foodId"]),
        Index(value = ["symptomId"]),
        Index(value = ["timeOffsetHours"]),
        Index(value = ["correlationStrength"])
    ]
)
@TypeConverters(
    InstantConverter::class,
    DurationConverter::class,
)
data class CorrelationPattern(
    @PrimaryKey val id: Long = 0L,
    val foodId: Long,
    val symptomId: Long,
    val correlationStrength: Float, // 0.0-1.0
    val confidenceLevel: ConfidenceLevel,
    val timeOffsetHours: Int,
    val occurrenceCount: Int,
    val isActive: Boolean = true,
    val calculatedAt: Instant = Instant.now(),
) {
    init {
        require(correlationStrength in 0.0f..1.0f) {
            "Correlation strength must be between 0.0 and 1.0"
        }
        require(timeOffsetHours >= 0) {
            "Time offset must be non-negative"
        }
        require(occurrenceCount > 0) {
            "Occurrence count must be positive"
        }
    }

    companion object {
        fun create(
            foodId: Long,
            symptomId: Long,
            correlationStrength: Float,
            confidenceLevel: ConfidenceLevel,
            timeOffsetHours: Int,
            occurrenceCount: Int,
        ) = CorrelationPattern(
            foodId = foodId,
            symptomId = symptomId,
            correlationStrength = correlationStrength,
            confidenceLevel = confidenceLevel,
            timeOffsetHours = timeOffsetHours,
            occurrenceCount = occurrenceCount,
        )
    }
}

enum class CorrelationType {
    LIKELY,
    POSSIBLE,
    SUSPECTED,
    UNLIKELY
}