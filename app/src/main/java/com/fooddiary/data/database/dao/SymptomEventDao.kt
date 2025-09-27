package com.fooddiary.data.database.dao

import androidx.room.*
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.models.SymptomFrequency
import com.fooddiary.data.models.SymptomType
import java.time.Instant
import kotlinx.coroutines.flow.Flow

@Dao
interface SymptomEventDao {

    @Insert
    suspend fun insert(event: SymptomEvent): Long

    @Insert
    suspend fun insertAll(events: List<SymptomEvent>): List<Long>

    @Update
    suspend fun update(event: SymptomEvent): Int

    @Delete
    suspend fun delete(event: SymptomEvent): Int

    @Query("SELECT * FROM symptom_events WHERE id = :id AND isDeleted = 0")
    suspend fun getById(id: Long): SymptomEvent?

    @Query("SELECT * FROM symptom_events WHERE isDeleted = 0 ORDER BY timestamp DESC")
    fun getAll(): Flow<List<SymptomEvent>>

    @Query("SELECT * FROM symptom_events WHERE isDeleted = 0 ORDER BY timestamp DESC")
    suspend fun getAllSync(): List<SymptomEvent>

    @Query(
        """
        SELECT * FROM symptom_events
        WHERE DATE(timestamp, 'unixepoch') BETWEEN :startDate AND :endDate
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """,
    )
    fun getAllByDateRange(startDate: String, endDate: String): Flow<List<SymptomEvent>>

    @Query("SELECT * FROM symptom_events WHERE symptomType = :symptomType AND isDeleted = 0 ORDER BY timestamp DESC")
    suspend fun getBySymptomType(symptomType: SymptomType): List<SymptomEvent>


    @Query("SELECT * FROM symptom_events WHERE severity >= :minSeverity AND isDeleted = 0 ORDER BY timestamp DESC")
    fun getBySeverity(minSeverity: Int): Flow<List<SymptomEvent>>

    @Query(
        """
        SELECT * FROM symptom_events
        WHERE duration IS NULL OR duration > 0
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """,
    )
    fun getActiveSymptoms(): Flow<List<SymptomEvent>>

    @Query(
        """
        SELECT * FROM symptom_events
        WHERE timestamp BETWEEN :startTime AND :endTime
        AND isDeleted = 0
        ORDER BY timestamp ASC
    """,
    )
    suspend fun getInTimeRange(startTime: Instant, endTime: Instant): List<SymptomEvent>

    @Query(
        """
        SELECT * FROM symptom_events
        WHERE timestamp BETWEEN :startTime AND :endTime
        AND isDeleted = 0
        ORDER BY timestamp ASC
    """,
    )
    suspend fun getEventsInTimeWindow(startTime: Instant, endTime: Instant): List<SymptomEvent>

    @Query(
        """
        SELECT AVG(severity) FROM symptom_events
        WHERE symptomType = :symptomType AND isDeleted = 0
    """,
    )
    suspend fun getAverageSeverityByType(symptomType: SymptomType): Float?

    @Query(
        """
        SELECT COUNT(*) FROM symptom_events
        WHERE symptomType = :symptomType AND isDeleted = 0
    """,
    )
    suspend fun getCountByType(symptomType: SymptomType): Int

    @Query(
        """
        SELECT symptomType as type, COUNT(*) as count FROM symptom_events
        WHERE isDeleted = 0
        GROUP BY symptomType
        ORDER BY count DESC
    """,
    )
    suspend fun getSymptomFrequency(): List<SymptomFrequency>

    @Query("DELETE FROM symptom_events")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM symptom_events WHERE isDeleted = 0")
    suspend fun getCount(): Int

    @Query(
        """
        SELECT COUNT(*) FROM symptom_events
        WHERE DATE(timestamp, 'unixepoch') = :date
        AND isDeleted = 0
    """,
    )
    suspend fun getCountByDate(date: String): Int

    @Query(
        """
        SELECT COUNT(DISTINCT DATE(timestamp, 'unixepoch')) FROM symptom_events
        WHERE isDeleted = 0 AND timestamp >= :sinceTimestamp
    """,
    )
    suspend fun getSymptomFreeDays(sinceTimestamp: Instant): Int

    @Query(
        """
        SELECT MAX(consecutive_days) FROM (
            SELECT COUNT(*) as consecutive_days
            FROM symptom_events
            WHERE isDeleted = 0
            GROUP BY date(timestamp, 'unixepoch')
            ORDER BY timestamp
        )
    """,
    )
    suspend fun getLongestSymptomFreeStreak(): Int?

    @Query(
        """
        SELECT * FROM symptom_events
        WHERE severity BETWEEN :minSeverity AND :maxSeverity
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """
    )
    suspend fun getBySeverityRange(minSeverity: Int, maxSeverity: Int): List<SymptomEvent>

    @Query(
        """
        SELECT * FROM symptom_events
        WHERE isDeleted = 0
        ORDER BY timestamp DESC
        LIMIT :limit
    """
    )
    suspend fun getRecentSymptoms(limit: Int): List<SymptomEvent>

    @Query(
        """
        SELECT * FROM symptom_events
        WHERE duration BETWEEN :minDuration AND :maxDuration
        AND duration IS NOT NULL
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """
    )
    suspend fun getSymptomsByDuration(minDuration: Long, maxDuration: Long): List<SymptomEvent>

    @Query(
        """
        UPDATE symptom_events
        SET isDeleted = 1, modifiedAt = :currentTime
        WHERE id = :id
    """
    )
    suspend fun softDelete(id: Long, currentTime: Instant): Int

    @Query(
        """
        SELECT * FROM symptom_events
        WHERE timestamp >= :sinceTime
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """
    )
    suspend fun getCorrelationData(sinceTime: Instant): List<SymptomEvent>

    @Query(
        """
        SELECT * FROM symptom_events
        WHERE DATE(timestamp, 'unixepoch') = :date
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """
    )
    suspend fun getEntriesForDate(date: java.time.LocalDate): List<SymptomEvent>

    @Query(
        """
        SELECT * FROM symptom_events
        WHERE DATE(timestamp, 'unixepoch') BETWEEN :startDate AND :endDate
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """
    )
    suspend fun getEntriesInDateRange(startDate: java.time.LocalDate, endDate: java.time.LocalDate): List<SymptomEvent>

    @Query(
        """
        SELECT * FROM symptom_events
        WHERE isDeleted = 0
        ORDER BY timestamp DESC
    """
    )
    fun getAllPaged(): kotlinx.coroutines.flow.Flow<List<SymptomEvent>>

    @Query(
        """
        SELECT * FROM symptom_events
        WHERE notes LIKE '%' || :query || '%'
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """
    )
    suspend fun searchByNotes(query: String): List<SymptomEvent>
}
