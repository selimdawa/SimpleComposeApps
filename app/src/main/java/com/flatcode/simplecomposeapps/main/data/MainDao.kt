package com.flatcode.simplecomposeapps.main.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MainDao {
    @Query("SELECT * FROM main_items")
    fun getAllItems(): Flow<List<MainEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MainEntity>)

    @Query("DELETE FROM main_items")
    suspend fun deleteAll()

    @Query("SELECT count FROM main_settings WHERE `key` = :key")
    fun getCount(key: String): Flow<Int?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSetting(setting: MainSettingsEntity)
}