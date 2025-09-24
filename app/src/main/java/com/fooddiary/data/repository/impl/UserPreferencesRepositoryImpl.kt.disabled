package com.fooddiary.data.repository.impl

import com.fooddiary.data.preferences.UserPreferences
import com.fooddiary.data.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepositoryImpl @Inject constructor(
    private val userPreferences: UserPreferences
) : UserPreferencesRepository {

    override val notificationsEnabled: Flow<Boolean> = userPreferences.notificationsEnabled
    override val mealReminderTime: Flow<String> = userPreferences.mealReminderTime
    override val symptomReminderTime: Flow<String> = userPreferences.symptomReminderTime
    override val darkModeEnabled: Flow<Boolean> = userPreferences.darkModeEnabled
    override val dataRetentionDays: Flow<Int> = userPreferences.dataRetentionDays
    override val exportFormat: Flow<String> = userPreferences.exportFormat
    override val medicalMode: Flow<Boolean> = userPreferences.medicalMode
    override val showFodmapInfo: Flow<Boolean> = userPreferences.showFodmapInfo
    override val biometricAuthEnabled: Flow<Boolean> = userPreferences.biometricAuthEnabled

    override suspend fun setNotificationsEnabled(enabled: Boolean) {
        userPreferences.setNotificationsEnabled(enabled)
    }

    override suspend fun setMealReminderTime(time: String) {
        userPreferences.setMealReminderTime(time)
    }

    override suspend fun setSymptomReminderTime(time: String) {
        userPreferences.setSymptomReminderTime(time)
    }

    override suspend fun setDarkModeEnabled(enabled: Boolean) {
        userPreferences.setDarkModeEnabled(enabled)
    }

    override suspend fun setDataRetentionDays(days: Int) {
        userPreferences.setDataRetentionDays(days)
    }

    override suspend fun setExportFormat(format: String) {
        userPreferences.setExportFormat(format)
    }

    override suspend fun setMedicalMode(enabled: Boolean) {
        userPreferences.setMedicalMode(enabled)
    }

    override suspend fun setShowFodmapInfo(enabled: Boolean) {
        userPreferences.setShowFodmapInfo(enabled)
    }

    override suspend fun setBiometricAuthEnabled(enabled: Boolean) {
        userPreferences.setBiometricAuthEnabled(enabled)
    }

    override suspend fun resetToDefaults() {
        userPreferences.resetToDefaults()
    }
}