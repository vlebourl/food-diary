package com.fooddiary.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fooddiary.data.database.dao.FoodEntryDao
import com.fooddiary.data.database.dao.SymptomEventDao
import com.fooddiary.data.database.dao.CorrelationPatternDao
import com.fooddiary.data.database.dao.TriggerPatternDao
import com.fooddiary.data.database.dao.EnvironmentalContextDao
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.CorrelationPattern
import com.fooddiary.data.database.entities.TriggerPattern
import com.fooddiary.data.database.entities.EnvironmentalContext
import com.fooddiary.data.models.*
import com.fooddiary.data.analysis.StatisticsEngine
import com.fooddiary.data.analysis.PatternDetector
import com.fooddiary.data.analysis.InsightGenerator
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * TDD Tests for AnalyticsRepository - THESE WILL FAIL INITIALLY
 *
 * Test coverage:
 * - Statistical analysis of food-symptom correlations
 * - Pattern detection across time periods
 * - Insight generation from data trends
 * - Trigger food identification
 * - Health metrics calculation
 * - Predictive analytics
 */
class AnalyticsRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var foodEntryDao: FoodEntryDao

    @MockK
    private lateinit var symptomEventDao: SymptomEventDao

    @MockK
    private lateinit var correlationPatternDao: CorrelationPatternDao

    @MockK
    private lateinit var triggerPatternDao: TriggerPatternDao

    @MockK
    private lateinit var environmentalContextDao: EnvironmentalContextDao

    @MockK
    private lateinit var statisticsEngine: StatisticsEngine

    @MockK
    private lateinit var patternDetector: PatternDetector

    @MockK
    private lateinit var insightGenerator: InsightGenerator

    private lateinit var repository: AnalyticsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        // This will fail initially since AnalyticsRepository doesn't exist yet
        repository = AnalyticsRepository(
            foodEntryDao = foodEntryDao,
            symptomEventDao = symptomEventDao,
            correlationPatternDao = correlationPatternDao,
            triggerPatternDao = triggerPatternDao,
            environmentalContextDao = environmentalContextDao,
            statisticsEngine = statisticsEngine,
            patternDetector = patternDetector,
            insightGenerator = insightGenerator
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `calculateCorrelations should analyze food-symptom relationships over time period`() = runTest {
        // Arrange
        val startDate = LocalDate.now().minusDays(30)
        val endDate = LocalDate.now()

        val foodEntries = listOf(
            createTestFoodEntry(foods = listOf("Dairy"), timestamp = Instant.now().minus(2, ChronoUnit.HOURS)),
            createTestFoodEntry(foods = listOf("Gluten"), timestamp = Instant.now().minus(6, ChronoUnit.HOURS))
        )

        val symptoms = listOf(
            createTestSymptomEvent(symptomType = SymptomType.ABDOMINAL_PAIN, timestamp = Instant.now())
        )

        val correlationResults = CorrelationAnalysis(
            strongCorrelations = listOf(
                CorrelationResult(
                    foodName = "Dairy",
                    symptomType = SymptomType.ABDOMINAL_PAIN,
                    correlationStrength = 0.85f,
                    confidence = ConfidenceLevel.HIGH,
                    occurrenceCount = 8,
                    averageTimeOffset = Duration.ofHours(2)
                )
            ),
            moderateCorrelations = listOf(),
            weakCorrelations = listOf(),
            analysisTimespan = Duration.ofDays(30)
        )

        coEvery { foodEntryDao.getEntriesInDateRange(startDate, endDate) } returns flowOf(foodEntries)
        coEvery { symptomEventDao.getEventsInDateRange(startDate, endDate) } returns flowOf(symptoms)
        coEvery { statisticsEngine.calculateCorrelations(foodEntries, symptoms) } returns correlationResults

        // Act
        val analysis = repository.calculateCorrelations(startDate, endDate).first()

        // Assert
        assertNotNull("Should provide correlation analysis", analysis)
        assertEquals("Should identify strong correlations", 1, analysis.strongCorrelations.size)

        val strongCorrelation = analysis.strongCorrelations.first()
        assertEquals("Should identify dairy as trigger", "Dairy", strongCorrelation.foodName)
        assertEquals("Should correlate with abdominal pain", SymptomType.ABDOMINAL_PAIN, strongCorrelation.symptomType)
        assertEquals("Should have high correlation strength", 0.85f, strongCorrelation.correlationStrength, 0.01f)

        coVerify { statisticsEngine.calculateCorrelations(foodEntries, symptoms) }
    }

    @Test
    fun `getStatistics should provide comprehensive health metrics`() = runTest {
        // Arrange
        val timeRange = AnalysisTimeRange.LAST_30_DAYS
        val baseStats = HealthStatistics(
            totalFoodEntries = 45,
            totalSymptomEvents = 12,
            averageSymptomSeverity = 5.2f,
            symptomFreeDays = 18,
            mostCommonSymptom = SymptomType.BLOATING,
            mostTriggeringFood = "Dairy",
            averageTimeToSymptom = Duration.ofHours(3),
            symptomsPerWeek = 2.8f,
            improvementTrend = TrendDirection.IMPROVING
        )

        coEvery { foodEntryDao.getTotalCount(any(), any()) } returns 45
        coEvery { symptomEventDao.getTotalCount(any(), any()) } returns 12
        coEvery { statisticsEngine.calculateHealthStatistics(any()) } returns baseStats

        // Act
        val statistics = repository.getStatistics(timeRange).first()

        // Assert
        assertNotNull("Should provide health statistics", statistics)
        assertEquals("Should count total food entries", 45, statistics.totalFoodEntries)
        assertEquals("Should count total symptom events", 12, statistics.totalSymptomEvents)
        assertEquals("Should calculate average severity", 5.2f, statistics.averageSymptomSeverity, 0.1f)
        assertEquals("Should count symptom-free days", 18, statistics.symptomFreeDays)
        assertEquals("Should identify most common symptom", SymptomType.BLOATING, statistics.mostCommonSymptom)
        assertEquals("Should show improving trend", TrendDirection.IMPROVING, statistics.improvementTrend)

        coVerify { statisticsEngine.calculateHealthStatistics(any()) }
    }

    @Test
    fun `getTriggerPatterns should identify foods that consistently cause symptoms`() = runTest {
        // Arrange
        val triggerPatterns = listOf(
            TriggerAnalysisResult(
                triggerFood = "Dairy",
                associatedSymptoms = listOf(
                    SymptomTriggerInfo(
                        symptomType = SymptomType.BLOATING,
                        frequency = 0.85f,
                        averageSeverity = 6.2f,
                        averageOnsetTime = Duration.ofHours(2)
                    ),
                    SymptomTriggerInfo(
                        symptomType = SymptomType.ABDOMINAL_PAIN,
                        frequency = 0.65f,
                        averageSeverity = 7.1f,
                        averageOnsetTime = Duration.ofHours(1)
                    )
                ),
                overallTriggerScore = 0.92f,
                confidence = ConfidenceLevel.VERY_HIGH,
                recommendedAction = RecommendedAction.ELIMINATE
            )
        )

        coEvery { triggerPatternDao.getHighConfidencePatterns(any()) } returns triggerPatterns
        coEvery { patternDetector.analyzeTriggerPatterns(any(), any()) } returns triggerPatterns

        // Act
        val patterns = repository.getTriggerPatterns(minConfidence = 0.7f).first()

        // Assert
        assertEquals("Should identify trigger patterns", 1, patterns.size)

        val pattern = patterns.first()
        assertEquals("Should identify dairy as trigger", "Dairy", pattern.triggerFood)
        assertEquals("Should have high trigger score", 0.92f, pattern.overallTriggerScore, 0.01f)
        assertEquals("Should recommend elimination", RecommendedAction.ELIMINATE, pattern.recommendedAction)
        assertEquals("Should have very high confidence", ConfidenceLevel.VERY_HIGH, pattern.confidence)
        assertEquals("Should associate with 2 symptoms", 2, pattern.associatedSymptoms.size)

        coVerify { patternDetector.analyzeTriggerPatterns(any(), any()) }
    }

    @Test
    fun `generateInsights should provide actionable recommendations`() = runTest {
        // Arrange
        val analysisData = AnalysisData(
            foodEntries = listOf(createTestFoodEntry()),
            symptoms = listOf(createTestSymptomEvent()),
            correlations = listOf(),
            environmentalData = listOf()
        )

        val insights = listOf(
            HealthInsight(
                id = "insight-1",
                type = InsightType.TRIGGER_FOOD_IDENTIFIED,
                title = "Dairy May Be Triggering Symptoms",
                description = "Based on 30 days of data, dairy products show a strong correlation with bloating symptoms occurring within 2-3 hours of consumption.",
                priority = InsightPriority.HIGH,
                confidence = ConfidenceLevel.HIGH,
                recommendations = listOf(
                    "Consider eliminating dairy for 2 weeks",
                    "Try lactose-free alternatives",
                    "Consult with a healthcare provider"
                ),
                supportingData = mapOf(
                    "correlation_strength" to "0.85",
                    "occurrence_count" to "8",
                    "average_severity" to "6.2"
                )
            )
        )

        coEvery { insightGenerator.generateInsights(any()) } returns insights

        // Act
        val generatedInsights = repository.generateInsights().first()

        // Assert
        assertEquals("Should generate insights", 1, generatedInsights.size)

        val insight = generatedInsights.first()
        assertEquals("Should identify trigger food insight", InsightType.TRIGGER_FOOD_IDENTIFIED, insight.type)
        assertEquals("Should have high priority", InsightPriority.HIGH, insight.priority)
        assertEquals("Should have actionable recommendations", 3, insight.recommendations.size)
        assertTrue("Should include elimination recommendation",
            insight.recommendations.any { it.contains("eliminating dairy") })

        coVerify { insightGenerator.generateInsights(any()) }
    }

    @Test
    fun `analyzeTrends should identify patterns over different time periods`() = runTest {
        // Arrange
        val trendAnalysis = TrendAnalysis(
            overallTrend = TrendDirection.IMPROVING,
            weeklyTrends = mapOf(
                1 to WeeklyTrend(averageSeverity = 6.5f, symptomCount = 8, mainTriggers = listOf("Dairy")),
                2 to WeeklyTrend(averageSeverity = 5.8f, symptomCount = 6, mainTriggers = listOf("Gluten")),
                3 to WeeklyTrend(averageSeverity = 4.2f, symptomCount = 4, mainTriggers = listOf()),
                4 to WeeklyTrend(averageSeverity = 3.9f, symptomCount = 3, mainTriggers = listOf())
            ),
            seasonalPatterns = listOf(
                SeasonalPattern(
                    season = "Spring",
                    symptomIncrease = 0.15f,
                    likelyTriggers = listOf("Pollen-related foods")
                )
            ),
            dayOfWeekPatterns = mapOf(
                "FRIDAY" to 1.2f, // 20% more symptoms on Fridays
                "SATURDAY" to 0.8f // 20% fewer symptoms on Saturdays
            )
        )

        coEvery { patternDetector.analyzeTrends(any(), any()) } returns trendAnalysis

        // Act
        val trends = repository.analyzeTrends(AnalysisTimeRange.LAST_90_DAYS).first()

        // Assert
        assertNotNull("Should provide trend analysis", trends)
        assertEquals("Should show improving trend", TrendDirection.IMPROVING, trends.overallTrend)
        assertEquals("Should analyze 4 weeks", 4, trends.weeklyTrends.size)
        assertTrue("Should show improvement over time",
            trends.weeklyTrends[4]!!.averageSeverity < trends.weeklyTrends[1]!!.averageSeverity)
        assertFalse("Should identify day-of-week patterns", trends.dayOfWeekPatterns.isEmpty())

        coVerify { patternDetector.analyzeTrends(any(), any()) }
    }

    @Test
    fun `predictSymptomRisk should estimate likelihood of future symptoms`() = runTest {
        // Arrange
        val riskPrediction = SymptomRiskPrediction(
            overallRiskScore = 0.65f,
            riskLevel = RiskLevel.MODERATE,
            predictedSymptoms = listOf(
                PredictedSymptom(
                    symptomType = SymptomType.BLOATING,
                    probability = 0.72f,
                    estimatedSeverity = 5.8f,
                    likelyTriggers = listOf("Dairy", "High FODMAP foods"),
                    preventionTips = listOf("Avoid dairy today", "Limit portion sizes")
                )
            ),
            timeWindow = Duration.ofHours(24),
            basedOnFactors = listOf("Recent dairy consumption", "Stress levels", "Historical patterns")
        )

        coEvery { statisticsEngine.predictSymptomRisk(any(), any()) } returns riskPrediction

        // Act
        val prediction = repository.predictSymptomRisk(
            timeWindow = Duration.ofHours(24),
            basedOnRecentDays = 7
        )

        // Assert
        assertEquals("Should predict moderate risk", RiskLevel.MODERATE, prediction.riskLevel)
        assertEquals("Should have risk score", 0.65f, prediction.overallRiskScore, 0.01f)
        assertEquals("Should predict specific symptoms", 1, prediction.predictedSymptoms.size)

        val predictedSymptom = prediction.predictedSymptoms.first()
        assertEquals("Should predict bloating", SymptomType.BLOATING, predictedSymptom.symptomType)
        assertTrue("Should have reasonable probability", predictedSymptom.probability > 0.5f)
        assertFalse("Should provide prevention tips", predictedSymptom.preventionTips.isEmpty())

        coVerify { statisticsEngine.predictSymptomRisk(any(), any()) }
    }

    @Test
    fun `calculateFODMAPRisk should assess diet based on FODMAP levels`() = runTest {
        // Arrange
        val recentFoods = listOf(
            createTestFoodEntry(foods = listOf("Wheat Bread", "Garlic", "Onions")), // High FODMAP
            createTestFoodEntry(foods = listOf("Rice", "Chicken", "Carrots")) // Low FODMAP
        )

        val fodmapRisk = FODMAPRiskAssessment(
            overallRisk = FODMAPRiskLevel.HIGH,
            riskScore = 0.78f,
            highRiskFoods = listOf("Wheat Bread", "Garlic", "Onions"),
            fodmapBreakdown = FODMAPBreakdown(
                oligosaccharides = FODMAPLevel.HIGH,
                disaccharides = FODMAPLevel.LOW,
                monosaccharides = FODMAPLevel.MEDIUM,
                polyols = FODMAPLevel.LOW
            ),
            recommendations = listOf(
                "Consider low-FODMAP alternatives",
                "Reduce wheat-based products",
                "Limit garlic and onion consumption"
            )
        )

        coEvery { foodEntryDao.getRecentEntries(any()) } returns flowOf(recentFoods)
        coEvery { statisticsEngine.calculateFODMAPRisk(any()) } returns fodmapRisk

        // Act
        val riskAssessment = repository.calculateFODMAPRisk(days = 7).first()

        // Assert
        assertEquals("Should assess high FODMAP risk", FODMAPRiskLevel.HIGH, riskAssessment.overallRisk)
        assertEquals("Should have high risk score", 0.78f, riskAssessment.riskScore, 0.01f)
        assertEquals("Should identify high-risk foods", 3, riskAssessment.highRiskFoods.size)
        assertTrue("Should recommend low-FODMAP alternatives",
            riskAssessment.recommendations.any { it.contains("low-FODMAP alternatives") })

        coVerify { statisticsEngine.calculateFODMAPRisk(recentFoods) }
    }

    @Test
    fun `getComparisonAnalysis should compare different time periods`() = runTest {
        // Arrange
        val comparison = PeriodComparison(
            currentPeriod = PeriodStats(
                averageSeverity = 4.2f,
                symptomCount = 8,
                symptomFreeDays = 15,
                topTriggers = listOf("Dairy", "Gluten")
            ),
            previousPeriod = PeriodStats(
                averageSeverity = 6.8f,
                symptomCount = 18,
                symptomFreeDays = 8,
                topTriggers = listOf("Dairy", "Spicy Food", "Alcohol")
            ),
            improvementMetrics = ImprovementMetrics(
                severityImprovement = 0.38f, // 38% improvement
                symptomReduction = 0.56f,    // 56% fewer symptoms
                symptomFreeDayIncrease = 0.88f, // 88% more symptom-free days
                overallImprovement = 0.61f   // 61% overall improvement
            ),
            insights = listOf("Significant reduction in severe symptoms", "Elimination of trigger foods showing positive results")
        )

        coEvery { statisticsEngine.compareTimePeriods(any(), any()) } returns comparison

        // Act
        val comparisonResult = repository.getComparisonAnalysis(
            currentPeriod = AnalysisTimeRange.LAST_30_DAYS,
            previousPeriod = AnalysisTimeRange.PREVIOUS_30_DAYS
        ).first()

        // Assert
        assertNotNull("Should provide comparison analysis", comparisonResult)
        assertTrue("Should show severity improvement", comparisonResult.improvementMetrics.severityImprovement > 0)
        assertTrue("Should show symptom reduction", comparisonResult.improvementMetrics.symptomReduction > 0)
        assertTrue("Should show more symptom-free days", comparisonResult.improvementMetrics.symptomFreeDayIncrease > 0)
        assertFalse("Should provide insights", comparisonResult.insights.isEmpty())

        coVerify { statisticsEngine.compareTimePeriods(any(), any()) }
    }

    // Test helper methods
    private fun createTestFoodEntry(
        foods: List<String> = listOf("Test Food"),
        timestamp: Instant = Instant.now()
    ) = FoodEntry(
        id = kotlin.random.Random.nextLong(1, 1000),
        timestamp = timestamp,
        mealType = MealType.BREAKFAST,
        foods = foods,
        portions = foods.associateWith { "1 serving" },
        notes = null
    )

    private fun createTestSymptomEvent(
        symptomType: SymptomType = SymptomType.ABDOMINAL_PAIN,
        timestamp: Instant = Instant.now(),
        severity: Int = 5
    ) = SymptomEvent(
        id = kotlin.random.Random.nextLong(1, 1000),
        timestamp = timestamp,
        symptomType = symptomType,
        severity = severity,
        duration = null,
        notes = null
    )
}

/**
 * Supporting data classes that would be defined in the repository
 */
data class CorrelationAnalysis(
    val strongCorrelations: List<CorrelationResult>,
    val moderateCorrelations: List<CorrelationResult>,
    val weakCorrelations: List<CorrelationResult>,
    val analysisTimespan: Duration
)

data class CorrelationResult(
    val foodName: String,
    val symptomType: SymptomType,
    val correlationStrength: Float,
    val confidence: ConfidenceLevel,
    val occurrenceCount: Int,
    val averageTimeOffset: Duration
)

data class HealthStatistics(
    val totalFoodEntries: Int,
    val totalSymptomEvents: Int,
    val averageSymptomSeverity: Float,
    val symptomFreeDays: Int,
    val mostCommonSymptom: SymptomType,
    val mostTriggeringFood: String,
    val averageTimeToSymptom: Duration,
    val symptomsPerWeek: Float,
    val improvementTrend: TrendDirection
)

data class TriggerAnalysisResult(
    val triggerFood: String,
    val associatedSymptoms: List<SymptomTriggerInfo>,
    val overallTriggerScore: Float,
    val confidence: ConfidenceLevel,
    val recommendedAction: RecommendedAction
)

data class SymptomTriggerInfo(
    val symptomType: SymptomType,
    val frequency: Float,
    val averageSeverity: Float,
    val averageOnsetTime: Duration
)

data class HealthInsight(
    val id: String,
    val type: InsightType,
    val title: String,
    val description: String,
    val priority: InsightPriority,
    val confidence: ConfidenceLevel,
    val recommendations: List<String>,
    val supportingData: Map<String, String>
)

data class TrendAnalysis(
    val overallTrend: TrendDirection,
    val weeklyTrends: Map<Int, WeeklyTrend>,
    val seasonalPatterns: List<SeasonalPattern>,
    val dayOfWeekPatterns: Map<String, Float>
)

data class WeeklyTrend(
    val averageSeverity: Float,
    val symptomCount: Int,
    val mainTriggers: List<String>
)

data class SeasonalPattern(
    val season: String,
    val symptomIncrease: Float,
    val likelyTriggers: List<String>
)

data class SymptomRiskPrediction(
    val overallRiskScore: Float,
    val riskLevel: RiskLevel,
    val predictedSymptoms: List<PredictedSymptom>,
    val timeWindow: Duration,
    val basedOnFactors: List<String>
)

data class PredictedSymptom(
    val symptomType: SymptomType,
    val probability: Float,
    val estimatedSeverity: Float,
    val likelyTriggers: List<String>,
    val preventionTips: List<String>
)

data class FODMAPRiskAssessment(
    val overallRisk: FODMAPRiskLevel,
    val riskScore: Float,
    val highRiskFoods: List<String>,
    val fodmapBreakdown: FODMAPBreakdown,
    val recommendations: List<String>
)

data class FODMAPBreakdown(
    val oligosaccharides: FODMAPLevel,
    val disaccharides: FODMAPLevel,
    val monosaccharides: FODMAPLevel,
    val polyols: FODMAPLevel
)

data class PeriodComparison(
    val currentPeriod: PeriodStats,
    val previousPeriod: PeriodStats,
    val improvementMetrics: ImprovementMetrics,
    val insights: List<String>
)

data class PeriodStats(
    val averageSeverity: Float,
    val symptomCount: Int,
    val symptomFreeDays: Int,
    val topTriggers: List<String>
)

data class ImprovementMetrics(
    val severityImprovement: Float,
    val symptomReduction: Float,
    val symptomFreeDayIncrease: Float,
    val overallImprovement: Float
)

data class AnalysisData(
    val foodEntries: List<FoodEntry>,
    val symptoms: List<SymptomEvent>,
    val correlations: List<CorrelationPattern>,
    val environmentalData: List<EnvironmentalContext>
)

enum class AnalysisTimeRange {
    LAST_7_DAYS, LAST_30_DAYS, LAST_90_DAYS, PREVIOUS_30_DAYS
}

enum class TrendDirection {
    IMPROVING, DECLINING, STABLE, FLUCTUATING
}

enum class InsightType {
    TRIGGER_FOOD_IDENTIFIED, IMPROVEMENT_DETECTED, PATTERN_DISCOVERED, RISK_WARNING
}

enum class InsightPriority {
    LOW, MEDIUM, HIGH, CRITICAL
}

enum class RecommendedAction {
    MONITOR, REDUCE, ELIMINATE, CONSULT_DOCTOR
}

enum class RiskLevel {
    LOW, MODERATE, HIGH, VERY_HIGH
}

enum class FODMAPRiskLevel {
    LOW, MODERATE, HIGH
}