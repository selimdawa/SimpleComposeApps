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
    
    fun numberPlaceholder(number: Int) = number.toString()
    fun selectedPlaceholder(count: Int) = "Selected $count"
    fun youClickedPlaceholder(text: String) = "You clicked $text"
}
