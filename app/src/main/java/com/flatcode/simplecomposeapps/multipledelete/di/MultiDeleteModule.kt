package com.flatcode.simplecomposeapps.multipledelete.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.flatcode.simplecomposeapps.multipledelete.data.MultiDeleteDao
import com.flatcode.simplecomposeapps.multipledelete.data.MultiDeleteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MultiDeleteModule {

    @Provides
    @Singleton
    fun provideMultiDeleteDatabase(@ApplicationContext context: Context): MultiDeleteDatabase {
        return Room.databaseBuilder(
            context,
            MultiDeleteDatabase::class.java,
            "multiple_delete_database"
        ).build()
    }

    @Provides
    fun provideMultiDeleteDao(database: MultiDeleteDatabase): MultiDeleteDao {
        return database.multiDeleteDao()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("multi_delete_prefs", Context.MODE_PRIVATE)
    }
}