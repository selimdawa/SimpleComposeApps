package com.flatcode.simplecomposeapps.pokemon.data

import com.flatcode.simplecomposeapps.pokemon.data.database.dao.PokeDao
import com.flatcode.simplecomposeapps.pokemon.data.database.entities.toDomain
import com.flatcode.simplecomposeapps.pokemon.data.model.toDomain
import com.flatcode.simplecomposeapps.pokemon.data.network.ApiService
import com.flatcode.simplecomposeapps.pokemon.domain.model.PokeItem
import com.flatcode.simplecomposeapps.pokemon.domain.model.PokeItemDetails
import com.flatcode.simplecomposeapps.pokemon.domain.model.toDatabase
import com.flatcode.simplecomposeapps.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokeRepository @Inject constructor(
    private val api: ApiService,
    private val pokeDao: PokeDao
) {
    val allPokemon: Flow<List<PokeItem>> = pokeDao.getAllPokemon().map { it.map { it.toDomain() } }

    suspend fun getPokemonFromApi(): Resource<Unit> = withContext(Dispatchers.IO) {
        try {
            val response = api.getPokemon(151, 0)
            val pokemon = response.results.map { it.toDatabase() }
            pokeDao.insertAll(pokemon)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun getPokemonDetails(id: Int): Resource<PokeItemDetails> = withContext(Dispatchers.IO) {
        try {
            val localDetails = pokeDao.getPokemonDetails(id)
            if (localDetails != null) return@withContext Resource.Success(localDetails.toDomain())

            val response = api.getPokemonDetails(id)
            val details = response.toDomain()
            pokeDao.insertDetails(details.toDatabase())
            Resource.Success(details)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}