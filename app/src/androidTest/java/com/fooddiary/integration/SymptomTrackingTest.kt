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

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SymptomTrackingTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun `user can log symptom with severity scale 1-10`() {
        // This test will fail until symptom tracking UI is implemented

        // Navigate to symptom tracking
        onView(withId(R.id.btn_add_symptom))
            .perform(click())

        // Select symptom type
        onView(withId(R.id.spinner_symptom_type))
            .perform(click())

        onView(withText("Bloating"))
            .perform(click())

        // Set severity using 1-10 scale
        onView(withId(R.id.severity_slider))
            .perform(click())

        // Set severity to 6
        onView(withId(R.id.severity_6))
            .perform(click())

        // Verify severity is displayed
        onView(withText("6/10"))
            .check(matches(isDisplayed()))

        // Add notes
        onView(withId(R.id.et_symptom_notes))
            .perform(typeText("Moderate bloating after lunch"))

        // Save symptom
        onView(withId(R.id.btn_save_symptom))
            .perform(click())

        // Verify symptom is logged
        onView(withText("Symptom logged"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `user can log bowel movement with Bristol Stool Chart`() {
        // This test will fail until Bristol Stool Chart is implemented

        onView(withId(R.id.btn_add_symptom))
            .perform(click())

        // Select bowel movement
        onView(withId(R.id.spinner_symptom_type))
            .perform(click())

        onView(withText("Bowel Movement"))
            .perform(click())

        // Bristol Stool Chart should appear
        onView(withId(R.id.bristol_stool_chart))
            .check(matches(isDisplayed()))

        // Select Type 4 (ideal)
        onView(withId(R.id.bristol_type_4))
            .perform(click())

        // Verify selection
        onView(withText("Type 4: Like a sausage or snake, smooth and soft"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btn_save_symptom))
            .perform(click())

        onView(withText("Symptom logged"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `user can set symptom duration for ongoing issues`() {
        // This test will fail until duration tracking is implemented

        onView(withId(R.id.btn_add_symptom))
            .perform(click())

        onView(withId(R.id.spinner_symptom_type))
            .perform(click())

        onView(withText("Abdominal Pain"))
            .perform(click())

        // Set severity
        onView(withId(R.id.severity_7))
            .perform(click())

        // Set duration
        onView(withId(R.id.btn_set_duration))
            .perform(click())

        // Duration picker should appear
        onView(withId(R.id.duration_hours))
            .perform(typeText("2"))

        onView(withId(R.id.duration_minutes))
            .perform(typeText("30"))

        onView(withId(R.id.btn_confirm_duration))
            .perform(click())

        // Verify duration is displayed
        onView(withText("Duration: 2h 30m"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btn_save_symptom))
            .perform(click())
    }

    @Test
    fun `user can specify suspected food triggers`() {
        // This test will fail until trigger selection is implemented

        onView(withId(R.id.btn_add_symptom))
            .perform(click())

        onView(withId(R.id.spinner_symptom_type))
            .perform(click())

        onView(withText("Gas"))
            .perform(click())

        onView(withId(R.id.severity_4))
            .perform(click())

        // Add suspected triggers
        onView(withId(R.id.btn_add_suspected_trigger))
            .perform(click())

        // Should show recent food entries
        onView(withText("Recent Foods"))
            .check(matches(isDisplayed()))

        // Select dairy from recent foods
        onView(withText("Milk"))
            .perform(click())

        // Add custom trigger
        onView(withId(R.id.btn_add_custom_trigger))
            .perform(click())

        onView(withId(R.id.et_custom_trigger))
            .perform(typeText("beans"))

        onView(withId(R.id.btn_add_trigger))
            .perform(click())

        // Verify triggers are displayed
        onView(withText("Suspected triggers: Milk, beans"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btn_save_symptom))
            .perform(click())
    }

    @Test
    fun `user can photo document symptoms`() {
        // This test will fail until photo attachment is implemented

        onView(withId(R.id.btn_add_symptom))
            .perform(click())

        onView(withId(R.id.spinner_symptom_type))
            .perform(click())

        onView(withText("Skin Reaction"))
            .perform(click())

        onView(withId(R.id.severity_5))
            .perform(click())

        // Add photo documentation
        onView(withId(R.id.btn_add_photo))
            .perform(click())

        // Should show camera/gallery options
        onView(withText("Take Photo"))
            .check(matches(isDisplayed()))

        onView(withText("Choose from Gallery"))
            .check(matches(isDisplayed()))

        // Select take photo (this would launch camera in real scenario)
        onView(withText("Take Photo"))
            .perform(click())

        // Verify photo placeholder appears
        onView(withId(R.id.symptom_photo_preview))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btn_save_symptom))
            .perform(click())
    }

    @Test
    fun `user can edit symptom location or body area`() {
        // This test will fail until body location tracking is implemented

        onView(withId(R.id.btn_add_symptom))
            .perform(click())

        onView(withId(R.id.spinner_symptom_type))
            .perform(click())

        onView(withText("Pain"))
            .perform(click())

        onView(withId(R.id.severity_8))
            .perform(click())

        // Set body location
        onView(withId(R.id.btn_set_location))
            .perform(click())

        // Body diagram or location picker should appear
        onView(withId(R.id.body_location_picker))
            .check(matches(isDisplayed()))

        onView(withText("Lower abdomen"))
            .perform(click())

        onView(withText("Location: Lower abdomen"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btn_save_symptom))
            .perform(click())
    }

    @Test
    fun `symptom timeline shows chronological order`() {
        // This test will fail until symptom timeline is implemented

        // Log multiple symptoms with different timestamps
        // First symptom
        onView(withId(R.id.btn_add_symptom))
            .perform(click())

        onView(withId(R.id.spinner_symptom_type))
            .perform(click())

        onView(withText("Bloating"))
            .perform(click())

        onView(withId(R.id.severity_5))
            .perform(click())

        onView(withId(R.id.btn_save_symptom))
            .perform(click())

        // Second symptom (adjust timestamp to be later)
        onView(withId(R.id.btn_add_symptom))
            .perform(click())

        onView(withId(R.id.btn_adjust_time))
            .perform(click())

        // Set time 2 hours later
        onView(withId(R.id.btn_add_hours))
            .perform(click(), click()) // Add 2 hours

        onView(withId(R.id.btn_confirm_time))
            .perform(click())

        onView(withId(R.id.spinner_symptom_type))
            .perform(click())

        onView(withText("Gas"))
            .perform(click())

        onView(withId(R.id.severity_3))
            .perform(click())

        onView(withId(R.id.btn_save_symptom))
            .perform(click())

        // View symptom timeline
        onView(withId(R.id.navigation_symptoms))
            .perform(click())

        // Verify chronological order (newest first)
        // This would require custom matchers to verify order
        onView(withText("Gas"))
            .check(matches(isDisplayed()))

        onView(withText("Bloating"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `default symptom baseline is maintained when no symptoms`() {
        // This test will fail until baseline tracking is implemented

        // Navigate to today's summary
        onView(withId(R.id.navigation_analysis))
            .perform(click())

        onView(withId(R.id.tab_today))
            .perform(click())

        // Should show "No symptoms" or wellness indicator
        onView(withText("Feeling well"))
            .check(matches(isDisplayed()))

        // Wellness streak should be displayed
        onView(withText("Symptom-free streak: 1 day"))
            .check(matches(isDisplayed()))
    }
}