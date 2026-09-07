package com.flatcode.simplecomposeapps.multipledelete.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MultiDeleteEntity::class], version = 1, exportSchema = true)
abstract class MultiDeleteDatabase : RoomDatabase() {
    abstract fun multiDeleteDao(): MultiDeleteDao
}