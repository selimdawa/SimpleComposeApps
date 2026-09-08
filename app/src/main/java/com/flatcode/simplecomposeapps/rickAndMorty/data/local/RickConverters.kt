package com.flatcode.simplecomposeapps.rickAndMorty.data.local

import androidx.room.TypeConverter
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.LocationShort
import kotlinx.serialization.json.Json

class RickConverters {

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromLocationShort(value: LocationShort): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toLocationShort(value: String): LocationShort {
        return Json.decodeFromString(value)
    }
}