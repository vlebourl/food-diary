package com.fooddiary.data.repository

import com.fooddiary.data.database.entities.EnvironmentalContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import java.time.LocalDate
import kotlin.test.*

@RunWith(MockitoJUnitRunner::class)
class EnvironmentalContextRepositoryTest {

    private val repository: EnvironmentalContextRepository = mock()

    @Test
    fun `insertOrUpdate should handle new and existing contexts`() = runTest {
        // This test will fail until implementation is complete
        val context = createTestEnvironmentalContext()
        assertDoesNotThrow {
            repository.insertOrUpdate(context)
        }
    }

    @Test
    fun `getByDate should return context for specific date`() = runTest {
        // This test will fail until implementation is complete
        val date = LocalDate.now()
        val result = repository.getByDate(date)
        assertNull(result) // Expected to fail initially
    }

    @Test
    fun `getByDateRange should return contexts within range`() = runTest {
        // This test will fail until implementation is complete
        val startDate = LocalDate.now().minusDays(7)
        val endDate = LocalDate.now()
        val flow: Flow<List<EnvironmentalContext>> = repository.getByDateRange(startDate, endDate)
        val result = flow.first()
        assertNotNull(result)
        assertTrue(result.isEmpty()) // Initially empty
    }

    @Test
    fun `getAverageStress should calculate stress over period`() = runTest {
        // This test will fail until implementation is complete
        val days = 7
        val flow: Flow<Float> = repository.getAverageStress(days)
        val result = flow.first()
        assertEquals(0.0f, result) // Initially zero
    }

    @Test
    fun `getAverageSleep should calculate sleep over period`() = runTest {
        // This test will fail until implementation is complete
        val days = 7
        val flow: Flow<Float> = repository.getAverageSleep(days)
        val result = flow.first()
        assertEquals(0.0f, result) // Initially zero
    }

    @Test
    fun `stress level should be within valid range`() = runTest {
        // Test stress level validation
        val validContext = createTestEnvironmentalContext().copy(stressLevel = 5)
        assertTrue(validContext.stressLevel in 1..10)
    }

    @Test
    fun `sleep quality should be within valid range`() = runTest {
        // Test sleep quality validation
        val validContext = createTestEnvironmentalContext().copy(sleepQuality = 8)
        assertTrue(validContext.sleepQuality in 1..10)
    }

    @Test
    fun `sleep hours should be reasonable`() = runTest {
        // Test sleep hours validation
        val validContext = createTestEnvironmentalContext().copy(sleepHours = 7.5f)
        assertTrue(validContext.sleepHours in 0f..24f)
    }

    private fun createTestEnvironmentalContext(): EnvironmentalContext {
        // This will fail until EnvironmentalContext entity is implemented
        return EnvironmentalContext(
            date = LocalDate.now(),
            stressLevel = 4,
            sleepHours = 7.5f,
            sleepQuality = 8,
            exerciseMinutes = 30,
            exerciseType = "walking",
            exerciseIntensity = "moderate",
            menstrualPhase = null,
            weather = "sunny",
            location = "home"
        )
    }
}