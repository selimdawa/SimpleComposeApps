package com.flatcode.simplecomposeapps.weather.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flatcode.simplecomposeapps.weather.model.WeatherModel
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherModel)

    @Query("SELECT * FROM weather_table WHERE time = :time")
    fun getWeather(time: String): Flow<WeatherModel>
}