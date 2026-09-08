package com.flatcode.simplecomposeapps.dictionary.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.dictionary.data.local.DictionaryDatabase
import com.flatcode.simplecomposeapps.dictionary.data.local.WordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DictionaryDatabaseModule {

    @Provides
    @Singleton
    fun provideDictionaryDatabase(@ApplicationContext context: Context): DictionaryDatabase {
        return Room.databaseBuilder(
            context,
            DictionaryDatabase::class.java,
            "dictionary_db"
        ).build()
    }

    @Provides
    fun provideWordDao(database: DictionaryDatabase): WordDao {
        return database.wordDao()
    }
}