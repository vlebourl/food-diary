package com.fooddiary.data.repository.impl

import com.fooddiary.data.database.dao.QuickEntryTemplateDao
import com.fooddiary.data.database.entities.QuickEntryTemplate
import com.fooddiary.data.repository.QuickEntryTemplateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuickEntryTemplateRepositoryImpl @Inject constructor(
    private val quickEntryTemplateDao: QuickEntryTemplateDao
) : QuickEntryTemplateRepository {

    override suspend fun insert(template: QuickEntryTemplate): Long {
        return quickEntryTemplateDao.insert(template)
    }

    override suspend fun update(template: QuickEntryTemplate) {
        quickEntryTemplateDao.update(template)
    }

    override suspend fun delete(template: QuickEntryTemplate) {
        quickEntryTemplateDao.delete(template)
    }

    override suspend fun getById(id: Long): QuickEntryTemplate? {
        return quickEntryTemplateDao.getById(id)
    }

    override fun getAll(): Flow<List<QuickEntryTemplate>> {
        return quickEntryTemplateDao.getAll()
    }

    override fun getActiveTemplates(): Flow<List<QuickEntryTemplate>> {
        return quickEntryTemplateDao.getActiveTemplates()
    }

    override fun getTemplatesByType(type: String): Flow<List<QuickEntryTemplate>> {
        return quickEntryTemplateDao.getTemplatesByType(type)
    }

    override fun getFrequentlyUsedTemplates(limit: Int): Flow<List<QuickEntryTemplate>> {
        return quickEntryTemplateDao.getFrequentlyUsedTemplates(limit)
    }

    override suspend fun incrementUsageCount(templateId: Long) {
        quickEntryTemplateDao.incrementUsageCount(templateId)
    }

    override suspend fun updateLastUsedTime(templateId: Long, timestamp: Long) {
        quickEntryTemplateDao.updateLastUsedTime(templateId, timestamp)
    }

    override suspend fun setActive(templateId: Long, isActive: Boolean) {
        quickEntryTemplateDao.setActive(templateId, isActive)
    }

    override fun searchByName(query: String): Flow<List<QuickEntryTemplate>> {
        return quickEntryTemplateDao.searchByName("%$query%")
    }

    override suspend fun deleteAll() {
        quickEntryTemplateDao.deleteAll()
    }
}