package com.flatcode.simplecomposeapps.pop.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.pop.db.PopDao
import com.flatcode.simplecomposeapps.pop.db.PopDatabase
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
    fun providePopDatabase(@ApplicationContext context: Context): PopDatabase {
        return Room.databaseBuilder(
            context,
            PopDatabase::class.java,
            "pop_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun providePopDao(database: PopDatabase): PopDao {
        return database.popDao()
    }
}