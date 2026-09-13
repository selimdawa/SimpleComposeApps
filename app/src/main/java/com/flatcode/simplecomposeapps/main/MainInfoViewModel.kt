package com.flatcode.simplecomposeapps.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.main.data.MainInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainInfoViewModel @Inject constructor(
    private val repository: MainInfoRepository
) : ViewModel() {

    private val _dataMainInfo = MutableStateFlow<List<MainInfo>>(emptyList())
    val dataMainInfo: StateFlow<List<MainInfo>> = _dataMainInfo.asStateFlow()

    fun getInfoItems() {
        Timber.d("Getting about/info items")
        viewModelScope.launch {
            _dataMainInfo.value = repository.getInfoItems()
        }
    }
}