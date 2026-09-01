package com.flatcode.simplecomposeapps.movies.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flatcode.simplecomposeapps.movies.models.MovieItemModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieItemModel)

    @Delete
    suspend fun deleteMovie(movie: MovieItemModel)

    @Query("SELECT * FROM movies_table")
    fun getAllMovies(): Flow<List<MovieItemModel>>
}