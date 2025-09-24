package com.fooddiary.domain.usecase

import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.data.repository.FODMAPRepository
import java.time.Instant
import javax.inject.Inject

class AddFoodEntryUseCase @Inject constructor(
    private val foodEntryRepository: FoodEntryRepository,
    private val fodmapRepository: FODMAPRepository
) {
    suspend operator fun invoke(
        foodName: String,
        portions: Double,
        portionUnit: String,
        mealType: String,
        ingredients: List<String>,
        timestamp: Instant = Instant.now(),
        location: String? = null,
        socialContext: String? = null,
        eatingSpeed: String? = null,
        preparationMethod: String? = null,
        notes: String? = null
    ): Result<Long> {
        return try {
            // Validate input
            require(foodName.isNotBlank()) { "Food name cannot be empty" }
            require(portions > 0) { "Portions must be greater than 0" }
            require(mealType.isNotBlank()) { "Meal type cannot be empty" }

            // Analyze FODMAP content if ingredients are provided
            val fodmapLevel = if (ingredients.isNotEmpty()) {
                fodmapRepository.analyzeFoodIngredients(ingredients)
            } else {
                null
            }

            // Create food entry
            val foodEntry = FoodEntry(
                foodName = foodName,
                portions = portions,
                portionUnit = portionUnit,
                mealType = mealType,
                ingredients = ingredients,
                timestamp = timestamp,
                location = location,
                socialContext = socialContext,
                eatingSpeed = eatingSpeed,
                preparationMethod = preparationMethod,
                notes = notes,
                fodmapLevel = fodmapLevel
            )

            // Save to database
            val id = foodEntryRepository.insert(foodEntry)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}