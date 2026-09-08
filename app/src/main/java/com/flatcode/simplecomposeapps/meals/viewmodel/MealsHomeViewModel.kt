package com.flatcode.simplecomposeapps.meals.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.meals.db.MealDao
import com.flatcode.simplecomposeapps.meals.model.Category
import com.flatcode.simplecomposeapps.meals.model.CategoryList
import com.flatcode.simplecomposeapps.meals.model.Meal
import com.flatcode.simplecomposeapps.meals.model.MealList
import com.flatcode.simplecomposeapps.meals.model.MealsByCategory
import com.flatcode.simplecomposeapps.meals.model.MealsByCategoryList
import com.flatcode.simplecomposeapps.meals.retrofit.MealApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsHomeViewModel @Inject constructor(
    private val mealApi: MealApi,
    private val mealDao: MealDao
) : ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var favoritesMealsLiveData = mealDao.getAllMeals()

    fun getRandomMeal() {
        viewModelScope.launch {
            try {
                val response = mealApi.getRandomMeal()
                if (response.meals.isNotEmpty()) {
                    randomMealLiveData.value = response.meals[0]
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getPopularItems() {
        viewModelScope.launch {
            try {
                val response = mealApi.getPopularItems("Seafood")
                popularItemsLiveData.value = response.meals
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            try {
                val response = mealApi.getCategories()
                categoriesLiveData.postValue(response.categories)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDao.upsert(meal)
        }
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDao.delete(meal)
        }
    }

    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observerPopularItemsLiveData(): LiveData<List<MealsByCategory>> {
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData(): LiveData<List<Category>> {
        return categoriesLiveData
    }

    fun observeFavoritesMealsLiveData(): LiveData<List<Meal>> {
        return favoritesMealsLiveData
    }
}