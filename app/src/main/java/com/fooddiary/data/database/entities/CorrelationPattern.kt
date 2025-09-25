package com.fooddiary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import androidx.room.ForeignKey
import androidx.room.Index
import com.fooddiary.data.database.converters.InstantConverter
import com.fooddiary.data.database.converters.DurationConverter
import java.time.Instant
import java.time.Duration

@Entity(
    tableName = "correlation_patterns",
    foreignKeys = [
        ForeignKey(
            entity = FoodEntry::class,
            parentColumns = ["id"],
            childColumns = ["foodEntryId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SymptomEvent::class,
            parentColumns = ["id"],
            childColumns = ["symptomEventId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["foodEntryId"]),
        Index(value = ["symptomEventId"]),
        Index(value = ["timeOffset"]),
        Index(value = ["confidenceScore"])
    ]
)
@TypeConverters(
    InstantConverter::class,
    DurationConverter::class,
)
data class CorrelationPattern(
    @PrimaryKey val id: Long = 0L,
    val foodEntryId: Long,
    val symptomEventId: Long,
    val timeOffset: Duration,
    val confidenceScore: Float, // 0.0-1.0
    val correlationType: CorrelationType,
    val calculatedAt: Instant = Instant.now(),
) {
    init {
        require(confidenceScore in 0.0f..1.0f) {
            "Confidence score must be between 0.0 and 1.0"
        }
        require(!timeOffset.isNegative) {
            "Time offset must be positive"
        }
    }

    companion object {
        fun create(
            foodEntryId: Long,
            symptomEventId: Long,
            timeOffset: Duration,
            confidenceScore: Float,
            correlationType: CorrelationType,
        ) = CorrelationPattern(
            foodEntryId = foodEntryId,
            symptomEventId = symptomEventId,
            timeOffset = timeOffset,
            confidenceScore = confidenceScore,
            correlationType = correlationType,
        )
    }
}

enum class CorrelationType {
    LIKELY,
    POSSIBLE,
    SUSPECTED,
    UNLIKELY
}