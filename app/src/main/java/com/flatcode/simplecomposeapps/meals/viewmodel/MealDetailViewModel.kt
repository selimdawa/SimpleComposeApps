package com.flatcode.simplecomposeapps.meals.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.meals.model.Meal
import com.flatcode.simplecomposeapps.meals.data.repository.MealRepository
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    private val _mealDetails = MutableLiveData<Resource<Meal>>()
    val mealDetails: LiveData<Resource<Meal>> = _mealDetails

    fun getMealDetail(id: String) {
        viewModelScope.launch {
            _mealDetails.value = Resource.Loading()
            _mealDetails.value = repository.getMealDetails(id)
        }
    }

    fun isMealFavorite(id: String): LiveData<Meal?> {
        return repository.getMealById(id)
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            repository.upsertMeal(meal)
        }
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            repository.deleteMeal(meal)
        }
    }
}
