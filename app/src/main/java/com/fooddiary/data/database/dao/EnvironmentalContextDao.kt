package com.fooddiary.data.database.dao

import androidx.room.*
import com.fooddiary.data.database.entities.EnvironmentalContext
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface EnvironmentalContextDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(context: EnvironmentalContext)

    @Query("SELECT * FROM environmental_contexts WHERE date = :date")
    suspend fun getByDate(date: LocalDate): EnvironmentalContext?

    @Query("""
        SELECT * FROM environmental_contexts
        WHERE date BETWEEN :startDate AND :endDate
        ORDER BY date DESC
    """)
    fun getByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<EnvironmentalContext>>

    @Query("""
        SELECT AVG(stressLevel) FROM environmental_contexts
        WHERE date >= date('now', '-' || :days || ' days')
    """)
    fun getAverageStress(days: Int): Flow<Float>

    @Query("""
        SELECT AVG(sleepHours) FROM environmental_contexts
        WHERE date >= date('now', '-' || :days || ' days')
    """)
    fun getAverageSleep(days: Int): Flow<Float>

    @Query("""
        SELECT AVG(sleepQuality) FROM environmental_contexts
        WHERE date >= date('now', '-' || :days || ' days')
    """)
    fun getAverageSleepQuality(days: Int): Flow<Float>

    @Query("""
        SELECT SUM(COALESCE(exerciseMinutes, 0)) FROM environmental_contexts
        WHERE date >= date('now', '-' || :days || ' days')
    """)
    fun getTotalExerciseMinutes(days: Int): Flow<Int>

    @Query("SELECT * FROM environmental_contexts ORDER BY date DESC")
    fun getAll(): Flow<List<EnvironmentalContext>>

    @Query("DELETE FROM environmental_contexts WHERE date = :date")
    suspend fun delete(date: LocalDate)

    @Query("DELETE FROM environmental_contexts")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM environmental_contexts")
    suspend fun getCount(): Int
}