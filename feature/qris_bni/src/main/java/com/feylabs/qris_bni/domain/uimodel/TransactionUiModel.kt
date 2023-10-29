package com.feylabs.qris_bni.domain.uimodel

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionUiModel(
    val merchantName: String,
    val transactionAmount: Double,
    val timestamp: Long,
) {
    fun getFormattedRupiahBalance(): String {
        val formattedBalance = String.format("Rp%,.2f", transactionAmount)
        return "$formattedBalance IDR"
    }

    fun getFormattedTime(): String {
        // Convert timestamp to Date
        val date = Date(timestamp)

        // Define the desired date format
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())

        // Format the date and return the result
        return dateFormat.format(date)
    }
}