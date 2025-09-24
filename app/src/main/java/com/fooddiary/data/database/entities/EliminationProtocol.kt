package com.fooddiary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fooddiary.data.database.converters.LocalDateConverter
import com.fooddiary.data.database.converters.StringListConverter
import com.fooddiary.data.models.EliminationPhase
import java.time.LocalDate
import java.util.*

@Entity(tableName = "elimination_protocols")
@TypeConverters(
    LocalDateConverter::class,
    StringListConverter::class
)
data class EliminationProtocol(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val currentPhase: EliminationPhase,
    val eliminatedFoods: List<String>,
    val reintroducedFoods: List<String> = emptyList(),
    val phaseStartDate: LocalDate,
    val notes: String?,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val modifiedAt: Long? = null
) {
    companion object {
        fun create(
            name: String,
            startDate: LocalDate = LocalDate.now(),
            eliminatedFoods: List<String>,
            estimatedDurationWeeks: Int = 8,
            notes: String? = null
        ) = EliminationProtocol(
            name = name,
            startDate = startDate,
            endDate = startDate.plusWeeks(estimatedDurationWeeks.toLong()),
            currentPhase = EliminationPhase.BASELINE,
            eliminatedFoods = eliminatedFoods,
            phaseStartDate = startDate,
            notes = notes
        )
    }

    fun advanceToElimination(): EliminationProtocol = copy(
        currentPhase = EliminationPhase.ELIMINATION,
        phaseStartDate = LocalDate.now(),
        modifiedAt = System.currentTimeMillis()
    )

    fun advanceToReintroduction(): EliminationProtocol = copy(
        currentPhase = EliminationPhase.REINTRODUCTION,
        phaseStartDate = LocalDate.now(),
        modifiedAt = System.currentTimeMillis()
    )

    fun advanceToMaintenance(): EliminationProtocol = copy(
        currentPhase = EliminationPhase.MAINTENANCE,
        phaseStartDate = LocalDate.now(),
        modifiedAt = System.currentTimeMillis()
    )

    fun addReintroducedFood(food: String): EliminationProtocol = copy(
        reintroducedFoods = reintroducedFoods + food,
        modifiedAt = System.currentTimeMillis()
    )

    fun complete(): EliminationProtocol = copy(
        isActive = false,
        endDate = LocalDate.now(),
        modifiedAt = System.currentTimeMillis()
    )

    val daysInCurrentPhase: Long
        get() = java.time.temporal.ChronoUnit.DAYS.between(phaseStartDate, LocalDate.now())

    val totalDays: Long
        get() = java.time.temporal.ChronoUnit.DAYS.between(startDate, LocalDate.now())

    val isCompleted: Boolean
        get() = !isActive || (endDate?.isBefore(LocalDate.now()) == true)
}