package com.feylabs.qris_bni.domain.usecase

import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.qris_bni.domain.uimodel.BalanceUiModel
import com.feylabs.qris_bni.domain.uimodel.TransactionUiModel
import kotlinx.coroutines.flow.Flow

interface QrUseCase {
    suspend fun getAllTransaction(
    ): Flow<ResponseState<List<TransactionUiModel>>>

    suspend fun addTransaction(
        merchantName:String,
        transactionAmount:Double,
        transactionType:String,
        timestamp:Long
    ): Flow<ResponseState<String>>

    suspend fun getBalance(
    ): Flow<ResponseState<BalanceUiModel>>


}