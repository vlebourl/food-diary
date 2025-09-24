package com.fooddiary.domain.analysis

import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.models.SymptomType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.junit.jupiter.params.provider.EnumSource
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.test.*

@DisplayName("CorrelationEngine Tests")
class CorrelationEngineTest {

    private lateinit var correlationEngine: CorrelationEngine

    @BeforeEach
    fun setUp() {
        correlationEngine = CorrelationEngine()
    }

    @Nested
    @DisplayName("Pearson Correlation Tests")
    inner class PearsonCorrelationTests {

        @Test
        @DisplayName("Should return null for insufficient food entries")
        fun testInsufficientFoodEntries() {
            val foodEntries = createFoodEntries("Dairy", 3) // Less than minimum 5
            val symptomEvents = createSymptomEvents(SymptomType.ABDOMINAL_PAIN, 5)

            val result = correlationEngine.calculatePearsonCorrelation(
                foodEntries, symptomEvents, "Dairy", SymptomType.ABDOMINAL_PAIN
            )

            assertNull(result)
        }

        @Test
        @DisplayName("Should return null for no symptom events")
        fun testNoSymptomEvents() {
            val foodEntries = createFoodEntries("Dairy", 10)
            val symptomEvents = emptyList<SymptomEvent>()

            val result = correlationEngine.calculatePearsonCorrelation(
                foodEntries, symptomEvents, "Dairy", SymptomType.ABDOMINAL_PAIN
            )

            assertNull(result)
        }

        @Test
        @DisplayName("Should calculate positive correlation for strong food-symptom relationship")
        fun testPositiveCorrelation() {
            // Create scenario where eating dairy always leads to symptoms within 2 hours
            val baseTime = Instant.now()
            val foodEntries = mutableListOf<FoodEntry>()
            val symptomEvents = mutableListOf<SymptomEvent>()

            repeat(10) { i ->
                val foodTime = baseTime.plus(i.toLong(), ChronoUnit.DAYS)
                foodEntries.add(createFoodEntry("Dairy", foodTime))

                // Add symptom 1 hour after eating dairy
                val symptomTime = foodTime.plus(1, ChronoUnit.HOURS)
                symptomEvents.add(createSymptomEvent(SymptomType.ABDOMINAL_PAIN, symptomTime, 6))
            }

            val result = correlationEngine.calculatePearsonCorrelation(
                foodEntries, symptomEvents, "Dairy", SymptomType.ABDOMINAL_PAIN
            )

            assertNotNull(result)
            assertTrue(result.correlation > 0.5, "Expected positive correlation, got ${result.correlation}")
            assertTrue(result.isStatisticallySignificant)
            assertEquals(CorrelationStrength.STRONG, result.strength)
        }

        @Test
        @DisplayName("Should calculate weak correlation for random relationship")
        fun testWeakCorrelation() {
            // Create scenario with random food-symptom timing
            val foodEntries = createFoodEntries("RandomFood", 20)
            val symptomEvents = createRandomSymptomEvents(SymptomType.BLOATING, 15)

            val result = correlationEngine.calculatePearsonCorrelation(
                foodEntries, symptomEvents, "RandomFood", SymptomType.BLOATING
            )

            assertNotNull(result)
            assertTrue(kotlin.math.abs(result.correlation) < 0.3,
                "Expected weak correlation, got ${result.correlation}")
            assertFalse(result.isStatisticallySignificant)
        }

        @ParameterizedTest
        @ValueSource(ints = [1, 6, 12, 24, 48])
        @DisplayName("Should respect time window parameter")
        fun testTimeWindowParameter(timeWindowHours: Int) {
            val baseTime = Instant.now()
            val foodEntries = listOf(createFoodEntry("TestFood", baseTime))

            // Create symptom just outside the time window
            val symptomTime = baseTime.plus((timeWindowHours + 1).toLong(), ChronoUnit.HOURS)
            val symptomEvents = listOf(createSymptomEvent(SymptomType.NAUSEA, symptomTime, 5))

            val result = correlationEngine.calculatePearsonCorrelation(
                foodEntries + createFoodEntries("TestFood", 10), // Add more entries for minimum
                symptomEvents,
                "TestFood",
                SymptomType.NAUSEA,
                timeWindowHours
            )

            // Symptom outside window should not be correlated
            assertNotNull(result)
            assertTrue(kotlin.math.abs(result.correlation) < 0.3)
        }

        @Test
        @DisplayName("Should calculate confidence intervals correctly")
        fun testConfidenceIntervals() {
            val foodEntries = createFoodEntries("TestFood", 15)
            val symptomEvents = createSymptomEvents(SymptomType.CRAMPING, 10)

            val result = correlationEngine.calculatePearsonCorrelation(
                foodEntries, symptomEvents, "TestFood", SymptomType.CRAMPING
            )

            assertNotNull(result)
            val (lower, upper) = result.confidenceInterval95
            assertTrue(lower <= upper, "Lower bound should be <= upper bound")
            assertTrue(lower >= -1.0 && upper <= 1.0, "Confidence interval should be within [-1, 1]")
            assertTrue(result.correlation >= lower && result.correlation <= upper,
                "Correlation should be within confidence interval")
        }
    }

    @Nested
    @DisplayName("Chi-Square Test Tests")
    inner class ChiSquareTests {

        @Test
        @DisplayName("Should return null for insufficient data")
        fun testInsufficientData() {
            val foodEntries = createFoodEntries("TestFood", 2)
            val symptomEvents = createSymptomEvents(SymptomType.GAS, 1)

            val result = correlationEngine.calculateChiSquare(
                foodEntries, symptomEvents, "TestFood", SymptomType.GAS
            )

            assertNull(result, "Should return null for insufficient data")
        }

        @Test
        @DisplayName("Should calculate chi-square statistic correctly")
        fun testChiSquareCalculation() {
            // Create controlled scenario
            val baseTime = Instant.now()
            val foodEntries = mutableListOf<FoodEntry>()
            val symptomEvents = mutableListOf<SymptomEvent>()

            // Days with food and symptoms (strong association)
            repeat(8) { i ->
                val dayStart = baseTime.plus(i.toLong(), ChronoUnit.DAYS)
                foodEntries.add(createFoodEntry("TestFood", dayStart))
                symptomEvents.add(createSymptomEvent(
                    SymptomType.DIARRHEA,
                    dayStart.plus(2, ChronoUnit.HOURS),
                    7
                ))
            }

            // Days without food or symptoms
            repeat(12) { i ->
                // Add some entries for other foods on different days
                val dayStart = baseTime.plus((i + 10).toLong(), ChronoUnit.DAYS)
                foodEntries.add(createFoodEntry("OtherFood", dayStart))
            }

            val result = correlationEngine.calculateChiSquare(
                foodEntries, symptomEvents, "TestFood", SymptomType.DIARRHEA
            )

            assertNotNull(result)
            assertTrue(result.chiSquareStatistic > 0, "Chi-square statistic should be positive")
            assertTrue(result.degreesOfFreedom == 1, "Should have 1 degree of freedom for 2x2 table")
            assertTrue(result.isStatisticallySignificant, "Should be statistically significant")

            // Verify contingency table
            val table = result.contingencyTable
            assertTrue(table.foodWithSymptom > 0, "Should have food with symptom cases")
            assertTrue(table.foodWithoutSymptom >= 0, "Should have food without symptom cases")
        }
    }

    @Nested
    @DisplayName("Comprehensive Analysis Tests")
    inner class ComprehensiveAnalysisTests {

        @Test
        @DisplayName("Should generate complete analysis with both statistical tests")
        fun testCompleteAnalysis() {
            val foodEntries = createRealisticFoodEntries()
            val symptomEvents = createRealisticSymptomEvents()

            val result = correlationEngine.analyzeCorrelation(
                foodEntries, symptomEvents, "Dairy", SymptomType.BLOATING
            )

            assertNotNull(result)
            assertEquals("Dairy", result.foodName)
            assertEquals(SymptomType.BLOATING, result.symptomType)

            // Should have either Pearson or Chi-square result (or both)
            assertTrue(result.pearsonResult != null || result.chiSquareResult != null,
                "Should have at least one statistical test result")

            assertTrue(result.occurrences >= 0)
            assertTrue(result.averageTimeOffsetMinutes >= 0)
            assertTrue(result.averageSeverity >= 0.0)
            assertNotNull(result.recommendedAction)
        }

        @ParameterizedTest
        @EnumSource(RecommendedAction::class)
        @DisplayName("Should provide appropriate recommended actions")
        fun testRecommendedActions(expectedAction: RecommendedAction) {
            val (foodEntries, symptomEvents) = createScenarioForAction(expectedAction)

            val result = correlationEngine.analyzeCorrelation(
                foodEntries, symptomEvents, "TestFood", SymptomType.ABDOMINAL_PAIN
            )

            assertNotNull(result)
            assertEquals(expectedAction, result.recommendedAction)
        }

        @Test
        @DisplayName("Should calculate time offsets correctly")
        fun testTimeOffsetCalculation() {
            val baseTime = Instant.now()
            val foodEntry = createFoodEntry("TestFood", baseTime)

            // Symptom occurs 90 minutes after eating
            val symptomEvent = createSymptomEvent(
                SymptomType.NAUSEA,
                baseTime.plus(90, ChronoUnit.MINUTES),
                6
            )

            val result = correlationEngine.analyzeCorrelation(
                listOf(foodEntry), listOf(symptomEvent), "TestFood", SymptomType.NAUSEA
            )

            assertNotNull(result)
            assertEquals(90, result.averageTimeOffsetMinutes)
            assertEquals(1, result.occurrences)
            assertEquals(6.0, result.averageSeverity)
        }
    }

    @Nested
    @DisplayName("Edge Cases and Error Handling")
    inner class EdgeCaseTests {

        @Test
        @DisplayName("Should handle empty input lists gracefully")
        fun testEmptyInputs() {
            val result = correlationEngine.calculatePearsonCorrelation(
                emptyList(), emptyList(), "NonexistentFood", SymptomType.HEARTBURN
            )
            assertNull(result)
        }

        @Test
        @DisplayName("Should handle non-existent food names")
        fun testNonexistentFood() {
            val foodEntries = createFoodEntries("RealFood", 10)
            val symptomEvents = createSymptomEvents(SymptomType.CONSTIPATION, 5)

            val result = correlationEngine.calculatePearsonCorrelation(
                foodEntries, symptomEvents, "NonexistentFood", SymptomType.CONSTIPATION
            )

            assertNull(result, "Should return null for non-existent food")
        }

        @Test
        @DisplayName("Should handle extreme correlation values")
        fun testExtremeCorrelations() {
            // Perfect positive correlation scenario
            val baseTime = Instant.now()
            val foodEntries = mutableListOf<FoodEntry>()
            val symptomEvents = mutableListOf<SymptomEvent>()

            repeat(10) { i ->
                val dayTime = baseTime.plus(i.toLong(), ChronoUnit.DAYS)
                if (i < 5) {
                    // First 5 days: food and symptoms
                    foodEntries.add(createFoodEntry("TestFood", dayTime))
                    symptomEvents.add(createSymptomEvent(
                        SymptomType.BLOATING,
                        dayTime.plus(1, ChronoUnit.HOURS),
                        8
                    ))
                }
                // Last 5 days: no food, no symptoms (perfect separation)
            }

            val result = correlationEngine.calculatePearsonCorrelation(
                foodEntries, symptomEvents, "TestFood", SymptomType.BLOATING
            )

            assertNotNull(result)
            assertTrue(kotlin.math.abs(result.correlation) >= 0.8,
                "Should have strong correlation for perfect scenario")
        }
    }

    // Helper methods for test data creation

    private fun createFoodEntries(foodName: String, count: Int): List<FoodEntry> {
        val baseTime = Instant.now().minus(count.toLong(), ChronoUnit.DAYS)
        return (0 until count).map { i ->
            createFoodEntry(foodName, baseTime.plus(i.toLong(), ChronoUnit.DAYS))
        }
    }

    private fun createFoodEntry(foodName: String, timestamp: Instant): FoodEntry {
        return FoodEntry(
            id = 0,
            foodName = foodName,
            portions = 1.0,
            portionUnit = "serving",
            mealType = "Lunch",
            ingredients = listOf(foodName),
            timestamp = timestamp,
            notes = "Test entry",
            estimatedCalories = 200,
            preparationMethod = "Raw"
        )
    }

    private fun createSymptomEvents(symptomType: SymptomType, count: Int): List<SymptomEvent> {
        val baseTime = Instant.now().minus(count.toLong(), ChronoUnit.DAYS)
        return (0 until count).map { i ->
            createSymptomEvent(symptomType, baseTime.plus(i.toLong(), ChronoUnit.DAYS), 5)
        }
    }

    private fun createSymptomEvent(
        symptomType: SymptomType,
        timestamp: Instant,
        severity: Int
    ): SymptomEvent {
        return SymptomEvent(
            id = 0,
            symptomType = symptomType,
            severity = severity,
            timestamp = timestamp,
            duration = 60,
            notes = "Test symptom",
            bristolStoolType = null,
            location = "Abdomen",
            triggers = emptyList()
        )
    }

    private fun createRandomSymptomEvents(symptomType: SymptomType, count: Int): List<SymptomEvent> {
        val baseTime = Instant.now().minus(30, ChronoUnit.DAYS)
        return (0 until count).map { i ->
            val randomOffset = (kotlin.random.Random.nextDouble() * 30).toLong()
            createSymptomEvent(
                symptomType,
                baseTime.plus(randomOffset, ChronoUnit.DAYS),
                kotlin.random.Random.nextInt(1, 11)
            )
        }
    }

    private fun createRealisticFoodEntries(): List<FoodEntry> {
        val foods = listOf("Dairy", "Wheat", "Beans", "Apples", "Coffee")
        val baseTime = Instant.now().minus(30, ChronoUnit.DAYS)
        val entries = mutableListOf<FoodEntry>()

        repeat(50) { i ->
            val food = foods[i % foods.size]
            val timestamp = baseTime.plus(i.toLong(), ChronoUnit.HOURS)
            entries.add(createFoodEntry(food, timestamp))
        }

        return entries
    }

    private fun createRealisticSymptomEvents(): List<SymptomEvent> {
        val symptoms = SymptomType.values().toList()
        val baseTime = Instant.now().minus(30, ChronoUnit.DAYS)
        val events = mutableListOf<SymptomEvent>()

        repeat(25) { i ->
            val symptom = symptoms[i % symptoms.size]
            val timestamp = baseTime.plus(i.toLong(), ChronoUnit.HOURS).plus(
                kotlin.random.Random.nextLong(1, 6), ChronoUnit.HOURS
            )
            events.add(createSymptomEvent(symptom, timestamp, kotlin.random.Random.nextInt(3, 9)))
        }

        return events
    }

    private fun createScenarioForAction(action: RecommendedAction): Pair<List<FoodEntry>, List<SymptomEvent>> {
        val baseTime = Instant.now().minus(30, ChronoUnit.DAYS)

        return when (action) {
            RecommendedAction.ELIMINATE -> {
                // High correlation, many occurrences
                val foodEntries = (0..15).map { i ->
                    createFoodEntry("TestFood", baseTime.plus(i.toLong(), ChronoUnit.DAYS))
                }
                val symptomEvents = (0..15).map { i ->
                    createSymptomEvent(
                        SymptomType.ABDOMINAL_PAIN,
                        baseTime.plus(i.toLong(), ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS),
                        8
                    )
                }
                Pair(foodEntries, symptomEvents)
            }

            RecommendedAction.REDUCE -> {
                // Moderate correlation, some occurrences
                val foodEntries = (0..10).map { i ->
                    createFoodEntry("TestFood", baseTime.plus(i.toLong(), ChronoUnit.DAYS))
                }
                val symptomEvents = (0..7).map { i ->
                    createSymptomEvent(
                        SymptomType.ABDOMINAL_PAIN,
                        baseTime.plus(i.toLong(), ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS),
                        6
                    )
                }
                Pair(foodEntries, symptomEvents)
            }

            RecommendedAction.MONITOR -> {
                // Some correlation, few occurrences
                val foodEntries = (0..5).map { i ->
                    createFoodEntry("TestFood", baseTime.plus(i.toLong(), ChronoUnit.DAYS))
                }
                val symptomEvents = (0..3).map { i ->
                    createSymptomEvent(
                        SymptomType.ABDOMINAL_PAIN,
                        baseTime.plus(i.toLong(), ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS),
                        5
                    )
                }
                Pair(foodEntries, symptomEvents)
            }

            RecommendedAction.CONTINUE -> {
                // No significant correlation
                val foodEntries = listOf(createFoodEntry("TestFood", baseTime))
                val symptomEvents = listOf(createSymptomEvent(
                    SymptomType.ABDOMINAL_PAIN,
                    baseTime.plus(2, ChronoUnit.DAYS),
                    3
                ))
                Pair(foodEntries, symptomEvents)
            }
        }
    }
}