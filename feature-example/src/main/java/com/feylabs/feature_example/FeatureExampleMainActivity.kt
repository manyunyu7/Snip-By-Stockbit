package com.feylabs.feature_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.feylabs.core.base.BaseActivity
import com.feylabs.core.helper.snackbar.SnackbarHelper.showSnackbar
import com.feylabs.core.helper.toast.ToastHelper.showToast
import com.feylabs.feature_example.databinding.ActivityFeatureExampleMainBinding

class FeatureExampleMainActivity : BaseActivity<ActivityFeatureExampleMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnClickMe.setOnClickListener {
            showSnackbar(binding.root,"Halo Gais")
        }
    }
}