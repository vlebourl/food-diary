package com.fooddiary.data.database.converters

import androidx.room.TypeConverter
import com.fooddiary.data.models.ConsumptionContext
import com.fooddiary.data.models.EatingSpeed
import com.fooddiary.data.models.LocationType
import com.fooddiary.data.models.SocialContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.time.Instant
import java.time.LocalDate

class InstantConverter {
    @TypeConverter
    fun fromInstant(instant: Instant?): Long? {
        return instant?.epochSecond
    }

    @TypeConverter
    fun toInstant(epochSecond: Long?): Instant? {
        return epochSecond?.let { Instant.ofEpochSecond(it) }
    }
}

class LocalDateConverter {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }

    @TypeConverter
    fun toLocalDate(epochDay: Long?): LocalDate? {
        return epochDay?.let { LocalDate.ofEpochDay(it) }
    }
}

class StringListConverter {
    @TypeConverter
    fun fromStringList(list: List<String>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun toStringList(string: String?): List<String>? {
        return string?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() }
    }
}

class StringMapConverter {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromStringMap(map: Map<String, String>?): String? {
        return map?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toStringMap(string: String?): Map<String, String>? {
        return string?.let { json.decodeFromString(it) }
    }
}

class ConsumptionContextConverter {
    @TypeConverter
    fun fromConsumptionContext(context: ConsumptionContext?): String? {
        return context?.let {
            "${it.location.name}|${it.social.name}|${it.speed.name}"
        }
    }

    @TypeConverter
    fun toConsumptionContext(string: String?): ConsumptionContext? {
        return string?.let {
            val parts = it.split("|")
            if (parts.size == 3) {
                ConsumptionContext(
                    location = LocationType.valueOf(parts[0]),
                    social = SocialContext.valueOf(parts[1]),
                    speed = EatingSpeed.valueOf(parts[2])
                )
            } else null
        }
    }
}