package com.flatcode.simplecomposeapps.weather.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.weather.db.WeatherDao
import com.flatcode.simplecomposeapps.weather.network.WeatherApi
import com.flatcode.simplecomposeapps.weather.network.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dao: WeatherDao,
    private val api: WeatherApi
) : ViewModel() {

    private val _liveDataList = MutableStateFlow<List<WeatherModel>>(emptyList())
    val liveDataList: StateFlow<List<WeatherModel>> = _liveDataList

    private val _liveDataCurrent = MutableStateFlow<WeatherModel?>(null)
    val liveDataCurrent: StateFlow<WeatherModel?> = _liveDataCurrent

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    var lastCity: String? = null

    val savedWeather: StateFlow<WeatherModel?> =
        dao.getLatestWeather().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun updateCurrent(weather: WeatherModel) {
        _liveDataCurrent.value = weather
    }

    fun updateList(list: List<WeatherModel>) {
        _liveDataList.value = list
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun saveWeather(weather: WeatherModel) = viewModelScope.launch {
        dao.insertWeather(weather)
    }

    fun getWeather(city: String) {
        lastCity = city
        setLoading(true)
        viewModelScope.launch {
            try {
                val response = api.getWeather(city)
                parseWeatherData(response)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                setLoading(false)
            }
        }
    }

    private fun parseWeatherData(response: WeatherResponse) {
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
        updateList(days)

        val current = response.current
        val firstDay = days.firstOrNull()
        firstDay?.let {
            val item = WeatherModel(
                city = cityName,
                time = current.last_updated,
                condition = current.condition.text,
                currentTemp = "${current.temp_c}°C",
                maxTemp = it.maxTemp,
                minTemp = it.minTemp,
                imageUrl = current.condition.icon,
                hours = it.hours
            )
            updateCurrent(item)
            saveWeather(item)
        }
    }
}
