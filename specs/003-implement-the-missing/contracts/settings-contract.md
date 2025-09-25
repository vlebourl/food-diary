# Settings Screen Contract

## Purpose
Provide comprehensive user preference management for all app configuration options.

## UI Contract

### Data Requirements
```kotlin
data class SettingsState(
    val generalSettings: GeneralSettings,
    val correlationSettings: CorrelationSettings,
    val notificationSettings: NotificationSettings,
    val dataSettings: DataSettings,
    val privacySettings: PrivacySettings,
    val exportSettings: ExportSettings,
    val medicalSettings: MedicalSettings,
    val isLoading: Boolean = false,
    val error: String? = null,
    val hasUnsavedChanges: Boolean = false
)

data class GeneralSettings(
    val measurementUnit: MeasurementUnit = MeasurementUnit.METRIC,
    val timeFormat: TimeFormat = TimeFormat.SYSTEM,
    val dateFormat: DateFormat = DateFormat.SYSTEM,
    val language: Language = Language.SYSTEM
)

data class CorrelationSettings(
    val timeWindowHours: Int = 3,
    val confidenceThreshold: Float = 0.3f,
    val autoCorrelationEnabled: Boolean = true,
    val manualReviewRequired: Boolean = false
)

data class NotificationSettings(
    val enabled: Boolean = true,
    val dailyReminderTime: LocalTime? = null,
    val symptomAlerts: Boolean = false,
    val correlationAlerts: Boolean = false,
    val reportGeneration: Boolean = true
)

data class DataSettings(
    val retentionMonths: Int = 12,
    val autoBackup: Boolean = false,
    val compressionEnabled: Boolean = true,
    val storageLimit: StorageLimit = StorageLimit.STANDARD
)

data class PrivacySettings(
    val dataMinimization: Boolean = true,
    val anonymizeExports: Boolean = false,
    val shareUsageStats: Boolean = false,
    val thirdPartyAnalytics: Boolean = false
)

data class ExportSettings(
    val defaultFormat: ExportFormat = ExportFormat.PDF,
    val includeCharts: Boolean = true,
    val includeRawData: Boolean = false,
    val reportTemplate: ReportTemplate = ReportTemplate.BASIC
)

data class MedicalSettings(
    val customSymptomTypes: List<String> = emptyList(),
    val severityDescriptors: Map<Int, String> = defaultSeverityMap(),
    val triggerAlertFoods: List<String> = emptyList(),
    val clinicalMode: Boolean = false
)
```

### User Actions
```kotlin
interface SettingsActions {
    fun updateMeasurementUnit(unit: MeasurementUnit)
    fun updateCorrelationWindow(hours: Int)
    fun updateNotificationSettings(settings: NotificationSettings)
    fun updateDataRetention(months: Int)
    fun addCustomSymptom(symptom: String)
    fun removeCustomSymptom(symptom: String)
    fun updateExportFormat(format: ExportFormat)
    fun resetToDefaults(category: SettingsCategory)
    fun saveAllChanges()
    fun discardChanges()
    fun exportSettings()
    fun importSettings(settingsData: String)
    fun clearAllData()
    fun requestDataExport()
}
```

### Expected Behaviors
- Grouped settings with expandable sections
- Immediate preview of changes where applicable
- Unsaved changes warning
- Reset to defaults options
- Import/export settings capability
- Data management tools
- Validation for all inputs
- Help tooltips for complex settings

## Settings Categories

### General Preferences
- **Measurement Units**: Metric/Imperial with unit conversion
- **Time Format**: 12hr/24hr/System default
- **Date Format**: Regional formats or system default
- **Language**: App language selection

### Correlation Configuration
- **Time Window**: 1-48 hours slider with descriptions
- **Confidence Threshold**: 0.1-1.0 sensitivity adjustment
- **Auto-Correlation**: Enable/disable automatic correlation detection
- **Manual Review**: Require user confirmation for correlations

### Notification Management
- **Daily Reminders**: Time picker for logging reminders
- **Symptom Alerts**: Notifications for potential triggers
- **Correlation Alerts**: New correlation discovery notifications
- **Report Generation**: Notify when reports are ready

### Data Management
- **Retention Period**: 1-60 months with storage impact
- **Auto-Backup**: Enable scheduled local backups
- **Compression**: Reduce storage usage
- **Storage Monitoring**: Current usage and limits

### Privacy Controls
- **Data Minimization**: Store only essential data
- **Export Anonymization**: Remove identifying info from exports
- **Usage Statistics**: Share anonymous usage data
- **Third-party Analytics**: Disable all external tracking

### Export Preferences
- **Default Format**: PDF/CSV/JSON selection
- **Include Charts**: Visual data in exports
- **Include Raw Data**: Technical data for analysis
- **Report Template**: Basic/Detailed/Medical defaults

### Medical Configuration
- **Custom Symptoms**: Add/remove personal symptom types
- **Severity Descriptors**: Customize 1-10 scale descriptions
- **Trigger Alert Foods**: Foods to watch for reactions
- **Clinical Mode**: Enhanced medical reporting features

## Validation Rules
```kotlin
data class SettingsValidationRules(
    val correlationWindowRange: IntRange = 1..48,
    val confidenceThresholdRange: ClosedFloatingPointRange<Float> = 0.1f..1.0f,
    val retentionMonthsRange: IntRange = 1..60,
    val maxCustomSymptoms: Int = 20,
    val maxTriggerFoods: Int = 50,
    val maxDescriptorLength: Int = 100
)
```

## Constitutional Compliance Checks
- **Privacy First**: Default to privacy-preserving options
- **Medical Accuracy**: Validate medical settings don't encourage self-diagnosis
- **Data Integrity**: Ensure retention settings don't compromise data consistency
- **Offline-First**: All settings must work without network
- **Testing**: All setting changes must be testable

## Test Scenarios
1. **Settings Navigation**: All categories accessible and organized
2. **Input Validation**: Range limits, format requirements enforced
3. **Change Detection**: Unsaved changes warning works
4. **Reset Functionality**: Restore defaults for each category
5. **Import/Export**: Settings backup and restore works
6. **Privacy Controls**: Verify privacy settings are respected
7. **Correlation Tuning**: Window and threshold changes affect analysis
8. **Notification Settings**: All notification types work correctly
9. **Data Management**: Retention and cleanup settings function
10. **Medical Customization**: Custom symptoms and descriptors work
11. **Performance Impact**: Settings changes don't degrade performance
12. **Persistence**: Settings survive app restarts and updates