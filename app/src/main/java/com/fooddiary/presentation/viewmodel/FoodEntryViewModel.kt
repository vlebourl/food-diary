package com.fooddiary.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.models.*
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.domain.usecase.AddFoodEntryUseCase
import com.fooddiary.domain.usecase.AnalyzeFODMAPContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class FoodEntryViewModel @Inject constructor(
    private val addFoodEntryUseCase: AddFoodEntryUseCase,
    private val analyzeFODMAPContentUseCase: AnalyzeFODMAPContentUseCase,
    private val foodEntryRepository: FoodEntryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FoodEntryUiState())
    val uiState: StateFlow<FoodEntryUiState> = _uiState.asStateFlow()

    private val _fodmapAnalysis = MutableStateFlow<FODMAPAnalysis?>(null)
    val fodmapAnalysis: StateFlow<FODMAPAnalysis?> = _fodmapAnalysis.asStateFlow()

    fun updateFoodName(name: String) {
        _uiState.value = _uiState.value.copy(foodName = name)
        if (name.isNotBlank()) {
            analyzeFODMAPContent()
        }
    }

    fun updateIngredients(ingredients: List<String>) {
        _uiState.value = _uiState.value.copy(ingredients = ingredients)
        analyzeFODMAPContent()
    }

    fun addIngredient(ingredient: String) {
        if (ingredient.isNotBlank() && !_uiState.value.ingredients.contains(ingredient)) {
            val updated = _uiState.value.ingredients + ingredient
            updateIngredients(updated)
        }
    }

    fun removeIngredient(ingredient: String) {
        val updated = _uiState.value.ingredients.filter { it != ingredient }
        updateIngredients(updated)
    }

    fun updatePortions(portions: String) {
        _uiState.value = _uiState.value.copy(
            portions = portions,
            portionsError = if (portions.toFloatOrNull() != null) null else "Invalid number"
        )
    }

    fun updatePortionUnit(unit: String) {
        _uiState.value = _uiState.value.copy(portionUnit = unit)
    }

    fun updateMealType(mealType: MealType) {
        _uiState.value = _uiState.value.copy(mealType = mealType)
    }

    fun updateLocation(location: LocationType) {
        val currentContext = _uiState.value.context
        _uiState.value = _uiState.value.copy(
            context = currentContext.copy(location = location)
        )
    }

    fun updateSocialContext(social: SocialContext) {
        val currentContext = _uiState.value.context
        _uiState.value = _uiState.value.copy(
            context = currentContext.copy(social = social)
        )
    }

    fun updateEatingSpeed(speed: EatingSpeed) {
        val currentContext = _uiState.value.context
        _uiState.value = _uiState.value.copy(
            context = currentContext.copy(speed = speed)
        )
    }

    fun updateTimestamp(timestamp: Instant) {
        _uiState.value = _uiState.value.copy(timestamp = timestamp)
    }

    fun updatePreparationMethod(method: String) {
        _uiState.value = _uiState.value.copy(preparationMethod = method.takeIf { it.isNotBlank() })
    }

    fun updateNotes(notes: String) {
        _uiState.value = _uiState.value.copy(notes = notes.takeIf { it.isNotBlank() })
    }

    private fun analyzeFODMAPContent() {
        val currentState = _uiState.value
        val allIngredients = listOf(currentState.foodName) + currentState.ingredients

        viewModelScope.launch {
            try {
                val analysis = analyzeFODMAPContentUseCase(allIngredients)
                _fodmapAnalysis.value = analysis
            } catch (e: Exception) {
                // Handle error - could emit error state
                _fodmapAnalysis.value = null
            }
        }
    }

    fun saveFoodEntry() {
        val currentState = _uiState.value

        // Validation
        if (currentState.foodName.isBlank()) {
            _uiState.value = currentState.copy(foodNameError = "Food name is required")
            return
        }

        val portions = currentState.portions.toFloatOrNull()
        if (portions == null || portions <= 0) {
            _uiState.value = currentState.copy(portionsError = "Valid portion amount required")
            return
        }

        _uiState.value = currentState.copy(
            isLoading = true,
            foodNameError = null,
            portionsError = null
        )

        viewModelScope.launch {
            try {
                val foodEntry = FoodEntry.create(
                    name = currentState.foodName,
                    ingredients = currentState.ingredients,
                    portions = portions,
                    portionUnit = currentState.portionUnit,
                    mealType = currentState.mealType,
                    context = currentState.context,
                    timestamp = currentState.timestamp,
                    timezone = ZoneId.systemDefault().id,
                    preparationMethod = currentState.preparationMethod,
                    notes = currentState.notes
                )

                val entryId = addFoodEntryUseCase(foodEntry)

                _uiState.value = currentState.copy(
                    isLoading = false,
                    isSuccess = true,
                    savedEntryId = entryId
                )
            } catch (e: Exception) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to save food entry"
                )
            }
        }
    }

    fun resetForm() {
        _uiState.value = FoodEntryUiState()
        _fodmapAnalysis.value = null
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun getSuggestedIngredients(query: String): Flow<List<String>> {
        return if (query.length >= 2) {
            flow {
                try {
                    val suggestions = foodEntryRepository.searchByIngredient(query)
                        .first()
                        .flatMap { it.ingredients }
                        .filter { it.contains(query, ignoreCase = true) }
                        .distinct()
                        .take(5)
                    emit(suggestions)
                } catch (e: Exception) {
                    emit(emptyList())
                }
            }
        } else {
            flowOf(emptyList())
        }
    }

    fun getRecentFoodNames(): Flow<List<String>> = flow {
        try {
            val recent = foodEntryRepository.getRecent(10)
                .first()
                .map { it.name }
                .distinct()
            emit(recent)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}

data class FoodEntryUiState(
    val foodName: String = "",
    val ingredients: List<String> = emptyList(),
    val portions: String = "",
    val portionUnit: String = "grams",
    val mealType: MealType = MealType.SNACK,
    val context: ConsumptionContext = ConsumptionContext(
        location = LocationType.HOME,
        social = SocialContext.ALONE,
        speed = EatingSpeed.NORMAL
    ),
    val timestamp: Instant = Instant.now(),
    val preparationMethod: String? = null,
    val notes: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val savedEntryId: String? = null,
    val errorMessage: String? = null,
    val foodNameError: String? = null,
    val portionsError: String? = null
) {
    val isValid: Boolean
        get() = foodName.isNotBlank() &&
               portions.toFloatOrNull()?.let { it > 0 } == true &&
               foodNameError == null &&
               portionsError == null

    val timestampFormatted: String
        get() = DateTimeFormatter.ofPattern("MMM dd, HH:mm")
            .withZone(ZoneId.systemDefault())
            .format(timestamp)
}