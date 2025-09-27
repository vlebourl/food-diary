package com.fooddiary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fooddiary.data.database.converters.InstantConverter
import com.fooddiary.data.database.converters.LocalDateConverter
import com.fooddiary.data.database.converters.StringListConverter
import com.fooddiary.data.database.converters.StringMapConverter
import com.fooddiary.data.models.SymptomType
import java.time.Instant
import java.time.LocalDate

@Entity(tableName = "report_data")
@TypeConverters(
    InstantConverter::class,
    LocalDateConverter::class,
    StringListConverter::class,
    StringMapConverter::class,
)
data class ReportData(
    @PrimaryKey val id: Long = 0L,
    val reportType: ReportType,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val totalEntries: Int,
    val totalSymptoms: Int,
    val topTriggerFoods: List<String>,
    val symptomFrequency: Map<String, String>, // SymptomType name to count mapping
    val averageSeverity: Float,
    val reportData: String, // JSON blob with detailed analysis
    val generatedAt: Instant = Instant.now(),
) {
    init {
        require(startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
            "Start date must be before or equal to end date"
        }
        require(totalEntries >= 0) {
            "Total entries must be non-negative"
        }
        require(totalSymptoms >= 0) {
            "Total symptoms must be non-negative"
        }
        require(averageSeverity in 0.0f..10.0f) {
            "Average severity must be between 0 and 10"
        }
    }

    companion object {
        fun create(
            reportType: ReportType,
            startDate: LocalDate,
            endDate: LocalDate,
            totalEntries: Int,
            totalSymptoms: Int,
            topTriggerFoods: List<String>,
            symptomFrequency: Map<String, String>,
            averageSeverity: Float,
            reportData: String,
        ) = ReportData(
            reportType = reportType,
            startDate = startDate,
            endDate = endDate,
            totalEntries = totalEntries,
            totalSymptoms = totalSymptoms,
            topTriggerFoods = topTriggerFoods,
            symptomFrequency = symptomFrequency,
            averageSeverity = averageSeverity,
            reportData = reportData,
        )
    }
}

enum class ReportType {
    WEEKLY,
    MONTHLY,
    CUSTOM_RANGE
}