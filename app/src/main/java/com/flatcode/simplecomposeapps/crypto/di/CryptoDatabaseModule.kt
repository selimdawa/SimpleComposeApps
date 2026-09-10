package com.flatcode.simplecomposeapps.crypto.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.crypto.db.CryptoDatabase
import com.flatcode.simplecomposeapps.crypto.db.dao.CoinDao
import com.flatcode.simplecomposeapps.crypto.db.dao.CoinDetailDao
import com.flatcode.simplecomposeapps.crypto.db.dao.SettingsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CryptoDatabaseModule {

    @Provides
    @Singleton
    fun provideCryptoDatabase(@ApplicationContext context: Context): CryptoDatabase {
        return Room.databaseBuilder(
            context,
            CryptoDatabase::class.java,
            "crypto_db",
        ).build()
    }

    @Provides
    fun provideCoinDao(database: CryptoDatabase): CoinDao {
        return database.coinDao()
    }

    @Provides
    fun provideCoinDetailDao(database: CryptoDatabase): CoinDetailDao {
        return database.coinDetailDao()
    }

    @Provides
    fun provideSettingsDao(database: CryptoDatabase): SettingsDao {
        return database.settingsDao()
    }
}