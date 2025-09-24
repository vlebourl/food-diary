package com.fooddiary.data.database.dao

import androidx.room.*
import com.fooddiary.data.database.entities.MedicalReport
import com.fooddiary.data.models.ReportFormat
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicalReportDao {

    @Insert
    suspend fun insert(report: MedicalReport): Long

    @Update
    suspend fun update(report: MedicalReport)

    @Query("SELECT * FROM medical_reports WHERE id = :id")
    suspend fun getById(id: String): MedicalReport?

    @Query("SELECT * FROM medical_reports ORDER BY generatedAt DESC")
    fun getAll(): Flow<List<MedicalReport>>

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
}