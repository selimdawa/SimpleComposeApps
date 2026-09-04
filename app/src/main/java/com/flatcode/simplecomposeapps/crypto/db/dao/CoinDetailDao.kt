package com.flatcode.simplecomposeapps.crypto.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flatcode.simplecomposeapps.crypto.db.entity.CoinDetailEntity

@Dao
interface CoinDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinDetail(coinDetail: CoinDetailEntity)

    @Query("SELECT * FROM coin_details WHERE id = :id")
    suspend fun getCoinDetail(id: Int): CoinDetailEntity?

    @Query("DELETE FROM coin_details WHERE id = :id")
    suspend fun deleteCoinDetail(id: Int)
}