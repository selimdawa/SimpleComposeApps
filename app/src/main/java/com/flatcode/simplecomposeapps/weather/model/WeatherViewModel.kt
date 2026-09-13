package com.flatcode.simplecomposeapps.weather.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _liveDataList = MutableStateFlow<List<WeatherModel>>(emptyList())
    val liveDataList: StateFlow<List<WeatherModel>> = _liveDataList.asStateFlow()

    private val _liveDataCurrent = MutableStateFlow<WeatherModel?>(null)
    val liveDataCurrent: StateFlow<WeatherModel?> = _liveDataCurrent.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    var lastCity: String? = null

    val savedWeather: StateFlow<WeatherModel?> = repository.getLatestWeatherFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    var isLocationRequested: Boolean
        get() = repository.isLocationRequested
        set(value) { repository.isLocationRequested = value }

    fun updateCurrent(weather: WeatherModel) {
        _liveDataCurrent.value = weather
    }

    fun updateList(list: List<WeatherModel>) {
        _liveDataList.value = list
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    suspend fun getLatestWeatherSingle(): WeatherModel? = repository.getLatestWeatherSingle()

    fun getWeather(city: String) {
        Timber.d("Fetching weather for city: %s", city)
        lastCity = city
        setLoading(true)
        viewModelScope.launch {
            val result = repository.fetchWeather(city)
            if (result is Resource.Success) {
                result.data?.let { (current, list) ->
                    updateCurrent(current)
                    updateList(list)
                }
            } else if (result is Resource.Error) {
                Timber.e("Error fetching weather: %s", result.message)
            }
            setLoading(false)
        }
    }
}