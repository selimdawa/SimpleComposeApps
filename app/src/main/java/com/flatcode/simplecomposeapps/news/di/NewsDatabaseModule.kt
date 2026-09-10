package com.flatcode.simplecomposeapps.news.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.news.data.local.NewsDao
import com.flatcode.simplecomposeapps.news.data.local.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsDatabaseModule {

    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(database: NewsDatabase): NewsDao {
        return database.newsDao()
    }
}