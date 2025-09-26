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

    @Query("SELECT * FROM correlation_patterns ORDER BY confidenceScore DESC")
    suspend fun getAll(): List<CorrelationPattern>

    @Query(
        """
        SELECT cp.* FROM correlation_patterns cp
        INNER JOIN food_entries fe ON cp.foodEntryId = fe.id
        WHERE EXISTS (
            SELECT 1 FROM json_each(fe.foods)
            WHERE json_each.value = :food
        )
        ORDER BY cp.confidenceScore DESC
    """
    )
    suspend fun getCorrelationsForFood(food: String): List<CorrelationPattern>

    @Query(
        """
        SELECT cp.* FROM correlation_patterns cp
        INNER JOIN symptom_events se ON cp.symptomEventId = se.id
        WHERE se.symptomType = :symptomType
        ORDER BY cp.confidenceScore DESC
    """
    )
    suspend fun getCorrelationsForSymptom(symptomType: SymptomType): List<CorrelationPattern>

    @Query(
        """
        SELECT * FROM correlation_patterns
        WHERE confidenceScore BETWEEN :minConfidence AND :maxConfidence
        ORDER BY confidenceScore DESC
    """
    )
    suspend fun getByConfidenceRange(minConfidence: Float, maxConfidence: Float): List<CorrelationPattern>

    @Query(
        """
        SELECT cp.* FROM correlation_patterns cp
        INNER JOIN food_entries fe ON cp.foodEntryId = fe.id
        INNER JOIN symptom_events se ON cp.symptomEventId = se.id
        WHERE cp.confidenceScore >= 0.6
        AND fe.isDeleted = 0
        AND se.isDeleted = 0
        ORDER BY cp.confidenceScore DESC
    """
    )
    suspend fun getSignificantPatterns(): List<CorrelationPattern>

    @Query(
        """
        SELECT * FROM correlation_patterns
        WHERE confidenceScore >= 0.8
        ORDER BY confidenceScore DESC
    """
    )
    suspend fun getHighConfidencePatterns(): List<CorrelationPattern>

    @Query(
        """
        SELECT * FROM correlation_patterns
        WHERE timeOffset BETWEEN :minOffset AND :maxOffset
        ORDER BY confidenceScore DESC
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
}