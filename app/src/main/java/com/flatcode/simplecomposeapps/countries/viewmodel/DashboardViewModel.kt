package com.flatcode.simplecomposeapps.countries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.countries.model.Country
import com.flatcode.simplecomposeapps.countries.model.CountrySettings
import com.flatcode.simplecomposeapps.countries.db.CountryDAO
import com.flatcode.simplecomposeapps.countries.service.CountryService
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    application: Application, private val countryService: CountryService,
    private val countryDao: CountryDAO
) : AndroidViewModel(application) {

    private var refreshTime = 10 * 60 * 1000L

    private val _countriesResult = MutableStateFlow<Resource<List<Country>>>(Resource.Idle)
    val countriesResult: StateFlow<Resource<List<Country>>> = _countriesResult.asStateFlow()

    init {
        refreshData()
    }

    fun refreshData() {
        Timber.d("Refreshing countries data")
        viewModelScope.launch {
            _countriesResult.value = Resource.Loading()
            val updateTime = countryDao.getRefreshTime() ?: 0L
            if (updateTime != 0L && System.currentTimeMillis() - updateTime < refreshTime) {
                getDataFromRoom()
            } else {
                getDataFromAPI()
            }
        }
    }

    private fun getDataFromRoom() {
        Timber.d("Getting countries from Room")
        viewModelScope.launch {
            val countries = countryDao.getAllCountries()
            _countriesResult.value = Resource.Success(countries)
            Toast.makeText(getApplication(), "Countries from Room", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDataFromAPI() {
        Timber.d("Getting countries from API")
        viewModelScope.launch {
            try {
                val list = withContext(Dispatchers.IO) {
                    countryService.getCountries()
                }
                storeInRoom(list)
                Toast.makeText(getApplication(), "Countries from API", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Timber.e(e, "Error getting countries from API")
                val countries = countryDao.getAllCountries()
                if (countries.isNotEmpty()) {
                    _countriesResult.value = Resource.Success(countries)
                    Toast.makeText(getApplication(), "Countries from Room (Cache)", Toast.LENGTH_SHORT).show()
                } else {
                    _countriesResult.value = Resource.Error(Strings.FAILED_LOAD_DATA)
                }
                e.printStackTrace()
            }
        }
    }

    private suspend fun storeInRoom(list: List<Country>) {
        val listLong = countryDao.refreshCountries(*list.toTypedArray())

        list.forEachIndexed { index, country ->
            country.uuid = listLong[index].toInt()
        }

        countryDao.saveSettings(CountrySettings(refreshTime = System.currentTimeMillis()))
        _countriesResult.value = Resource.Success(list)
        Timber.d("Stored %d countries in Room", list.size)
    }
}
