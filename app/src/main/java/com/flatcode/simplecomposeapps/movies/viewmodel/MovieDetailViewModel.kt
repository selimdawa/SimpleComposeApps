package com.flatcode.simplecomposeapps.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.movies.db.MoviesRepository
import com.flatcode.simplecomposeapps.movies.model.MovieItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun checkFavoriteStatus(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val status = repository.isMovieFavorite(movieId)
            _isFavorite.value = status
        }
    }

    fun toggleFavorite(movie: MovieItemModel) {
        val currentStatus = _isFavorite.value
        Timber.d("Toggling movie favorite status to: %b", !currentStatus)
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMovie(movie.copy(isFavorite = !currentStatus))
            _isFavorite.value = !currentStatus
        }
    }
}