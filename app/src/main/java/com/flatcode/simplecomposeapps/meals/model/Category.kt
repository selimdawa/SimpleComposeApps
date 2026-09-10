package com.flatcode.simplecomposeapps.meals.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "categories")
data class Category(
    @PrimaryKey val idCategory: String,
    val strCategory: String = "",
    val strCategoryDescription: String = "",
    val strCategoryThumb: String = ""
)