package com.fooddiary.data.database.dao

import androidx.room.*
import com.fooddiary.data.database.entities.QuickEntryTemplate
import kotlinx.coroutines.flow.Flow

@Dao
interface QuickEntryTemplateDao {

    @Insert
    suspend fun insert(template: QuickEntryTemplate): Long

    @Update
    suspend fun update(template: QuickEntryTemplate)

    @Query("SELECT * FROM quick_entry_templates WHERE id = :id")
    suspend fun getById(id: String): QuickEntryTemplate?

    @Query("SELECT * FROM quick_entry_templates WHERE isActive = 1 ORDER BY sortOrder ASC")
    fun getAllActive(): Flow<List<QuickEntryTemplate>>

    @Query("SELECT * FROM quick_entry_templates ORDER BY sortOrder ASC")
    fun getAll(): Flow<List<QuickEntryTemplate>>

    @Query("SELECT * FROM quick_entry_templates WHERE entryType = :type AND isActive = 1 ORDER BY sortOrder ASC")
    fun getByType(type: String): Flow<List<QuickEntryTemplate>>

    @Transaction
    suspend fun reorder(templates: List<QuickEntryTemplate>) {
        templates.forEachIndexed { index, template ->
            update(template.copy(sortOrder = index, modifiedAt = System.currentTimeMillis()))
        }
    }

    @Query("DELETE FROM quick_entry_templates WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM quick_entry_templates")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM quick_entry_templates WHERE isActive = 1")
    suspend fun getActiveCount(): Int

    @Query("SELECT MAX(sortOrder) FROM quick_entry_templates WHERE isActive = 1")
    suspend fun getMaxSortOrder(): Int?
}