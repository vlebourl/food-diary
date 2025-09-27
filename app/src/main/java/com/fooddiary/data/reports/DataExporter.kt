package com.fooddiary.data.reports

import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.CorrelationPattern
import com.fooddiary.data.models.*
import java.io.File
import java.io.FileWriter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Handles data export to various file formats
 */
@Singleton
class DataExporter @Inject constructor() {

    /**
     * Export food entries to CSV format
     */
    suspend fun exportToCsv(
        foodEntries: List<FoodEntry>,
        filePath: String
    ): Result<String> {
        return try {
            val csvContent = buildString {
                // CSV Header
                appendLine("Date,Time,Meal Type,Foods,Portions,Notes,Created At,Modified At")

                // CSV Data
                foodEntries.sortedBy { it.timestamp }.forEach { entry ->
                    val dateTime = entry.timestamp.atZone(java.time.ZoneId.systemDefault())
                    val date = dateTime.toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
                    val time = dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))

                    val foods = "\"${entry.foods.joinToString("; ")}\""
                    val portions = "\"${entry.portions.entries.joinToString("; ") { "${it.key}: ${it.value}" }}\""
                    val notes = "\"${entry.notes?.replace("\"", "\"\"") ?: ""}\""
                    val createdAt = entry.createdAt.atZone(java.time.ZoneId.systemDefault())
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    val modifiedAt = entry.modifiedAt.atZone(java.time.ZoneId.systemDefault())
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

                    appendLine("$date,$time,${entry.mealType},$foods,$portions,$notes,$createdAt,$modifiedAt")
                }
            }

            File(filePath).writeText(csvContent)
            Result.success(filePath)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export symptom events to CSV format
     */
    suspend fun exportSymptomsToCsv(
        symptomEvents: List<SymptomEvent>,
        filePath: String
    ): Result<String> {
        return try {
            val csvContent = buildString {
                // CSV Header
                appendLine("Date,Time,Symptom Type,Severity,Duration Minutes,Notes,Created At,Modified At")

                // CSV Data
                symptomEvents.sortedBy { it.timestamp }.forEach { event ->
                    val dateTime = event.timestamp.atZone(java.time.ZoneId.systemDefault())
                    val date = dateTime.toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
                    val time = dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))

                    val durationMinutes = event.duration?.toMinutes() ?: ""
                    val notes = "\"${event.notes?.replace("\"", "\"\"") ?: ""}\""
                    val createdAt = event.createdAt.atZone(java.time.ZoneId.systemDefault())
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    val modifiedAt = event.modifiedAt.atZone(java.time.ZoneId.systemDefault())
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

                    appendLine("$date,$time,${event.symptomType},${event.severity},$durationMinutes,$notes,$createdAt,$modifiedAt")
                }
            }

            File(filePath).writeText(csvContent)
            Result.success(filePath)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export data to JSON format
     */
    suspend fun exportToJson(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        correlations: List<CorrelationPattern>,
        filePath: String
    ): Result<String> {
        return try {
            val jsonContent = buildJsonExport(foodEntries, symptomEvents, correlations)
            File(filePath).writeText(jsonContent)
            Result.success(filePath)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export to XML format for healthcare systems
     */
    suspend fun exportToXml(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        patientId: String,
        filePath: String
    ): Result<String> {
        return try {
            val xmlContent = buildXmlExport(foodEntries, symptomEvents, patientId)
            File(filePath).writeText(xmlContent)
            Result.success(filePath)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export to FHIR-compatible format
     */
    suspend fun exportToFhir(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        patientId: String,
        filePath: String
    ): Result<String> {
        return try {
            val fhirContent = buildFhirExport(foodEntries, symptomEvents, patientId)
            File(filePath).writeText(fhirContent)
            Result.success(filePath)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Create compressed archive with multiple format exports
     */
    suspend fun createMultiFormatExport(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        correlations: List<CorrelationPattern>,
        exportDir: String
    ): Result<List<String>> {
        return try {
            val exportedFiles = mutableListOf<String>()

            // Export to CSV
            val csvPath = "$exportDir/food_entries.csv"
            exportToCsv(foodEntries, csvPath).getOrThrow()
            exportedFiles.add(csvPath)

            val symptomsCsvPath = "$exportDir/symptoms.csv"
            exportSymptomsToCsv(symptomEvents, symptomsCsvPath).getOrThrow()
            exportedFiles.add(symptomsCsvPath)

            // Export to JSON
            val jsonPath = "$exportDir/complete_data.json"
            exportToJson(foodEntries, symptomEvents, correlations, jsonPath).getOrThrow()
            exportedFiles.add(jsonPath)

            // Export correlations separately
            val correlationsCsvPath = "$exportDir/correlations.csv"
            exportCorrelationsToCsv(correlations, correlationsCsvPath).getOrThrow()
            exportedFiles.add(correlationsCsvPath)

            Result.success(exportedFiles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export data in research-friendly format
     */
    suspend fun exportForResearch(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        correlations: List<CorrelationPattern>,
        anonymize: Boolean,
        filePath: String
    ): Result<String> {
        return try {
            val researchData = if (anonymize) {
                anonymizeData(foodEntries, symptomEvents, correlations)
            } else {
                ResearchExportData(foodEntries, symptomEvents, correlations)
            }

            val csvContent = buildResearchCsv(researchData)
            File(filePath).writeText(csvContent)
            Result.success(filePath)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Private helper methods

    private fun buildJsonExport(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        correlations: List<CorrelationPattern>
    ): String {
        return """
        {
            "export_info": {
                "generated_at": "${java.time.Instant.now()}",
                "version": "1.0",
                "total_food_entries": ${foodEntries.size},
                "total_symptom_events": ${symptomEvents.size},
                "total_correlations": ${correlations.size}
            },
            "food_entries": [
                ${foodEntries.joinToString(",\n") { entry ->
                    """
                    {
                        "id": ${entry.id},
                        "timestamp": "${entry.timestamp}",
                        "meal_type": "${entry.mealType}",
                        "foods": [${entry.foods.joinToString(",") { "\"$it\"" }}],
                        "portions": {
                            ${entry.portions.entries.joinToString(",\n") { (food, portion) ->
                                "\"$food\": \"$portion\""
                            }}
                        },
                        "notes": "${entry.notes?.replace("\"", "\\\"") ?: ""}",
                        "created_at": "${entry.createdAt}",
                        "modified_at": "${entry.modifiedAt}"
                    }
                    """.trimIndent()
                }}
            ],
            "symptom_events": [
                ${symptomEvents.joinToString(",\n") { event ->
                    """
                    {
                        "id": ${event.id},
                        "timestamp": "${event.timestamp}",
                        "symptom_type": "${event.symptomType}",
                        "severity": ${event.severity},
                        "duration_minutes": ${event.duration?.toMinutes() ?: "null"},
                        "notes": "${event.notes?.replace("\"", "\\\"") ?: ""}",
                        "created_at": "${event.createdAt}",
                        "modified_at": "${event.modifiedAt}"
                    }
                    """.trimIndent()
                }}
            ],
            "correlations": [
                ${correlations.joinToString(",\n") { correlation ->
                    """
                    {
                        "id": ${correlation.id},
                        "food_id": ${correlation.foodId},
                        "symptom_id": ${correlation.symptomId},
                        "strength": ${correlation.correlationStrength},
                        "confidence_level": "${correlation.confidenceLevel}",
                        "time_offset_hours": ${correlation.timeOffsetHours},
                        "occurrence_count": ${correlation.occurrenceCount},
                        "is_active": ${correlation.isActive}
                    }
                    """.trimIndent()
                }}
            ]
        }
        """.trimIndent()
    }

    private fun buildXmlExport(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        patientId: String
    ): String {
        return buildString {
            appendLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
            appendLine("<FoodDiaryExport>")
            appendLine("  <ExportInfo>")
            appendLine("    <GeneratedAt>${java.time.Instant.now()}</GeneratedAt>")
            appendLine("    <PatientId>$patientId</PatientId>")
            appendLine("    <TotalFoodEntries>${foodEntries.size}</TotalFoodEntries>")
            appendLine("    <TotalSymptomEvents>${symptomEvents.size}</TotalSymptomEvents>")
            appendLine("  </ExportInfo>")

            appendLine("  <FoodEntries>")
            foodEntries.forEach { entry ->
                appendLine("    <Entry>")
                appendLine("      <Id>${entry.id}</Id>")
                appendLine("      <Timestamp>${entry.timestamp}</Timestamp>")
                appendLine("      <MealType>${entry.mealType}</MealType>")
                appendLine("      <Foods>")
                entry.foods.forEach { food ->
                    appendLine("        <Food>${xmlEscape(food)}</Food>")
                }
                appendLine("      </Foods>")
                appendLine("      <Portions>")
                entry.portions.forEach { (food, portion) ->
                    appendLine("        <Portion food=\"${xmlEscape(food)}\">${xmlEscape(portion)}</Portion>")
                }
                appendLine("      </Portions>")
                entry.notes?.let { notes ->
                    appendLine("      <Notes>${xmlEscape(notes)}</Notes>")
                }
                appendLine("    </Entry>")
            }
            appendLine("  </FoodEntries>")

            appendLine("  <SymptomEvents>")
            symptomEvents.forEach { event ->
                appendLine("    <Event>")
                appendLine("      <Id>${event.id}</Id>")
                appendLine("      <Timestamp>${event.timestamp}</Timestamp>")
                appendLine("      <SymptomType>${event.symptomType}</SymptomType>")
                appendLine("      <Severity>${event.severity}</Severity>")
                event.duration?.let { duration ->
                    appendLine("      <DurationMinutes>${duration.toMinutes()}</DurationMinutes>")
                }
                event.notes?.let { notes ->
                    appendLine("      <Notes>${xmlEscape(notes)}</Notes>")
                }
                appendLine("    </Event>")
            }
            appendLine("  </SymptomEvents>")

            appendLine("</FoodDiaryExport>")
        }
    }

    private fun buildFhirExport(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        patientId: String
    ): String {
        return """
        {
            "resourceType": "Bundle",
            "id": "food-diary-export",
            "type": "collection",
            "timestamp": "${java.time.Instant.now()}",
            "entry": [
                {
                    "resource": {
                        "resourceType": "Patient",
                        "id": "$patientId",
                        "identifier": [
                            {
                                "system": "http://example.com/food-diary",
                                "value": "$patientId"
                            }
                        ]
                    }
                },
                ${foodEntries.joinToString(",\n") { entry ->
                    """
                    {
                        "resource": {
                            "resourceType": "Observation",
                            "id": "food-entry-${entry.id}",
                            "status": "final",
                            "category": [
                                {
                                    "coding": [
                                        {
                                            "system": "http://terminology.hl7.org/CodeSystem/observation-category",
                                            "code": "survey",
                                            "display": "Survey"
                                        }
                                    ]
                                }
                            ],
                            "code": {
                                "coding": [
                                    {
                                        "system": "http://snomed.info/sct",
                                        "code": "226479002",
                                        "display": "Food intake"
                                    }
                                ]
                            },
                            "subject": {
                                "reference": "Patient/$patientId"
                            },
                            "effectiveDateTime": "${entry.timestamp}",
                            "valueString": "${entry.foods.joinToString(", ")}",
                            "component": [
                                {
                                    "code": {
                                        "coding": [
                                            {
                                                "system": "http://example.com/food-diary",
                                                "code": "meal-type",
                                                "display": "Meal Type"
                                            }
                                        ]
                                    },
                                    "valueString": "${entry.mealType}"
                                }
                            ]
                        }
                    }
                    """.trimIndent()
                }}${if (symptomEvents.isNotEmpty()) "," else ""}
                ${symptomEvents.joinToString(",\n") { event ->
                    """
                    {
                        "resource": {
                            "resourceType": "Observation",
                            "id": "symptom-event-${event.id}",
                            "status": "final",
                            "category": [
                                {
                                    "coding": [
                                        {
                                            "system": "http://terminology.hl7.org/CodeSystem/observation-category",
                                            "code": "symptom",
                                            "display": "Symptom"
                                        }
                                    ]
                                }
                            ],
                            "code": {
                                "coding": [
                                    {
                                        "system": "http://snomed.info/sct",
                                        "code": "418799008",
                                        "display": "Finding reported by patient"
                                    }
                                ]
                            },
                            "subject": {
                                "reference": "Patient/$patientId"
                            },
                            "effectiveDateTime": "${event.timestamp}",
                            "valueInteger": ${event.severity},
                            "component": [
                                {
                                    "code": {
                                        "coding": [
                                            {
                                                "system": "http://example.com/food-diary",
                                                "code": "symptom-type",
                                                "display": "Symptom Type"
                                            }
                                        ]
                                    },
                                    "valueString": "${event.symptomType}"
                                }
                            ]
                        }
                    }
                    """.trimIndent()
                }}
            ]
        }
        """.trimIndent()
    }

    private suspend fun exportCorrelationsToCsv(
        correlations: List<CorrelationPattern>,
        filePath: String
    ): Result<String> {
        return try {
            val csvContent = buildString {
                appendLine("Food ID,Symptom ID,Correlation Strength,Confidence Level,Time Offset Hours,Occurrence Count,Is Active")
                correlations.forEach { correlation ->
                    appendLine("${correlation.foodId},${correlation.symptomId},${correlation.correlationStrength},${correlation.confidenceLevel},${correlation.timeOffsetHours},${correlation.occurrenceCount},${correlation.isActive}")
                }
            }

            File(filePath).writeText(csvContent)
            Result.success(filePath)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun buildResearchCsv(data: ResearchExportData): String {
        return buildString {
            // Header combining all data types
            appendLine("RecordType,Timestamp,MealType,FoodItems,Portions,SymptomType,Severity,Duration,Notes")

            // Food entries
            data.foodEntries.forEach { entry ->
                val foods = entry.foods.joinToString("; ")
                val portions = entry.portions.entries.joinToString("; ") { "${it.key}:${it.value}" }
                appendLine("FOOD,${entry.timestamp},${entry.mealType},\"$foods\",\"$portions\",,,,\"${entry.notes ?: ""}\"")
            }

            // Symptom events
            data.symptomEvents.forEach { event ->
                val durationMinutes = event.duration?.toMinutes() ?: ""
                appendLine("SYMPTOM,${event.timestamp},,,,${event.symptomType},${event.severity},$durationMinutes,\"${event.notes ?: ""}\"")
            }
        }
    }

    private fun anonymizeData(
        foodEntries: List<FoodEntry>,
        symptomEvents: List<SymptomEvent>,
        correlations: List<CorrelationPattern>
    ): ResearchExportData {
        // Remove or hash any identifying information
        val anonymizedFoodEntries = foodEntries.map { entry ->
            entry.copy(
                id = entry.id.hashCode().toLong().absoluteValue,
                notes = if (entry.notes?.isNotBlank() == true) "[REDACTED]" else null
            )
        }

        val anonymizedSymptomEvents = symptomEvents.map { event ->
            event.copy(
                id = event.id.hashCode().toLong().absoluteValue,
                notes = if (event.notes?.isNotBlank() == true) "[REDACTED]" else null
            )
        }

        val anonymizedCorrelations = correlations.map { correlation ->
            correlation.copy(
                id = correlation.id.hashCode().toLong().absoluteValue,
                foodId = correlation.foodId.hashCode().toLong().absoluteValue,
                symptomId = correlation.symptomId.hashCode().toLong().absoluteValue
            )
        }

        return ResearchExportData(anonymizedFoodEntries, anonymizedSymptomEvents, anonymizedCorrelations)
    }

    private fun xmlEscape(text: String): String {
        return text.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&apos;")
    }

    private fun Long.absoluteValue(): Long = if (this < 0) -this else this
}

// Supporting data classes
data class ResearchExportData(
    val foodEntries: List<FoodEntry>,
    val symptomEvents: List<SymptomEvent>,
    val correlations: List<CorrelationPattern>
)