package com.fooddiary.data.database.dao

import androidx.room.*
import com.fooddiary.data.database.entities.EliminationProtocol
import com.fooddiary.data.models.EliminationPhase
import kotlinx.coroutines.flow.Flow

@Dao
interface EliminationProtocolDao {

    @Insert
    suspend fun insert(protocol: EliminationProtocol): Long

    @Update
    suspend fun update(protocol: EliminationProtocol)

    @Query("SELECT * FROM elimination_protocols WHERE id = :id")
    suspend fun getById(id: String): EliminationProtocol?

    @Query("SELECT * FROM elimination_protocols WHERE isActive = 1 LIMIT 1")
    suspend fun getActive(): EliminationProtocol?

    @Query("SELECT * FROM elimination_protocols ORDER BY startDate DESC")
    fun getAll(): Flow<List<EliminationProtocol>>

    @Query("SELECT * FROM elimination_protocols WHERE currentPhase = :phase ORDER BY startDate DESC")
    fun getByPhase(phase: EliminationPhase): Flow<List<EliminationProtocol>>

    @Query("SELECT * FROM elimination_protocols WHERE isActive = 0 ORDER BY startDate DESC")
    fun getCompleted(): Flow<List<EliminationProtocol>>

    @Query("DELETE FROM elimination_protocols WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM elimination_protocols")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM elimination_protocols")
    suspend fun getCount(): Int

    @Query("SELECT COUNT(*) FROM elimination_protocols WHERE isActive = 1")
    suspend fun getActiveCount(): Int
}