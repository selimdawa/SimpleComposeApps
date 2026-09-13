package com.flatcode.simplecomposeapps.multipledelete

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.multipledelete.data.MultiDeleteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MultiDeleteViewModel @Inject constructor(
    private val repository: MultiDeleteRepository
) : ViewModel() {

    private val _items = MutableStateFlow<List<String>>(emptyList())
    val items: StateFlow<List<String>> = _items.asStateFlow()

    private val _selectedItems = MutableStateFlow<Set<String>>(emptySet())
    val selectedItems: StateFlow<Set<String>> = _selectedItems.asStateFlow()

    private val _isSelectionMode = MutableStateFlow(false)
    val isSelectionMode: StateFlow<Boolean> = _isSelectionMode.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        observeItems()
    }

    private fun observeItems() {
        viewModelScope.launch {
            repository.getAllItems().collectLatest { entities ->
                _items.value = entities.map { it.text }
                _isLoading.value = false
            }
        }
    }

    fun setItems(initialItems: List<String>) {
        viewModelScope.launch {
            repository.insertInitialItems(initialItems)
        }
    }

    fun restoreItems(initialItems: List<String>) {
        Timber.d("Restoring elements to database")
        viewModelScope.launch {
            repository.restoreItems(initialItems)
        }
    }

    fun toggleSelection(item: String) {
        val currentSelected = _selectedItems.value.toMutableSet()
        if (currentSelected.contains(item)) {
            currentSelected.remove(item)
            if (currentSelected.isEmpty()) {
                _isSelectionMode.value = false
            }
        } else {
            currentSelected.add(item)
            _isSelectionMode.value = true
        }
        _selectedItems.value = currentSelected
    }

    fun enterSelectionMode(item: String) {
        Timber.d("Entering multi-selection mode")
        _isSelectionMode.value = true
        val currentSelected = _selectedItems.value.toMutableSet()
        currentSelected.add(item)
        _selectedItems.value = currentSelected
    }

    fun exitSelectionMode() {
        _isSelectionMode.value = false
        _selectedItems.value = emptySet()
    }

    fun selectAll() {
        val currentItems = _items.value
        if (_selectedItems.value.size == currentItems.size) {
            _selectedItems.value = emptySet()
            _isSelectionMode.value = false
        } else {
            _selectedItems.value = currentItems.toSet()
            _isSelectionMode.value = true
        }
    }

    fun deleteSelected() {
        viewModelScope.launch {
            val selected = _selectedItems.value.toList()
            Timber.d("Deleting selected items count: %d", selected.size)
            repository.deleteByTexts(selected)
            exitSelectionMode()
        }
    }
}