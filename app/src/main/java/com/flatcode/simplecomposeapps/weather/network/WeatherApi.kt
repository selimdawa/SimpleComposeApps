package com.flatcode.simplecomposeapps.weather.network

import com.flatcode.simplecomposeapps.utils.DATA
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.Serializable
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
data class WeatherResponse(
    val location: LocationData,
    val current: CurrentData,
    val forecast: ForecastData
)

@Serializable
data class LocationData(
    val name: String,
    val lat: Double,
    val lon: Double
)

@Serializable
data class CurrentData(
    val last_updated: String,
    val temp_c: Float,
    val condition: ConditionData
)

@Serializable
data class ForecastData(
    val forecastday: List<ForecastDay>
)

@Serializable
data class ForecastDay(
    val date: String,
    val day: DayData,
    val hour: List<HourData>
)

@Serializable
data class DayData(
    val maxtemp_c: Float,
    val mintemp_c: Float,
    val condition: ConditionData
)

@Serializable
data class HourData(
    val time: String,
    val temp_c: Float,
    val condition: ConditionData
)

@Serializable
data class ConditionData(
    val text: String,
    val icon: String
)

@Singleton
class WeatherApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getWeather(
        city: String,
        apiKey: String = DATA.API_KEY_WEATHER,
        days: Int = 3,
        aqi: String = "no",
        alerts: String = "no"
    ): WeatherResponse {
        return client.get("https://api.weatherapi.com/v1/forecast.json") {
            parameter("key", apiKey)
            parameter("q", city)
            parameter("days", days)
            parameter("aqi", aqi)
            parameter("alerts", alerts)
        }.body()
    }
}
