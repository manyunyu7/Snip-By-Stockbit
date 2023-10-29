package com.feylabs.qris_bni.data.source.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Keep
@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val transactionId: UUID,
    val merchantName: String,
    val transactionAmount: Double,
    val transactionType: String,
    val timestamp: Long,
    val userId: Int // Assume a single user
)