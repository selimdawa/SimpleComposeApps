package com.flatcode.simplecomposeapps.blogger.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.blogger.data.BloggerDao
import com.flatcode.simplecomposeapps.blogger.data.BloggerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BloggerDatabaseModule {

    @Provides
    @Singleton
    fun provideBloggerDatabase(@ApplicationContext context: Context): BloggerDatabase {
        return Room.databaseBuilder(
            context,
            BloggerDatabase::class.java,
            "blogger_database"
        ).build()
    }

    @Provides
    fun provideBloggerDao(database: BloggerDatabase): BloggerDao {
        return database.bloggerDao()
    }
}
