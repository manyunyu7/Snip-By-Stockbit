package com.feylabs.qris_bni.di

import com.feylabs.qris_bni.domain.interactor.QrisInteractor
import com.feylabs.qris_bni.domain.usecase.QrUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class QrisUseCaseModule {
    @Binds
    abstract fun useCaseProvider(useCase: QrisInteractor): QrUseCase
}