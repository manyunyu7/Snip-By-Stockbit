package com.feylabs.snips.di

import com.feylabs.movie_genre.data.repository.MovieRepositoryImpl
import com.feylabs.movie_genre.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieRepositoryModule {

    @Binds
    abstract fun bindRepository(luminaRepository: MovieRepositoryImpl): MovieRepository
}