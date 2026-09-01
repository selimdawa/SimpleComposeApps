package com.flatcode.simplecomposeapps.rickAndMorty

import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Episode
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.RickAndMortyResponse
import com.flatcode.simplecomposeapps.rickAndMorty.data.repositories.MainRepository
import com.flatcode.simplecomposeapps.rickAndMorty.ui.base.BaseViewModel
import com.flatcode.simplecomposeapps.rickAndMorty.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RickEpisodesViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    private val _episodes = MutableStateFlow<Resource<RickAndMortyResponse<Episode>>>(Resource.Loading())
    val episodes: StateFlow<Resource<RickAndMortyResponse<Episode>>> = _episodes.asStateFlow()

    fun getEpisodes(page: Int? = null) {
        viewModelScope.launch {
            repository.getEpisodes(page).collect {
                _episodes.value = it
            }
        }
    }
}