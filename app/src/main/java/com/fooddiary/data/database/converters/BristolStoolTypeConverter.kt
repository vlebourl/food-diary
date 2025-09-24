package com.fooddiary.data.database.converters

import androidx.room.TypeConverter
import com.fooddiary.data.models.BristolStoolType

class BristolStoolTypeConverter {
    @TypeConverter
    fun fromBristolStoolType(bristolStoolType: BristolStoolType?): Int? {
        return bristolStoolType?.scale
    }

    @TypeConverter
    fun toBristolStoolType(scale: Int?): BristolStoolType? {
        return scale?.let { BristolStoolType.fromScale(it) }
    }
}