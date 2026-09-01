package com.flatcode.simplecomposeapps.crypto.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val symbol: String,
    val price: Double
)