package com.fooddiary.presentation.ui.entry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooddiary.data.repository.SymptomEventRepository
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.data.repository.CorrelationRepository
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.models.*
import com.fooddiary.data.validation.SymptomEntryValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject

/**
 * ViewModel for Symptom Entry screen with correlation detection
 * Manages symptom selection, severity tracking, duration input, and correlation analysis
 */
@HiltViewModel
class SymptomEntryViewModel @Inject constructor(
    private val symptomEventRepository: SymptomEventRepository,
    private val foodEntryRepository: FoodEntryRepository,
    private val correlationRepository: CorrelationRepository,
    private val symptomEntryValidator: SymptomEntryValidator
) : ViewModel() {

    private val _uiState = MutableStateFlow(SymptomEntryUiState())
    val uiState: StateFlow<SymptomEntryUiState> = _uiState.asStateFlow()

    init {
        loadCorrelations()
    }

    /**
     * Select a symptom type
     */
    fun selectSymptom(symptomType: SymptomType) {
        _uiState.value = _uiState.value.copy(selectedSymptomType = symptomType)

        viewModelScope.launch {
            detectCorrelations(symptomType)
            validateSymptomEntry()
        }
    }

    /**
     * Set symptom severity (1-10 scale)
     */
    fun setSeverity(severity: Int) {
        val clampedSeverity = severity.coerceIn(1, 10)
        _uiState.value = _uiState.value.copy(severity = clampedSeverity)

        viewModelScope.launch {
            validateSymptomEntry()
        }
    }

    /**
     * Set symptom duration
     */
    fun setDuration(duration: String) {
        _uiState.value = _uiState.value.copy(duration = duration)

        viewModelScope.launch {
            validateSymptomEntry()
        }
    }

    /**
     * Update timestamp
     */
    fun updateTimestamp(timestamp: Instant) {
        _uiState.value = _uiState.value.copy(timestamp = timestamp)

        viewModelScope.launch {
            // Re-detect correlations with new timestamp
            _uiState.value.selectedSymptomType?.let { detectCorrelations(it) }
        }
    }

    /**
     * Update notes
     */
    fun updateNotes(notes: String) {
        _uiState.value = _uiState.value.copy(notes = notes)
    }

    /**
     * Select a trigger food from correlation suggestions
     */
    fun selectTriggerFood(foodEntryId: String?) {
        _uiState.value = _uiState.value.copy(selectedTriggerFoodId = foodEntryId)
    }

    /**
     * Validate symptom entry
     */
    fun validateSymptomEntry() {
        viewModelScope.launch {
            val currentState = _uiState.value

            if (currentState.selectedSymptomType == null) {
                _uiState.value = currentState.copy(
                    validationErrors = listOf("Please select a symptom type"),
                    isValid = false
                )
                return@launch
            }

            val tempSymptomEvent = SymptomEvent(
                id = "",
                timestamp = currentState.timestamp,
                type = currentState.selectedSymptomType,
                severity = currentState.severity,
                duration = currentState.duration,
                triggerFoodId = currentState.selectedTriggerFoodId,
                notes = currentState.notes,
                isDeleted = false
            )

            val validationResult = symptomEntryValidator.validateSymptomEntry(tempSymptomEvent)

            when (validationResult) {
                is ValidationResult.Success -> {
                    _uiState.value = currentState.copy(
                        validationErrors = emptyList(),
                        isValid = true
                    )
                }
                is ValidationResult.Error -> {
                    _uiState.value = currentState.copy(
                        validationErrors = validationResult.errors,
                        isValid = false
                    )
                }
            }
        }
    }

    /**
     * Save the symptom entry
     */
    suspend fun saveSymptom(): Boolean {
        return try {
            _uiState.value = _uiState.value.copy(isSaving = true)

            // Validate first
            validateSymptomEntry()

            if (!_uiState.value.isValid) {
                _uiState.value = _uiState.value.copy(isSaving = false)
                return false
            }

            val currentState = _uiState.value
            val symptomEvent = SymptomEvent(
                id = "",
                timestamp = currentState.timestamp,
                type = currentState.selectedSymptomType!!,
                severity = currentState.severity,
                duration = currentState.duration,
                triggerFoodId = currentState.selectedTriggerFoodId,
                notes = currentState.notes,
                isDeleted = false
            )

            val savedId = symptomEventRepository.insert(symptomEvent)

            // Update correlations if trigger food was identified
            if (currentState.selectedTriggerFoodId != null) {
                correlationRepository.recordCorrelation(
                    currentState.selectedTriggerFoodId,
                    savedId,
                    calculateTimeDifference(currentState.timestamp, currentState.selectedTriggerFoodId)
                )
            }

            _uiState.value = _uiState.value.copy(
                isSaving = false,
                message = "Symptom logged successfully"
            )

            true
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isSaving = false,
                message = "Error saving symptom: ${e.message}"
            )
            false
        }
    }

    /**
     * Detect potential food correlations for the symptom
     */
    fun detectCorrelations(symptomType: SymptomType) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoadingCorrelations = true)

                // Get recent food entries (last 24 hours)
                val endTime = _uiState.value.timestamp
                val startTime = endTime.minus(24, ChronoUnit.HOURS)

                foodEntryRepository.getByTimeRange(startTime, endTime).collect { recentFoodEntries ->
                    // Calculate correlations
                    val correlations = correlationRepository.calculateCorrelations(
                        recentFoodEntries.map { it.id },
                        listOf(symptomType)
                    )

                    // Create correlation suggestions
                    val suggestions = recentFoodEntries.mapNotNull { foodEntry ->
                        val correlation = correlations.find { it.foodEntryId == foodEntry.id }
                        if (correlation != null && correlation.strength > 0.3) { // 30% correlation threshold
                            CorrelationSuggestion(
                                foodEntry = foodEntry,
                                correlationStrength = correlation.strength,
                                timeOffset = ChronoUnit.HOURS.between(foodEntry.timestamp, endTime),
                                confidence = correlation.confidence
                            )
                        } else {
                            // Include all recent foods for user selection even without strong correlations
                            CorrelationSuggestion(
                                foodEntry = foodEntry,
                                correlationStrength = correlation?.strength ?: 0.0,
                                timeOffset = ChronoUnit.HOURS.between(foodEntry.timestamp, endTime),
                                confidence = correlation?.confidence ?: 0.0
                            )
                        }
                    }.sortedByDescending { it.correlationStrength }

                    _uiState.value = _uiState.value.copy(
                        correlationSuggestions = suggestions,
                        isLoadingCorrelations = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingCorrelations = false,
                    correlationSuggestions = emptyList()
                )
            }
        }
    }

    /**
     * Clear form data
     */
    fun clearForm() {
        _uiState.value = SymptomEntryUiState()
        loadCorrelations()
    }

    /**
     * Load historical correlations
     */
    private fun loadCorrelations() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoadingCorrelations = true)
                // Load recent correlations for display
                _uiState.value = _uiState.value.copy(isLoadingCorrelations = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoadingCorrelations = false)
            }
        }
    }

    /**
     * Calculate time difference between symptom and potential trigger food
     */
    private suspend fun calculateTimeDifference(symptomTime: Instant, foodEntryId: String): Long {
        return try {
            val foodEntry = foodEntryRepository.getById(foodEntryId)
            if (foodEntry != null) {
                ChronoUnit.HOURS.between(foodEntry.timestamp, symptomTime)
            } else {
                0L
            }
        } catch (e: Exception) {
            0L
        }
    }
}

/**
 * UI State for Symptom Entry screen
 */
data class SymptomEntryUiState(
    val selectedSymptomType: SymptomType? = null,
    val severity: Int = 1,
    val duration: String? = null,
    val timestamp: Instant = Instant.now(),
    val notes: String = "",
    val selectedTriggerFoodId: String? = null,
    val correlationSuggestions: List<CorrelationSuggestion> = emptyList(),
    val validationErrors: List<String> = emptyList(),
    val isValid: Boolean = false,
    val isSaving: Boolean = false,
    val isLoadingCorrelations: Boolean = true,
    val message: String? = null
)

/**
 * Correlation suggestion data class
 */
data class CorrelationSuggestion(
    val foodEntry: FoodEntry,
    val correlationStrength: Double,
    val timeOffset: Long, // Hours between food and symptom
    val confidence: Double
)

/**
 * Food correlation data class
 */
data class FoodCorrelation(
    val foodEntryId: String,
    val symptomType: SymptomType,
    val strength: Double,
    val confidence: Double,
    val occurrences: Int
)