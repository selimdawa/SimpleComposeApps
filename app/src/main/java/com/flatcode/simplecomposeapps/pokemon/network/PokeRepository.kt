package com.flatcode.simplecomposeapps.pokemon.network

import com.flatcode.simplecomposeapps.pokemon.db.PokeDao
import com.flatcode.simplecomposeapps.pokemon.db.toDomain
import com.flatcode.simplecomposeapps.pokemon.model.PokeItem
import com.flatcode.simplecomposeapps.pokemon.model.PokeItemDetails
import com.flatcode.simplecomposeapps.pokemon.model.toDatabase
import com.flatcode.simplecomposeapps.pokemon.model.toDomain
import com.flatcode.simplecomposeapps.ui.theme.Strings
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
    val allPokemon: Flow<List<PokeItem>> = pokeDao.getAllPokemon().map { entities ->
        entities.map { entity -> entity.toDomain() }
    }

    suspend fun getPokemonFromApi(): Resource<Unit> = withContext(Dispatchers.IO) {
        try {
            val response = api.getPokemon(151, 0)
            val pokemon = response.results.map { it.toDatabase() }
            pokeDao.insertAll(pokemon)
            Resource.Success(Unit)
        } catch (_: Exception) {
            Resource.Error(Strings.FAILED_LOAD_DATA)
        }
    }

    suspend fun getPokemonDetails(id: Int): Resource<PokeItemDetails> =
        withContext(Dispatchers.IO) {
            try {
                val localDetails = pokeDao.getPokemonDetails(id)
                if (localDetails != null) return@withContext Resource.Success(localDetails.toDomain())

                val response = api.getPokemonDetails(id)
                val details = response.toDomain()
                pokeDao.insertDetails(details.toDatabase())
                Resource.Success(details)
            } catch (_: Exception) {
                Resource.Error(Strings.FAILED_LOAD_DATA)
            }
        }
}