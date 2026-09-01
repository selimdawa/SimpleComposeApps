package com.flatcode.simplecomposeapps.meals.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.flatcode.simplecomposeapps.meals.pojo.Meal

@Database(entities = [Meal::class], version = 1)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
}