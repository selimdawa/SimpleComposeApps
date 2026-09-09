package com.flatcode.simplecomposeapps.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _isFavorite = MutableLiveData<Boolean>(false)
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun checkFavoriteStatus(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isFavorite.postValue(saveShared.getFavorite(movieId))
        }
    }

    fun toggleFavorite(movie: MovieItemModel) {
        val currentStatus = _isFavorite.value ?: false
        viewModelScope.launch(Dispatchers.IO) {
            if (currentStatus) {
                repository.deleteMovie(movie)
                saveShared.setFavorite(movie.id, false)
            } else {
                repository.insertMovie(movie)
                saveShared.setFavorite(movie.id, true)
            }
            _isFavorite.postValue(!currentStatus)
        }
    }
}