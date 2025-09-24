package com.fooddiary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fooddiary.data.database.converters.StringMapConverter
import java.util.*

@Entity(tableName = "quick_entry_templates")
@TypeConverters(StringMapConverter::class)
data class QuickEntryTemplate(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val entryType: String, // "food", "beverage", "symptom"
    val defaultData: Map<String, String>, // Key-value pairs for default form data
    val buttonColor: String, // Hex color code
    val buttonIcon: String, // Icon identifier
    val isActive: Boolean = true,
    val sortOrder: Int,
    val createdAt: Long = System.currentTimeMillis(),
    val modifiedAt: Long? = null,
) {
    companion object {
        fun createFood(
            name: String,
            defaultFoodName: String,
            defaultPortion: String,
            defaultUnit: String,
            buttonColor: String = "#4CAF50",
            buttonIcon: String = "restaurant",
            sortOrder: Int = 0,
            ingredients: List<String> = emptyList(),
            mealType: String = "snack",
        ) = QuickEntryTemplate(
            name = name,
            entryType = "food",
            defaultData = mapOf(
                "name" to defaultFoodName,
                "portions" to defaultPortion,
                "portionUnit" to defaultUnit,
                "ingredients" to ingredients.joinToString(","),
                "mealType" to mealType,
            ),
            buttonColor = buttonColor,
            buttonIcon = buttonIcon,
            sortOrder = sortOrder,
        )

        fun createBeverage(
            name: String,
            defaultBeverageName: String,
            defaultVolume: String,
            defaultUnit: String,
            caffeineContent: String? = null,
            buttonColor: String = "#2196F3",
            buttonIcon: String = "local_drink",
            sortOrder: Int = 0,
        ) = QuickEntryTemplate(
            name = name,
            entryType = "beverage",
            defaultData = buildMap {
                put("name", defaultBeverageName)
                put("volume", defaultVolume)
                put("volumeUnit", defaultUnit)
                caffeineContent?.let { put("caffeineContent", it) }
            },
            buttonColor = buttonColor,
            buttonIcon = buttonIcon,
            sortOrder = sortOrder,
        )

        fun createSymptom(
            name: String,
            defaultSymptomType: String,
            defaultSeverity: String,
            buttonColor: String = "#FF5722",
            buttonIcon: String = "warning",
            sortOrder: Int = 0,
        ) = QuickEntryTemplate(
            name = name,
            entryType = "symptom",
            defaultData = mapOf(
                "type" to defaultSymptomType,
                "severity" to defaultSeverity,
            ),
            buttonColor = buttonColor,
            buttonIcon = buttonIcon,
            sortOrder = sortOrder,
        )
    }

    fun update(
        name: String? = null,
        defaultData: Map<String, String>? = null,
        buttonColor: String? = null,
        buttonIcon: String? = null,
        isActive: Boolean? = null,
        sortOrder: Int? = null,
    ): QuickEntryTemplate = copy(
        name = name ?: this.name,
        defaultData = defaultData ?: this.defaultData,
        buttonColor = buttonColor ?: this.buttonColor,
        buttonIcon = buttonIcon ?: this.buttonIcon,
        isActive = isActive ?: this.isActive,
        sortOrder = sortOrder ?: this.sortOrder,
        modifiedAt = System.currentTimeMillis(),
    )

    fun deactivate(): QuickEntryTemplate = copy(
        isActive = false,
        modifiedAt = System.currentTimeMillis(),
    )

    fun reorder(newOrder: Int): QuickEntryTemplate = copy(
        sortOrder = newOrder,
        modifiedAt = System.currentTimeMillis(),
    )
}
