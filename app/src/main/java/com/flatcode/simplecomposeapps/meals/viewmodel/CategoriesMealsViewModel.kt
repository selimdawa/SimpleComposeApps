package com.flatcode.simplecomposeapps.meals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.meals.model.MealsByCategory
import com.flatcode.simplecomposeapps.meals.repository.MealRepository
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategoriesMealsViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    private val _meals = MutableStateFlow<Resource<List<MealsByCategory>>>(Resource.Idle)
    val meals: StateFlow<Resource<List<MealsByCategory>>> = _meals.asStateFlow()

    fun getMealsByCategory(categoryName: String) {
        Timber.d("Fetching meals for category: %s", categoryName)
        viewModelScope.launch {
            _meals.value = Resource.Loading()
            val result = repository.getMealsByCategory(categoryName)
            _meals.value = result
            if (result is Resource.Error) {
                Timber.e("Error fetching meals for category %s: %s", categoryName, result.message)
            }
        }
    }
}