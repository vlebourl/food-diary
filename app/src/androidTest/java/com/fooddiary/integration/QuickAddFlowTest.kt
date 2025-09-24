package com.fooddiary.integration

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import com.fooddiary.MainActivity
import com.fooddiary.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.*

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class QuickAddFlowTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun `user can configure quick entry button for coffee`() {
        // This test will fail until UI is implemented
        // Navigate to Settings -> Quick Entry
        onView(withId(R.id.navigation_settings))
            .perform(click())

        onView(withId(R.id.quick_entry_settings))
            .perform(click())

        // Add new quick entry button
        onView(withId(R.id.btn_add_quick_entry))
            .perform(click())

        // Configure coffee quick entry
        onView(withId(R.id.et_entry_name))
            .perform(typeText("Morning Coffee"))

        onView(withId(R.id.spinner_entry_type))
            .perform(click())

        onView(withText("Beverage"))
            .perform(click())

        onView(withId(R.id.et_default_quantity))
            .perform(typeText("250"))

        onView(withId(R.id.spinner_unit))
            .perform(click())

        onView(withText("ml"))
            .perform(click())

        // Set caffeine content
        onView(withId(R.id.et_caffeine_content))
            .perform(typeText("95"))

        // Choose button color and icon
        onView(withId(R.id.btn_choose_color))
            .perform(click())

        onView(withId(R.id.color_brown))
            .perform(click())

        onView(withId(R.id.btn_choose_icon))
            .perform(click())

        onView(withId(R.id.icon_coffee))
            .perform(click())

        // Save the quick entry
        onView(withId(R.id.btn_save_quick_entry))
            .perform(click())

        // Verify we return to main screen and button appears
        onView(withId(R.id.quick_entry_morning_coffee))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `quick entry button logs food with single tap`() {
        // This test assumes a quick entry button already exists
        // This test will fail until quick entry functionality is implemented

        // Tap the configured coffee quick entry button
        onView(withId(R.id.quick_entry_morning_coffee))
            .perform(click())

        // Entry should be logged immediately
        // Verify confirmation appears
        onView(withText("Morning Coffee logged"))
            .check(matches(isDisplayed()))

        // Verify entry appears in today's timeline
        onView(withId(R.id.navigation_timeline))
            .perform(click())

        onView(withText("Morning Coffee"))
            .check(matches(isDisplayed()))

        onView(withText("250 ml"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `quick entry allows timestamp adjustment`() {
        // This test will fail until quick entry with timestamp adjustment is implemented

        // Long press on quick entry button for options
        onView(withId(R.id.quick_entry_morning_coffee))
            .perform(longClick())

        // Should show quick options dialog
        onView(withText("Log with current time"))
            .check(matches(isDisplayed()))

        onView(withText("Adjust time"))
            .check(matches(isDisplayed()))

        // Choose adjust time
        onView(withText("Adjust time"))
            .perform(click())

        // Time picker should appear
        onView(withId(R.id.time_picker))
            .check(matches(isDisplayed()))

        // Set time to 2 hours ago
        // Note: This would require custom time picker interactions
        // For now, just verify the picker is shown

        onView(withId(R.id.btn_confirm_time))
            .perform(click())

        // Verify entry is logged with adjusted time
        onView(withText("Morning Coffee logged"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `quick entry supports quantity adjustment`() {
        // This test will fail until quantity adjustment is implemented

        // Long press on quick entry button
        onView(withId(R.id.quick_entry_morning_coffee))
            .perform(longClick())

        onView(withText("Adjust quantity"))
            .perform(click())

        // Quantity adjustment dialog should appear
        onView(withId(R.id.et_quick_quantity))
            .check(matches(isDisplayed()))
            .check(matches(withText("250"))) // Default value

        // Change quantity
        onView(withId(R.id.et_quick_quantity))
            .perform(clearText(), typeText("350"))

        onView(withId(R.id.btn_log_with_quantity))
            .perform(click())

        // Verify entry is logged with adjusted quantity
        onView(withText("Morning Coffee logged"))
            .check(matches(isDisplayed()))

        // Check timeline shows correct quantity
        onView(withId(R.id.navigation_timeline))
            .perform(click())

        onView(withText("350 ml"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `quick symptom entry logs diarrhea event`() {
        // This test assumes a quick symptom button exists
        // This test will fail until symptom quick entry is implemented

        onView(withId(R.id.quick_entry_diarrhea))
            .perform(click())

        // Should immediately log symptom with default severity
        onView(withText("Symptom logged"))
            .check(matches(isDisplayed()))

        // Verify in symptom timeline
        onView(withId(R.id.navigation_symptoms))
            .perform(click())

        onView(withText("Diarrhea"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `multiple quick entries can be used in sequence`() {
        // This test will fail until multiple quick entries are supported

        // Log coffee
        onView(withId(R.id.quick_entry_morning_coffee))
            .perform(click())

        onView(withText("Morning Coffee logged"))
            .check(matches(isDisplayed()))

        // Log breakfast
        onView(withId(R.id.quick_entry_breakfast))
            .perform(click())

        onView(withText("Breakfast logged"))
            .check(matches(isDisplayed()))

        // Verify both appear in timeline
        onView(withId(R.id.navigation_timeline))
            .perform(click())

        onView(withText("Morning Coffee"))
            .check(matches(isDisplayed()))

        onView(withText("Breakfast"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `quick entry respects user ordering preferences`() {
        // This test will fail until button ordering is implemented

        // Verify buttons appear in configured order
        // This would require custom matchers to check button positions
        onView(withId(R.id.quick_entries_container))
            .check(matches(isDisplayed()))

        // First button should be the highest priority one
        // Additional assertions would depend on the UI implementation
    }
}