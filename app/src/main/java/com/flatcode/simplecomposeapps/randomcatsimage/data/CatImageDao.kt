package com.flatcode.simplecomposeapps.randomcatsimage.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CatImageDao {
    @Query("SELECT * FROM cat_images ORDER BY timestamp DESC")
    fun getAllImages(): Flow<List<CatImageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: CatImageEntity)

    @Query("DELETE FROM cat_images")
    suspend fun deleteAll()
}