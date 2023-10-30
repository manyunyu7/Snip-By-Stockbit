package com.feylabs.qris_bni.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.feylabs.qris_bni.data.source.local.dao.BalanceDao
import com.feylabs.qris_bni.data.source.local.dao.TransactionDao
import com.feylabs.qris_bni.data.source.local.entity.BalanceEntity
import com.feylabs.qris_bni.data.source.local.entity.TransactionEntity

@Database(
    entities = [BalanceEntity::class,TransactionEntity::class], version = 2
)
abstract class QrisDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun balanceDao(): BalanceDao
}