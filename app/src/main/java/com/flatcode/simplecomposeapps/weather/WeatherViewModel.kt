package com.flatcode.simplecomposeapps.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flatcode.simplecomposeapps.weather.model.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor() : ViewModel() {
    val liveDataCurrent = MutableLiveData<WeatherModel>()
    val liveDataList = MutableLiveData<List<WeatherModel>>()
}