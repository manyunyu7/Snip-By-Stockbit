package com.feylabs.qris_bni.data.source.local.mapper

import com.feylabs.qris_bni.data.source.local.entity.TransactionEntity
import com.feylabs.qris_bni.domain.uimodel.TransactionUiModel


object Mapper {


    fun TransactionEntity.toTransactionUiModel() : TransactionUiModel{
        return TransactionUiModel(
            merchantName = this.merchantName,
            timestamp = this.timestamp,
            transactionAmount = this.transactionAmount
        )
    }

}