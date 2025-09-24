package com.fooddiary.data.models

enum class FODMAPLevel {
    LOW,
    MEDIUM,
    HIGH;

    val displayName: String
        get() = when (this) {
            LOW -> "Low"
            MEDIUM -> "Medium"
            HIGH -> "High"
        }

    val colorCode: String
        get() = when (this) {
            LOW -> "#4CAF50"  // Green
            MEDIUM -> "#FF9800"  // Orange
            HIGH -> "#F44336"  // Red
        }
}

enum class FoodCategory {
    FRUITS,
    VEGETABLES,
    GRAINS,
    DAIRY,
    MEAT,
    LEGUMES,
    NUTS_SEEDS,
    BEVERAGES,
    CONDIMENTS,
    OTHER;

    val displayName: String
        get() = when (this) {
            FRUITS -> "Fruits"
            VEGETABLES -> "Vegetables"
            GRAINS -> "Grains & Cereals"
            DAIRY -> "Dairy Products"
            MEAT -> "Meat & Poultry"
            LEGUMES -> "Beans & Legumes"
            NUTS_SEEDS -> "Nuts & Seeds"
            BEVERAGES -> "Beverages"
            CONDIMENTS -> "Condiments & Spices"
            OTHER -> "Other"
        }
}