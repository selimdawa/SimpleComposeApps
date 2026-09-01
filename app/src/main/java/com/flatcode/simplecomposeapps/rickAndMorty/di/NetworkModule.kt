package com.flatcode.simplecomposeapps.rickAndMorty.di

import com.flatcode.simplecomposeapps.rickAndMorty.data.remote.ApiService
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RickAndMortyRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @RickAndMortyRetrofit
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DATA.BASE_URL_RICK_AND_MORTY)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(@RickAndMortyRetrofit retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}