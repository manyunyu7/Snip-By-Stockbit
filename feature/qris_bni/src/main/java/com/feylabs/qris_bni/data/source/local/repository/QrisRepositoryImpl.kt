package com.feylabs.qris_bni.data.source.local.repository

import com.feylabs.core.helper.error.ErrorResponse
import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.qris_bni.data.source.local.dao.BalanceDao
import com.feylabs.qris_bni.data.source.local.dao.TransactionDao
import com.feylabs.qris_bni.data.source.local.entity.TransactionEntity
import com.feylabs.qris_bni.data.source.local.mapper.Mapper.toTransactionUiModel
import com.feylabs.qris_bni.domain.repository.QrisRepository
import com.feylabs.qris_bni.domain.uimodel.BalanceUiModel
import kotlinx.coroutines.Dispatchers
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
                balanceDao.setInitialBalance(0,20000000.0)
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
                balanceDao.setInitialBalance(0,20000000.0)
                val data = transactionDao.getTransactionsByUserId().map {
                    it.toTransactionUiModel()
                }
                emit(ResponseState.Success(data))
            }catch (e:Exception){
                emit(ResponseState.Error(errorResponse = ErrorResponse(errorMessage = "Failed to fetch transactions: ${e.message}")))
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getBalance(): Flow<ResponseState<BalanceUiModel>> =
        flow {
            balanceDao.setInitialBalance(0,20000000.0)
            emit(ResponseState.Loading())
            try {
                val balance = balanceDao.getBalance()
                val balanceUiModel = BalanceUiModel(
                    currentBalance = balance?.saldo?:0.0,
                    timeStamp = System.currentTimeMillis()
                )
                emit(ResponseState.Success(balanceUiModel))
            }catch (e:Exception){
                emit(ResponseState.Error(errorResponse = ErrorResponse(errorMessage = "Failed to fetch transactions: ${e.message}")))
            }
        }.flowOn(Dispatchers.IO)



}



