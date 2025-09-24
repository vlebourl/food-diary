package com.fooddiary.domain.usecase

import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.models.SymptomType
import com.fooddiary.data.repository.SymptomEventRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.Instant

@DisplayName("Add Symptom Event UseCase Tests")
class AddSymptomEventUseCaseTest {

    private lateinit var symptomEventRepository: SymptomEventRepository
    private lateinit var addSymptomEventUseCase: AddSymptomEventUseCase

    private val testTimestamp = Instant.now()

    @BeforeEach
    fun setup() {
        symptomEventRepository = mockk(relaxed = true)
        addSymptomEventUseCase = AddSymptomEventUseCase(symptomEventRepository)
    }

    @Nested
    @DisplayName("Successful Symptom Event Creation")
    inner class SuccessfulSymptomEventCreation {

        @Test
        @DisplayName("Should create basic symptom event successfully")
        fun `should create basic symptom event successfully`() = runTest {
            // Given
            val expectedId = 123L
            coEvery { symptomEventRepository.insert(any()) } returns expectedId

            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.BLOATING,
                severity = 5,
                timestamp = testTimestamp
            )

            // Then
            assertTrue(result.isSuccess)
            assertEquals(expectedId, result.getOrNull())

            val capturedEvent = slot<SymptomEvent>()
            coVerify { symptomEventRepository.insert(capture(capturedEvent)) }

            with(capturedEvent.captured) {
                assertEquals(SymptomType.BLOATING, symptomType)
                assertEquals(5, severity)
                assertEquals(testTimestamp, timestamp)
                assertNull(durationMinutes)
                assertNull(bristolScale)
                assertNull(bodyLocation)
                assertTrue(suspectedTriggers.isEmpty())
                assertNull(notes)
            }
        }

        @Test
        @DisplayName("Should create bowel movement symptom with Bristol scale")
        fun `should create bowel movement symptom with Bristol scale`() = runTest {
            // Given
            val expectedId = 456L
            coEvery { symptomEventRepository.insert(any()) } returns expectedId

            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.BOWEL_MOVEMENT,
                severity = 3,
                timestamp = testTimestamp,
                bristolScale = 4
            )

            // Then
            assertTrue(result.isSuccess)

            val capturedEvent = slot<SymptomEvent>()
            coVerify { symptomEventRepository.insert(capture(capturedEvent)) }

            with(capturedEvent.captured) {
                assertEquals(SymptomType.BOWEL_MOVEMENT, symptomType)
                assertEquals(4, bristolScale)
            }
        }

        @Test
        @DisplayName("Should create diarrhea symptom with Bristol scale")
        fun `should create diarrhea symptom with Bristol scale`() = runTest {
            // Given
            coEvery { symptomEventRepository.insert(any()) } returns 1L

            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.DIARRHEA,
                severity = 8,
                bristolScale = 6
            )

            // Then
            assertTrue(result.isSuccess)

            val capturedEvent = slot<SymptomEvent>()
            coVerify { symptomEventRepository.insert(capture(capturedEvent)) }

            with(capturedEvent.captured) {
                assertEquals(SymptomType.DIARRHEA, symptomType)
                assertEquals(6, bristolScale)
            }
        }

        @Test
        @DisplayName("Should create constipation symptom with Bristol scale")
        fun `should create constipation symptom with Bristol scale`() = runTest {
            // Given
            coEvery { symptomEventRepository.insert(any()) } returns 1L

            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.CONSTIPATION,
                severity = 6,
                bristolScale = 1
            )

            // Then
            assertTrue(result.isSuccess)

            val capturedEvent = slot<SymptomEvent>()
            coVerify { symptomEventRepository.insert(capture(capturedEvent)) }

            with(capturedEvent.captured) {
                assertEquals(SymptomType.CONSTIPATION, symptomType)
                assertEquals(1, bristolScale)
            }
        }

        @Test
        @DisplayName("Should create symptom event with all optional parameters")
        fun `should create symptom event with all optional parameters`() = runTest {
            // Given
            val expectedId = 789L
            val suspectedTriggers = listOf("dairy", "gluten")
            coEvery { symptomEventRepository.insert(any()) } returns expectedId

            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.ABDOMINAL_PAIN,
                severity = 7,
                timestamp = testTimestamp,
                durationMinutes = 120,
                bodyLocation = "Lower right abdomen",
                suspectedTriggers = suspectedTriggers,
                notes = "Sharp pain after eating"
            )

            // Then
            assertTrue(result.isSuccess)

            val capturedEvent = slot<SymptomEvent>()
            coVerify { symptomEventRepository.insert(capture(capturedEvent)) }

            with(capturedEvent.captured) {
                assertEquals(SymptomType.ABDOMINAL_PAIN, symptomType)
                assertEquals(7, severity)
                assertEquals(120, durationMinutes)
                assertEquals("Lower right abdomen", bodyLocation)
                assertEquals(suspectedTriggers, this.suspectedTriggers)
                assertEquals("Sharp pain after eating", notes)
            }
        }

        @Test
        @DisplayName("Should use current timestamp when not provided")
        fun `should use current timestamp when not provided`() = runTest {
            // Given
            val beforeCall = Instant.now()
            coEvery { symptomEventRepository.insert(any()) } returns 1L

            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.NAUSEA,
                severity = 4
                // timestamp not provided - should use default (Instant.now())
            )

            // Then
            val afterCall = Instant.now()
            assertTrue(result.isSuccess)

            val capturedEvent = slot<SymptomEvent>()
            coVerify { symptomEventRepository.insert(capture(capturedEvent)) }

            // Timestamp should be between beforeCall and afterCall
            assertTrue(capturedEvent.captured.timestamp.isAfter(beforeCall.minusSeconds(1)))
            assertTrue(capturedEvent.captured.timestamp.isBefore(afterCall.plusSeconds(1)))
        }
    }

    @Nested
    @DisplayName("Input Validation")
    inner class InputValidation {

        @Test
        @DisplayName("Should fail when severity is below 1")
        fun `should fail when severity is below 1`() = runTest {
            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.BLOATING,
                severity = 0
            )

            // Then
            assertTrue(result.isFailure)
            val exception = result.exceptionOrNull()
            assertInstanceOf(IllegalArgumentException::class.java, exception)
            assertEquals("Severity must be between 1 and 10", exception?.message)

            coVerify(exactly = 0) { symptomEventRepository.insert(any()) }
        }

        @Test
        @DisplayName("Should fail when severity is above 10")
        fun `should fail when severity is above 10`() = runTest {
            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.BLOATING,
                severity = 11
            )

            // Then
            assertTrue(result.isFailure)
            assertInstanceOf(IllegalArgumentException::class.java, result.exceptionOrNull())
            assertEquals("Severity must be between 1 and 10", result.exceptionOrNull()?.message)
        }

        @Test
        @DisplayName("Should accept valid severity values")
        fun `should accept valid severity values`() = runTest {
            // Given
            coEvery { symptomEventRepository.insert(any()) } returns 1L

            // Test all valid severity levels
            for (severity in 1..10) {
                // When
                val result = addSymptomEventUseCase.invoke(
                    symptomType = SymptomType.BLOATING,
                    severity = severity
                )

                // Then
                assertTrue(result.isSuccess, "Severity $severity should be valid")
            }

            coVerify(exactly = 10) { symptomEventRepository.insert(any()) }
        }

        @Test
        @DisplayName("Should fail when Bristol scale is below 1")
        fun `should fail when Bristol scale is below 1`() = runTest {
            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.BOWEL_MOVEMENT,
                severity = 5,
                bristolScale = 0
            )

            // Then
            assertTrue(result.isFailure)
            assertInstanceOf(IllegalArgumentException::class.java, result.exceptionOrNull())
            assertEquals("Bristol Scale must be between 1 and 7", result.exceptionOrNull()?.message)
        }

        @Test
        @DisplayName("Should fail when Bristol scale is above 7")
        fun `should fail when Bristol scale is above 7`() = runTest {
            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.BOWEL_MOVEMENT,
                severity = 5,
                bristolScale = 8
            )

            // Then
            assertTrue(result.isFailure)
            assertInstanceOf(IllegalArgumentException::class.java, result.exceptionOrNull())
            assertEquals("Bristol Scale must be between 1 and 7", result.exceptionOrNull()?.message)
        }

        @Test
        @DisplayName("Should accept valid Bristol scale values")
        fun `should accept valid Bristol scale values`() = runTest {
            // Given
            coEvery { symptomEventRepository.insert(any()) } returns 1L

            // Test all valid Bristol scale values
            for (bristolScale in 1..7) {
                // When
                val result = addSymptomEventUseCase.invoke(
                    symptomType = SymptomType.BOWEL_MOVEMENT,
                    severity = 5,
                    bristolScale = bristolScale
                )

                // Then
                assertTrue(result.isSuccess, "Bristol scale $bristolScale should be valid")
            }

            coVerify(exactly = 7) { symptomEventRepository.insert(any()) }
        }
    }

    @Nested
    @DisplayName("Bristol Scale Validation")
    inner class BristolScaleValidation {

        @Test
        @DisplayName("Should require Bristol scale for diarrhea symptoms")
        fun `should require Bristol scale for diarrhea symptoms`() = runTest {
            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.DIARRHEA,
                severity = 5
                // bristolScale not provided
            )

            // Then
            assertTrue(result.isFailure)
            assertInstanceOf(IllegalArgumentException::class.java, result.exceptionOrNull())
            assertEquals("Bristol Scale is required for bowel movement symptoms", result.exceptionOrNull()?.message)
        }

        @Test
        @DisplayName("Should require Bristol scale for constipation symptoms")
        fun `should require Bristol scale for constipation symptoms`() = runTest {
            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.CONSTIPATION,
                severity = 6
                // bristolScale not provided
            )

            // Then
            assertTrue(result.isFailure)
            assertInstanceOf(IllegalArgumentException::class.java, result.exceptionOrNull())
            assertEquals("Bristol Scale is required for bowel movement symptoms", result.exceptionOrNull()?.message)
        }

        @Test
        @DisplayName("Should require Bristol scale for bowel movement symptoms")
        fun `should require Bristol scale for bowel movement symptoms`() = runTest {
            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.BOWEL_MOVEMENT,
                severity = 3
                // bristolScale not provided
            )

            // Then
            assertTrue(result.isFailure)
            assertInstanceOf(IllegalArgumentException::class.java, result.exceptionOrNull())
            assertEquals("Bristol Scale is required for bowel movement symptoms", result.exceptionOrNull()?.message)
        }

        @Test
        @DisplayName("Should reject Bristol scale for non-bowel symptoms")
        fun `should reject Bristol scale for non-bowel symptoms`() = runTest {
            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.BLOATING,
                severity = 5,
                bristolScale = 4 // Should not be allowed for bloating
            )

            // Then
            assertTrue(result.isFailure)
            assertInstanceOf(IllegalArgumentException::class.java, result.exceptionOrNull())
            assertEquals("Bristol Scale is only applicable for bowel movement symptoms", result.exceptionOrNull()?.message)
        }

        @Test
        @DisplayName("Should reject Bristol scale for nausea")
        fun `should reject Bristol scale for nausea`() = runTest {
            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.NAUSEA,
                severity = 7,
                bristolScale = 3
            )

            // Then
            assertTrue(result.isFailure)
            assertEquals("Bristol Scale is only applicable for bowel movement symptoms", result.exceptionOrNull()?.message)
        }

        @Test
        @DisplayName("Should allow null Bristol scale for non-bowel symptoms")
        fun `should allow null Bristol scale for non-bowel symptoms`() = runTest {
            // Given
            coEvery { symptomEventRepository.insert(any()) } returns 1L

            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.ABDOMINAL_PAIN,
                severity = 6,
                bristolScale = null
            )

            // Then
            assertTrue(result.isSuccess)

            val capturedEvent = slot<SymptomEvent>()
            coVerify { symptomEventRepository.insert(capture(capturedEvent)) }
            assertNull(capturedEvent.captured.bristolScale)
        }
    }

    @Nested
    @DisplayName("Error Handling")
    inner class ErrorHandling {

        @Test
        @DisplayName("Should handle repository insertion failure")
        fun `should handle repository insertion failure`() = runTest {
            // Given
            coEvery { symptomEventRepository.insert(any()) } throws RuntimeException("Database error")

            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.BLOATING,
                severity = 5
            )

            // Then
            assertTrue(result.isFailure)
            assertEquals("Database error", result.exceptionOrNull()?.message)
        }

        @Test
        @DisplayName("Should wrap unexpected exceptions in Result.failure")
        fun `should wrap unexpected exceptions in Result failure`() = runTest {
            // Given
            coEvery { symptomEventRepository.insert(any()) } throws OutOfMemoryError("System error")

            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.NAUSEA,
                severity = 4
            )

            // Then
            assertTrue(result.isFailure)
            assertInstanceOf(OutOfMemoryError::class.java, result.exceptionOrNull())
        }

        @Test
        @DisplayName("Should handle null values appropriately")
        fun `should handle null values appropriately`() = runTest {
            // Given
            coEvery { symptomEventRepository.insert(any()) } returns 1L

            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.FATIGUE,
                severity = 3,
                durationMinutes = null,
                bodyLocation = null,
                suspectedTriggers = emptyList(), // Empty list, not null
                notes = null
            )

            // Then
            assertTrue(result.isSuccess)

            val capturedEvent = slot<SymptomEvent>()
            coVerify { symptomEventRepository.insert(capture(capturedEvent)) }

            with(capturedEvent.captured) {
                assertNull(durationMinutes)
                assertNull(bodyLocation)
                assertTrue(suspectedTriggers.isEmpty())
                assertNull(notes)
            }
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    inner class EdgeCases {

        @Test
        @DisplayName("Should handle all symptom types correctly")
        fun `should handle all symptom types correctly`() = runTest {
            // Given
            coEvery { symptomEventRepository.insert(any()) } returns 1L

            val bowelSymptoms = setOf(SymptomType.DIARRHEA, SymptomType.CONSTIPATION, SymptomType.BOWEL_MOVEMENT)
            val allSymptomTypes = SymptomType.values()

            for (symptomType in allSymptomTypes) {
                // When
                val result = if (symptomType in bowelSymptoms) {
                    // Bowel symptoms require Bristol scale
                    addSymptomEventUseCase.invoke(
                        symptomType = symptomType,
                        severity = 5,
                        bristolScale = 4
                    )
                } else {
                    // Other symptoms don't need Bristol scale
                    addSymptomEventUseCase.invoke(
                        symptomType = symptomType,
                        severity = 5
                    )
                }

                // Then
                assertTrue(result.isSuccess, "Symptom type $symptomType should be handled correctly")
            }

            coVerify(exactly = allSymptomTypes.size) { symptomEventRepository.insert(any()) }
        }

        @Test
        @DisplayName("Should handle very long suspected triggers list")
        fun `should handle very long suspected triggers list`() = runTest {
            // Given
            val manyTriggers = (1..100).map { "trigger_$it" }
            coEvery { symptomEventRepository.insert(any()) } returns 1L

            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.BLOATING,
                severity = 5,
                suspectedTriggers = manyTriggers
            )

            // Then
            assertTrue(result.isSuccess)

            val capturedEvent = slot<SymptomEvent>()
            coVerify { symptomEventRepository.insert(capture(capturedEvent)) }
            assertEquals(manyTriggers, capturedEvent.captured.suspectedTriggers)
        }

        @Test
        @DisplayName("Should handle special characters in notes and body location")
        fun `should handle special characters in notes and body location`() = runTest {
            // Given
            coEvery { symptomEventRepository.insert(any()) } returns 1L

            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.ABDOMINAL_PAIN,
                severity = 6,
                bodyLocation = "Côté gauche du ventre 左側",
                notes = "Douleur très forte! 痛み度: 8/10 😣"
            )

            // Then
            assertTrue(result.isSuccess)

            val capturedEvent = slot<SymptomEvent>()
            coVerify { symptomEventRepository.insert(capture(capturedEvent)) }

            with(capturedEvent.captured) {
                assertEquals("Côté gauche du ventre 左側", bodyLocation)
                assertEquals("Douleur très forte! 痛み度: 8/10 😣", notes)
            }
        }

        @Test
        @DisplayName("Should handle zero duration minutes")
        fun `should handle zero duration minutes`() = runTest {
            // Given
            coEvery { symptomEventRepository.insert(any()) } returns 1L

            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.HEARTBURN,
                severity = 4,
                durationMinutes = 0 // Instantaneous symptom
            )

            // Then
            assertTrue(result.isSuccess)

            val capturedEvent = slot<SymptomEvent>()
            coVerify { symptomEventRepository.insert(capture(capturedEvent)) }
            assertEquals(0, capturedEvent.captured.durationMinutes)
        }

        @Test
        @DisplayName("Should handle very long duration minutes")
        fun `should handle very long duration minutes`() = runTest {
            // Given
            coEvery { symptomEventRepository.insert(any()) } returns 1L

            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.CHRONIC_PAIN,
                severity = 7,
                durationMinutes = 24 * 60 * 7 // One week in minutes
            )

            // Then
            assertTrue(result.isSuccess)

            val capturedEvent = slot<SymptomEvent>()
            coVerify { symptomEventRepository.insert(capture(capturedEvent)) }
            assertEquals(24 * 60 * 7, capturedEvent.captured.durationMinutes)
        }

        @Test
        @DisplayName("Should handle empty suspected triggers list")
        fun `should handle empty suspected triggers list`() = runTest {
            // Given
            coEvery { symptomEventRepository.insert(any()) } returns 1L

            // When
            val result = addSymptomEventUseCase.invoke(
                symptomType = SymptomType.BLOATING,
                severity = 5,
                suspectedTriggers = emptyList()
            )

            // Then
            assertTrue(result.isSuccess)

            val capturedEvent = slot<SymptomEvent>()
            coVerify { symptomEventRepository.insert(capture(capturedEvent)) }
            assertTrue(capturedEvent.captured.suspectedTriggers.isEmpty())
        }
    }
}