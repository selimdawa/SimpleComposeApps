package com.flatcode.simplecomposeapps.ui.theme

object Strings {
    const val APP_NAME = "Simple Compose Apps"
    const val NONE_DISPLAY = "There are no items to display"
    const val NAME = "Name :"
    const val DAGGER_HILT = "Dagger-Hilt :"
    const val NAVIGATION = "Navigation :"
    const val ROOM = "Room :"
    const val COROUTINES = "Coroutines :"
    const val APP_FEATURES_MVVM = "App Features - MVVM"

    const val RANDOM_IMAGE_GENERATING = "Random Image Generating"
    const val IMAGE_INFO = "Image Info"
    const val TEMPERAMENT = "Temperament"
    const val WIKIPEDIA = "Wikipedia"
    const val MORE_INFO = "More Info"
    const val STOP_WATCH = "Stop Watch"
    const val LAST_TIME_LABEL = "Last Time : "

    // Blogger
    const val BLOGGER_NAME = "Tip Top Blogger"
    const val BLOGGER_PAGES = "Tip Top Pages"
    const val POST_DETAILS = "Post Details"
    const val PAGE_DETAILS = "Page Details"
    const val LOAD_MORE = "Load More"
    const val COMMENTS = "Comments"
    const val LABELS = "Labels"
    const val NO_MORE_POSTS = "No more posts"
    const val REACHED_END_OF_PAGE = "Reached end of page"
    fun publishInfo(name: String, date: String) = "By $name $date"

    fun numberPlaceholder(number: Int) = number.toString()
    fun selectedPlaceholder(count: Int) = "Selected $count"
    fun youClickedPlaceholder(text: String) = "You clicked $text"
}
