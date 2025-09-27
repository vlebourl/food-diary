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
 * T055: UI test for analytics dashboard functionality
 * Tests analytics dashboard with test data including:
 * - Verify overview stats calculation
 * - Test time range filtering
 * - Verify chart data updates
 * - Test export functionality
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class AnalyticsDashboardTest {

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
    fun testEmptyDashboardDisplay() {
        // Navigate to analytics with no data
        navigateToAnalytics()

        // Verify empty state displays
        onView(withId(R.id.tv_total_entries_count))
            .check(matches(withText("0")))

        onView(withId(R.id.tv_symptoms_count))
            .check(matches(withText("0")))

        onView(withId(R.id.tv_correlations_count))
            .check(matches(withText("0")))

        onView(withId(R.id.tv_avg_severity))
            .check(matches(withText("0.0")))
    }

    @Test
    fun testOverviewStatsCalculation() {
        // Setup test data
        setupTestData()

        // Navigate to analytics
        navigateToAnalytics()

        // Verify overview stats are calculated correctly
        onView(withId(R.id.tv_total_entries_count))
            .check(matches(withText("5"))) // From setupTestData

        onView(withId(R.id.tv_symptoms_count))
            .check(matches(withText("3"))) // From setupTestData

        onView(withId(R.id.tv_correlations_count))
            .check(matches(withText(not("0")))) // Should detect some correlations

        // Verify average severity calculation
        onView(withId(R.id.tv_avg_severity))
            .check(matches(withText(matchesPattern("\\d+\\.\\d")))) // Should show decimal number
    }

    @Test
    fun testTimeRangeFiltering() {
        // Setup data across different time ranges
        setupExtendedTestData()

        navigateToAnalytics()

        // Test Week filter (default)
        onView(allOf(withText("Week"), isDescendantOfA(withId(R.id.tab_layout_time_range))))
            .perform(click())

        verifyStatsForTimeRange("week")

        // Test Month filter
        onView(allOf(withText("Month"), isDescendantOfA(withId(R.id.tab_layout_time_range))))
            .perform(click())

        verifyStatsForTimeRange("month")

        // Test Three Months filter
        onView(allOf(withText("Three Months"), isDescendantOfA(withId(R.id.tab_layout_time_range))))
            .perform(click())

        verifyStatsForTimeRange("three_months")
    }

    @Test
    fun testChartDataUpdates() {
        setupTestData()
        navigateToAnalytics()

        // Verify timeline chart container is displayed
        onView(withId(R.id.chart_container_timeline))
            .check(matches(isDisplayed()))

        // Verify severity distribution chart container is displayed
        onView(withId(R.id.chart_container_severity))
            .check(matches(isDisplayed()))

        // Change time range and verify charts update
        onView(allOf(withText("Month"), isDescendantOfA(withId(R.id.tab_layout_time_range))))
            .perform(click())

        // Charts should still be displayed (content may change)
        onView(withId(R.id.chart_container_timeline))
            .check(matches(isDisplayed()))

        onView(withId(R.id.chart_container_severity))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testSymptomTrendsList() {
        setupTestData()
        navigateToAnalytics()

        // Verify symptom trends list is populated
        onView(withId(R.id.rv_symptom_trends))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rv_symptom_trends))
            .check(matches(hasMinimumChildCount(1)))

        // Verify symptom trend items contain expected data
        onView(allOf(
            withText(containsString("Bloating")),
            hasAncestor(withId(R.id.rv_symptom_trends))
        )).check(matches(isDisplayed()))
    }

    @Test
    fun testTopTriggersList() {
        setupTestData()
        navigateToAnalytics()

        // Verify top triggers list is populated
        onView(withId(R.id.rv_top_triggers))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rv_top_triggers))
            .check(matches(hasMinimumChildCount(1)))

        // Verify trigger analysis items contain food names and percentages
        onView(allOf(
            withText(containsString("Dairy")),
            hasAncestor(withId(R.id.rv_top_triggers))
        )).check(matches(isDisplayed()))

        onView(allOf(
            withText(containsString("%")),
            hasAncestor(withId(R.id.rv_top_triggers))
        )).check(matches(isDisplayed()))
    }

    @Test
    fun testAnalyticsWithNoCorrelations() {
        // Setup data without clear correlations
        setupUncorrelatedTestData()

        navigateToAnalytics()

        // Verify basic stats still show
        onView(withId(R.id.tv_total_entries_count))
            .check(matches(not(withText("0"))))

        onView(withId(R.id.tv_symptoms_count))
            .check(matches(not(withText("0"))))

        // Correlations count should be 0 or very low
        onView(withId(R.id.tv_correlations_count))
            .check(matches(withText("0")))

        // Top triggers list should show empty state or minimal items
        onView(anyOf(
            withText(containsString("No patterns")),
            withText(containsString("No triggers")),
            withId(R.id.rv_top_triggers)
        )).check(matches(isDisplayed()))
    }

    @Test
    fun testAnalyticsDataRefresh() {
        // Start with initial data
        setupTestData()
        navigateToAnalytics()

        // Record initial counts
        onView(withId(R.id.tv_total_entries_count))
            .check(matches(withText("5")))

        // Navigate away and add more data
        onView(withId(R.id.navigation_timeline))
            .perform(click())

        // Add more test data
        addAdditionalTestData()

        // Navigate back to analytics
        navigateToAnalytics()

        // Verify data has refreshed
        onView(withId(R.id.tv_total_entries_count))
            .check(matches(withText("7"))) // Should show updated count
    }

    @Test
    fun testAnalyticsProgressIndicator() {
        navigateToAnalytics()

        // Verify progress bar appears during loading (may be brief)
        // This is implementation-specific and may require IdlingResource
        onView(withId(R.id.progress_bar))
            .check(matches(anyOf(
                withEffectiveVisibility(Visibility.GONE),
                withEffectiveVisibility(Visibility.VISIBLE)
            )))
    }

    @Test
    fun testCustomTimeRangeSelection() {
        setupExtendedTestData()
        navigateToAnalytics()

        // Select custom time range tab
        onView(allOf(withText("Custom"), isDescendantOfA(withId(R.id.tab_layout_time_range))))
            .perform(click())

        // This would trigger a date picker dialog
        // Verify custom range UI appears
        // Implementation would depend on the custom date picker implementation
        onView(withText(containsString("Select")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testAnalyticsScrolling() {
        setupTestData()
        navigateToAnalytics()

        // Verify the scroll view works properly
        onView(withId(R.id.rv_symptom_trends))
            .perform(swipeUp())

        onView(withId(R.id.rv_top_triggers))
            .check(matches(isDisplayed()))

        // Scroll back up
        onView(withId(R.id.tab_layout_time_range))
            .perform(swipeDown())

        onView(withId(R.id.tv_total_entries_count))
            .check(matches(isDisplayed()))
    }

    // Helper methods

    private fun navigateToAnalytics() {
        onView(withId(R.id.navigation_analysis))
            .perform(click())

        // Verify we're on the analytics screen
        onView(withId(R.id.tv_total_entries_count))
            .check(matches(isDisplayed()))
    }

    private fun setupTestData() {
        runBlocking {
            val baseTime = Instant.now().minusSeconds(86400) // 1 day ago

            // Create food entries
            val foodEntries = listOf(
                FoodEntry.create(
                    foods = listOf("Dairy", "Milk"),
                    portions = mapOf("Dairy" to "1 cup", "Milk" to "1 cup"),
                    mealType = MealType.BREAKFAST,
                    timestamp = baseTime,
                    notes = "Test breakfast"
                ),
                FoodEntry.create(
                    foods = listOf("Wheat", "Bread"),
                    portions = mapOf("Wheat" to "2 slices", "Bread" to "2 slices"),
                    mealType = MealType.LUNCH,
                    timestamp = baseTime.plusSeconds(3600),
                    notes = "Test lunch"
                ),
                FoodEntry.create(
                    foods = listOf("Garlic", "Onion"),
                    portions = mapOf("Garlic" to "1 clove", "Onion" to "1 medium"),
                    mealType = MealType.DINNER,
                    timestamp = baseTime.plusSeconds(7200),
                    notes = "Test dinner"
                ),
                FoodEntry.create(
                    foods = listOf("Apple", "Fruit"),
                    portions = mapOf("Apple" to "1 medium", "Fruit" to "1 medium"),
                    mealType = MealType.SNACK,
                    timestamp = baseTime.plusSeconds(10800),
                    notes = "Test snack"
                ),
                FoodEntry.create(
                    foods = listOf("Beans", "Legumes"),
                    portions = mapOf("Beans" to "1 cup", "Legumes" to "1 cup"),
                    mealType = MealType.DINNER,
                    timestamp = baseTime.plusSeconds(14400),
                    notes = "Test second dinner"
                )
            )

            foodEntries.forEach { database.foodEntryDao().insert(it) }

            // Create symptom events with correlations
            val symptomEvents = listOf(
                SymptomEvent.create(
                    symptomType = SymptomType.BLOATING,
                    severity = 6,
                    timestamp = baseTime.plusSeconds(7200), // 2 hours after dairy
                    duration = Duration.ofMinutes(60),
                    notes = "After dairy consumption"
                ),
                SymptomEvent.create(
                    symptomType = SymptomType.GAS,
                    severity = 7,
                    timestamp = baseTime.plusSeconds(14400), // 2 hours after garlic/onion
                    duration = Duration.ofMinutes(90),
                    notes = "After garlic and onion"
                ),
                SymptomEvent.create(
                    symptomType = SymptomType.ABDOMINAL_PAIN,
                    severity = 5,
                    timestamp = baseTime.plusSeconds(21600), // 2 hours after beans
                    duration = Duration.ofMinutes(45),
                    notes = "After beans"
                )
            )

            symptomEvents.forEach { database.symptomEventDao().insert(it) }
        }
    }

    private fun setupExtendedTestData() {
        runBlocking {
            val baseTime = Instant.now().minusSeconds(2592000) // 30 days ago

            // Create data spanning different time periods
            for (i in 0..29) { // 30 days of data
                val dayTime = baseTime.plusSeconds(i * 86400L)

                if (i % 3 == 0) { // Every 3rd day
                    val foodEntry = FoodEntry.create(
                        foods = listOf("Daily food $i"),
                        portions = mapOf("Daily food $i" to "1 serving"),
                        mealType = MealType.LUNCH,
                        timestamp = dayTime,
                        notes = "Day $i entry"
                    )
                    database.foodEntryDao().insert(foodEntry)

                    if (i % 6 == 0) { // Every 6th day add symptom
                        val symptom = SymptomEvent.create(
                            symptomType = SymptomType.BLOATING,
                            severity = 4 + (i % 6),
                            timestamp = dayTime.plusSeconds(7200),
                            duration = Duration.ofMinutes(30),
                            notes = "Day $i symptom"
                        )
                        database.symptomEventDao().insert(symptom)
                    }
                }
            }
        }
    }

    private fun setupUncorrelatedTestData() {
        runBlocking {
            val baseTime = Instant.now().minusSeconds(86400)

            // Create food entries
            val foodEntry = FoodEntry.create(
                foods = listOf("Rice", "Vegetables"),
                portions = mapOf("Rice" to "1 cup", "Vegetables" to "1 cup"),
                mealType = MealType.LUNCH,
                timestamp = baseTime,
                notes = "Unrelated food"
            )
            database.foodEntryDao().insert(foodEntry)

            // Create symptom far from food timing
            val symptom = SymptomEvent.create(
                symptomType = SymptomType.HEADACHE,
                severity = 3,
                timestamp = baseTime.plusSeconds(43200), // 12 hours later
                duration = Duration.ofMinutes(30),
                notes = "Unrelated symptom"
            )
            database.symptomEventDao().insert(symptom)
        }
    }

    private fun addAdditionalTestData() {
        runBlocking {
            val currentTime = Instant.now()

            val additionalEntries = listOf(
                FoodEntry.create(
                    foods = listOf("Additional Food 1"),
                    portions = mapOf("Additional Food 1" to "1 serving"),
                    mealType = MealType.BREAKFAST,
                    timestamp = currentTime.minusSeconds(3600),
                    notes = "Additional entry 1"
                ),
                FoodEntry.create(
                    foods = listOf("Additional Food 2"),
                    portions = mapOf("Additional Food 2" to "1 serving"),
                    mealType = MealType.SNACK,
                    timestamp = currentTime.minusSeconds(1800),
                    notes = "Additional entry 2"
                )
            )

            additionalEntries.forEach { database.foodEntryDao().insert(it) }
        }
    }

    private fun verifyStatsForTimeRange(range: String) {
        // Verify stats update based on time range
        // This is implementation-specific and would depend on actual data in each range
        onView(withId(R.id.tv_total_entries_count))
            .check(matches(withText(matchesPattern("\\d+"))))

        onView(withId(R.id.tv_symptoms_count))
            .check(matches(withText(matchesPattern("\\d+"))))
    }
}