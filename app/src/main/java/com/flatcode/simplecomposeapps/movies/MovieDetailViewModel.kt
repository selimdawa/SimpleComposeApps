package com.flatcode.simplecomposeapps.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.movies.data.room.repository.MoviesRepository
import com.flatcode.simplecomposeapps.movies.models.MovieItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val saveShared: SaveShared
) : ViewModel() {

    suspend fun isFavorite(movieId: Int): Boolean {
        return saveShared.getFavorite(movieId)
    }

    fun toggleFavorite(movie: MovieItemModel, isCurrentlyFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isCurrentlyFavorite) {
                repository.deleteMovie(movie)
                saveShared.setFavorite(movie.id, false)
            } else {
                repository.insertMovie(movie)
                saveShared.setFavorite(movie.id, true)
            }
        }
    }
}