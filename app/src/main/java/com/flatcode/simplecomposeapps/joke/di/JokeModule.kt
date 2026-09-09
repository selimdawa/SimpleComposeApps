package com.flatcode.simplecomposeapps.joke.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.joke.data.JokeDao
import com.flatcode.simplecomposeapps.joke.data.JokeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JokeModule {

    @Provides
    @Singleton
    fun provideJokeDatabase(@ApplicationContext context: Context): JokeDatabase {
        return Room.databaseBuilder(
            context,
            JokeDatabase::class.java,
            "joke_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideJokeDao(database: JokeDatabase): JokeDao {
        return database.jokeDao()
    }
}