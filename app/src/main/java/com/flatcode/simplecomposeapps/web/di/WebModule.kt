package com.flatcode.simplecomposeapps.web.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.web.data.WebDao
import com.flatcode.simplecomposeapps.web.data.WebDatabase
import com.flatcode.simplecomposeapps.web.data.WebRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WebModule {

    @Provides
    @Singleton
    fun provideWebDatabase(@ApplicationContext context: Context): WebDatabase {
        return Room.databaseBuilder(
            context,
            WebDatabase::class.java,
            "web_database"
        ).build()
    }

    @Provides
    fun provideWebDao(database: WebDatabase): WebDao {
        return database.webDao()
    }

    @Provides
    @Singleton
    fun provideWebRepository(webDao: WebDao): WebRepository {
        return WebRepository(webDao)
    }
}