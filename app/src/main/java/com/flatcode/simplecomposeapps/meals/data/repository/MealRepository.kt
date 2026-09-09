package com.flatcode.simplecomposeapps.meals.data.repository

import com.flatcode.simplecomposeapps.meals.data.network.MealApi
import com.flatcode.simplecomposeapps.meals.db.MealDao
import com.flatcode.simplecomposeapps.meals.model.Meal
import com.flatcode.simplecomposeapps.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealRepository @Inject constructor(
    private val mealApi: MealApi,
    private val mealDao: MealDao
) {
    suspend fun getRandomMeal() = withContext(Dispatchers.IO) {
        try {
            val response = mealApi.getRandomMeal()
            if (response.meals.isNotEmpty()) {
                Resource.Success(response.meals[0])
            } else {
                Resource.Error("No meal found")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun getPopularItems(categoryName: String) = withContext(Dispatchers.IO) {
        try {
            val response = mealApi.getPopularItems(categoryName)
            Resource.Success(response.meals)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun getCategories() = withContext(Dispatchers.IO) {
        try {
            val response = mealApi.getCategories()
            Resource.Success(response.categories)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun getMealsByCategory(categoryName: String) = withContext(Dispatchers.IO) {
        try {
            val response = mealApi.getMealsByCategory(categoryName)
            Resource.Success(response.meals)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun getMealDetails(id: String) = withContext(Dispatchers.IO) {
        try {
            val response = mealApi.getMealDetails(id)
            if (response.meals.isNotEmpty()) {
                Resource.Success(response.meals[0])
            } else {
                Resource.Error("Meal details not found")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun searchMeals(searchQuery: String) = withContext(Dispatchers.IO) {
        try {
            val response = mealApi.searchMeals(searchQuery)
            Resource.Success(response.meals)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun upsertMeal(meal: Meal) = withContext(Dispatchers.IO) {
        mealDao.upsert(meal)
    }

    suspend fun deleteMeal(meal: Meal) = withContext(Dispatchers.IO) {
        mealDao.delete(meal)
    }

    fun getFavoriteMeals() = mealDao.getAllMeals()
    
    fun getMealById(id: String) = mealDao.getMealById(id)
}