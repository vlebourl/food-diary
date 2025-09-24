package com.fooddiary.data.models

enum class SymptomType {
    BLOATING,
    GAS,
    PAIN,
    CRAMPING,
    DIARRHEA,
    CONSTIPATION,
    NAUSEA,
    REFLUX,
    HEARTBURN,
    FATIGUE,
    HEADACHE,
    SKIN_REACTION,
    JOINT_PAIN,
    BOWEL_MOVEMENT,
    OTHER;

    val displayName: String
        get() = when (this) {
            BLOATING -> "Bloating"
            GAS -> "Gas"
            PAIN -> "Abdominal Pain"
            CRAMPING -> "Cramping"
            DIARRHEA -> "Diarrhea"
            CONSTIPATION -> "Constipation"
            NAUSEA -> "Nausea"
            REFLUX -> "Acid Reflux"
            HEARTBURN -> "Heartburn"
            FATIGUE -> "Fatigue"
            HEADACHE -> "Headache"
            SKIN_REACTION -> "Skin Reaction"
            JOINT_PAIN -> "Joint Pain"
            BOWEL_MOVEMENT -> "Bowel Movement"
            OTHER -> "Other"
        }

    val requiresBristolScale: Boolean
        get() = this == BOWEL_MOVEMENT || this == DIARRHEA || this == CONSTIPATION
}