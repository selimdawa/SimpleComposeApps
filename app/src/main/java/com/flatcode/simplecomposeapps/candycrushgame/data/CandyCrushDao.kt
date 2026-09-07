package com.flatcode.simplecomposeapps.candycrushgame.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CandyCrushDao {
    @Query("SELECT * FROM candy_crush_scores WHERE id = 1")
    fun getCandyCrushData(): Flow<CandyCrushEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCandyCrushData(data: CandyCrushEntity)
}