package com.flatcode.simplecomposeapps.stockmarket.di

import com.flatcode.simplecomposeapps.stockmarket.network.CSVParser
import com.flatcode.simplecomposeapps.stockmarket.network.CompanyListingsParser
import com.flatcode.simplecomposeapps.stockmarket.model.CompanyListing
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
}