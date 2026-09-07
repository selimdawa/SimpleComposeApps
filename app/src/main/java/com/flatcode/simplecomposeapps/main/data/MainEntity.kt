package com.flatcode.simplecomposeapps.main.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "main_items")
data class MainEntity(
    @PrimaryKey val title: String,
    val imageType: String, // "IMAGE_VECTOR" or "RESOURCE_ID"
    val imageValue: String, // Name of ImageVector or String value of Resource ID
    val number: Int,
    val activityClassName: String
)