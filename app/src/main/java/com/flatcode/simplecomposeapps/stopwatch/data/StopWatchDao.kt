package com.flatcode.simplecomposeapps.stopwatch.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StopWatchDao {
    @Query("SELECT lastTime FROM stopwatch_settings WHERE id = 1")
    fun getLastTime(): Flow<String?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLastTime(time: StopWatchEntity)
}
