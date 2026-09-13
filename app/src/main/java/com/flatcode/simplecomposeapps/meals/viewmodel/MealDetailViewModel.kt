package com.flatcode.simplecomposeapps.meals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.meals.model.Meal
import com.flatcode.simplecomposeapps.meals.repository.MealRepository
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
class MealDetailViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    private val _mealDetails = MutableStateFlow<Resource<Meal>>(Resource.Idle)
    val mealDetails: StateFlow<Resource<Meal>> = _mealDetails.asStateFlow()

    fun getMealDetail(id: String) {
        Timber.d("Fetching meal detail for id: %s", id)
        viewModelScope.launch {
            _mealDetails.value = Resource.Loading()
            val result = repository.getMealDetails(id)
            _mealDetails.value = result
            if (result is Resource.Error) {
                Timber.e("Error fetching meal detail: %s", result.message)
            }
        }
    }

    fun isMealFavorite(id: String): StateFlow<Meal?> {
        return repository.getMealById(id)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )
    }

    fun insertMeal(meal: Meal) {
        Timber.d("Inserting meal to favorites: %s", meal.idMeal)
        viewModelScope.launch {
            repository.upsertMeal(meal.copy(isFavorite = true))
        }
    }

    fun deleteMeal(meal: Meal) {
        Timber.d("Removing meal from favorites: %s", meal.idMeal)
        viewModelScope.launch {
            repository.upsertMeal(meal.copy(isFavorite = false))
        }
    }
}
