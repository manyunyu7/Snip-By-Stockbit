package com.feylabs.feature_example.domain.di

import com.feylabs.feature_example.domain.interactor.LuminaInteractor
import com.feylabs.feature_example.domain.usecase.LuminaUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class LuminaUseCaseModule {
    @Binds
    abstract fun authUseCaseProvider(useCase: LuminaInteractor): LuminaUseCase
}