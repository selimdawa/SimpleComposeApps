package com.flatcode.simplecomposeapps.pop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.pop.model.PopItem
import com.flatcode.simplecomposeapps.pop.repository.FunkoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopViewModel @Inject constructor(
    private val repository: FunkoRepository
) : ViewModel() {

    val isLoading: StateFlow<Boolean>
        field = MutableStateFlow(false)

    val error: StateFlow<String?>
        field = MutableStateFlow<String?>(null)

    val searchQuery: StateFlow<String>
        field = MutableStateFlow("")

    val pops: StateFlow<List<PopItem>> = combine(
        repository.getAllPops(),
        searchQuery
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
        viewModelScope,
        kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    init {
        loadPops()
    }

    fun loadPops() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                repository.loadPops()
                error.value = null
            } catch (e: Exception) {
                error.value = e.message
            }
            isLoading.value = false
        }
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }
}