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

    private val _status = MutableLiveData<ApiStatus>(ApiStatus.LOADING)
    val status: LiveData<ApiStatus> = _status

    init {
        getPokemon()
    }

    private fun getPokemon() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                repository.getPokemonFromApi()
                _status.value = ApiStatus.DONE
            } catch (_: Exception) {
                _status.value = ApiStatus.ERROR
            }
        }
    }
}