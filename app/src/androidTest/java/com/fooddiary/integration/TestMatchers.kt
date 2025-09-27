package com.fooddiary.integration

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

/**
 * Custom Espresso matchers for integration tests
 */
object TestMatchers {

    /**
     * Matches a RecyclerView that has at least the specified number of items
     */
    fun hasMinimumChildCount(minChildCount: Int): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has at least $minChildCount children")
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                return recyclerView.adapter?.itemCount ?: 0 >= minChildCount
            }
        }
    }

    /**
     * Matches a RecyclerView that has exactly the specified number of items
     */
    fun hasChildCount(childCount: Int): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has exactly $childCount children")
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                return recyclerView.adapter?.itemCount == childCount
            }
        }
    }

    /**
     * Matches text that follows a specific pattern
     */
    fun matchesPattern(pattern: String): Matcher<String> {
        return object : BoundedMatcher<String, String>(String::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("matches pattern: $pattern")
            }

            override fun matchesSafely(text: String): Boolean {
                return text.matches(pattern.toRegex())
            }
        }
    }
}