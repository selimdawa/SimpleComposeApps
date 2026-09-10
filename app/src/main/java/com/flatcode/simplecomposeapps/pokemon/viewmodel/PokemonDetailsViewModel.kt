package com.flatcode.simplecomposeapps.pokemon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.pokemon.network.PokeRepository
import com.flatcode.simplecomposeapps.pokemon.model.PokeItemDetails
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val repository: PokeRepository
) : ViewModel() {

    private val _details = MutableLiveData<Resource<PokeItemDetails>>(Resource.Idle)
    val details: LiveData<Resource<PokeItemDetails>> = _details

    fun getPokemonDetails(id: Int) {
        if (_details.value?.data?.id == id) return

        viewModelScope.launch {
            _details.value = Resource.Loading()
            _details.value = repository.getPokemonDetails(id)
        }
    }
}