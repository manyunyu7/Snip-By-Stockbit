package com.feylabs.qris_bni.data.source.local.dao

import androidx.room.*
import com.feylabs.qris_bni.data.source.local.entity.BalanceEntity

@Dao
interface BalanceDao {

    @Transaction
    suspend fun setInitialBalance(userId: Int, initialBalance: Double) {
        val existingBalance = getBalance()
        val newBalance = BalanceEntity(userId, initialBalance)
        if (existingBalance!=null){
            if (existingBalance.saldo < 10.0) {
                // Only update the balance if it's null or 0
                insertOrUpdateBalance(newBalance)
            }
        }else{
            insertOrUpdateBalance(newBalance)
        }

    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateBalance(balance: BalanceEntity)

    @Query("SELECT * FROM balances")
    suspend fun getBalance(): BalanceEntity?

    @Insert
    suspend fun insertBalance(balance: BalanceEntity)

    @Update
    suspend fun updateBalance(balance: BalanceEntity)

    @Query("UPDATE balances SET saldo = saldo - :amount WHERE userId = :userId")
    suspend fun reduceBalance(userId: Int, amount: Double)

    @Query("UPDATE balances SET saldo = saldo + :amount WHERE userId = :userId")
    suspend fun addBalance(userId: Int, amount: Double)
}