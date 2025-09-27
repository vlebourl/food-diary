package com.fooddiary.data.database.dao

import androidx.room.*
import com.fooddiary.data.database.entities.MedicalReport
import com.fooddiary.data.database.entities.ReportData
import com.fooddiary.data.database.entities.ReportType
import com.fooddiary.data.models.ReportFormat
import java.time.Instant
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicalReportDao {

    @Insert
    suspend fun insert(report: MedicalReport): Long

    @Insert
    suspend fun insert(report: ReportData): Long

    @Insert
    suspend fun insertAll(reports: List<ReportData>): List<Long>

    @Update
    suspend fun update(report: MedicalReport)

    @Update
    suspend fun update(report: ReportData): Int

    @Query("SELECT * FROM medical_reports WHERE id = :id")
    suspend fun getById(id: String): MedicalReport?

    @Query("SELECT * FROM report_data WHERE id = :id")
    suspend fun getReportDataById(id: Long): ReportData?

    @Query("SELECT * FROM medical_reports ORDER BY generatedAt DESC")
    fun getAll(): Flow<List<MedicalReport>>

    @Query("SELECT * FROM report_data ORDER BY generatedAt DESC")
    suspend fun getAllReportData(): List<ReportData>

    @Query("SELECT * FROM medical_reports WHERE format = :format ORDER BY generatedAt DESC")
    fun getByFormat(format: ReportFormat): Flow<List<MedicalReport>>

    @Query("SELECT * FROM medical_reports WHERE filePath IS NOT NULL ORDER BY generatedAt DESC")
    fun getGenerated(): Flow<List<MedicalReport>>

    @Query("SELECT * FROM medical_reports WHERE isShared = 1 ORDER BY generatedAt DESC")
    fun getShared(): Flow<List<MedicalReport>>

    @Query("DELETE FROM medical_reports WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM medical_reports")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM medical_reports")
    suspend fun getCount(): Int

    @Query("SELECT COUNT(*) FROM medical_reports WHERE filePath IS NOT NULL")
    suspend fun getGeneratedCount(): Int

    @Query("SELECT SUM(COALESCE(fileSize, 0)) FROM medical_reports WHERE filePath IS NOT NULL")
    suspend fun getTotalFileSize(): Long

    // ReportData specific methods
    @Query("SELECT * FROM report_data WHERE reportType = :reportType ORDER BY generatedAt DESC")
    suspend fun getReportsByType(reportType: ReportType): List<ReportData>

    @Query(
        """
        SELECT * FROM report_data
        WHERE startDate >= :startDate AND endDate <= :endDate
        ORDER BY generatedAt DESC
    """
    )
    suspend fun getReportsByDateRange(startDate: LocalDate, endDate: LocalDate): List<ReportData>

    @Query(
        """
        SELECT * FROM report_data
        WHERE generatedAt BETWEEN :startTime AND :endTime
        ORDER BY generatedAt DESC
    """
    )
    suspend fun getReportsByGenerationTime(startTime: Instant, endTime: Instant): List<ReportData>

    @Query(
        """
        SELECT * FROM report_data
        WHERE averageSeverity BETWEEN :minSeverity AND :maxSeverity
        ORDER BY averageSeverity DESC
    """
    )
    suspend fun getReportsByAverageSeverity(minSeverity: Float, maxSeverity: Float): List<ReportData>

    @Query(
        """
        SELECT rd.* FROM report_data rd
        WHERE EXISTS (
            SELECT 1 FROM json_each(rd.topTriggerFoods)
            WHERE json_each.value = :food
        )
        ORDER BY rd.generatedAt DESC
    """
    )
    suspend fun getReportsByTriggerFood(food: String): List<ReportData>

    @Query(
        """
        SELECT
            COUNT(*) as totalReports,
            AVG(totalEntries) as avgEntries,
            AVG(totalSymptoms) as avgSymptoms,
            AVG(averageSeverity) as avgSeverity
        FROM report_data
    """
    )
    suspend fun getReportStatistics(): ReportStatistics

    @Delete
    suspend fun delete(report: ReportData): Int

    @Query("DELETE FROM report_data WHERE id = :id")
    suspend fun deleteById(id: Long): Int

    @Query(
        """
        DELETE FROM report_data
        WHERE generatedAt < :cutoffTime
    """
    )
    suspend fun deleteOldReports(cutoffTime: Instant): Int

    @Query("DELETE FROM report_data WHERE reportType = :reportType")
    suspend fun deleteReportsByType(reportType: ReportType): Int

    data class ReportStatistics(
        val totalReports: Int,
        val avgEntries: Float,
        val avgSymptoms: Float,
        val avgSeverity: Float
    )
}
