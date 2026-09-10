package com.flatcode.simplecomposeapps.rickAndMorty.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.flatcode.simplecomposeapps.rickAndMorty.model.Character
import com.flatcode.simplecomposeapps.rickAndMorty.model.Episode
import com.flatcode.simplecomposeapps.rickAndMorty.model.Location

@Database(entities = [Character::class, Episode::class, Location::class], version = 1, exportSchema = true)
@TypeConverters(RickConverters::class)
abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract fun rickAndMortyDao(): RickAndMortyDao
}