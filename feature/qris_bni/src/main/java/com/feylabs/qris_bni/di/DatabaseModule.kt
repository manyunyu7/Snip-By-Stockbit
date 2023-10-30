package com.feylabs.qris_bni.di

import android.content.Context
import androidx.room.Room
import com.feylabs.qris_bni.data.source.local.dao.BalanceDao
import com.feylabs.qris_bni.data.source.local.dao.TransactionDao
import com.feylabs.qris_bni.data.source.local.room.QrisDatabase
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
    fun provideSnipsDatabase(@ApplicationContext context: Context): QrisDatabase {
        return Room.databaseBuilder(context, QrisDatabase::class.java, "bni_qris")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideSnipsDao(database: QrisDatabase): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    fun provideBalanceDao(database: QrisDatabase): BalanceDao {
        return database.balanceDao()
    }
}