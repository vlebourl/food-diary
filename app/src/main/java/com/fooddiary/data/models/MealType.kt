package com.fooddiary.data.models

enum class MealType {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK,
    OTHER,
}

enum class LocationType {
    HOME,
    RESTAURANT,
    WORK,
    TRAVEL,
    OTHER,
}

enum class SocialContext {
    ALONE,
    FAMILY,
    FRIENDS,
    BUSINESS,
    OTHER,
}

enum class EatingSpeed {
    RUSHED,
    NORMAL,
    RELAXED,
}

data class ConsumptionContext(
    val location: LocationType,
    val social: SocialContext,
    val speed: EatingSpeed,
)
