package com.flatcode.simplecomposeapps.movies.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.movies.db.MoviesRoomDatabase
import com.flatcode.simplecomposeapps.movies.db.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDatabaseModule {

    @Provides
    @Singleton
    fun provideMoviesDatabase(@ApplicationContext context: Context): MoviesRoomDatabase {
        return Room.databaseBuilder(
            context,
            MoviesRoomDatabase::class.java,
            "movies_db"
        ).fallbackToDestructiveMigration(true).build()
    }

    @Provides
    fun provideMoviesDao(database: MoviesRoomDatabase): MoviesDao {
        return database.moviesDao()
    }
}