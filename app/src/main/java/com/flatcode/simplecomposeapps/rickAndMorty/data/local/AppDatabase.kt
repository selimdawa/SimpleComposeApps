package com.flatcode.simplecomposeapps.rickAndMorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Character
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Episode
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Location

@Database(entities = [Character::class, Episode::class, Location::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}