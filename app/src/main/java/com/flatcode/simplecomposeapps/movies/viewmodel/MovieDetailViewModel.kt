package com.flatcode.simplecomposeapps.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.movies.db.MoviesRepository
import com.flatcode.simplecomposeapps.movies.model.MovieItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private val _isFavorite = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun checkFavoriteStatus(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isFavorite.postValue(repository.isMovieFavorite(movieId))
        }
    }

    fun toggleFavorite(movie: MovieItemModel) {
        val currentStatus = _isFavorite.value ?: false
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMovie(movie.copy(isFavorite = !currentStatus))
            _isFavorite.postValue(!currentStatus)
        }
    }
}