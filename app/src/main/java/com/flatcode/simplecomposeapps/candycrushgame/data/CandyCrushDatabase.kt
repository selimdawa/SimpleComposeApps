package com.flatcode.simplecomposeapps.candycrushgame.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CandyCrushEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ],
    exportSchema = true
)
abstract class CandyCrushDatabase : RoomDatabase() {
    abstract fun candyCrushDao(): CandyCrushDao
}