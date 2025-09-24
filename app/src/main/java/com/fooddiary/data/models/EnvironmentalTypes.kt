package com.fooddiary.data.models

enum class ExerciseIntensity {
    LOW,
    MODERATE,
    HIGH;

    val displayName: String
        get() = when (this) {
            LOW -> "Low"
            MODERATE -> "Moderate"
            HIGH -> "High"
        }
}

enum class MenstrualPhase {
    MENSTRUAL,
    FOLLICULAR,
    OVULATION,
    LUTEAL;

    val displayName: String
        get() = when (this) {
            MENSTRUAL -> "Menstrual"
            FOLLICULAR -> "Follicular"
            OVULATION -> "Ovulation"
            LUTEAL -> "Luteal"
        }

    val description: String
        get() = when (this) {
            MENSTRUAL -> "Days 1-5: Menstruation"
            FOLLICULAR -> "Days 1-13: Follicular phase"
            OVULATION -> "Days 14: Ovulation"
            LUTEAL -> "Days 15-28: Luteal phase"
        }
}