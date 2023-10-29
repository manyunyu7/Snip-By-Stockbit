package com.feylabs.qris_bni.data.source.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val transactionId: Int,
    val merchantName: String,
    val transactionAmount: Double,
    val transactionType: String,
    val timestamp: Long,
    val userId: Int // Assume a single user
)

