package com.flatcode.simplecomposeapps.wordpress.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PostEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ],
    exportSchema = true
)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}