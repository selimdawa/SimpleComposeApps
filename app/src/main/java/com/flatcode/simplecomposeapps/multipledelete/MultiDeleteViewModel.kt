package com.flatcode.simplecomposeapps.multipledelete

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MultiDeleteViewModel : ViewModel() {

    val items = mutableStateListOf<String>()

    val selectedItems = mutableStateListOf<String>()

    private val _isSelectionMode = mutableStateOf(false)
    val isSelectionMode: State<Boolean> = _isSelectionMode

    fun setItems(initialItems: List<String>) {
        if (items.isEmpty()) {
            items.addAll(initialItems)
        }
    }

    fun toggleSelection(item: String) {
        if (selectedItems.contains(item)) {
            selectedItems.remove(item)
            if (selectedItems.isEmpty()) {
                _isSelectionMode.value = false
            }
        } else {
            selectedItems.add(item)
            _isSelectionMode.value = true
        }
    }

    fun enterSelectionMode(item: String) {
        _isSelectionMode.value = true
        if (!selectedItems.contains(item)) {
            selectedItems.add(item)
        }
    }

    fun exitSelectionMode() {
        _isSelectionMode.value = false
        selectedItems.clear()
    }

    fun selectAll() {
        if (selectedItems.size == items.size) {
            selectedItems.clear()
            _isSelectionMode.value = false
        } else {
            selectedItems.clear()
            selectedItems.addAll(items)
            _isSelectionMode.value = true
        }
    }

    fun deleteSelected() {
        items.removeAll(selectedItems)
        exitSelectionMode()
    }
}