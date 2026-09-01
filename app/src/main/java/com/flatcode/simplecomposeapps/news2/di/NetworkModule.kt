package com.flatcode.simplecomposeapps.news2.di

import com.flatcode.simplecomposeapps.news2.data.remote.ApiKeyInterceptor
import com.flatcode.simplecomposeapps.news2.data.remote.NewsApiServices
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @NewsLoggingInterceptor
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(): ApiKeyInterceptor {
        return ApiKeyInterceptor()
    }

    @NewsOkHttpClient
    @Provides
    @Singleton
    fun provideOkHttpClient(
        @NewsLoggingInterceptor loggingInterceptor: HttpLoggingInterceptor,
        apiKeyInterceptor: ApiKeyInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @NewsRetrofit
    @Provides
    @Singleton
    fun provideRetrofit(@NewsOkHttpClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DATA.BASE_URL_NEWS)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApi(@NewsRetrofit retrofit: Retrofit): NewsApiServices {
        return retrofit.create(NewsApiServices::class.java)
    }
}