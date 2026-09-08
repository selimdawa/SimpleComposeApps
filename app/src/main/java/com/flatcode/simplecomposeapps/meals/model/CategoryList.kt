package com.flatcode.simplecomposeapps.meals.model

import kotlinx.serialization.Serializable

@Serializable
data class CategoryList(
    val categories: List<Category>
)