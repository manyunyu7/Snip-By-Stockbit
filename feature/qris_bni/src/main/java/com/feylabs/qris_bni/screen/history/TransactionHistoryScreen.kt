package com.feylabs.qris_bni.screen.history

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.feylabs.qris_bni.domain.uimodel.TransactionUiModel
import com.feylabs.qris_bni.ui.theme.SnipByStockbitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryScreen : ComponentActivity() {
    private val viewModel: TransactionHistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnipByStockbitTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TransactionList(viewModel = viewModel)
                }
            }
        }
    }

    @Composable
    fun TransactionList(viewModel: TransactionHistoryViewModel) {
        val transactionListState by viewModel.transactionListValue
            .collectAsState(initial = TransactionHistoryViewModel.TransactionListState()) // Provide initial state

        LaunchedEffect(Unit) {
            viewModel.fetchTransaction()
        }

        when {
            transactionListState.isLoading -> {
                Text("Loading, Please Wait")
            }
            transactionListState.transactionList.isNotEmpty() -> {
                LazyColumn {
                    items(transactionListState.transactionList) { transactionUiModel ->
                        TransactionCard(transaction = transactionUiModel)
                    }
                }
            }
            else -> {
            }
        }
    }

    @Composable
    fun TransactionCard(transaction: TransactionUiModel) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Merchant: ${transaction.merchantName}", color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Amount: $${transaction.transactionAmount}", color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Timestamp: ${transaction.timestamp}", color = Color.Black)
            }
        }
    }

}