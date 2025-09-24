package com.fooddiary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fooddiary.data.database.converters.LocalDateConverter
import com.fooddiary.data.models.ExerciseIntensity
import com.fooddiary.data.models.MenstrualPhase
import java.time.LocalDate

@Entity(tableName = "environmental_contexts")
@TypeConverters(LocalDateConverter::class)
data class EnvironmentalContext(
    @PrimaryKey val date: LocalDate,
    val stressLevel: Int, // 1-10 scale
    val sleepHours: Float,
    val sleepQuality: Int, // 1-10 scale
    val exerciseMinutes: Int?,
    val exerciseType: String?,
    val exerciseIntensity: ExerciseIntensity?,
    val menstrualPhase: MenstrualPhase?, // Optional tracking (FR-039)
    val weather: String?,
    val location: String?,
    val additionalNotes: String?,
) {
    init {
        require(stressLevel in 1..10) {
            "Stress level must be between 1 and 10"
        }
        require(sleepQuality in 1..10) {
            "Sleep quality must be between 1 and 10"
        }
        require(sleepHours in 0f..24f) {
            "Sleep hours must be between 0 and 24"
        }
        exerciseMinutes?.let { minutes ->
            require(minutes >= 0) {
                "Exercise minutes must be non-negative"
            }
        }
    }

    companion object {
        fun create(
            date: LocalDate,
            stressLevel: Int,
            sleepHours: Float,
            sleepQuality: Int,
            exerciseMinutes: Int? = null,
            exerciseType: String? = null,
            exerciseIntensity: ExerciseIntensity? = null,
            menstrualPhase: MenstrualPhase? = null,
            weather: String? = null,
            location: String? = null,
            additionalNotes: String? = null,
        ) = EnvironmentalContext(
            date = date,
            stressLevel = stressLevel,
            sleepHours = sleepHours,
            sleepQuality = sleepQuality,
            exerciseMinutes = exerciseMinutes,
            exerciseType = exerciseType,
            exerciseIntensity = exerciseIntensity,
            menstrualPhase = menstrualPhase,
            weather = weather,
            location = location,
            additionalNotes = additionalNotes,
        )
    }

    fun update(
        stressLevel: Int? = null,
        sleepHours: Float? = null,
        sleepQuality: Int? = null,
        exerciseMinutes: Int? = null,
        exerciseType: String? = null,
        exerciseIntensity: ExerciseIntensity? = null,
        menstrualPhase: MenstrualPhase? = null,
        weather: String? = null,
        location: String? = null,
        additionalNotes: String? = null,
    ): EnvironmentalContext = copy(
        stressLevel = stressLevel ?: this.stressLevel,
        sleepHours = sleepHours ?: this.sleepHours,
        sleepQuality = sleepQuality ?: this.sleepQuality,
        exerciseMinutes = exerciseMinutes ?: this.exerciseMinutes,
        exerciseType = exerciseType ?: this.exerciseType,
        exerciseIntensity = exerciseIntensity ?: this.exerciseIntensity,
        menstrualPhase = menstrualPhase ?: this.menstrualPhase,
        weather = weather ?: this.weather,
        location = location ?: this.location,
        additionalNotes = additionalNotes ?: this.additionalNotes,
    )
}
