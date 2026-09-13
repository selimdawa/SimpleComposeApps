package com.flatcode.simplecomposeapps.weather.model

import android.content.SharedPreferences
import androidx.core.content.edit
import com.flatcode.simplecomposeapps.weather.db.WeatherDao
import com.flatcode.simplecomposeapps.weather.di.WeatherPrefs
import com.flatcode.simplecomposeapps.weather.network.WeatherApi
import com.flatcode.simplecomposeapps.weather.network.WeatherResponse
import com.flatcode.simplecomposeapps.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val dao: WeatherDao,
    private val api: WeatherApi,
    @WeatherPrefs private val prefs: SharedPreferences
) {
    fun getLatestWeatherFlow(): Flow<WeatherModel?> = dao.getLatestWeather()

    suspend fun getLatestWeatherSingle(): WeatherModel? = dao.getLatestWeatherSingle()

    suspend fun insertWeather(weather: WeatherModel) {
        dao.insertWeather(weather)
    }

    var isLocationRequested: Boolean
        get() = prefs.getBoolean("location_requested", false)
        set(value) = prefs.edit { putBoolean("location_requested", value) }

    suspend fun fetchWeather(city: String): Resource<Pair<WeatherModel, List<WeatherModel>>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getWeather(city)
            val parsed = parseWeatherData(response)
            if (parsed != null) {
                insertWeather(parsed.first)
                Resource.Success(parsed)
            } else {
                Resource.Error("Failed to parse weather data")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    private fun parseWeatherData(response: WeatherResponse): Pair<WeatherModel, List<WeatherModel>>? {
        val cityName = response.location.name
        val days = response.forecast.forecastday.map { day ->
            WeatherModel(
                city = cityName,
                time = day.date,
                condition = day.day.condition.text,
                currentTemp = "",
                maxTemp = day.day.maxtemp_c.toInt().toString(),
                minTemp = day.day.mintemp_c.toInt().toString(),
                imageUrl = day.day.condition.icon,
                hours = Json.encodeToString(day.hour)
            )
        }

        val current = response.current
        val firstDay = days.firstOrNull() ?: return null
        
        val currentItem = WeatherModel(
            city = cityName,
            time = current.last_updated,
            condition = current.condition.text,
            currentTemp = "${current.temp_c}°C",
            maxTemp = firstDay.maxTemp,
            minTemp = firstDay.minTemp,
            imageUrl = current.condition.icon,
            hours = firstDay.hours
        )
        
        return Pair(currentItem, days)
    }
}
