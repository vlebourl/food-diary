package com.fooddiary.domain.usecase

import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.models.SymptomType
import com.fooddiary.data.repository.SymptomEventRepository
import java.time.Instant
import javax.inject.Inject

class AddSymptomEventUseCase @Inject constructor(
    private val symptomEventRepository: SymptomEventRepository
) {
    suspend operator fun invoke(
        symptomType: SymptomType,
        severity: Int,
        timestamp: Instant = Instant.now(),
        durationMinutes: Int? = null,
        bristolScale: Int? = null,
        bodyLocation: String? = null,
        suspectedTriggers: List<String> = emptyList(),
        notes: String? = null
    ): Result<Long> {
        return try {
            // Validate input
            require(severity in 1..10) { "Severity must be between 1 and 10" }

            // Validate Bristol Scale if provided and symptom type requires it
            if (bristolScale != null) {
                require(bristolScale in 1..7) { "Bristol Scale must be between 1 and 7" }
                require(symptomType == SymptomType.DIARRHEA ||
                       symptomType == SymptomType.CONSTIPATION ||
                       symptomType == SymptomType.BOWEL_MOVEMENT) {
                    "Bristol Scale is only applicable for bowel movement symptoms"
                }
            }

            // Validate that Bristol Scale is provided for bowel-related symptoms
            if (symptomType in listOf(SymptomType.DIARRHEA, SymptomType.CONSTIPATION, SymptomType.BOWEL_MOVEMENT)) {
                require(bristolScale != null) { "Bristol Scale is required for bowel movement symptoms" }
            }

            // Create symptom event
            val symptomEvent = SymptomEvent(
                symptomType = symptomType,
                severity = severity,
                timestamp = timestamp,
                durationMinutes = durationMinutes,
                bristolScale = bristolScale,
                bodyLocation = bodyLocation,
                suspectedTriggers = suspectedTriggers,
                notes = notes
            )

            // Save to database
            val id = symptomEventRepository.insert(symptomEvent)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}