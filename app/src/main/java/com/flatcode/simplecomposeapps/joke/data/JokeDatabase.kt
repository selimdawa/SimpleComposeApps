package com.flatcode.simplecomposeapps.joke.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [JokeEntity::class], version = 1, exportSchema = false)
abstract class JokeDatabase : RoomDatabase() {
    abstract fun jokeDao(): JokeDao
}