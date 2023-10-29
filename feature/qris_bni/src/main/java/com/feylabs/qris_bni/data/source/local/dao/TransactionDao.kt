package com.feylabs.qris_bni.data.source.local.dao

import androidx.room.*
import com.feylabs.qris_bni.data.source.local.entity.TransactionEntity

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    suspend fun getTransactionsByUserId(): List<TransactionEntity>

    @Insert
    suspend fun insertTransaction(transaction: TransactionEntity)
}