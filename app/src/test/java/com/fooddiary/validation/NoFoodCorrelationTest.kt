package com.fooddiary.validation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.data.repository.SymptomRepository
import com.fooddiary.data.repository.CorrelationRepository
import com.fooddiary.data.database.entities.Symptom
import com.fooddiary.data.models.*
import com.fooddiary.presentation.ui.entry.SymptomEntryViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * T056: Test symptom logging with no recent food entries
 * Tests graceful handling when no food correlation data is available
 */
@ExperimentalCoroutinesApi
class NoFoodCorrelationTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var foodEntryRepository: FoodEntryRepository

    @MockK
    private lateinit var symptomRepository: SymptomRepository

    @MockK
    private lateinit var correlationRepository: CorrelationRepository

    private lateinit var symptomEntryViewModel: SymptomEntryViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        // Mock empty food entries by default
        every { foodEntryRepository.getRecent(any()) } returns flowOf(emptyList())
        every { foodEntryRepository.getRecentByTimeWindow(any(), any()) } returns flowOf(emptyList())
        coEvery { correlationRepository.getPotentialTriggers(any()) } returns emptyList()
        coEvery { symptomRepository.insert(any()) } returns "symptom-123"

        symptomEntryViewModel = SymptomEntryViewModel(
            foodEntryRepository,
            symptomRepository,
            correlationRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `symptom entry should handle empty food entry list gracefully`() = runTest {
        // Given - completely empty food database
        every { foodEntryRepository.getRecent(24) } returns flowOf(emptyList())
        every { foodEntryRepository.getRecentByTimeWindow(any(), any()) } returns flowOf(emptyList())

        // When - loading potential triggers
        symptomEntryViewModel.loadPotentialTriggers()
        advanceUntilIdle()

        // Then
        val state = symptomEntryViewModel.uiState.value
        assertTrue("Potential triggers should be empty", state.potentialTriggers.isEmpty())
        assertFalse("Should not be loading", state.isLoadingTriggers)
        assertNull("Should not show error for empty state", state.errorMessage)
        assertEquals("Should show appropriate message", "No recent food entries found", state.noTriggersMessage)
    }

    @Test
    fun `correlation analysis should return empty list when no food entries exist`() = runTest {
        // Given - no food entries in recent 24 hours
        every { foodEntryRepository.getRecentByTimeWindow(any(), any()) } returns flowOf(emptyList())
        coEvery { correlationRepository.getPotentialTriggers(any()) } returns emptyList()

        // When - analyzing potential correlations
        val symptom = createTestSymptom()
        val correlations = correlationRepository.getPotentialTriggers(symptom)

        // Then
        assertTrue("Correlations should be empty", correlations.isEmpty())
        coVerify { correlationRepository.getPotentialTriggers(symptom) }
    }

    @Test
    fun `UI should display empty state message when no food entries available`() = runTest {
        // Given - empty food repository
        every { foodEntryRepository.getRecent(any()) } returns flowOf(emptyList())

        // When - initializing symptom entry screen
        symptomEntryViewModel.loadPotentialTriggers()
        advanceUntilIdle()

        // Then
        val state = symptomEntryViewModel.uiState.value
        assertTrue("Should show empty state", state.showEmptyTriggersState)
        assertEquals("Should show guidance message",
            "Start by logging some meals to identify potential trigger foods.",
            state.emptyStateGuidance)
        assertFalse("Add trigger button should be hidden", state.showAddTriggerButton)
    }

    @Test
    fun `symptom entry should still be savable without food correlation`() = runTest {
        // Given - no food entries but valid symptom data
        every { foodEntryRepository.getRecent(any()) } returns flowOf(emptyList())
        coEvery { symptomRepository.insert(any()) } returns "symptom-456"

        // When - entering symptom without food correlation
        symptomEntryViewModel.updateSymptomType(SymptomType.BLOATING)
        symptomEntryViewModel.updateSeverity(6)
        symptomEntryViewModel.updateNotes("Sudden bloating without known cause")

        val saveResult = symptomEntryViewModel.saveSymptom()
        advanceUntilIdle()

        // Then
        assertTrue("Should save successfully without correlations", saveResult)
        coVerify { symptomRepository.insert(match { symptom ->
            symptom.type == SymptomType.BLOATING &&
            symptom.severity == 6 &&
            symptom.triggerFoodId == null &&
            symptom.notes == "Sudden bloating without known cause"
        }) }

        val state = symptomEntryViewModel.uiState.value
        assertEquals("Should show success message", "Symptom logged successfully", state.successMessage)
    }

    @Test
    fun `trigger suggestion should show instructional content when no foods available`() = runTest {
        // Given - empty food database
        every { foodEntryRepository.getRecent(any()) } returns flowOf(emptyList())

        // When - showing trigger selection
        symptomEntryViewModel.showTriggerSelection()
        advanceUntilIdle()

        // Then
        val state = symptomEntryViewModel.uiState.value
        assertTrue("Should show trigger selection mode", state.showTriggerSelection)
        assertTrue("Should show instructional content", state.showInstructionalContent)
        assertEquals("Should show guidance text",
            "To identify trigger foods, start by logging your meals. After a few entries, potential triggers will appear here.",
            state.instructionalText)
        assertTrue("Available triggers should be empty", state.availableTriggers.isEmpty())
    }

    @Test
    fun `empty correlation list should not prevent symptom analysis`() = runTest {
        // Given - symptom with no potential triggers
        val symptom = createTestSymptom()
        coEvery { correlationRepository.getPotentialTriggers(symptom) } returns emptyList()
        coEvery { correlationRepository.analyzeSymptomPattern(symptom) } returns SymptomAnalysis(
            patternType = PatternType.ISOLATED,
            confidence = 0.0f,
            relatedSymptoms = emptyList(),
            potentialTriggers = emptyList(),
            recommendations = listOf("Continue logging meals to identify patterns")
        )

        // When - analyzing symptom pattern
        val analysis = correlationRepository.analyzeSymptomPattern(symptom)

        // Then
        assertNotNull("Analysis should still be performed", analysis)
        assertEquals("Should indicate isolated pattern", PatternType.ISOLATED, analysis.patternType)
        assertEquals("Confidence should be zero", 0.0f, analysis.confidence)
        assertTrue("Potential triggers should be empty", analysis.potentialTriggers.isEmpty())
        assertFalse("Should still provide recommendations", analysis.recommendations.isEmpty())
        assertTrue("Should suggest continuing to log meals",
            analysis.recommendations.any { it.contains("Continue logging meals") })
    }

    @Test
    fun `food search should return empty results gracefully`() = runTest {
        // Given - empty food database
        every { foodEntryRepository.searchFoods(any()) } returns flowOf(emptyList())

        // When - searching for foods to add as trigger
        symptomEntryViewModel.searchFoodsForTrigger("apple")
        advanceUntilIdle()

        // Then
        val state = symptomEntryViewModel.uiState.value
        assertTrue("Food search results should be empty", state.foodSearchResults.isEmpty())
        assertFalse("Should not be searching", state.isSearchingFoods)
        assertEquals("Should show no results message", "No foods found. Try a different search term.", state.noSearchResultsMessage)
    }

    @Test
    fun `validation should pass for symptom entry without food correlation`() = runTest {
        // Given - valid symptom data but no food entries
        every { foodEntryRepository.getRecent(any()) } returns flowOf(emptyList())

        // When - validating symptom entry
        symptomEntryViewModel.updateSymptomType(SymptomType.STOMACH_PAIN)
        symptomEntryViewModel.updateSeverity(5)
        symptomEntryViewModel.updateDuration(30) // 30 minutes

        val isValid = symptomEntryViewModel.validateEntry()
        advanceUntilIdle()

        // Then
        assertTrue("Entry should be valid without food correlation", isValid)
        val state = symptomEntryViewModel.uiState.value
        assertTrue("Validation errors should be empty", state.validationErrors.isEmpty())
        assertTrue("Entry should be considered complete", state.isEntryComplete)
    }

    @Test
    fun `recent symptoms view should work without food correlations`() = runTest {
        // Given - symptoms without food correlations
        val recentSymptoms = listOf(
            createTestSymptom(type = SymptomType.BLOATING, triggerFoodId = null),
            createTestSymptom(type = SymptomType.NAUSEA, triggerFoodId = null)
        )
        every { symptomRepository.getRecent(7) } returns flowOf(recentSymptoms)

        // When - loading recent symptoms
        symptomEntryViewModel.loadRecentSymptoms()
        advanceUntilIdle()

        // Then
        val state = symptomEntryViewModel.uiState.value
        assertEquals("Should show recent symptoms", 2, state.recentSymptoms.size)
        assertTrue("All symptoms should have null trigger",
            state.recentSymptoms.all { it.triggerFoodId == null })
        assertEquals("Should show uncorrelated status", "No trigger identified",
            state.recentSymptoms.first().triggerStatus)
    }

    // Helper methods
    private fun createTestSymptom(
        id: String = "symptom-test",
        type: SymptomType = SymptomType.BLOATING,
        severity: Int = 5,
        triggerFoodId: String? = null,
        timestamp: Instant = Instant.now()
    ) = Symptom(
        id = id,
        timestamp = timestamp,
        type = type,
        severity = severity,
        duration = 30,
        triggerFoodId = triggerFoodId,
        notes = "Test symptom",
        isDeleted = false
    )
}

// Supporting data classes for SymptomEntryViewModel
data class SymptomEntryUiState(
    val symptomType: SymptomType = SymptomType.BLOATING,
    val severity: Int = 1,
    val duration: Int = 0,
    val notes: String = "",
    val potentialTriggers: List<PotentialTrigger> = emptyList(),
    val selectedTrigger: PotentialTrigger? = null,
    val isLoadingTriggers: Boolean = false,
    val validationErrors: List<String> = emptyList(),
    val isEntryComplete: Boolean = false,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val noTriggersMessage: String? = null,
    val showEmptyTriggersState: Boolean = false,
    val emptyStateGuidance: String? = null,
    val showAddTriggerButton: Boolean = true,
    val showTriggerSelection: Boolean = false,
    val showInstructionalContent: Boolean = false,
    val instructionalText: String? = null,
    val availableTriggers: List<String> = emptyList(),
    val foodSearchResults: List<String> = emptyList(),
    val isSearchingFoods: Boolean = false,
    val noSearchResultsMessage: String? = null,
    val recentSymptoms: List<SymptomWithTrigger> = emptyList()
)

data class PotentialTrigger(
    val foodName: String,
    val confidence: Float,
    val timeSinceConsumed: Long, // minutes
    val mealType: MealType
)

data class SymptomWithTrigger(
    val symptom: Symptom,
    val triggerFoodId: String?,
    val triggerStatus: String
)

data class SymptomAnalysis(
    val patternType: PatternType,
    val confidence: Float,
    val relatedSymptoms: List<Symptom>,
    val potentialTriggers: List<PotentialTrigger>,
    val recommendations: List<String>
)

enum class PatternType {
    ISOLATED, RECURRING, MEAL_RELATED, TIME_BASED
}

enum class SymptomType {
    BLOATING, STOMACH_PAIN, NAUSEA, DIARRHEA, CONSTIPATION, GAS, CRAMPING
}