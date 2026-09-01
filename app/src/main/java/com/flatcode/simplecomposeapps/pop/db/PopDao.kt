package com.flatcode.simplecomposeapps.pop.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flatcode.simplecomposeapps.pop.model.PopItem
import kotlinx.coroutines.flow.Flow

@Dao
interface PopDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPops(pops: List<PopItem>)

    @Query("SELECT * FROM pops")
    fun getAllPops(): Flow<List<PopItem>>

    @Query("DELETE FROM pops")
    suspend fun deleteAllPops()
}