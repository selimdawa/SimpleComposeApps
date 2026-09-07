package com.flatcode.simplecomposeapps.multipledelete.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "multiple_delete_items")
data class MultiDeleteEntity(
    @PrimaryKey val text: String
)