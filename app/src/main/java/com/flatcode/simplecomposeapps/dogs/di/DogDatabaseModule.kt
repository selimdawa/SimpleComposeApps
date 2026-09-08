package com.flatcode.simplecomposeapps.dogs.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.dogs.data.DogDatabase
import com.flatcode.simplecomposeapps.dogs.data.DogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
object DogDatabaseModule {

    @Provides
    @Singleton
    fun provideDogDatabase(@ApplicationContext context: Context): DogDatabase {
        return Room.databaseBuilder(
            context, DogDatabase::class.java, "dogs_db"
        ).build()
    }

    @Provides
    fun provideDogDao(database: DogDatabase): DogDao {
        return database.dogDao()
    }
}