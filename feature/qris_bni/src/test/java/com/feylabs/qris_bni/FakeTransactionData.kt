package com.feylabs.qris_bni

import com.feylabs.qris_bni.domain.uimodel.TransactionUiModel

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
