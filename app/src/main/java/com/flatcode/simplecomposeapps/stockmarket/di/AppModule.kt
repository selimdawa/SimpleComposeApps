package com.flatcode.simplecomposeapps.stockmarket.di

import android.app.Application
import androidx.room.Room
import com.flatcode.simplecomposeapps.stockmarket.data.local.StockDao
import com.flatcode.simplecomposeapps.stockmarket.data.local.StockDatabase
import com.flatcode.simplecomposeapps.stockmarket.data.remote.StockApi
import com.flatcode.simplecomposeapps.stockmarket.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideStockApi(): StockApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideStockDatabase(app: Application): StockDatabase {
        return Room.databaseBuilder(app, StockDatabase::class.java, "stockdb.db").build()
    }

    @Provides
    @Singleton
    fun provideStockDao(db: StockDatabase): StockDao = db.dao
}