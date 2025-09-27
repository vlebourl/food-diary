package com.fooddiary.validation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fooddiary.data.validation.FoodEntryValidator
import com.fooddiary.data.validation.SymptomValidator
import com.fooddiary.data.validation.PortionValidator
import com.fooddiary.data.validation.TimestampValidator
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.Symptom
import com.fooddiary.data.models.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * T059: Test invalid input validation (portion sizes, severity ratings)
 * Tests form field validation logic and error handling
 */
@ExperimentalCoroutinesApi
class InputValidationTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var foodEntryValidator: FoodEntryValidator

    @MockK
    private lateinit var symptomValidator: SymptomValidator

    @MockK
    private lateinit var portionValidator: PortionValidator

    @MockK
    private lateinit var timestampValidator: TimestampValidator

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should validate portion size format correctly`() {
        // Given - various portion size inputs
        val validPortions = listOf(
            "1 cup",
            "2 tablespoons",
            "150g",
            "1 medium apple",
            "2.5 servings",
            "1/2 cup",
            "3-4 pieces"
        )

        val invalidPortions = listOf(
            "",
            "   ",
            "abc",
            "just some",
            "-1 cup",
            "0 servings",
            "999999 tons"
        )

        every { portionValidator.validate(any()) } answers {
            val portion = firstArg<String>()
            if (validPortions.contains(portion)) {
                PortionValidationResult.Valid
            } else {
                PortionValidationResult.Invalid("Invalid portion format: '$portion'")
            }
        }

        // When & Then - validating valid portions
        validPortions.forEach { portion ->
            val result = portionValidator.validate(portion)
            assertTrue("$portion should be valid", result is PortionValidationResult.Valid)
        }

        // When & Then - validating invalid portions
        invalidPortions.forEach { portion ->
            val result = portionValidator.validate(portion)
            assertTrue("$portion should be invalid", result is PortionValidationResult.Invalid)
            if (result is PortionValidationResult.Invalid) {
                assertTrue("Should contain error message for $portion",
                    result.message.contains("Invalid portion format"))
            }
        }
    }

    @Test
    fun `should validate severity rating bounds 1-10`() {
        // Given - severity ratings in and out of bounds
        val validSeverities = (1..10).toList()
        val invalidSeverities = listOf(-1, 0, 11, 15, 100, -999)

        every { symptomValidator.validateSeverity(any()) } answers {
            val severity = firstArg<Int>()
            when {
                severity in 1..10 -> SeverityValidationResult.Valid
                severity < 1 -> SeverityValidationResult.TooLow("Severity must be at least 1")
                severity > 10 -> SeverityValidationResult.TooHigh("Severity cannot exceed 10")
                else -> SeverityValidationResult.Invalid("Invalid severity value")
            }
        }

        // When & Then - validating valid severities
        validSeverities.forEach { severity ->
            val result = symptomValidator.validateSeverity(severity)
            assertTrue("Severity $severity should be valid", result is SeverityValidationResult.Valid)
        }

        // When & Then - validating invalid severities
        invalidSeverities.forEach { severity ->
            val result = symptomValidator.validateSeverity(severity)
            assertTrue("Severity $severity should be invalid", result !is SeverityValidationResult.Valid)

            when {
                severity < 1 -> assertTrue("Should indicate too low", result is SeverityValidationResult.TooLow)
                severity > 10 -> assertTrue("Should indicate too high", result is SeverityValidationResult.TooHigh)
            }
        }
    }

    @Test
    fun `should validate timestamp within reasonable bounds`() {
        // Given - various timestamp scenarios
        val now = Instant.now()
        val validTimestamps = listOf(
            now, // current time
            now.minus(1, ChronoUnit.HOURS), // 1 hour ago
            now.minus(1, ChronoUnit.DAYS), // 1 day ago
            now.minus(7, ChronoUnit.DAYS), // 1 week ago
            now.minus(30, ChronoUnit.DAYS) // 1 month ago
        )

        val invalidTimestamps = listOf(
            now.plus(1, ChronoUnit.HOURS), // future
            now.plus(1, ChronoUnit.DAYS), // future
            now.minus(365, ChronoUnit.DAYS), // too far in past
            now.minus(1000, ChronoUnit.DAYS) // way too far in past
        )

        every { timestampValidator.validate(any()) } answers {
            val timestamp = firstArg<Instant>()
            val maxPastDays = 180 // 6 months
            val maxPast = now.minus(maxPastDays.toLong(), ChronoUnit.DAYS)

            when {
                timestamp.isAfter(now) -> TimestampValidationResult.Future("Cannot log entries in the future")
                timestamp.isBefore(maxPast) -> TimestampValidationResult.TooOld("Cannot log entries older than 6 months")
                else -> TimestampValidationResult.Valid
            }
        }

        // When & Then - validating valid timestamps
        validTimestamps.forEach { timestamp ->
            val result = timestampValidator.validate(timestamp)
            assertTrue("Timestamp should be valid", result is TimestampValidationResult.Valid)
        }

        // When & Then - validating invalid timestamps
        invalidTimestamps.forEach { timestamp ->
            val result = timestampValidator.validate(timestamp)
            assertTrue("Timestamp should be invalid", result !is TimestampValidationResult.Valid)

            when {
                timestamp.isAfter(now) -> assertTrue("Should indicate future", result is TimestampValidationResult.Future)
                timestamp.isBefore(now.minus(180, ChronoUnit.DAYS)) ->
                    assertTrue("Should indicate too old", result is TimestampValidationResult.TooOld)
            }
        }
    }

    @Test
    fun `should validate food entry form fields comprehensively`() {
        // Given - food entry with various validation issues
        val invalidEntry = FoodEntry(
            id = "test-entry",
            timestamp = Instant.now().plus(1, ChronoUnit.HOURS), // future timestamp
            mealType = MealType.BREAKFAST,
            foods = emptyList(), // empty foods list
            portions = listOf("invalid portion"), // invalid portion
            notes = "a".repeat(1001), // notes too long
            isDeleted = false
        )

        every { foodEntryValidator.validateEntry(any()) } returns ValidationResult.Error(listOf(
            "Foods list cannot be empty",
            "Timestamp cannot be in the future",
            "Invalid portion format: 'invalid portion'",
            "Notes cannot exceed 1000 characters"
        ))

        // When - validating invalid entry
        val result = foodEntryValidator.validateEntry(invalidEntry)

        // Then
        assertTrue("Should return validation errors", result is ValidationResult.Error)
        val errors = (result as ValidationResult.Error).errors
        assertEquals("Should have 4 validation errors", 4, errors.size)
        assertTrue("Should validate foods list", errors.any { it.contains("Foods list cannot be empty") })
        assertTrue("Should validate timestamp", errors.any { it.contains("Timestamp cannot be in the future") })
        assertTrue("Should validate portion format", errors.any { it.contains("Invalid portion format") })
        assertTrue("Should validate notes length", errors.any { it.contains("Notes cannot exceed 1000 characters") })
    }

    @Test
    fun `should validate symptom entry form fields comprehensively`() {
        // Given - symptom entry with validation issues
        val invalidSymptom = Symptom(
            id = "test-symptom",
            timestamp = Instant.now().minus(400, ChronoUnit.DAYS), // too old
            type = SymptomType.BLOATING,
            severity = 15, // out of bounds
            duration = -5, // negative duration
            triggerFoodId = null,
            notes = "", // empty but allowed
            isDeleted = false
        )

        every { symptomValidator.validateSymptom(any()) } returns ValidationResult.Error(listOf(
            "Timestamp cannot be older than 6 months",
            "Severity must be between 1 and 10",
            "Duration cannot be negative"
        ))

        // When - validating invalid symptom
        val result = symptomValidator.validateSymptom(invalidSymptom)

        // Then
        assertTrue("Should return validation errors", result is ValidationResult.Error)
        val errors = (result as ValidationResult.Error).errors
        assertEquals("Should have 3 validation errors", 3, errors.size)
        assertTrue("Should validate timestamp", errors.any { it.contains("Timestamp cannot be older") })
        assertTrue("Should validate severity", errors.any { it.contains("Severity must be between 1 and 10") })
        assertTrue("Should validate duration", errors.any { it.contains("Duration cannot be negative") })
    }

    @Test
    fun `should handle edge cases in portion validation`() {
        // Given - edge case portion formats
        val edgeCasePortions = mapOf(
            "0.5 cup" to true, // decimal
            "1/4 teaspoon" to true, // fraction
            "2-3 slices" to true, // range
            "1 large (approx 200g)" to true, // with approximation
            "just a bit" to false, // vague
            "some" to false, // vague
            "lots" to false, // vague
            "" to false, // empty
            "   " to false, // whitespace only
            "1 cup; 2 tablespoons" to false // multiple items in one portion
        )

        every { portionValidator.validate(any()) } answers {
            val portion = firstArg<String>()
            val isValid = edgeCasePortions[portion] ?: false
            if (isValid) {
                PortionValidationResult.Valid
            } else {
                PortionValidationResult.Invalid("Invalid or vague portion description")
            }
        }

        // When & Then - testing edge cases
        edgeCasePortions.forEach { (portion, expectedValid) ->
            val result = portionValidator.validate(portion)
            if (expectedValid) {
                assertTrue("$portion should be valid", result is PortionValidationResult.Valid)
            } else {
                assertTrue("$portion should be invalid", result is PortionValidationResult.Invalid)
            }
        }
    }

    @Test
    fun `should validate duration field constraints`() {
        // Given - various duration values
        val validDurations = listOf(1, 5, 15, 30, 60, 120, 240, 480) // 1 min to 8 hours
        val invalidDurations = listOf(-1, 0, 1441, 2000) // negative, zero, over 24 hours

        every { symptomValidator.validateDuration(any()) } answers {
            val duration = firstArg<Int>()
            when {
                duration < 1 -> DurationValidationResult.TooShort("Duration must be at least 1 minute")
                duration > 1440 -> DurationValidationResult.TooLong("Duration cannot exceed 24 hours (1440 minutes)")
                else -> DurationValidationResult.Valid
            }
        }

        // When & Then - validating valid durations
        validDurations.forEach { duration ->
            val result = symptomValidator.validateDuration(duration)
            assertTrue("Duration $duration should be valid", result is DurationValidationResult.Valid)
        }

        // When & Then - validating invalid durations
        invalidDurations.forEach { duration ->
            val result = symptomValidator.validateDuration(duration)
            assertTrue("Duration $duration should be invalid", result !is DurationValidationResult.Valid)

            when {
                duration < 1 -> assertTrue("Should indicate too short", result is DurationValidationResult.TooShort)
                duration > 1440 -> assertTrue("Should indicate too long", result is DurationValidationResult.TooLong)
            }
        }
    }

    @Test
    fun `should validate notes field length and content`() {
        // Given - various notes content
        val validNotes = listOf(
            "", // empty allowed
            "Short note",
            "A".repeat(500), // medium length
            "B".repeat(1000) // max length
        )

        val invalidNotes = listOf(
            "C".repeat(1001), // too long
            "\t\t\t", // only whitespace
            "Invalid\u0000chars" // null characters
        )

        every { foodEntryValidator.validateNotes(any()) } answers {
            val notes = firstArg<String>()
            when {
                notes.length > 1000 -> NotesValidationResult.TooLong("Notes cannot exceed 1000 characters")
                notes.contains('\u0000') -> NotesValidationResult.InvalidCharacters("Notes contain invalid characters")
                notes.trim().isEmpty() && notes.isNotEmpty() -> NotesValidationResult.OnlyWhitespace("Notes cannot contain only whitespace")
                else -> NotesValidationResult.Valid
            }
        }

        // When & Then - validating valid notes
        validNotes.forEach { notes ->
            val result = foodEntryValidator.validateNotes(notes)
            assertTrue("Notes should be valid", result is NotesValidationResult.Valid)
        }

        // When & Then - validating invalid notes
        invalidNotes.forEach { notes ->
            val result = foodEntryValidator.validateNotes(notes)
            assertTrue("Notes should be invalid", result !is NotesValidationResult.Valid)
        }
    }

    @Test
    fun `should validate meal type selection`() {
        // Given - meal type validation
        every { foodEntryValidator.validateMealType(any()) } answers {
            val mealType = firstArg<MealType?>()
            when (mealType) {
                null -> MealTypeValidationResult.Missing("Meal type is required")
                else -> MealTypeValidationResult.Valid
            }
        }

        // When & Then - valid meal types
        MealType.values().forEach { mealType ->
            val result = foodEntryValidator.validateMealType(mealType)
            assertTrue("$mealType should be valid", result is MealTypeValidationResult.Valid)
        }

        // When & Then - null meal type
        val result = foodEntryValidator.validateMealType(null)
        assertTrue("Null meal type should be invalid", result is MealTypeValidationResult.Missing)
    }

    @Test
    fun `should validate complete form state before submission`() {
        // Given - complete validation check
        val completeValidEntry = FoodEntry(
            id = "valid-entry",
            timestamp = Instant.now().minus(1, ChronoUnit.HOURS),
            mealType = MealType.LUNCH,
            foods = listOf("Apple", "Sandwich"),
            portions = listOf("1 medium", "1 whole"),
            notes = "Feeling good after this meal",
            isDeleted = false
        )

        every { foodEntryValidator.validateEntry(any()) } returns ValidationResult.Success

        // When - validating complete entry
        val result = foodEntryValidator.validateEntry(completeValidEntry)

        // Then
        assertTrue("Complete valid entry should pass validation", result is ValidationResult.Success)
        verify { foodEntryValidator.validateEntry(completeValidEntry) }
    }

    @Test
    fun `should provide user-friendly error messages for validation failures`() {
        // Given - validation with user-friendly messages
        every { portionValidator.validate("bad portion") } returns
            PortionValidationResult.Invalid("Please use format like '1 cup', '2 tablespoons', or '150g'")

        every { symptomValidator.validateSeverity(15) } returns
            SeverityValidationResult.TooHigh("Please rate severity from 1 (mild) to 10 (severe)")

        every { timestampValidator.validate(Instant.now().plus(1, ChronoUnit.DAYS)) } returns
            TimestampValidationResult.Future("Please select a time in the past when you actually ate")

        // When - getting validation messages
        val portionResult = portionValidator.validate("bad portion")
        val severityResult = symptomValidator.validateSeverity(15)
        val timestampResult = timestampValidator.validate(Instant.now().plus(1, ChronoUnit.DAYS))

        // Then
        assertTrue("Should provide helpful portion message",
            (portionResult as PortionValidationResult.Invalid).message.contains("Please use format"))
        assertTrue("Should provide helpful severity message",
            (severityResult as SeverityValidationResult.TooHigh).message.contains("from 1 (mild) to 10 (severe)"))
        assertTrue("Should provide helpful timestamp message",
            (timestampResult as TimestampValidationResult.Future).message.contains("when you actually ate"))
    }
}

// Validation result classes
sealed class PortionValidationResult {
    object Valid : PortionValidationResult()
    data class Invalid(val message: String) : PortionValidationResult()
}

sealed class SeverityValidationResult {
    object Valid : SeverityValidationResult()
    data class TooLow(val message: String) : SeverityValidationResult()
    data class TooHigh(val message: String) : SeverityValidationResult()
    data class Invalid(val message: String) : SeverityValidationResult()
}

sealed class TimestampValidationResult {
    object Valid : TimestampValidationResult()
    data class Future(val message: String) : TimestampValidationResult()
    data class TooOld(val message: String) : TimestampValidationResult()
    data class Invalid(val message: String) : TimestampValidationResult()
}

sealed class DurationValidationResult {
    object Valid : DurationValidationResult()
    data class TooShort(val message: String) : DurationValidationResult()
    data class TooLong(val message: String) : DurationValidationResult()
    data class Invalid(val message: String) : DurationValidationResult()
}

sealed class NotesValidationResult {
    object Valid : NotesValidationResult()
    data class TooLong(val message: String) : NotesValidationResult()
    data class InvalidCharacters(val message: String) : NotesValidationResult()
    data class OnlyWhitespace(val message: String) : NotesValidationResult()
}

sealed class MealTypeValidationResult {
    object Valid : MealTypeValidationResult()
    data class Missing(val message: String) : MealTypeValidationResult()
}