package com.fooddiary.data.repository

import com.fooddiary.data.database.dao.FoodEntryDao
import com.fooddiary.data.database.dao.FODMAPFoodDao
import com.fooddiary.data.database.dao.QuickEntryTemplateDao
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.FODMAPFood
import com.fooddiary.data.database.entities.QuickEntryTemplate
import com.fooddiary.data.models.*
import com.fooddiary.data.validation.FoodEntryValidator
import com.fooddiary.data.validation.ValidationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for food entry operations with validation and FODMAP analysis
 */
@Singleton
class FoodEntryRepository @Inject constructor(
    private val foodEntryDao: FoodEntryDao,
    private val fodmapFoodDao: FODMAPFoodDao,
    private val quickEntryTemplateDao: QuickEntryTemplateDao,
    private val validator: FoodEntryValidator
) {

    /**
     * Add a new food entry with validation
     */
    suspend fun addFoodEntry(entry: FoodEntry): Result<Long> {
        return try {
            val validationResult = validator.validate(entry)

            if (validationResult.isError) {
                Result.failure(Exception((validationResult as ValidationResult.Error).message))
            } else {
                val insertedId = foodEntryDao.insert(entry)
                Result.success(insertedId)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Update an existing food entry with validation
     */
    suspend fun updateFoodEntry(entry: FoodEntry): Result<Unit> {
        return try {
            val existingEntry = foodEntryDao.getById(entry.id)
                ?: return Result.failure(Exception("Food entry not found"))

            val validationResult = validator.validate(entry)

            if (validationResult.isError) {
                Result.failure(Exception((validationResult as ValidationResult.Error).message))
            } else {
                val updatedEntry = entry.copy(modifiedAt = Instant.now())
                foodEntryDao.update(updatedEntry)
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Soft delete a food entry
     */
    suspend fun deleteFoodEntry(entryId: String): Result<Unit> {
        return try {
            val id = entryId.removePrefix("entry-").toLong()
            val existingEntry = foodEntryDao.getById(id)
                ?: return Result.failure(Exception("Food entry not found"))

            val deletedEntry = existingEntry.softDelete()
            foodEntryDao.update(deletedEntry)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Search foods with FODMAP analysis
     */
    fun searchFoods(query: String): Flow<List<FoodSearchResult>> {
        return foodEntryDao.getAll().map { entries ->
            val matchingEntries = entries.filter { entry ->
                entry.foods.any { food ->
                    food.lowercase().contains(query.lowercase())
                }
            }

            matchingEntries.map { entry ->
                FoodSearchResult(
                    entry = entry,
                    fodmapAnalysis = analyzeFODMAP(entry.foods),
                    nutritionalInfo = analyzeNutritionalContent(entry),
                    relevanceScore = calculateRelevanceScore(entry, query)
                )
            }
        }
    }

    /**
     * Validate a food entry
     */
    suspend fun validateEntry(entry: FoodEntry): EntryValidationResult {
        val validationResult = validator.validate(entry)

        val warnings = mutableListOf<String>()

        // Add timing warnings
        val timingResult = validator.validateMealTiming(entry.mealType, entry.timestamp)
        if (timingResult is ValidationResult.Warning) {
            warnings.add(timingResult.message)
        }

        // Add combination warnings
        val combinationResult = validator.validateFoodCombination(entry.foods)
        if (combinationResult is ValidationResult.Warning) {
            warnings.add(combinationResult.message)
        }

        // Add duplicate warnings
        val duplicateResult = validator.validateDuplicateFoods(entry.foods)
        if (duplicateResult is ValidationResult.Warning) {
            warnings.add(duplicateResult.message)
        }

        // Add portion suggestions
        warnings.addAll(validator.suggestPortionImprovements(entry.portions))

        return when (validationResult) {
            is ValidationResult.Success -> EntryValidationResult(
                isValid = true,
                warnings = warnings
            )
            is ValidationResult.Error -> EntryValidationResult(
                isValid = false,
                errorMessage = validationResult.message,
                warnings = warnings
            )
            is ValidationResult.Warning -> EntryValidationResult(
                isValid = true,
                errorMessage = null,
                warnings = warnings + validationResult.message
            )
        }
    }

    /**
     * Get most frequently consumed foods
     */
    suspend fun getMostFrequent(limit: Int): List<FoodEntry> {
        return foodEntryDao.getMostFrequentFoods(limit)
    }

    /**
     * Get entries for a date range
     */
    fun getEntriesForDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<FoodEntry>> {
        return foodEntryDao.getAllByDateRange(startDate.toString(), endDate.toString())
    }

    /**
     * Analyze nutritional content of a food entry
     */
    suspend fun analyzeNutritionalContent(entry: FoodEntry): NutritionalInfo {
        val fodmapAnalysis = analyzeFODMAP(entry.foods)
        val allergens = identifyPotentialAllergens(entry.foods)

        // Simplified calorie estimation
        val estimatedCalories = estimateCalories(entry.foods, entry.portions)

        // Basic macronutrient estimation
        val macros = estimateMacronutrients(entry.foods)

        return NutritionalInfo(
            estimatedCalories = estimatedCalories,
            macronutrients = macros,
            potentialAllergens = allergens,
            fodmapRisk = fodmapAnalysis?.overallLevel ?: FODMAPLevel.MEDIUM
        )
    }

    /**
     * Detect duplicate entries within a time window
     */
    suspend fun detectDuplicateEntries(entry: FoodEntry, timeWindowMinutes: Int = 15): List<FoodEntry> {
        val startTime = entry.timestamp.minus(timeWindowMinutes.toLong(), ChronoUnit.MINUTES)
        val endTime = entry.timestamp.plus(timeWindowMinutes.toLong(), ChronoUnit.MINUTES)

        val existingEntries = foodEntryDao.getEntriesInTimeWindow(startTime, endTime)

        return existingEntries.filter { existing ->
            existing.id != entry.id &&
            existing.foods.size == entry.foods.size &&
            existing.foods.containsAll(entry.foods)
        }
    }

    /**
     * Create entry from template
     */
    suspend fun createFromTemplate(templateId: String, mealType: MealType): Result<Long> {
        return try {
            val id = templateId.removePrefix("template-").toLong()
            val template = quickEntryTemplateDao.getById(id)
                ?: return Result.failure(Exception("Template not found"))

            val entry = FoodEntry.create(
                foods = template.foods,
                portions = template.defaultPortions,
                mealType = mealType,
                timestamp = Instant.now()
            )

            addFoodEntry(entry)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Batch insert multiple entries
     */
    suspend fun batchInsert(entries: List<FoodEntry>): Result<List<Long>> {
        return try {
            // Validate all entries first
            val validationErrors = entries.mapIndexedNotNull { index, entry ->
                val result = validator.validate(entry)
                if (result.isError) {
                    "$index: ${(result as ValidationResult.Error).message}"
                } else null
            }

            if (validationErrors.isNotEmpty()) {
                return Result.failure(Exception("Validation errors: ${validationErrors.joinToString("; ")}"))
            }

            val insertedIds = foodEntryDao.insertAll(entries)
            Result.success(insertedIds)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Private helper methods

    private suspend fun analyzeFODMAP(foods: List<String>): FODMAPAnalysis? {
        val fodmapFoods = mutableListOf<FODMAPFood>()

        for (food in foods) {
            val matches = fodmapFoodDao.searchByName(food)
            fodmapFoods.addAll(matches)
        }

        if (fodmapFoods.isEmpty()) return null

        val overallLevel = when {
            fodmapFoods.any { it.overallLevel == FODMAPLevel.HIGH } -> FODMAPLevel.HIGH
            fodmapFoods.any { it.overallLevel == FODMAPLevel.MEDIUM } -> FODMAPLevel.MEDIUM
            else -> FODMAPLevel.LOW
        }

        val maxOligos = fodmapFoods.maxByOrNull { it.oligosaccharides.ordinal }?.oligosaccharides ?: FODMAPLevel.LOW
        val maxDisaccharides = fodmapFoods.maxByOrNull { it.disaccharides.ordinal }?.disaccharides ?: FODMAPLevel.LOW
        val maxMonosaccharides = fodmapFoods.maxByOrNull { it.monosaccharides.ordinal }?.monosaccharides ?: FODMAPLevel.LOW
        val maxPolyols = fodmapFoods.maxByOrNull { it.polyols.ordinal }?.polyols ?: FODMAPLevel.LOW

        val riskFoods = fodmapFoods.filter { it.isHighFODMAP }.map { it.name }

        return FODMAPAnalysis(
            overallLevel = overallLevel,
            oligosaccharides = maxOligos,
            disaccharides = maxDisaccharides,
            monosaccharides = maxMonosaccharides,
            polyols = maxPolyols,
            riskFoods = riskFoods
        )
    }

    private fun identifyPotentialAllergens(foods: List<String>): List<String> {
        val allergenKeywords = mapOf(
            "Dairy" to listOf("milk", "cheese", "butter", "cream", "yogurt", "dairy"),
            "Gluten" to listOf("wheat", "bread", "pasta", "flour", "barley", "rye", "oats"),
            "Nuts" to listOf("nut", "almond", "walnut", "peanut", "cashew", "hazelnut"),
            "Soy" to listOf("soy", "tofu", "soya", "edamame"),
            "Eggs" to listOf("egg", "eggs"),
            "Shellfish" to listOf("shrimp", "lobster", "crab", "shellfish", "prawns"),
            "Fish" to listOf("fish", "salmon", "tuna", "cod", "mackerel")
        )

        return allergenKeywords.filter { (_, keywords) ->
            foods.any { food ->
                keywords.any { keyword ->
                    food.lowercase().contains(keyword.lowercase())
                }
            }
        }.keys.toList()
    }

    private fun estimateCalories(foods: List<String>, portions: Map<String, String>): Int {
        // Simplified calorie estimation based on food types
        val calorieMap = mapOf(
            "rice" to 200, "bread" to 250, "pasta" to 220,
            "chicken" to 300, "beef" to 350, "fish" to 200,
            "apple" to 80, "banana" to 100, "orange" to 60,
            "broccoli" to 55, "carrot" to 40, "potato" to 160,
            "milk" to 150, "cheese" to 400, "butter" to 750
        )

        return foods.sumOf { food ->
            calorieMap.entries.find { entry ->
                food.lowercase().contains(entry.key)
            }?.value ?: 150 // Default calories
        }
    }

    private fun estimateMacronutrients(foods: List<String>): Map<String, Float> {
        // Simplified macro estimation
        val proteinFoods = listOf("chicken", "beef", "fish", "egg", "tofu", "beans")
        val carbFoods = listOf("rice", "bread", "pasta", "potato", "fruit")
        val fatFoods = listOf("oil", "butter", "nuts", "avocado", "cheese")

        val protein = foods.count { food -> proteinFoods.any { food.lowercase().contains(it) } } * 25f
        val carbs = foods.count { food -> carbFoods.any { food.lowercase().contains(it) } } * 30f
        val fat = foods.count { food -> fatFoods.any { food.lowercase().contains(it) } } * 20f

        val total = protein + carbs + fat
        if (total == 0f) return mapOf("protein" to 33.3f, "carbs" to 33.3f, "fat" to 33.3f)

        return mapOf(
            "protein" to (protein / total) * 100,
            "carbs" to (carbs / total) * 100,
            "fat" to (fat / total) * 100
        )
    }

    private fun calculateRelevanceScore(entry: FoodEntry, query: String): Float {
        val queryLower = query.lowercase()
        var score = 0f

        // Exact matches get highest score
        entry.foods.forEach { food ->
            when {
                food.lowercase() == queryLower -> score += 1.0f
                food.lowercase().startsWith(queryLower) -> score += 0.8f
                food.lowercase().contains(queryLower) -> score += 0.6f
            }
        }

        // Recent entries get bonus
        val daysSinceEntry = ChronoUnit.DAYS.between(entry.timestamp, Instant.now())
        if (daysSinceEntry < 7) score += 0.2f
        if (daysSinceEntry < 1) score += 0.1f

        return score
    }
}

/**
 * Supporting data classes
 */
data class FoodSearchResult(
    val entry: FoodEntry,
    val fodmapAnalysis: FODMAPAnalysis?,
    val nutritionalInfo: NutritionalInfo?,
    val relevanceScore: Float
)

data class NutritionalInfo(
    val estimatedCalories: Int,
    val macronutrients: Map<String, Float>, // protein, carbs, fat percentages
    val potentialAllergens: List<String>,
    val fodmapRisk: FODMAPLevel
)

data class FODMAPAnalysis(
    val overallLevel: FODMAPLevel,
    val oligosaccharides: FODMAPLevel,
    val disaccharides: FODMAPLevel,
    val monosaccharides: FODMAPLevel,
    val polyols: FODMAPLevel,
    val riskFoods: List<String>
)

data class EntryValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null,
    val warnings: List<String> = emptyList()
)