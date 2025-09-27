package com.fooddiary.integration

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.fooddiary.R
import com.fooddiary.data.database.FoodDiaryDatabase
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.models.MealType
import com.fooddiary.data.models.SymptomType
import com.fooddiary.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.*
import com.fooddiary.integration.TestMatchers.hasMinimumChildCount
import com.fooddiary.integration.TestMatchers.matchesPattern
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.Duration
import java.time.Instant
import javax.inject.Inject

/**
 * T054: UI test for symptom correlation workflow
 * Tests food entry → symptom correlation workflow including:
 * - Create food entry at time T
 * - Create symptom entry at time T+2hours
 * - Verify correlation is detected and displayed
 * - Test correlation time window settings
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SymptomCorrelationTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Inject
    lateinit var database: FoodDiaryDatabase

    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        hiltRule.inject()

        // Clear database before each test
        runBlocking {
            database.clearAllTables()
        }

        // Launch activity
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        // Clean up database after each test
        runBlocking {
            database.clearAllTables()
        }
        activityScenario.close()
    }

    @Test
    fun testBasicFoodSymptomCorrelationFlow() {
        val baseTime = Instant.now().minusSeconds(7200) // 2 hours ago

        // Step 1: Create food entry at time T
        createFoodEntryAtTime(baseTime)

        // Step 2: Create symptom entry at time T+2hours
        createSymptomEntryAtTime(
            baseTime.plusSeconds(7200), // 2 hours later
            SymptomType.BLOATING,
            severity = 6
        )

        // Step 3: Navigate to analytics to verify correlation
        navigateToAnalytics()

        // Step 4: Verify correlation is detected and displayed
        verifyCorrelationDisplayed()
    }

    @Test
    fun testMultipleFoodToSymptomCorrelations() {
        val baseTime = Instant.now().minusSeconds(10800) // 3 hours ago

        // Create multiple food entries with different foods
        createFoodEntryAtTime(baseTime, listOf("Milk", "Cheese"))
        createFoodEntryAtTime(baseTime.plusSeconds(1800), listOf("Bread", "Wheat"))

        // Create symptoms that correlate with the foods
        createSymptomEntryAtTime(
            baseTime.plusSeconds(5400), // 1.5 hours after first food
            SymptomType.BLOATING,
            severity = 7
        )

        createSymptomEntryAtTime(
            baseTime.plusSeconds(7200), // 2 hours after first food
            SymptomType.ABDOMINAL_PAIN,
            severity = 5
        )

        // Navigate to analytics
        navigateToAnalytics()

        // Verify multiple correlations are shown
        verifyMultipleCorrelationsDisplayed()
    }

    @Test
    fun testCorrelationTimeWindowSettings() {
        val baseTime = Instant.now().minusSeconds(14400) // 4 hours ago

        // Create food entry
        createFoodEntryAtTime(baseTime, listOf("Onion", "Garlic"))

        // Create symptom entry 4 hours later (outside typical window)
        createSymptomEntryAtTime(
            baseTime.plusSeconds(14400), // 4 hours later
            SymptomType.GAS,
            severity = 8
        )

        // Go to settings and modify correlation time window
        navigateToSettings()
        adjustCorrelationTimeWindow(6) // Extend to 6 hours

        // Navigate back to analytics
        navigateToAnalytics()

        // Verify correlation is now detected with extended window
        verifyCorrelationDisplayed()
    }

    @Test
    fun testSymptomEntryWithPotentialTriggers() {
        val baseTime = Instant.now().minusSeconds(3600) // 1 hour ago

        // Create food entry with potential trigger foods
        createFoodEntryAtTime(baseTime, listOf("Milk", "Ice cream"))

        // Navigate to symptom entry
        navigateToSymptomEntry()

        // Fill symptom details
        onView(withId(R.id.chip_bloating))
            .perform(click())

        // Set severity
        onView(withId(R.id.slider_severity))
            .perform(click()) // This may need more specific slider interaction

        // Verify potential triggers are displayed
        onView(withId(R.id.rv_potential_triggers))
            .check(matches(isDisplayed()))

        // Verify recent food entries appear as potential triggers
        onView(withText(containsString("Milk")))
            .check(matches(isDisplayed()))

        // Select a trigger food
        onView(withText(containsString("Milk")))
            .perform(click())

        // Save symptom entry
        onView(withId(R.id.btn_save))
            .perform(click())

        // Navigate to analytics to verify correlation
        navigateToAnalytics()
        verifyCorrelationDisplayed()
    }

    @Test
    fun testCorrelationWithDifferentSymptomTypes() {
        val baseTime = Instant.now().minusSeconds(5400) // 1.5 hours ago

        // Create food entry with FODMAP foods
        createFoodEntryAtTime(baseTime, listOf("Apple", "Pear", "Honey"))

        // Create multiple symptoms
        createSymptomEntryAtTime(
            baseTime.plusSeconds(3600), // 1 hour later
            SymptomType.BLOATING,
            severity = 6
        )

        createSymptomEntryAtTime(
            baseTime.plusSeconds(5400), // 1.5 hours later
            SymptomType.GAS,
            severity = 7
        )

        createSymptomEntryAtTime(
            baseTime.plusSeconds(7200), // 2 hours later
            SymptomType.DIARRHEA,
            severity = 5
        )

        // Navigate to analytics
        navigateToAnalytics()

        // Verify correlations with different symptom types
        verifyMultipleSymptomTypeCorrelations()
    }

    @Test
    fun testCorrelationStrengthCalculation() {
        val baseTime = Instant.now().minusSeconds(21600) // 6 hours ago

        // Create repeated patterns to establish strong correlation
        for (i in 0..2) {
            val entryTime = baseTime.plusSeconds(i * 86400L) // Daily entries
            createFoodEntryAtTime(entryTime, listOf("Dairy", "Lactose"))

            createSymptomEntryAtTime(
                entryTime.plusSeconds(7200), // 2 hours after each
                SymptomType.BLOATING,
                severity = 7 + i // Varying severity
            )
        }

        // Navigate to analytics
        navigateToAnalytics()

        // Verify strong correlation strength is displayed
        verifyCorrelationStrength()
    }

    @Test
    fun testNoCorrelationScenario() {
        val baseTime = Instant.now().minusSeconds(43200) // 12 hours ago

        // Create food entry
        createFoodEntryAtTime(baseTime, listOf("Rice", "Chicken"))

        // Create symptom entry far outside correlation window
        createSymptomEntryAtTime(
            baseTime.plusSeconds(43200), // 12 hours later
            SymptomType.HEADACHE,
            severity = 4
        )

        // Navigate to analytics
        navigateToAnalytics()

        // Verify no correlation is shown
        verifyNoCorrelationDisplayed()
    }

    // Helper methods

    private fun createFoodEntryAtTime(timestamp: Instant, foods: List<String> = listOf("Test Food")) {
        runBlocking {
            val foodEntry = FoodEntry.create(
                foods = foods,
                portions = foods.associateWith { "1 serving" },
                mealType = MealType.LUNCH,
                timestamp = timestamp,
                notes = "Test entry for correlation"
            )
            database.foodEntryDao().insert(foodEntry)
        }
    }

    private fun createSymptomEntryAtTime(
        timestamp: Instant,
        symptomType: SymptomType,
        severity: Int
    ) {
        runBlocking {
            val symptomEvent = SymptomEvent.create(
                symptomType = symptomType,
                severity = severity,
                timestamp = timestamp,
                duration = Duration.ofMinutes(30),
                notes = "Test symptom for correlation"
            )
            database.symptomEventDao().insert(symptomEvent)
        }
    }

    private fun navigateToAnalytics() {
        onView(withId(R.id.navigation_analysis))
            .perform(click())

        // Verify we're on the analytics screen
        onView(withId(R.id.tv_total_entries_count))
            .check(matches(isDisplayed()))
    }

    private fun navigateToSymptomEntry() {
        onView(withId(R.id.navigation_entry))
            .perform(click())

        // Navigate specifically to symptom entry
        onView(withId(R.id.navigation_symptom_entry))
            .perform(click())

        // Verify we're on the symptom entry screen
        onView(withId(R.id.chip_group_symptom_type))
            .check(matches(isDisplayed()))
    }

    private fun navigateToSettings() {
        onView(withId(R.id.navigation_settings))
            .perform(click())

        // Verify we're on the settings screen
        onView(withText(containsString("Settings")))
            .check(matches(isDisplayed()))
    }

    private fun adjustCorrelationTimeWindow(hours: Int) {
        // Find and adjust correlation time window setting
        onView(withText(containsString("Correlation Window")))
            .perform(click())

        // This would interact with a time picker or slider
        // Implementation specific to the settings UI
        onView(withText("$hours hours"))
            .perform(click())

        // Save settings
        onView(withText("Save"))
            .perform(click())
    }

    private fun verifyCorrelationDisplayed() {
        // Check that correlation analysis shows results
        onView(withId(R.id.rv_top_triggers))
            .check(matches(isDisplayed()))

        // Verify at least one correlation item is shown
        onView(withId(R.id.rv_top_triggers))
            .check(matches(hasMinimumChildCount(1)))

        // Check for correlation strength indicator
        onView(allOf(
            withText(containsString("%")),
            hasAncestor(withId(R.id.rv_top_triggers))
        )).check(matches(isDisplayed()))
    }

    private fun verifyMultipleCorrelationsDisplayed() {
        // Verify multiple trigger foods are shown
        onView(withId(R.id.rv_top_triggers))
            .check(matches(hasMinimumChildCount(2)))

        // Check for different food items in triggers list
        onView(withText(containsString("Milk")))
            .check(matches(isDisplayed()))

        onView(withText(containsString("Bread")))
            .check(matches(isDisplayed()))
    }

    private fun verifyMultipleSymptomTypeCorrelations() {
        // Verify different symptom types are correlated
        onView(withText(containsString("Bloating")))
            .check(matches(isDisplayed()))

        onView(withText(containsString("Gas")))
            .check(matches(isDisplayed()))

        onView(withText(containsString("Diarrhea")))
            .check(matches(isDisplayed()))
    }

    private fun verifyCorrelationStrength() {
        // Check for high correlation percentage
        onView(allOf(
            withText(containsString("80%")), // High correlation
            hasAncestor(withId(R.id.rv_top_triggers))
        )).check(matches(isDisplayed()))

        // Verify confidence level indicator
        onView(withText(containsString("High Confidence")))
            .check(matches(isDisplayed()))
    }

    private fun verifyNoCorrelationDisplayed() {
        // Check for empty state or "no correlations" message
        onView(anyOf(
            withText(containsString("No correlations found")),
            withText(containsString("No patterns detected")),
            withId(R.id.empty_triggers)
        )).check(matches(isDisplayed()))
    }
}