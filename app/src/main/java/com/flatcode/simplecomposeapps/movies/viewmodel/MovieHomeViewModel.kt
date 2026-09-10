package com.flatcode.simplecomposeapps.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.movies.network.MovieRepository
import com.flatcode.simplecomposeapps.movies.model.MovieItemModel
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieHomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<Resource<List<MovieItemModel>>>(Resource.Idle)
    val uiState: LiveData<Resource<List<MovieItemModel>>> = _uiState

    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            _uiState.value = Resource.Loading()
            _uiState.value = repository.getMovies()
        }
    }
}