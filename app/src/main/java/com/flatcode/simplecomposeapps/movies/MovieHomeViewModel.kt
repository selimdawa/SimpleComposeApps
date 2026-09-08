package com.flatcode.simplecomposeapps.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.movies.data.retrofit.RetrofitRepository
import com.flatcode.simplecomposeapps.movies.models.MoviesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieHomeViewModel @Inject constructor(
    private val repository: RetrofitRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<MoviesUiState>()
    val uiState: LiveData<MoviesUiState> = _uiState

    init {
        getMoviesRetrofit()
    }

    private fun getMoviesRetrofit() {
        viewModelScope.launch {
            _uiState.value = MoviesUiState.Loading
            try {
                val moviesModel = repository.getMovies()
                val movies = moviesModel.results
                _uiState.value = MoviesUiState.Success(movies)
            } catch (e: Exception) {
                _uiState.value = MoviesUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
