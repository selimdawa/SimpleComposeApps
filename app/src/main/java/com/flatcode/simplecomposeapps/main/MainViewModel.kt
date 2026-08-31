package com.flatcode.simplecomposeapps.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flatcode.simplecomposeapps.stopwatch.StopWatchActivity
import com.flatcode.simplecomposeapps.ui.AppIcons
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
            Main(AppIcons.StopWatch, DATA.STOP_WATCH, counts[0], StopWatchActivity::class.java),
            Main(AppIcons.CandyCrush, DATA.CANDY_CRUSH, counts[1], null),
            Main(AppIcons.MultiDelete, DATA.MULTI_DELETE, counts[2], null),
            Main(AppIcons.RandomImage, DATA.RANDOM_IMAGE, counts[3], null),
            Main(AppIcons.Blogger, DATA.BLOGGER, counts[4], null),
            Main(AppIcons.Joke, DATA.JOKE, counts[5], null),
            Main(AppIcons.LiveTv, DATA.LIVE_TV, counts[6], null),
            Main(AppIcons.NewsMulti, DATA.NEWS_MULTI, counts[7], null),
            Main(AppIcons.PdfReader, DATA.PDF_READER, counts[8], null),
            Main(AppIcons.VideoPlayer, DATA.VIDEO_PLAYER, counts[9], null),
            Main(AppIcons.WebApp, DATA.WEB_APP, counts[10], null),
            Main(AppIcons.WordPress, DATA.WORDPRESS, counts[11], null),
            Main(AppIcons.Dogs, DATA.DOGS, counts[12], null),
            Main(AppIcons.Countries, DATA.COUNTRIES, counts[13], null),
            Main(AppIcons.Calculator, DATA.CALCULATOR, counts[14], null),
            Main(AppIcons.Crypto, DATA.CRYPTO, counts[15], null),
            Main(AppIcons.Dictionary, DATA.DICTIONARY, counts[16], null),
            Main(AppIcons.Meals, DATA.MEALS, counts[17], null),
            Main(AppIcons.Pop, DATA.POP, counts[18], null),
            Main(AppIcons.Movie, DATA.MOVIE, counts[19], null),
            Main(AppIcons.News, DATA.NEWS, counts[20], null),
            Main(AppIcons.RickAndMorty, DATA.RICK_AND_MORTY, counts[21], null),
            Main(AppIcons.Weather, DATA.WEATHER, counts[22], null),
            Main(AppIcons.Poke, DATA.POKE, counts[23], null),
            Main(AppIcons.TodoNote, DATA.TODO_NOTE, counts[24], null),
            Main(AppIcons.StockMarket, DATA.STOCK_MARKET, counts[25], null)
        )
}