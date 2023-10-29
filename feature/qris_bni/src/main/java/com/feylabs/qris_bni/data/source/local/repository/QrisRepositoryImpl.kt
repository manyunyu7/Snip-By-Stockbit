package com.feylabs.qris_bni.data.source.local.repository

import android.net.ConnectivityManager
import com.feylabs.core.helper.error.ErrorResponse
import com.feylabs.core.helper.error.ResponseExceptionHandler
import com.feylabs.core.helper.network.NetworkInfo
import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.qris_bni.data.source.local.dao.BalanceDao
import com.feylabs.qris_bni.data.source.local.dao.TransactionDao
import com.feylabs.qris_bni.data.source.local.entity.TransactionEntity
import com.feylabs.qris_bni.data.source.local.mapper.Mapper.toTransactionUiModel
import com.feylabs.qris_bni.domain.repository.QrisRepository
import com.feylabs.qris_bni.domain.uimodel.TransactionUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

class QrisRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val balanceDao: BalanceDao,
) : QrisRepository {
    override fun addTransaction(
        merchantName: String,
        transactionAmount: Double,
        transactionType: String,
        timestamp: Long
    ): Flow<ResponseState<String>> {
        return flow {
            try {
                Timber.d("12: trying")
                val transactionId = UUID.randomUUID()
                val userId = 1 // Example user ID

                // Create a new TransactionEntity
                val transaction = TransactionEntity(
                    transactionId = transactionId,
                    merchantName = merchantName,
                    transactionAmount = transactionAmount,
                    transactionType = transactionType,
                    timestamp = timestamp,
                    userId = userId
                )

                // Insert the transaction into the database
                transactionDao.insertTransaction(transaction)

                emit(ResponseState.Success("Transaction added successfully"))
            } catch (e: Exception) {
                Timber.d("12: errror $e")
                emit(ResponseState.Error(ErrorResponse(errorMessage = "Failed to add transaction: ${e.message}")))
            }
        }
    }

    override fun getTransaction() =
        flow {
            emit(ResponseState.Loading())
            try {
                val data = transactionDao.getTransactionsByUserId().map {
                    it.toTransactionUiModel()
                }
                emit(ResponseState.Success(data))
            }catch (e:Exception){
                emit(ResponseState.Error(errorResponse = ErrorResponse(errorMessage = "Failed to fetch transactions: ${e.message}")))
            }
        }.flowOn(Dispatchers.IO)


}



