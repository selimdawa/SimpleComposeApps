package com.flatcode.simplecomposeapps.joke.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface JokeDao {
    @Query("SELECT * FROM jokes WHERE requestedCategory = :category")
    suspend fun getJokesByCategory(category: String): List<JokeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJokes(jokes: List<JokeEntity>)

    @Query("DELETE FROM jokes WHERE requestedCategory = :category")
    suspend fun deleteJokesByCategory(category: String)
}