package com.fooddiary.data.validation

import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.models.MealType
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodEntryValidator @Inject constructor() {

    fun validate(entry: FoodEntry): ValidationResult {
        return when {
            entry.foods.isEmpty() -> ValidationResult.Error("Foods list cannot be empty")
            entry.foods.any { it.isBlank() } -> ValidationResult.Error("Food names cannot be blank")
            entry.portions.isEmpty() -> ValidationResult.Error("Portions cannot be empty")
            entry.foods.size != entry.portions.size -> ValidationResult.Error("All foods must have corresponding portions")
            !entry.foods.all { entry.portions.containsKey(it) } -> ValidationResult.Error("All foods must have corresponding portions")
            entry.portions.values.any { it.isBlank() } -> ValidationResult.Error("Portion values cannot be blank")
            entry.timestamp.isAfter(Instant.now().plus(1, ChronoUnit.HOURS)) -> ValidationResult.Error("Entry cannot be in the future")
            entry.timestamp.isBefore(Instant.now().minus(365 * 2, ChronoUnit.DAYS)) -> ValidationResult.Error("Entry cannot be more than 2 years old")
            entry.foods.size > 20 -> ValidationResult.Error("Maximum 20 foods per entry")
            entry.notes?.length ?: 0 > 1000 -> ValidationResult.Error("Notes cannot exceed 1000 characters")
            else -> ValidationResult.Success
        }
    }

    fun validatePortionFormat(portion: String): Boolean {
        if (portion.isBlank()) return false

        // Common portion formats
        val validFormats = listOf(
            Regex("\\d+(\\.\\d+)?\\s*(cup|cups|tbsp|tsp|oz|g|kg|ml|l|slice|slices|piece|pieces|serving|servings|medium|small|large)s?", RegexOption.IGNORE_CASE),
            Regex("\\d+(\\.\\d+)?\\s*x\\s*.+", RegexOption.IGNORE_CASE), // "2 x medium"
            Regex("1/\\d+\\s*.+", RegexOption.IGNORE_CASE), // "1/2 cup"
            Regex("\\d+/\\d+\\s*.+", RegexOption.IGNORE_CASE), // "3/4 cup"
            Regex(".+\\s*\\(\\d+g\\)", RegexOption.IGNORE_CASE), // "1 medium (150g)"
        )

        return validFormats.any { it.matches(portion) }
    }

    fun validateMealTiming(mealType: MealType, timestamp: Instant): ValidationResult {
        val hour = timestamp.atZone(java.time.ZoneId.systemDefault()).hour

        return when (mealType) {
            MealType.BREAKFAST -> {
                if (hour in 4..11) ValidationResult.Success
                else ValidationResult.Warning("Breakfast is typically logged between 4 AM and 11 AM")
            }
            MealType.LUNCH -> {
                if (hour in 11..15) ValidationResult.Success
                else ValidationResult.Warning("Lunch is typically logged between 11 AM and 3 PM")
            }
            MealType.DINNER -> {
                if (hour in 17..22) ValidationResult.Success
                else ValidationResult.Warning("Dinner is typically logged between 5 PM and 10 PM")
            }
            MealType.SNACK -> ValidationResult.Success // Snacks can be at any time
        }
    }

    fun validateFoodCombination(foods: List<String>): ValidationResult {
        val problematicCombinations = mapOf(
            "alcohol" to listOf("medication", "medicine", "pills"),
            "dairy" to listOf("high fiber", "beans", "legumes"),
            "high fat" to listOf("high sugar", "candy", "soda")
        )

        for ((problematic, conflicts) in problematicCombinations) {
            val hasProblematic = foods.any { it.lowercase().contains(problematic) }
            val hasConflict = foods.any { food ->
                conflicts.any { conflict -> food.lowercase().contains(conflict) }
            }

            if (hasProblematic && hasConflict) {
                return ValidationResult.Warning("This combination of foods may cause digestive issues")
            }
        }

        return ValidationResult.Success
    }

    fun suggestPortionImprovements(portions: Map<String, String>): List<String> {
        val suggestions = mutableListOf<String>()

        portions.forEach { (food, portion) ->
            if (!validatePortionFormat(portion)) {
                suggestions.add("Consider using a more specific portion for '$food' (e.g., '1 cup', '150g', '1 medium')")
            }

            if (portion.lowercase().contains("handful") || portion.lowercase().contains("some")) {
                suggestions.add("Try to be more specific than '$portion' for '$food'")
            }
        }

        return suggestions
    }

    fun validateDuplicateFoods(foods: List<String>): ValidationResult {
        val duplicates = foods.groupBy { it.lowercase().trim() }
            .filter { it.value.size > 1 }
            .keys

        return if (duplicates.isNotEmpty()) {
            ValidationResult.Warning("Duplicate foods detected: ${duplicates.joinToString(", ")}")
        } else {
            ValidationResult.Success
        }
    }
}

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
    data class Warning(val message: String) : ValidationResult()

    val isValid: Boolean get() = this is Success || this is Warning
    val isError: Boolean get() = this is Error
    val isWarning: Boolean get() = this is Warning
}