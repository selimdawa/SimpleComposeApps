package com.flatcode.simplecomposeapps.multipledelete

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import android.content.SharedPreferences
import androidx.core.content.edit
import com.flatcode.simplecomposeapps.multipledelete.data.MultiDeleteDao
import com.flatcode.simplecomposeapps.multipledelete.data.MultiDeleteEntity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MultiDeleteViewModel @Inject constructor(
    private val multiDeleteDao: MultiDeleteDao,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    val items = mutableStateListOf<String>()

    val selectedItems = mutableStateListOf<String>()

    private val _isSelectionMode = mutableStateOf(false)
    val isSelectionMode: State<Boolean> = _isSelectionMode

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    init {
        observeItems()
    }

    private fun observeItems() {
        viewModelScope.launch {
            multiDeleteDao.getAllItems().collectLatest { entities ->
                items.clear()
                items.addAll(entities.map { it.text })
                _isLoading.value = false
            }
        }
    }

    fun setItems(initialItems: List<String>) {
        viewModelScope.launch {
            val isFirstTime = sharedPreferences.getBoolean("is_first_time", true)
            if (isFirstTime) {
                multiDeleteDao.insertAll(initialItems.map { MultiDeleteEntity(it) })
                sharedPreferences.edit { putBoolean("is_first_time", false) }
            }
        }
    }

    fun restoreItems(initialItems: List<String>) {
        viewModelScope.launch {
            multiDeleteDao.deleteAll()
            multiDeleteDao.insertAll(initialItems.map { MultiDeleteEntity(it) })
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
        viewModelScope.launch {
            multiDeleteDao.deleteByTexts(selectedItems)
            exitSelectionMode()
        }
    }
}