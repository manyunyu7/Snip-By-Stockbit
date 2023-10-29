package com.feylabs.qris_bni.domain

import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.qris_bni.domain.interactor.QrisInteractor
import com.feylabs.qris_bni.domain.repository.QrisRepository
import com.feylabs.qris_bni.domain.uimodel.BalanceUiModel
import com.feylabs.qris_bni.domain.uimodel.TransactionUiModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class QrisInteractorTest {

    @Mock
    private lateinit var mockQrRepository: QrisRepository

    private lateinit var qrisInteractor: QrisInteractor

    object FakeTransactionData {

        fun generateFakeTransactionList(size: Int): List<TransactionUiModel> {
            val fakeTransactionList = mutableListOf<TransactionUiModel>()

            for (i in 1..size) {
                val merchantName = "Merchant $i"
                val transactionAmount = (i * 100).toDouble()  // Adjust as needed
                val timestamp = System.currentTimeMillis() + i * 1000  // Adjust as needed

                val fakeTransaction = TransactionUiModel(merchantName, transactionAmount, timestamp)
                fakeTransactionList.add(fakeTransaction)
            }

            return fakeTransactionList
        }
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        qrisInteractor = QrisInteractor(mockQrRepository)
    }

    @Test
    fun `test getAllTransaction`() = runBlocking {
        // Arrange
        val fakeTransactionList = FakeTransactionData.generateFakeTransactionList(100)
        `when`(mockQrRepository.getTransaction()).thenReturn(flowOf(ResponseState.Success(fakeTransactionList)))

        // Act
        val result = qrisInteractor.getAllTransaction().single()

        // Assert
        assertEquals(ResponseState.Success(fakeTransactionList.size).data, result.data?.size)
    }

    @Test
    fun `test addTransaction`() = runBlocking {
        // Arrange
        val merchantName = "SomeMerchant"
        val transactionAmount = 100.0
        val transactionType = "Purchase"
        val timestamp = System.currentTimeMillis()

        `when`(mockQrRepository.addTransaction(merchantName, transactionAmount, transactionType, timestamp))
            .thenReturn(flowOf(ResponseState.Success("Transaction added successfully")))

        // Act
        val result = qrisInteractor.addTransaction(merchantName, transactionAmount, transactionType, timestamp).single()

        // Assert
        assertEquals(ResponseState.Success("Transaction added successfully").data, result?.data)
    }

    @Test
    fun `test getBalance`() = runBlocking {
        // Arrange
        val fakeBalance = BalanceUiModel(currentBalance = 20000.0, timeStamp = System.currentTimeMillis())
        `when`(mockQrRepository.getBalance()).thenReturn(flowOf(ResponseState.Success(fakeBalance)))

        // Act
        val result = qrisInteractor.getBalance().single()

        // Assert
        assertEquals(ResponseState.Success(fakeBalance.currentBalance).data, result.data?.currentBalance)
    }
}