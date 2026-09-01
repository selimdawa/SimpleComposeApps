package com.flatcode.simplecomposeapps.pokemon

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

    private val _details = MutableLiveData<PokeItemDetails?>()
    val details: LiveData<PokeItemDetails?> get() = _details

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getPokemonDetails(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _details.value = repository.getPokemonDetails(id)
            _isLoading.value = false
        }
    }
}