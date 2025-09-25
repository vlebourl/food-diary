package com.fooddiary.data.analysis

import com.fooddiary.data.models.BristolStoolType

/**
 * Bristol Stool Chart implementation for medically accurate bowel movement classification
 * Provides the standard 7-point Bristol Stool Form Scale used in medical practice
 */
object BristolStoolChart {

    /**
     * Validates a Bristol Stool Type according to medical standards
     * @param type The Bristol stool type to validate
     * @return true if the type is valid, false otherwise
     */
    fun validateStoolType(type: BristolStoolType): Boolean {
        return when (type) {
            BristolStoolType.TYPE_1,
            BristolStoolType.TYPE_2,
            BristolStoolType.TYPE_3,
            BristolStoolType.TYPE_4,
            BristolStoolType.TYPE_5,
            BristolStoolType.TYPE_6,
            BristolStoolType.TYPE_7 -> true
            else -> false
        }
    }

    /**
     * Get the medical description for a Bristol Stool Type
     * @param type The Bristol stool type
     * @return Medical description of the stool type
     */
    fun getDescription(type: BristolStoolType): String {
        return when (type) {
            BristolStoolType.TYPE_1 -> "Separate hard lumps, like nuts (hard to pass)"
            BristolStoolType.TYPE_2 -> "Sausage-shaped but lumpy"
            BristolStoolType.TYPE_3 -> "Like a sausage but with cracks on its surface"
            BristolStoolType.TYPE_4 -> "Like a sausage or snake, smooth and soft"
            BristolStoolType.TYPE_5 -> "Soft blobs with clear-cut edges (passed easily)"
            BristolStoolType.TYPE_6 -> "Fluffy pieces with ragged edges, a mushy stool"
            BristolStoolType.TYPE_7 -> "Watery, no solid pieces. Entirely liquid"
            else -> "Unknown type"
        }
    }

    /**
     * Determine if a Bristol stool type indicates constipation
     * @param type The Bristol stool type
     * @return true if indicates constipation
     */
    fun isConstipation(type: BristolStoolType): Boolean {
        return type == BristolStoolType.TYPE_1 || type == BristolStoolType.TYPE_2
    }

    /**
     * Determine if a Bristol stool type indicates diarrhea
     * @param type The Bristol stool type
     * @return true if indicates diarrhea
     */
    fun isDiarrhea(type: BristolStoolType): Boolean {
        return type == BristolStoolType.TYPE_6 || type == BristolStoolType.TYPE_7
    }

    /**
     * Determine if a Bristol stool type is considered normal
     * @param type The Bristol stool type
     * @return true if considered normal
     */
    fun isNormal(type: BristolStoolType): Boolean {
        return type == BristolStoolType.TYPE_3 || type == BristolStoolType.TYPE_4
    }
}