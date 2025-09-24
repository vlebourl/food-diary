package com.fooddiary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fooddiary.data.database.converters.LocalDateConverter
import com.fooddiary.data.database.converters.StringListConverter
import com.fooddiary.data.models.ReportFormat
import com.fooddiary.data.models.ReportSection
import java.time.LocalDate
import java.util.*

@Entity(tableName = "medical_reports")
@TypeConverters(
    LocalDateConverter::class,
    StringListConverter::class
)
data class MedicalReport(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val format: ReportFormat,
    val sections: List<String>, // ReportSection names as strings
    val filePath: String?, // Local file path for generated report
    val fileSize: Long?, // File size in bytes
    val generatedAt: Long = System.currentTimeMillis(),
    val isShared: Boolean = false,
    val shareHistory: List<String> = emptyList(), // Timestamps of shares as strings
    val notes: String?
) {
    companion object {
        fun create(
            title: String,
            startDate: LocalDate,
            endDate: LocalDate,
            format: ReportFormat,
            sections: List<ReportSection>,
            notes: String? = null
        ) = MedicalReport(
            title = title,
            startDate = startDate,
            endDate = endDate,
            format = format,
            sections = sections.map { it.name },
            notes = notes
        )
    }

    fun markAsGenerated(filePath: String, fileSize: Long): MedicalReport = copy(
        filePath = filePath,
        fileSize = fileSize
    )

    fun markAsShared(): MedicalReport = copy(
        isShared = true,
        shareHistory = shareHistory + System.currentTimeMillis().toString()
    )

    val isGenerated: Boolean
        get() = filePath != null

    val formattedFileSize: String
        get() = fileSize?.let { size ->
            when {
                size < 1024 -> "${size}B"
                size < 1024 * 1024 -> "${size / 1024}KB"
                else -> "${size / (1024 * 1024)}MB"
            }
        } ?: "Unknown"

    val reportSections: List<ReportSection>
        get() = sections.mapNotNull { sectionName ->
            try {
                ReportSection.valueOf(sectionName)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
}