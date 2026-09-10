package com.flatcode.simplecomposeapps.news.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flatcode.simplecomposeapps.news.model.NewsHeadlines

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<NewsHeadlines>)

    @Query("SELECT * FROM news_headlines WHERE category = :category")
    suspend fun getNewsByCategory(category: String): List<NewsHeadlines>

    @Query("DELETE FROM news_headlines WHERE category = :category")
    suspend fun deleteByCategory(category: String)
}