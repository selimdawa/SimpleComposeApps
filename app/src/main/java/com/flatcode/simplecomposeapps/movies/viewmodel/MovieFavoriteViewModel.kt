package com.flatcode.simplecomposeapps.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.flatcode.simplecomposeapps.movies.db.MoviesRepository
import com.flatcode.simplecomposeapps.movies.model.MovieItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieFavoriteViewModel @Inject constructor(
    repository: MoviesRepository
) : ViewModel() {

    val allMovies: LiveData<List<MovieItemModel>> = repository.allMovies.asLiveData()
}