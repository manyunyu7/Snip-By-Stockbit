package com.feylabs.feature_example.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.feylabs.core.base.BaseFragment
import com.feylabs.core.helper.toast.ToastHelper.showToast
import com.feylabs.feature_example.databinding.FragmentLuminaHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LuminaHomeFragment : BaseFragment<FragmentLuminaHomeBinding>() {

    val viewModel: LuminaViewModel by viewModels()
    override fun initData() {
        viewModel.getImage(10)
    }

    val luminaAdapter by lazy { LuminaListAdapter() }

    override fun initObserver() {

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.luminaListValue.collect { value ->
                when {
                    value.isLoading -> {
                        binding.pgLoading.visibility = View.VISIBLE
                    }
                    value.error.isNotBlank() -> {
                        showToast(value.error)
                        binding.pgLoading.visibility = View.GONE
                    }
                    value.coinList.isNotEmpty() -> {
                        luminaAdapter.addData(value.coinList.toMutableList())
                        luminaAdapter.notifyDataSetChanged()
                        binding.pgLoading.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun initUI() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        luminaAdapter.page = 1
        binding.rvLuminar.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = luminaAdapter
        }
    }


    override fun initAction() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initData()
        initObserver()
        initAction()
    }
}