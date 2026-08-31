package com.flatcode.simplecomposeapps.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flatcode.simplecomposeapps.blogger.activity.BloggerAppActivity
import com.flatcode.simplecomposeapps.candycrushgame.CandyCrushGameActivity
import com.flatcode.simplecomposeapps.joke.activity.JokeAppActivity
import com.flatcode.simplecomposeapps.multipledelete.MultiDeleteActivity
import com.flatcode.simplecomposeapps.newsapp.activity.NewsAppActivity
import com.flatcode.simplecomposeapps.stopwatch.StopWatchActivity
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.utils.DATA

class MainViewModel : ViewModel() {

    val dataMain: LiveData<List<Main>>
        field = MutableLiveData<List<Main>>()

    val isLoading: LiveData<Boolean>
        field = MutableLiveData(true)

    private val counts = intArrayOf(
        1, 1, 1, 2, 4, 1, 2, 2, 3, 2, 3, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1
    )

    fun getItems() {
        isLoading.value = true
        dataMain.value = data
        isLoading.value = false
    }

    private val data: List<Main>
        get() = listOf(
            Main(AppIcons.StopWatch, DATA.STOP_WATCH, counts[0], StopWatchActivity::class.java),
            Main(AppIcons.CandyCrush, DATA.CANDY_CRUSH, counts[1], CandyCrushGameActivity::class.java),
            Main(AppIcons.MultiDelete, DATA.MULTI_DELETE, counts[2], MultiDeleteActivity::class.java),
            Main(AppIcons.RandomImage, DATA.RANDOM_IMAGE, counts[3], null),
            Main(AppIcons.Blogger, DATA.BLOGGER, counts[4], BloggerAppActivity::class.java),
            Main(AppIcons.Joke, DATA.JOKE, counts[5], JokeAppActivity::class.java),
            Main(AppIcons.NewsMulti, DATA.NEWS_MULTI, counts[6], NewsAppActivity::class.java),
            Main(AppIcons.PdfReader, DATA.PDF_READER, counts[7], null),
            Main(AppIcons.VideoPlayer, DATA.VIDEO_PLAYER, counts[8], null),
            Main(AppIcons.WebApp, DATA.WEB_APP, counts[9], null),
            Main(AppIcons.WordPress, DATA.WORDPRESS, counts[10], null),
            Main(AppIcons.Dogs, DATA.DOGS, counts[11], null),
            Main(AppIcons.Countries, DATA.COUNTRIES, counts[12], null),
            Main(AppIcons.Calculator, DATA.CALCULATOR, counts[13], null),
            Main(AppIcons.Crypto, DATA.CRYPTO, counts[14], null),
            Main(AppIcons.Dictionary, DATA.DICTIONARY, counts[15], null),
            Main(AppIcons.Meals, DATA.MEALS, counts[16], null),
            Main(AppIcons.Pop, DATA.POP, counts[17], null),
            Main(AppIcons.Movie, DATA.MOVIE, counts[18], null),
            Main(AppIcons.News, DATA.NEWS, counts[19], NewsAppActivity::class.java),
            Main(AppIcons.RickAndMorty, DATA.RICK_AND_MORTY, counts[20], null),
            Main(AppIcons.Weather, DATA.WEATHER, counts[21], null),
            Main(AppIcons.Poke, DATA.POKE, counts[22], null),
            Main(AppIcons.TodoNote, DATA.TODO_NOTE, counts[23], null),
            Main(AppIcons.StockMarket, DATA.STOCK_MARKET, counts[24], null)
        )
}