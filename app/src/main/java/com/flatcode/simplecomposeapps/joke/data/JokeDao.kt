package com.flatcode.simplecomposeapps.joke.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {
    @Query("SELECT * FROM jokes WHERE isCache = 0 ORDER BY id DESC")
    fun getAllJokes(): Flow<List<JokeEntity>>

    @Query("SELECT * FROM jokes WHERE cachedCategory = :category AND isCache = 1")
    suspend fun getCacheByCategory(category: String): List<JokeEntity>

    @Query("DELETE FROM jokes WHERE cachedCategory = :category AND isCache = 1")
    suspend fun deleteCacheByCategory(category: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJokes(jokes: List<JokeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(joke: JokeEntity)

    @Delete
    suspend fun deleteJoke(joke: JokeEntity)
}