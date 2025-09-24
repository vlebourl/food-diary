package com.fooddiary.domain.usecase

import com.fooddiary.data.models.FODMAPLevel
import com.fooddiary.data.repository.FODMAPRepository
import javax.inject.Inject

class AnalyzeFODMAPContentUseCase @Inject constructor(
    private val fodmapRepository: FODMAPRepository
) {
    suspend operator fun invoke(
        foodName: String? = null,
        ingredients: List<String> = emptyList()
    ): Result<FODMAPAnalysis> {
        return try {
            // If a specific food name is provided, look it up directly
            if (!foodName.isNullOrBlank()) {
                val fodmapFood = fodmapRepository.getByName(foodName)
                if (fodmapFood != null) {
                    return Result.success(
                        FODMAPAnalysis(
                            overallLevel = fodmapFood.overallLevel,
                            oligosaccharides = fodmapFood.oligosaccharides,
                            disaccharides = fodmapFood.disaccharides,
                            monosaccharides = fodmapFood.monosaccharides,
                            polyols = fodmapFood.polyols,
                            problematicIngredients = if (fodmapFood.overallLevel == FODMAPLevel.HIGH) {
                                listOf(foodName)
                            } else {
                                emptyList()
                            },
                            servingSizeNote = fodmapFood.servingSizeNote,
                            confidence = 1.0f // High confidence for direct match
                        )
                    )
                }
            }

            // Analyze ingredients if provided
            if (ingredients.isNotEmpty()) {
                val problematicIngredients = mutableListOf<String>()
                var oligoLevel = FODMAPLevel.LOW
                var disLevel = FODMAPLevel.LOW
                var monoLevel = FODMAPLevel.LOW
                var polyolLevel = FODMAPLevel.LOW
                var matchedIngredients = 0

                for (ingredient in ingredients) {
                    val fodmapFood = fodmapRepository.getByName(ingredient)
                    if (fodmapFood != null) {
                        matchedIngredients++

                        // Track problematic ingredients
                        if (fodmapFood.overallLevel == FODMAPLevel.HIGH) {
                            problematicIngredients.add(ingredient)
                        }

                        // Update component levels (take highest)
                        oligoLevel = maxOf(oligoLevel, fodmapFood.oligosaccharides)
                        disLevel = maxOf(disLevel, fodmapFood.disaccharides)
                        monoLevel = maxOf(monoLevel, fodmapFood.monosaccharides)
                        polyolLevel = maxOf(polyolLevel, fodmapFood.polyols)
                    }
                }

                // Calculate overall level based on components
                val overallLevel = when {
                    oligoLevel == FODMAPLevel.HIGH || disLevel == FODMAPLevel.HIGH ||
                    monoLevel == FODMAPLevel.HIGH || polyolLevel == FODMAPLevel.HIGH -> FODMAPLevel.HIGH
                    oligoLevel == FODMAPLevel.MEDIUM || disLevel == FODMAPLevel.MEDIUM ||
                    monoLevel == FODMAPLevel.MEDIUM || polyolLevel == FODMAPLevel.MEDIUM -> FODMAPLevel.MEDIUM
                    else -> FODMAPLevel.LOW
                }

                // Calculate confidence based on how many ingredients were matched
                val confidence = if (ingredients.isNotEmpty()) {
                    matchedIngredients.toFloat() / ingredients.size
                } else {
                    0f
                }

                return Result.success(
                    FODMAPAnalysis(
                        overallLevel = overallLevel,
                        oligosaccharides = oligoLevel,
                        disaccharides = disLevel,
                        monosaccharides = monoLevel,
                        polyols = polyolLevel,
                        problematicIngredients = problematicIngredients,
                        servingSizeNote = if (problematicIngredients.isNotEmpty()) {
                            "FODMAP levels may vary with serving size"
                        } else null,
                        confidence = confidence
                    )
                )
            }

            // No food name or ingredients provided
            Result.success(
                FODMAPAnalysis(
                    overallLevel = FODMAPLevel.LOW,
                    oligosaccharides = FODMAPLevel.LOW,
                    disaccharides = FODMAPLevel.LOW,
                    monosaccharides = FODMAPLevel.LOW,
                    polyols = FODMAPLevel.LOW,
                    problematicIngredients = emptyList(),
                    servingSizeNote = "Unable to analyze - no food information provided",
                    confidence = 0f
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun maxOf(level1: FODMAPLevel, level2: FODMAPLevel): FODMAPLevel {
        return when {
            level1 == FODMAPLevel.HIGH || level2 == FODMAPLevel.HIGH -> FODMAPLevel.HIGH
            level1 == FODMAPLevel.MEDIUM || level2 == FODMAPLevel.MEDIUM -> FODMAPLevel.MEDIUM
            else -> FODMAPLevel.LOW
        }
    }
}

data class FODMAPAnalysis(
    val overallLevel: FODMAPLevel,
    val oligosaccharides: FODMAPLevel,
    val disaccharides: FODMAPLevel,
    val monosaccharides: FODMAPLevel,
    val polyols: FODMAPLevel,
    val problematicIngredients: List<String>,
    val servingSizeNote: String?,
    val confidence: Float // 0-1 confidence in the analysis
)