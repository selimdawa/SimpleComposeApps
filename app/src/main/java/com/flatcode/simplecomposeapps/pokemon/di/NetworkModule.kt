package com.flatcode.simplecomposeapps.pokemon.di

import com.flatcode.simplecomposeapps.pokemon.data.network.ApiService
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
annotation class PokeRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @PokeRetrofit
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DATA.BASE_URL_POKE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePokeApi(@PokeRetrofit retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}