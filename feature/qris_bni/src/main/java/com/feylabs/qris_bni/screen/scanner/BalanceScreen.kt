package com.feylabs.qris_bni.screen.scanner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.feylabs.qris_bni.screen.history.TransactionHistoryViewModel

@Composable
fun BalanceScreen(balanceViewModel: TransactionHistoryViewModel) {
    val balanceState = balanceViewModel.balance.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Your Balance",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        BalanceComponent(balanceState.value)
    }
}

@Composable
fun BalanceComponent(balanceState: TransactionHistoryViewModel.BalanceState) {
    when {
        balanceState.amount.isEmpty() -> {
            // Show loading state or empty state
            Text(text = "Loading...", textAlign = TextAlign.Center)
        }
        balanceState.amount.isNotBlank() -> {
            // Show the balance amount
            Text(text = balanceState.amount, textAlign = TextAlign.Center)
        }
        else -> {
            // Show error state
            Text(text = "Error fetching balance", textAlign = TextAlign.Center)
        }
    }
}
