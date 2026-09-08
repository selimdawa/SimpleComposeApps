package com.flatcode.simplecomposeapps.meals.model

import kotlinx.serialization.Serializable

@Serializable
data class MealList(
    val meals: List<Meal>
)