package com.flatcode.simplecomposeapps.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.movies.db.MoviesRepository
import com.flatcode.simplecomposeapps.movies.model.MovieItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieFavoriteViewModel @Inject constructor(
    repository: MoviesRepository
) : ViewModel() {

    init {
        Timber.d("Initializing MovieFavoriteViewModel")
    }

    val allMovies: StateFlow<List<MovieItemModel>> = repository.allMovies
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}