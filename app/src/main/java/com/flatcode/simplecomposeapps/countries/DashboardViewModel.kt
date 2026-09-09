package com.flatcode.simplecomposeapps.countries

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.countries.model.Country
import com.flatcode.simplecomposeapps.countries.model.CountrySettings
import com.flatcode.simplecomposeapps.countries.service.CountryDAO
import com.flatcode.simplecomposeapps.countries.service.CountryService
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    application: Application, private val countryService: CountryService,
    private val countryDao: CountryDAO
) : AndroidViewModel(application) {

    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    private val _countriesResult = MutableLiveData<Resource<List<Country>>>(Resource.Idle)
    val countriesResult: LiveData<Resource<List<Country>>> = _countriesResult

    fun refreshData() {
        viewModelScope.launch {
            _countriesResult.value = Resource.Loading()
            val updateTime = countryDao.getRefreshTime().first() ?: 0L
            if (updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
                getDataFromSQLite()
            } else {
                getDataFromAPI()
            }
        }
    }

    private fun getDataFromSQLite() {
        viewModelScope.launch {
            val countries = countryDao.getAllCountries()
            _countriesResult.value = Resource.Success(countries)
            Toast.makeText(getApplication(), "Countries from SQLite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDataFromAPI() {
        viewModelScope.launch {
            try {
                val list = withContext(Dispatchers.IO) {
                    countryService.getCountries()
                }
                storeInSQLite(list)
                Toast.makeText(getApplication(), "Countries from API", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                _countriesResult.value = Resource.Error(e.message ?: "An error occurred")
                e.printStackTrace()
            }
        }
    }

    private fun storeInSQLite(list: List<Country>) {
        viewModelScope.launch {
            countryDao.deleteAllCountries()
            val listLong = countryDao.insertAll(*list.toTypedArray())

            list.forEachIndexed { index, country ->
                country.uuid = listLong[index].toInt()
            }

            countryDao.saveSettings(CountrySettings(refreshTime = System.nanoTime()))
            _countriesResult.value = Resource.Success(list)
        }
    }
}