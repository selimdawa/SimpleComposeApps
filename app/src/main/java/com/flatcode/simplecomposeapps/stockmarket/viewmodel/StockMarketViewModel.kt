package com.flatcode.simplecomposeapps.stockmarket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.stockmarket.model.CompanyListingsState
import com.flatcode.simplecomposeapps.stockmarket.network.StockRepository
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class StockMarketViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    private val _state = MutableLiveData(CompanyListingsState())
    val state: LiveData<CompanyListingsState> = _state

    private var searchJob: Job? = null

    init {
        getCompanyListings()
    }

    fun onRefresh() {
        getCompanyListings(fetchFromRemote = true)
    }

    fun onSearchQueryChange(query: String) {
        _state.value = _state.value?.copy(searchQuery = query)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500.milliseconds)
            getCompanyListings()
        }
    }

    private fun getCompanyListings(
        query: String = _state.value?.searchQuery?.lowercase() ?: "",
        fetchFromRemote: Boolean = false,
    ) {
        viewModelScope.launch {
            repository.getCompanyListings(fetchFromRemote, query).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { _state.value = _state.value?.copy(companies = it) }
                    }

                    is Resource.Error -> Unit
                    is Resource.Loading -> {
                        _state.value = _state.value?.copy(isLoading = result.isLoading)
                    }

                    else -> {}
                }
            }
        }
    }
}