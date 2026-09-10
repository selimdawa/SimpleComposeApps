package com.flatcode.simplecomposeapps.meals.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.meals.model.MealsByCategory
import com.flatcode.simplecomposeapps.meals.repository.MealRepository
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesMealsViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    private val _meals = MutableLiveData<Resource<List<MealsByCategory>>>()
    val meals: LiveData<Resource<List<MealsByCategory>>> = _meals

    fun getMealsByCategory(categoryName: String) {
        viewModelScope.launch {
            _meals.value = Resource.Loading()
            _meals.value = repository.getMealsByCategory(categoryName)
        }
    }
}