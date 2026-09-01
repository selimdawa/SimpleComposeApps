package com.flatcode.simplecomposeapps.crypto.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_details")
data class CoinDetailEntity(
    @PrimaryKey val symbol: String,
    val name: String,
    val description: String,
    val logo: String
)