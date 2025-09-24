package com.fooddiary.data.integrity

import com.fooddiary.data.database.dao.FoodEntryDao
import com.fooddiary.data.database.dao.SymptomDao
import com.fooddiary.data.database.entity.FoodEntry
import com.fooddiary.data.database.entity.SymptomEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataIntegrityChecker @Inject constructor(
    private val foodEntryDao: FoodEntryDao,
    private val symptomDao: SymptomDao
) {

    companion object {
        private const val MAX_FUTURE_DAYS = 7
        private const val MAX_PAST_YEARS = 5
        private const val MIN_VALID_SEVERITY = 1
        private const val MAX_VALID_SEVERITY = 10
        private const val MAX_REASONABLE_PORTION = 5000.0 // grams
        private const val MIN_REASONABLE_PORTION = 0.1 // grams
    }

    suspend fun performFullIntegrityCheck(): IntegrityReport {
        val issues = mutableListOf<IntegrityIssue>()
        val statistics = IntegrityStatistics()

        issues.addAll(checkFoodEntryIntegrity(statistics))
        issues.addAll(checkSymptomEventIntegrity(statistics))
        issues.addAll(checkReferentialIntegrity(statistics))
        issues.addAll(checkBusinessLogicConstraints(statistics))
        issues.addAll(checkDataConsistency(statistics))

        return IntegrityReport(
            totalIssues = issues.size,
            criticalIssues = issues.count { it.severity == IssueSeverity.CRITICAL },
            warnings = issues.count { it.severity == IssueSeverity.WARNING },
            issues = issues,
            statistics = statistics,
            checkTimestamp = Instant.now()
        )
    }

    suspend fun checkFoodEntryIntegrity(statistics: IntegrityStatistics): List<IntegrityIssue> {
        val issues = mutableListOf<IntegrityIssue>()
        val foodEntries = foodEntryDao.getAllEntries()

        statistics.totalFoodEntries = foodEntries.size

        val now = Instant.now()
        val minValidDate = now.minus(java.time.Duration.ofDays(365L * MAX_PAST_YEARS))
        val maxValidDate = now.plus(java.time.Duration.ofDays(MAX_FUTURE_DAYS.toLong()))

        foodEntries.forEach { entry ->
            try {
                // Check timestamp validity
                if (entry.timestamp.isBefore(minValidDate)) {
                    issues.add(IntegrityIssue.InvalidTimestamp(
                        entityType = "FoodEntry",
                        entityId = entry.id,
                        timestamp = entry.timestamp,
                        reason = "Timestamp is too far in the past (>${MAX_PAST_YEARS} years)",
                        severity = IssueSeverity.WARNING,
                        autoFixable = false
                    ))
                    statistics.timestampIssues++
                }

                if (entry.timestamp.isAfter(maxValidDate)) {
                    issues.add(IntegrityIssue.InvalidTimestamp(
                        entityType = "FoodEntry",
                        entityId = entry.id,
                        timestamp = entry.timestamp,
                        reason = "Timestamp is too far in the future (>${MAX_FUTURE_DAYS} days)",
                        severity = IssueSeverity.CRITICAL,
                        autoFixable = true,
                        suggestedFix = "Set timestamp to current time"
                    ))
                    statistics.timestampIssues++
                }

                // Check required fields
                if (entry.mealType.isBlank()) {
                    issues.add(IntegrityIssue.MissingRequiredField(
                        entityType = "FoodEntry",
                        entityId = entry.id,
                        fieldName = "mealType",
                        severity = IssueSeverity.CRITICAL,
                        autoFixable = true,
                        suggestedFix = "Set to 'Unknown'"
                    ))
                    statistics.missingFieldIssues++
                }

                // Check food items
                if (entry.foodItems.isEmpty()) {
                    issues.add(IntegrityIssue.EmptyCollection(
                        entityType = "FoodEntry",
                        entityId = entry.id,
                        fieldName = "foodItems",
                        severity = IssueSeverity.CRITICAL,
                        autoFixable = false
                    ))
                    statistics.missingFieldIssues++
                } else {
                    entry.foodItems.forEach { foodItem ->
                        // Check portion sizes
                        if (foodItem.portionGrams < MIN_REASONABLE_PORTION) {
                            issues.add(IntegrityIssue.InvalidValue(
                                entityType = "FoodEntry",
                                entityId = entry.id,
                                fieldName = "portionGrams",
                                value = foodItem.portionGrams.toString(),
                                reason = "Portion size too small (<$MIN_REASONABLE_PORTION g)",
                                severity = IssueSeverity.WARNING,
                                autoFixable = true,
                                suggestedFix = "Set to minimum reasonable portion"
                            ))
                            statistics.dataValidationIssues++
                        }

                        if (foodItem.portionGrams > MAX_REASONABLE_PORTION) {
                            issues.add(IntegrityIssue.InvalidValue(
                                entityType = "FoodEntry",
                                entityId = entry.id,
                                fieldName = "portionGrams",
                                value = foodItem.portionGrams.toString(),
                                reason = "Portion size unreasonably large (>${MAX_REASONABLE_PORTION} g)",
                                severity = IssueSeverity.WARNING,
                                autoFixable = true,
                                suggestedFix = "Review and correct portion size"
                            ))
                            statistics.dataValidationIssues++
                        }

                        // Check food name validity
                        if (foodItem.foodName.isBlank()) {
                            issues.add(IntegrityIssue.MissingRequiredField(
                                entityType = "FoodItem",
                                entityId = "${entry.id}-${foodItem.foodName}",
                                fieldName = "foodName",
                                severity = IssueSeverity.CRITICAL,
                                autoFixable = true,
                                suggestedFix = "Set to 'Unknown Food'"
                            ))
                            statistics.missingFieldIssues++
                        }
                    }
                }

                // Check timezone validity
                try {
                    ZoneId.of(entry.timezone)
                } catch (e: Exception) {
                    issues.add(IntegrityIssue.InvalidValue(
                        entityType = "FoodEntry",
                        entityId = entry.id,
                        fieldName = "timezone",
                        value = entry.timezone,
                        reason = "Invalid timezone identifier",
                        severity = IssueSeverity.CRITICAL,
                        autoFixable = true,
                        suggestedFix = "Set to system default timezone"
                    ))
                    statistics.dataValidationIssues++
                }

                statistics.validFoodEntries++

            } catch (e: Exception) {
                issues.add(IntegrityIssue.CorruptedData(
                    entityType = "FoodEntry",
                    entityId = entry.id,
                    corruption = "Unable to process entry: ${e.message}",
                    severity = IssueSeverity.CRITICAL,
                    autoFixable = false
                ))
                statistics.corruptedRecords++
            }
        }

        return issues
    }

    suspend fun checkSymptomEventIntegrity(statistics: IntegrityStatistics): List<IntegrityIssue> {
        val issues = mutableListOf<IntegrityIssue>()
        val symptoms = symptomDao.getAllSymptoms()

        statistics.totalSymptoms = symptoms.size

        val now = Instant.now()
        val minValidDate = now.minus(java.time.Duration.ofDays(365L * MAX_PAST_YEARS))
        val maxValidDate = now.plus(java.time.Duration.ofDays(MAX_FUTURE_DAYS.toLong()))

        symptoms.forEach { symptom ->
            try {
                // Check timestamp validity
                if (symptom.timestamp.isBefore(minValidDate)) {
                    issues.add(IntegrityIssue.InvalidTimestamp(
                        entityType = "SymptomEvent",
                        entityId = symptom.id,
                        timestamp = symptom.timestamp,
                        reason = "Timestamp is too far in the past (>${MAX_PAST_YEARS} years)",
                        severity = IssueSeverity.WARNING,
                        autoFixable = false
                    ))
                    statistics.timestampIssues++
                }

                if (symptom.timestamp.isAfter(maxValidDate)) {
                    issues.add(IntegrityIssue.InvalidTimestamp(
                        entityType = "SymptomEvent",
                        entityId = symptom.id,
                        timestamp = symptom.timestamp,
                        reason = "Timestamp is too far in the future (>${MAX_FUTURE_DAYS} days)",
                        severity = IssueSeverity.CRITICAL,
                        autoFixable = true,
                        suggestedFix = "Set timestamp to current time"
                    ))
                    statistics.timestampIssues++
                }

                // Check severity scale
                if (symptom.severity < MIN_VALID_SEVERITY || symptom.severity > MAX_VALID_SEVERITY) {
                    issues.add(IntegrityIssue.InvalidValue(
                        entityType = "SymptomEvent",
                        entityId = symptom.id,
                        fieldName = "severity",
                        value = symptom.severity.toString(),
                        reason = "Severity must be between $MIN_VALID_SEVERITY and $MAX_VALID_SEVERITY",
                        severity = IssueSeverity.CRITICAL,
                        autoFixable = true,
                        suggestedFix = "Clamp to valid range"
                    ))
                    statistics.dataValidationIssues++
                }

                // Check symptom type
                if (symptom.type.isBlank()) {
                    issues.add(IntegrityIssue.MissingRequiredField(
                        entityType = "SymptomEvent",
                        entityId = symptom.id,
                        fieldName = "type",
                        severity = IssueSeverity.CRITICAL,
                        autoFixable = true,
                        suggestedFix = "Set to 'Unknown Symptom'"
                    ))
                    statistics.missingFieldIssues++
                }

                // Check duration validity
                if (symptom.durationMinutes < 0) {
                    issues.add(IntegrityIssue.InvalidValue(
                        entityType = "SymptomEvent",
                        entityId = symptom.id,
                        fieldName = "durationMinutes",
                        value = symptom.durationMinutes.toString(),
                        reason = "Duration cannot be negative",
                        severity = IssueSeverity.CRITICAL,
                        autoFixable = true,
                        suggestedFix = "Set to 0"
                    ))
                    statistics.dataValidationIssues++
                }

                if (symptom.durationMinutes > 24 * 60) { // More than 24 hours
                    issues.add(IntegrityIssue.InvalidValue(
                        entityType = "SymptomEvent",
                        entityId = symptom.id,
                        fieldName = "durationMinutes",
                        value = symptom.durationMinutes.toString(),
                        reason = "Duration exceeds 24 hours, may be incorrect",
                        severity = IssueSeverity.WARNING,
                        autoFixable = false
                    ))
                    statistics.dataValidationIssues++
                }

                // Check Bristol Stool Scale validity
                if (symptom.type.contains("stool", ignoreCase = true) ||
                    symptom.type.contains("bowel", ignoreCase = true)) {

                    if (symptom.bristolStoolType != null &&
                        (symptom.bristolStoolType!! < 1 || symptom.bristolStoolType!! > 7)) {
                        issues.add(IntegrityIssue.InvalidValue(
                            entityType = "SymptomEvent",
                            entityId = symptom.id,
                            fieldName = "bristolStoolType",
                            value = symptom.bristolStoolType.toString(),
                            reason = "Bristol Stool Type must be between 1 and 7",
                            severity = IssueSeverity.WARNING,
                            autoFixable = true,
                            suggestedFix = "Clamp to valid range (1-7)"
                        ))
                        statistics.dataValidationIssues++
                    }
                }

                statistics.validSymptoms++

            } catch (e: Exception) {
                issues.add(IntegrityIssue.CorruptedData(
                    entityType = "SymptomEvent",
                    entityId = symptom.id,
                    corruption = "Unable to process symptom: ${e.message}",
                    severity = IssueSeverity.CRITICAL,
                    autoFixable = false
                ))
                statistics.corruptedRecords++
            }
        }

        return issues
    }

    private suspend fun checkReferentialIntegrity(statistics: IntegrityStatistics): List<IntegrityIssue> {
        val issues = mutableListOf<IntegrityIssue>()

        val symptoms = symptomDao.getAllSymptoms()
        val foodEntryIds = foodEntryDao.getAllEntries().map { it.id }.toSet()

        // Check symptom -> food entry references
        symptoms.forEach { symptom ->
            symptom.triggerFoodEntryId?.let { foodEntryId ->
                if (!foodEntryIds.contains(foodEntryId)) {
                    issues.add(IntegrityIssue.BrokenReference(
                        entityType = "SymptomEvent",
                        entityId = symptom.id,
                        referencedEntityType = "FoodEntry",
                        referencedEntityId = foodEntryId,
                        fieldName = "triggerFoodEntryId",
                        severity = IssueSeverity.WARNING,
                        autoFixable = true,
                        suggestedFix = "Clear broken reference"
                    ))
                    statistics.brokenReferences++
                }
            }
        }

        return issues
    }

    private suspend fun checkBusinessLogicConstraints(statistics: IntegrityStatistics): List<IntegrityIssue> {
        val issues = mutableListOf<IntegrityIssue>()

        // Check for temporal consistency - symptoms should generally follow meals
        val foodEntries = foodEntryDao.getAllEntries().sortedBy { it.timestamp }
        val symptoms = symptomDao.getAllSymptoms().sortedBy { it.timestamp }

        symptoms.forEach { symptom ->
            if (symptom.triggerFoodEntryId != null) {
                val triggerFood = foodEntries.find { it.id == symptom.triggerFoodEntryId }
                if (triggerFood != null) {
                    val timeDiff = java.time.Duration.between(triggerFood.timestamp, symptom.timestamp)

                    // Symptom should occur after food intake, but not too long after
                    if (timeDiff.isNegative) {
                        issues.add(IntegrityIssue.TemporalInconsistency(
                            symptomId = symptom.id,
                            foodEntryId = triggerFood.id,
                            symptomTime = symptom.timestamp,
                            foodTime = triggerFood.timestamp,
                            reason = "Symptom occurs before associated food intake",
                            severity = IssueSeverity.WARNING,
                            autoFixable = false
                        ))
                        statistics.temporalIssues++
                    } else if (timeDiff.toHours() > 48) {
                        issues.add(IntegrityIssue.TemporalInconsistency(
                            symptomId = symptom.id,
                            foodEntryId = triggerFood.id,
                            symptomTime = symptom.timestamp,
                            foodTime = triggerFood.timestamp,
                            reason = "Symptom occurs more than 48 hours after food intake",
                            severity = IssueSeverity.WARNING,
                            autoFixable = false
                        ))
                        statistics.temporalIssues++
                    }
                }
            }
        }

        return issues
    }

    private suspend fun checkDataConsistency(statistics: IntegrityStatistics): List<IntegrityIssue> {
        val issues = mutableListOf<IntegrityIssue>()

        // Check for duplicate entries (same timestamp, meal type, similar foods)
        val foodEntries = foodEntryDao.getAllEntries()

        for (i in foodEntries.indices) {
            for (j in i + 1 until foodEntries.size) {
                val entry1 = foodEntries[i]
                val entry2 = foodEntries[j]

                if (arePotentialDuplicates(entry1, entry2)) {
                    issues.add(IntegrityIssue.PotentialDuplicate(
                        entity1Type = "FoodEntry",
                        entity1Id = entry1.id,
                        entity2Type = "FoodEntry",
                        entity2Id = entry2.id,
                        similarityReason = "Similar timestamp, meal type, and food items",
                        severity = IssueSeverity.WARNING,
                        autoFixable = false
                    ))
                    statistics.potentialDuplicates++
                }
            }
        }

        return issues
    }

    private fun arePotentialDuplicates(entry1: FoodEntry, entry2: FoodEntry): Boolean {
        // Check if timestamps are within 5 minutes
        val timeDiff = kotlin.math.abs(
            java.time.Duration.between(entry1.timestamp, entry2.timestamp).toMinutes()
        )
        if (timeDiff > 5) return false

        // Check if meal types match
        if (entry1.mealType != entry2.mealType) return false

        // Check if food items are similar
        val foods1 = entry1.foodItems.map { it.foodName.lowercase() }.toSet()
        val foods2 = entry2.foodItems.map { it.foodName.lowercase() }.toSet()
        val intersection = foods1.intersect(foods2)
        val union = foods1.union(foods2)

        // If more than 80% similarity in food items
        return intersection.size.toDouble() / union.size > 0.8
    }

    suspend fun attemptAutoFix(issues: List<IntegrityIssue>): AutoFixResult {
        val fixableIssues = issues.filter { it.autoFixable }
        val fixedIssues = mutableListOf<IntegrityIssue>()
        val failedFixes = mutableListOf<Pair<IntegrityIssue, String>>()

        fixableIssues.forEach { issue ->
            try {
                when (issue) {
                    is IntegrityIssue.InvalidTimestamp -> {
                        if (issue.suggestedFix?.contains("current time") == true) {
                            // Fix future timestamps by setting to current time
                            when (issue.entityType) {
                                "FoodEntry" -> {
                                    val entry = foodEntryDao.getEntryById(issue.entityId)
                                    if (entry != null) {
                                        foodEntryDao.updateEntry(entry.copy(timestamp = Instant.now()))
                                        fixedIssues.add(issue)
                                    }
                                }
                                "SymptomEvent" -> {
                                    val symptom = symptomDao.getSymptomById(issue.entityId)
                                    if (symptom != null) {
                                        symptomDao.updateSymptom(symptom.copy(timestamp = Instant.now()))
                                        fixedIssues.add(issue)
                                    }
                                }
                            }
                        }
                    }

                    is IntegrityIssue.MissingRequiredField -> {
                        // Apply suggested fixes for missing fields
                        when (issue.entityType) {
                            "FoodEntry" -> {
                                val entry = foodEntryDao.getEntryById(issue.entityId)
                                if (entry != null) {
                                    val fixedEntry = when (issue.fieldName) {
                                        "mealType" -> entry.copy(mealType = "Unknown")
                                        else -> entry
                                    }
                                    if (fixedEntry != entry) {
                                        foodEntryDao.updateEntry(fixedEntry)
                                        fixedIssues.add(issue)
                                    }
                                }
                            }

                            "SymptomEvent" -> {
                                val symptom = symptomDao.getSymptomById(issue.entityId)
                                if (symptom != null) {
                                    val fixedSymptom = when (issue.fieldName) {
                                        "type" -> symptom.copy(type = "Unknown Symptom")
                                        else -> symptom
                                    }
                                    if (fixedSymptom != symptom) {
                                        symptomDao.updateSymptom(fixedSymptom)
                                        fixedIssues.add(issue)
                                    }
                                }
                            }
                        }
                    }

                    is IntegrityIssue.BrokenReference -> {
                        // Clear broken references
                        when (issue.entityType) {
                            "SymptomEvent" -> {
                                val symptom = symptomDao.getSymptomById(issue.entityId)
                                if (symptom != null) {
                                    symptomDao.updateSymptom(symptom.copy(triggerFoodEntryId = null))
                                    fixedIssues.add(issue)
                                }
                            }
                        }
                    }

                    // Add more auto-fix cases as needed
                    else -> {
                        // Issue type doesn't have auto-fix implementation yet
                    }
                }
            } catch (e: Exception) {
                failedFixes.add(issue to "Auto-fix failed: ${e.message}")
            }
        }

        return AutoFixResult(
            totalFixableIssues = fixableIssues.size,
            successfulFixes = fixedIssues.size,
            failedFixes = failedFixes.size,
            fixedIssues = fixedIssues,
            failedFixDetails = failedFixes
        )
    }
}

// Data classes for integrity checking

data class IntegrityReport(
    val totalIssues: Int,
    val criticalIssues: Int,
    val warnings: Int,
    val issues: List<IntegrityIssue>,
    val statistics: IntegrityStatistics,
    val checkTimestamp: Instant
)

data class IntegrityStatistics(
    var totalFoodEntries: Int = 0,
    var validFoodEntries: Int = 0,
    var totalSymptoms: Int = 0,
    var validSymptoms: Int = 0,
    var corruptedRecords: Int = 0,
    var timestampIssues: Int = 0,
    var missingFieldIssues: Int = 0,
    var dataValidationIssues: Int = 0,
    var brokenReferences: Int = 0,
    var temporalIssues: Int = 0,
    var potentialDuplicates: Int = 0
)

sealed class IntegrityIssue {
    abstract val entityType: String
    abstract val entityId: String
    abstract val severity: IssueSeverity
    abstract val autoFixable: Boolean
    abstract val suggestedFix: String?

    data class InvalidTimestamp(
        override val entityType: String,
        override val entityId: String,
        val timestamp: Instant,
        val reason: String,
        override val severity: IssueSeverity,
        override val autoFixable: Boolean = false,
        override val suggestedFix: String? = null
    ) : IntegrityIssue()

    data class MissingRequiredField(
        override val entityType: String,
        override val entityId: String,
        val fieldName: String,
        override val severity: IssueSeverity,
        override val autoFixable: Boolean = false,
        override val suggestedFix: String? = null
    ) : IntegrityIssue()

    data class InvalidValue(
        override val entityType: String,
        override val entityId: String,
        val fieldName: String,
        val value: String,
        val reason: String,
        override val severity: IssueSeverity,
        override val autoFixable: Boolean = false,
        override val suggestedFix: String? = null
    ) : IntegrityIssue()

    data class EmptyCollection(
        override val entityType: String,
        override val entityId: String,
        val fieldName: String,
        override val severity: IssueSeverity,
        override val autoFixable: Boolean = false,
        override val suggestedFix: String? = null
    ) : IntegrityIssue()

    data class BrokenReference(
        override val entityType: String,
        override val entityId: String,
        val referencedEntityType: String,
        val referencedEntityId: String,
        val fieldName: String,
        override val severity: IssueSeverity,
        override val autoFixable: Boolean = false,
        override val suggestedFix: String? = null
    ) : IntegrityIssue()

    data class CorruptedData(
        override val entityType: String,
        override val entityId: String,
        val corruption: String,
        override val severity: IssueSeverity = IssueSeverity.CRITICAL,
        override val autoFixable: Boolean = false,
        override val suggestedFix: String? = null
    ) : IntegrityIssue()

    data class TemporalInconsistency(
        val symptomId: String,
        val foodEntryId: String,
        val symptomTime: Instant,
        val foodTime: Instant,
        val reason: String,
        override val severity: IssueSeverity,
        override val autoFixable: Boolean = false,
        override val suggestedFix: String? = null
    ) : IntegrityIssue() {
        override val entityType = "TemporalRelationship"
        override val entityId = "$symptomId-$foodEntryId"
    }

    data class PotentialDuplicate(
        val entity1Type: String,
        val entity1Id: String,
        val entity2Type: String,
        val entity2Id: String,
        val similarityReason: String,
        override val severity: IssueSeverity,
        override val autoFixable: Boolean = false,
        override val suggestedFix: String? = null
    ) : IntegrityIssue() {
        override val entityType = "DuplicateCheck"
        override val entityId = "$entity1Id-$entity2Id"
    }
}

enum class IssueSeverity {
    WARNING,
    CRITICAL
}

data class AutoFixResult(
    val totalFixableIssues: Int,
    val successfulFixes: Int,
    val failedFixes: Int,
    val fixedIssues: List<IntegrityIssue>,
    val failedFixDetails: List<Pair<IntegrityIssue, String>>
)