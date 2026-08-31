package com.flatcode.simplecomposeapps.multipledelete

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MultiDeleteViewModel : ViewModel() {

    val items: List<String>
        field = mutableStateListOf<String>()

    val selectedItems: List<String>
        field = mutableStateListOf<String>()

    val isSelectionMode: State<Boolean>
        field = mutableStateOf(false)

    fun setItems(initialItems: List<String>) {
        if (items.isEmpty()) {
            items.addAll(initialItems)
        }
    }

    fun toggleSelection(item: String) {
        if (selectedItems.contains(item)) {
            selectedItems.remove(item)
            if (selectedItems.isEmpty()) {
                isSelectionMode.value = false
            }
        } else {
            selectedItems.add(item)
            isSelectionMode.value = true
        }
    }

    fun enterSelectionMode(item: String) {
        isSelectionMode.value = true
        if (!selectedItems.contains(item)) {
            selectedItems.add(item)
        }
    }

    fun exitSelectionMode() {
        isSelectionMode.value = false
        selectedItems.clear()
    }

    fun selectAll() {
        if (selectedItems.size == items.size) {
            selectedItems.clear()
            isSelectionMode.value = false
        } else {
            selectedItems.clear()
            selectedItems.addAll(items)
            isSelectionMode.value = true
        }
    }

    fun deleteSelected() {
        items.removeAll(selectedItems)
        exitSelectionMode()
    }
}