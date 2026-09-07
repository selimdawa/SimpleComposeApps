package com.flatcode.simplecomposeapps.web.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WebEntity::class], version = 1, exportSchema = true)
abstract class WebDatabase : RoomDatabase() {
    abstract fun webDao(): WebDao
}