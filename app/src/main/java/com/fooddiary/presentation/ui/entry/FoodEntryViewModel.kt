package com.fooddiary.presentation.ui.entry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.data.repository.FODMAPRepository
import com.fooddiary.data.repository.QuickEntryTemplateRepository
import com.fooddiary.data.repository.CorrelationRepository
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.FODMAPFood
import com.fooddiary.data.database.entities.QuickEntryTemplate
import com.fooddiary.data.models.*
import com.fooddiary.data.validation.FoodEntryValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import javax.inject.Inject

/**
 * ViewModel for Food Entry screen with validation and FODMAP analysis
 * Manages form state, food selection, portion input, and template management
 */
@HiltViewModel
class FoodEntryViewModel @Inject constructor(
    private val foodEntryRepository: FoodEntryRepository,
    private val fodmapRepository: FODMAPRepository,
    private val quickEntryTemplateRepository: QuickEntryTemplateRepository,
    private val correlationRepository: CorrelationRepository,
    private val foodEntryValidator: FoodEntryValidator
) : ViewModel() {

    private val _uiState = MutableStateFlow(FoodEntryUiState())
    val uiState: StateFlow<FoodEntryUiState> = _uiState.asStateFlow()

    init {
        loadTemplates()
        loadRecentEntries()
    }

    /**
     * Add food to the entry
     */
    fun addFood(foodName: String) {
        viewModelScope.launch {
            try {
                // Check for duplicates
                if (_uiState.value.selectedFoods.any { it.name.equals(foodName, ignoreCase = true) }) {
                    return@launch
                }

                // Search for FODMAP info
                val fodmapFoods = fodmapRepository.searchFood(foodName)
                val fodmapFood = fodmapFoods.firstOrNull() ?: FODMAPFood(
                    id = "unknown-$foodName",
                    name = foodName,
                    category = FoodCategory.OTHER,
                    overallLevel = FODMAPLevel.LOW,
                    oligosaccharides = FODMAPLevel.LOW,
                    disaccharides = FODMAPLevel.LOW,
                    monosaccharides = FODMAPLevel.LOW,
                    polyols = FODMAPLevel.LOW,
                    servingSize = "1 serving",
                    notes = null
                )

                val currentFoods = _uiState.value.selectedFoods.toMutableList()
                val currentPortions = _uiState.value.portions.toMutableList()

                currentFoods.add(fodmapFood)
                currentPortions.add("1 serving") // Default portion

                _uiState.value = _uiState.value.copy(
                    selectedFoods = currentFoods,
                    portions = currentPortions
                )

                // Analyze FODMAP content
                analyzeFODMAP()
                checkDuplicates()
                validateEntry()

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    message = "Error adding food: ${e.message}"
                )
            }
        }
    }

    /**
     * Remove food at index
     */
    fun removeFood(index: Int) {
        val currentFoods = _uiState.value.selectedFoods.toMutableList()
        val currentPortions = _uiState.value.portions.toMutableList()

        if (index < currentFoods.size) {
            currentFoods.removeAt(index)
            currentPortions.removeAt(index)

            _uiState.value = _uiState.value.copy(
                selectedFoods = currentFoods,
                portions = currentPortions
            )

            viewModelScope.launch {
                analyzeFODMAP()
                checkDuplicates()
                validateEntry()
            }
        }
    }

    /**
     * Update portion at index
     */
    fun updatePortion(index: Int, portion: String) {
        val currentPortions = _uiState.value.portions.toMutableList()

        if (index < currentPortions.size) {
            currentPortions[index] = portion

            _uiState.value = _uiState.value.copy(portions = currentPortions)

            viewModelScope.launch {
                validateEntry()
            }
        }
    }

    /**
     * Update meal type
     */
    fun updateMealType(mealType: MealType) {
        _uiState.value = _uiState.value.copy(mealType = mealType)
    }

    /**
     * Update timestamp
     */
    fun updateTimestamp(timestamp: Instant) {
        _uiState.value = _uiState.value.copy(timestamp = timestamp)
    }

    /**
     * Update notes
     */
    fun updateNotes(notes: String) {
        _uiState.value = _uiState.value.copy(notes = notes)
    }

    /**
     * Validate current entry
     */
    fun validateEntry() {
        viewModelScope.launch {
            val currentState = _uiState.value

            val tempEntry = FoodEntry(
                id = "",
                timestamp = currentState.timestamp,
                mealType = currentState.mealType,
                foods = currentState.selectedFoods.map { it.name },
                portions = currentState.portions,
                notes = currentState.notes,
                isDeleted = false
            )

            val validationResult = foodEntryValidator.validateEntry(tempEntry)

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
     * Save the food entry
     */
    suspend fun saveEntry(): Boolean {
        return try {
            _uiState.value = _uiState.value.copy(isSaving = true)

            // Validate first
            validateEntry()

            if (!_uiState.value.isValid) {
                _uiState.value = _uiState.value.copy(isSaving = false)
                return false
            }

            val currentState = _uiState.value
            val entry = FoodEntry(
                id = "",
                timestamp = currentState.timestamp,
                mealType = currentState.mealType,
                foods = currentState.selectedFoods.map { it.name },
                portions = currentState.portions,
                notes = currentState.notes,
                isDeleted = false
            )

            val savedId = foodEntryRepository.insert(entry)

            _uiState.value = _uiState.value.copy(
                isSaving = false,
                message = "Entry saved successfully"
            )

            true
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isSaving = false,
                message = "Error saving entry: ${e.message}"
            )
            false
        }
    }

    /**
     * Load quick entry templates
     */
    fun loadTemplates() {
        viewModelScope.launch {
            quickEntryTemplateRepository.getAllActive().collect { templates ->
                _uiState.value = _uiState.value.copy(
                    quickEntryTemplates = templates,
                    isLoadingTemplates = false
                )
            }
        }
    }

    /**
     * Apply quick entry template
     */
    fun applyTemplate(templateId: String) {
        viewModelScope.launch {
            try {
                val template = quickEntryTemplateRepository.getById(templateId)
                if (template != null) {
                    clearForm()

                    // Add foods from template
                    template.foods.forEach { foodName ->
                        addFood(foodName)
                    }

                    // Update meal type and portions
                    _uiState.value = _uiState.value.copy(
                        mealType = template.mealType,
                        portions = template.portions.toMutableList(),
                        notes = template.notes ?: ""
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    message = "Error applying template: ${e.message}"
                )
            }
        }
    }

    /**
     * Search for foods in FODMAP database
     */
    suspend fun searchFood(query: String): List<FODMAPFood> {
        return try {
            fodmapRepository.searchFood(query)
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Clear form data
     */
    fun clearForm() {
        _uiState.value = FoodEntryUiState()
        loadTemplates()
        loadRecentEntries()
    }

    /**
     * Analyze FODMAP content of current meal
     */
    private suspend fun analyzeFODMAP() {
        try {
            val foodNames = _uiState.value.selectedFoods.map { it.name }
            if (foodNames.isNotEmpty()) {
                val analysis = fodmapRepository.analyzeMeal(foodNames)
                _uiState.value = _uiState.value.copy(fodmapAnalysis = analysis)
            } else {
                _uiState.value = _uiState.value.copy(fodmapAnalysis = null)
            }
        } catch (e: Exception) {
            // Ignore FODMAP analysis errors
        }
    }

    /**
     * Check for duplicate entries in recent history
     */
    private fun checkDuplicates() {
        viewModelScope.launch {
            try {
                foodEntryRepository.getRecent(7).collect { recentEntries ->
                    val currentFoods = _uiState.value.selectedFoods.map { it.name }.toSet()

                    val duplicateWarning = recentEntries.find { entry ->
                        val recentFoods = entry.foods.toSet()
                        // Check for similar entries (50% or more foods in common)
                        val intersection = currentFoods.intersect(recentFoods)
                        intersection.size >= (currentFoods.size * 0.5).toInt() && currentFoods.isNotEmpty()
                    }?.let {
                        "You logged a similar entry recently. Are you sure you want to add this?"
                    }

                    _uiState.value = _uiState.value.copy(duplicateWarning = duplicateWarning)
                }
            } catch (e: Exception) {
                // Ignore duplicate checking errors
            }
        }
    }

    /**
     * Load recent entries for duplicate detection
     */
    private fun loadRecentEntries() {
        // This is handled in checkDuplicates()
    }
}

/**
 * UI State for Food Entry screen
 */
data class FoodEntryUiState(
    val selectedFoods: List<FODMAPFood> = emptyList(),
    val portions: List<String> = emptyList(),
    val mealType: MealType = MealType.BREAKFAST,
    val timestamp: Instant = Instant.now(),
    val notes: String = "",
    val validationErrors: List<String> = emptyList(),
    val isValid: Boolean = false,
    val isSaving: Boolean = false,
    val isLoadingTemplates: Boolean = true,
    val quickEntryTemplates: List<QuickEntryTemplate> = emptyList(),
    val fodmapAnalysis: FODMAPAnalysis? = null,
    val duplicateWarning: String? = null,
    val message: String? = null
)

/**
 * Validation result sealed class
 */
sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val errors: List<String>) : ValidationResult()
}

/**
 * FODMAP levels enum
 */
enum class FODMAPLevel {
    LOW, MODERATE, HIGH;

    val displayName: String
        get() = when (this) {
            LOW -> "Low FODMAP"
            MODERATE -> "Moderate FODMAP"
            HIGH -> "High FODMAP"
        }
}

/**
 * Food categories enum
 */
enum class FoodCategory {
    FRUITS, VEGETABLES, GRAINS, PROTEIN, DAIRY, FATS, BEVERAGES, CONDIMENTS, OTHER
}

/**
 * FODMAP Analysis data class
 */
data class FODMAPAnalysis(
    val overallLevel: FODMAPLevel,
    val oligosaccharides: FODMAPLevel,
    val disaccharides: FODMAPLevel,
    val monosaccharides: FODMAPLevel,
    val polyols: FODMAPLevel,
    val problematicIngredients: List<String>
)