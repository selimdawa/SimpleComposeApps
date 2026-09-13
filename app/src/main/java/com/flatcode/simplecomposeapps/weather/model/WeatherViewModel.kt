package com.flatcode.simplecomposeapps.weather.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _liveDataList = MutableLiveData<List<WeatherModel>>(emptyList())
    val liveDataList: LiveData<List<WeatherModel>> = _liveDataList

    private val _liveDataCurrent = MutableLiveData<WeatherModel?>(null)
    val liveDataCurrent: LiveData<WeatherModel?> = _liveDataCurrent

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    var lastCity: String? = null

    val savedWeather: LiveData<WeatherModel?> = repository.getLatestWeatherFlow().asLiveData()

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
        lastCity = city
        setLoading(true)
        viewModelScope.launch {
            val result = repository.fetchWeather(city)
            if (result is Resource.Success) {
                result.data?.let { (current, list) ->
                    updateCurrent(current)
                    updateList(list)
                }
            }
            setLoading(false)
        }
    }
}