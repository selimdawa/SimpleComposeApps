package com.flatcode.simplecomposeapps.pokemon.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.pokemon.data.database.PokeDatabase
import com.flatcode.simplecomposeapps.pokemon.data.database.dao.PokeDao
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
    fun providePokeDatabase(@ApplicationContext context: Context): PokeDatabase {
        return Room.databaseBuilder(
            context,
            PokeDatabase::class.java,
            "poke_db"
        ).build()
    }

    @Provides
    fun providePokeDao(database: PokeDatabase): PokeDao {
        return database.pokeDao()
    }
}