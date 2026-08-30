package com.flatcode.simplecomposeapps.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flatcode.simplecomposeapps.utils.DATA

class MainInfoViewModel : ViewModel() {

    private val _dataMainInfo = MutableLiveData<List<MainInfo>>()
    val dataMainInfo: LiveData<List<MainInfo>> = _dataMainInfo

    fun getInfoItems() {
        _dataMainInfo.value = dataInfo
    }

    private val dataInfo: List<MainInfo>
        get() = listOf(
            MainInfo(DATA.STOP_WATCH, 0, 0, 0, 0),
            MainInfo(DATA.CANDY_CRUSH, 0, 0, 0, 0),
            MainInfo(DATA.MULTI_DELETE, 0, 0, 0, 0),
            MainInfo(DATA.RANDOM_IMAGE, 0, 0, 0, 0),
            MainInfo(DATA.BLOGGER, 0, 0, 0, 0),
            MainInfo(DATA.JOKE, 0, 0, 0, 0),
            MainInfo(DATA.LIVE_TV, 0, 0, 0, 0),
            MainInfo(DATA.NEWS_MULTI, 0, 0, 0, 0),
            MainInfo(DATA.PDF_READER, 0, 0, 0, 0),
            MainInfo(DATA.VIDEO_PLAYER, 0, 0, 0, 0),
            MainInfo(DATA.WEB_APP, 0, 0, 0, 0),
            MainInfo(DATA.WORDPRESS, 0, 0, 0, 0),
            MainInfo(DATA.DOGS, 1, 1, 1, 1),
            MainInfo(DATA.COUNTRIES, 1, 1, 1, 1),
            MainInfo(DATA.CALCULATOR, 1, 1, 1, 1),
            MainInfo(DATA.CRYPTO, 1, 1, 1, 1),
            MainInfo(DATA.DICTIONARY, 1, 1, 1, 1),
            MainInfo(DATA.MEALS, 1, 1, 1, 1),
            MainInfo(DATA.POP, 1, 1, 1, 1),
            MainInfo(DATA.MOVIE, 1, 1, 1, 1),
            MainInfo(DATA.NEWS, 1, 1, 1, 1),
            MainInfo(DATA.RICK_AND_MORTY, 1, 1, 1, 1),
            MainInfo(DATA.WEATHER, 1, 1, 1, 1),
            MainInfo(DATA.POKE, 1, 1, 1, 1),
            MainInfo(DATA.TODO_NOTE, 1, 1, 1, 1),
            MainInfo(DATA.STOCK_MARKET, 0, 0, 0, 0)
        )
}