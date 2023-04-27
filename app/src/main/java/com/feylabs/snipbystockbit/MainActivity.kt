package com.feylabs.snipbystockbit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.feylabs.core.helper.toast.showToast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showToast("Hai Apa Kabar")
    }
}