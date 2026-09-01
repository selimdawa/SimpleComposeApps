package com.flatcode.simplecomposeapps.meals.di

import com.flatcode.simplecomposeapps.meals.retrofit.MealApi
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
annotation class MealRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @MealRetrofit
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DATA.BASE_URL_MEALS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMealApi(@MealRetrofit retrofit: Retrofit): MealApi {
        return retrofit.create(MealApi::class.java)
    }
}