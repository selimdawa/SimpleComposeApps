package com.flatcode.simplecomposeapps.randomcatsimage.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.randomcatsimage.data.CatImageDao
import com.flatcode.simplecomposeapps.randomcatsimage.data.CatImageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CatImageModule {

    @Provides
    @Singleton
    fun provideCatImageDatabase(@ApplicationContext context: Context): CatImageDatabase {
        return Room.databaseBuilder(
            context,
            CatImageDatabase::class.java,
            "cat_image_database"
        ).build()
    }

    @Provides
    fun provideCatImageDao(database: CatImageDatabase): CatImageDao {
        return database.catImageDao()
    }
}