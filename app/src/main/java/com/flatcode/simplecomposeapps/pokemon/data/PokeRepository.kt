package com.flatcode.simplecomposeapps.pokemon.data

import com.flatcode.simplecomposeapps.pokemon.data.database.dao.PokeDao
import com.flatcode.simplecomposeapps.pokemon.data.database.entities.toDomain
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
        val response = api.getPokemon(151, 0)
        if (response.isSuccessful && response.body() != null) {
            val pokemon = response.body()!!.results.map { it.toDatabase() }
            pokeDao.insertAll(pokemon)
        }
    }

    suspend fun getPokemonDetails(id: Int): PokeItemDetails? {
        val localDetails = pokeDao.getPokemonDetails(id)
        if (localDetails != null) return localDetails.toDomain()

        val response = api.getPokemonDetails(id)
        if (response.isSuccessful && response.body() != null) {
            val details = response.body()!!.toDomain()
            pokeDao.insertDetails(details.toDatabase())
            return details
        }
        return null
    }
}