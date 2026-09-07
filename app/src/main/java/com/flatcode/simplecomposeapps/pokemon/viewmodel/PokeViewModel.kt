package com.flatcode.simplecomposeapps.pokemon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.pokemon.data.PokeRepository
import com.flatcode.simplecomposeapps.pokemon.domain.model.PokeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class ApiStatus { LOADING, ERROR, DONE }

@HiltViewModel
class PokeViewModel @Inject constructor(
    private val repository: PokeRepository
) : ViewModel() {

    val pokemon: LiveData<List<PokeItem>> = repository.allPokemon.asLiveData()

    val status: LiveData<ApiStatus>
        field = MutableLiveData<ApiStatus>(ApiStatus.LOADING)

    init {
        getPokemon()
    }

    private fun getPokemon() {
        viewModelScope.launch {
            status.value = ApiStatus.LOADING
            try {
                repository.getPokemonFromApi()
                status.value = ApiStatus.DONE
            } catch (_: Exception) {
                status.value = ApiStatus.ERROR
            }
        }
    }
}