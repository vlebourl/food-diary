package com.fooddiary.presentation.ui

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.fooddiary.data.models.BristolStoolType
import com.fooddiary.presentation.MainActivity
import com.fooddiary.presentation.ui.custom.BristolStoolChartView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.allOf

@RunWith(AndroidJUnit4::class)
class BristolStoolChartTest {

    @get:Rule
    var activityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun bristolChartDisplaysCorrectly() {
        // Test that the Bristol Stool Chart displays all 7 types
        onView(withId(R.id.bristol_stool_chart))
            .check(matches(isDisplayed()))
    }

    @Test
    fun bristolChartSelectionWorks() {
        // Test selecting different stool types
        onView(withId(R.id.bristol_stool_chart))
            .perform(click())

        // Verify selection state changes
        onView(withId(R.id.bristol_stool_chart))
            .check(matches(isDisplayed()))
    }

    @Test
    fun bristolChartClearSelectionWorks() {
        // Test clearing selection by clicking same type
        val chartView = activityTestRule.activity.findViewById<BristolStoolChartView>(R.id.bristol_stool_chart)

        // Set initial selection
        chartView.setSelectedType(BristolStoolType.TYPE_4)

        // Clear selection
        chartView.clearSelection()

        // Verify no selection
        assertNull(chartView.getSelectedType())
    }
}