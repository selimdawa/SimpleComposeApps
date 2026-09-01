package com.flatcode.simplecomposeapps.pokemon.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flatcode.simplecomposeapps.pokemon.data.database.entities.PokeDetailEntity
import com.flatcode.simplecomposeapps.pokemon.data.database.entities.PokeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemon: List<PokeEntity>)

    @Query("SELECT * FROM pokemon_table")
    fun getAllPokemon(): Flow<List<PokeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetails(details: PokeDetailEntity)

    @Query("SELECT * FROM pokemon_details WHERE id = :id")
    suspend fun getPokemonDetails(id: Int): PokeDetailEntity?
}