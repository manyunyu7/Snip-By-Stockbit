package com.feylabs.snips.di

import com.feylabs.movie_genre.domain.interactor.MovieInteractor
import com.feylabs.movie_genre.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MovieUseCaseModule {
    @Binds
    abstract fun snipUseCaseProvider(useCase: MovieInteractor): MovieUseCase
}