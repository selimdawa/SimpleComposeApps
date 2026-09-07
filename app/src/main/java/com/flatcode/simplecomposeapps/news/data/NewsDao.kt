package com.flatcode.simplecomposeapps.news.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news_headlines WHERE isCache = 0")
    fun getAllNews(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news_headlines WHERE category = :category AND isCache = 1")
    suspend fun getCacheByCategory(category: String): List<NewsEntity>

    @Query("DELETE FROM news_headlines WHERE category = :category AND isCache = 1")
    suspend fun deleteCacheByCategory(category: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsEntity>)

    @Query("DELETE FROM news_headlines")
    suspend fun deleteAll()
}