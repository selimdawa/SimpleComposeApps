package com.flatcode.simplecomposeapps.multipledelete

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MultiDeleteViewModel : ViewModel() {

    private val _items = mutableStateListOf<String>()
    val items: List<String> get() = _items

    private val _selectedItems = mutableStateListOf<String>()
    val selectedItems: List<String> get() = _selectedItems

    private val _isSelectionMode = mutableStateOf(false)
    val isSelectionMode: State<Boolean> = _isSelectionMode

    fun setItems(initialItems: List<String>) {
        if (_items.isEmpty()) {
            _items.addAll(initialItems)
        }
    }

    fun toggleSelection(item: String) {
        if (_selectedItems.contains(item)) {
            _selectedItems.remove(item)
            if (_selectedItems.isEmpty()) {
                _isSelectionMode.value = false
            }
        } else {
            _selectedItems.add(item)
            _isSelectionMode.value = true
        }
    }

    fun enterSelectionMode(item: String) {
        _isSelectionMode.value = true
        if (!_selectedItems.contains(item)) {
            _selectedItems.add(item)
        }
    }

    fun exitSelectionMode() {
        _isSelectionMode.value = false
        _selectedItems.clear()
    }

    fun selectAll() {
        if (_selectedItems.size == _items.size) {
            _selectedItems.clear()
            _isSelectionMode.value = false
        } else {
            _selectedItems.clear()
            _selectedItems.addAll(_items)
            _isSelectionMode.value = true
        }
    }

    fun deleteSelected() {
        _items.removeAll(_selectedItems)
        exitSelectionMode()
    }
}