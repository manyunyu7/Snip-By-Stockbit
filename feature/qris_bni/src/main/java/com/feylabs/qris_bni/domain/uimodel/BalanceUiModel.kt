package com.feylabs.qris_bni.domain.uimodel

class BalanceUiModel(
    val currentBalance: Double,
    val timeStamp: Long,
) {

    fun getFormattedRupiahBalance(): String {
        val formattedBalance = String.format("Rp%,.2f", currentBalance)
        return "$formattedBalance IDR"
    }
}