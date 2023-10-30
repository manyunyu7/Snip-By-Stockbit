package com.feylabs.qris_bni.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.qris_bni.domain.uimodel.TransactionUiModel
import com.feylabs.qris_bni.domain.usecase.QrUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TransactionHistoryViewModel @Inject constructor(
    private val transactionUseCase: QrUseCase
) :
    ViewModel() {

    init {
        fetchTransaction()
        fetchBalance()
    }

    private var _transactionListValue = MutableStateFlow(TransactionListState())
    val transactionListValue: StateFlow<TransactionListState> = _transactionListValue

    private var _addTransactionValue = MutableStateFlow(AddTransactionState())
    val addTransactionValue: StateFlow<AddTransactionState> = _addTransactionValue

    private var _balance = MutableStateFlow(BalanceState(amount = ""))
    val balance: StateFlow<BalanceState> = _balance

    class BalanceState(
        var amount: String = "",
    )

    class AddTransactionState(
        val isLoading: Boolean = false,
        var error: String = "",
        var success: String = ""
    )
    class TransactionListState(
        val isLoading: Boolean = false,
        val transactionList: List<TransactionUiModel> = emptyList<TransactionUiModel>(),
        var error: String = ""
    )


    fun fetchBalance(){
        viewModelScope.launch {
            transactionUseCase.getBalance(
            ).collect {
                when (it) {
                    is ResponseState.Loading -> _balance.value = BalanceState(amount ="")
                    is ResponseState.Success -> _balance.value = BalanceState(amount =it.data?.getFormattedRupiahBalance().orEmpty())
                    is ResponseState.Error ->{
                        _balance.value = BalanceState(amount ="")
                    }
                }
            }
        }
    }

    fun fetchTransaction() {
        viewModelScope.launch {
            transactionUseCase.getAllTransaction().collect {
                when (it) {
                    is ResponseState.Loading -> {
                        _transactionListValue.value = TransactionListState(isLoading = true)
                    }

                    is ResponseState.Success -> {
                        _transactionListValue.value = TransactionListState(isLoading = false, transactionList = it.data.orEmpty())
                    }

                    is ResponseState.Error -> {
                        _transactionListValue.value = TransactionListState(isLoading = false, transactionList = it.data.orEmpty())
                    }

                    else -> {}
                }
            }
        }
    }

    fun addTransaction(merchantName:String, transactionAmount:Double){
        viewModelScope.launch {
            transactionUseCase.addTransaction(
                merchantName = merchantName,
                transactionAmount = transactionAmount,
                transactionType = "QR",
                timestamp = System.currentTimeMillis()
            ).collect {
                when (it) {
                    is ResponseState.Loading -> _addTransactionValue.value = AddTransactionState(isLoading = true)
                    is ResponseState.Success -> _addTransactionValue.value = AddTransactionState(success = "y", isLoading = false)
                    is ResponseState.Error ->{
                        _addTransactionValue.value = AddTransactionState(error = "x", isLoading = false)
                    }
                }
            }
        }
    }


}

