package com.flatcode.simplecomposeapps.weather.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
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
