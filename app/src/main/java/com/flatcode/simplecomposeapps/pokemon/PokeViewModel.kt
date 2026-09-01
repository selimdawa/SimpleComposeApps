package com.flatcode.simplecomposeapps.pokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.pokemon.data.PokeRepository
import com.flatcode.simplecomposeapps.pokemon.domain.model.PokeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeViewModel @Inject constructor(
    private val repository: PokeRepository
) : ViewModel() {

    val pokemon: LiveData<List<PokeItem>> = repository.allPokemon.asLiveData()

    init {
        getPokemon()
    }

    private fun getPokemon() {
        viewModelScope.launch {
            repository.getPokemonFromApi()
        }
    }
}