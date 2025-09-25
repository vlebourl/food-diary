package com.fooddiary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fooddiary.data.database.converters.ConsumptionContextConverter
import com.fooddiary.data.database.converters.InstantConverter
import com.fooddiary.data.database.converters.StringListConverter
import com.fooddiary.data.database.converters.StringMapConverter
import com.fooddiary.data.models.*
import java.time.Instant
import java.util.*

@Entity(tableName = "food_entries")
@TypeConverters(
    InstantConverter::class,
    StringListConverter::class,
    ConsumptionContextConverter::class,
    StringMapConverter::class,
)
data class FoodEntry(
    @PrimaryKey val id: Long = 0L,
    val timestamp: Instant,
    val mealType: MealType,
    val foods: List<String>,
    val portions: Map<String, String>,
    val notes: String?,
    val isDeleted: Boolean = false,
    val createdAt: Instant = Instant.now(),
    val modifiedAt: Instant = Instant.now(),
) {
    companion object {
        fun create(
            foods: List<String>,
            portions: Map<String, String>,
            mealType: MealType,
            timestamp: Instant = Instant.now(),
            notes: String? = null,
        ) = FoodEntry(
            timestamp = timestamp,
            mealType = mealType,
            foods = foods,
            portions = portions,
            notes = notes,
        )
    }

    fun softDelete(): FoodEntry = copy(
        isDeleted = true,
        modifiedAt = Instant.now(),
    )

    fun update(
        foods: List<String>? = null,
        portions: Map<String, String>? = null,
        mealType: MealType? = null,
        notes: String? = null,
    ): FoodEntry = copy(
        foods = foods ?: this.foods,
        portions = portions ?: this.portions,
        mealType = mealType ?: this.mealType,
        notes = notes ?: this.notes,
        modifiedAt = Instant.now(),
    )
}
