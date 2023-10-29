package com.feylabs.qris_bni.screen.scanner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.feylabs.qris_bni.screen.history.TransactionHistoryViewModel
import kotlinx.coroutines.delay

@Composable
fun BalanceScreen(balanceViewModel: TransactionHistoryViewModel) {
    // Collect balance state
    val balanceState by rememberUpdatedState(newValue = balanceViewModel.balance.collectAsState())

    // Fetch balance every second
    LaunchedEffect(Unit) {
        while (true) {
            balanceViewModel.fetchBalance()
            delay(1000) // Delay for 1 second
        }
    }

    // Display the UI
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Sisa Saldo",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        BalanceComponent(balanceState)
    }
}

@Composable
fun BalanceComponent(balanceState: State<TransactionHistoryViewModel.BalanceState>) {
    when {
        balanceState.value.amount.isEmpty() -> {
            // Show loading state
            Text(text = "Loading...", textAlign = TextAlign.Center)
        }
        balanceState.value.amount.isNotBlank() -> {
            // Show the balance amount
            Text(text = balanceState.value.amount, textAlign = TextAlign.Center)
        }
        else -> {
            // Show error state
        }
    }
}