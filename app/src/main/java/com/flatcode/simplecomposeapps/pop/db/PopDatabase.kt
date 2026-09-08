package com.flatcode.simplecomposeapps.pop.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.flatcode.simplecomposeapps.pop.model.PopItem

@Database(
    entities = [PopItem::class],
    version = 2,
    autoMigrations = [AutoMigration(from = 1, to = 2)],
    exportSchema = true
)
abstract class PopDatabase : RoomDatabase() {
    abstract fun popDao(): PopDao
}