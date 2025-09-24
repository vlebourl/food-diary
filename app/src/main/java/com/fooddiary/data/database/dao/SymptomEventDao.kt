package com.fooddiary.data.database.dao

import androidx.room.*
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.models.SymptomType
import kotlinx.coroutines.flow.Flow
import java.time.Instant

@Dao
interface SymptomEventDao {

    @Insert
    suspend fun insert(event: SymptomEvent): Long

    @Update
    suspend fun update(event: SymptomEvent)

    @Query("SELECT * FROM symptom_events WHERE id = :id AND isDeleted = 0")
    suspend fun getById(id: String): SymptomEvent?

    @Query("SELECT * FROM symptom_events WHERE isDeleted = 0 ORDER BY timestamp DESC")
    fun getAll(): Flow<List<SymptomEvent>>

    @Query("""
        SELECT * FROM symptom_events
        WHERE DATE(timestamp, 'unixepoch') BETWEEN :startDate AND :endDate
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """)
    fun getAllByDateRange(startDate: String, endDate: String): Flow<List<SymptomEvent>>

    @Query("SELECT * FROM symptom_events WHERE type = :type AND isDeleted = 0 ORDER BY timestamp DESC")
    fun getByType(type: SymptomType): Flow<List<SymptomEvent>>

    @Query("SELECT * FROM symptom_events WHERE severity >= :minSeverity AND isDeleted = 0 ORDER BY timestamp DESC")
    fun getBySeverity(minSeverity: Int): Flow<List<SymptomEvent>>

    @Query("""
        SELECT * FROM symptom_events
        WHERE duration IS NULL OR duration > 0
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """)
    fun getActiveSymptoms(): Flow<List<SymptomEvent>>

    @Query("""
        SELECT * FROM symptom_events
        WHERE timestamp BETWEEN :startTime AND :endTime
        AND isDeleted = 0
        ORDER BY timestamp ASC
    """)
    suspend fun getEventsInTimeWindow(startTime: Instant, endTime: Instant): List<SymptomEvent>

    @Query("""
        SELECT AVG(severity) FROM symptom_events
        WHERE type = :type AND isDeleted = 0
    """)
    suspend fun getAverageSeverityByType(type: SymptomType): Float?

    @Query("""
        SELECT COUNT(*) FROM symptom_events
        WHERE type = :type AND isDeleted = 0
    """)
    suspend fun getCountByType(type: SymptomType): Int

    @Query("""
        SELECT type, COUNT(*) as count FROM symptom_events
        WHERE isDeleted = 0
        GROUP BY type
        ORDER BY count DESC
    """)
    suspend fun getSymptomFrequency(): Map<SymptomType, Int>

    @Query("""
        SELECT * FROM symptom_events
        WHERE bristolScale IS NOT NULL AND isDeleted = 0
        ORDER BY timestamp DESC
    """)
    fun getBowelMovements(): Flow<List<SymptomEvent>>

    @Query("""
        SELECT * FROM symptom_events
        WHERE suspectedTriggers LIKE '%' || :trigger || '%'
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """)
    fun getByTrigger(trigger: String): Flow<List<SymptomEvent>>

    @Query("""
        UPDATE symptom_events
        SET isDeleted = 1, deletedAt = :deletedAt, modifiedAt = :modifiedAt
        WHERE id = :id
    """)
    suspend fun softDelete(id: String, deletedAt: Instant, modifiedAt: Instant)

    @Query("DELETE FROM symptom_events WHERE id = :id")
    suspend fun hardDelete(id: String)

    @Query("DELETE FROM symptom_events")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM symptom_events WHERE isDeleted = 0")
    suspend fun getCount(): Int

    @Query("""
        SELECT COUNT(*) FROM symptom_events
        WHERE DATE(timestamp, 'unixepoch') = :date
        AND isDeleted = 0
    """)
    suspend fun getCountByDate(date: String): Int

    @Query("""
        SELECT COUNT(DISTINCT DATE(timestamp, 'unixepoch')) FROM symptom_events
        WHERE isDeleted = 0 AND timestamp >= :sinceTimestamp
    """)
    suspend fun getSymptomFreeDays(sinceTimestamp: Instant): Int

    @Query("""
        SELECT MAX(consecutive_days) FROM (
            SELECT COUNT(*) as consecutive_days
            FROM symptom_events
            WHERE isDeleted = 0
            GROUP BY date(timestamp, 'unixepoch')
            ORDER BY timestamp
        )
    """)
    suspend fun getLongestSymptomFreeStreak(): Int?
}