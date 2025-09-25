package com.fooddiary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fooddiary.data.database.converters.InstantConverter
import com.fooddiary.data.database.converters.StringListConverter
import com.fooddiary.data.database.converters.LocalTimeConverter
import java.time.Instant
import java.time.LocalTime

@Entity(tableName = "user_preferences")
@TypeConverters(
    InstantConverter::class,
    StringListConverter::class,
    LocalTimeConverter::class,
)
data class UserPreferences(
    @PrimaryKey val id: Long = 1L, // Single row table
    val correlationTimeWindowHours: Int = 3, // Default 2-4 hours
    val measurementUnit: MeasurementUnit = MeasurementUnit.METRIC,
    val notificationEnabled: Boolean = true,
    val notificationTime: LocalTime? = null,
    val dataRetentionMonths: Int = 12,
    val exportFormat: ExportFormat = ExportFormat.JSON,
    val customSymptomTypes: List<String> = emptyList(),
    val triggerAlertEnabled: Boolean = false,
    val reportTemplate: ReportTemplate = ReportTemplate.BASIC,
    val privacyMode: Boolean = true,
    val createdAt: Instant = Instant.now(),
    val modifiedAt: Instant = Instant.now(),
) {
    init {
        require(correlationTimeWindowHours in 1..48) {
            "Correlation time window must be between 1-48 hours"
        }
        require(dataRetentionMonths in 1..60) {
            "Data retention must be between 1-60 months"
        }
        require(customSymptomTypes.size <= 20) {
            "Maximum 20 custom symptom types allowed"
        }
    }

    companion object {
        fun createDefault() = UserPreferences()
    }

    fun update(
        correlationTimeWindowHours: Int? = null,
        measurementUnit: MeasurementUnit? = null,
        notificationEnabled: Boolean? = null,
        notificationTime: LocalTime? = null,
        dataRetentionMonths: Int? = null,
        exportFormat: ExportFormat? = null,
        customSymptomTypes: List<String>? = null,
        triggerAlertEnabled: Boolean? = null,
        reportTemplate: ReportTemplate? = null,
        privacyMode: Boolean? = null,
    ): UserPreferences = copy(
        correlationTimeWindowHours = correlationTimeWindowHours ?: this.correlationTimeWindowHours,
        measurementUnit = measurementUnit ?: this.measurementUnit,
        notificationEnabled = notificationEnabled ?: this.notificationEnabled,
        notificationTime = notificationTime ?: this.notificationTime,
        dataRetentionMonths = dataRetentionMonths ?: this.dataRetentionMonths,
        exportFormat = exportFormat ?: this.exportFormat,
        customSymptomTypes = customSymptomTypes ?: this.customSymptomTypes,
        triggerAlertEnabled = triggerAlertEnabled ?: this.triggerAlertEnabled,
        reportTemplate = reportTemplate ?: this.reportTemplate,
        privacyMode = privacyMode ?: this.privacyMode,
        modifiedAt = Instant.now(),
    )
}

enum class MeasurementUnit {
    METRIC,
    IMPERIAL
}

enum class ExportFormat {
    JSON,
    CSV
}

enum class ReportTemplate {
    BASIC,
    DETAILED,
    MEDICAL
}