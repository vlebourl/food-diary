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
class PatternAnalysisTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun `pattern analysis shows correlations with minimum 10 data points`() {
        // This test will fail until pattern analysis is implemented
        // Navigate to analysis section
        onView(withId(R.id.navigation_analysis))
            .perform(click())

        onView(withId(R.id.tab_patterns))
            .perform(click())

        // Should show message about insufficient data initially
        onView(withText("Need at least 10 food-symptom pairs for analysis"))
            .check(matches(isDisplayed()))

        // After sufficient data is logged (would require test data setup)
        // Should show significant correlations (p<0.05, correlation ≥0.6)

        // Mock sufficient data scenario
        onView(withId(R.id.btn_load_test_data))
            .perform(click())

        // Wait for analysis to complete
        Thread.sleep(2000)

        // Should show correlation results
        onView(withText("Food-Symptom Correlations"))
            .check(matches(isDisplayed()))

        // Should show statistically significant patterns
        onView(withText("High confidence patterns"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `correlation analysis calculates time delays between food and symptoms`() {
        // This test will fail until time window analysis is implemented

        onView(withId(R.id.navigation_analysis))
            .perform(click())

        onView(withId(R.id.tab_patterns))
            .perform(click())

        // Load test data with known time delays
        onView(withId(R.id.btn_load_time_delay_data))
            .perform(click())

        // Select a specific food correlation
        onView(withText("Dairy Products"))
            .perform(click())

        // Should show time offset analysis
        onView(withText("Time to symptom onset"))
            .check(matches(isDisplayed()))

        // Should show average delay (e.g., "2 hours 15 minutes")
        onView(withText("Average delay: 2h 15m"))
            .check(matches(isDisplayed()))

        // Should show time window chart
        onView(withId(R.id.time_window_chart))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `FODMAP analysis categorizes food items correctly`() {
        // This test will fail until FODMAP analysis is implemented

        onView(withId(R.id.navigation_analysis))
            .perform(click())

        onView(withId(R.id.tab_fodmap))
            .perform(click())

        // Add high FODMAP food for testing
        onView(withId(R.id.btn_analyze_meal))
            .perform(click())

        onView(withId(R.id.et_ingredients))
            .perform(typeText("onion, garlic, apple"))

        onView(withId(R.id.btn_analyze))
            .perform(click())

        // Should show FODMAP breakdown
        onView(withText("FODMAP Analysis"))
            .check(matches(isDisplayed()))

        onView(withText("Overall Level: HIGH"))
            .check(matches(isDisplayed()))

        // Should show category breakdown
        onView(withText("Oligosaccharides: HIGH"))
            .check(matches(isDisplayed()))

        onView(withText("Problematic ingredients: onion, garlic"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `trigger pattern identification shows confidence levels`() {
        // This test will fail until trigger pattern analysis is implemented

        onView(withId(R.id.navigation_analysis))
            .perform(click())

        onView(withId(R.id.tab_triggers))
            .perform(click())

        // Load test data with clear trigger patterns
        onView(withId(R.id.btn_load_trigger_data))
            .perform(click())

        // Should show trigger patterns with confidence levels
        onView(withText("Identified Triggers"))
            .check(matches(isDisplayed()))

        // Should show high confidence triggers (≥70%)
        onView(withText("High Confidence (≥70%)"))
            .check(matches(isDisplayed()))

        onView(withText("Dairy → Bloating (85% confidence)"))
            .check(matches(isDisplayed()))

        // Should show medium confidence triggers
        onView(withText("Medium Confidence (50-69%)"))
            .check(matches(isDisplayed()))

        // Should allow drilling down into specific triggers
        onView(withText("Dairy → Bloating (85% confidence)"))
            .perform(click())

        // Should show detailed analysis
        onView(withText("12 occurrences"))
            .check(matches(isDisplayed()))

        onView(withText("Average onset: 1h 45m"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `cumulative effect analysis detects multiple trigger combinations`() {
        // This test will fail until cumulative analysis is implemented

        onView(withId(R.id.navigation_analysis))
            .perform(click())

        onView(withId(R.id.tab_cumulative))
            .perform(click())

        // Load data with cumulative trigger effects
        onView(withId(R.id.btn_load_cumulative_data))
            .perform(click())

        // Should show combination patterns
        onView(withText("Combination Effects"))
            .check(matches(isDisplayed()))

        onView(withText("Dairy + Wheat → Severe symptoms"))
            .check(matches(isDisplayed()))

        onView(withText("Effect strength: 92%"))
            .check(matches(isDisplayed()))

        // Should show timing analysis for combinations
        onView(withText("Combined onset: 3-6 hours"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `symptom-free period analysis identifies contributing factors`() {
        // This test will fail until wellness tracking is implemented

        onView(withId(R.id.navigation_analysis))
            .perform(click())

        onView(withId(R.id.tab_wellness))
            .perform(click())

        // Should show symptom-free streaks
        onView(withText("Wellness Streaks"))
            .check(matches(isDisplayed()))

        onView(withText("Current streak: 3 days"))
            .check(matches(isDisplayed()))

        onView(withText("Longest streak: 12 days"))
            .check(matches(isDisplayed()))

        // Should show factors during good periods
        onView(withText("Factors during wellness periods:"))
            .check(matches(isDisplayed()))

        onView(withText("• Avoided dairy"))
            .check(matches(isDisplayed()))

        onView(withText("• Low stress levels"))
            .check(matches(isDisplayed()))

        onView(withText("• Regular sleep schedule"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `analysis supports different time windows`() {
        // This test will fail until time window selection is implemented

        onView(withId(R.id.navigation_analysis))
            .perform(click())

        // Select different analysis periods
        onView(withId(R.id.spinner_time_period))
            .perform(click())

        onView(withText("Last 7 days"))
            .perform(click())

        // Should update analysis for selected period
        onView(withText("Analysis for last 7 days"))
            .check(matches(isDisplayed()))

        // Change to monthly view
        onView(withId(R.id.spinner_time_period))
            .perform(click())

        onView(withText("Last 30 days"))
            .perform(click())

        onView(withText("Analysis for last 30 days"))
            .check(matches(isDisplayed()))

        // Should show different results based on time window
        onView(withId(R.id.analysis_results))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `pattern analysis requires statistical significance`() {
        // This test will fail until statistical validation is implemented

        onView(withId(R.id.navigation_analysis))
            .perform(click())

        onView(withId(R.id.tab_statistics))
            .perform(click())

        // Should show statistical requirements
        onView(withText("Statistical Requirements"))
            .check(matches(isDisplayed()))

        onView(withText("Minimum correlation: 0.6"))
            .check(matches(isDisplayed()))

        onView(withText("Confidence level: 95%"))
            .check(matches(isDisplayed()))

        onView(withText("P-value threshold: 0.05"))
            .check(matches(isDisplayed()))

        // Should only show patterns meeting these criteria
        onView(withText("Statistically significant patterns:"))
            .check(matches(isDisplayed()))

        // Should filter out weak correlations
        onView(withText("(Patterns below 60% correlation are not shown)"))
            .check(matches(isDisplayed()))
    }
}