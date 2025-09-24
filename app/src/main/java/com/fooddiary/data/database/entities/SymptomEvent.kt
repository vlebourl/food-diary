package com.fooddiary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fooddiary.data.database.converters.InstantConverter
import com.fooddiary.data.database.converters.StringListConverter
import com.fooddiary.data.database.converters.BristolStoolTypeConverter
import com.fooddiary.data.models.SymptomType
import com.fooddiary.data.models.BristolStoolType
import java.time.Instant
import java.util.*

@Entity(tableName = "symptom_events")
@TypeConverters(
    InstantConverter::class,
    StringListConverter::class,
    BristolStoolTypeConverter::class,
)
data class SymptomEvent(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val timestamp: Instant,
    val timezone: String,
    val type: SymptomType,
    val severity: Int, // 1-10 scale as per medical standard (FR-004)
    val duration: Int?, // minutes
    val location: String?, // body location
    val bristolScale: Int?, // 1-7 scale for bowel movements (deprecated - use bristolStoolType)
    val bristolStoolType: BristolStoolType?, // Medical standard Bristol Stool Chart classification
    val suspectedTriggers: List<String>?,
    val notes: String?,
    val photoPath: String?, // local file path for medical documentation
    val createdAt: Instant = Instant.now(),
    val modifiedAt: Instant?,
    val isDeleted: Boolean = false,
    val deletedAt: Instant? = null,
) {
    init {
        require(severity in 1..10) {
            "Severity must be between 1 and 10 (medical standard)"
        }
        bristolScale?.let { scale ->
            require(scale in 1..7) {
                "Bristol Stool Scale must be between 1 and 7"
            }
        }
        // Ensure bristolStoolType consistency with bristolScale if both are provided
        if (bristolScale != null && bristolStoolType != null) {
            require(bristolScale == bristolStoolType.scale) {
                "Bristol Stool Scale ($bristolScale) must match bristolStoolType scale (${bristolStoolType.scale})"
            }
        }
        duration?.let { dur ->
            require(dur >= 0) {
                "Duration must be non-negative"
            }
        }
    }

    companion object {
        fun create(
            type: SymptomType,
            severity: Int,
            timestamp: Instant = Instant.now(),
            timezone: String = "UTC",
            duration: Int? = null,
            location: String? = null,
            bristolScale: Int? = null,
            bristolStoolType: BristolStoolType? = null,
            suspectedTriggers: List<String>? = null,
            notes: String? = null,
            photoPath: String? = null,
        ) = SymptomEvent(
            timestamp = timestamp,
            timezone = timezone,
            type = type,
            severity = severity,
            duration = duration,
            location = location,
            bristolScale = bristolScale,
            bristolStoolType = bristolStoolType,
            suspectedTriggers = suspectedTriggers,
            notes = notes,
            photoPath = photoPath,
            modifiedAt = null,
        )
    }

    fun softDelete(): SymptomEvent = copy(
        isDeleted = true,
        deletedAt = Instant.now(),
        modifiedAt = Instant.now(),
    )

    fun update(
        type: SymptomType? = null,
        severity: Int? = null,
        duration: Int? = null,
        location: String? = null,
        bristolScale: Int? = null,
        bristolStoolType: BristolStoolType? = null,
        suspectedTriggers: List<String>? = null,
        notes: String? = null,
        photoPath: String? = null,
    ): SymptomEvent = copy(
        type = type ?: this.type,
        severity = severity ?: this.severity,
        duration = duration ?: this.duration,
        location = location ?: this.location,
        bristolScale = bristolScale ?: this.bristolScale,
        bristolStoolType = bristolStoolType ?: this.bristolStoolType,
        suspectedTriggers = suspectedTriggers ?: this.suspectedTriggers,
        notes = notes ?: this.notes,
        photoPath = photoPath ?: this.photoPath,
        modifiedAt = Instant.now(),
    )

    val isOngoing: Boolean
        get() = duration == null || duration > 0

    val isActive: Boolean
        get() = !isDeleted && isOngoing
}
