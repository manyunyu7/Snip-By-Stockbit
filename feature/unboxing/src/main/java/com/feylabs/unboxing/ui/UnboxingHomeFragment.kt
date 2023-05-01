package com.feylabs.unboxing.ui

import android.os.Bundle
import android.view.View
import com.feylabs.core.base.BaseFragment
import com.feylabs.unboxing.databinding.FragmentSnipsHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UnboxingHomeFragment : BaseFragment<FragmentSnipsHomeBinding>(
    FragmentSnipsHomeBinding::inflate
) {
    override fun initUI() {
        hideActionBar()
    }

    override fun initObserver() {
    }

    override fun initAction() {
    }

    override fun initData() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObserver()
        initAction()
        initData()
    }


}