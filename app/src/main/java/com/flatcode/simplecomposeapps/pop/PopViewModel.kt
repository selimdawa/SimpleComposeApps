package com.flatcode.simplecomposeapps.pop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.pop.model.PopItem
import com.flatcode.simplecomposeapps.pop.repository.FunkoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class PopViewModel @Inject constructor(
    private val repository: FunkoRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
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
                        it.series.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        loadPops()
    }

    fun loadPops() {
        viewModelScope.launch {
            Timber.d("Loading Pops from repository")
            _isLoading.value = true
            try {
                repository.loadPops()
                _error.value = null
                withTimeoutOrNull(2000.milliseconds) {
                    repository.getAllPops().first { it.isNotEmpty() }
                }
            } catch (e: Exception) {
                _error.value = e.message
                Timber.e(e, "Error loading Pops")
            }
            _isLoading.value = false
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}