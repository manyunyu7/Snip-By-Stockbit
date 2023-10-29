package com.feylabs.qris_bni.di

import com.feylabs.qris_bni.data.source.local.repository.QrisRepositoryImpl
import com.feylabs.qris_bni.domain.repository.QrisRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class QrisRepositoryModule {

    @Binds
    abstract fun bindRepository(repo: QrisRepositoryImpl): QrisRepository
}