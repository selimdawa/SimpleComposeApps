package com.flatcode.simplecomposeapps.randomcatsimage.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CatImageEntity::class], version = 1, exportSchema = true)
abstract class CatImageDatabase : RoomDatabase() {
    abstract fun catImageDao(): CatImageDao
}