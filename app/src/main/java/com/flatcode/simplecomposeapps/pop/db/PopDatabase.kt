package com.flatcode.simplecomposeapps.pop.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flatcode.simplecomposeapps.pop.model.PopItem

@Database(entities = [PopItem::class], version = 2, exportSchema = false)
abstract class PopDatabase : RoomDatabase() {
    abstract fun popDao(): PopDao
}