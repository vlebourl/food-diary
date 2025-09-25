package com.fooddiary.data.database.dao

import androidx.room.*
import com.fooddiary.data.database.entities.BeverageEntry
import java.time.Instant
import kotlinx.coroutines.flow.Flow

@Dao
interface BeverageEntryDao {

    @Insert
    suspend fun insert(entry: BeverageEntry): Long

    @Update
    suspend fun update(entry: BeverageEntry)

    @Query("SELECT * FROM beverage_entries WHERE id = :id AND isDeleted = 0")
    suspend fun getById(id: String): BeverageEntry?

    @Query("SELECT * FROM beverage_entries WHERE isDeleted = 0 ORDER BY timestamp DESC")
    fun getAll(): Flow<List<BeverageEntry>>

    @Query(
        """
        SELECT * FROM beverage_entries
        WHERE DATE(timestamp, 'unixepoch') BETWEEN :startDate AND :endDate
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """,
    )
    fun getAllByDateRange(startDate: String, endDate: String): Flow<List<BeverageEntry>>

    @Query(
        """
        SELECT COALESCE(SUM(caffeineContent * volume / 1000.0), 0) as totalCaffeine
        FROM beverage_entries
        WHERE DATE(timestamp, 'unixepoch') = :date
        AND caffeineContent IS NOT NULL
        AND isDeleted = 0
    """,
    )
    fun getCaffeineIntake(date: String): Flow<Float>

    @Query(
        """
        SELECT COALESCE(SUM(CASE
            WHEN volumeUnit = 'L' THEN volume * 1000
            WHEN volumeUnit = 'OZ' THEN volume * 29.5735
            WHEN volumeUnit = 'CUP' THEN volume * 240
            ELSE volume
        END), 0) as totalHydration
        FROM beverage_entries
        WHERE DATE(timestamp, 'unixepoch') = :date
        AND isDeleted = 0
    """,
    )
    fun getHydration(date: String): Flow<Float>

    @Query(
        """
        SELECT * FROM beverage_entries
        WHERE caffeineContent > 0
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """,
    )
    fun getCaffeinatedBeverages(): Flow<List<BeverageEntry>>

    @Query(
        """
        SELECT * FROM beverage_entries
        WHERE alcoholContent > 0
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """,
    )
    fun getAlcoholicBeverages(): Flow<List<BeverageEntry>>

    @Query(
        """
        UPDATE beverage_entries
        SET isDeleted = 1, deletedAt = :deletedAt, modifiedAt = :modifiedAt
        WHERE id = :id
    """,
    )
    suspend fun softDelete(id: String, deletedAt: Instant, modifiedAt: Instant)

    @Query("DELETE FROM beverage_entries WHERE id = :id")
    suspend fun hardDelete(id: String)

    @Query("DELETE FROM beverage_entries")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM beverage_entries WHERE isDeleted = 0")
    suspend fun getCount(): Int
}
