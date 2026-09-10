package com.flatcode.simplecomposeapps.meals.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.flatcode.simplecomposeapps.meals.model.Category
import com.flatcode.simplecomposeapps.meals.model.Meal
import com.flatcode.simplecomposeapps.meals.model.MealsByCategory

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: Meal)

    @Update
    suspend fun updateFavorite(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM mealInformation WHERE isFavorite = 1")
    fun getAllMeals(): LiveData<List<Meal>>

    @Query("SELECT * FROM mealInformation")
    suspend fun getAllMealsList(): List<Meal>

    @Query("SELECT * FROM mealInformation WHERE idMeal = :id")
    fun getMealById(id: String): LiveData<Meal?>

    @Query("SELECT * FROM mealInformation WHERE idMeal = :id")
    suspend fun getMealByIdInternal(id: String): Meal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)

    @Query("SELECT * FROM categories")
    suspend fun getCategories(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealsByCategory(meals: List<MealsByCategory>)

    @Query("SELECT * FROM mealsByCategory WHERE categoryName = :categoryName")
    suspend fun getMealsByCategory(categoryName: String): List<MealsByCategory>
}
