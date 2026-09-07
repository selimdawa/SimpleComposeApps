package com.flatcode.simplecomposeapps.videoplayer.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.videoplayer.data.VideoDao
import com.flatcode.simplecomposeapps.videoplayer.data.VideoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VideoModule {

    @Provides
    @Singleton
    fun provideVideoDatabase(@ApplicationContext context: Context): VideoDatabase {
        return Room.databaseBuilder(
            context,
            VideoDatabase::class.java,
            "video_database"
        ).build()
    }

    @Provides
    fun provideVideoDao(database: VideoDatabase): VideoDao {
        return database.videoDao()
    }
}