package com.flatcode.simplecomposeapps.joke.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [JokeEntity::class],
    version = 4,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4)
    ],
    exportSchema = true
)
abstract class JokeDatabase : RoomDatabase() {
    abstract fun jokeDao(): JokeDao
}