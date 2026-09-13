package com.flatcode.simplecomposeapps.pokemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.pokemon.network.PokeRepository
import com.flatcode.simplecomposeapps.pokemon.model.PokeItemDetails
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val repository: PokeRepository
) : ViewModel() {

    private val _details = MutableStateFlow<Resource<PokeItemDetails>>(Resource.Idle)
    val details: StateFlow<Resource<PokeItemDetails>> = _details.asStateFlow()

    fun getPokemonDetails(id: Int) {
        if (_details.value.data?.id == id) return

        viewModelScope.launch {
            Timber.d("Fetching pokemon details for id: %d", id)
            _details.value = Resource.Loading()
            val result = repository.getPokemonDetails(id)
            _details.value = result
            if (result is Resource.Error) {
                Timber.e("Error fetching pokemon details: %s", result.message)
            }
        }
    }
}