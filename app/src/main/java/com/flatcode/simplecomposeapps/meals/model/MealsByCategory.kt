package com.flatcode.simplecomposeapps.meals.model

import kotlinx.serialization.Serializable

@Serializable
data class MealsByCategory(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)