package com.flatcode.simplecomposeapps.movies.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flatcode.simplecomposeapps.movies.data.room.dao.MoviesDao
import com.flatcode.simplecomposeapps.movies.models.MovieItemModel

@Database(entities = [MovieItemModel::class], version = 1, exportSchema = false)
abstract class MoviesRoomDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}