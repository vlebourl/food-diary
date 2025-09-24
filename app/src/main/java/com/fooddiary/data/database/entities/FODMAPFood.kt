package com.fooddiary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fooddiary.data.models.*

@Entity(tableName = "fodmap_foods")
data class FODMAPFood(
    @PrimaryKey val id: String,
    val name: String,
    val category: FoodCategory,
    val oligosaccharides: FODMAPLevel,
    val disaccharides: FODMAPLevel,
    val monosaccharides: FODMAPLevel,
    val polyols: FODMAPLevel,
    val overallLevel: FODMAPLevel,
    val servingSize: String,
    val notes: String?,
    val aliases: List<String> = emptyList() // Alternative names for search
) {
    companion object {
        // High FODMAP foods
        val HIGH_FODMAP_FOODS = listOf(
            FODMAPFood(
                id = "onion",
                name = "Onion",
                category = FoodCategory.VEGETABLES,
                oligosaccharides = FODMAPLevel.HIGH,
                disaccharides = FODMAPLevel.LOW,
                monosaccharides = FODMAPLevel.LOW,
                polyols = FODMAPLevel.LOW,
                overallLevel = FODMAPLevel.HIGH,
                servingSize = "1/4 medium",
                notes = null,
                aliases = listOf("onions", "white onion", "yellow onion")
            ),
            FODMAPFood(
                id = "garlic",
                name = "Garlic",
                category = FoodCategory.VEGETABLES,
                oligosaccharides = FODMAPLevel.HIGH,
                disaccharides = FODMAPLevel.LOW,
                monosaccharides = FODMAPLevel.LOW,
                polyols = FODMAPLevel.LOW,
                overallLevel = FODMAPLevel.HIGH,
                servingSize = "1 clove",
                notes = null,
                aliases = listOf("garlic cloves", "fresh garlic")
            ),
            FODMAPFood(
                id = "wheat",
                name = "Wheat bread",
                category = FoodCategory.GRAINS,
                oligosaccharides = FODMAPLevel.HIGH,
                disaccharides = FODMAPLevel.LOW,
                monosaccharides = FODMAPLevel.LOW,
                polyols = FODMAPLevel.LOW,
                overallLevel = FODMAPLevel.HIGH,
                servingSize = "2 slices",
                notes = null,
                aliases = listOf("bread", "wheat", "white bread")
            ),
            FODMAPFood(
                id = "milk",
                name = "Cow's milk",
                category = FoodCategory.DAIRY,
                oligosaccharides = FODMAPLevel.LOW,
                disaccharides = FODMAPLevel.HIGH,
                monosaccharides = FODMAPLevel.LOW,
                polyols = FODMAPLevel.LOW,
                overallLevel = FODMAPLevel.HIGH,
                servingSize = "1/2 cup",
                notes = null,
                aliases = listOf("milk", "dairy milk", "whole milk")
            ),
            FODMAPFood(
                id = "apple",
                name = "Apple",
                category = FoodCategory.FRUITS,
                oligosaccharides = FODMAPLevel.LOW,
                disaccharides = FODMAPLevel.LOW,
                monosaccharides = FODMAPLevel.HIGH,
                polyols = FODMAPLevel.MEDIUM,
                overallLevel = FODMAPLevel.HIGH,
                servingSize = "1/2 medium",
                notes = null,
                aliases = listOf("apples", "red apple", "green apple")
            ),
            FODMAPFood(
                id = "beans",
                name = "Kidney beans",
                category = FoodCategory.LEGUMES,
                oligosaccharides = FODMAPLevel.HIGH,
                disaccharides = FODMAPLevel.LOW,
                monosaccharides = FODMAPLevel.LOW,
                polyols = FODMAPLevel.LOW,
                overallLevel = FODMAPLevel.HIGH,
                servingSize = "1/4 cup",
                notes = null,
                aliases = listOf("beans", "red beans", "legumes")
            )
        )

        // Low FODMAP foods
        val LOW_FODMAP_FOODS = listOf(
            FODMAPFood(
                id = "rice",
                name = "White rice",
                category = FoodCategory.GRAINS,
                oligosaccharides = FODMAPLevel.LOW,
                disaccharides = FODMAPLevel.LOW,
                monosaccharides = FODMAPLevel.LOW,
                polyols = FODMAPLevel.LOW,
                overallLevel = FODMAPLevel.LOW,
                servingSize = "1 cup cooked",
                notes = null,
                aliases = listOf("rice", "basmati rice", "jasmine rice")
            ),
            FODMAPFood(
                id = "chicken",
                name = "Chicken breast",
                category = FoodCategory.MEAT,
                oligosaccharides = FODMAPLevel.LOW,
                disaccharides = FODMAPLevel.LOW,
                monosaccharides = FODMAPLevel.LOW,
                polyols = FODMAPLevel.LOW,
                overallLevel = FODMAPLevel.LOW,
                servingSize = "150g",
                notes = null,
                aliases = listOf("chicken", "chicken breast", "poultry")
            ),
            FODMAPFood(
                id = "carrot",
                name = "Carrots",
                category = FoodCategory.VEGETABLES,
                oligosaccharides = FODMAPLevel.LOW,
                disaccharides = FODMAPLevel.LOW,
                monosaccharides = FODMAPLevel.LOW,
                polyols = FODMAPLevel.LOW,
                overallLevel = FODMAPLevel.LOW,
                servingSize = "1 cup",
                notes = null,
                aliases = listOf("carrots", "baby carrots")
            ),
            FODMAPFood(
                id = "banana",
                name = "Banana",
                category = FoodCategory.FRUITS,
                oligosaccharides = FODMAPLevel.LOW,
                disaccharides = FODMAPLevel.LOW,
                monosaccharides = FODMAPLevel.LOW,
                polyols = FODMAPLevel.LOW,
                overallLevel = FODMAPLevel.LOW,
                servingSize = "1 medium",
                notes = null,
                aliases = listOf("bananas", "ripe banana")
            )
        )

        fun getAllFODMAPFoods(): List<FODMAPFood> = HIGH_FODMAP_FOODS + LOW_FODMAP_FOODS
    }

    val isHighFODMAP: Boolean
        get() = overallLevel == FODMAPLevel.HIGH

    val isLowFODMAP: Boolean
        get() = overallLevel == FODMAPLevel.LOW

    fun matches(searchTerm: String): Boolean {
        val term = searchTerm.lowercase()
        return name.lowercase().contains(term) ||
                aliases.any { it.lowercase().contains(term) }
    }
}