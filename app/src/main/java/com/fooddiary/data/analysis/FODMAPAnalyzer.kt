package com.fooddiary.data.analysis

/**
 * FODMAP Analyzer for ingredient classification and elimination diet support
 * Provides analysis of foods according to the FODMAP (Fermentable, Oligo-, Di-, Mono-saccharides And Polyols) classification
 */
object FODMAPAnalyzer {

    enum class FODMAPLevel {
        LOW, MODERATE, HIGH, UNKNOWN
    }

    enum class FODMAPType {
        OLIGOSACCHARIDES,
        DISACCHARIDES,
        MONOSACCHARIDES,
        POLYOLS,
        MIXED
    }

    data class FODMAPAnalysis(
        val level: FODMAPLevel,
        val types: List<FODMAPType>,
        val reason: String,
        val isEliminationSafe: Boolean
    )

    /**
     * Analyze a food ingredient for FODMAP content
     * @param ingredient The ingredient to analyze
     * @return FODMAP analysis result
     */
    fun analyzeIngredient(ingredient: String): FODMAPAnalysis {
        val normalizedIngredient = ingredient.lowercase().trim()

        return when {
            isHighFODMAP(normalizedIngredient) -> FODMAPAnalysis(
                level = FODMAPLevel.HIGH,
                types = getHighFODMAPTypes(normalizedIngredient),
                reason = "Contains high FODMAP components",
                isEliminationSafe = false
            )
            isModerateFODMAP(normalizedIngredient) -> FODMAPAnalysis(
                level = FODMAPLevel.MODERATE,
                types = getModerateFODMAPTypes(normalizedIngredient),
                reason = "Contains moderate FODMAP components",
                isEliminationSafe = false
            )
            isLowFODMAP(normalizedIngredient) -> FODMAPAnalysis(
                level = FODMAPLevel.LOW,
                types = emptyList(),
                reason = "Low FODMAP content",
                isEliminationSafe = true
            )
            else -> FODMAPAnalysis(
                level = FODMAPLevel.UNKNOWN,
                types = emptyList(),
                reason = "FODMAP content not classified",
                isEliminationSafe = false
            )
        }
    }

    /**
     * Check if an ingredient is safe for elimination phase
     * @param ingredient The ingredient to check
     * @return true if safe for elimination diet
     */
    fun isEliminationSafe(ingredient: String): Boolean {
        return analyzeIngredient(ingredient).isEliminationSafe
    }

    /**
     * Get FODMAP level for an ingredient
     * @param ingredient The ingredient to analyze
     * @return FODMAP level
     */
    fun getFODMAPLevel(ingredient: String): FODMAPLevel {
        return analyzeIngredient(ingredient).level
    }

    private fun isHighFODMAP(ingredient: String): Boolean {
        val highFODMAPFoods = setOf(
            "wheat", "rye", "barley", "onion", "garlic", "apple", "pear",
            "mango", "watermelon", "beans", "lentils", "chickpeas", "milk",
            "yogurt", "ice cream", "honey", "agave", "cashews", "pistachios"
        )
        return highFODMAPFoods.any { ingredient.contains(it) }
    }

    private fun isModerateFODMAP(ingredient: String): Boolean {
        val moderateFODMAPFoods = setOf(
            "avocado", "beetroot", "brussels sprouts", "cabbage", "fennel",
            "snow peas", "sweet corn", "almonds", "hazelnuts"
        )
        return moderateFODMAPFoods.any { ingredient.contains(it) }
    }

    private fun isLowFODMAP(ingredient: String): Boolean {
        val lowFODMAPFoods = setOf(
            "rice", "quinoa", "oats", "potato", "carrot", "spinach", "lettuce",
            "tomato", "cucumber", "strawberry", "orange", "banana", "grape",
            "chicken", "beef", "fish", "egg", "tofu", "lactose-free milk"
        )
        return lowFODMAPFoods.any { ingredient.contains(it) }
    }

    private fun getHighFODMAPTypes(ingredient: String): List<FODMAPType> {
        return when {
            ingredient.contains("wheat") || ingredient.contains("onion") -> listOf(FODMAPType.OLIGOSACCHARIDES)
            ingredient.contains("milk") || ingredient.contains("yogurt") -> listOf(FODMAPType.DISACCHARIDES)
            ingredient.contains("apple") || ingredient.contains("honey") -> listOf(FODMAPType.MONOSACCHARIDES)
            ingredient.contains("avocado") || ingredient.contains("mushroom") -> listOf(FODMAPType.POLYOLS)
            else -> listOf(FODMAPType.MIXED)
        }
    }

    private fun getModerateFODMAPTypes(ingredient: String): List<FODMAPType> {
        return when {
            ingredient.contains("beetroot") || ingredient.contains("cabbage") -> listOf(FODMAPType.OLIGOSACCHARIDES)
            ingredient.contains("almonds") -> listOf(FODMAPType.POLYOLS)
            else -> listOf(FODMAPType.MIXED)
        }
    }
}