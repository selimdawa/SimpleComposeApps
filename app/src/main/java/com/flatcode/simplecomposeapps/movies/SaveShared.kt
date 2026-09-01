package com.flatcode.simplecomposeapps.movies

import android.content.Context
import android.content.SharedPreferences

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveShared @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("movies_prefs", Context.MODE_PRIVATE)

    fun setFavorite(id: Int, isFavorite: Boolean) {
        sharedPreferences.edit().putBoolean(id.toString(), isFavorite).apply()
    }

    fun getFavorite(id: Int): Boolean {
        return sharedPreferences.getBoolean(id.toString(), false)
    }
}