package com.flatcode.simplecomposeapps.pokemon.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.flatcode.simplecomposeapps.pokemon.data.database.dao.PokeDao
import com.flatcode.simplecomposeapps.pokemon.data.database.entities.PokeDetailEntity
import com.flatcode.simplecomposeapps.pokemon.data.database.entities.PokeEntity

@Database(entities = [PokeEntity::class, PokeDetailEntity::class], version = 1, exportSchema = false)
abstract class PokeDatabase : RoomDatabase() {
    abstract fun pokeDao(): PokeDao
}