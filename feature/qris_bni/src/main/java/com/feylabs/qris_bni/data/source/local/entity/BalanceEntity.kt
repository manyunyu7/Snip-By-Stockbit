package com.feylabs.qris_bni.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "balances")
data class BalanceEntity(
    @PrimaryKey val userId: Int,
    val saldo: Double
)