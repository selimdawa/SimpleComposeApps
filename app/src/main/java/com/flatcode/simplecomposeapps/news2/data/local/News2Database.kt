package com.flatcode.simplecomposeapps.news2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flatcode.simplecomposeapps.news2.models.EverythingNewsItem
import com.flatcode.simplecomposeapps.news2.models.TopArticlesNewsItem

@Database(entities = [EverythingNewsItem::class, TopArticlesNewsItem::class], version = 1, exportSchema = true)
abstract class News2Database : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}