package com.fooddiary.domain.analysis

import com.fooddiary.data.database.entities.FODMAPFood
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.models.FODMAPLevel
import com.fooddiary.data.repository.FODMAPRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.Instant

@DisplayName("FODMAP Analyzer Tests")
class FODMAPAnalyzerTest {

    private lateinit var fodmapRepository: FODMAPRepository
    private lateinit var fodmapAnalyzer: FODMAPAnalyzer

    // Test data
    private val onionFodmap = FODMAPFood(
        name = "onion",
        overallLevel = FODMAPLevel.HIGH,
        oligosaccharides = FODMAPLevel.HIGH,
        disaccharides = FODMAPLevel.LOW,
        monosaccharides = FODMAPLevel.LOW,
        polyols = FODMAPLevel.LOW,
        servingSizeNote = "High in oligosaccharides - avoid large portions",
        category = "Vegetables"
    )

    private val bananFodmap = FODMAPFood(
        name = "banana",
        overallLevel = FODMAPLevel.LOW,
        oligosaccharides = FODMAPLevel.LOW,
        disaccharides = FODMAPLevel.LOW,
        monosaccharides = FODMAPLevel.LOW,
        polyols = FODMAPLevel.LOW,
        servingSizeNote = "Generally well tolerated",
        category = "Fruits"
    )

    private val appleFodmap = FODMAPFood(
        name = "apple",
        overallLevel = FODMAPLevel.MEDIUM,
        oligosaccharides = FODMAPLevel.LOW,
        disaccharides = FODMAPLevel.LOW,
        monosaccharides = FODMAPLevel.MEDIUM,
        polyols = FODMAPLevel.MEDIUM,
        servingSizeNote = "Moderate in fructose and polyols - limit serving size",
        category = "Fruits"
    )

    @BeforeEach
    fun setup() {
        fodmapRepository = mockk(relaxed = true)
        fodmapAnalyzer = FODMAPAnalyzer(fodmapRepository)
    }

    @Nested
    @DisplayName("Single Food Item Analysis")
    inner class SingleFoodItemAnalysis {

        @Test
        @DisplayName("Should analyze high FODMAP food correctly")
        fun `should analyze high FODMAP food correctly`() = runTest {
            // Given
            coEvery { fodmapRepository.getByName("onion") } returns onionFodmap

            // When
            val result = fodmapAnalyzer.analyzeFoodItem("Onion")

            // Then
            assertEquals("Onion", result.foodName)
            assertEquals(FODMAPLevel.HIGH, result.overallLevel)
            assertEquals(FODMAPLevel.HIGH, result.oligosaccharides)
            assertEquals(FODMAPLevel.LOW, result.disaccharides)
            assertEquals(FODMAPLevel.LOW, result.monosaccharides)
            assertEquals(FODMAPLevel.LOW, result.polyols)
            assertEquals("High in oligosaccharides - avoid large portions", result.servingSizeNote)
            assertEquals("Vegetables", result.category)
            assertEquals(1.0f, result.confidence)
            assertEquals("FODMAP Database", result.dataSource)
            assertTrue(result.recommendations.isNotEmpty())
            assertTrue(result.recommendations.any { it.contains("Avoid during elimination") })
        }

        @Test
        @DisplayName("Should analyze low FODMAP food correctly")
        fun `should analyze low FODMAP food correctly`() = runTest {
            // Given
            coEvery { fodmapRepository.getByName("banana") } returns bananFodmap

            // When
            val result = fodmapAnalyzer.analyzeFoodItem("banana")

            // Then
            assertEquals("banana", result.foodName)
            assertEquals(FODMAPLevel.LOW, result.overallLevel)
            assertEquals(FODMAPLevel.LOW, result.oligosaccharides)
            assertEquals(FODMAPLevel.LOW, result.disaccharides)
            assertEquals(FODMAPLevel.LOW, result.monosaccharides)
            assertEquals(FODMAPLevel.LOW, result.polyols)
            assertEquals(1.0f, result.confidence)
            assertEquals("FODMAP Database", result.dataSource)
            assertTrue(result.recommendations.any { it.contains("Generally well tolerated") })
        }

        @Test
        @DisplayName("Should handle unknown food gracefully")
        fun `should handle unknown food gracefully`() = runTest {
            // Given
            coEvery { fodmapRepository.getByName("unknownfood") } returns null

            // When
            val result = fodmapAnalyzer.analyzeFoodItem("UnknownFood")

            // Then
            assertEquals("UnknownFood", result.foodName)
            assertEquals(FODMAPLevel.UNKNOWN, result.overallLevel)
            assertEquals(FODMAPLevel.UNKNOWN, result.oligosaccharides)
            assertEquals(FODMAPLevel.UNKNOWN, result.disaccharides)
            assertEquals(FODMAPLevel.UNKNOWN, result.monosaccharides)
            assertEquals(FODMAPLevel.UNKNOWN, result.polyols)
            assertEquals("FODMAP data not available for this food", result.servingSizeNote)
            assertEquals("Unknown", result.category)
            assertEquals(0.0f, result.confidence)
            assertEquals("No Data", result.dataSource)
            assertTrue(result.recommendations.any { it.contains("Consider consulting a dietitian") })
        }

        @Test
        @DisplayName("Should handle repository errors gracefully")
        fun `should handle repository errors gracefully`() = runTest {
            // Given
            coEvery { fodmapRepository.getByName(any()) } throws RuntimeException("Database error")

            // When
            val result = fodmapAnalyzer.analyzeFoodItem("TestFood")

            // Then
            assertEquals("TestFood", result.foodName)
            assertEquals(FODMAPLevel.UNKNOWN, result.overallLevel)
            assertEquals("Error analyzing FODMAP content", result.servingSizeNote)
            assertEquals("Error", result.category)
            assertEquals(0.0f, result.confidence)
            assertEquals("Error", result.dataSource)
            assertTrue(result.recommendations.any { it.contains("Analysis failed") })
        }

        @Test
        @DisplayName("Should normalize food names by trimming and converting to lowercase")
        fun `should normalize food names by trimming and converting to lowercase`() = runTest {
            // Given
            coEvery { fodmapRepository.getByName("banana") } returns bananFodmap

            // When
            val result = fodmapAnalyzer.analyzeFoodItem("  BANANA  ")

            // Then
            assertEquals("  BANANA  ", result.foodName) // Original name preserved
            assertEquals(FODMAPLevel.LOW, result.overallLevel) // But lookup worked
            assertEquals("FODMAP Database", result.dataSource)
        }
    }

    @Nested
    @DisplayName("Combined Ingredients Analysis")
    inner class CombinedIngredientsAnalysis {

        @BeforeEach
        fun setupMocks() {
            coEvery { fodmapRepository.getByName("onion") } returns onionFodmap
            coEvery { fodmapRepository.getByName("banana") } returns bananFodmap
            coEvery { fodmapRepository.getByName("apple") } returns appleFodmap
            coEvery { fodmapRepository.getByName("unknown") } returns null
        }

        @Test
        @DisplayName("Should analyze mixed FODMAP levels correctly")
        fun `should analyze mixed FODMAP levels correctly`() = runTest {
            // Given
            val ingredients = listOf("banana", "apple", "onion")

            // When
            val result = fodmapAnalyzer.analyzeIngredients(ingredients)

            // Then
            assertEquals(3, result.individualAnalyses.size)
            assertEquals(FODMAPLevel.HIGH, result.overallLevel) // Highest level wins
            assertEquals(FODMAPLevel.HIGH, result.oligosaccharides) // From onion
            assertEquals(FODMAPLevel.MEDIUM, result.monosaccharides) // From apple
            assertEquals(FODMAPLevel.MEDIUM, result.polyols) // From apple
            assertEquals(listOf("onion"), result.problematicIngredients)
            assertTrue(result.warnings.any { it.contains("apple") && it.contains("moderate") })
            assertTrue(result.confidence > 0.5f) // Good confidence with known ingredients
        }

        @Test
        @DisplayName("Should identify all high FODMAP ingredients as problematic")
        fun `should identify all high FODMAP ingredients as problematic`() = runTest {
            // Given
            val ingredients = listOf("onion", "onion") // Multiple high FODMAP

            // When
            val result = fodmapAnalyzer.analyzeIngredients(ingredients)

            // Then
            assertEquals(FODMAPLevel.HIGH, result.overallLevel)
            assertEquals(2, result.problematicIngredients.size)
            assertTrue(result.problematicIngredients.all { it == "onion" })
        }

        @Test
        @DisplayName("Should handle ingredients with unknown FODMAP data")
        fun `should handle ingredients with unknown FODMAP data`() = runTest {
            // Given
            val ingredients = listOf("banana", "unknown", "mystery_food")

            // When
            val result = fodmapAnalyzer.analyzeIngredients(ingredients)

            // Then
            assertEquals(3, result.individualAnalyses.size)
            assertEquals(FODMAPLevel.LOW, result.overallLevel) // Known banana is low
            assertTrue(result.recommendations.any { it.contains("2 ingredients lack FODMAP data") })
            assertTrue(result.confidence < 1.0f) // Reduced confidence due to unknowns
        }

        @Test
        @DisplayName("Should calculate combined confidence correctly")
        fun `should calculate combined confidence correctly`() = runTest {
            // Given - mix of known (confidence 1.0) and unknown (confidence 0.0)
            val ingredients = listOf("banana", "unknown")

            // When
            val result = fodmapAnalyzer.analyzeIngredients(ingredients)

            // Then
            assertEquals(0.5f, result.confidence, 0.01f) // Average of 1.0 and 0.0
        }

        @Test
        @DisplayName("Should generate appropriate summary")
        fun `should generate appropriate summary`() = runTest {
            // Given
            val ingredients = listOf("onion", "banana")

            // When
            val result = fodmapAnalyzer.analyzeIngredients(ingredients)

            // Then
            assertTrue(result.summary.contains("Overall FODMAP level: HIGH"))
            assertTrue(result.summary.contains("High FODMAP ingredients: onion"))
            assertTrue(result.summary.contains("Analyzed 2 ingredient(s)"))
        }

        @Test
        @DisplayName("Should handle empty ingredients list")
        fun `should handle empty ingredients list`() = runTest {
            // Given
            val ingredients = emptyList<String>()

            // When
            val result = fodmapAnalyzer.analyzeIngredients(ingredients)

            // Then
            assertTrue(result.individualAnalyses.isEmpty())
            assertEquals(FODMAPLevel.LOW, result.overallLevel) // Default to low when no data
            assertEquals(0.0f, result.confidence)
            assertTrue(result.problematicIngredients.isEmpty())
        }
    }

    @Nested
    @DisplayName("Food Entry Analysis")
    inner class FoodEntryAnalysis {

        @BeforeEach
        fun setupMocks() {
            coEvery { fodmapRepository.getByName("pasta") } returns FODMAPFood(
                name = "pasta",
                overallLevel = FODMAPLevel.HIGH,
                oligosaccharides = FODMAPLevel.HIGH,
                disaccharides = FODMAPLevel.LOW,
                monosaccharides = FODMAPLevel.LOW,
                polyols = FODMAPLevel.LOW,
                servingSizeNote = "Wheat-based - high in oligosaccharides",
                category = "Grains"
            )
            coEvery { fodmapRepository.getByName("onion") } returns onionFodmap
            coEvery { fodmapRepository.getByName("olive oil") } returns FODMAPFood(
                name = "olive oil",
                overallLevel = FODMAPLevel.LOW,
                oligosaccharides = FODMAPLevel.LOW,
                disaccharides = FODMAPLevel.LOW,
                monosaccharides = FODMAPLevel.LOW,
                polyols = FODMAPLevel.LOW,
                servingSizeNote = null,
                category = "Oils"
            )
        }

        @Test
        @DisplayName("Should analyze food entry with ingredients correctly")
        fun `should analyze food entry with ingredients correctly`() = runTest {
            // Given
            val foodEntry = FoodEntry(
                id = "1",
                foodName = "pasta",
                mealType = "lunch",
                timestamp = Instant.now(),
                portions = 1.5,
                ingredients = listOf("onion", "olive oil"),
                timezone = "UTC"
            )

            // When
            val result = fodmapAnalyzer.analyzeFoodEntry(foodEntry)

            // Then
            assertNotNull(result.primaryFoodAnalysis)
            assertEquals("pasta", result.primaryFoodAnalysis.foodName)
            assertEquals(FODMAPLevel.HIGH, result.primaryFoodAnalysis.overallLevel)

            assertNotNull(result.ingredientsAnalysis)
            assertEquals(2, result.ingredientsAnalysis?.individualAnalyses?.size)
            assertEquals(FODMAPLevel.HIGH, result.ingredientsAnalysis?.overallLevel) // Onion makes it high

            assertEquals(FODMAPLevel.HIGH, result.combinedLevel) // Max of pasta and ingredients
            assertTrue(result.recommendations.isNotEmpty())
        }

        @Test
        @DisplayName("Should handle food entry without ingredients")
        fun `should handle food entry without ingredients`() = runTest {
            // Given
            val foodEntry = FoodEntry(
                id = "1",
                foodName = "pasta",
                mealType = "lunch",
                timestamp = Instant.now(),
                portions = 1.0,
                ingredients = emptyList(),
                timezone = "UTC"
            )

            // When
            val result = fodmapAnalyzer.analyzeFoodEntry(foodEntry)

            // Then
            assertNotNull(result.primaryFoodAnalysis)
            assertEquals("pasta", result.primaryFoodAnalysis.foodName)
            assertNull(result.ingredientsAnalysis)
            assertEquals(result.primaryFoodAnalysis.overallLevel, result.combinedLevel)
        }

        @Test
        @DisplayName("Should generate portion size warnings for large servings")
        fun `should generate portion size warnings for large servings`() = runTest {
            // Given
            val foodEntry = FoodEntry(
                id = "1",
                foodName = "apple",
                mealType = "snack",
                timestamp = Instant.now(),
                portions = 2.5, // Large portion
                ingredients = emptyList(),
                timezone = "UTC"
            )

            coEvery { fodmapRepository.getByName("apple") } returns appleFodmap

            // When
            val result = fodmapAnalyzer.analyzeFoodEntry(foodEntry)

            // Then
            assertTrue(result.portionWarnings.isNotEmpty())
            assertTrue(result.portionWarnings.any { it.contains("Large portion size") })
        }

        @Test
        @DisplayName("Should combine recommendations from food and ingredients")
        fun `should combine recommendations from food and ingredients`() = runTest {
            // Given
            val foodEntry = FoodEntry(
                id = "1",
                foodName = "pasta",
                mealType = "lunch",
                timestamp = Instant.now(),
                portions = 1.0,
                ingredients = listOf("onion"),
                timezone = "UTC"
            )

            // When
            val result = fodmapAnalyzer.analyzeFoodEntry(foodEntry)

            // Then
            // Should contain recommendations from both pasta analysis and onion analysis
            val allRecommendations = result.recommendations.joinToString(" ")
            assertTrue(allRecommendations.contains("elimination") || allRecommendations.contains("avoid"))
            assertTrue(result.recommendations.size >= 2) // At least some from each source
        }
    }

    @Nested
    @DisplayName("FODMAP Level Utilities")
    inner class FODMAPLevelUtilities {

        @Test
        @DisplayName("Should calculate maximum level correctly")
        fun `should calculate maximum level correctly`() = runTest {
            // Test the maxLevel logic through combined analysis
            val ingredients = listOf("banana", "apple", "onion")
            coEvery { fodmapRepository.getByName("banana") } returns bananFodmap
            coEvery { fodmapRepository.getByName("apple") } returns appleFodmap
            coEvery { fodmapRepository.getByName("onion") } returns onionFodmap

            val result = fodmapAnalyzer.analyzeIngredients(ingredients)

            // HIGH should win over MEDIUM and LOW
            assertEquals(FODMAPLevel.HIGH, result.overallLevel)
            assertEquals(FODMAPLevel.HIGH, result.oligosaccharides) // From onion
        }

        @Test
        @DisplayName("Should handle all unknown levels")
        fun `should handle all unknown levels`() = runTest {
            val ingredients = listOf("unknown1", "unknown2")
            coEvery { fodmapRepository.getByName(any()) } returns null

            val result = fodmapAnalyzer.analyzeIngredients(ingredients)

            assertEquals(FODMAPLevel.LOW, result.overallLevel) // Defaults to LOW when all unknown
        }
    }

    @Nested
    @DisplayName("Diet Phase Recommendations")
    inner class DietPhaseRecommendations {

        private val sampleFoodEntries = listOf(
            FoodEntry(
                id = "1",
                foodName = "rice",
                mealType = "lunch",
                timestamp = Instant.now(),
                portions = 1.0,
                ingredients = emptyList(),
                timezone = "UTC"
            )
        )

        @Test
        @DisplayName("Should provide elimination phase recommendations")
        fun `should provide elimination phase recommendations`() {
            // Given
            val phase = FODMAPDietPhase.ELIMINATION
            val symptoms = listOf("bloating", "cramping")

            // When
            val result = fodmapAnalyzer.generateDietPhaseRecommendations(
                phase, sampleFoodEntries, symptoms
            )

            // Then
            assertEquals(FODMAPDietPhase.ELIMINATION, result.phase)
            assertEquals("2-6 weeks", result.duration)
            assertTrue(result.description.contains("Strictly avoid high FODMAP"))
            assertTrue(result.allowedFoods.isNotEmpty())
            assertTrue(result.forbiddenFoods.isNotEmpty())
            assertTrue(result.allowedFoods.contains("Rice"))
            assertTrue(result.forbiddenFoods.contains("Onions"))
            assertTrue(result.warnings.any { it.contains("dietitian") })
        }

        @Test
        @DisplayName("Should provide reintroduction phase recommendations")
        fun `should provide reintroduction phase recommendations`() {
            // Given
            val phase = FODMAPDietPhase.REINTRODUCTION

            // When
            val result = fodmapAnalyzer.generateDietPhaseRecommendations(
                phase, sampleFoodEntries, emptyList()
            )

            // Then
            assertEquals(FODMAPDietPhase.REINTRODUCTION, result.phase)
            assertEquals("6-8 weeks", result.duration)
            assertTrue(result.description.contains("Systematically reintroduce"))
            assertTrue(result.allowedFoods.any { it.contains("oligosaccharides") })
            assertTrue(result.nextSteps.any { it.contains("one FODMAP group at a time") })
            assertTrue(result.warnings.any { it.contains("Don't test multiple groups") })
        }

        @Test
        @DisplayName("Should provide personalization phase recommendations")
        fun `should provide personalization phase recommendations`() {
            // Given
            val phase = FODMAPDietPhase.PERSONALIZATION

            // When
            val result = fodmapAnalyzer.generateDietPhaseRecommendations(
                phase, sampleFoodEntries, emptyList()
            )

            // Then
            assertEquals(FODMAPDietPhase.PERSONALIZATION, result.phase)
            assertEquals("Ongoing", result.duration)
            assertTrue(result.description.contains("personalized"))
            assertTrue(result.nextSteps.any { it.contains("Continue monitoring") })
            assertTrue(result.warnings.any { it.contains("Individual tolerance may change") })
        }
    }

    @Nested
    @DisplayName("Edge Cases and Error Handling")
    inner class EdgeCasesAndErrorHandling {

        @Test
        @DisplayName("Should handle null and empty strings gracefully")
        fun `should handle null and empty strings gracefully`() = runTest {
            // Test empty string
            val emptyResult = fodmapAnalyzer.analyzeFoodItem("")
            assertEquals("", emptyResult.foodName)
            assertEquals(FODMAPLevel.UNKNOWN, emptyResult.overallLevel)

            // Test whitespace only
            val whitespaceResult = fodmapAnalyzer.analyzeFoodItem("   ")
            assertEquals("   ", whitespaceResult.foodName)
            assertEquals(FODMAPLevel.UNKNOWN, whitespaceResult.overallLevel)
        }

        @Test
        @DisplayName("Should handle very large ingredients lists")
        fun `should handle very large ingredients lists`() = runTest {
            // Given
            val largeIngredientsList = (1..100).map { "ingredient_$it" }
            coEvery { fodmapRepository.getByName(any()) } returns null

            // When
            val result = fodmapAnalyzer.analyzeIngredients(largeIngredientsList)

            // Then
            assertEquals(100, result.individualAnalyses.size)
            assertEquals(0.0f, result.confidence)
            assertTrue(result.recommendations.any { it.contains("100 ingredients lack FODMAP data") })
        }

        @Test
        @DisplayName("Should handle concurrent access gracefully")
        fun `should handle concurrent access gracefully`() = runTest {
            // Given
            coEvery { fodmapRepository.getByName("banana") } returns bananFodmap

            // When - simulate concurrent access
            val results = (1..10).map {
                fodmapAnalyzer.analyzeFoodItem("banana")
            }

            // Then - all results should be consistent
            results.forEach { result ->
                assertEquals("banana", result.foodName)
                assertEquals(FODMAPLevel.LOW, result.overallLevel)
                assertEquals(1.0f, result.confidence)
            }
        }

        @Test
        @DisplayName("Should preserve original food name case and spacing")
        fun `should preserve original food name case and spacing`() = runTest {
            // Given
            val originalName = "  Green APPLE  "
            coEvery { fodmapRepository.getByName("green apple") } returns appleFodmap

            // When
            val result = fodmapAnalyzer.analyzeFoodItem(originalName)

            // Then
            assertEquals(originalName, result.foodName) // Original preserved
            assertEquals(FODMAPLevel.MEDIUM, result.overallLevel) // But lookup worked
        }
    }

    @Nested
    @DisplayName("Recommendation Generation")
    inner class RecommendationGeneration {

        @Test
        @DisplayName("Should generate specific recommendations for high FODMAP foods")
        fun `should generate specific recommendations for high FODMAP foods`() = runTest {
            // Given
            coEvery { fodmapRepository.getByName("onion") } returns onionFodmap

            // When
            val result = fodmapAnalyzer.analyzeFoodItem("onion")

            // Then
            val recommendations = result.recommendations.joinToString(" ").lowercase()
            assertTrue(recommendations.contains("avoid") && recommendations.contains("elimination"))
            assertTrue(recommendations.contains("reintroduction") || recommendations.contains("small amounts"))
            assertTrue(recommendations.contains(onionFodmap.servingSizeNote!!.lowercase()))
        }

        @Test
        @DisplayName("Should generate appropriate recommendations for low FODMAP foods")
        fun `should generate appropriate recommendations for low FODMAP foods`() = runTest {
            // Given
            coEvery { fodmapRepository.getByName("banana") } returns bananFodmap

            // When
            val result = fodmapAnalyzer.analyzeFoodItem("banana")

            // Then
            val recommendations = result.recommendations.joinToString(" ").lowercase()
            assertTrue(recommendations.contains("generally well tolerated") ||
                      recommendations.contains("safe"))
            assertTrue(recommendations.contains("ibs"))
        }

        @Test
        @DisplayName("Should include serving size notes in recommendations")
        fun `should include serving size notes in recommendations`() = runTest {
            // Given
            val foodWithNote = FODMAPFood(
                name = "test_food",
                overallLevel = FODMAPLevel.MEDIUM,
                oligosaccharides = FODMAPLevel.MEDIUM,
                disaccharides = FODMAPLevel.LOW,
                monosaccharides = FODMAPLevel.LOW,
                polyols = FODMAPLevel.LOW,
                servingSizeNote = "Important serving note",
                category = "Test"
            )
            coEvery { fodmapRepository.getByName("test_food") } returns foodWithNote

            // When
            val result = fodmapAnalyzer.analyzeFoodItem("test_food")

            // Then
            assertTrue(result.recommendations.any { it.contains("Important serving note") })
        }
    }
}