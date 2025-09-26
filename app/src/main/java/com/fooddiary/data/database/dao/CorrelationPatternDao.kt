package com.fooddiary.data.database.dao

import androidx.room.*
import com.fooddiary.data.database.entities.CorrelationPattern
import com.fooddiary.data.models.SymptomType
import java.time.Duration
import java.time.Instant
import kotlinx.coroutines.flow.Flow

@Dao
interface CorrelationPatternDao {

    @Insert
    suspend fun insert(pattern: CorrelationPattern): Long

    @Insert
    suspend fun insertAll(patterns: List<CorrelationPattern>): List<Long>

    @Update
    suspend fun update(pattern: CorrelationPattern): Int

    @Delete
    suspend fun delete(pattern: CorrelationPattern): Int

    @Query("DELETE FROM correlation_patterns WHERE id = :id")
    suspend fun deleteById(id: Long): Int

    @Query("SELECT * FROM correlation_patterns WHERE id = :id")
    suspend fun getById(id: Long): CorrelationPattern?

    @Query("SELECT * FROM correlation_patterns ORDER BY correlationStrength DESC")
    suspend fun getAll(): List<CorrelationPattern>

    @Query(
        """
        SELECT cp.* FROM correlation_patterns cp
        INNER JOIN food_entries fe ON cp.foodId = fe.id
        WHERE EXISTS (
            SELECT 1 FROM json_each(fe.foods)
            WHERE json_each.value = :food
        )
        ORDER BY cp.correlationStrength DESC
    """
    )
    suspend fun getCorrelationsForFood(food: String): List<CorrelationPattern>

    @Query(
        """
        SELECT cp.* FROM correlation_patterns cp
        INNER JOIN symptom_events se ON cp.symptomId = se.id
        WHERE se.symptomType = :symptomType
        ORDER BY cp.correlationStrength DESC
    """
    )
    suspend fun getCorrelationsForSymptom(symptomType: SymptomType): List<CorrelationPattern>

    @Query(
        """
        SELECT * FROM correlation_patterns
        WHERE correlationStrength BETWEEN :minConfidence AND :maxConfidence
        ORDER BY correlationStrength DESC
    """
    )
    suspend fun getByConfidenceRange(minConfidence: Float, maxConfidence: Float): List<CorrelationPattern>

    @Query(
        """
        SELECT cp.* FROM correlation_patterns cp
        INNER JOIN food_entries fe ON cp.foodId = fe.id
        INNER JOIN symptom_events se ON cp.symptomId = se.id
        WHERE cp.correlationStrength >= 0.6
        AND fe.isDeleted = 0
        AND se.isDeleted = 0
        ORDER BY cp.correlationStrength DESC
    """
    )
    suspend fun getSignificantPatterns(): List<CorrelationPattern>

    @Query(
        """
        SELECT * FROM correlation_patterns
        WHERE correlationStrength >= 0.8
        ORDER BY correlationStrength DESC
    """
    )
    suspend fun getHighConfidencePatterns(): List<CorrelationPattern>

    @Query(
        """
        SELECT * FROM correlation_patterns
        WHERE timeOffsetHours BETWEEN :minOffset AND :maxOffset
        ORDER BY correlationStrength DESC
    """
    )
    suspend fun getPatternsByTimeOffset(minOffset: Long, maxOffset: Long): List<CorrelationPattern>

    @Query(
        """
        DELETE FROM correlation_patterns
        WHERE calculatedAt < :cutoffTime
    """
    )
    suspend fun deleteOldPatterns(cutoffTime: Instant): Int

    @Query("SELECT * FROM correlation_patterns WHERE isActive = 1 ORDER BY correlationStrength DESC")
    suspend fun getActiveCorrelations(): List<CorrelationPattern>

    @Query(
        """
        SELECT * FROM correlation_patterns
        WHERE correlationStrength >= :minConfidence
        AND isActive = 1
        ORDER BY correlationStrength DESC
    """
    )
    suspend fun getHighConfidenceCorrelations(minConfidence: Float): List<CorrelationPattern>

    @Query(
        """
        SELECT * FROM correlation_patterns cp
        WHERE DATE(cp.calculatedAt, 'unixepoch') = :date
        ORDER BY cp.correlationStrength DESC
    """
    )
    suspend fun getCorrelationsForDate(date: java.time.LocalDate): List<CorrelationPattern>
}