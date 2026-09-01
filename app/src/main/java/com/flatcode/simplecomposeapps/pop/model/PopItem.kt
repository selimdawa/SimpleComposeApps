package com.flatcode.simplecomposeapps.pop.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pops")
data class PopItem(
    @PrimaryKey val name: String,
    val series: String? = null
)