package com.flatcode.simplecomposeapps.crypto.db.dao

import androidx.room.*
import com.flatcode.simplecomposeapps.crypto.db.entity.CryptoSettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Query("SELECT * FROM crypto_settings WHERE id = 1")
    fun getSettings(): Flow<CryptoSettingsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: CryptoSettingsEntity)
}