package com.fooddiary.data.models

enum class BristolStoolType(val scale: Int, val description: String, val medicalCategory: String) {
    TYPE_1(
        scale = 1,
        description = "Separate hard lumps, like nuts (hard to pass)",
        medicalCategory = "Severe constipation"
    ),
    TYPE_2(
        scale = 2,
        description = "Sausage-shaped but lumpy",
        medicalCategory = "Mild constipation"
    ),
    TYPE_3(
        scale = 3,
        description = "Like a sausage but with cracks on surface",
        medicalCategory = "Normal (lower end)"
    ),
    TYPE_4(
        scale = 4,
        description = "Like a sausage or snake, smooth and soft",
        medicalCategory = "Normal (ideal)"
    ),
    TYPE_5(
        scale = 5,
        description = "Soft blobs with clear-cut edges (passed easily)",
        medicalCategory = "Normal (upper end)"
    ),
    TYPE_6(
        scale = 6,
        description = "Fluffy pieces with ragged edges, a mushy stool",
        medicalCategory = "Mild diarrhea"
    ),
    TYPE_7(
        scale = 7,
        description = "Watery, no solid pieces (entirely liquid)",
        medicalCategory = "Severe diarrhea"
    );

    val displayName: String
        get() = "Type $scale - $description"

    val isConstipation: Boolean
        get() = scale <= 2

    val isDiarrhea: Boolean
        get() = scale >= 6

    val isNormal: Boolean
        get() = scale in 3..5

    companion object {
        fun fromScale(scale: Int): BristolStoolType? =
            values().find { it.scale == scale }

        fun fromScaleOrThrow(scale: Int): BristolStoolType =
            fromScale(scale) ?: throw IllegalArgumentException("Invalid Bristol Stool Scale: $scale. Must be 1-7")
    }
}