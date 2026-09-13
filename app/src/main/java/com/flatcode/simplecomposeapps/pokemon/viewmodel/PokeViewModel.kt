package com.flatcode.simplecomposeapps.pokemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.pokemon.network.PokeRepository
import com.flatcode.simplecomposeapps.pokemon.model.PokeItem
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokeViewModel @Inject constructor(
    private val repository: PokeRepository
) : ViewModel() {

    val pokemon: StateFlow<List<PokeItem>> = repository.allPokemon
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _status = MutableStateFlow<Resource<Unit>>(Resource.Idle)
    val status: StateFlow<Resource<Unit>> = _status.asStateFlow()

    init {
        getPokemon()
    }

    private fun getPokemon() {
        viewModelScope.launch {
            Timber.d("Fetching pokemon from API")
            _status.value = Resource.Loading()
            _status.value = repository.getPokemonFromApi()
        }
    }
}