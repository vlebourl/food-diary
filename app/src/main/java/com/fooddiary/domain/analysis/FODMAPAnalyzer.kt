package com.fooddiary.domain.analysis

import com.fooddiary.data.database.entities.FODMAPFood
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.models.FODMAPLevel
import com.fooddiary.data.repository.FODMAPRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FODMAPAnalyzer @Inject constructor(
    private val fodmapRepository: FODMAPRepository
) {

    /**
     * Analyze a single food item for FODMAP content
     */
    suspend fun analyzeFoodItem(foodName: String): FODMAPAnalysisResult {
        return try {
            val fodmapFood = fodmapRepository.getByName(foodName.lowercase().trim())

            if (fodmapFood != null) {
                FODMAPAnalysisResult(
                    foodName = foodName,
                    overallLevel = fodmapFood.overallLevel,
                    oligosaccharides = fodmapFood.oligosaccharides,
                    disaccharides = fodmapFood.disaccharides,
                    monosaccharides = fodmapFood.monosaccharides,
                    polyols = fodmapFood.polyols,
                    servingSizeNote = fodmapFood.servingSizeNote,
                    category = fodmapFood.category,
                    confidence = 1.0f,
                    dataSource = "FODMAP Database",
                    recommendations = generateRecommendations(fodmapFood)
                )
            } else {
                // Try fuzzy matching for common variations
                val fuzzyMatch = findFuzzyMatch(foodName)
                if (fuzzyMatch != null) {
                    FODMAPAnalysisResult(
                        foodName = foodName,
                        overallLevel = fuzzyMatch.overallLevel,
                        oligosaccharides = fuzzyMatch.oligosaccharides,
                        disaccharides = fuzzyMatch.disaccharides,
                        monosaccharides = fuzzyMatch.monosaccharides,
                        polyols = fuzzyMatch.polyols,
                        servingSizeNote = "Estimated based on similar food: ${fuzzyMatch.name}",
                        category = fuzzyMatch.category,
                        confidence = 0.7f,
                        dataSource = "Fuzzy Match",
                        recommendations = generateRecommendations(fuzzyMatch)
                    )
                } else {
                    FODMAPAnalysisResult(
                        foodName = foodName,
                        overallLevel = FODMAPLevel.UNKNOWN,
                        oligosaccharides = FODMAPLevel.UNKNOWN,
                        disaccharides = FODMAPLevel.UNKNOWN,
                        monosaccharides = FODMAPLevel.UNKNOWN,
                        polyols = FODMAPLevel.UNKNOWN,
                        servingSizeNote = "FODMAP data not available for this food",
                        category = "Unknown",
                        confidence = 0.0f,
                        dataSource = "No Data",
                        recommendations = listOf("Consider consulting a dietitian for FODMAP guidance")
                    )
                }
            }
        } catch (e: Exception) {
            FODMAPAnalysisResult(
                foodName = foodName,
                overallLevel = FODMAPLevel.UNKNOWN,
                oligosaccharides = FODMAPLevel.UNKNOWN,
                disaccharides = FODMAPLevel.UNKNOWN,
                monosaccharides = FODMAPLevel.UNKNOWN,
                polyols = FODMAPLevel.UNKNOWN,
                servingSizeNote = "Error analyzing FODMAP content",
                category = "Error",
                confidence = 0.0f,
                dataSource = "Error",
                recommendations = listOf("Analysis failed: ${e.message}")
            )
        }
    }

    /**
     * Analyze ingredients list for combined FODMAP impact
     */
    suspend fun analyzeIngredients(ingredients: List<String>): CombinedFODMAPAnalysis {
        val individualAnalyses = mutableListOf<FODMAPAnalysisResult>()
        val problematicIngredients = mutableListOf<String>()
        val warnings = mutableListOf<String>()

        var highestOligo = FODMAPLevel.LOW
        var highestDisac = FODMAPLevel.LOW
        var highestMonosac = FODMAPLevel.LOW
        var highestPolyol = FODMAPLevel.LOW

        var totalConfidence = 0f
        var analyzedCount = 0

        for (ingredient in ingredients) {
            val analysis = analyzeFoodItem(ingredient)
            individualAnalyses.add(analysis)

            if (analysis.confidence > 0) {
                totalConfidence += analysis.confidence
                analyzedCount++

                // Track highest FODMAP levels across all ingredients
                highestOligo = maxLevel(highestOligo, analysis.oligosaccharides)
                highestDisac = maxLevel(highestDisac, analysis.disaccharides)
                highestMonosac = maxLevel(highestMonosac, analysis.monosaccharides)
                highestPolyol = maxLevel(highestPolyol, analysis.polyols)

                // Identify problematic ingredients
                if (analysis.overallLevel == FODMAPLevel.HIGH) {
                    problematicIngredients.add(ingredient)
                }

                // Add warnings for moderate FODMAP ingredients
                if (analysis.overallLevel == FODMAPLevel.MEDIUM) {
                    warnings.add("$ingredient contains moderate FODMAP levels - watch portion size")
                }
            }
        }

        // Calculate overall FODMAP level based on component levels
        val overallLevel = calculateOverallLevel(highestOligo, highestDisac, highestMonosac, highestPolyol)

        // Calculate combined confidence
        val combinedConfidence = if (analyzedCount > 0) totalConfidence / analyzedCount else 0f

        // Generate combined recommendations
        val recommendations = generateCombinedRecommendations(
            overallLevel, problematicIngredients, analyzedCount, ingredients.size
        )

        return CombinedFODMAPAnalysis(
            individualAnalyses = individualAnalyses,
            overallLevel = overallLevel,
            oligosaccharides = highestOligo,
            disaccharides = highestDisac,
            monosaccharides = highestMonosac,
            polyols = highestPolyol,
            problematicIngredients = problematicIngredients,
            warnings = warnings,
            confidence = combinedConfidence,
            recommendations = recommendations,
            summary = generateSummary(overallLevel, problematicIngredients, ingredients.size)
        )
    }

    /**
     * Analyze a food entry for FODMAP content
     */
    suspend fun analyzeFoodEntry(foodEntry: FoodEntry): FoodEntryFODMAPAnalysis {
        val primaryFoodAnalysis = analyzeFoodItem(foodEntry.foodName)

        val ingredientsAnalysis = if (foodEntry.ingredients.isNotEmpty()) {
            analyzeIngredients(foodEntry.ingredients)
        } else {
            null
        }

        // Combine primary food and ingredients analysis
        val combinedLevel = if (ingredientsAnalysis != null) {
            maxLevel(primaryFoodAnalysis.overallLevel, ingredientsAnalysis.overallLevel)
        } else {
            primaryFoodAnalysis.overallLevel
        }

        // Generate portion size warnings
        val portionWarnings = generatePortionWarnings(foodEntry, primaryFoodAnalysis)

        return FoodEntryFODMAPAnalysis(
            foodEntry = foodEntry,
            primaryFoodAnalysis = primaryFoodAnalysis,
            ingredientsAnalysis = ingredientsAnalysis,
            combinedLevel = combinedLevel,
            portionWarnings = portionWarnings,
            recommendations = generateFoodEntryRecommendations(
                combinedLevel, primaryFoodAnalysis, ingredientsAnalysis
            )
        )
    }

    /**
     * Generate FODMAP diet phase recommendations
     */
    fun generateDietPhaseRecommendations(
        currentPhase: FODMAPDietPhase,
        recentFoodEntries: List<FoodEntry>,
        symptoms: List<String>
    ): FODMAPDietRecommendations {
        return when (currentPhase) {
            FODMAPDietPhase.ELIMINATION -> {
                FODMAPDietRecommendations(
                    phase = currentPhase,
                    duration = "2-6 weeks",
                    description = "Strictly avoid high FODMAP foods",
                    allowedFoods = getEliminationPhaseAllowedFoods(),
                    forbiddenFoods = getHighFODMAPFoods(),
                    nextSteps = listOf(
                        "Continue until symptoms improve significantly",
                        "Then move to reintroduction phase"
                    ),
                    warnings = listOf(
                        "Consult dietitian for proper guidance",
                        "Ensure nutritional adequacy"
                    )
                )
            }
            FODMAPDietPhase.REINTRODUCTION -> {
                FODMAPDietRecommendations(
                    phase = currentPhase,
                    duration = "6-8 weeks",
                    description = "Systematically reintroduce FODMAP groups",
                    allowedFoods = getReintroductionPhaseGuidance(),
                    forbiddenFoods = emptyList(),
                    nextSteps = listOf(
                        "Test one FODMAP group at a time",
                        "Wait 3 days between tests",
                        "Note symptom responses"
                    ),
                    warnings = listOf(
                        "Don't test multiple groups simultaneously",
                        "Return to elimination if symptoms worsen"
                    )
                )
            }
            FODMAPDietPhase.PERSONALIZATION -> {
                FODMAPDietRecommendations(
                    phase = currentPhase,
                    duration = "Ongoing",
                    description = "Maintain personalized FODMAP tolerance",
                    allowedFoods = getPersonalizationPhaseGuidance(recentFoodEntries, symptoms),
                    forbiddenFoods = getPersonalTriggers(recentFoodEntries, symptoms),
                    nextSteps = listOf(
                        "Continue monitoring symptoms",
                        "Adjust intake based on tolerance",
                        "Re-test tolerance periodically"
                    ),
                    warnings = listOf(
                        "Individual tolerance may change over time",
                        "Regular dietitian check-ins recommended"
                    )
                )
            }
        }
    }

    private suspend fun findFuzzyMatch(foodName: String): FODMAPFood? {
        // Simple fuzzy matching by checking if food name contains known FODMAP food names
        val cleanedName = foodName.lowercase().trim()
        val allFoods = fodmapRepository.getAll()
        // In a real implementation, this would use the Flow properly
        // For now, return null as fuzzy matching needs proper implementation
        return null
    }

    private fun maxLevel(level1: FODMAPLevel, level2: FODMAPLevel): FODMAPLevel {
        return when {
            level1 == FODMAPLevel.HIGH || level2 == FODMAPLevel.HIGH -> FODMAPLevel.HIGH
            level1 == FODMAPLevel.MEDIUM || level2 == FODMAPLevel.MEDIUM -> FODMAPLevel.MEDIUM
            level1 == FODMAPLevel.LOW || level2 == FODMAPLevel.LOW -> FODMAPLevel.LOW
            else -> FODMAPLevel.UNKNOWN
        }
    }

    private fun calculateOverallLevel(
        oligo: FODMAPLevel, disac: FODMAPLevel,
        monosac: FODMAPLevel, polyol: FODMAPLevel
    ): FODMAPLevel {
        val levels = listOf(oligo, disac, monosac, polyol)
        return when {
            levels.any { it == FODMAPLevel.HIGH } -> FODMAPLevel.HIGH
            levels.any { it == FODMAPLevel.MEDIUM } -> FODMAPLevel.MEDIUM
            levels.all { it == FODMAPLevel.LOW } -> FODMAPLevel.LOW
            else -> FODMAPLevel.UNKNOWN
        }
    }

    private fun generateRecommendations(fodmapFood: FODMAPFood): List<String> {
        val recommendations = mutableListOf<String>()

        when (fodmapFood.overallLevel) {
            FODMAPLevel.HIGH -> {
                recommendations.add("Avoid during elimination phase")
                recommendations.add("May be tested in small amounts during reintroduction")
            }
            FODMAPLevel.MEDIUM -> {
                recommendations.add("Limit portion size")
                recommendations.add("Monitor symptoms when consuming")
            }
            FODMAPLevel.LOW -> {
                recommendations.add("Generally well tolerated")
                recommendations.add("Safe for most people with IBS")
            }
            FODMAPLevel.UNKNOWN -> {
                recommendations.add("FODMAP level unknown - proceed with caution")
            }
        }

        fodmapFood.servingSizeNote?.let { note ->
            recommendations.add("Note: $note")
        }

        return recommendations
    }

    private fun generateCombinedRecommendations(
        overallLevel: FODMAPLevel,
        problematicIngredients: List<String>,
        analyzedCount: Int,
        totalIngredients: Int
    ): List<String> {
        val recommendations = mutableListOf<String>()

        when (overallLevel) {
            FODMAPLevel.HIGH -> {
                recommendations.add("This food combination is HIGH in FODMAPs")
                recommendations.add("Avoid during elimination phase")
                if (problematicIngredients.isNotEmpty()) {
                    recommendations.add("Primary triggers: ${problematicIngredients.joinToString(", ")}")
                }
            }
            FODMAPLevel.MEDIUM -> {
                recommendations.add("This food combination has MODERATE FODMAP levels")
                recommendations.add("Consume in small portions and monitor symptoms")
            }
            FODMAPLevel.LOW -> {
                recommendations.add("This food combination is generally well tolerated")
            }
            FODMAPLevel.UNKNOWN -> {
                recommendations.add("FODMAP analysis incomplete")
            }
        }

        if (analyzedCount < totalIngredients) {
            val unknownCount = totalIngredients - analyzedCount
            recommendations.add("Warning: $unknownCount ingredients lack FODMAP data")
        }

        return recommendations
    }

    private fun generateSummary(
        overallLevel: FODMAPLevel,
        problematicIngredients: List<String>,
        totalIngredients: Int
    ): String {
        return buildString {
            append("Overall FODMAP level: ${overallLevel.name}")
            if (problematicIngredients.isNotEmpty()) {
                append(". High FODMAP ingredients: ${problematicIngredients.joinToString(", ")}")
            }
            append(". Analyzed $totalIngredients ingredient(s).")
        }
    }

    private fun generatePortionWarnings(
        foodEntry: FoodEntry,
        analysis: FODMAPAnalysisResult
    ): List<String> {
        val warnings = mutableListOf<String>()

        if (analysis.overallLevel == FODMAPLevel.MEDIUM && foodEntry.portions > 1.0) {
            warnings.add("Large portion size may increase FODMAP load")
        }

        if (analysis.servingSizeNote?.contains("serving size") == true) {
            warnings.add("FODMAP levels depend on serving size - check recommended portions")
        }

        return warnings
    }

    private fun generateFoodEntryRecommendations(
        combinedLevel: FODMAPLevel,
        primaryAnalysis: FODMAPAnalysisResult,
        ingredientsAnalysis: CombinedFODMAPAnalysis?
    ): List<String> {
        val recommendations = mutableListOf<String>()

        recommendations.addAll(primaryAnalysis.recommendations)

        ingredientsAnalysis?.let { ingredients ->
            if (ingredients.problematicIngredients.isNotEmpty()) {
                recommendations.add("Consider avoiding: ${ingredients.problematicIngredients.joinToString(", ")}")
            }
            recommendations.addAll(ingredients.warnings)
        }

        return recommendations.distinct()
    }

    // Helper methods for diet phase recommendations
    private fun getEliminationPhaseAllowedFoods(): List<String> {
        return listOf(
            "Bananas", "Oranges", "Strawberries", "Carrots", "Spinach",
            "Potatoes", "Rice", "Quinoa", "Chicken", "Fish", "Eggs"
        )
    }

    private fun getHighFODMAPFoods(): List<String> {
        return listOf(
            "Onions", "Garlic", "Wheat", "Apples", "Pears",
            "Beans", "Lentils", "Dairy with lactose"
        )
    }

    private fun getReintroductionPhaseGuidance(): List<String> {
        return listOf(
            "Week 1: Test oligosaccharides (e.g., 1/4 onion)",
            "Week 3: Test lactose (e.g., 1/2 cup milk)",
            "Week 5: Test fructose (e.g., 1/2 mango)",
            "Week 7: Test polyols (e.g., 1/4 avocado)"
        )
    }

    private fun getPersonalizationPhaseGuidance(
        recentFoodEntries: List<FoodEntry>,
        symptoms: List<String>
    ): List<String> {
        // This would analyze recent entries and symptoms to provide personalized guidance
        return listOf("Continue foods that don't trigger symptoms")
    }

    private fun getPersonalTriggers(
        recentFoodEntries: List<FoodEntry>,
        symptoms: List<String>
    ): List<String> {
        // This would identify personal triggers based on food entries and symptoms
        return emptyList()
    }
}

data class FODMAPAnalysisResult(
    val foodName: String,
    val overallLevel: FODMAPLevel,
    val oligosaccharides: FODMAPLevel,
    val disaccharides: FODMAPLevel,
    val monosaccharides: FODMAPLevel,
    val polyols: FODMAPLevel,
    val servingSizeNote: String?,
    val category: String?,
    val confidence: Float, // 0-1
    val dataSource: String,
    val recommendations: List<String>
)

data class CombinedFODMAPAnalysis(
    val individualAnalyses: List<FODMAPAnalysisResult>,
    val overallLevel: FODMAPLevel,
    val oligosaccharides: FODMAPLevel,
    val disaccharides: FODMAPLevel,
    val monosaccharides: FODMAPLevel,
    val polyols: FODMAPLevel,
    val problematicIngredients: List<String>,
    val warnings: List<String>,
    val confidence: Float,
    val recommendations: List<String>,
    val summary: String
)

data class FoodEntryFODMAPAnalysis(
    val foodEntry: FoodEntry,
    val primaryFoodAnalysis: FODMAPAnalysisResult,
    val ingredientsAnalysis: CombinedFODMAPAnalysis?,
    val combinedLevel: FODMAPLevel,
    val portionWarnings: List<String>,
    val recommendations: List<String>
)

data class FODMAPDietRecommendations(
    val phase: FODMAPDietPhase,
    val duration: String,
    val description: String,
    val allowedFoods: List<String>,
    val forbiddenFoods: List<String>,
    val nextSteps: List<String>,
    val warnings: List<String>
)

enum class FODMAPDietPhase {
    ELIMINATION, REINTRODUCTION, PERSONALIZATION
}