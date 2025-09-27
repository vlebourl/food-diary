package com.fooddiary.data.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fooddiary.data.database.FoodDiaryDatabase
import com.fooddiary.data.database.entities.TriggerPattern
import com.fooddiary.data.models.SymptomType
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CorrelationPatternDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: FoodDiaryDatabase
    private lateinit var correlationPatternDao: TriggerPatternDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FoodDiaryDatabase::class.java
        ).allowMainThreadQueries().build()

        correlationPatternDao = database.triggerPatternDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insert_shouldInsertCorrelationPattern() = runTest {
        // Arrange
        val pattern = createTestCorrelationPattern()

        // Act
        val insertResult = correlationPatternDao.insert(pattern)

        // Assert
        assertTrue("Insert should succeed", insertResult > 0 || insertResult == -1L) // Room returns -1 for String PK

        val retrieved = correlationPatternDao.getById(pattern.id)
        assertNotNull("Retrieved pattern should not be null", retrieved)
        assertEquals("Food name should match", pattern.foodName, retrieved!!.foodName)
        assertEquals("Symptom type should match", pattern.symptomType, retrieved.symptomType)
        assertEquals("Correlation strength should match", pattern.correlationStrength, retrieved.correlationStrength, 0.001f)
    }

    @Test
    fun insertAll_shouldInsertMultipleCorrelationPatterns() = runTest {
        // Arrange
        val patterns = listOf(
            createTestCorrelationPattern(foodName = "Dairy", symptomType = SymptomType.BLOATING),
            createTestCorrelationPattern(foodName = "Gluten", symptomType = SymptomType.ABDOMINAL_PAIN),
            createTestCorrelationPattern(foodName = "Spicy Food", symptomType = SymptomType.DIARRHEA)
        )

        // Act
        val insertResults = correlationPatternDao.insertAll(patterns)

        // Assert
        assertEquals("Should insert all 3 patterns", 3, insertResults.size)

        val allPatterns = correlationPatternDao.getAll()
        assertEquals("Should retrieve all 3 patterns", 3, allPatterns.size)

        val foodNames = allPatterns.map { it.foodName }
        assertTrue("Should contain Dairy", foodNames.contains("Dairy"))
        assertTrue("Should contain Gluten", foodNames.contains("Gluten"))
        assertTrue("Should contain Spicy Food", foodNames.contains("Spicy Food"))
    }

    @Test
    fun update_shouldUpdateCorrelationPattern() = runTest {
        // Arrange
        val originalPattern = createTestCorrelationPattern()
        correlationPatternDao.insert(originalPattern)

        val updatedPattern = originalPattern.update(
            correlationStrength = 0.85f,
            occurrences = 15,
            confidence = 0.92f,
            pValue = 0.02f
        )

        // Act
        val updateCount = correlationPatternDao.update(updatedPattern)

        // Assert
        assertEquals("Should update 1 pattern", 1, updateCount)

        val retrieved = correlationPatternDao.getById(originalPattern.id)
        assertNotNull("Updated pattern should exist", retrieved)
        assertEquals("Correlation strength should be updated", 0.85f, retrieved!!.correlationStrength, 0.001f)
        assertEquals("Occurrences should be updated", 15, retrieved.occurrences)
        assertEquals("Confidence should be updated", 0.92f, retrieved.confidence, 0.001f)
        assertEquals("P-value should be updated", 0.02f, retrieved.pValue!!, 0.001f)
    }

    @Test
    fun getById_shouldReturnCorrectCorrelationPattern() = runTest {
        // Arrange
        val pattern1 = createTestCorrelationPattern(foodName = "Apple")
        val pattern2 = createTestCorrelationPattern(foodName = "Banana")

        correlationPatternDao.insert(pattern1)
        correlationPatternDao.insert(pattern2)

        // Act
        val retrieved1 = correlationPatternDao.getById(pattern1.id)
        val retrieved2 = correlationPatternDao.getById(pattern2.id)
        val nonExistent = correlationPatternDao.getById("non-existent-id")

        // Assert
        assertNotNull("First pattern should exist", retrieved1)
        assertNotNull("Second pattern should exist", retrieved2)
        assertNull("Non-existent pattern should be null", nonExistent)

        assertEquals("First pattern food should match", "Apple", retrieved1!!.foodName)
        assertEquals("Second pattern food should match", "Banana", retrieved2!!.foodName)
    }

    @Test
    fun getAll_shouldReturnAllCorrelationPatterns() = runTest {
        // Arrange
        val patterns = listOf(
            createTestCorrelationPattern(foodName = "Dairy"),
            createTestCorrelationPattern(foodName = "Wheat"),
            createTestCorrelationPattern(foodName = "Nuts")
        )
        patterns.forEach { correlationPatternDao.insert(it) }

        // Act
        val allPatterns = correlationPatternDao.getAll()

        // Assert
        assertEquals("Should return all 3 patterns", 3, allPatterns.size)

        val foodNames = allPatterns.map { it.foodName }
        assertTrue("Should contain Dairy", foodNames.contains("Dairy"))
        assertTrue("Should contain Wheat", foodNames.contains("Wheat"))
        assertTrue("Should contain Nuts", foodNames.contains("Nuts"))
    }

    @Test
    fun getCorrelationsForFood_shouldReturnPatternsForSpecificFood() = runTest {
        // Arrange
        val dairyBloating = createTestCorrelationPattern(
            foodName = "Dairy",
            symptomType = SymptomType.BLOATING,
            correlationStrength = 0.7f
        )
        val dairyPain = createTestCorrelationPattern(
            foodName = "Dairy",
            symptomType = SymptomType.ABDOMINAL_PAIN,
            correlationStrength = 0.65f
        )
        val glutenPain = createTestCorrelationPattern(
            foodName = "Gluten",
            symptomType = SymptomType.ABDOMINAL_PAIN,
            correlationStrength = 0.8f
        )

        correlationPatternDao.insert(dairyBloating)
        correlationPatternDao.insert(dairyPain)
        correlationPatternDao.insert(glutenPain)

        // Act
        val dairyCorrelations = correlationPatternDao.getCorrelationsForFood("Dairy")
        val glutenCorrelations = correlationPatternDao.getCorrelationsForFood("Gluten")
        val nonExistentCorrelations = correlationPatternDao.getCorrelationsForFood("NonExistent")

        // Assert
        assertEquals("Should find 2 dairy correlations", 2, dairyCorrelations.size)
        assertEquals("Should find 1 gluten correlation", 1, glutenCorrelations.size)
        assertEquals("Should find 0 non-existent correlations", 0, nonExistentCorrelations.size)

        dairyCorrelations.forEach { pattern ->
            assertEquals("All results should be for Dairy", "Dairy", pattern.foodName)
        }

        val dairySymptoms = dairyCorrelations.map { it.symptomType }
        assertTrue("Should include bloating", dairySymptoms.contains(SymptomType.BLOATING))
        assertTrue("Should include abdominal pain", dairySymptoms.contains(SymptomType.ABDOMINAL_PAIN))
    }

    @Test
    fun getCorrelationsForSymptom_shouldReturnPatternsForSpecificSymptom() = runTest {
        // Arrange
        val dairyPain = createTestCorrelationPattern(
            foodName = "Dairy",
            symptomType = SymptomType.ABDOMINAL_PAIN
        )
        val glutenPain = createTestCorrelationPattern(
            foodName = "Gluten",
            symptomType = SymptomType.ABDOMINAL_PAIN
        )
        val dairyBloating = createTestCorrelationPattern(
            foodName = "Dairy",
            symptomType = SymptomType.BLOATING
        )

        correlationPatternDao.insert(dairyPain)
        correlationPatternDao.insert(glutenPain)
        correlationPatternDao.insert(dairyBloating)

        // Act
        val painCorrelations = correlationPatternDao.getCorrelationsForSymptom(SymptomType.ABDOMINAL_PAIN)
        val bloatingCorrelations = correlationPatternDao.getCorrelationsForSymptom(SymptomType.BLOATING)
        val nauseaCorrelations = correlationPatternDao.getCorrelationsForSymptom(SymptomType.NAUSEA)

        // Assert
        assertEquals("Should find 2 abdominal pain correlations", 2, painCorrelations.size)
        assertEquals("Should find 1 bloating correlation", 1, bloatingCorrelations.size)
        assertEquals("Should find 0 nausea correlations", 0, nauseaCorrelations.size)

        painCorrelations.forEach { pattern ->
            assertEquals("All results should be for abdominal pain", SymptomType.ABDOMINAL_PAIN, pattern.symptomType)
        }

        val painFoods = painCorrelations.map { it.foodName }
        assertTrue("Should include Dairy", painFoods.contains("Dairy"))
        assertTrue("Should include Gluten", painFoods.contains("Gluten"))
    }

    @Test
    fun getByConfidenceRange_shouldReturnPatternsWithinConfidenceRange() = runTest {
        // Arrange
        val lowConfidence = createTestCorrelationPattern(
            foodName = "Low",
            confidence = 0.7f,
            correlationStrength = 0.5f
        )
        val mediumConfidence = createTestCorrelationPattern(
            foodName = "Medium",
            confidence = 0.85f,
            correlationStrength = 0.65f
        )
        val highConfidence = createTestCorrelationPattern(
            foodName = "High",
            confidence = 0.95f,
            correlationStrength = 0.8f
        )

        correlationPatternDao.insert(lowConfidence)
        correlationPatternDao.insert(mediumConfidence)
        correlationPatternDao.insert(highConfidence)

        // Act
        val mediumToHigh = correlationPatternDao.getByConfidenceRange(0.8f, 1.0f)
        val lowToMedium = correlationPatternDao.getByConfidenceRange(0.6f, 0.9f)

        // Assert
        assertEquals("Should find 2 patterns in medium-high confidence range", 2, mediumToHigh.size)
        assertEquals("Should find 2 patterns in low-medium confidence range", 2, lowToMedium.size)

        mediumToHigh.forEach { pattern ->
            assertTrue("All results should be in confidence range", pattern.confidence in 0.8f..1.0f)
        }

        val highConfidenceFoods = mediumToHigh.map { it.foodName }
        assertTrue("Should include Medium confidence", highConfidenceFoods.contains("Medium"))
        assertTrue("Should include High confidence", highConfidenceFoods.contains("High"))
    }

    @Test
    fun getSignificantPatterns_shouldReturnStatisticallySignificantPatterns() = runTest {
        // Arrange
        val significantPattern = createTestCorrelationPattern(
            foodName = "Significant",
            correlationStrength = 0.7f,
            confidence = 0.95f,
            occurrences = 15,
            pValue = 0.03f
        )
        val insignificantCorrelation = createTestCorrelationPattern(
            foodName = "Low Correlation",
            correlationStrength = 0.4f,
            confidence = 0.95f,
            occurrences = 15,
            pValue = 0.03f
        )
        val insignificantConfidence = createTestCorrelationPattern(
            foodName = "Low Confidence",
            correlationStrength = 0.7f,
            confidence = 0.8f,
            occurrences = 15,
            pValue = 0.03f
        )
        val insufficientData = createTestCorrelationPattern(
            foodName = "Insufficient Data",
            correlationStrength = 0.7f,
            confidence = 0.95f,
            occurrences = 5,
            pValue = 0.03f
        )

        correlationPatternDao.insert(significantPattern)
        correlationPatternDao.insert(insignificantCorrelation)
        correlationPatternDao.insert(insignificantConfidence)
        correlationPatternDao.insert(insufficientData)

        // Act
        val significantPatterns = correlationPatternDao.getSignificantPatterns()

        // Assert
        assertEquals("Should find 1 statistically significant pattern", 1, significantPatterns.size)
        assertEquals("Should return the significant pattern", "Significant", significantPatterns.first().foodName)

        val pattern = significantPatterns.first()
        assertTrue("Pattern should be statistically significant", pattern.isStatisticallySignificant)
        assertTrue("Correlation should be >= 0.6", pattern.correlationStrength >= 0.6f)
        assertTrue("Confidence should be >= 0.95", pattern.confidence >= 0.95f)
        assertTrue("Occurrences should be >= 10", pattern.occurrences >= 10)
        assertTrue("P-value should be < 0.05", pattern.pValue!! < 0.05f)
    }

    @Test
    fun getHighConfidencePatterns_shouldReturnHighConfidencePatterns() = runTest {
        // Arrange
        val highConfidencePattern = createTestCorrelationPattern(
            foodName = "High",
            correlationStrength = 0.8f,
            confidence = 0.9f
        )
        val lowConfidencePattern = createTestCorrelationPattern(
            foodName = "Low",
            correlationStrength = 0.6f,
            confidence = 0.8f
        )

        correlationPatternDao.insert(highConfidencePattern)
        correlationPatternDao.insert(lowConfidencePattern)

        // Act
        val highConfidencePatterns = correlationPatternDao.getHighConfidencePatterns()

        // Assert
        assertEquals("Should find 1 high confidence pattern", 1, highConfidencePatterns.size)
        assertEquals("Should return the high confidence pattern", "High", highConfidencePatterns.first().foodName)

        val pattern = highConfidencePatterns.first()
        assertTrue("Pattern should be high confidence", pattern.isHighConfidence)
        assertTrue("Correlation should be >= 0.7", pattern.correlationStrength >= 0.7f)
        assertTrue("Confidence should be >= 0.85", pattern.confidence >= 0.85f)
    }

    @Test
    fun getPatternsByTimeOffset_shouldReturnPatternsWithinTimeOffsetRange() = runTest {
        // Arrange
        val shortOffset = createTestCorrelationPattern(
            foodName = "Short",
            averageTimeOffsetMinutes = 30
        )
        val mediumOffset = createTestCorrelationPattern(
            foodName = "Medium",
            averageTimeOffsetMinutes = 120
        )
        val longOffset = createTestCorrelationPattern(
            foodName = "Long",
            averageTimeOffsetMinutes = 300
        )

        correlationPatternDao.insert(shortOffset)
        correlationPatternDao.insert(mediumOffset)
        correlationPatternDao.insert(longOffset)

        // Act
        val quickOnset = correlationPatternDao.getPatternsByTimeOffset(0, 60)
        val mediumOnset = correlationPatternDao.getPatternsByTimeOffset(60, 240)

        // Assert
        assertEquals("Should find 1 quick onset pattern", 1, quickOnset.size)
        assertEquals("Should find 2 medium onset patterns", 2, mediumOnset.size)

        assertEquals("Quick onset should be Short", "Short", quickOnset.first().foodName)

        val mediumOnsetFoods = mediumOnset.map { it.foodName }
        assertTrue("Should include Medium", mediumOnsetFoods.contains("Medium"))
        assertTrue("Should include Long", mediumOnsetFoods.contains("Long"))
    }

    @Test
    fun delete_shouldPermanentlyRemovePattern() = runTest {
        // Arrange
        val pattern = createTestCorrelationPattern()
        correlationPatternDao.insert(pattern)

        // Act
        val deleteCount = correlationPatternDao.delete(pattern)

        // Assert
        assertEquals("Should delete 1 pattern", 1, deleteCount)

        val retrieved = correlationPatternDao.getById(pattern.id)
        assertNull("Pattern should not exist after deletion", retrieved)
    }

    @Test
    fun deleteById_shouldRemovePatternById() = runTest {
        // Arrange
        val pattern = createTestCorrelationPattern()
        correlationPatternDao.insert(pattern)

        // Act
        val deleteCount = correlationPatternDao.deleteById(pattern.id)

        // Assert
        assertEquals("Should delete 1 pattern", 1, deleteCount)

        val retrieved = correlationPatternDao.getById(pattern.id)
        assertNull("Pattern should not exist after deletion", retrieved)
    }

    @Test
    fun deleteOldPatterns_shouldRemoveOutdatedPatterns() = runTest {
        // Arrange
        val currentTime = System.currentTimeMillis()
        val recentPattern = createTestCorrelationPattern(
            foodName = "Recent",
            lastCalculated = currentTime - (24 * 60 * 60 * 1000) // 1 day ago
        )
        val oldPattern = createTestCorrelationPattern(
            foodName = "Old",
            lastCalculated = currentTime - (8 * 24 * 60 * 60 * 1000) // 8 days ago
        )

        correlationPatternDao.insert(recentPattern)
        correlationPatternDao.insert(oldPattern)

        // Act
        val cutoffTime = currentTime - (7 * 24 * 60 * 60 * 1000) // 7 days ago
        val deleteCount = correlationPatternDao.deleteOldPatterns(cutoffTime)

        // Assert
        assertEquals("Should delete 1 old pattern", 1, deleteCount)

        val remainingPatterns = correlationPatternDao.getAll()
        assertEquals("Should have 1 remaining pattern", 1, remainingPatterns.size)
        assertEquals("Should keep the recent pattern", "Recent", remainingPatterns.first().foodName)
    }

    @Test
    fun entity_validation_shouldEnforceConstraints() {
        // Test entity validation rules

        // Valid pattern should work
        val validPattern = createTestCorrelationPattern()
        assertNotNull("Valid pattern should be created", validPattern)

        // Test correlation strength constraints
        try {
            createTestCorrelationPattern(correlationStrength = -0.1f)
            fail("Should throw exception for negative correlation strength")
        } catch (e: IllegalArgumentException) {
            assertTrue("Should contain correlation strength validation message",
                e.message?.contains("Correlation strength must be between 0.0 and 1.0") == true)
        }

        try {
            createTestCorrelationPattern(correlationStrength = 1.1f)
            fail("Should throw exception for correlation strength > 1.0")
        } catch (e: IllegalArgumentException) {
            assertTrue("Should contain correlation strength validation message",
                e.message?.contains("Correlation strength must be between 0.0 and 1.0") == true)
        }

        // Test confidence constraints
        try {
            createTestCorrelationPattern(confidence = 1.1f)
            fail("Should throw exception for confidence > 1.0")
        } catch (e: IllegalArgumentException) {
            assertTrue("Should contain confidence validation message",
                e.message?.contains("Confidence must be between 0.0 and 1.0") == true)
        }

        // Test occurrences constraint
        try {
            createTestCorrelationPattern(occurrences = 0)
            fail("Should throw exception for zero occurrences")
        } catch (e: IllegalArgumentException) {
            assertTrue("Should contain occurrences validation message",
                e.message?.contains("Occurrences must be positive") == true)
        }

        // Test time offset constraint
        try {
            createTestCorrelationPattern(averageTimeOffsetMinutes = -10)
            fail("Should throw exception for negative time offset")
        } catch (e: IllegalArgumentException) {
            assertTrue("Should contain time offset validation message",
                e.message?.contains("Time offset must be non-negative") == true)
        }
    }

    @Test
    fun entity_isStatisticallySignificant_shouldReturnCorrectValues() {
        // Test the statistical significance property

        // Significant pattern (meets all criteria)
        val significantPattern = createTestCorrelationPattern(
            correlationStrength = 0.7f,
            confidence = 0.95f,
            occurrences = 15,
            pValue = 0.03f
        )
        assertTrue("Should be statistically significant", significantPattern.isStatisticallySignificant)

        // Not significant due to low correlation
        val lowCorrelation = createTestCorrelationPattern(
            correlationStrength = 0.5f,
            confidence = 0.95f,
            occurrences = 15,
            pValue = 0.03f
        )
        assertFalse("Should not be significant due to low correlation", lowCorrelation.isStatisticallySignificant)

        // Not significant due to low confidence
        val lowConfidence = createTestCorrelationPattern(
            correlationStrength = 0.7f,
            confidence = 0.8f,
            occurrences = 15,
            pValue = 0.03f
        )
        assertFalse("Should not be significant due to low confidence", lowConfidence.isStatisticallySignificant)

        // Not significant due to insufficient data
        val insufficientData = createTestCorrelationPattern(
            correlationStrength = 0.7f,
            confidence = 0.95f,
            occurrences = 5,
            pValue = 0.03f
        )
        assertFalse("Should not be significant due to insufficient data", insufficientData.isStatisticallySignificant)

        // Not significant due to high p-value
        val highPValue = createTestCorrelationPattern(
            correlationStrength = 0.7f,
            confidence = 0.95f,
            occurrences = 15,
            pValue = 0.08f
        )
        assertFalse("Should not be significant due to high p-value", highPValue.isStatisticallySignificant)
    }

    @Test
    fun entity_isHighConfidence_shouldReturnCorrectValues() {
        // High confidence pattern
        val highConfidence = createTestCorrelationPattern(
            correlationStrength = 0.8f,
            confidence = 0.9f
        )
        assertTrue("Should be high confidence", highConfidence.isHighConfidence)

        // Not high confidence due to low correlation
        val lowCorrelation = createTestCorrelationPattern(
            correlationStrength = 0.6f,
            confidence = 0.9f
        )
        assertFalse("Should not be high confidence due to low correlation", lowCorrelation.isHighConfidence)

        // Not high confidence due to low confidence
        val lowConfidencePattern = createTestCorrelationPattern(
            correlationStrength = 0.8f,
            confidence = 0.8f
        )
        assertFalse("Should not be high confidence due to low confidence", lowConfidencePattern.isHighConfidence)
    }

    @Test
    fun entity_averageTimeOffsetHours_shouldConvertCorrectly() {
        val pattern = createTestCorrelationPattern(averageTimeOffsetMinutes = 120) // 2 hours
        assertEquals("Should convert minutes to hours correctly", 2.0f, pattern.averageTimeOffsetHours, 0.001f)

        val pattern30Min = createTestCorrelationPattern(averageTimeOffsetMinutes = 30) // 0.5 hours
        assertEquals("Should handle fractional hours", 0.5f, pattern30Min.averageTimeOffsetHours, 0.001f)
    }

    private fun createTestCorrelationPattern(
        foodName: String = "Test Food",
        symptomType: SymptomType = SymptomType.ABDOMINAL_PAIN,
        correlationStrength: Float = 0.7f,
        averageTimeOffsetMinutes: Int = 120,
        occurrences: Int = 12,
        confidence: Float = 0.9f,
        pValue: Float? = 0.04f,
        standardDeviation: Float? = 15.0f,
        minTimeOffset: Int? = 60,
        maxTimeOffset: Int? = 180,
        lastCalculated: Long = System.currentTimeMillis()
    ) = TriggerPattern.create(
        foodName = foodName,
        symptomType = symptomType,
        correlationStrength = correlationStrength,
        averageTimeOffsetMinutes = averageTimeOffsetMinutes,
        occurrences = occurrences,
        confidence = confidence,
        pValue = pValue,
        standardDeviation = standardDeviation,
        minTimeOffset = minTimeOffset,
        maxTimeOffset = maxTimeOffset
    ).copy(lastCalculated = lastCalculated)
}