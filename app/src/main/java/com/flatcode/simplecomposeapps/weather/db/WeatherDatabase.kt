package com.flatcode.simplecomposeapps.weather.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flatcode.simplecomposeapps.weather.model.WeatherModel

@Database(entities = [WeatherModel::class], version = 1, exportSchema = true)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun dao(): WeatherDao
}