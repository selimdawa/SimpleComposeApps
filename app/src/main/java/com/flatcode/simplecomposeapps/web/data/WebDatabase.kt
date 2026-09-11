package com.flatcode.simplecomposeapps.web.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WebEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ],
    exportSchema = true
)
abstract class WebDatabase : RoomDatabase() {
    abstract fun webDao(): WebDao
}