package com.flatcode.simplecomposeapps.crypto.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crypto_settings")
data class CryptoSettingsEntity(
    @PrimaryKey val id: Int = 1,
    val coinId: Int?,
    val coinSymbol: String?
)