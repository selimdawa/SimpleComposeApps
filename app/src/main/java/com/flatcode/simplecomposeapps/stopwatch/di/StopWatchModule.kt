package com.flatcode.simplecomposeapps.stopwatch.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.stopwatch.data.StopWatchDao
import com.flatcode.simplecomposeapps.stopwatch.data.StopWatchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StopWatchModule {

    @Provides
    @Singleton
    fun provideStopWatchDatabase(@ApplicationContext context: Context): StopWatchDatabase {
        return Room.databaseBuilder(
            context,
            StopWatchDatabase::class.java,
            "stopwatch_database"
        ).build()
    }

    @Provides
    fun provideStopWatchDao(database: StopWatchDatabase): StopWatchDao {
        return database.stopWatchDao()
    }
}