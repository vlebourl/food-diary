# Symptom Entry Screen Contract

## Purpose
Enable users to log digestive symptoms with severity, duration, and correlation tracking.

## UI Contract

### Data Requirements
```kotlin
data class SymptomEntryState(
    val symptomType: SymptomType = SymptomType.OTHER,
    val severity: Int = 5,
    val duration: Duration? = null,
    val timestamp: Instant = Clock.System.now(),
    val notes: String = "",
    val potentialTriggers: List<FoodTrigger> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val validationErrors: Map<String, String> = emptyMap()
)

data class FoodTrigger(
    val foodEntryId: Long,
    val foodName: String,
    val consumedAt: Instant,
    val timeOffset: Duration,
    val isSelected: Boolean = false
)

enum class SymptomType {
    NAUSEA, BLOATING, ABDOMINAL_CRAMPS, DIARRHEA,
    CONSTIPATION, HEARTBURN, GAS, ACID_REFLUX,
    STOMACH_PAIN, INDIGESTION, OTHER
}
```

### User Actions
```kotlin
interface SymptomEntryActions {
    fun updateSymptomType(symptomType: SymptomType)
    fun updateSeverity(severity: Int)
    fun updateDuration(duration: Duration?)
    fun updateTimestamp(timestamp: Instant)
    fun updateNotes(notes: String)
    fun toggleFoodTrigger(foodEntryId: Long)
    fun saveSymptomEntry()
    fun cancelEntry()
    fun clearForm()
    fun loadPotentialTriggers()
}
```

### Expected Behaviors
- Symptom type selection dropdown/chips
- Severity slider/stepper (1-10 scale with descriptors)
- Optional duration picker (minutes to hours)
- Timestamp picker with current time default
- Automatic loading of recent food entries as potential triggers
- Visual correlation indicators for suggested triggers
- Form validation before saving
- Success feedback and navigation

## Validation Rules
```kotlin
data class SymptomValidationRules(
    val severityRange: IntRange = 1..10,
    val maxDurationHours: Int = 72,
    val maxNotesLength: Int = 500,
    val correlationWindowHours: Int = 24 // Based on user preferences
)
```

- Severity must be between 1-10 inclusive
- Duration cannot exceed 72 hours if specified
- Timestamp cannot be in future
- Timestamp cannot be more than 7 days in past
- Notes limited to 500 characters
- At least one symptom type must be selected

## Correlation Logic
```kotlin
data class CorrelationConfig(
    val timeWindow: Duration, // From user preferences
    val minimumConfidence: Float = 0.3f,
    val maxTriggers: Int = 5
)
```

- Load food entries within user's correlation time window
- Calculate time offset between food consumption and symptom onset
- Sort by recency and potential correlation strength
- Limit to maximum 5 suggested triggers
- Allow manual trigger selection/deselection

## Test Scenarios
1. **Basic Symptom Entry**: Valid symptom with severity, successful save
2. **Complete Entry**: All fields filled including duration and triggers
3. **Severity Selection**: Slider/stepper interaction with visual feedback
4. **Duration Input**: Optional duration with time picker
5. **Trigger Correlation**: Load and select potential food triggers
6. **Validation Errors**: Invalid severity, future timestamp, excessive duration
7. **Empty Triggers**: Handle case with no recent food entries
8. **Form Persistence**: Maintain state during configuration changes
9. **Correlation Feedback**: Visual indicators for trigger confidence
10. **Error Recovery**: Handle database operations gracefully