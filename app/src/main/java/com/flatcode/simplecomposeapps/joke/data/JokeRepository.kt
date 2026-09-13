package com.flatcode.simplecomposeapps.joke.data

import com.flatcode.simplecomposeapps.joke.model.Joke
import com.flatcode.simplecomposeapps.joke.network.JokeApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JokeRepository @Inject constructor(
    private val api: JokeApi,
    private val jokeDao: JokeDao
) {

    suspend fun getJokes(category: String): List<Joke> {
        return try {
            val response = api.getJokes(category)
            if (response.error) {
                loadFromDb(category)
            } else {
                val jokeList = response.jokes ?: emptyList()
                if (jokeList.isNotEmpty()) {
                    jokeDao.deleteJokesByCategory(category)
                    jokeDao.insertJokes(jokeList.map { it.toEntity(category) })
                    jokeList
                } else {
                    loadFromDb(category)
                }
            }
        } catch (_: Exception) {
            loadFromDb(category)
        }
    }

    private suspend fun loadFromDb(category: String): List<Joke> {
        val cachedJokes = jokeDao.getJokesByCategory(category)
        return cachedJokes.map { it.toDomain() }
    }

    private fun Joke.toEntity(requestedCategory: String): JokeEntity {
        return JokeEntity(
            requestedCategory = requestedCategory,
            category = category,
            type = type,
            joke = joke,
            setup = setup,
            delivery = delivery
        )
    }

    private fun JokeEntity.toDomain(): Joke {
        return Joke(
            category = category,
            type = type,
            joke = joke,
            setup = setup,
            delivery = delivery
        )
    }
}