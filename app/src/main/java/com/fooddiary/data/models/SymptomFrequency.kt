package com.fooddiary.data.models

import androidx.room.ColumnInfo

data class SymptomFrequency(
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "count")
    val count: Int,
)
