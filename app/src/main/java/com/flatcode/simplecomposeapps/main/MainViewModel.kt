package com.flatcode.simplecomposeapps.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flatcode.simplecomposeapps.R
import com.flatcode.simplecomposeapps.utils.DATA

class MainViewModel : ViewModel() {

    private val _dataMain = MutableLiveData<List<Main>>()
    val dataMain: LiveData<List<Main>> = _dataMain

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val counts = intArrayOf(
        1, 1, 1, 2, 4, 1, 4, 2, 2, 3, 2, 3, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1
    )

    fun getItems() {
        _isLoading.value = true
        _dataMain.value = data
        _isLoading.value = false
    }

    private val data: List<Main>
        get() = listOf(
            Main(R.drawable.ic_stop_watch, DATA.STOP_WATCH, counts[0], null),
            Main(R.drawable.ic_candy_crush, DATA.CANDY_CRUSH, counts[1], null),
            Main(R.drawable.ic_multi_delete, DATA.MULTI_DELETE, counts[2], null),
            Main(R.drawable.ic_random, DATA.RANDOM_IMAGE, counts[3], null),
            Main(R.drawable.ic_blogger, DATA.BLOGGER, counts[4], null),
            Main(R.drawable.ic_joke, DATA.JOKE, counts[5], null),
            Main(R.drawable.ic_live_tv, DATA.LIVE_TV, counts[6], null),
            Main(R.drawable.ic_news, DATA.NEWS_MULTI, counts[7], null),
            Main(R.drawable.ic_pdf_reader, DATA.PDF_READER, counts[8], null),
            Main(R.drawable.ic_video_player, DATA.VIDEO_PLAYER, counts[9], null),
            Main(R.drawable.ic_web, DATA.WEB_APP, counts[10], null),
            Main(R.drawable.ic_wordpress, DATA.WORDPRESS, counts[11], null),
            Main(R.drawable.ic_home_work, DATA.DOGS, counts[12], null),
            Main(R.drawable.ic_flag, DATA.COUNTRIES, counts[13], null),
            Main(R.drawable.ic_calculate, DATA.CALCULATOR, counts[14], null),
            Main(R.drawable.ic_monetization, DATA.CRYPTO, counts[15], null),
            Main(R.drawable.ic_words, DATA.DICTIONARY, counts[16], null),
            Main(R.drawable.ic_meal, DATA.MEALS, counts[17], null),
            Main(R.drawable.ic_game, DATA.POP, counts[18], null),
            Main(R.drawable.ic_movie, DATA.MOVIE, counts[19], null),
            Main(R.drawable.ic_feed, DATA.NEWS, counts[20], null),
            Main(R.drawable.ic_child, DATA.RICK_AND_MORTY, counts[21], null),
            Main(R.drawable.ic_nights, DATA.WEATHER, counts[22], null),
            Main(R.drawable.ic_gamepad, DATA.POKE, counts[23], null),
            Main(R.drawable.ic_note, DATA.TODO_NOTE, counts[24], null),
            Main(R.drawable.ic_company, DATA.STOCK_MARKET, counts[25], null)
        )
}