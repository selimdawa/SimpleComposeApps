package com.flatcode.simplecomposeapps.meals.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.meals.model.MealsByCategory
import com.flatcode.simplecomposeapps.meals.data.network.MealApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesMealsViewModel @Inject constructor(
    private val mealApi: MealApi
) : ViewModel() {

    private var mealsLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName: String) {
        viewModelScope.launch {
            try {
                val response = mealApi.getMealsByCategory(categoryName)
                mealsLiveData.postValue(response.meals)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun observeMealsLiveData(): LiveData<List<MealsByCategory>> {
        return mealsLiveData
    }
}