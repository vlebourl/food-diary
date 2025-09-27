package com.fooddiary.data.database.dao

import androidx.room.*
import com.fooddiary.data.database.entities.ReportData
import com.fooddiary.data.models.ReportTypes
import kotlinx.coroutines.flow.Flow
import java.time.Instant

@Dao
interface ReportDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reportData: ReportData): Long

    @Insert
    suspend fun insertAll(reports: List<ReportData>): List<Long>

    @Update
    suspend fun update(reportData: ReportData): Int

    @Delete
    suspend fun delete(reportData: ReportData): Int

    @Query("SELECT * FROM report_data WHERE id = :id")
    suspend fun getById(id: String): ReportData?

    @Query("SELECT * FROM report_data ORDER BY generatedAt DESC")
    suspend fun getAll(): List<ReportData>

    @Query("SELECT * FROM report_data ORDER BY generatedAt DESC")
    fun getAllFlow(): Flow<List<ReportData>>

    @Query(
        """
        SELECT * FROM report_data
        WHERE reportType = :reportType
        ORDER BY generatedAt DESC
    """
    )
    suspend fun getByType(reportType: ReportTypes.ReportType): List<ReportData>

    @Query(
        """
        SELECT * FROM report_data
        WHERE generatedAt BETWEEN :startTime AND :endTime
        ORDER BY generatedAt DESC
    """
    )
    suspend fun getReportsInTimeRange(startTime: Instant, endTime: Instant): List<ReportData>

    @Query(
        """
        SELECT * FROM report_data
        WHERE patientName = :patientName
        ORDER BY generatedAt DESC
    """
    )
    suspend fun getReportsForPatient(patientName: String): List<ReportData>

    @Query(
        """
        SELECT * FROM report_data
        WHERE isShared = 1
        ORDER BY generatedAt DESC
    """
    )
    suspend fun getSharedReports(): List<ReportData>

    @Query(
        """
        SELECT * FROM report_data
        WHERE isScheduled = 1
        AND nextGenerationTime <= :currentTime
    """
    )
    suspend fun getScheduledReports(currentTime: Instant): List<ReportData>

    @Query(
        """
        SELECT * FROM report_data
        WHERE reportType = :reportType
        AND generatedAt >= :since
        ORDER BY generatedAt DESC
        LIMIT 1
    """
    )
    suspend fun getLatestReportOfType(reportType: ReportTypes.ReportType, since: Instant): ReportData?

    @Query("DELETE FROM report_data WHERE id = :id")
    suspend fun deleteById(id: String): Int

    @Query(
        """
        DELETE FROM report_data
        WHERE generatedAt < :cutoffTime
        AND isShared = 0
    """
    )
    suspend fun deleteOldReports(cutoffTime: Instant): Int

    @Query("DELETE FROM report_data")
    suspend fun deleteAll(): Int

    @Query("SELECT COUNT(*) FROM report_data")
    suspend fun getCount(): Int

    @Query(
        """
        SELECT COUNT(*) FROM report_data
        WHERE reportType = :reportType
    """
    )
    suspend fun getCountByType(reportType: ReportTypes.ReportType): Int

    @Query(
        """
        SELECT COUNT(*) FROM report_data
        WHERE generatedAt >= :since
    """
    )
    suspend fun getCountSince(since: Instant): Int

    @Query(
        """
        UPDATE report_data
        SET isShared = 1, sharedAt = :sharedAt
        WHERE id = :id
    """
    )
    suspend fun markAsShared(id: String, sharedAt: Instant): Int

    @Query(
        """
        UPDATE report_data
        SET nextGenerationTime = :nextTime
        WHERE id = :id
    """
    )
    suspend fun updateSchedule(id: String, nextTime: Instant): Int

    @Query(
        """
        SELECT * FROM report_data
        WHERE filePath IS NOT NULL
        ORDER BY generatedAt DESC
    """
    )
    suspend fun getExportedReports(): List<ReportData>

    @Query(
        """
        SELECT SUM(fileSizeBytes) FROM report_data
        WHERE filePath IS NOT NULL
    """
    )
    suspend fun getTotalStorageUsed(): Long?
}