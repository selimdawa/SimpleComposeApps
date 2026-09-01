package com.flatcode.simplecomposeapps.news2.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flatcode.simplecomposeapps.news2.models.EverythingNewsItem
import com.flatcode.simplecomposeapps.news2.models.TopArticlesNewsItem
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEverything(news: List<EverythingNewsItem>)

    @Query("SELECT * FROM everything_news")
    fun getAllEverything(): Flow<List<EverythingNewsItem>>

    @Query("DELETE FROM everything_news")
    suspend fun deleteAllEverything()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopArticles(news: List<TopArticlesNewsItem>)

    @Query("SELECT * FROM top_articles_news")
    fun getAllTopArticles(): Flow<List<TopArticlesNewsItem>>

    @Query("DELETE FROM top_articles_news")
    suspend fun deleteAllTopArticles()
}