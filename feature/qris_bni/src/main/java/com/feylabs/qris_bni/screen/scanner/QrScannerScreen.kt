package com.feylabs.qris_bni.screen.scanner

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.feylabs.core.helper.toast.ToastHelper
import com.feylabs.core.helper.toast.ToastHelper.showToast
import com.feylabs.qris_bni.ui.theme.SnipByStockbitTheme
import com.feylabs.qris_bni.R
import com.feylabs.qris_bni.screen.history.HistoryScreen
import com.feylabs.qris_bni.screen.history.TransactionHistoryViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QrScannerScreen : ComponentActivity() {

    private val viewModel: TransactionHistoryViewModel by viewModels()

    private var textResult = mutableStateOf("")
    private var balance = mutableStateOf("")
    private var barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            showToast("Cancelled")
        } else {
            textResult.value = result.contents.orEmpty()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                showCamera()
            }
        }

    private fun showCamera() {
        try {
            val options = ScanOptions()
            options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            options.setPrompt("Scan a QRIS for doing a transaction")
            options.setCameraId(0)
            options.setBeepEnabled(true)
            options.setOrientationLocked(true)
            // Launch the scanner with the specified options
            barcodeLauncher.launch(options)
        } catch (e: Exception) {
            ToastHelper.showToast(applicationContext, e.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnipByStockbitTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    bottomBar = {
                        BottomAppBar(
                            actions = {
                                // Add the button for showing history here
                                IconButton(onClick = {
                                    startActivity(Intent(this@QrScannerScreen,HistoryScreen::class.java))
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.History,
                                        contentDescription = "Show History"
                                    )
                                }
                            },
                            floatingActionButton = {
                                FloatingActionButton(onClick = {
                                    checkCameraPermission(this@QrScannerScreen)
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.qrscan),
                                        contentDescription = "QR Scan"
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.qrscan),
                            modifier = Modifier.size(100.dp),
                            contentDescription = "QR"
                        )
                        BalanceScreen(balanceViewModel = viewModel)
                        Text(
                            text = textResult.value,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(horizontal = 16.dp) // Horizontal padding
                                .padding(top = 8.dp)          // Top margin
                        )
                        DisplayQrInfo(textResult.value)
                    }
                }
            }
        }
    }

    @Composable
    fun DisplayQrInfo(qrString: String) {
        val qrInfo = parseQrString(qrString)
        var isConfirmationDialogVisible by remember { mutableStateOf(false) }

        if (qrInfo.bankSumber.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Bank Sumber: ${qrInfo.bankSumber}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "ID Transaksi: ${qrInfo.idTransaksi}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Nama Merchant: ${qrInfo.namaMerchant}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Nominal Transaksi: ${qrInfo.nominalTransaksi}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(){
                    Button(
                        onClick = {
                            isConfirmationDialogVisible = true
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(text = "Confirm Transaction")
                    }
                }

                if (isConfirmationDialogVisible) {
                    TransactionConfirmationDialog(
                        onConfirm = {
                            // Perform the transaction confirmation logic here
                            showSuccessNotification(applicationContext)
                            isConfirmationDialogVisible = false
                            textResult.value = ""
                            viewModel.addTransaction(
                                merchantName = qrInfo.namaMerchant,
                                transactionAmount = qrInfo.nominalTransaksi.toDoubleOrNull() ?: 0.0
                            )
                        },
                        onDismiss = {
                            isConfirmationDialogVisible = false
                        }
                    )
                }
            }
        } else {
            Text(
                text = "Scan a QRIS for transaction information",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    data class QrInfo(
        val bankSumber: String,
        val idTransaksi: String,
        val namaMerchant: String,
        val nominalTransaksi: String
    )

    fun parseQrString(qrString: String): QrInfo {
        val parts = qrString.split(".")
        return QrInfo(
            bankSumber = parts.getOrNull(0).orEmpty(),
            idTransaksi = parts.getOrNull(1).orEmpty(),
            namaMerchant = parts.getOrNull(2).orEmpty(),
            nominalTransaksi = parts.getOrNull(3).orEmpty()
        )
    }

    private fun checkCameraPermission(qrScannerScreen: Context) {
        if (ContextCompat.checkSelfPermission(
                qrScannerScreen,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            showCamera()
        } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
            showToast("Camera Required")
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    @Composable
    fun TransactionConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
        var isConfirmed by remember { mutableStateOf(false) }

        if (!isConfirmed) {
            AlertDialog(
                onDismissRequest = {
                    onDismiss.invoke()
                },
                title = { Text("Transaction Confirmation") },
                text = { Text("Do you want to confirm this transaction?") },
                confirmButton = {
                    Button(onClick = {
                        isConfirmed = true
                        onConfirm.invoke()
                    }) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        onDismiss.invoke()
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }

    fun showSuccessNotification(context: Context) {
        // Implement your logic to show a success notification here
        viewModel.fetchBalance()
        ToastHelper.showToast(context, "Transaction successful!")
    }
}

