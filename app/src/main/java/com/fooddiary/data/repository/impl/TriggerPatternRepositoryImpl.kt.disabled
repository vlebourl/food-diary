package com.fooddiary.data.repository.impl

import com.fooddiary.data.database.dao.TriggerPatternDao
import com.fooddiary.data.database.entities.TriggerPattern
import com.fooddiary.data.models.SymptomType
import com.fooddiary.data.repository.TriggerPatternRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TriggerPatternRepositoryImpl @Inject constructor(
    private val triggerPatternDao: TriggerPatternDao
) : TriggerPatternRepository {

    override suspend fun insert(pattern: TriggerPattern): Long {
        return triggerPatternDao.insert(pattern)
    }

    override suspend fun update(pattern: TriggerPattern) {
        triggerPatternDao.update(pattern)
    }

    override suspend fun delete(pattern: TriggerPattern) {
        triggerPatternDao.delete(pattern)
    }

    override suspend fun getById(id: Long): TriggerPattern? {
        return triggerPatternDao.getById(id)
    }

    override fun getAll(): Flow<List<TriggerPattern>> {
        return triggerPatternDao.getAll()
    }

    override fun getBySymptomType(symptomType: SymptomType): Flow<List<TriggerPattern>> {
        return triggerPatternDao.getBySymptomType(symptomType)
    }

    override fun getByFood(foodName: String): Flow<List<TriggerPattern>> {
        return triggerPatternDao.getByFood(foodName)
    }

    override fun getStatisticallySignificant(): Flow<List<TriggerPattern>> {
        return triggerPatternDao.getStatisticallySignificant()
    }

    override fun getHighConfidence(): Flow<List<TriggerPattern>> {
        return triggerPatternDao.getHighConfidence()
    }

    override fun getByCorrelationStrength(minStrength: Float): Flow<List<TriggerPattern>> {
        return triggerPatternDao.getByCorrelationStrength(minStrength)
    }

    override suspend fun updateConfidence(patternId: Long, newConfidence: Float) {
        triggerPatternDao.updateConfidence(patternId, newConfidence)
    }

    override suspend fun updateOccurrences(patternId: Long, newOccurrences: Int) {
        triggerPatternDao.updateOccurrences(patternId, newOccurrences)
    }

    override suspend fun getSignificantCount(): Int {
        return triggerPatternDao.getSignificantCount()
    }

    override suspend fun getCount(): Int {
        return triggerPatternDao.getCount()
    }

    override suspend fun deleteWeakPatterns(minOccurrences: Int, minCorrelation: Float): Int {
        return triggerPatternDao.deleteWeakPatterns(minOccurrences, minCorrelation)
    }

    override suspend fun deleteAll() {
        triggerPatternDao.deleteAll()
    }
}