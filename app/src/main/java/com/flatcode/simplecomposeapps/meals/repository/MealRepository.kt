package com.flatcode.simplecomposeapps.meals.repository

import com.flatcode.simplecomposeapps.meals.network.MealApi
import com.flatcode.simplecomposeapps.meals.db.MealDao
import com.flatcode.simplecomposeapps.meals.model.Meal
import com.flatcode.simplecomposeapps.ui.theme.Strings
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
                val meal = response.meals[0]
                val existing = mealDao.getMealByIdInternal(meal.idMeal)
                val mealToSave = if (existing != null) {
                    meal.copy(isFavorite = existing.isFavorite)
                } else {
                    meal
                }
                mealDao.upsert(mealToSave)
                Resource.Success(mealToSave)
            } else {
                Resource.Error(Strings.FAILED_LOAD_DATA)
            }
        } catch (_: Exception) {
            val cachedMeals = mealDao.getAllMealsList()
            if (cachedMeals.isNotEmpty()) {
                Resource.Success(cachedMeals.random())
            } else {
                Resource.Error(Strings.FAILED_LOAD_DATA)
            }
        }
    }

    suspend fun getPopularItems(categoryName: String) = withContext(Dispatchers.IO) {
        try {
            val response = mealApi.getPopularItems(categoryName)
            val meals = response.meals.map { it.copy(categoryName = categoryName) }
            mealDao.insertMealsByCategory(meals)
            Resource.Success(meals)
        } catch (_: Exception) {
            val cached = mealDao.getMealsByCategory(categoryName)
            if (cached.isNotEmpty()) {
                Resource.Success(cached)
            } else {
                Resource.Error(Strings.FAILED_LOAD_DATA)
            }
        }
    }

    suspend fun getCategories() = withContext(Dispatchers.IO) {
        try {
            val response = mealApi.getCategories()
            mealDao.insertCategories(response.categories)
            Resource.Success(response.categories)
        } catch (_: Exception) {
            val cached = mealDao.getCategories()
            if (cached.isNotEmpty()) {
                Resource.Success(cached)
            } else {
                Resource.Error(Strings.FAILED_LOAD_DATA)
            }
        }
    }

    suspend fun getMealsByCategory(categoryName: String) = withContext(Dispatchers.IO) {
        try {
            val response = mealApi.getMealsByCategory(categoryName)
            val meals = response.meals.map { it.copy(categoryName = categoryName) }
            mealDao.insertMealsByCategory(meals)
            Resource.Success(meals)
        } catch (_: Exception) {
            val cached = mealDao.getMealsByCategory(categoryName)
            if (cached.isNotEmpty()) {
                Resource.Success(cached)
            } else {
                Resource.Error(Strings.FAILED_LOAD_DATA)
            }
        }
    }

    suspend fun getMealDetails(id: String) = withContext(Dispatchers.IO) {
        try {
            val response = mealApi.getMealDetails(id)
            if (response.meals.isNotEmpty()) {
                val meal = response.meals[0]
                val existing = mealDao.getMealByIdInternal(meal.idMeal)
                val mealToSave = if (existing != null) {
                    meal.copy(isFavorite = existing.isFavorite)
                } else {
                    meal
                }
                mealDao.upsert(mealToSave)
                Resource.Success(mealToSave)
            } else {
                Resource.Error(Strings.FAILED_LOAD_DATA)
            }
        } catch (_: Exception) {
            val cached = mealDao.getMealByIdInternal(id)
            if (cached != null) {
                Resource.Success(cached)
            } else {
                Resource.Error(Strings.FAILED_LOAD_DATA)
            }
        }
    }

    suspend fun upsertMeal(meal: Meal) = withContext(Dispatchers.IO) {
        mealDao.upsert(meal)
    }

    fun getFavoriteMeals() = mealDao.getAllMeals()

    fun getMealById(id: String) = mealDao.getMealById(id)
}