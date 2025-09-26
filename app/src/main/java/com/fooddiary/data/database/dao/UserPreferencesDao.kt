package com.fooddiary.data.database.dao

import androidx.room.*
import com.fooddiary.data.database.entities.UserPreferences
import com.fooddiary.data.database.entities.MeasurementUnit
import com.fooddiary.data.database.entities.ExportFormat
import com.fooddiary.data.database.entities.ReportTemplate
import java.time.LocalTime

@Dao
interface UserPreferencesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(preferences: UserPreferences): Long

    @Update
    suspend fun update(preferences: UserPreferences): Int

    @Delete
    suspend fun delete(preferences: UserPreferences): Int

    @Query("DELETE FROM user_preferences")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM user_preferences LIMIT 1")
    suspend fun get(): UserPreferences?

    @Query("SELECT * FROM user_preferences")
    suspend fun getAll(): List<UserPreferences>

    suspend fun getOrDefault(): UserPreferences {
        return get() ?: UserPreferences.createDefault()
    }

    @Query(
        """
        UPDATE user_preferences
        SET
            correlationTimeWindowHours = :correlationTimeWindowHours,
            measurementUnit = :measurementUnit,
            notificationEnabled = :notificationEnabled,
            notificationTime = :notificationTime,
            dataRetentionMonths = :dataRetentionMonths,
            exportFormat = :exportFormat,
            triggerAlertEnabled = :triggerAlertEnabled,
            reportTemplate = :reportTemplate,
            privacyMode = :privacyMode,
            modifiedAt = :modifiedAt
        WHERE id = 1
    """
    )
    suspend fun updatePreferences(
        correlationTimeWindowHours: Int,
        measurementUnit: MeasurementUnit,
        notificationEnabled: Boolean,
        notificationTime: LocalTime?,
        dataRetentionMonths: Int,
        exportFormat: ExportFormat,
        triggerAlertEnabled: Boolean,
        reportTemplate: ReportTemplate,
        privacyMode: Boolean,
        modifiedAt: java.time.Instant
    ): Int

    @Query(
        """
        UPDATE user_preferences
        SET
            notificationEnabled = :notificationEnabled,
            notificationTime = :notificationTime,
            modifiedAt = :modifiedAt
        WHERE id = 1
    """
    )
    suspend fun updateNotificationSettings(
        notificationEnabled: Boolean,
        notificationTime: LocalTime?,
        modifiedAt: java.time.Instant
    ): Int

    @Query(
        """
        UPDATE user_preferences
        SET
            customSymptomTypes = :customSymptomTypes,
            modifiedAt = :modifiedAt
        WHERE id = 1
    """
    )
    suspend fun updateCustomSymptomTypes(
        customSymptomTypes: List<String>,
        modifiedAt: java.time.Instant
    ): Int

    suspend fun resetToDefaults(): UserPreferences {
        deleteAll()
        val defaults = UserPreferences.createDefault()
        insert(defaults)
        return defaults
    }
}