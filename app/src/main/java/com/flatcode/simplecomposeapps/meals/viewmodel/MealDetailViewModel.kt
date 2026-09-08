package com.flatcode.simplecomposeapps.meals.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.meals.db.MealDao
import com.flatcode.simplecomposeapps.meals.model.Meal
import com.flatcode.simplecomposeapps.meals.model.MealList
import com.flatcode.simplecomposeapps.meals.retrofit.MealApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    private val mealApi: MealApi,
    private val mealDao: MealDao
) : ViewModel() {

    private var mealDetailsLiveData = MutableLiveData<Meal>()

    fun getMealDetail(id: String) {
        viewModelScope.launch {
            try {
                val response = mealApi.getMealDetails(id)
                if (response.meals.isNotEmpty()) {
                    mealDetailsLiveData.value = response.meals[0]
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun observeMealDetailsLiveData(): LiveData<Meal> {
        return mealDetailsLiveData
    }

    fun isMealFavorite(id: String): LiveData<Meal?> {
        return mealDao.getMealById(id)
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
}