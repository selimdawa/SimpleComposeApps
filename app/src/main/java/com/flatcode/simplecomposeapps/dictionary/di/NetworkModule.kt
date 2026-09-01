package com.flatcode.simplecomposeapps.dictionary.di

import com.flatcode.simplecomposeapps.dictionary.service.DictionaryAPI
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
annotation class DictionaryRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @DictionaryRetrofit
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DATA.DICTIONARY_BASIC_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryAPI(@DictionaryRetrofit retrofit: Retrofit): DictionaryAPI {
        return retrofit.create(DictionaryAPI::class.java)
    }
}