package com.flatcode.simplecomposeapps.web.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WebDao {
    @Query("SELECT * FROM web_items WHERE type = :type ORDER BY timestamp DESC")
    fun getItemsByType(type: String): Flow<List<WebEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: WebEntity)

    @Delete
    suspend fun deleteItem(item: WebEntity)

    @Query("DELETE FROM web_items WHERE type = :type")
    suspend fun clearByType(type: String)
}