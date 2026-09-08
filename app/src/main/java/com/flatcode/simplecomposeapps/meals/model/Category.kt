package com.flatcode.simplecomposeapps.meals.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String
)