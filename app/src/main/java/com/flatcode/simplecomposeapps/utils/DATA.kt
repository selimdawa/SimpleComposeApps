package com.flatcode.simplecomposeapps.utils

import androidx.compose.ui.graphics.vector.ImageVector
import com.flatcode.simplecomposeapps.blogger.activity.BloggerAppActivity
import com.flatcode.simplecomposeapps.calculator.CalculatorActivity
import com.flatcode.simplecomposeapps.candycrushgame.CandyCrushGameActivity
import com.flatcode.simplecomposeapps.countries.CountriesActivity
import com.flatcode.simplecomposeapps.crypto.CryptoActivity
import com.flatcode.simplecomposeapps.dictionary.DictionaryActivity
import com.flatcode.simplecomposeapps.dogs.DogsActivity
import com.flatcode.simplecomposeapps.joke.activity.JokeAppActivity
import com.flatcode.simplecomposeapps.main.Main
import com.flatcode.simplecomposeapps.main.MainInfo
import com.flatcode.simplecomposeapps.meals.activity.MealsActivity
import com.flatcode.simplecomposeapps.movies.MoviesActivity
import com.flatcode.simplecomposeapps.multipledelete.MultiDeleteActivity
import com.flatcode.simplecomposeapps.news.activity.NewsAppActivity
import com.flatcode.simplecomposeapps.news2.News2Activity
import com.flatcode.simplecomposeapps.pdfreader.activity.PdfReaderActivity
import com.flatcode.simplecomposeapps.pokemon.PokemonActivity
import com.flatcode.simplecomposeapps.pop.PopActivity
import com.flatcode.simplecomposeapps.randomcatsimage.RandomCatsImageActivity
import com.flatcode.simplecomposeapps.rickAndMorty.RickAndMortyActivity
import com.flatcode.simplecomposeapps.stockmarket.StockMarketActivity
import com.flatcode.simplecomposeapps.stopwatch.StopWatchActivity
import com.flatcode.simplecomposeapps.todoNote.TodoNoteActivity
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.videoplayer.activity.VideoPlayerActivity
import com.flatcode.simplecomposeapps.weather.WeatherActivity
import com.flatcode.simplecomposeapps.web.activity.WebAppActivity
import com.flatcode.simplecomposeapps.wordpress.activity.WordpressActivity
import androidx.navigation.NavType
import androidx.navigation.navArgument

@Suppress("SpellCheckingInspection")
object DATA {

    data class NavItem(
        val route: Any, val label: String, val icon: ImageVector
    )

    val NEWS_NAV = listOf(
        NavItem(News2Activity.Everything, Strings.EVERYTHING, AppIcons.MultiDelete),
        NavItem(News2Activity.TopArticles, Strings.TOP_ARTICLES, AppIcons.News)
    )

    val MEALS_NAV = listOf(
        NavItem(MealsActivity.Home, Strings.HOME, AppIcons.Home),
        NavItem(MealsActivity.Favorites, Strings.FAVORITES, AppIcons.Favorite),
        NavItem(MealsActivity.Categories, Strings.CATEGORIES, AppIcons.Category)
    )

    val RICK_NAV = listOf(
        NavItem(RickAndMortyActivity.Character, Strings.CHARACTER, AppIcons.RickAndMorty),
        NavItem(RickAndMortyActivity.Location, Strings.LOCATION, AppIcons.Location),
        NavItem(RickAndMortyActivity.Episode, Strings.EPISODE, AppIcons.EventNote)
    )

    val TODO_NAV = listOf(
        NavItem(TodoNoteActivity.Tasks, Strings.TASKS, AppIcons.TodoCheck),
        NavItem(TodoNoteActivity.Notes, Strings.NOTES, AppIcons.TodoNote)
    )

    val WEB_NAV = listOf(
        NavItem(WebAppActivity.Home, Strings.HOME, AppIcons.Home),
        NavItem(WebAppActivity.History, Strings.HISTORY, AppIcons.History),
        NavItem(WebAppActivity.Bookmarks, Strings.BOOKMARKS, AppIcons.Bookmark)
    )

    // Nav Arguments
    val BLOGGER_POST_ARGS = listOf(navArgument("postId") { type = NavType.StringType })
    val BLOGGER_PAGE_ARGS = listOf(navArgument("pageId") { type = NavType.StringType })
    val COUNTRY_DETAIL_ARGS = listOf(navArgument(COUNTRY_UUID) { type = NavType.IntType })
    val CRYPTO_DETAIL_ARGS = listOf(
        navArgument("symbol") { type = NavType.StringType },
        navArgument("coinId") { type = NavType.IntType }
    )
    val MOVIE_DETAIL_ARGS = listOf(navArgument("movieJson") { type = NavType.StringType })
    val POKE_DETAIL_ARGS = listOf(navArgument("pokeId") { type = NavType.IntType })

    @Suppress("SpellCheckingInspection")
    val BREEDS_LIST = listOf(
        "Affenpinscher",
        "African",
        "Airedale",
        "Akita",
        "Appenzeller",
        "Australian Shepherd",
        "Basenji",
        "Beagle",
        "Bluetick",
        "Borzoi",
        "Bouvier",
        "Boxer",
        "Brabancon",
        "Briard",
        "Buhund Norwegian",
        "Bulldog Boston",
        "Bulldog English",
        "Bulldog French",
        "Bullterrier Staffordshire",
        "Cattledog Australian",
        "Chihuahua",
        "Chow",
        "Clumber",
        "Cockapoo",
        "Collie Border",
        "Coonhound",
        "Corgi Cardigan",
        "Cotondetulear",
        "Dachshund",
        "Dalmatian",
        "Dane Great",
        "Deerhound Scottish",
        "Dhole",
        "Dingo",
        "Doberman",
        "Elkhound Norwegian",
        "Entlebucher",
        "Eskimo",
        "Finnish Lapphund",
        "Frise Bichon",
        "Germanshepherd",
        "Golden",
        "Greyhound Italian",
        "Groenendael",
        "Havanese",
        "Hound Afghan",
        "Hound Basset",
        "Hound Blood",
        "Hound English",
        "Hound Ibizan",
        "Hound Plott",
        "Hound Walker",
        "Husky",
        "Keeshond",
        "Kelpie",
        "Komondor",
        "Kuvasz",
        "Labradoodle",
        "Labrador",
        "Leonberg",
        "Lhasa",
        "Malamute",
        "Malinois",
        "Maltese",
        "Mastiff Bull",
        "Mastiff English",
        "Mastiff Tibetan",
        "Mexicanhairless",
        "Mix",
        "Mountain Bernese",
        "Mountain Swiss",
        "Newfoundland",
        "Otterhound",
        "Ovcharka Caucasian",
        "Papillon",
        "Pekinese",
        "Pembroke",
        "Pinscher Miniature",
        "Pitbull",
        "Pointer German",
        "Pointer Germanlonghair",
        "Pomeranian",
        "Poodle Medium",
        "Poodle Miniature",
        "Poodle Standard",
        "Poodle Toy",
        "Pug",
        "Puggle",
        "Pyrenees",
        "Redbone",
        "Retriever Chesapeake",
        "Retriever Curly",
        "Retriever Flatcoated",
        "Retriever Golden",
        "Ridgeback Rhodesian",
        "Rottweiler",
        "Saluki",
        "Samoyed",
        "Schipperke",
        "Schnauzer Giant",
        "Schnauzer Miniature",
        "Segugio Italian",
        "Setter English",
        "Setter Gordon",
        "Setter Irish",
        "Sharpei",
        "Sheepdog English",
        "Sheepdog Shetland",
        "Shiba",
        "Shihtzu",
        "Spaniel Blenheim",
        "Spaniel Brittany",
        "Spaniel Cocker",
        "Spaniel Irish",
        "Spaniel Japanese",
        "Spaniel Sussex",
        "Spaniel Welsh",
        "Springer English",
        "Stbernard",
        "Terrier America",
        "Terrier Australia",
        "Terrier Bedlington",
        "Terrier Borde",
        "Terrier Cairn",
        "Terrier Dandi",
        "Terrier Fox",
        "Terrier Irish",
        "Terrier Kerryblue",
        "Terrier Lakeland",
        "Terrier Norfolk",
        "Terrier Norwich",
        "Terrier Patterdale",
        "Terrier Russell",
        "Terrier Scottish",
        "Terrier Sealyham",
        "Terrier Silky",
        "Terrier Tibetan",
        "Terrier Toy",
        "Terrier Welsh",
        "Terrier Westhighland",
        "Terrier Wheaten",
        "Terrier Yorkshire",
        "Tervuren",
        "Vizsla",
        "Waterdog Spanis",
        "Weimaraner",
        "Whippet",
        "Wolfhound Irish"
    )

    val MULTI_DELETE_VALUES = listOf(
        Strings.ONE,
        Strings.TWO,
        Strings.THREE,
        Strings.FOUR,
        Strings.FIVE,
        Strings.SIX,
        Strings.SEVEN,
        Strings.EIGHT,
        Strings.NINE,
        Strings.TEN,
        Strings.ELEVEN,
        Strings.TWELVE,
        Strings.THIRTEEN,
        Strings.FOURTEEN,
        Strings.FIFTEEN,
        Strings.SIXTEEN,
        Strings.SEVENTEEN,
        Strings.EIGHTEEN,
        Strings.NINETEEN,
        Strings.TWENTY
    )

    val JOKE_CATEGORIES =
        listOf("Any", Strings.PROGRAMMING, "Dark", "Spooky", "Misc", "Pun", "Christmas")

    val NEWS_CATEGORIES =
        listOf("general", "business", "entertainment", "health", "science", "sports", "technology")

    val WEATHER_TABS = listOf(Strings.HOURS, Strings.DAYS)

    val MAIN_INFO_DATA = listOf(
        MainInfo(STOP_WATCH, 1, 1, 1, 1),
        MainInfo(CANDY_CRUSH, 1, 1, 1, 1),
        MainInfo(MULTI_DELETE, 1, 1, 1, 1),
        MainInfo(RANDOM_IMAGE, 1, 1, 1, 1),
        MainInfo(BLOGGER, 1, 1, 0, 0),
        MainInfo(JOKE, 1, 1, 1, 1),
        MainInfo(NEWS, 1, 1, 1, 1),
        MainInfo(PDF_READER, 1, 1, 1, 1),
        MainInfo(VIDEO_PLAYER, 1, 1, 1, 1),
        MainInfo(WEB, 1, 1, 1, 1),
        MainInfo(WORDPRESS, 1, 1, 1, 1),
        MainInfo(DOGS, 1, 1, 1, 1),
        MainInfo(COUNTRIES, 1, 1, 1, 1),
        MainInfo(CALCULATOR, 1, 1, 1, 1),
        MainInfo(CRYPTO, 1, 1, 1, 1),
        MainInfo(DICTIONARY, 1, 0, 1, 1),
        MainInfo(MEALS, 1, 1, 1, 1),
        MainInfo(POP, 1, 1, 1, 1),
        MainInfo(MOVIE, 1, 1, 1, 1),
        MainInfo(NEWS_2, 1, 1, 1, 1),
        MainInfo(RICK_AND_MORTY, 1, 1, 1, 1),
        MainInfo(WEATHER, 1, 1, 1, 1),
        MainInfo(POKE, 1, 1, 1, 1),
        MainInfo(TODO_NOTE, 1, 1, 1, 1),
        MainInfo(STOCK_MARKET, 1, 1, 1, 1)
    )

    private val counts = intArrayOf(
        1, 1, 1, 1, 4, 1, 2, 2, 3, 2, 3, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1
    )

    val MAIN_DATA = listOf(
        Main(AppIcons.StopWatch, STOP_WATCH, counts[0], StopWatchActivity::class.java),
        Main(AppIcons.CandyCrush, CANDY_CRUSH, counts[1], CandyCrushGameActivity::class.java),
        Main(AppIcons.MultiDelete, MULTI_DELETE, counts[2], MultiDeleteActivity::class.java),
        Main(AppIcons.RandomImage, RANDOM_IMAGE, counts[3], RandomCatsImageActivity::class.java),
        Main(AppIcons.Blogger, BLOGGER, counts[4], BloggerAppActivity::class.java),
        Main(AppIcons.Joke, JOKE, counts[5], JokeAppActivity::class.java),
        Main(AppIcons.NewsMulti, NEWS, counts[6], NewsAppActivity::class.java),
        Main(AppIcons.PdfReader, PDF_READER, counts[7], PdfReaderActivity::class.java),
        Main(AppIcons.VideoPlayer, VIDEO_PLAYER, counts[8], VideoPlayerActivity::class.java),
        Main(AppIcons.WebApp, WEB, counts[9], WebAppActivity::class.java),
        Main(AppIcons.WordPress, WORDPRESS, counts[10], WordpressActivity::class.java),
        Main(AppIcons.Dogs, DOGS, counts[11], DogsActivity::class.java),
        Main(AppIcons.Countries, COUNTRIES, counts[12], CountriesActivity::class.java),
        Main(AppIcons.Calculator, CALCULATOR, counts[13], CalculatorActivity::class.java),
        Main(AppIcons.Crypto, CRYPTO, counts[14], CryptoActivity::class.java),
        Main(AppIcons.Dictionary, DICTIONARY, counts[15], DictionaryActivity::class.java),
        Main(AppIcons.Meals, MEALS, counts[16], MealsActivity::class.java),
        Main(AppIcons.Pop, POP, counts[17], PopActivity::class.java),
        Main(AppIcons.Movie, MOVIE, counts[18], MoviesActivity::class.java),
        Main(AppIcons.News, NEWS_2, counts[19], News2Activity::class.java),
        Main(AppIcons.RickAndMorty, RICK_AND_MORTY, counts[20], RickAndMortyActivity::class.java),
        Main(AppIcons.Weather, WEATHER, counts[21], WeatherActivity::class.java),
        Main(AppIcons.Poke, POKE, counts[22], PokemonActivity::class.java),
        Main(AppIcons.TodoNote, TODO_NOTE, counts[23], TodoNoteActivity::class.java),
        Main(AppIcons.StockMarket, STOCK_MARKET, counts[24], StockMarketActivity::class.java)
    )

    val ICON_PATH_DATA = listOf(
        Triple("M36.06,28.92L36.06,32.18", 0xFFE7E7E7, 0xFFCCCCCf),
        Triple("M39.45,29.88L37.82,32.71", 0xFFCACACA, 0xFFC8C8CC),
        Triple("M42.12,32.32L39.3,33.95", 0xFFCDCDCD, 0xFFBBBBBE),
        Triple("M39.8,35.98L43.06,35.98", 0xFFCBCBCB, 0xFFB2B2B7),
        Triple("M32.77,29.99L34.4,32.81", 0xFFEDEDED, 0xFFD0D0D4),
        Triple("M30.1,32.42L32.92,34.05", 0xFF525252, 0xFF949497),
        Triple("M32.42,35.98L29.16,35.98", 0xFF6E6E6E, 0xFF97979B),
        Triple("M36.06,43.08L36.06,39.82", 0xFFA0A0A0, 0xFFA8A8AC),
        Triple("M39.7,41.99L38.07,39.16", 0xFFCACACA, 0xFFCACACA),
        Triple("M42.19,39.4L39.37,37.77", 0xFFCCCCCC, 0xFFB6B6BA),
        Triple("M32.46,41.98L34.09,39.16", 0xFF909090, 0xFFA1A1A5),
        Triple("M29.85,39.4L32.67,37.77", 0xFF7A7A7A, 0xFF9D9DA0)
    )

    //Database
    const val EMPTY = ""
    const val DATA = "data"
    const val UNKNOWN = "Unknown"

    //Main
    const val STOP_WATCH = "Stop Watch"
    const val CANDY_CRUSH = "Candy Crush Game"
    const val MULTI_DELETE = "Multiple Delete"
    const val RANDOM_IMAGE = "Random Cats Image"
    const val BLOGGER = "Blogger"
    const val JOKE = "Joke"
    const val NEWS = "News"
    const val PDF_READER = "Pdf Reader"
    const val VIDEO_PLAYER = "Video Player"
    const val WEB = "Web"
    const val WORDPRESS = "WordPress Blog"
    const val DOGS = "Dogs"
    const val COUNTRIES = "Countries"
    const val CALCULATOR = "Calculator"
    const val CRYPTO = "Crypto"
    const val DICTIONARY = "Dictionary"
    const val MEALS = "Meals"
    const val POP = "Pop"
    const val MOVIE = "Movies"
    const val NEWS_2 = "News 2"
    const val RICK_AND_MORTY = "Rick & Morty"
    const val WEATHER = "Weather"
    const val POKE = "Pokémon"
    const val TODO_NOTE = "TODO-Note"
    const val STOCK_MARKET = "Stock Market"

    const val ADD_RESULT_OK = 1
    const val EDIT_RESULT_OK = 2

    //Class Name
    const val COUNTRY_DETAILS = "Country Details"
    const val CRYPTO_DETAILS = "Crypto Details"
    const val MEANING_OF_THE_WORD = "Meaning Of The Word"
    const val DAGGER_HILT = "Dagger-Hilt :"
    const val NAVIGATION = "Navigation :"
    const val ROOM = "Room :"
    const val COROUTINES = "Coroutines :"
    const val ZERO_TIME = "00:00:00"

    //Blogger
    const val BLOGGER_API = "AIzaSyDAq5n9ShBngyuSoWrFBnuena94qPm2Gk0" // API your blogger
    const val BLOG_ID = "5758825298436553050" // ID for your blogger
    const val MAX_POST_RESULTS = "10" // Max post display

    //Web App
    var myFacebook = "https://www.facebook.com" // FB here
    var myTwitter = "https://www.twitter.com" // Twitter here
    var myInstagram = "https://www.instagram.com" // Instagram here
    var mySite = "https://www.google.com" // WebSite here
    var myMobileNumber = "+963994683386" // Mobile Number here
    var myEmail = "selimdawa@gmail.com" // Email here
    var aboutUs = "About Here" // About here
    var WEB_NAME = "webName"
    var WEBSITE = "website"
    var INSTAGRAM = "instagram"
    var FACEBOOK = "facebook"
    var TWITTER = "twitter"

    //API & Url
    var API_RANDOM_IMAGE = "https://api.thecatapi.com/v1/images/search"
    var NEWS_API = "07f40de92d3644908496e8f9677ee838"
    var JOKE_URL = "https://v2.jokeapi.dev/joke/"

    //Calculator
    const val ZERO = "0"
    const val ONE = "1"
    const val TWO = "2"
    const val THREE = "3"
    const val FOUR = "4"
    const val FIVE = "5"
    const val SIX = "6"
    const val SEVEN = "7"
    const val EIGHT = "8"
    const val NINE = "9"
    const val DOT = "."
    const val DIVIDE = "/"
    const val MULTIPLY = "X"
    const val MUL = "*"
    const val MINUS = "-"
    const val PLUS = "+"
    const val CLEAR = "C"
    const val EQUALS = "="

    //Dictionary
    const val DICTIONARY_API_KEY = "a13b6fd3-80c2-44de-a1a4-d40b14184662"
    const val DICTIONARY_BASIC_URL =
        "https://www.dictionaryapi.com/api/v3/references/learners/json/"

    //Crypto
    const val BASE_URL_CRYPTO = "https://pro-api.coinmarketcap.com/"
    const val API_KEY_CRYPTO = "e15a2a51-07b1-4d7c-bbff-ae29b8df3b29"
    const val LIMIT_CRYPTO = "10"
    const val IMAGE_CRYPTO = "https://s2.coinmarketcap.com/static/img/coins/128x128/"

    @Suppress("UNUSED_VARIABLE", "unused")
    const val LATEST_CRYPTO = "v1/cryptocurrency/listings/latest"
    const val INFO_CRYPTO = "v2/cryptocurrency/info"

    //Rick & Morty
    const val BASE_URL_RICK_AND_MORTY = "https://rickandmortyapi.com/api/"

    //Country
    const val COUNTRY_GSON: String =
        "atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json"
    const val BASE_URL_COUNTRY = "https://raw.githubusercontent.com/"

    //Dogs
    const val BASE_URL_DOGS = "https://dog.ceo/api/breed/"

    //Meals
    const val BASE_URL_MEALS = "https://www.themealdb.com/api/json/v1/1/"

    //Movies
    const val BASE_URL_MOVIES = "https://api.themoviedb.org/"
    const val POPULAR_MOVIES =
        "3/movie/popular?api_key=a036dc05c534b0cd90d6e8a8e2bcf871&language=en-US&page=1"
    const val IMAGE_MOVIE = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2"

    //News
    const val BASE_URL_NEWS = "https://newsapi.org/v2/"
    const val API_NEWS = "45df755913c947ea82988b1dad81c6e7"

    //Pokémon
    const val BASE_URL_POKE = "https://pokeapi.co/api/v2/"
    const val RAW_URL_POKE =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"

    //Pop
    const val FILE_POP = "funko_pops.json"

    //Weather
    const val API_KEY_WEATHER = "aadc41a523b744b483c154258230510"
    const val BASE_URL_WEATHER = "https://api.weatherapi.com/v1/forecast.json?key="

    // Routes
    const val DASHBOARD = "dashboard"
    const val DETAIL_COUNTRY = "detail/{countryUuid}"
    const val COUNTRY_UUID = "countryUuid"
    const val DETAIL_BY_ID = "detail/"

    // Formats
    fun symbolBrackets(s: String) = "($s)"

    // Theme Attributes
    const val COLOR_ERROR = "colorError"
    const val COLOR_ON_BACKGROUND = "colorOnBackground"
    const val MC_TRACK = "mc_track"
    const val MC_TICK = "mc_tick"
    const val MC_BG = "mc_bg"

    // Blogger API
    const val BLOGGER_BASE_URL = "https://www.googleapis.com/blogger/v3/blogs/"
    const val POSTS = "posts"
    const val PAGES = "pages"
    const val SEARCH = "search"
    const val COMMENTS_KEY = "comments"
    const val MAX_RESULTS = "maxResults"
    const val PAGE_TOKEN = "pageToken"
    const val Q = "q"
    const val KEY = "key"

    // JSON Keys & API Params
    const val FORECAST = "forecast"
    const val FORECAST_DAY = "forecastday"
    const val LOCATION = "location"
    const val NAME = "name"
    const val LAT = "lat"
    const val LON = "lon"
    const val DATE = "date"
    const val DAY = "day"
    const val CONDITION = "condition"
    const val TEXT = "text"
    const val MAX_TEMP_C = "maxtemp_c"
    const val MIN_TEMP_C = "mintemp_c"
    const val ICON = "icon"
    const val HOUR = "hour"
    const val CURRENT = "current"
    const val LAST_UPDATED = "last_updated"
    const val TEMP_C = "temp_c"
    const val TIME = "time"

    const val NEXT_PAGE_TOKEN = "nextPageToken"
    const val ITEMS = "items"
    const val LABELS = "labels"
    const val AUTHOR = "author"
    const val DISPLAY_NAME = "displayName"
    const val IMAGE = "image"
    const val URL = "url"
    const val ID = "id"
    const val PUBLISHED = "published"
    const val CONTENT = "content"
    const val SELF_LINK = "selfLink"
    const val TITLE = "title"
    const val UPDATED = "updated"

    const val IMAGE_NAME = "imageName"
    const val SERIES = "series"

    const val ERROR = "error"
    const val MESSAGE = "message"
    const val JOKES = "jokes"
    const val TYPE = "type"
    const val SINGLE = "single"
    const val JOKE_KEY = "joke"
    const val SETUP = "setup"
    const val DELIVERY = "delivery"
    const val CATEGORY = "category"
}