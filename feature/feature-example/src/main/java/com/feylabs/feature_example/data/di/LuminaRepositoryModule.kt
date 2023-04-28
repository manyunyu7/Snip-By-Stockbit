package com.feylabs.feature_example.data.di

import com.feylabs.feature_example.data.repository.LuminaRepositoryImpl
import com.feylabs.feature_example.domain.repository.LuminaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LuminaRepositoryModule {

    @Binds
    abstract fun bindLuminaRepository(luminaRepository: LuminaRepositoryImpl): LuminaRepository
}