package com.flatcode.simplecomposeapps.stockmarket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.stockmarket.model.CompanyListingsState
import com.flatcode.simplecomposeapps.stockmarket.network.StockRepository
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class StockMarketViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CompanyListingsState())
    val state: StateFlow<CompanyListingsState> = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        getCompanyListings()
    }

    fun onRefresh() {
        Timber.d("Refreshing stock market data")
        getCompanyListings(fetchFromRemote = true)
    }

    fun onSearchQueryChange(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500.milliseconds)
            getCompanyListings()
        }
    }

    private fun getCompanyListings(
        query: String = _state.value.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false,
    ) {
        viewModelScope.launch {
            repository.getCompanyListings(fetchFromRemote, query).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { _state.value = _state.value.copy(companies = it) }
                    }

                    is Resource.Error -> {
                        Timber.e("Error fetching stock market data: %s", result.message)
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = result.isLoading)
                    }

                    else -> {}
                }
            }
        }
    }
}