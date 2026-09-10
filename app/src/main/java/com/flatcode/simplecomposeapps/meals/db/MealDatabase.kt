package com.flatcode.simplecomposeapps.meals.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flatcode.simplecomposeapps.meals.model.Category
import com.flatcode.simplecomposeapps.meals.model.Meal
import com.flatcode.simplecomposeapps.meals.model.MealsByCategory

@Database(entities = [Meal::class, Category::class, MealsByCategory::class], version = 3, exportSchema = true)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
}