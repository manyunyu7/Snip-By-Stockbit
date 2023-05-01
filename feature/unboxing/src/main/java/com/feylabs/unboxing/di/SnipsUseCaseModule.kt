package com.feylabs.snips.di

import com.feylabs.unboxing.domain.interactor.SnipsInteractor
import com.feylabs.unboxing.domain.usecase.SnipsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SnipsUseCaseModule {
    @Binds
    abstract fun snipUseCaseProvider(useCase: SnipsInteractor): SnipsUseCase
}