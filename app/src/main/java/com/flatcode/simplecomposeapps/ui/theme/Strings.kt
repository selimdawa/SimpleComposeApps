package com.flatcode.simplecomposeapps.ui.theme

object Strings {
    const val APP_NAME = "Simple Compose Apps"

    // Dogs
    const val NONE_DISPLAY = "There are no items to display"
    const val HINT_TEXT_BREEDS = "Breeds list"
    const val SELECT_BREED = "Please select a breed"

    // Dictionary
    const val HINT_SEARCH = "Enter your word"
    const val BUTTON_FIND = "Find"

    // Pokémon
    const val TYPE = "Type :"
    const val HP = "Hp :"
    const val SPEED = "Speed :"
    const val ATTACK = "Attack :"
    const val DEFENSE = "Defense :"
    const val SPECIAL_ATTACK = "Special Attack :"
    const val SPECIAL_DEFENSE = "Special Defense :"
    const val HEIGHT = "Height :"
    const val WEIGHT = "Weight :"

    // Movies
    const val FAVORITE_MOVIES = "Favorite movies"
    const val DETAILS_MOVIE = "Details Movie"
    const val NO_FAVORITES_YET = "No favorites yet"

    // Pop
    const val SEARCH_ET = "Search by name or series"

    // Main Info
    const val NAME = "Name :"
    const val APP_FEATURES_MVVM = "App Features - MVVM"
    const val ERROR = "Error!"

    // Rick & Morty
    const val CHARACTER = "Character"
    const val LOCATION = "Location"
    const val EPISODE = "Episode"
    const val LAST_KNOW_LOCATION = "Last know location :"
    const val GENDER = "Gender :"

    // Country Details
    const val COUNTRY_NAME = "Country Name :"
    const val COUNTRY_CAPITAL = "Country Capital :"
    const val COUNTRY_REGION = "Country Region :"
    const val COUNTRY_LANGUAGE = "Country Language :"
    const val COUNTRY_CURRENCY = "Country Currency :"
    const val ERROR_LOADING_COUNTRIES = "Error loading countries"

    // Meals
    const val HOME = "Home"
    const val WHAT_WOULD_YOU_LIKE_TO_EAT = "What would you like to eat?"
    const val OVER_POPULAR_ITEMS = "Over popular items"
    const val CATEGORIES = "Categories"
    const val INSTRUCTIONS = "Instructions :"
    fun categoryPlaceholder(s: String) = "Category: $s"
    fun areaPlaceholder(s: String) = "Area: $s"
    const val MEAL_REMOVED = "Meal removed from favorites"
    const val MEAL_SAVED = "Meal is saved"
    const val UNDO = "Undo"
    const val FAILED_LOAD_DATA = "Failed to load data"

    // News
    const val EVERYTHING = "Everything"
    const val TOP_ARTICLES = "Top Articles"

    // Weather
    const val HOURS = "Hours"
    const val DAYS = "Days"
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
    const val TASKS = "Tasks"
    const val MSG_TASK_ADDED = "Task Added."
    const val MSG_TASK_UPDATED = "Task updated."
    const val MSG_TASK_DELETED = "Task deleted."
    const val MSG_COMPLETED_TASKS_DELETED = "Completed tasks deleted."
    const val TITLE_NEW_TASK = "New Task"
    const val TITLE_EDIT_TASK = "Edit Task"
    const val ADD_TASK = "Add Task"
    const val SORT_BY_NAME = "Sort by name"
    const val SORT_BY_DATE = "Sort by date created"
    const val HIDE_COMPLETED = "Hide completed"
    const val DELETE_COMPLETED_TASKS = "Delete completed tasks"

    // TODO - Notes
    const val NOTES = "Notes"
    const val MSG_NOTE_ADDED = "Note added."
    const val MSG_NOTE_UPDATED = "Note updated."
    const val MSG_NOTE_DELETED = "Note deleted."
    const val MSG_ALL_NOTES_DELETED = "All notes deleted."
    const val TITLE_NEW_NOTE = "New Note"
    const val TITLE_EDIT_NOTE = "Edit Note"
    const val ADD_NOTE = "Add Note"
    const val NO_NOTES_FOUND = "No notes found"
    const val NO_TASKS_FOUND = "No tasks found"
    const val TITLE = "Title"
    const val CONTENT = "Content"
    const val IMPORTANT_TASK = "Important Task"
    const val TASK = "Task"
    const val EMPTY = "Empty"
    fun releaseDate(date: String) = "Release Date: $date"

    fun numberPlaceholder(n: Int) = n.toString()
    fun selectedPlaceholder(count: Int) = "$count Selected"
    fun youClickedPlaceholder(text: String) = "You clicked $text"

    // Stop Watch
    const val LAST_TIME_LABEL = "Last Time: "

    // Candy Crush
    const val SCORE_LABEL = "Score: "

    // Multi Delete
    const val DELETE = "Delete"
    const val SELECT_ALL = "Select All"
    const val RESTORE_ITEMS = "Restore Items"

    // Blogger
    const val POST_DETAILS = "Post Details"
    const val BLOGGER_PAGES = "Tip Top Pages"
    const val PAGE_DETAILS = "Page Details"
    const val BLOGGER_NAME = "Tip Top Blogger"
    const val LOAD_MORE = "Load More"
    const val LABELS = "Labels"
    const val COMMENTS = "Comments"
    fun publishInfo(name: String, date: String) = "By $name $date"

    //News

    //PDF Reader
    const val PICK_FILE = "Pick File"
    const val SHARE_FILE = "Share File"
    const val PRINT = "Print File"
    const val FULL_SCREEN = "Full Screen"
    const val META = "File Info"

    // WordPress
    const val FAVORITES = "Favorites"

    // Web App
    const val SUPPORT = "Support"
    const val ABOUT_US = "About Us"
    const val WEB_SITE = "WebSite"
    const val RATE_APP = "Rate App"
    const val SHARE_APP = "Share App"
    const val HISTORY = "History"
    const val BOOKMARKS = "Bookmarks"

    // Video Player
    const val FOLDERS = "Folders"
    const val FILES = "Files"
    const val PERMISSION_DENIED = "Permission denied"

    // Joke App
    const val PROGRAMMING = "Programming"

    // Other
    const val NO_DATA_FOUND = "No Data Found"
    const val UNKNOWN_ERROR = "An unknown error occurred"
    const val CLOSE = "Close"
    const val BACK = "Back"
    const val FAVORITE = "Favorite"
    const val YOUTUBE = "YouTube"

    // Numbers
    const val ONE = "One"
    const val TWO = "Two"
    const val THREE = "Three"
    const val FOUR = "Four"
    const val FIVE = "Five"
    const val SIX = "Six"
    const val SEVEN = "Seven"
    const val EIGHT = "Eight"
    const val NINE = "Nine"
    const val TEN = "Ten"
    const val ELEVEN = "Eleven"
    const val TWELVE = "Twelve"
    const val THIRTEEN = "Thirteen"
    const val FOURTEEN = "Fourteen"
    const val FIFTEEN = "Fifteen"
    const val SIXTEEN = "Sixteen"
    const val SEVENTEEN = "Seventeen"
    const val EIGHTEEN = "Eighteen"
    const val NINETEEN = "Nineteen"
    const val TWENTY = "Twenty"
}