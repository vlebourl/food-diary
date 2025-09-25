package com.fooddiary.data.models

enum class EliminationPhase {
    BASELINE,
    ELIMINATION,
    REINTRODUCTION,
    MAINTENANCE,
    ;

    val displayName: String
        get() = when (this) {
            BASELINE -> "Baseline"
            ELIMINATION -> "Elimination"
            REINTRODUCTION -> "Reintroduction"
            MAINTENANCE -> "Maintenance"
        }

    val description: String
        get() = when (this) {
            BASELINE -> "Establishing baseline symptoms before elimination"
            ELIMINATION -> "Avoiding all targeted trigger foods"
            REINTRODUCTION -> "Systematically testing individual food groups"
            MAINTENANCE -> "Following personalized diet based on results"
        }

    val typicalDurationWeeks: IntRange
        get() = when (this) {
            BASELINE -> 1..2
            ELIMINATION -> 3..6
            REINTRODUCTION -> 4..8
            MAINTENANCE -> Int.MAX_VALUE..Int.MAX_VALUE // Ongoing
        }
}
