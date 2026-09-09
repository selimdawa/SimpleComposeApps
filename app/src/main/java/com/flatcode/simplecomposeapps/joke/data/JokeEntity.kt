package com.flatcode.simplecomposeapps.joke.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jokes")
data class JokeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val requestedCategory: String,
    val category: String?,
    val type: String?,
    val joke: String?,
    val setup: String?,
    val delivery: String?
)