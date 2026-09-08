package com.flatcode.simplecomposeapps.weather.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "weather")
@Serializable
data class WeatherModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val city: String,
    val time: String,
    val condition: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val imageUrl: String,
    val hours: String
)