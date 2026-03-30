package com.eslirodrigues.focuscounter.database

import androidx.room.TypeConverter
import kotlin.time.Instant
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromInstant(value: Instant): Long = value.toEpochMilliseconds()

    @TypeConverter
    fun toInstant(value: Long): Instant = Instant.fromEpochMilliseconds(value)

    @TypeConverter
    fun fromList(value: List<Long>): String = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String): List<Long> = Json.decodeFromString(value)
}
