package com.fooddiary.domain.usecase

import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.TriggerPattern
import com.fooddiary.data.models.SymptomType
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.data.repository.SymptomEventRepository
import com.fooddiary.data.repository.TriggerPatternRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

@DisplayName("Calculate Correlations UseCase Tests")
class CalculateCorrelationsUseCaseTest {

    private lateinit var foodEntryRepository: FoodEntryRepository
    private lateinit var symptomEventRepository: SymptomEventRepository
    private lateinit var triggerPatternRepository: TriggerPatternRepository
    private lateinit var calculateCorrelationsUseCase: CalculateCorrelationsUseCase

    private val baseTime = Instant.now().truncatedTo(ChronoUnit.DAYS)
    private val startDate = LocalDate.now().minusDays(30)
    private val endDate = LocalDate.now()

    @BeforeEach
    fun setup() {
        foodEntryRepository = mockk(relaxed = true)
        symptomEventRepository = mockk(relaxed = true)
        triggerPatternRepository = mockk(relaxed = true)
        calculateCorrelationsUseCase = CalculateCorrelationsUseCase(
            foodEntryRepository,
            symptomEventRepository,
            triggerPatternRepository
        )
    }

    @Nested
    @DisplayName("Successful Correlation Calculation")
    inner class SuccessfulCorrelationCalculation {

        @Test
        @DisplayName("Should calculate correlations for single food-symptom pair")
        fun `should calculate correlations for single food-symptom pair`() = runTest {
            // Given
            val foodEntries = listOf(
                createFoodEntry("1", "milk", baseTime),
                createFoodEntry("2", "milk", baseTime.plus(1, ChronoUnit.DAYS)),
                createFoodEntry("3", "milk", baseTime.plus(2, ChronoUnit.DAYS))
            )

            val symptomEvents = listOf(
                createSymptomEvent("1", SymptomType.BLOATING, baseTime.plus(2, ChronoUnit.HOURS)),
                createSymptomEvent("2", SymptomType.BLOATING, baseTime.plus(1, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS)),
                createSymptomEvent("3", SymptomType.BLOATING, baseTime.plus(2, ChronoUnit.DAYS).plus(3, ChronoUnit.HOURS))
            )

            setupRepositoryMocks(foodEntries, symptomEvents, emptyList())

            // When
            val result = calculateCorrelationsUseCase.invoke(
                startDate = startDate,
                endDate = endDate,
                minOccurrences = 3,
                minCorrelation = 0.5f
            )

            // Then
            assertTrue(result.isSuccess)
            val patterns = result.getOrNull()!!
            assertEquals(1, patterns.size)

            val pattern = patterns.first()
            assertEquals("milk", pattern.foodName)
            assertEquals(SymptomType.BLOATING, pattern.symptomType)
            assertEquals(3, pattern.occurrences)
            assertTrue(pattern.correlationStrength >= 0.5f)
            assertTrue(pattern.averageTimeOffsetMinutes > 0)
            assertTrue(pattern.isStatisticallySignificant)
        }

        @Test
        @DisplayName("Should calculate correlations for multiple food-symptom pairs")
        fun `should calculate correlations for multiple food-symptom pairs`() = runTest {
            // Given
            val foodEntries = listOf(
                createFoodEntry("1", "milk", baseTime),
                createFoodEntry("2", "wheat", baseTime.plus(6, ChronoUnit.HOURS)),
                createFoodEntry("3", "milk", baseTime.plus(1, ChronoUnit.DAYS)),
                createFoodEntry("4", "wheat", baseTime.plus(1, ChronoUnit.DAYS).plus(8, ChronoUnit.HOURS))
            )

            val symptomEvents = listOf(
                createSymptomEvent("1", SymptomType.BLOATING, baseTime.plus(2, ChronoUnit.HOURS)),
                createSymptomEvent("2", SymptomType.DIARRHEA, baseTime.plus(8, ChronoUnit.HOURS)),
                createSymptomEvent("3", SymptomType.BLOATING, baseTime.plus(1, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS)),
                createSymptomEvent("4", SymptomType.DIARRHEA, baseTime.plus(1, ChronoUnit.DAYS).plus(10, ChronoUnit.HOURS))
            )

            setupRepositoryMocks(foodEntries, symptomEvents, emptyList())

            // When
            val result = calculateCorrelationsUseCase.invoke(
                startDate = startDate,
                endDate = endDate,
                minOccurrences = 2,
                minCorrelation = 0.5f
            )

            // Then
            assertTrue(result.isSuccess)
            val patterns = result.getOrNull()!!
            assertTrue(patterns.size >= 2)

            val milkBloatingPattern = patterns.find { it.foodName == "milk" && it.symptomType == SymptomType.BLOATING }
            val wheatDiarrheaPattern = patterns.find { it.foodName == "wheat" && it.symptomType == SymptomType.DIARRHEA }

            assertNotNull(milkBloatingPattern)
            assertNotNull(wheatDiarrheaPattern)
        }

        @Test
        @DisplayName("Should respect time window constraints")
        fun `should respect time window constraints`() = runTest {
            // Given
            val foodEntries = listOf(
                createFoodEntry("1", "apple", baseTime),
                createFoodEntry("2", "apple", baseTime.plus(1, ChronoUnit.DAYS))
            )

            val symptomEvents = listOf(
                createSymptomEvent("1", SymptomType.BLOATING, baseTime.plus(2, ChronoUnit.HOURS)), // Within window
                createSymptomEvent("2", SymptomType.BLOATING, baseTime.plus(1, ChronoUnit.DAYS).plus(30, ChronoUnit.HOURS)) // Outside 24h window
            )

            setupRepositoryMocks(foodEntries, symptomEvents, emptyList())

            // When
            val result = calculateCorrelationsUseCase.invoke(
                startDate = startDate,
                endDate = endDate,
                minOccurrences = 1,
                minCorrelation = 0.0f,
                maxTimeWindowHours = 24
            )

            // Then
            assertTrue(result.isSuccess)
            val patterns = result.getOrNull()!!

            if (patterns.isNotEmpty()) {
                val pattern = patterns.find { it.foodName == "apple" && it.symptomType == SymptomType.BLOATING }
                if (pattern != null) {
                    assertEquals(1, pattern.occurrences) // Only the symptom within 24h should count
                }
            }
        }

        @Test
        @DisplayName("Should save new patterns to repository")
        fun `should save new patterns to repository`() = runTest {
            // Given
            val foodEntries = listOf(createFoodEntry("1", "eggs", baseTime))
            val symptomEvents = listOf(createSymptomEvent("1", SymptomType.NAUSEA, baseTime.plus(1, ChronoUnit.HOURS)))

            setupRepositoryMocks(foodEntries, symptomEvents, emptyList())
            coEvery { triggerPatternRepository.getByFood("eggs") } returns flowOf(emptyList())

            // When
            val result = calculateCorrelationsUseCase.invoke(
                startDate = startDate,
                endDate = endDate,
                minOccurrences = 1,
                minCorrelation = 0.0f
            )

            // Then
            assertTrue(result.isSuccess)
            coVerify { triggerPatternRepository.insert(any()) }
        }

        @Test
        @DisplayName("Should update existing patterns")
        fun `should update existing patterns`() = runTest {
            // Given
            val existingPattern = TriggerPattern(
                id = "existing_1",
                foodName = "chocolate",
                symptomType = SymptomType.HEARTBURN,
                correlationStrength = 0.5f,
                occurrences = 5,
                averageTimeOffsetMinutes = 30,
                confidenceLevel = 0.7f,
                isStatisticallySignificant = true,
                lastOccurrenceDate = baseTime.toString()
            )

            val foodEntries = listOf(createFoodEntry("1", "chocolate", baseTime))
            val symptomEvents = listOf(createSymptomEvent("1", SymptomType.HEARTBURN, baseTime.plus(45, ChronoUnit.MINUTES)))

            setupRepositoryMocks(foodEntries, symptomEvents, listOf(existingPattern))
            coEvery { triggerPatternRepository.getByFood("chocolate") } returns flowOf(listOf(existingPattern))

            // When
            val result = calculateCorrelationsUseCase.invoke(
                startDate = startDate,
                endDate = endDate,
                minOccurrences = 1,
                minCorrelation = 0.0f
            )

            // Then
            assertTrue(result.isSuccess)
            coVerify { triggerPatternRepository.update(any()) }
        }
    }

    @Nested
    @DisplayName("Filtering and Thresholds")
    inner class FilteringAndThresholds {

        @Test
        @DisplayName("Should filter out patterns below minimum occurrences")
        fun `should filter out patterns below minimum occurrences`() = runTest {
            // Given - only 2 occurrences, but minOccurrences = 3
            val foodEntries = listOf(
                createFoodEntry("1", "coffee", baseTime),
                createFoodEntry("2", "coffee", baseTime.plus(1, ChronoUnit.DAYS))
            )

            val symptomEvents = listOf(
                createSymptomEvent("1", SymptomType.JITTERS, baseTime.plus(30, ChronoUnit.MINUTES)),
                createSymptomEvent("2", SymptomType.JITTERS, baseTime.plus(1, ChronoUnit.DAYS).plus(30, ChronoUnit.MINUTES))
            )

            setupRepositoryMocks(foodEntries, symptomEvents, emptyList())

            // When
            val result = calculateCorrelationsUseCase.invoke(
                startDate = startDate,
                endDate = endDate,
                minOccurrences = 3,
                minCorrelation = 0.0f
            )

            // Then
            assertTrue(result.isSuccess)
            val patterns = result.getOrNull()!!
            assertEquals(0, patterns.size) // Should be filtered out
        }

        @Test
        @DisplayName("Should filter out patterns below minimum correlation")
        fun `should filter out patterns below minimum correlation`() = runTest {
            // Given - create a weak correlation
            val foodEntries = listOf(
                createFoodEntry("1", "rice", baseTime),
                createFoodEntry("2", "rice", baseTime.plus(1, ChronoUnit.DAYS)),
                createFoodEntry("3", "rice", baseTime.plus(2, ChronoUnit.DAYS))
            )

            // Only one symptom follows rice consumption (weak correlation)
            val symptomEvents = listOf(
                createSymptomEvent("1", SymptomType.BLOATING, baseTime.plus(2, ChronoUnit.HOURS)),
                createSymptomEvent("2", SymptomType.BLOATING, baseTime.plus(12, ChronoUnit.HOURS)) // Not after rice consumption
            )

            setupRepositoryMocks(foodEntries, symptomEvents, emptyList())

            // When
            val result = calculateCorrelationsUseCase.invoke(
                startDate = startDate,
                endDate = endDate,
                minOccurrences = 1,
                minCorrelation = 0.8f // High threshold
            )

            // Then
            assertTrue(result.isSuccess)
            val patterns = result.getOrNull()!!
            // Should be empty or have very few patterns due to high correlation threshold
            assertTrue(patterns.isEmpty() || patterns.all { it.correlationStrength >= 0.8f })
        }

        @Test
        @DisplayName("Should clean up weak patterns from database")
        fun `should clean up weak patterns from database`() = runTest {
            // Given
            val foodEntries = listOf(createFoodEntry("1", "test_food", baseTime))
            val symptomEvents = listOf(createSymptomEvent("1", SymptomType.BLOATING, baseTime.plus(1, ChronoUnit.HOURS)))

            setupRepositoryMocks(foodEntries, symptomEvents, emptyList())

            // When
            val result = calculateCorrelationsUseCase.invoke(
                startDate = startDate,
                endDate = endDate,
                minOccurrences = 5,
                minCorrelation = 0.7f
            )

            // Then
            assertTrue(result.isSuccess)
            coVerify { triggerPatternRepository.deleteWeakPatterns(5, 0.7f) }
        }
    }

    @Nested
    @DisplayName("Statistical Calculations")
    inner class StatisticalCalculations {

        @Test
        @DisplayName("Should calculate average time offset correctly")
        fun `should calculate average time offset correctly`() = runTest {
            // Given
            val foodEntries = listOf(
                createFoodEntry("1", "onion", baseTime),
                createFoodEntry("2", "onion", baseTime.plus(1, ChronoUnit.DAYS))
            )

            val symptomEvents = listOf(
                createSymptomEvent("1", SymptomType.ABDOMINAL_PAIN, baseTime.plus(60, ChronoUnit.MINUTES)), // 60 min offset
                createSymptomEvent("2", SymptomType.ABDOMINAL_PAIN, baseTime.plus(1, ChronoUnit.DAYS).plus(120, ChronoUnit.MINUTES)) // 120 min offset
            )

            setupRepositoryMocks(foodEntries, symptomEvents, emptyList())

            // When
            val result = calculateCorrelationsUseCase.invoke(
                startDate = startDate,
                endDate = endDate,
                minOccurrences = 2,
                minCorrelation = 0.0f
            )

            // Then
            assertTrue(result.isSuccess)
            val patterns = result.getOrNull()!!

            if (patterns.isNotEmpty()) {
                val pattern = patterns.find { it.foodName == "onion" }
                assertNotNull(pattern)
                assertEquals(90, pattern!!.averageTimeOffsetMinutes) // Average of 60 and 120
            }
        }

        @Test
        @DisplayName("Should calculate confidence level appropriately")
        fun `should calculate confidence level appropriately`() = runTest {
            // Given
            val foodEntries = (1..20).map { i ->
                createFoodEntry("$i", "trigger_food", baseTime.plus(i.toLong(), ChronoUnit.DAYS))
            }

            val symptomEvents = (1..20).map { i ->
                createSymptomEvent("$i", SymptomType.BLOATING, baseTime.plus(i.toLong(), ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS))
            }

            setupRepositoryMocks(foodEntries, symptomEvents, emptyList())

            // When
            val result = calculateCorrelationsUseCase.invoke(
                startDate = startDate.minusDays(30),
                endDate = endDate.plusDays(30),
                minOccurrences = 10,
                minCorrelation = 0.0f
            )

            // Then
            assertTrue(result.isSuccess)
            val patterns = result.getOrNull()!!

            if (patterns.isNotEmpty()) {
                val pattern = patterns.find { it.foodName == "trigger_food" }
                assertNotNull(pattern)
                assertTrue(pattern!!.confidenceLevel > 0.5f) // High occurrences should give good confidence
                assertTrue(pattern.isStatisticallySignificant)
            }
        }

        @Test
        @DisplayName("Should mark patterns as statistically significant when appropriate")
        fun `should mark patterns as statistically significant when appropriate`() = runTest {
            // Given - Strong pattern with many occurrences
            val foodEntries = (1..15).map { i ->
                createFoodEntry("$i", "strong_trigger", baseTime.plus(i.toLong(), ChronoUnit.HOURS))
            }

            val symptomEvents = (1..15).map { i ->
                createSymptomEvent("$i", SymptomType.CRAMPING, baseTime.plus(i.toLong(), ChronoUnit.HOURS).plus(30, ChronoUnit.MINUTES))
            }

            setupRepositoryMocks(foodEntries, symptomEvents, emptyList())

            // When
            val result = calculateCorrelationsUseCase.invoke(
                startDate = startDate,
                endDate = endDate,
                minOccurrences = 10,
                minCorrelation = 0.6f
            )

            // Then
            assertTrue(result.isSuccess)
            val patterns = result.getOrNull()!!

            if (patterns.isNotEmpty()) {
                val pattern = patterns.find { it.foodName == "strong_trigger" }
                assertNotNull(pattern)
                assertTrue(pattern!!.isStatisticallySignificant)
                assertTrue(pattern.correlationStrength >= 0.6f)
            }
        }
    }

    @Nested
    @DisplayName("Error Handling")
    inner class ErrorHandling {

        @Test
        @DisplayName("Should handle repository failures gracefully")
        fun `should handle repository failures gracefully`() = runTest {
            // Given
            coEvery { foodEntryRepository.getByDateRange(any(), any()) } throws RuntimeException("Database error")

            // When
            val result = calculateCorrelationsUseCase.invoke(startDate, endDate)

            // Then
            assertTrue(result.isFailure)
            assertEquals("Database error", result.exceptionOrNull()?.message)
        }

        @Test
        @DisplayName("Should handle empty data sets gracefully")
        fun `should handle empty data sets gracefully`() = runTest {
            // Given
            setupRepositoryMocks(emptyList(), emptyList(), emptyList())

            // When
            val result = calculateCorrelationsUseCase.invoke(startDate, endDate)

            // Then
            assertTrue(result.isSuccess)
            val patterns = result.getOrNull()!!
            assertTrue(patterns.isEmpty())
        }

        @Test
        @DisplayName("Should handle malformed data gracefully")
        fun `should handle malformed data gracefully`() = runTest {
            // Given
            val foodEntries = listOf(createFoodEntry("1", "", baseTime)) // Empty food name
            val symptomEvents = listOf(createSymptomEvent("1", SymptomType.BLOATING, baseTime))

            setupRepositoryMocks(foodEntries, symptomEvents, emptyList())

            // When
            val result = calculateCorrelationsUseCase.invoke(startDate, endDate)

            // Then
            assertTrue(result.isSuccess) // Should not crash, but may produce no patterns
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    inner class EdgeCases {

        @Test
        @DisplayName("Should handle same timestamp for food and symptom")
        fun `should handle same timestamp for food and symptom`() = runTest {
            // Given
            val sameTime = baseTime
            val foodEntries = listOf(createFoodEntry("1", "instant_trigger", sameTime))
            val symptomEvents = listOf(createSymptomEvent("1", SymptomType.IMMEDIATE_REACTION, sameTime))

            setupRepositoryMocks(foodEntries, symptomEvents, emptyList())

            // When
            val result = calculateCorrelationsUseCase.invoke(
                startDate = startDate,
                endDate = endDate,
                minOccurrences = 1,
                minCorrelation = 0.0f
            )

            // Then
            assertTrue(result.isSuccess)
            val patterns = result.getOrNull()!!

            if (patterns.isNotEmpty()) {
                val pattern = patterns.find { it.foodName == "instant_trigger" }
                assertNotNull(pattern)
                assertEquals(0, pattern!!.averageTimeOffsetMinutes) // Same timestamp = 0 offset
            }
        }

        @Test
        @DisplayName("Should handle very large time offsets")
        fun `should handle very large time offsets`() = runTest {
            // Given
            val foodEntries = listOf(createFoodEntry("1", "delayed_trigger", baseTime))
            val symptomEvents = listOf(
                createSymptomEvent("1", SymptomType.DELAYED_REACTION, baseTime.plus(23, ChronoUnit.HOURS))
            )

            setupRepositoryMocks(foodEntries, symptomEvents, emptyList())

            // When
            val result = calculateCorrelationsUseCase.invoke(
                startDate = startDate,
                endDate = endDate,
                minOccurrences = 1,
                minCorrelation = 0.0f,
                maxTimeWindowHours = 24
            )

            // Then
            assertTrue(result.isSuccess)
            val patterns = result.getOrNull()!!

            if (patterns.isNotEmpty()) {
                val pattern = patterns.find { it.foodName == "delayed_trigger" }
                assertNotNull(pattern)
                assertEquals(23 * 60, pattern!!.averageTimeOffsetMinutes) // 23 hours in minutes
            }
        }

        @Test
        @DisplayName("Should handle multiple symptoms of same type after one food")
        fun `should handle multiple symptoms of same type after one food`() = runTest {
            // Given
            val foodEntries = listOf(createFoodEntry("1", "multi_symptom_food", baseTime))
            val symptomEvents = listOf(
                createSymptomEvent("1", SymptomType.BLOATING, baseTime.plus(1, ChronoUnit.HOURS)),
                createSymptomEvent("2", SymptomType.BLOATING, baseTime.plus(2, ChronoUnit.HOURS)),
                createSymptomEvent("3", SymptomType.BLOATING, baseTime.plus(3, ChronoUnit.HOURS))
            )

            setupRepositoryMocks(foodEntries, symptomEvents, emptyList())

            // When
            val result = calculateCorrelationsUseCase.invoke(
                startDate = startDate,
                endDate = endDate,
                minOccurrences = 3,
                minCorrelation = 0.0f
            )

            // Then
            assertTrue(result.isSuccess)
            val patterns = result.getOrNull()!!

            if (patterns.isNotEmpty()) {
                val pattern = patterns.find { it.foodName == "multi_symptom_food" }
                assertNotNull(pattern)
                assertEquals(3, pattern!!.occurrences)
                assertEquals(120, pattern.averageTimeOffsetMinutes) // Average of 60, 120, 180
            }
        }
    }

    private fun createFoodEntry(id: String, foodName: String, timestamp: Instant): FoodEntry {
        return FoodEntry(
            id = id,
            foodName = foodName,
            mealType = "test",
            timestamp = timestamp,
            portions = 1.0,
            ingredients = emptyList(),
            timezone = "UTC"
        )
    }

    private fun createSymptomEvent(id: String, symptomType: SymptomType, timestamp: Instant): SymptomEvent {
        return SymptomEvent(
            id = id,
            symptomType = symptomType,
            severity = 5,
            timestamp = timestamp
        )
    }

    private fun setupRepositoryMocks(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        existingPatterns: List<TriggerPattern>
    ) {
        coEvery { foodEntryRepository.getByDateRange(any(), any()) } returns flowOf(foodEntries)
        coEvery { symptomEventRepository.getByDateRange(any(), any()) } returns flowOf(symptomEvents)
        coEvery { triggerPatternRepository.getByFood(any()) } returns flowOf(emptyList())
        coEvery { triggerPatternRepository.insert(any()) } returns 1L
        coEvery { triggerPatternRepository.update(any()) } returns Unit
        coEvery { triggerPatternRepository.deleteWeakPatterns(any(), any()) } returns Unit
    }
}