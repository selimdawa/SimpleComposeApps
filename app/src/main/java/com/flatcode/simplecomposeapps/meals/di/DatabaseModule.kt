package com.flatcode.simplecomposeapps.meals.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.meals.db.MealDao
import com.flatcode.simplecomposeapps.meals.db.MealDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMealDatabase(@ApplicationContext context: Context): MealDatabase {
        return Room.databaseBuilder(
            context,
            MealDatabase::class.java,
            "meal_db"
        ).build()
    }

    @Provides
    fun provideMealDao(database: MealDatabase): MealDao {
        return database.mealDao()
    }
}