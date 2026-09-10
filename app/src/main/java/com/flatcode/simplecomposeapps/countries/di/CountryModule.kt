package com.flatcode.simplecomposeapps.countries.di

import android.content.Context
import com.flatcode.simplecomposeapps.countries.db.CountryDAO
import com.flatcode.simplecomposeapps.countries.db.CountryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CountryModule {

    @Provides
    @Singleton
    fun provideCountryDatabase(@ApplicationContext context: Context): CountryDatabase {
        return CountryDatabase(context)
    }

    @Provides
    @Singleton
    fun provideCountryDAO(database: CountryDatabase): CountryDAO {
        return database.countryDao()
    }
}