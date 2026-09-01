package com.flatcode.simplecomposeapps.movies

import android.content.Context
import android.content.SharedPreferences

class SaveShared(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("movies_prefs", Context.MODE_PRIVATE)

    fun setFavorite(id: Int, isFavorite: Boolean) {
        sharedPreferences.edit().putBoolean(id.toString(), isFavorite).apply()
    }

    fun getFavorite(id: Int): Boolean {
        return sharedPreferences.getBoolean(id.toString(), false)
    }
}