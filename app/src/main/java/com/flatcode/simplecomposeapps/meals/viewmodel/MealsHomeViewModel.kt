package com.flatcode.simplecomposeapps.meals.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.meals.repository.MealRepository
import com.flatcode.simplecomposeapps.meals.model.Category
import com.flatcode.simplecomposeapps.meals.model.Meal
import com.flatcode.simplecomposeapps.meals.model.MealsByCategory
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsHomeViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    private val _randomMeal = MutableLiveData<Resource<Meal>>()
    val randomMeal: LiveData<Resource<Meal>> = _randomMeal

    private val _popularItems = MutableLiveData<Resource<List<MealsByCategory>>>()
    val popularItems: LiveData<Resource<List<MealsByCategory>>> = _popularItems

    private val _categories = MutableLiveData<Resource<List<Category>>>()
    val categories: LiveData<Resource<List<Category>>> = _categories

    val favoritesMeals: LiveData<List<Meal>> = repository.getFavoriteMeals()

    fun getRandomMeal() {
        viewModelScope.launch {
            _randomMeal.value = Resource.Loading()
            _randomMeal.value = repository.getRandomMeal()
        }
    }

    fun getPopularItems() {
        viewModelScope.launch {
            _popularItems.value = Resource.Loading()
            _popularItems.value = repository.getPopularItems("Seafood")
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            _categories.value = Resource.Loading()
            _categories.value = repository.getCategories()
        }
    }
}
