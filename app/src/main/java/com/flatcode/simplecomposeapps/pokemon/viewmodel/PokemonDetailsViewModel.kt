package com.flatcode.simplecomposeapps.pokemon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.pokemon.data.PokeRepository
import com.flatcode.simplecomposeapps.pokemon.domain.model.PokeItemDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val repository: PokeRepository
) : ViewModel() {

    val details: LiveData<PokeItemDetails?>
        field = MutableLiveData<PokeItemDetails?>()

    val isLoading: LiveData<Boolean>
        field = MutableLiveData<Boolean>(true)

    fun getPokemonDetails(id: Int) {
        viewModelScope.launch {
            isLoading.value = true
            details.value = repository.getPokemonDetails(id)
            isLoading.value = false
        }
    }
}