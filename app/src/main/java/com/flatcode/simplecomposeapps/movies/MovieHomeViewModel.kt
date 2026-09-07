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

    val uiState: LiveData<MoviesUiState>
        field = MutableLiveData<MoviesUiState>()

    init {
        getMoviesRetrofit()
    }

    private fun getMoviesRetrofit() {
        viewModelScope.launch {
            uiState.value = MoviesUiState.Loading
            try {
                val response = repository.getMovies()
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    uiState.value = MoviesUiState.Success(movies)
                } else {
                    uiState.value = MoviesUiState.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                uiState.value = MoviesUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}