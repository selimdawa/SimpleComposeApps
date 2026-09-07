package com.flatcode.simplecomposeapps.main.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MainEntity::class, MainSettingsEntity::class],
    version = 2,
    autoMigrations = [AutoMigration(from = 1, to = 2)],
    exportSchema = true
)
abstract class MainDatabase : RoomDatabase() {
    abstract fun mainDao(): MainDao
}