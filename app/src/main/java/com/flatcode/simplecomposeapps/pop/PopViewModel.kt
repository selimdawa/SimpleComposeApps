package com.flatcode.simplecomposeapps.pop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.pop.model.PopItem
import com.flatcode.simplecomposeapps.pop.repository.FunkoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class PopViewModel @Inject constructor(
    private val repository: FunkoRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: LiveData<String> = _searchQuery.asLiveData()

    val pops: LiveData<List<PopItem>> = combine(
        repository.getAllPops(),
        _searchQuery
    ) { pops, query ->
        if (query.isEmpty()) {
            pops
        } else {
            pops.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.series.contains(query, ignoreCase = true)
            }
        }
    }.asLiveData()

    init {
        loadPops()
    }

    fun loadPops() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.loadPops()
                _error.value = null
                withTimeoutOrNull(2000.milliseconds) {
                    repository.getAllPops().first { it.isNotEmpty() }
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
            _isLoading.value = false
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}