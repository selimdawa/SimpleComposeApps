package com.flatcode.simplecomposeapps.pokemon.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PokeEntity::class, PokeDetailEntity::class], version = 1, exportSchema = true)
abstract class PokeDatabase : RoomDatabase() {
    abstract fun pokeDao(): PokeDao
}