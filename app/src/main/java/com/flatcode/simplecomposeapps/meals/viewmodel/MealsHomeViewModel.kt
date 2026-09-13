package com.flatcode.simplecomposeapps.meals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.meals.repository.MealRepository
import com.flatcode.simplecomposeapps.meals.model.Category
import com.flatcode.simplecomposeapps.meals.model.Meal
import com.flatcode.simplecomposeapps.meals.model.MealsByCategory
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsHomeViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    private val _randomMeal = MutableStateFlow<Resource<Meal>>(Resource.Idle)
    val randomMeal: StateFlow<Resource<Meal>> = _randomMeal.asStateFlow()

    private val _popularItems = MutableStateFlow<Resource<List<MealsByCategory>>>(Resource.Idle)
    val popularItems: StateFlow<Resource<List<MealsByCategory>>> = _popularItems.asStateFlow()

    private val _categories = MutableStateFlow<Resource<List<Category>>>(Resource.Idle)
    val categories: StateFlow<Resource<List<Category>>> = _categories.asStateFlow()

    val favoritesMeals: StateFlow<List<Meal>> = repository.getFavoriteMeals()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun getRandomMeal() {
        viewModelScope.launch {
            _randomMeal.value = Resource.Loading()
            val result = repository.getRandomMeal()
            _randomMeal.value = result
        }
    }

    fun getPopularItems() {
        viewModelScope.launch {
            _popularItems.value = Resource.Loading()
            val result = repository.getPopularItems("Seafood")
            _popularItems.value = result
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            _categories.value = Resource.Loading()
            val result = repository.getCategories()
            _categories.value = result
        }
    }
}
