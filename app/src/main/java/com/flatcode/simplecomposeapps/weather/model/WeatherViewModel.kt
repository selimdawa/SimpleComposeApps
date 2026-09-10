package com.flatcode.simplecomposeapps.weather.model

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.weather.db.WeatherDao
import com.flatcode.simplecomposeapps.weather.di.WeatherPrefs
import com.flatcode.simplecomposeapps.weather.network.WeatherApi
import com.flatcode.simplecomposeapps.weather.network.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val dao: WeatherDao,
    private val api: WeatherApi,
    @WeatherPrefs private val prefs: SharedPreferences
) : ViewModel() {

    private val _liveDataList = MutableLiveData<List<WeatherModel>>(emptyList())
    val liveDataList: LiveData<List<WeatherModel>> = _liveDataList

    private val _liveDataCurrent = MutableLiveData<WeatherModel?>(null)
    val liveDataCurrent: LiveData<WeatherModel?> = _liveDataCurrent

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    var lastCity: String? = null

    val savedWeather: LiveData<WeatherModel?> = dao.getLatestWeather().asLiveData()

    var isLocationRequested: Boolean
        get() = prefs.getBoolean("location_requested", false)
        set(value) = prefs.edit { putBoolean("location_requested", value) }

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

    suspend fun getLatestWeatherSingle(): WeatherModel? = dao.getLatestWeatherSingle()

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