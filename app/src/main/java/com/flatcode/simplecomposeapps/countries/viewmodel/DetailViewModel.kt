package com.flatcode.simplecomposeapps.countries.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.countries.db.CountryDAO
import com.flatcode.simplecomposeapps.countries.model.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    application: Application, private val countryDao: CountryDAO,
) : AndroidViewModel(application) {
    private val _country = MutableStateFlow<Country?>(null)
    val country: StateFlow<Country?> = _country.asStateFlow()

    fun getDataFromRoom(uuid: Int) {
        Timber.d("Getting country details for uuid: %d", uuid)
        viewModelScope.launch {
            val country = countryDao.getCountry(uuid)
            _country.value = country
        }
    }
}
