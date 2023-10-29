package com.feylabs.qris_bni.domain.repository

import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.qris_bni.domain.uimodel.TransactionUiModel
import kotlinx.coroutines.flow.Flow

interface QrisRepository {
    fun addTransaction(
        merchantName:String,
        transactionAmount:Double,
        transactionType:String,
        timestamp:Long
    ): Flow<ResponseState<String>>

    fun getTransaction(
    ): Flow<ResponseState<List<TransactionUiModel>>>

}