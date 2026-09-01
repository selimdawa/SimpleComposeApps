package com.flatcode.simplecomposeapps.rickAndMorty

import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Character
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
class RickCharactersViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    private val _characters = MutableStateFlow<Resource<RickAndMortyResponse<Character>>>(Resource.Loading())
    val characters: StateFlow<Resource<RickAndMortyResponse<Character>>> = _characters.asStateFlow()

    fun getCharacters(page: Int? = null) {
        viewModelScope.launch {
            repository.getCharacters(page).collect {
                _characters.value = it
            }
        }
    }
}