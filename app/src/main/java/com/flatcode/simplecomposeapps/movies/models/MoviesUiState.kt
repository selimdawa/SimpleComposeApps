package com.flatcode.simplecomposeapps.movies.models

sealed class MoviesUiState {
    data object Loading : MoviesUiState()
    data class Success(val movies: List<MovieItemModel>) : MoviesUiState()
    data class Error(val message: String) : MoviesUiState()
}