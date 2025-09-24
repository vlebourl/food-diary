package com.fooddiary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fooddiary.data.database.converters.InstantConverter
import com.fooddiary.data.database.converters.StringListConverter
import com.fooddiary.data.database.converters.ConsumptionContextConverter
import com.fooddiary.data.models.*
import java.time.Instant
import java.util.*

@Entity(tableName = "food_entries")
@TypeConverters(
    InstantConverter::class,
    StringListConverter::class,
    ConsumptionContextConverter::class
)
data class FoodEntry(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val timestamp: Instant,
    val timezone: String,
    val name: String,
    val ingredients: List<String>,
    val portions: Float,
    val portionUnit: String,
    val preparationMethod: String?,
    val mealType: MealType,
    val context: ConsumptionContext,
    val notes: String?,
    val createdAt: Instant = Instant.now(),
    val modifiedAt: Instant?,
    val isDeleted: Boolean = false,
    val deletedAt: Instant? = null
) {
    companion object {
        fun create(
            name: String,
            ingredients: List<String>,
            portions: Float,
            portionUnit: String,
            mealType: MealType,
            context: ConsumptionContext,
            timestamp: Instant = Instant.now(),
            timezone: String = "UTC",
            preparationMethod: String? = null,
            notes: String? = null
        ) = FoodEntry(
            timestamp = timestamp,
            timezone = timezone,
            name = name,
            ingredients = ingredients,
            portions = portions,
            portionUnit = portionUnit,
            preparationMethod = preparationMethod,
            mealType = mealType,
            context = context,
            notes = notes,
            modifiedAt = null
        )
    }

    fun softDelete(): FoodEntry = copy(
        isDeleted = true,
        deletedAt = Instant.now(),
        modifiedAt = Instant.now()
    )

    fun update(
        name: String? = null,
        ingredients: List<String>? = null,
        portions: Float? = null,
        portionUnit: String? = null,
        preparationMethod: String? = null,
        mealType: MealType? = null,
        context: ConsumptionContext? = null,
        notes: String? = null
    ): FoodEntry = copy(
        name = name ?: this.name,
        ingredients = ingredients ?: this.ingredients,
        portions = portions ?: this.portions,
        portionUnit = portionUnit ?: this.portionUnit,
        preparationMethod = preparationMethod ?: this.preparationMethod,
        mealType = mealType ?: this.mealType,
        context = context ?: this.context,
        notes = notes ?: this.notes,
        modifiedAt = Instant.now()
    )
}