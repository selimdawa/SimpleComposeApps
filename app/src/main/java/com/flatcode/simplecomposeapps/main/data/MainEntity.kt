package com.flatcode.simplecomposeapps.main.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "main_items")
data class MainEntity(
    @PrimaryKey val title: String,
    val imageType: String,
    val imageValue: String,
    val number: Int,
    val activityClassName: String
)