package com.flatcode.simplecomposeapps.crypto.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.crypto.db.AppDatabase
import com.flatcode.simplecomposeapps.crypto.db.dao.CoinDao
import com.flatcode.simplecomposeapps.crypto.db.dao.CoinDetailDao
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
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "crypto_db"
        ).build()
    }

    @Provides
    fun provideCoinDao(database: AppDatabase): CoinDao {
        return database.coinDao()
    }

    @Provides
    fun provideCoinDetailDao(database: AppDatabase): CoinDetailDao {
        return database.coinDetailDao()
    }
}