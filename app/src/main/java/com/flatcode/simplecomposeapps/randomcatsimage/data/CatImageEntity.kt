package com.flatcode.simplecomposeapps.randomcatsimage.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_images")
data class CatImageEntity(
    @PrimaryKey val url: String,
    val timestamp: Long = System.currentTimeMillis()
)