package com.fooddiary.data.repository.impl

import com.fooddiary.data.database.dao.EliminationProtocolDao
import com.fooddiary.data.database.entities.EliminationProtocol
import com.fooddiary.data.repository.EliminationProtocolRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EliminationProtocolRepositoryImpl @Inject constructor(
    private val eliminationProtocolDao: EliminationProtocolDao
) : EliminationProtocolRepository {

    override suspend fun insert(protocol: EliminationProtocol): Long {
        return eliminationProtocolDao.insert(protocol)
    }

    override suspend fun update(protocol: EliminationProtocol) {
        eliminationProtocolDao.update(protocol)
    }

    override suspend fun delete(protocol: EliminationProtocol) {
        eliminationProtocolDao.delete(protocol)
    }

    override suspend fun getById(id: Long): EliminationProtocol? {
        return eliminationProtocolDao.getById(id)
    }

    override fun getAll(): Flow<List<EliminationProtocol>> {
        return eliminationProtocolDao.getAll()
    }

    override fun getActiveProtocols(): Flow<List<EliminationProtocol>> {
        return eliminationProtocolDao.getActiveProtocols()
    }

    override fun getByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<EliminationProtocol>> {
        return eliminationProtocolDao.getByDateRange(startDate, endDate)
    }

    override suspend fun getByFoodItem(foodItem: String): EliminationProtocol? {
        return eliminationProtocolDao.getByFoodItem(foodItem)
    }

    override suspend fun markAsCompleted(protocolId: Long, completionDate: LocalDate) {
        eliminationProtocolDao.markAsCompleted(protocolId, completionDate)
    }

    override suspend fun updateReintroductionResult(protocolId: Long, result: String, symptoms: String?) {
        eliminationProtocolDao.updateReintroductionResult(protocolId, result, symptoms)
    }

    override suspend fun deleteAll() {
        eliminationProtocolDao.deleteAll()
    }
}