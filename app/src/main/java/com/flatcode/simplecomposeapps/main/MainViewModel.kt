package com.flatcode.simplecomposeapps.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flatcode.simplecomposeapps.blogger.activity.BloggerAppActivity
import com.flatcode.simplecomposeapps.calculator.CalculatorActivity
import com.flatcode.simplecomposeapps.countries.CountriesActivity
import com.flatcode.simplecomposeapps.crypto.CryptoActivity
import com.flatcode.simplecomposeapps.dictionary.DictionaryActivity
import com.flatcode.simplecomposeapps.dogs.DogsActivity
import com.flatcode.simplecomposeapps.meals.MealsActivity
import com.flatcode.simplecomposeapps.pop.PopActivity
import com.flatcode.simplecomposeapps.movies.MoviesActivity
import com.flatcode.simplecomposeapps.news2.News2Activity
import com.flatcode.simplecomposeapps.pokemon.PokemonActivity
import com.flatcode.simplecomposeapps.rickAndMorty.RickAndMortyActivity
import com.flatcode.simplecomposeapps.stockmarket.StockMarketActivity
import com.flatcode.simplecomposeapps.todoNote.TodoNoteActivity
import com.flatcode.simplecomposeapps.weather.WeatherActivity
import com.flatcode.simplecomposeapps.candycrushgame.CandyCrushGameActivity
import com.flatcode.simplecomposeapps.joke.activity.JokeAppActivity
import com.flatcode.simplecomposeapps.multipledelete.MultiDeleteActivity
import com.flatcode.simplecomposeapps.news.activity.NewsAppActivity
import com.flatcode.simplecomposeapps.pdfreader.activity.PdfReaderActivity
import com.flatcode.simplecomposeapps.randomimagegenerating.RandomImageGeneratingActivity
import com.flatcode.simplecomposeapps.stopwatch.StopWatchActivity
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.videoplayer.activity.VideoPlayerActivity
import com.flatcode.simplecomposeapps.web.WebAppActivity
import com.flatcode.simplecomposeapps.wordpress.activity.WordpressActivity

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
            Main(
                AppIcons.CandyCrush, DATA.CANDY_CRUSH, counts[1], CandyCrushGameActivity::class.java
            ),
            Main(
                AppIcons.MultiDelete, DATA.MULTI_DELETE, counts[2], MultiDeleteActivity::class.java
            ),
            Main(AppIcons.RandomImage, DATA.RANDOM_IMAGE, counts[3], RandomImageGeneratingActivity::class.java),
            Main(AppIcons.Blogger, DATA.BLOGGER, counts[4], BloggerAppActivity::class.java),
            Main(AppIcons.Joke, DATA.JOKE, counts[5], JokeAppActivity::class.java),
            Main(AppIcons.NewsMulti, DATA.NEWS, counts[6], NewsAppActivity::class.java),
            Main(AppIcons.PdfReader, DATA.PDF_READER, counts[7], PdfReaderActivity::class.java),
            Main(
                AppIcons.VideoPlayer, DATA.VIDEO_PLAYER, counts[8], VideoPlayerActivity::class.java
            ),
            Main(AppIcons.WebApp, DATA.WEB_APP, counts[9], WebAppActivity::class.java),
            Main(AppIcons.WordPress, DATA.WORDPRESS, counts[10], WordpressActivity::class.java),
            Main(AppIcons.Dogs, DATA.DOGS, counts[11], DogsActivity::class.java),
            Main(AppIcons.Countries, DATA.COUNTRIES, counts[12], CountriesActivity::class.java),
            Main(AppIcons.Calculator, DATA.CALCULATOR, counts[13], CalculatorActivity::class.java),
            Main(AppIcons.Crypto, DATA.CRYPTO, counts[14], CryptoActivity::class.java),
            Main(AppIcons.Dictionary, DATA.DICTIONARY, counts[15], DictionaryActivity::class.java),
            Main(AppIcons.Meals, DATA.MEALS, counts[16], MealsActivity::class.java),
            Main(AppIcons.Pop, DATA.POP, counts[17], PopActivity::class.java),
            Main(AppIcons.Movie, DATA.MOVIE, counts[18], MoviesActivity::class.java),
            Main(AppIcons.News, DATA.NEWS_2, counts[19], News2Activity::class.java),
            Main(AppIcons.RickAndMorty, DATA.RICK_AND_MORTY, counts[20], RickAndMortyActivity::class.java),
            Main(AppIcons.Weather, DATA.WEATHER, counts[21], WeatherActivity::class.java),
            Main(AppIcons.Poke, DATA.POKE, counts[22], PokemonActivity::class.java),
            Main(AppIcons.TodoNote, DATA.TODO_NOTE, counts[23], TodoNoteActivity::class.java),
            Main(AppIcons.StockMarket, DATA.STOCK_MARKET, counts[24], StockMarketActivity::class.java)
        )
}