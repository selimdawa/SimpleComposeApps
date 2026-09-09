package com.flatcode.simplecomposeapps.pokemon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.pokemon.data.PokeRepository
import com.flatcode.simplecomposeapps.pokemon.domain.model.PokeItem
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeViewModel @Inject constructor(
    private val repository: PokeRepository
) : ViewModel() {

    val pokemon: LiveData<List<PokeItem>> = repository.allPokemon.asLiveData()

    private val _status = MutableLiveData<Resource<Unit>>(Resource.Idle)
    val status: LiveData<Resource<Unit>> = _status

    init {
        getPokemon()
    }

    private fun getPokemon() {
        viewModelScope.launch {
            _status.value = Resource.Loading()
            _status.value = repository.getPokemonFromApi()
        }
    }
}