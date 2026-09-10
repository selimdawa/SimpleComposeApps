package com.flatcode.simplecomposeapps.news.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flatcode.simplecomposeapps.news.model.NewsHeadlines

@Database(entities = [NewsHeadlines::class], version = 2, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}