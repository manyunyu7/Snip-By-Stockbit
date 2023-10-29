package com.feylabs.qris_bni.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.qris_bni.domain.uimodel.TransactionUiModel
import com.feylabs.qris_bni.domain.usecase.QrUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class TransactionHistoryViewModel @Inject constructor(
    private val transactionUseCase: QrUseCase
) :
    ViewModel() {

    init {
        fetchTransaction()
    }

    private var _transactionListValue = MutableStateFlow(TransactionListState())
    val transactionListValue: StateFlow<TransactionListState> = _transactionListValue

    private var _addTransactionValue = MutableStateFlow(AddTransactionState())
    val addTransactionValue: StateFlow<AddTransactionState> = _addTransactionValue


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

