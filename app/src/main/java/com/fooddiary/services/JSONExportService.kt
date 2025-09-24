package com.fooddiary.services

import android.content.Context
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.models.BristolStoolType
import com.fooddiary.data.models.SymptomType
import com.fooddiary.domain.analysis.CorrelationAnalysis
import com.fooddiary.domain.analysis.TriggerPattern
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.io.File
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JSONExportService @Inject constructor(
    private val context: Context
) {

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    suspend fun exportCompleteData(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        correlations: List<CorrelationAnalysis>,
        triggerPatterns: List<TriggerPattern>,
        startDate: LocalDate,
        endDate: LocalDate,
        outputFile: File
    ): Result<File> = withContext(Dispatchers.IO) {
        try {
            val exportData = FoodDiaryExport(
                metadata = ExportMetadata(
                    version = SCHEMA_VERSION,
                    exportDate = Instant.now(),
                    startDate = startDate,
                    endDate = endDate,
                    appVersion = getAppVersion(),
                    totalFoodEntries = foodEntries.size,
                    totalSymptomEvents = symptomEvents.size
                ),
                foodEntries = foodEntries.map { it.toExportModel() },
                symptomEvents = symptomEvents.map { it.toExportModel() },
                correlations = correlations.map { it.toExportModel() },
                triggerPatterns = triggerPatterns.map { it.toExportModel() },
                schema = getSchemaDefinition()
            )

            val jsonString = json.encodeToString(exportData)
            outputFile.writeText(jsonString)

            // Validate the exported JSON
            validateExportedJSON(jsonString)

            Result.success(outputFile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun exportFoodEntriesOnly(
        foodEntries: List<FoodEntry>,
        startDate: LocalDate,
        endDate: LocalDate,
        outputFile: File
    ): Result<File> = withContext(Dispatchers.IO) {
        try {
            val exportData = FoodEntriesExport(
                metadata = ExportMetadata(
                    version = SCHEMA_VERSION,
                    exportDate = Instant.now(),
                    startDate = startDate,
                    endDate = endDate,
                    appVersion = getAppVersion(),
                    totalFoodEntries = foodEntries.size,
                    totalSymptomEvents = 0
                ),
                foodEntries = foodEntries.map { it.toExportModel() },
                schema = getSchemaDefinition()
            )

            val jsonString = json.encodeToString(exportData)
            outputFile.writeText(jsonString)

            Result.success(outputFile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun exportSymptomEventsOnly(
        symptomEvents: List<SymptomEvent>,
        startDate: LocalDate,
        endDate: LocalDate,
        outputFile: File
    ): Result<File> = withContext(Dispatchers.IO) {
        try {
            val exportData = SymptomEventsExport(
                metadata = ExportMetadata(
                    version = SCHEMA_VERSION,
                    exportDate = Instant.now(),
                    startDate = startDate,
                    endDate = endDate,
                    appVersion = getAppVersion(),
                    totalFoodEntries = 0,
                    totalSymptomEvents = symptomEvents.size
                ),
                symptomEvents = symptomEvents.map { it.toExportModel() },
                schema = getSchemaDefinition()
            )

            val jsonString = json.encodeToString(exportData)
            outputFile.writeText(jsonString)

            Result.success(outputFile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun exportAnalyticsOnly(
        correlations: List<CorrelationAnalysis>,
        triggerPatterns: List<TriggerPattern>,
        startDate: LocalDate,
        endDate: LocalDate,
        outputFile: File
    ): Result<File> = withContext(Dispatchers.IO) {
        try {
            val exportData = AnalyticsExport(
                metadata = ExportMetadata(
                    version = SCHEMA_VERSION,
                    exportDate = Instant.now(),
                    startDate = startDate,
                    endDate = endDate,
                    appVersion = getAppVersion(),
                    totalFoodEntries = 0,
                    totalSymptomEvents = 0
                ),
                correlations = correlations.map { it.toExportModel() },
                triggerPatterns = triggerPatterns.map { it.toExportModel() },
                schema = getSchemaDefinition()
            )

            val jsonString = json.encodeToString(exportData)
            outputFile.writeText(jsonString)

            Result.success(outputFile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun importData(inputFile: File): Result<ImportResult> = withContext(Dispatchers.IO) {
        try {
            val jsonString = inputFile.readText()
            validateImportedJSON(jsonString)

            // Try to determine the type of export
            when {
                jsonString.contains("\"foodEntries\"") && jsonString.contains("\"symptomEvents\"") -> {
                    val data = json.decodeFromString<FoodDiaryExport>(jsonString)
                    Result.success(ImportResult.Complete(
                        foodEntries = data.foodEntries.map { it.toEntity() },
                        symptomEvents = data.symptomEvents.map { it.toEntity() },
                        metadata = data.metadata
                    ))
                }
                jsonString.contains("\"foodEntries\"") -> {
                    val data = json.decodeFromString<FoodEntriesExport>(jsonString)
                    Result.success(ImportResult.FoodEntriesOnly(
                        foodEntries = data.foodEntries.map { it.toEntity() },
                        metadata = data.metadata
                    ))
                }
                jsonString.contains("\"symptomEvents\"") -> {
                    val data = json.decodeFromString<SymptomEventsExport>(jsonString)
                    Result.success(ImportResult.SymptomEventsOnly(
                        symptomEvents = data.symptomEvents.map { it.toEntity() },
                        metadata = data.metadata
                    ))
                }
                else -> {
                    Result.failure(Exception("Unrecognized JSON format"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun validateExportedJSON(jsonString: String) {
        // Basic JSON validation
        try {
            json.parseToJsonElement(jsonString)
        } catch (e: Exception) {
            throw Exception("Invalid JSON generated: ${e.message}")
        }

        // Schema validation
        if (!jsonString.contains("\"version\"")) {
            throw Exception("Missing version field in exported JSON")
        }

        if (!jsonString.contains("\"exportDate\"")) {
            throw Exception("Missing exportDate field in exported JSON")
        }
    }

    private fun validateImportedJSON(jsonString: String) {
        // Basic JSON validation
        try {
            val element = json.parseToJsonElement(jsonString)
        } catch (e: Exception) {
            throw Exception("Invalid JSON format: ${e.message}")
        }

        // Version validation
        if (!jsonString.contains("\"version\":\"$SCHEMA_VERSION\"")) {
            throw Exception("Unsupported schema version. Expected: $SCHEMA_VERSION")
        }
    }

    private fun getAppVersion(): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            "${packageInfo.versionName} (${packageInfo.longVersionCode})"
        } catch (e: Exception) {
            "Unknown"
        }
    }

    private fun getSchemaDefinition(): SchemaDefinition {
        return SchemaDefinition(
            version = SCHEMA_VERSION,
            description = "Food Diary JSON Export Schema",
            fields = mapOf(
                "foodEntry" to "Object containing food consumption data",
                "symptomEvent" to "Object containing symptom occurrence data",
                "correlation" to "Object containing statistical correlation analysis",
                "triggerPattern" to "Object containing identified trigger patterns"
            )
        )
    }

    // Extension functions for data conversion
    private fun FoodEntry.toExportModel(): FoodEntryExport {
        return FoodEntryExport(
            id = id,
            foodName = foodName,
            portions = portions,
            portionUnit = portionUnit,
            mealType = mealType,
            ingredients = ingredients,
            timestamp = timestamp,
            notes = notes,
            estimatedCalories = estimatedCalories,
            preparationMethod = preparationMethod
        )
    }

    private fun SymptomEvent.toExportModel(): SymptomEventExport {
        return SymptomEventExport(
            id = id,
            symptomType = symptomType.name,
            severity = severity,
            timestamp = timestamp,
            duration = duration,
            notes = notes,
            bristolStoolType = bristolStoolType?.name,
            location = location,
            triggers = triggers
        )
    }

    private fun CorrelationAnalysis.toExportModel(): CorrelationExport {
        return CorrelationExport(
            foodName = foodName,
            symptomType = symptomType.name,
            correlationCoefficient = correlationCoefficient,
            pValue = pearsonResult?.pValue ?: 0.0,
            sampleSize = pearsonResult?.sampleSize ?: 0,
            isStatisticallySignificant = isStatisticallySignificant,
            averageTimeOffsetMinutes = averageTimeOffsetMinutes,
            averageSeverity = averageSeverity,
            occurrences = occurrences
        )
    }

    private fun TriggerPattern.toExportModel(): TriggerPatternExport {
        return TriggerPatternExport(
            foodName = foodName,
            symptomType = symptomType.name,
            significance = significance,
            occurrences = occurrences,
            averageOnsetMinutes = averageOnsetMinutes,
            averageSeverity = averageSeverity,
            recommendedAction = recommendedAction,
            timeWindow = timeWindowHours
        )
    }

    private fun FoodEntryExport.toEntity(): FoodEntry {
        return FoodEntry(
            id = id,
            foodName = foodName,
            portions = portions,
            portionUnit = portionUnit,
            mealType = mealType,
            ingredients = ingredients,
            timestamp = timestamp,
            notes = notes,
            estimatedCalories = estimatedCalories,
            preparationMethod = preparationMethod
        )
    }

    private fun SymptomEventExport.toEntity(): SymptomEvent {
        return SymptomEvent(
            id = id,
            symptomType = SymptomType.valueOf(symptomType),
            severity = severity,
            timestamp = timestamp,
            duration = duration,
            notes = notes,
            bristolStoolType = bristolStoolType?.let { BristolStoolType.valueOf(it) },
            location = location,
            triggers = triggers
        )
    }

    companion object {
        const val SCHEMA_VERSION = "1.0.0"
    }
}

// Export models
@Serializable
data class FoodDiaryExport(
    val metadata: ExportMetadata,
    val foodEntries: List<FoodEntryExport>,
    val symptomEvents: List<SymptomEventExport>,
    val correlations: List<CorrelationExport>,
    val triggerPatterns: List<TriggerPatternExport>,
    val schema: SchemaDefinition
)

@Serializable
data class FoodEntriesExport(
    val metadata: ExportMetadata,
    val foodEntries: List<FoodEntryExport>,
    val schema: SchemaDefinition
)

@Serializable
data class SymptomEventsExport(
    val metadata: ExportMetadata,
    val symptomEvents: List<SymptomEventExport>,
    val schema: SchemaDefinition
)

@Serializable
data class AnalyticsExport(
    val metadata: ExportMetadata,
    val correlations: List<CorrelationExport>,
    val triggerPatterns: List<TriggerPatternExport>,
    val schema: SchemaDefinition
)

@Serializable
data class ExportMetadata(
    val version: String,
    @Serializable(with = InstantSerializer::class)
    val exportDate: Instant,
    @Serializable(with = LocalDateSerializer::class)
    val startDate: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val endDate: LocalDate,
    val appVersion: String,
    val totalFoodEntries: Int,
    val totalSymptomEvents: Int
)

@Serializable
data class FoodEntryExport(
    val id: Long = 0,
    val foodName: String,
    val portions: Double,
    val portionUnit: String,
    val mealType: String,
    val ingredients: List<String>,
    @Serializable(with = InstantSerializer::class)
    val timestamp: Instant,
    val notes: String = "",
    val estimatedCalories: Int? = null,
    val preparationMethod: String? = null
)

@Serializable
data class SymptomEventExport(
    val id: Long = 0,
    val symptomType: String,
    val severity: Int,
    @Serializable(with = InstantSerializer::class)
    val timestamp: Instant,
    val duration: Int? = null,
    val notes: String = "",
    val bristolStoolType: String? = null,
    val location: String? = null,
    val triggers: List<String> = emptyList()
)

@Serializable
data class CorrelationExport(
    val foodName: String,
    val symptomType: String,
    val correlationCoefficient: Double,
    val pValue: Double,
    val sampleSize: Int,
    val isStatisticallySignificant: Boolean,
    val averageTimeOffsetMinutes: Int,
    val averageSeverity: Double,
    val occurrences: Int
)

@Serializable
data class TriggerPatternExport(
    val foodName: String,
    val symptomType: String,
    val significance: Float,
    val occurrences: Int,
    val averageOnsetMinutes: Int,
    val averageSeverity: Double,
    val recommendedAction: String,
    val timeWindow: Int
)

@Serializable
data class SchemaDefinition(
    val version: String,
    val description: String,
    val fields: Map<String, String>
)

// Import result types
sealed class ImportResult {
    data class Complete(
        val foodEntries: List<FoodEntry>,
        val symptomEvents: List<SymptomEvent>,
        val metadata: ExportMetadata
    ) : ImportResult()

    data class FoodEntriesOnly(
        val foodEntries: List<FoodEntry>,
        val metadata: ExportMetadata
    ) : ImportResult()

    data class SymptomEventsOnly(
        val symptomEvents: List<SymptomEvent>,
        val metadata: ExportMetadata
    ) : ImportResult()
}

// Serializers for Java time types
object InstantSerializer : kotlinx.serialization.KSerializer<Instant> {
    override val descriptor = kotlinx.serialization.descriptors.PrimitiveSerialDescriptor("Instant", kotlinx.serialization.descriptors.PrimitiveKind.STRING)

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: Instant) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): Instant {
        return Instant.parse(decoder.decodeString())
    }
}

object LocalDateSerializer : kotlinx.serialization.KSerializer<LocalDate> {
    override val descriptor = kotlinx.serialization.descriptors.PrimitiveSerialDescriptor("LocalDate", kotlinx.serialization.descriptors.PrimitiveKind.STRING)

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: LocalDate) {
        encoder.encodeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE))
    }

    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString(), DateTimeFormatter.ISO_LOCAL_DATE)
    }
}