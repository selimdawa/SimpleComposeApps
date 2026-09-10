package com.flatcode.simplecomposeapps.movies.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flatcode.simplecomposeapps.movies.model.MovieItemModel

@Database(entities = [MovieItemModel::class], version = 1, exportSchema = true)
abstract class MoviesRoomDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}