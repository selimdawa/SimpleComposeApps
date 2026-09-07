package com.flatcode.simplecomposeapps.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainInfoViewModel @Inject constructor() : ViewModel() {

    private val _dataMainInfo = MutableLiveData<List<MainInfo>>()
    val dataMainInfo: LiveData<List<MainInfo>> = _dataMainInfo

    fun getInfoItems() {
        _dataMainInfo.value = DATA.MAIN_INFO_DATA
    }
}