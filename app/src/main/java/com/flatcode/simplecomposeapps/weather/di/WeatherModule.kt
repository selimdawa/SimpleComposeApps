package com.flatcode.simplecomposeapps.weather.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.flatcode.simplecomposeapps.weather.db.WeatherDao
import com.flatcode.simplecomposeapps.weather.db.WeatherDatabase
import com.flatcode.simplecomposeapps.weather.model.WeatherRepository
import com.flatcode.simplecomposeapps.weather.network.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WeatherPrefs

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, WeatherDatabase::class.java, "weather.db").build()

    @Provides
    fun provideDao(db: WeatherDatabase) = db.dao()

    @WeatherPrefs
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideWeatherRepository(
        dao: WeatherDao,
        api: WeatherApi,
        @WeatherPrefs prefs: SharedPreferences
    ): WeatherRepository = WeatherRepository(dao, api, prefs)
}