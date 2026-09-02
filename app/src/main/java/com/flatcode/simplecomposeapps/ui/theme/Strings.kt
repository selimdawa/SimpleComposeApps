package com.flatcode.simplecomposeapps.ui.theme

object Strings {
    const val APP_NAME = "Simple Compose Apps"

    // Dogs
    const val NONE_DISPLAY = "There are no items to display"
    const val HINT_TEXT_BREEDS = "Breeds list"
    const val DOGS = "photo dogs"

    // Calculator
    const val ONE = "1"
    const val TWO = "2"
    const val THREE = "3"
    const val FOUR = "4"
    const val FIVE = "5"
    const val SIX = "6"
    const val SEVEN = "7"
    const val EIGHT = "8"
    const val NINE = "9"
    const val ZERO = "0"
    const val CLEAR = "C"
    const val DOT = "."
    const val MULTIPLY = "X"
    const val DIVIDE = "/"
    const val EQUALS = "="

    // Crypto
    const val ERROR_MESSAGE = "An unknown error occurred."

    // Dictionary
    const val HINT_SEARCH = "Enter your word"
    const val BUTTON_FIND = "find"

    // Pokémon
    const val POKEMON_IMAGE = "Pokémon Image"
    const val TYPE = "Type :"
    const val HP = "Hp :"
    const val SPEED = "Speed :"
    const val ATTACK = "Attack :"
    const val DEFENSE = "Defense :"
    const val SPECIAL_ATTACK = "Special Attack :"
    const val SPECIAL_DEFENSE = "Special Defense :"
    const val HEIGHT = "Height :"
    const val WEIGHT = "Weight :"
    fun metro(s: String) = " $s m"
    fun kilo(s: String) = " $s kg"

    // Movies
    const val MOVIES = "Movies"
    const val FAVORITE_MOVIES = "Favorite movies"
    const val DETAILS_MOVIE = "Details Movie"
    const val NO_FAVORITES_YET = "No favorites yet"

    // Pop
    const val SEARCH_ET = "Search by name or series"
    const val NO_INTERNET_CONNECTION = "NO INTERNET CONNECTION"

    // Main Info
    const val NAME = "Name :"
    const val DAGGER_HILT = "Dagger-Hilt :"
    const val NAVIGATION = "Navigation :"
    const val ROOM = "Room :"
    const val COROUTINES = "Coroutines :"
    const val APP_FEATURES_MVVM = "App Features - MVVM"
    const val ERROR = "Error!"

    // Country Details
    const val COUNTRY_LANGUAGE = "Country Language :"
    const val COUNTRY_NAME = "Country Name :"
    const val COUNTRY_CAPITAL = "Country Capital :"
    const val COUNTRY_REGION = "Country Region :"
    const val COUNTRY_CURRENCY = "Country Currency :"
    const val CHARACTER = "Character"
    const val LOCATION = "Location"
    const val EPISODE = "Episode"
    const val LAST_KNOW_LOCATION = "Last know location :"
    const val GENDER = "Gender :"

    // Meals
    const val WHAT_WOULD_YOU_LIKE_TO_EAT = "What would you like to eat?"
    const val OVER_POPULAR_ITEMS = "Over popular items"
    const val CATEGORIES = "Categories"
    const val INSTRUCTIONS = "Instructions :"
    fun categoryPlaceholder(s: String) = "Category: $s"
    fun areaPlaceholder(s: String) = "Area: $s"
    const val MEAL_REMOVED = "Meal removed from favorites"
    const val MEAL_SAVED = "Meal is saved"
    const val MEAL_DELETED = "Meal deleted"
    const val UNDO = "Undo"
    fun categoryMealsCount(s: String, count: Int) = "$s ( $count )"

    // News
    const val EVERYTHING = "Everything"
    const val TOP_ARTICLES = "Top Articles"

    // Weather
    const val DATE_WEATHER = "23/07/2022"
    const val CITY_WEATHER = "Chicago"
    const val C_WEATHER = "23°C"
    const val C_C_WEATHER = "34°C/23°C"
    const val STATUS_WEATHER = "Sunny"
    const val HOURS = "Hours"
    const val DAYS = "Days"
    const val DAY_WEATHER = "Monday 23/07/2022"
    const val SEARCH = "Search"
    const val SYNC = "Sync"
    const val WEATHER_ICON = "Weather Icon"
    const val CITY_NAME_HINT = "City name:"
    const val SEARCH_HINT = "Search..."
    const val CANCEL = "Cancel"
    const val OK = "OK"

    // TODO - Dialog
    const val DIALOG_DELETE_TITLE = "Confirm Deletion"
    const val DIALOG_DELETE_MESSAGE_TASKS = "Are you sure you want to delete all completed tasks?"
    const val DIALOG_DELETE_MESSAGE_NOTES = "Are you sure you want to delete all notes?"
    const val DIALOG_BTN_NO = "No"
    const val DIALOG_BTN_YES = "Yes"

    // TODO - Tasks
    const val MSG_TASK_ADDED = "Task Added"
    const val MSG_TASK_UPDATED = "Task updated"
    const val MSG_ALL_COMPLETED_TASKS_DELETED = "All completed tasks deleted."
    const val MSG_TASK_DELETED = "Task deleted"
    const val ACTION_UNDO = "UNDO"
    const val TITLE_NEW_TASK = "New Task"
    const val TITLE_EDIT_TASK = "Edit Task"

    // TODO - Notes
    const val MSG_NOTE_ADDED = "Note added."
    const val MSG_NOTE_UPDATED = "Note updated."
    const val MSG_ALL_NOTES_DELETED = "All notes deleted."
    const val TITLE = "Title"
    const val CONTENT = "Content"
    const val IMPORTANT_TASK = "Important Task"
    const val TASK = "Task"
    const val EMPTY = "Empty"
    const val _0 = "0"
    const val RANDOM_IMAGE_GENERATING = "Random Image Generating"
    const val IMAGE_INFO = "Image Info"
    const val TEMPERAMENT = "Temperament"
    const val WIKIPEDIA = "Wikipedia"
    const val MORE_INFO = "More Info"

    fun numberPlaceholder(n: Int) = n.toString()
    fun selectedPlaceholder(count: Int) = "$count Selected"
    fun youClickedPlaceholder(text: String) = "You clicked $text"

    // Stop Watch
    const val STOP_WATCH = "Stop Watch"
    const val ZERO_TIME = "00:00:00"
    const val LAST_TIME_LABEL = "Last Time : "

    // Candy Crush
    const val CANDY_CRUSH_GAME = "Candy Crush Game"
    const val SCORE_LABEL = "Score - "
    const val ZERO_SCORE = "0000"

    // Multi Delete
    const val DELETE = "Delete"
    const val SELECT_ALL = "Select All"

    // Blogger
    const val POST_DETAILS = "Post Details"
    const val BLOGGER_PAGES = "Tip Top Pages"
    const val PAGE_DETAILS = "Page Details"
    const val BLOGGER_NAME = "Tip Top Blogger"
    const val LOAD_MORE = "Load More"
    const val LABELS = "Labels"
    const val COMMENTS = "Comments"
    fun publishInfo(name: String, date: String) = "By $name $date"

    // News Multi
    const val NEWS_APP = "News App"
    const val SHARE = "Share"

    // Pdf Reader
    const val PICK_FILE = "Pick File"
    const val TOAST_PICK_FILE_ERROR = "Unable to pick file. Check status of file manager."
    const val TOAST_HTTP_CODE_ERROR = "Remote server responded with an error."
    const val TOAST_GENERIC_DOWNLOAD_ERROR = "We couldn't download this file for some reason."
    const val TOAST_SSL_ERROR = "Secure connection failed."
    const val SAVED_TO_DOWNLOAD = "File saved to Download folder."
    const val SAVE_TO_DOWNLOAD_FAILED = "Couldn't save file to Download folder."
    const val INTRO = "Replay Intro"
    const val TITLE_PERMISSION = "Storage Permissions"
    const val DESCRIPTION_PERMISSION = "You will now be prompted to allow the app to access the phone's storage. Without this permission, documents might fail to open in some cases."
    const val SHARE_FILE = "Share File"
    const val PRINT = "Print File"
    const val FULL_SCREEN = "Full Screen"
    const val META = "File Info"
    fun pdfTitle(s: String) = "Title: $s"
    fun pdfAuthor(s: String) = "Author: $s"
    fun pdfCreationDate(s: String) = "Creation Date: $s"
    const val FILE_OPENING_ERROR = "An error occurred while opening the file."
    const val PROTECTED_PDF = "Protected PDF"
    const val ENTER_PASSWORD = "Enter the correct password to open the document:"
    const val WRONG_PASSWORD = "Wrong password."

    // WordPress
    const val WORDPRESS_APP = "Wordpress App"
    const val FAVORITES = "Favorites"
    const val ADD_AS_FAVORITE = "add"

    // Web App
    const val WEB_APP = "Web App"
    const val SUPPORT = "Support"
    const val ABOUT_US = "About Us"
    const val WEB_SITE = "WebSite"
    const val RATE_APP = "Rate App"
    const val SHARE_APP = "Share App"

    // Video Player
    const val VIDEO_PLAYER = "Video Player"
    const val FOLDERS = "Folders"
    const val FILES = "Files"
    const val PERMISSION_DENIED = "Permission denied"

    // Joke App
    const val JOKE = "Joke"
    const val PROGRAMMING = "Programming"
    const val FIRST_LINE = "First Line\""
    const val SECOND_LINE = "Second Line\""

    // Other
    const val NO_DATA_FOUND = "No Data Found"
    const val UNKNOWN_ERROR = "An unknown error occurred"
    const val CONNECT_INTERNET = "Can't connect to the Internet"

    // Live TV (Legacy Support)
    const val NEWS_CHANNEL = "News Channel"
    const val MORE = "More"
    const val SPORTS_CHANNEL = "Sports Channel"
    const val ENTERTAINMENT_CHANNEL = "Entertainment Channel"
    const val CHANNEL_INFO = "Channel Info"
    const val CHANNEL_DESCRIPTION = "Channel Description"
    const val CHANNEL_NAME = "Channel Name"
    const val SHARE_LINK = "Share link!"

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
        "One", "Two", "Three", "Five", "Sex", "Seven", "Eight", "Nine", "Ten",
        "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen"
    )
}
