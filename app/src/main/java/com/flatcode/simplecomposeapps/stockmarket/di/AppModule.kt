package com.flatcode.simplecomposeapps.stockmarket.di

import android.app.Application
import androidx.room.Room
import com.flatcode.simplecomposeapps.stockmarket.data.StockDao
import com.flatcode.simplecomposeapps.stockmarket.data.StockDatabase
import com.flatcode.simplecomposeapps.stockmarket.model.CompanyListing
import com.flatcode.simplecomposeapps.stockmarket.network.CSVParser
import com.flatcode.simplecomposeapps.stockmarket.network.CompanyListingsParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideStockDatabase(app: Application): StockDatabase {
        return Room.databaseBuilder(app, StockDatabase::class.java, "stockdb.db").build()
    }

    @Provides
    @Singleton
    fun provideStockDao(db: StockDatabase): StockDao = db.dao

    @Provides
    @Singleton
    fun provideCompanyListingsParser(
        companyListingsParser: CompanyListingsParser
    ): CSVParser<CompanyListing> = companyListingsParser
}