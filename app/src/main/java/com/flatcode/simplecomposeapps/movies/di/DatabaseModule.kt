package com.flatcode.simplecomposeapps.movies.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.movies.data.room.MoviesRoomDatabase
import com.flatcode.simplecomposeapps.movies.data.room.dao.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMoviesDatabase(@ApplicationContext context: Context): MoviesRoomDatabase {
        return Room.databaseBuilder(
            context,
            MoviesRoomDatabase::class.java,
            "movies_db"
        ).build()
    }

    @Provides
    fun provideMoviesDao(database: MoviesRoomDatabase): MoviesDao {
        return database.moviesDao()
    }
}