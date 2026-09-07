package com.flatcode.simplecomposeapps.multipledelete.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MultiDeleteDao {
    @Query("SELECT * FROM multiple_delete_items")
    fun getAllItems(): Flow<List<MultiDeleteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MultiDeleteEntity>)

    @Query("DELETE FROM multiple_delete_items WHERE text IN (:texts)")
    suspend fun deleteByTexts(texts: List<String>)

    @Query("DELETE FROM multiple_delete_items")
    suspend fun deleteAll()
}