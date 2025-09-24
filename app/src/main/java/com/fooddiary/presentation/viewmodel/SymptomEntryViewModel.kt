package com.fooddiary.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.models.SymptomType
import com.fooddiary.data.repository.SymptomEventRepository
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.domain.usecase.AddSymptomEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class SymptomEntryViewModel @Inject constructor(
    private val addSymptomEventUseCase: AddSymptomEventUseCase,
    private val symptomEventRepository: SymptomEventRepository,
    private val foodEntryRepository: FoodEntryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SymptomEntryUiState())
    val uiState: StateFlow<SymptomEntryUiState> = _uiState.asStateFlow()

    private val _recentFoodSuggestions = MutableStateFlow<List<String>>(emptyList())
    val recentFoodSuggestions: StateFlow<List<String>> = _recentFoodSuggestions.asStateFlow()

    init {
        loadRecentFoodSuggestions()
    }

    fun updateSymptomType(type: SymptomType) {
        _uiState.value = _uiState.value.copy(
            symptomType = type,
            // Reset Bristol scale if not bowel-related symptom
            bristolScale = if (type.requiresBristolScale) _uiState.value.bristolScale else null
        )
    }

    fun updateSeverity(severity: Int) {
        if (severity in 1..10) {
            _uiState.value = _uiState.value.copy(
                severity = severity,
                severityError = null
            )
        } else {
            _uiState.value = _uiState.value.copy(
                severityError = "Severity must be between 1 and 10"
            )
        }
    }

    fun updateDuration(hours: Int?, minutes: Int?) {
        val totalMinutes = when {
            hours != null && minutes != null -> hours * 60 + minutes
            hours != null -> hours * 60
            minutes != null -> minutes
            else -> null
        }

        _uiState.value = _uiState.value.copy(
            duration = totalMinutes,
            durationHours = hours,
            durationMinutes = minutes
        )
    }

    fun updateLocation(location: String) {
        _uiState.value = _uiState.value.copy(location = location.takeIf { it.isNotBlank() })
    }

    fun updateBristolScale(scale: Int?) {
        if (scale == null || scale in 1..7) {
            _uiState.value = _uiState.value.copy(
                bristolScale = scale,
                bristolScaleError = null
            )
        } else {
            _uiState.value = _uiState.value.copy(
                bristolScaleError = "Bristol scale must be between 1 and 7"
            )
        }
    }

    fun addSuspectedTrigger(trigger: String) {
        if (trigger.isNotBlank() && !_uiState.value.suspectedTriggers.contains(trigger)) {
            val updated = _uiState.value.suspectedTriggers + trigger
            _uiState.value = _uiState.value.copy(suspectedTriggers = updated)
        }
    }

    fun removeSuspectedTrigger(trigger: String) {
        val updated = _uiState.value.suspectedTriggers.filter { it != trigger }
        _uiState.value = _uiState.value.copy(suspectedTriggers = updated)
    }

    fun updateTimestamp(timestamp: Instant) {
        _uiState.value = _uiState.value.copy(timestamp = timestamp)
    }

    fun updateNotes(notes: String) {
        _uiState.value = _uiState.value.copy(notes = notes.takeIf { it.isNotBlank() })
    }

    fun updatePhotoPath(photoPath: String?) {
        _uiState.value = _uiState.value.copy(photoPath = photoPath)
    }

    private fun loadRecentFoodSuggestions() {
        viewModelScope.launch {
            try {
                val recentFoods = foodEntryRepository.getRecent(20)
                    .first()
                    .map { it.name }
                    .distinct()
                    .take(10)
                _recentFoodSuggestions.value = recentFoods
            } catch (e: Exception) {
                _recentFoodSuggestions.value = emptyList()
            }
        }
    }

    fun saveSymptomEvent() {
        val currentState = _uiState.value

        // Validation
        if (currentState.severity !in 1..10) {
            _uiState.value = currentState.copy(severityError = "Severity must be between 1 and 10")
            return
        }

        if (currentState.symptomType.requiresBristolScale && currentState.bristolScale == null) {
            _uiState.value = currentState.copy(
                bristolScaleError = "Bristol Stool Scale is required for bowel movements"
            )
            return
        }

        if (currentState.bristolScale != null && currentState.bristolScale !in 1..7) {
            _uiState.value = currentState.copy(
                bristolScaleError = "Bristol scale must be between 1 and 7"
            )
            return
        }

        _uiState.value = currentState.copy(
            isLoading = true,
            severityError = null,
            bristolScaleError = null
        )

        viewModelScope.launch {
            try {
                val symptomEvent = SymptomEvent.create(
                    type = currentState.symptomType,
                    severity = currentState.severity,
                    timestamp = currentState.timestamp,
                    timezone = ZoneId.systemDefault().id,
                    duration = currentState.duration,
                    location = currentState.location,
                    bristolScale = currentState.bristolScale,
                    suspectedTriggers = currentState.suspectedTriggers.takeIf { it.isNotEmpty() },
                    notes = currentState.notes,
                    photoPath = currentState.photoPath
                )

                val eventId = addSymptomEventUseCase(symptomEvent)

                _uiState.value = currentState.copy(
                    isLoading = false,
                    isSuccess = true,
                    savedEventId = eventId
                )
            } catch (e: Exception) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to save symptom event"
                )
            }
        }
    }

    fun resetForm() {
        _uiState.value = SymptomEntryUiState()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun getBristolScaleDescription(scale: Int): String = when (scale) {
        1 -> "Type 1: Separate hard lumps, like nuts (hard to pass)"
        2 -> "Type 2: Sausage-shaped but lumpy"
        3 -> "Type 3: Like a sausage but with cracks on its surface"
        4 -> "Type 4: Like a sausage or snake, smooth and soft (ideal)"
        5 -> "Type 5: Soft blobs with clear-cut edges (passed easily)"
        6 -> "Type 6: Fluffy pieces with ragged edges, mushy stool"
        7 -> "Type 7: Watery, no solid pieces (entirely liquid)"
        else -> "Unknown type"
    }

    fun getSymptomSeverityDescription(severity: Int): String = when (severity) {
        1 -> "Barely noticeable"
        2 -> "Very mild"
        3 -> "Mild"
        4 -> "Mild to moderate"
        5 -> "Moderate"
        6 -> "Moderate to severe"
        7 -> "Severe"
        8 -> "Very severe"
        9 -> "Extremely severe"
        10 -> "Worst imaginable"
        else -> "Unknown"
    }
}

data class SymptomEntryUiState(
    val symptomType: SymptomType = SymptomType.BLOATING,
    val severity: Int = 5,
    val duration: Int? = null, // in minutes
    val durationHours: Int? = null,
    val durationMinutes: Int? = null,
    val location: String? = null,
    val bristolScale: Int? = null,
    val suspectedTriggers: List<String> = emptyList(),
    val timestamp: Instant = Instant.now(),
    val notes: String? = null,
    val photoPath: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val savedEventId: String? = null,
    val errorMessage: String? = null,
    val severityError: String? = null,
    val bristolScaleError: String? = null
) {
    val isValid: Boolean
        get() = severity in 1..10 &&
               severityError == null &&
               bristolScaleError == null &&
               (!symptomType.requiresBristolScale || bristolScale != null)

    val timestampFormatted: String
        get() = DateTimeFormatter.ofPattern("MMM dd, HH:mm")
            .withZone(ZoneId.systemDefault())
            .format(timestamp)

    val durationFormatted: String?
        get() = duration?.let { minutes ->
            when {
                minutes < 60 -> "${minutes}m"
                minutes % 60 == 0 -> "${minutes / 60}h"
                else -> "${minutes / 60}h ${minutes % 60}m"
            }
        }
}