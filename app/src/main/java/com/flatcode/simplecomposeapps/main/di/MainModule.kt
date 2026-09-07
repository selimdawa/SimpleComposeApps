package com.flatcode.simplecomposeapps.main.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.main.data.MainDao
import com.flatcode.simplecomposeapps.main.data.MainDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideMainDatabase(@ApplicationContext context: Context): MainDatabase {
        return Room.databaseBuilder(
            context,
            MainDatabase::class.java,
            "main_database"
        ).build()
    }

    @Provides
    fun provideMainDao(database: MainDatabase): MainDao {
        return database.mainDao()
    }
}