package com.fooddiary.data.database.dao

import androidx.room.*
import com.fooddiary.data.database.entities.TriggerPattern
import com.fooddiary.data.models.SymptomType
import kotlinx.coroutines.flow.Flow

@Dao
interface TriggerPatternDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pattern: TriggerPattern)

    @Update
    suspend fun update(pattern: TriggerPattern)

    @Query("SELECT * FROM trigger_patterns ORDER BY correlationStrength DESC")
    fun getAll(): Flow<List<TriggerPattern>>

    @Query("SELECT * FROM trigger_patterns WHERE symptomType = :type ORDER BY correlationStrength DESC")
    fun getBySymptomType(type: SymptomType): Flow<List<TriggerPattern>>

    @Query("SELECT * FROM trigger_patterns WHERE foodName = :foodName ORDER BY correlationStrength DESC")
    fun getByFood(foodName: String): Flow<List<TriggerPattern>>

    @Query("SELECT * FROM trigger_patterns WHERE correlationStrength >= :minConfidence ORDER BY correlationStrength DESC")
    fun getHighConfidence(minConfidence: Float = 0.7f): Flow<List<TriggerPattern>>

    @Query("""
        SELECT * FROM trigger_patterns
        WHERE occurrences >= 10
        AND correlationStrength >= 0.6
        AND confidence >= 0.95
        AND pValue < 0.05
        ORDER BY correlationStrength DESC
    """)
    fun getStatisticallySignificant(): Flow<List<TriggerPattern>>

    @Query("SELECT * FROM trigger_patterns WHERE id = :id")
    suspend fun getById(id: String): TriggerPattern?

    @Query("DELETE FROM trigger_patterns WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM trigger_patterns")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM trigger_patterns")
    suspend fun getCount(): Int

    @Query("""
        SELECT COUNT(*) FROM trigger_patterns
        WHERE correlationStrength >= 0.6
        AND confidence >= 0.95
        AND occurrences >= 10
    """)
    suspend fun getSignificantCount(): Int
}