package com.flatcode.simplecomposeapps.pop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.pop.model.PopItem
import com.flatcode.simplecomposeapps.pop.repository.FunkoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopViewModel @Inject constructor(
    private val repository: FunkoRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    val pops: StateFlow<List<PopItem>> = combine(
        repository.getAllPops(),
        _searchQuery
    ) { pops, query ->
        if (query.isEmpty()) {
            pops
        } else {
            pops.filter { 
                it.name.contains(query, ignoreCase = true) || 
                (it.series?.contains(query, ignoreCase = true) == true)
            }
        }
    }.stateIn(
        viewModelScope,
        kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    init {
        loadPops()
    }

    fun loadPops() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.loadPops()
                _error.value = null
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