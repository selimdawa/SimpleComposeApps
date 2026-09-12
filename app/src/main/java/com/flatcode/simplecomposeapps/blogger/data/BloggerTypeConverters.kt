package com.flatcode.simplecomposeapps.blogger.data

import androidx.room.TypeConverter
import com.flatcode.simplecomposeapps.blogger.model.Author
import kotlinx.serialization.json.Json

class BloggerTypeConverters {

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.let { Json.decodeFromString(it) }
    }

    @TypeConverter
    fun fromAuthor(value: Author?): String? {
        return value?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toAuthor(value: String?): Author? {
        return value?.let { Json.decodeFromString(it) }
    }
}
