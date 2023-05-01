package com.feylabs.snips.di

import com.feylabs.unboxing.data.repository.SnipsRepositoryImpl
import com.feylabs.unboxing.domain.repository.SnipsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SnipsRepositoryModule {

    @Binds
    abstract fun bindRepository(luminaRepository: SnipsRepositoryImpl): SnipsRepository
}