package com.flatcode.simplecomposeapps.candycrushgame.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.candycrushgame.data.CandyCrushDao
import com.flatcode.simplecomposeapps.candycrushgame.data.CandyCrushDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CandyCrushModule {

    @Provides
    @Singleton
    fun provideCandyCrushDatabase(@ApplicationContext context: Context): CandyCrushDatabase {
        return Room.databaseBuilder(
            context,
            CandyCrushDatabase::class.java,
            "candy_crush_database"
        ).build()
    }

    @Provides
    fun provideCandyCrushDao(database: CandyCrushDatabase): CandyCrushDao {
        return database.candyCrushDao()
    }
}