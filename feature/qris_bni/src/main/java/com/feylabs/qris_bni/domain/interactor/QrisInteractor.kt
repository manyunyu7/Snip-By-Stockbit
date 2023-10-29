package com.feylabs.qris_bni.domain.interactor

import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.qris_bni.domain.repository.QrisRepository
import com.feylabs.qris_bni.domain.uimodel.TransactionUiModel
import com.feylabs.qris_bni.domain.usecase.QrUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QrisInteractor @Inject constructor(
    private val qrRepository: QrisRepository
) : QrUseCase {

    override suspend fun getAllTransaction(): Flow<ResponseState<List<TransactionUiModel>>> {
        return qrRepository.getTransaction()
    }

    override suspend fun addTransaction(
        merchantName: String,
        transactionAmount: Double,
        transactionType: String,
        timestamp: Long
    ): Flow<ResponseState<String>> {
        return qrRepository.addTransaction(merchantName, transactionAmount, transactionType, timestamp)
    }

}