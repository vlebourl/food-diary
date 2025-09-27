package com.fooddiary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fooddiary.data.database.converters.InstantConverter
import com.fooddiary.data.database.converters.DurationConverter
import com.fooddiary.data.models.SymptomType
import java.time.Instant
import java.time.Duration

@Entity(tableName = "symptom_events")
@TypeConverters(
    InstantConverter::class,
    DurationConverter::class,
)
data class SymptomEvent(
    @PrimaryKey val id: Long = 0L,
    val timestamp: Instant,
    val symptomType: SymptomType,
    val severity: Int, // 1-10 scale
    val duration: Duration?,
    val notes: String?,
    val isDeleted: Boolean = false,
    val createdAt: Instant = Instant.now(),
    val modifiedAt: Instant = Instant.now(),
) {
    init {
        require(severity in 1..10) {
            "Severity must be between 1 and 10"
        }
        duration?.let { dur ->
            require(!dur.isNegative) {
                "Duration must be non-negative"
            }
        }
    }

    companion object {
        fun create(
            symptomType: SymptomType,
            severity: Int,
            timestamp: Instant = Instant.now(),
            duration: Duration? = null,
            notes: String? = null,
        ) = SymptomEvent(
            timestamp = timestamp,
            symptomType = symptomType,
            severity = severity,
            duration = duration,
            notes = notes,
        )
    }

    fun softDelete(): SymptomEvent = copy(
        isDeleted = true,
        modifiedAt = Instant.now(),
    )

    fun update(
        symptomType: SymptomType? = null,
        severity: Int? = null,
        duration: Duration? = null,
        notes: String? = null,
    ): SymptomEvent = copy(
        symptomType = symptomType ?: this.symptomType,
        severity = severity ?: this.severity,
        duration = duration ?: this.duration,
        notes = notes ?: this.notes,
        modifiedAt = Instant.now(),
    )
}
