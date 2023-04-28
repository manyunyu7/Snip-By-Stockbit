package com.feylabs.feature_example.data.di

import android.content.Context
import androidx.room.Room
import com.feylabs.feature_example.data.source.local.dao.LuminaDAO
import com.feylabs.feature_example.data.source.local.room.LuminaDatabase
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
    fun provideJavalovaDatabase(@ApplicationContext context: Context): LuminaDatabase {
        return Room.databaseBuilder(context, LuminaDatabase::class.java, "lumina-db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideLuminaDao(javalovaDatabase: LuminaDatabase): LuminaDAO {
        return javalovaDatabase.luminaDao()
    }
}