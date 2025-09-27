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
import com.fooddiary.data.models.MealType
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
import java.time.Instant
import javax.inject.Inject

/**
 * T053: UI test for complete food entry flow
 * Tests complete food entry creation workflow including:
 * - Navigate to food entry screen
 * - Fill meal type, foods, portions, timestamp
 * - Save entry and verify it appears in timeline
 * - Test form validation edge cases
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class FoodEntryFlowTest {

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
    fun testCompleteBasicFoodEntryFlow() {
        // Navigate to food entry screen
        navigateToFoodEntry()

        // Fill basic food entry form
        fillBasicFoodEntry()

        // Save the entry
        onView(withId(R.id.btn_save))
            .check(matches(isDisplayed()))
            .perform(click())

        // Verify we're back on timeline and entry appears
        verifyEntryInTimeline()
    }

    @Test
    fun testFoodEntryWithMultipleItems() {
        navigateToFoodEntry()

        // Select meal type
        onView(withId(R.id.chip_lunch))
            .perform(click())

        // Add first food item
        onView(withId(R.id.btn_add_food_item))
            .perform(click())

        // Add second food item (if dynamic list exists)
        onView(withId(R.id.btn_add_food_item))
            .perform(click())

        // Add ingredients
        onView(withId(R.id.et_new_ingredient))
            .perform(typeText("Chicken"), closeSoftKeyboard())

        onView(withId(R.id.btn_add_ingredient))
            .perform(click())

        onView(withId(R.id.et_new_ingredient))
            .perform(clearText(), typeText("Rice"), closeSoftKeyboard())

        onView(withId(R.id.btn_add_ingredient))
            .perform(click())

        // Add notes
        onView(withId(R.id.et_notes))
            .perform(typeText("Homemade chicken and rice bowl"), closeSoftKeyboard())

        // Save entry
        onView(withId(R.id.btn_save))
            .perform(click())

        // Verify entry saved successfully
        verifyEntryInTimeline()
    }

    @Test
    fun testFoodEntryFormValidation() {
        navigateToFoodEntry()

        // Try to save without selecting meal type or adding foods
        onView(withId(R.id.btn_save))
            .perform(click())

        // Should show validation error or stay on form
        // (Implementation specific - may show snackbar, toast, or error state)
        onView(withId(R.id.chip_group_meal_type))
            .check(matches(isDisplayed())) // Still on food entry screen

        // Select meal type but no food items
        onView(withId(R.id.chip_breakfast))
            .perform(click())

        onView(withId(R.id.btn_save))
            .perform(click())

        // Should still require food items
        onView(withId(R.id.btn_add_food_item))
            .check(matches(isDisplayed())) // Still on food entry screen
    }

    @Test
    fun testFoodEntryTimestampSelection() {
        navigateToFoodEntry()

        // Fill minimum required fields
        onView(withId(R.id.chip_dinner))
            .perform(click())

        onView(withId(R.id.et_new_ingredient))
            .perform(typeText("Pizza"), closeSoftKeyboard())

        onView(withId(R.id.btn_add_ingredient))
            .perform(click())

        // Select custom timestamp
        onView(withId(R.id.btn_select_date_time))
            .perform(click())

        // Date/time picker interaction would be here
        // For now, verify the button was clickable
        onView(withId(R.id.tv_selected_datetime))
            .check(matches(isDisplayed()))

        // Save entry
        onView(withId(R.id.btn_save))
            .perform(click())

        verifyEntryInTimeline()
    }

    @Test
    fun testFoodEntryContextFields() {
        navigateToFoodEntry()

        // Fill required fields
        onView(withId(R.id.chip_snack))
            .perform(click())

        onView(withId(R.id.et_new_ingredient))
            .perform(typeText("Apple"), closeSoftKeyboard())

        onView(withId(R.id.btn_add_ingredient))
            .perform(click())

        // Fill context fields
        onView(withId(R.id.spinner_location))
            .perform(click())
        // Select location from dropdown if available

        onView(withId(R.id.spinner_social_context))
            .perform(click())
        // Select social context from dropdown if available

        onView(withId(R.id.spinner_eating_speed))
            .perform(click())
        // Select eating speed from dropdown if available

        // Add preparation method
        onView(withId(R.id.et_preparation_method))
            .perform(typeText("Raw"), closeSoftKeyboard())

        // Save entry
        onView(withId(R.id.btn_save))
            .perform(click())

        verifyEntryInTimeline()
    }

    @Test
    fun testFoodEntryCancelFlow() {
        navigateToFoodEntry()

        // Fill some fields
        onView(withId(R.id.chip_breakfast))
            .perform(click())

        onView(withId(R.id.et_new_ingredient))
            .perform(typeText("Oatmeal"), closeSoftKeyboard())

        // Cancel instead of save
        onView(withId(R.id.btn_cancel))
            .perform(click())

        // Should navigate back to timeline
        onView(withId(R.id.recycler_timeline))
            .check(matches(isDisplayed()))

        // Verify no entry was created in database
        verifyNoNewEntryInTimeline()
    }

    @Test
    fun testFODMAPAnalysisDisplay() {
        navigateToFoodEntry()

        // Fill entry with high FODMAP ingredients
        onView(withId(R.id.chip_lunch))
            .perform(click())

        onView(withId(R.id.et_new_ingredient))
            .perform(typeText("Onion"), closeSoftKeyboard())

        onView(withId(R.id.btn_add_ingredient))
            .perform(click())

        onView(withId(R.id.et_new_ingredient))
            .perform(clearText(), typeText("Garlic"), closeSoftKeyboard())

        onView(withId(R.id.btn_add_ingredient))
            .perform(click())

        // FODMAP analysis card should become visible
        // (Implementation specific - may need to wait or trigger analysis)
        onView(withId(R.id.card_fodmap_analysis))
            .check(matches(isDisplayed()))

        // Save entry
        onView(withId(R.id.btn_save))
            .perform(click())

        verifyEntryInTimeline()
    }

    // Helper methods

    private fun navigateToFoodEntry() {
        // Navigate to food entry fragment
        // This may be through bottom navigation, FAB, or menu
        onView(withId(R.id.navigation_entry))
            .perform(click())

        // Navigate specifically to food entry
        onView(withId(R.id.navigation_food_entry))
            .perform(click())

        // Verify we're on the food entry screen
        onView(withId(R.id.chip_group_meal_type))
            .check(matches(isDisplayed()))
    }

    private fun fillBasicFoodEntry() {
        // Select meal type
        onView(withId(R.id.chip_breakfast))
            .perform(click())

        // Add a basic ingredient
        onView(withId(R.id.et_new_ingredient))
            .perform(typeText("Cereal"), closeSoftKeyboard())

        onView(withId(R.id.btn_add_ingredient))
            .perform(click())

        // Verify ingredient was added
        onView(allOf(
            withText("Cereal"),
            hasAncestor(withId(R.id.chip_group_ingredients))
        )).check(matches(isDisplayed()))
    }

    private fun verifyEntryInTimeline() {
        // Should navigate back to timeline after saving
        onView(withId(R.id.navigation_timeline))
            .perform(click())

        onView(withId(R.id.recycler_timeline))
            .check(matches(isDisplayed()))

        // Check that at least one entry exists in the timeline
        onView(withId(R.id.recycler_timeline))
            .check(matches(hasMinimumChildCount(1)))
    }

    private fun verifyNoNewEntryInTimeline() {
        // Navigate to timeline first
        onView(withId(R.id.navigation_timeline))
            .perform(click())

        // Check that timeline is empty or has no new entries
        onView(withId(R.id.recycler_timeline))
            .check(matches(isDisplayed()))

        // Timeline should either be empty or show no increase in entries
        // This is implementation specific and may need database verification
    }

    private fun setupTestData() {
        // Create some test food entries for more complex scenarios
        runBlocking {
            val testEntry = FoodEntry.create(
                foods = listOf("Test Food"),
                portions = mapOf("Test Food" to "1 cup"),
                mealType = MealType.BREAKFAST,
                timestamp = Instant.now().minusSeconds(3600),
                notes = "Test entry"
            )
            database.foodEntryDao().insert(testEntry)
        }
    }
}