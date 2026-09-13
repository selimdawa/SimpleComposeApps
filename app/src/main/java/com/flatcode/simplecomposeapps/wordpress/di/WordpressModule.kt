package com.flatcode.simplecomposeapps.wordpress.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.wordpress.data.PostDao
import com.flatcode.simplecomposeapps.wordpress.data.PostDatabase
import com.flatcode.simplecomposeapps.wordpress.data.WordpressRepository
import com.flatcode.simplecomposeapps.wordpress.data.network.WordPressApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordpressModule {

    @Provides
    @Singleton
    fun providePostDatabase(@ApplicationContext context: Context): PostDatabase {
        return Room.databaseBuilder(
            context,
            PostDatabase::class.java,
            "wordpress_database"
        ).build()
    }

    @Provides
    fun providePostDao(database: PostDatabase): PostDao {
        return database.postDao()
    }

    @Provides
    @Singleton
    fun provideWordpressRepository(api: WordPressApi, dao: PostDao): WordpressRepository {
        return WordpressRepository(api, dao)
    }
}