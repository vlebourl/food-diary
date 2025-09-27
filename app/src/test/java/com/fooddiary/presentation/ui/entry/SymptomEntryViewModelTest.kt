package com.fooddiary.presentation.ui.entry

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fooddiary.data.repository.SymptomEventRepository
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.data.repository.CorrelationRepository
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.models.*
import com.fooddiary.data.validation.SymptomEntryValidator
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * Comprehensive TDD unit tests for SymptomEntryViewModel
 * Tests symptom selection, severity input, duration tracking, correlation detection, and validation
 *
 * THESE TESTS WILL FAIL initially because SymptomEntryViewModel doesn't exist yet (TDD approach)
 */
@ExperimentalCoroutinesApi
class SymptomEntryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var symptomEventRepository: SymptomEventRepository

    @MockK
    private lateinit var foodEntryRepository: FoodEntryRepository

    @MockK
    private lateinit var correlationRepository: CorrelationRepository

    @MockK
    private lateinit var symptomEntryValidator: SymptomEntryValidator

    private lateinit var symptomEntryViewModel: SymptomEntryViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        // Setup default mock behaviors
        every { foodEntryRepository.getRecent(any()) } returns flowOf(emptyList())
        coEvery { correlationRepository.calculateCorrelations(any(), any()) } returns emptyList()
        coEvery { symptomEntryValidator.validateSymptomEntry(any()) } returns ValidationResult.Success

        symptomEntryViewModel = SymptomEntryViewModel(
            symptomEventRepository,
            foodEntryRepository,
            correlationRepository,
            symptomEntryValidator
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `initial state should have empty symptom selection and default values`() {
        // Given - fresh ViewModel instance

        // When - accessing initial state
        val initialState = symptomEntryViewModel.uiState.value

        // Then
        assertEquals("No symptom type selected initially", null, initialState.selectedSymptomType)
        assertEquals("Default severity should be 1", 1, initialState.severity)
        assertEquals("Duration should be null initially", null, initialState.duration)
        assertEquals("Notes should be empty", "", initialState.notes)
        assertTrue("Validation errors should be empty", initialState.validationErrors.isEmpty())
        assertFalse("Should not be saving initially", initialState.isSaving)
        assertTrue("Should be loading correlations initially", initialState.isLoadingCorrelations)
    }

    @Test
    fun `selectSymptom should update selected symptom type`() = runTest {
        // When
        symptomEntryViewModel.selectSymptom(SymptomType.BLOATING)
        advanceUntilIdle()

        // Then
        val state = symptomEntryViewModel.uiState.value
        assertEquals("Symptom type should be updated", SymptomType.BLOATING, state.selectedSymptomType)
    }

    @Test
    fun `selectSymptom should trigger correlation detection automatically`() = runTest {
        // Given
        val mockCorrelations = listOf(
            createMockSymptomFoodCorrelation(SymptomType.BLOATING, "garlic", 0.8f),
            createMockSymptomFoodCorrelation(SymptomType.BLOATING, "beans", 0.7f)
        )
        coEvery { correlationRepository.getCorrelationsForSymptom(any()) } returns flowOf(mockCorrelations)

        // When
        symptomEntryViewModel.selectSymptom(SymptomType.BLOATING)
        advanceUntilIdle()

        // Then
        val state = symptomEntryViewModel.uiState.value
        assertEquals("Should detect correlations", 2, state.suggestedCorrelations.size)
        assertFalse("Should not be loading correlations anymore", state.isLoadingCorrelations)
    }

    @Test
    fun `setSeverity should update severity within valid range`() {
        // When
        symptomEntryViewModel.setSeverity(7)

        // Then
        val state = symptomEntryViewModel.uiState.value
        assertEquals("Severity should be updated", 7, state.severity)
    }

    @Test
    fun `setSeverity should clamp severity to valid range`() {
        // When - try to set severity outside valid range
        symptomEntryViewModel.setSeverity(15)
        val highState = symptomEntryViewModel.uiState.value

        symptomEntryViewModel.setSeverity(-2)
        val lowState = symptomEntryViewModel.uiState.value

        // Then
        assertEquals("High severity should be clamped to 10", 10, highState.severity)
        assertEquals("Low severity should be clamped to 1", 1, lowState.severity)
    }

    @Test
    fun `setDuration should update duration`() {
        // When
        symptomEntryViewModel.setDuration("30 minutes")

        // Then
        val state = symptomEntryViewModel.uiState.value
        assertEquals("Duration should be updated", "30 minutes", state.duration)
    }

    @Test
    fun `detectCorrelations should analyze recent food entries for patterns`() = runTest {
        // Given - recent food entries
        val recentFoodEntries = listOf(
            createMockFoodEntry("garlic", Instant.now().minus(2, ChronoUnit.HOURS)),
            createMockFoodEntry("bread", Instant.now().minus(4, ChronoUnit.HOURS)),
            createMockFoodEntry("milk", Instant.now().minus(6, ChronoUnit.HOURS))
        )
        every { foodEntryRepository.getRecent(48) } returns flowOf(recentFoodEntries)

        val expectedCorrelations = listOf(
            createMockTimeWindowAnalysis("garlic", 2, 5), // 5 symptoms in 2 hours after garlic
            createMockTimeWindowAnalysis("milk", 6, 2)    // 2 symptoms in 6 hours after milk
        )
        coEvery { correlationRepository.getTimeWindowAnalysis("garlic", 48) } returns expectedCorrelations[0]
        coEvery { correlationRepository.getTimeWindowAnalysis("milk", 48) } returns expectedCorrelations[1]
        coEvery { correlationRepository.getTimeWindowAnalysis("bread", 48) } returns
            createMockTimeWindowAnalysis("bread", 4, 0)

        // When
        symptomEntryViewModel.detectCorrelations()
        advanceUntilIdle()

        // Then
        val state = symptomEntryViewModel.uiState.value
        assertTrue("Should find correlations", state.realtimeCorrelations.isNotEmpty())
        assertEquals("Should prioritize higher correlation foods", "garlic", state.realtimeCorrelations[0].foodName)
        assertFalse("Should not be loading correlations", state.isLoadingCorrelations)
    }

    @Test
    fun `saveSymptom should validate then save successful symptom entry`() = runTest {
        // Given - valid symptom entry
        val savedSymptomId = "saved-symptom-123"
        coEvery { symptomEntryValidator.validateSymptomEntry(any()) } returns ValidationResult.Success
        coEvery { symptomEventRepository.insert(any()) } returns savedSymptomId

        symptomEntryViewModel.selectSymptom(SymptomType.BLOATING)
        symptomEntryViewModel.setSeverity(6)
        symptomEntryViewModel.setDuration("1 hour")
        advanceUntilIdle()

        // When
        val result = symptomEntryViewModel.saveSymptom()
        advanceUntilIdle()

        // Then
        assertTrue("Save should be successful", result)
        coVerify(exactly = 1) { symptomEventRepository.insert(any()) }

        val state = symptomEntryViewModel.uiState.value
        assertFalse("Should not be saving anymore", state.isSaving)
        assertEquals("Should have success message", "Symptom recorded successfully", state.message)
    }

    @Test
    fun `saveSymptom should not save invalid symptom entry`() = runTest {
        // Given - invalid entry
        val validationErrors = listOf("Symptom type is required")
        coEvery { symptomEntryValidator.validateSymptomEntry(any()) } returns ValidationResult.Error(validationErrors)

        // When - try to save without selecting symptom type
        val result = symptomEntryViewModel.saveSymptom()
        advanceUntilIdle()

        // Then
        assertFalse("Save should fail", result)
        coVerify(exactly = 0) { symptomEventRepository.insert(any()) }

        val state = symptomEntryViewModel.uiState.value
        assertEquals("Should show validation errors", validationErrors, state.validationErrors)
    }

    @Test
    fun `updateTimestamp should update symptom entry timestamp`() {
        // Given
        val newTimestamp = Instant.now().minus(1, ChronoUnit.HOURS)

        // When
        symptomEntryViewModel.updateTimestamp(newTimestamp)

        // Then
        val state = symptomEntryViewModel.uiState.value
        assertEquals("Timestamp should be updated", newTimestamp, state.timestamp)
    }

    @Test
    fun `updateNotes should update symptom notes`() {
        // When
        symptomEntryViewModel.updateNotes("Felt worse after eating spicy food")

        // Then
        val state = symptomEntryViewModel.uiState.value
        assertEquals("Notes should be updated", "Felt worse after eating spicy food", state.notes)
    }

    @Test
    fun `linkToFoodEntry should create connection to suspected trigger food`() = runTest {
        // Given - symptom entry data
        val triggerFoodEntryId = "food-entry-123"
        symptomEntryViewModel.selectSymptom(SymptomType.NAUSEA)
        symptomEntryViewModel.setSeverity(5)

        // When
        symptomEntryViewModel.linkToFoodEntry(triggerFoodEntryId)

        // Then
        val state = symptomEntryViewModel.uiState.value
        assertEquals("Should link to trigger food", triggerFoodEntryId, state.linkedFoodEntryId)
    }

    @Test
    fun `clearForm should reset all symptom entry fields`() = runTest {
        // Given - form with data
        symptomEntryViewModel.selectSymptom(SymptomType.CRAMPING)
        symptomEntryViewModel.setSeverity(8)
        symptomEntryViewModel.setDuration("2 hours")
        symptomEntryViewModel.updateNotes("Test notes")
        symptomEntryViewModel.linkToFoodEntry("food-123")
        advanceUntilIdle()

        // When
        symptomEntryViewModel.clearForm()
        advanceUntilIdle()

        // Then
        val state = symptomEntryViewModel.uiState.value
        assertEquals("Symptom type should be null", null, state.selectedSymptomType)
        assertEquals("Severity should reset to 1", 1, state.severity)
        assertEquals("Duration should be null", null, state.duration)
        assertEquals("Notes should be empty", "", state.notes)
        assertEquals("Linked food entry should be null", null, state.linkedFoodEntryId)
        assertTrue("Validation errors should be empty", state.validationErrors.isEmpty())
    }

    @Test
    fun `selectSymptomFromHistory should populate form with historical data`() = runTest {
        // Given - historical symptom data
        val historicalSymptom = createMockSymptomEvent(
            SymptomType.DIARRHEA,
            Instant.now().minus(1, ChronoUnit.DAYS),
            7,
            "45 minutes"
        )

        // When
        symptomEntryViewModel.selectSymptomFromHistory(historicalSymptom)

        // Then
        val state = symptomEntryViewModel.uiState.value
        assertEquals("Should use historical symptom type", SymptomType.DIARRHEA, state.selectedSymptomType)
        assertEquals("Should use historical severity", 7, state.severity)
        assertEquals("Should use historical duration", "45 minutes", state.duration)
    }

    @Test
    fun `bristolScale should be enabled for bowel movement symptoms`() = runTest {
        // When - select bowel movement symptom
        symptomEntryViewModel.selectSymptom(SymptomType.BOWEL_MOVEMENT)
        advanceUntilIdle()

        // Then
        val state = symptomEntryViewModel.uiState.value
        assertTrue("Bristol scale should be enabled", state.bristolScaleEnabled)
    }

    @Test
    fun `bristolScale should be disabled for non-bowel movement symptoms`() = runTest {
        // When - select non-bowel movement symptom
        symptomEntryViewModel.selectSymptom(SymptomType.HEADACHE)
        advanceUntilIdle()

        // Then
        val state = symptomEntryViewModel.uiState.value
        assertFalse("Bristol scale should be disabled", state.bristolScaleEnabled)
    }

    @Test
    fun `setBristolScale should update bristol scale value`() {
        // When
        symptomEntryViewModel.setBristolScale(BristolStoolType.TYPE_4)

        // Then
        val state = symptomEntryViewModel.uiState.value
        assertEquals("Bristol scale should be updated", BristolStoolType.TYPE_4, state.bristolScale)
    }

    @Test
    fun `getSeverityDescription should return appropriate description`() {
        // Test various severity levels
        assertEquals("Minimal", symptomEntryViewModel.getSeverityDescription(1))
        assertEquals("Mild", symptomEntryViewModel.getSeverityDescription(3))
        assertEquals("Moderate", symptomEntryViewModel.getSeverityDescription(5))
        assertEquals("Severe", symptomEntryViewModel.getSeverityDescription(8))
        assertEquals("Extreme", symptomEntryViewModel.getSeverityDescription(10))
    }

    @Test
    fun `getTimeSinceLastSymptom should calculate time since previous symptom`() = runTest {
        // Given - previous symptoms exist
        val previousSymptom = createMockSymptomEvent(
            SymptomType.BLOATING,
            Instant.now().minus(3, ChronoUnit.HOURS)
        )
        every { symptomEventRepository.getByType(SymptomType.BLOATING) } returns flowOf(listOf(previousSymptom))

        // When
        symptomEntryViewModel.selectSymptom(SymptomType.BLOATING)
        advanceUntilIdle()

        // Then
        val state = symptomEntryViewModel.uiState.value
        assertNotNull("Should calculate time since last symptom", state.timeSinceLastSymptom)
        assertTrue("Should indicate hours ago", state.timeSinceLastSymptom!!.contains("hours ago"))
    }

    // Helper methods for creating mock data
    private fun createMockSymptomFoodCorrelation(
        symptomType: SymptomType,
        foodName: String,
        strength: Float
    ): SymptomFoodCorrelation {
        return SymptomFoodCorrelation(
            symptomType = symptomType,
            foodName = foodName,
            correlationStrength = strength,
            occurrences = (strength * 10).toInt(),
            confidence = if (strength > 0.7f) AnalysisConfidence.HIGH else AnalysisConfidence.MEDIUM,
            averageTimeDifference = 120, // 2 hours
            lastOccurrence = Instant.now().minus(1, ChronoUnit.DAYS)
        )
    }

    private fun createMockTimeWindowAnalysis(
        foodName: String,
        peakHour: Int,
        totalSymptoms: Int
    ): TimeWindowAnalysis {
        return TimeWindowAnalysis(
            foodId = "food-$foodName",
            symptomOccurrences = mapOf(peakHour to emptyList()),
            peakHour = peakHour,
            totalSymptoms = totalSymptoms
        )
    }

    private fun createMockFoodEntry(
        name: String,
        timestamp: Instant
    ): FoodEntry {
        return FoodEntry(
            id = "entry-$name-${timestamp.epochSecond}",
            timestamp = timestamp,
            mealType = MealType.LUNCH,
            foods = listOf(name),
            portions = listOf("1 serving"),
            notes = null,
            isDeleted = false
        )
    }

    private fun createMockSymptomEvent(
        type: SymptomType,
        timestamp: Instant,
        severity: Int = 5,
        duration: String? = null
    ): SymptomEvent {
        return SymptomEvent(
            id = "symptom-${type}-${timestamp.epochSecond}",
            timestamp = timestamp,
            type = type,
            severity = severity,
            duration = duration,
            triggerFoodId = null,
            notes = null,
            isDeleted = false
        )
    }
}

// Data classes that SymptomEntryViewModel should use (these will need to be implemented)
data class SymptomEntryUiState(
    val selectedSymptomType: SymptomType? = null,
    val severity: Int = 1,
    val duration: String? = null,
    val notes: String = "",
    val timestamp: Instant = Instant.now(),
    val linkedFoodEntryId: String? = null,
    val bristolScale: BristolStoolType? = null,
    val bristolScaleEnabled: Boolean = false,
    val validationErrors: List<String> = emptyList(),
    val isSaving: Boolean = false,
    val isLoadingCorrelations: Boolean = true,
    val suggestedCorrelations: List<SymptomFoodCorrelation> = emptyList(),
    val realtimeCorrelations: List<RealtimeCorrelation> = emptyList(),
    val timeSinceLastSymptom: String? = null,
    val message: String? = null
)

data class SymptomFoodCorrelation(
    val symptomType: SymptomType,
    val foodName: String,
    val correlationStrength: Float,
    val occurrences: Int,
    val confidence: AnalysisConfidence,
    val averageTimeDifference: Int, // minutes
    val lastOccurrence: Instant
)

data class RealtimeCorrelation(
    val foodName: String,
    val timeSinceEaten: Int, // hours
    val correlationStrength: Float,
    val confidence: AnalysisConfidence
)