package com.flatcode.simplecomposeapps.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.flatcode.simplecomposeapps.movies.data.room.repository.MoviesRepository
import com.flatcode.simplecomposeapps.movies.models.MovieItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieFavoriteViewModel @Inject constructor(
    repository: MoviesRepository
) : ViewModel() {

    val allMovies: LiveData<List<MovieItemModel>> = repository.allMovies.asLiveData()
}