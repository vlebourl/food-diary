// Repository Interface Contracts
// IBS Food & Symptom Tracking Application

package com.fooddiary.data.repository

import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.time.LocalDate

/**
 * Core repository for food entry management
 */
interface FoodEntryRepository {
    suspend fun insert(entry: FoodEntry): String
    suspend fun update(entry: FoodEntry)
    suspend fun softDelete(id: String)
    suspend fun getById(id: String): FoodEntry?
    fun getAllByDateRange(start: LocalDate, end: LocalDate): Flow<List<FoodEntry>>
    fun getRecent(limit: Int = 10): Flow<List<FoodEntry>>
    fun searchByName(query: String): Flow<List<FoodEntry>>
    suspend fun getMostFrequent(limit: Int = 5): List<FoodEntry>
}

/**
 * Repository for beverage tracking
 */
interface BeverageEntryRepository {
    suspend fun insert(entry: BeverageEntry): String
    suspend fun update(entry: BeverageEntry)
    suspend fun softDelete(id: String)
    suspend fun getById(id: String): BeverageEntry?
    fun getAllByDateRange(start: LocalDate, end: LocalDate): Flow<List<BeverageEntry>>
    fun getCaffeineIntake(date: LocalDate): Flow<Float>
    fun getHydration(date: LocalDate): Flow<Float>
}

/**
 * Repository for symptom event tracking
 */
interface SymptomEventRepository {
    suspend fun insert(event: SymptomEvent): String
    suspend fun update(event: SymptomEvent)
    suspend fun softDelete(id: String)
    suspend fun getById(id: String): SymptomEvent?
    fun getAllByDateRange(start: LocalDate, end: LocalDate): Flow<List<SymptomEvent>>
    fun getByType(type: SymptomType): Flow<List<SymptomEvent>>
    fun getBySeverity(minSeverity: Int): Flow<List<SymptomEvent>>
    fun getActiveSymptoms(): Flow<List<SymptomEvent>>
}

/**
 * Repository for environmental context tracking
 */
interface EnvironmentalContextRepository {
    suspend fun insertOrUpdate(context: EnvironmentalContext)
    suspend fun getByDate(date: LocalDate): EnvironmentalContext?
    fun getByDateRange(start: LocalDate, end: LocalDate): Flow<List<EnvironmentalContext>>
    fun getAverageStress(days: Int): Flow<Float>
    fun getAverageSleep(days: Int): Flow<Float>
}

/**
 * Repository for quick entry template management
 */
interface QuickEntryTemplateRepository {
    suspend fun insert(template: QuickEntryTemplate): String
    suspend fun update(template: QuickEntryTemplate)
    suspend fun delete(id: String)
    fun getAllActive(): Flow<List<QuickEntryTemplate>>
    suspend fun reorder(templates: List<QuickEntryTemplate>)
    suspend fun getById(id: String): QuickEntryTemplate?
}

/**
 * Repository for trigger pattern analysis
 */
interface TriggerPatternRepository {
    suspend fun insert(pattern: TriggerPattern)
    suspend fun update(pattern: TriggerPattern)
    fun getAll(): Flow<List<TriggerPattern>>
    fun getBySymptomType(type: SymptomType): Flow<List<TriggerPattern>>
    fun getByFood(foodName: String): Flow<List<TriggerPattern>>
    fun getHighConfidence(minConfidence: Float = 0.7f): Flow<List<TriggerPattern>>
    suspend fun recalculatePatterns()
}

/**
 * Repository for elimination protocol management
 */
interface EliminationProtocolRepository {
    suspend fun insert(protocol: EliminationProtocol): String
    suspend fun update(protocol: EliminationProtocol)
    suspend fun getActive(): EliminationProtocol?
    fun getAll(): Flow<List<EliminationProtocol>>
    suspend fun advancePhase(protocolId: String, newPhase: EliminationPhase)
    suspend fun recordReintroduction(protocolId: String, food: ReintroducedFood)
}

/**
 * Repository for medical report generation
 */
interface MedicalReportRepository {
    suspend fun generateReport(
        startDate: LocalDate,
        endDate: LocalDate,
        format: ReportFormat,
        sections: List<ReportSection>
    ): MedicalReport
    suspend fun getById(id: String): MedicalReport?
    fun getAll(): Flow<List<MedicalReport>>
    suspend fun deleteReport(id: String)
    suspend fun exportReport(reportId: String): String // returns file path
}

/**
 * Repository for FODMAP food database
 */
interface FODMAPRepository {
    suspend fun searchFood(query: String): List<FODMAPFood>
    suspend fun getFoodById(id: String): FODMAPFood?
    suspend fun getFoodsByLevel(level: FODMAPLevel): List<FODMAPFood>
    suspend fun getFoodsByCategory(category: FoodCategory): List<FODMAPFood>
    suspend fun analyzeMeal(ingredients: List<String>): FODMAPAnalysis
}

/**
 * Repository for correlation analysis
 */
interface CorrelationRepository {
    suspend fun calculateCorrelations(
        startDate: LocalDate,
        endDate: LocalDate
    ): List<SymptomFoodCorrelation>

    fun getCorrelationsForSymptom(
        symptomId: String
    ): Flow<List<SymptomFoodCorrelation>>

    fun getCorrelationsForFood(
        foodId: String
    ): Flow<List<SymptomFoodCorrelation>>

    suspend fun getTimeWindowAnalysis(
        foodId: String,
        windowHours: Int = 48
    ): TimeWindowAnalysis
}

/**
 * Analysis result data classes
 */
data class FODMAPAnalysis(
    val overallLevel: FODMAPLevel,
    val oligosaccharides: FODMAPLevel,
    val disaccharides: FODMAPLevel,
    val monosaccharides: FODMAPLevel,
    val polyols: FODMAPLevel,
    val problematicIngredients: List<String>
)

data class TimeWindowAnalysis(
    val foodId: String,
    val symptomOccurrences: Map<Int, List<SymptomEvent>>, // hour -> symptoms
    val peakHour: Int,
    val totalSymptoms: Int
)