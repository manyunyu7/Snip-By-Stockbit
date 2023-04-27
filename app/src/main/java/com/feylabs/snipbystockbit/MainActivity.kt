package com.feylabs.snipbystockbit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.feylabs.core.helper.toast.ToastHelper.showToast
import com.feylabs.feature_example.FeatureExampleMainActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showToast("Hai Apa Kabar")

        startActivity(Intent(this, FeatureExampleMainActivity::class.java))
    }
}