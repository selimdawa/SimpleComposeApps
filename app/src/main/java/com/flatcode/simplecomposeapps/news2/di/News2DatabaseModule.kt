package com.flatcode.simplecomposeapps.news2.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.news2.data.local.News2Database
import com.flatcode.simplecomposeapps.news2.data.local.NewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object News2DatabaseModule {

    @Provides
    @Singleton
    fun provideNews2Database(@ApplicationContext context: Context): News2Database {
        return Room.databaseBuilder(
            context,
            News2Database::class.java,
            "news2_db"
        ).build()
    }

    @Provides
    fun provideNewsDao(database: News2Database): NewsDao {
        return database.newsDao()
    }
}