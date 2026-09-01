package com.flatcode.simplecomposeapps.stockmarket.di

import com.flatcode.simplecomposeapps.stockmarket.data.csv.CSVParser
import com.flatcode.simplecomposeapps.stockmarket.data.csv.CompanyListingsParser
import com.flatcode.simplecomposeapps.stockmarket.data.repository.StockRepositoryImpl
import com.flatcode.simplecomposeapps.stockmarket.domain.model.CompanyListing
import com.flatcode.simplecomposeapps.stockmarket.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCSVParser(parser: CompanyListingsParser): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(repo: StockRepositoryImpl): StockRepository
}