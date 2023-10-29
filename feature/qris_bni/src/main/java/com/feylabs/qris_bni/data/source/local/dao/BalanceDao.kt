package com.feylabs.qris_bni.data.source.local.dao

import androidx.room.*
import com.feylabs.qris_bni.data.source.local.entity.BalanceEntity

@Dao
interface BalanceDao {
    @Query("SELECT * FROM balances WHERE userId = :userId")
    suspend fun getBalance(userId: Int): BalanceEntity

    @Insert
    suspend fun insertBalance(balance: BalanceEntity)

    @Update
    suspend fun updateBalance(balance: BalanceEntity)

    @Query("UPDATE balances SET saldo = saldo - :amount WHERE userId = :userId")
    suspend fun reduceBalance(userId: Int, amount: Double)

    @Query("UPDATE balances SET saldo = saldo + :amount WHERE userId = :userId")
    suspend fun addBalance(userId: Int, amount: Double)
}