package com.flatcode.simplecomposeapps.stopwatch.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [StopWatchEntity::class],
    version = 2,
    autoMigrations = [AutoMigration(from = 1, to = 2)],
    exportSchema = true
)
abstract class StopWatchDatabase : RoomDatabase() {
    abstract fun stopWatchDao(): StopWatchDao
}
