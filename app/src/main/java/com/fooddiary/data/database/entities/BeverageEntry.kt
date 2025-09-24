package com.fooddiary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fooddiary.data.database.converters.InstantConverter
import com.fooddiary.data.models.*
import java.time.Instant
import java.util.*

@Entity(tableName = "beverage_entries")
@TypeConverters(InstantConverter::class)
data class BeverageEntry(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val timestamp: Instant,
    val timezone: String,
    val name: String,
    val type: BeverageType,
    val volume: Float,
    val volumeUnit: VolumeUnit,
    val caffeineContent: Float? = null, // mg
    val alcoholContent: Float? = null, // percentage
    val carbonation: Boolean = false,
    val temperature: Temperature,
    val notes: String?,
    val createdAt: Instant = Instant.now(),
    val modifiedAt: Instant?,
    val isDeleted: Boolean = false,
    val deletedAt: Instant? = null
) {
    companion object {
        fun create(
            name: String,
            type: BeverageType,
            volume: Float,
            volumeUnit: VolumeUnit,
            timestamp: Instant = Instant.now(),
            timezone: String = "UTC",
            caffeineContent: Float? = null,
            alcoholContent: Float? = null,
            carbonation: Boolean = false,
            temperature: Temperature = Temperature.ROOM_TEMPERATURE,
            notes: String? = null
        ) = BeverageEntry(
            timestamp = timestamp,
            timezone = timezone,
            name = name,
            type = type,
            volume = volume,
            volumeUnit = volumeUnit,
            caffeineContent = caffeineContent,
            alcoholContent = alcoholContent,
            carbonation = carbonation,
            temperature = temperature,
            notes = notes
        )
    }

    fun softDelete(): BeverageEntry = copy(
        isDeleted = true,
        deletedAt = Instant.now(),
        modifiedAt = Instant.now()
    )

    fun update(
        name: String? = null,
        type: BeverageType? = null,
        volume: Float? = null,
        volumeUnit: VolumeUnit? = null,
        caffeineContent: Float? = null,
        alcoholContent: Float? = null,
        carbonation: Boolean? = null,
        temperature: Temperature? = null,
        notes: String? = null
    ): BeverageEntry = copy(
        name = name ?: this.name,
        type = type ?: this.type,
        volume = volume ?: this.volume,
        volumeUnit = volumeUnit ?: this.volumeUnit,
        caffeineContent = caffeineContent ?: this.caffeineContent,
        alcoholContent = alcoholContent ?: this.alcoholContent,
        carbonation = carbonation ?: this.carbonation,
        temperature = temperature ?: this.temperature,
        notes = notes ?: this.notes,
        modifiedAt = Instant.now()
    )
}