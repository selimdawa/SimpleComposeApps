package com.flatcode.simplecomposeapps.multipledelete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _items = MutableLiveData<List<String>>(emptyList())
    val items: LiveData<List<String>> = _items

    private val _selectedItems = MutableLiveData<Set<String>>(emptySet())
    val selectedItems: LiveData<Set<String>> = _selectedItems

    private val _isSelectionMode = MutableLiveData(false)
    val isSelectionMode: LiveData<Boolean> = _isSelectionMode

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        observeItems()
    }

    private fun observeItems() {
        viewModelScope.launch {
            multiDeleteDao.getAllItems().collectLatest { entities ->
                _items.postValue(entities.map { it.text })
                _isLoading.postValue(false)
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
        val currentSelected = (_selectedItems.value ?: emptySet()).toMutableSet()
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
        _isSelectionMode.value = true
        val currentSelected = (_selectedItems.value ?: emptySet()).toMutableSet()
        currentSelected.add(item)
        _selectedItems.value = currentSelected
    }

    fun exitSelectionMode() {
        _isSelectionMode.value = false
        _selectedItems.value = emptySet()
    }

    fun selectAll() {
        val currentItems = _items.value ?: emptyList()
        if ((_selectedItems.value ?: emptySet()).size == currentItems.size) {
            _selectedItems.value = emptySet()
            _isSelectionMode.value = false
        } else {
            _selectedItems.value = currentItems.toSet()
            _isSelectionMode.value = true
        }
    }

    fun deleteSelected() {
        viewModelScope.launch {
            val selected = _selectedItems.value?.toList() ?: emptyList()
            multiDeleteDao.deleteByTexts(selected)
            exitSelectionMode()
        }
    }
}