package com.flatcode.simplecomposeapps.meals.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "mealsByCategory")
data class MealsByCategory(
    @PrimaryKey val idMeal: String,
    val strMeal: String = "",
    val strMealThumb: String = "",
    val categoryName: String = ""
)