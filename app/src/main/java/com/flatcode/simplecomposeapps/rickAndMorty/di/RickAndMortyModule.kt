package com.flatcode.simplecomposeapps.rickAndMorty.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.rickAndMorty.data.RickAndMortyDao
import com.flatcode.simplecomposeapps.rickAndMorty.data.RickAndMortyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RickAndMortyModule {

    @Provides
    @Singleton
    fun provideRickAndMortyDatabase(@ApplicationContext context: Context): RickAndMortyDatabase {
        return Room.databaseBuilder(
            context,
            RickAndMortyDatabase::class.java,
            "rick_and_morty_db"
        ).build()
    }

    @Provides
    fun provideRickAndMortyDao(database: RickAndMortyDatabase): RickAndMortyDao {
        return database.rickAndMortyDao()
    }
}