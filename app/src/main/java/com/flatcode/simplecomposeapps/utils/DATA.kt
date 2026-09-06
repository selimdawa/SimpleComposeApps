package com.flatcode.simplecomposeapps.utils

import androidx.compose.ui.graphics.vector.ImageVector
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.Strings

@Suppress("SpellCheckingInspection")
object DATA {

    data class NavItem(
        val route: String,
        val label: String,
        val icon: ImageVector
    )

    val NEWS_NAV = listOf(
        NavItem(Strings.EVERYTHING, Strings.EVERYTHING, AppIcons.MultiDelete),
        NavItem(Strings.TOP_ARTICLES, Strings.TOP_ARTICLES, AppIcons.News)
    )

    val MEALS_NAV = listOf(
        NavItem(Strings.HOME, Strings.HOME, AppIcons.Home),
        NavItem(Strings.FAVORITES, Strings.FAVORITES, AppIcons.Favorite),
        NavItem(Strings.CATEGORIES, Strings.CATEGORIES, AppIcons.Category)
    )

    val RICK_NAV = listOf(
        NavItem(Strings.CHARACTER, Strings.CHARACTER, AppIcons.RickAndMorty),
        NavItem(Strings.LOCATION, Strings.LOCATION, AppIcons.Location),
        NavItem(Strings.EPISODE, Strings.EPISODE, AppIcons.EventNote)
    )

    val TODO_NAV = listOf(
        NavItem(Strings.TASKS, Strings.TASKS, AppIcons.TodoCheck),
        NavItem(Strings.NOTES, Strings.NOTES, AppIcons.TodoNote)
    )

    @Suppress("SpellCheckingInspection")
    val BREEDS_LIST = listOf(
        "Affenpinscher", "African", "Airedale", "Akita", "Appenzeller", "Australian Shepherd",
        "Basenji", "Beagle", "Bluetick", "Borzoi", "Bouvier", "Boxer", "Brabancon", "Briard",
        "Buhund Norwegian", "Bulldog Boston", "Bulldog English", "Bulldog French",
        "Bullterrier Staffordshire", "Cattledog Australian", "Chihuahua", "Chow", "Clumber",
        "Cockapoo", "Collie Border", "Coonhound", "Corgi Cardigan", "Cotondetulear", "Dachshund",
        "Dalmatian", "Dane Great", "Deerhound Scottish", "Dhole", "Dingo", "Doberman",
        "Elkhound Norwegian", "Entlebucher", "Eskimo", "Finnish Lapphund", "Frise Bichon",
        "Germanshepherd", "Golden", "Greyhound Italian", "Groenendael", "Havanese", "Hound Afghan",
        "Hound Basset", "Hound Blood", "Hound English", "Hound Ibizan", "Hound Plott",
        "Hound Walker", "Husky", "Keeshond", "Kelpie", "Komondor", "Kuvasz", "Labradoodle",
        "Labrador", "Leonberg", "Lhasa", "Malamute", "Malinois", "Maltese", "Mastiff Bull",
        "Mastiff English", "Mastiff Tibetan", "Mexicanhairless", "Mix", "Mountain Bernese",
        "Mountain Swiss", "Newfoundland", "Otterhound", "Ovcharka Caucasian", "Papillon",
        "Pekinese", "Pembroke", "Pinscher Miniature", "Pitbull", "Pointer German",
        "Pointer Germanlonghair", "Pomeranian", "Poodle Medium", "Poodle Miniature",
        "Poodle Standard", "Poodle Toy", "Pug", "Puggle", "Pyrenees", "Redbone",
        "Retriever Chesapeake", "Retriever Curly", "Retriever Flatcoated", "Retriever Golden",
        "Ridgeback Rhodesian", "Rottweiler", "Saluki", "Samoyed", "Schipperke", "Schnauzer Giant",
        "Schnauzer Miniature", "Segugio Italian", "Setter English", "Setter Gordon",
        "Setter Irish", "Sharpei", "Sheepdog English", "Sheepdog Shetland", "Shiba", "Shihtzu",
        "Spaniel Blenheim", "Spaniel Brittany", "Spaniel Cocker", "Spaniel Irish",
        "Spaniel Japanese", "Spaniel Sussex", "Spaniel Welsh", "Springer English", "Stbernard",
        "Terrier America", "Terrier Australia", "Terrier Bedlington", "Terrier Borde",
        "Terrier Cairn", "Terrier Dandi", "Terrier Fox", "Terrier Irish", "Terrier Kerryblue",
        "Terrier Lakeland", "Terrier Norfolk", "Terrier Norwich", "Terrier Patterdale",
        "Terrier Russell", "Terrier Scottish", "Terrier Sealyham", "Terrier Silky",
        "Terrier Tibetan", "Terrier Toy", "Terrier Welsh", "Terrier Westhighland",
        "Terrier Wheaten", "Terrier Yorkshire", "Tervuren", "Vizsla", "Waterdog Spanis",
        "Weimaraner", "Whippet", "Wolfhound Irish"
    )

    val MULTI_DELETE_VALUES = listOf(
        "One", "Two", "Three", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
        "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen"
    )

    //Database
    const val EMPTY = ""
    const val DATA = "data"
    const val UNKNOWN = "Unknown"

    //Shared
    //Main
    const val STOP_WATCH = "Stop Watch"
    const val CANDY_CRUSH = "Candy Crush Game"
    const val MULTI_DELETE = "Multiple Delete"
    const val RANDOM_IMAGE = "Random Image Generating"
    const val BLOGGER = "Blogger"
    const val JOKE = "Joke"
    const val NEWS = "News"
    const val PDF_READER = "Pdf Reader"
    const val VIDEO_PLAYER = "Video Player"
    const val WEB_APP = "Web"
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

    //Random Img Generating
    const val KEY_NAME = "name"
    const val KEY_ORIGIN = "origin"
    const val KEY_DESC = "desc"
    const val KEY_TEMP = "temp"
    const val KEY_WIKI_URL = "wikiUrl"
    const val KEY_MORE_LINK = "moreLink"
    const val KEY_IMAGE_URL = "imageUrl"
    const val JSON_URL = "url"
    const val JSON_BREEDS = "breeds"
    const val JSON_NAME = "name"
    const val JSON_ORIGIN = "origin"
    const val JSON_DESCRIPTION = "description"
    const val JSON_TEMPERAMENT = "temperament"
    const val JSON_WIKIPEDIA_URL = "wikipedia_url"
    const val JSON_VCA_HOSPITALS_URL = "vcahospitals_url"

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

    // Theme Attributes
    const val COLOR_ERROR = "colorError"
    const val COLOR_ON_BACKGROUND = "colorOnBackground"
    const val MC_TRACK = "mc_track"
    const val MC_TICK = "mc_tick"
    const val MC_BG = "mc_bg"
}