package com.flatcode.simplecomposeapps.crypto.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flatcode.simplecomposeapps.crypto.db.dao.CoinDao
import com.flatcode.simplecomposeapps.crypto.db.dao.CoinDetailDao
import com.flatcode.simplecomposeapps.crypto.db.dao.SettingsDao
import com.flatcode.simplecomposeapps.crypto.db.entity.CoinDetailEntity
import com.flatcode.simplecomposeapps.crypto.db.entity.CoinEntity
import com.flatcode.simplecomposeapps.crypto.db.entity.CryptoSettingsEntity

@Database(
    entities = [CoinEntity::class, CoinDetailEntity::class, CryptoSettingsEntity::class],
    version = 1,
    exportSchema = true
)
abstract class CryptoDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
    abstract fun coinDetailDao(): CoinDetailDao
    abstract fun settingsDao(): SettingsDao
}