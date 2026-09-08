package com.flatcode.simplecomposeapps.pokemon.data

import com.flatcode.simplecomposeapps.pokemon.data.database.dao.PokeDao
import com.flatcode.simplecomposeapps.pokemon.data.database.entities.toDomain
import com.flatcode.simplecomposeapps.pokemon.data.model.toDomain
import com.flatcode.simplecomposeapps.pokemon.data.network.ApiService
import com.flatcode.simplecomposeapps.pokemon.domain.model.PokeItem
import com.flatcode.simplecomposeapps.pokemon.domain.model.PokeItemDetails
import com.flatcode.simplecomposeapps.pokemon.domain.model.toDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokeRepository @Inject constructor(
    private val api: ApiService,
    private val pokeDao: PokeDao
) {
    val allPokemon: Flow<List<PokeItem>> = pokeDao.getAllPokemon().map { it.map { it.toDomain() } }

    suspend fun getPokemonFromApi() {
        try {
            val response = api.getPokemon(151, 0)
            val pokemon = response.results.map { it.toDatabase() }
            pokeDao.insertAll(pokemon)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getPokemonDetails(id: Int): PokeItemDetails? {
        val localDetails = pokeDao.getPokemonDetails(id)
        if (localDetails != null) return localDetails.toDomain()

        return try {
            val response = api.getPokemonDetails(id)
            val details = response.toDomain()
            pokeDao.insertDetails(details.toDatabase())
            details
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}