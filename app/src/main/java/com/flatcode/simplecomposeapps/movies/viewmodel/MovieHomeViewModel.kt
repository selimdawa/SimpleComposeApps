package com.flatcode.simplecomposeapps.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.movies.network.MovieRepository
import com.flatcode.simplecomposeapps.movies.model.MovieItemModel
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieHomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<List<MovieItemModel>>>(Resource.Idle)
    val uiState: StateFlow<Resource<List<MovieItemModel>>> = _uiState.asStateFlow()

    init {
        getMovies()
    }

    fun getMovies() {
        Timber.d("Fetching movie list from repository")
        viewModelScope.launch {
            _uiState.value = Resource.Loading()
            val result = repository.getMovies()
            _uiState.value = result
            if (result is Resource.Error) {
                Timber.e("Error fetching movies: %s", result.message)
            }
        }
    }
}