package com.flatcode.simplecomposeapps.web.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "web_items")
data class WebEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val url: String,
    val type: String, // "HISTORY" or "BOOKMARK"
    val timestamp: Long = System.currentTimeMillis()
)