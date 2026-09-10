package com.flatcode.simplecomposeapps.movies.di

import com.flatcode.simplecomposeapps.movies.db.MoviesRepository
import com.flatcode.simplecomposeapps.movies.db.MoviesRepositoryRealization
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
    abstract fun bindMoviesRepository(
        moviesRepositoryRealization: MoviesRepositoryRealization
    ): MoviesRepository
}