package com.feylabs.unboxing.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.feylabs.core.base.BaseFragment
import com.feylabs.core.helper.toast.ToastHelper.showToast
import com.feylabs.unboxing.databinding.FragmentUnboxingHomeTestBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UnboxingHomeTestFragment : BaseFragment<FragmentUnboxingHomeTestBinding>(
    FragmentUnboxingHomeTestBinding::inflate
) {

    val viewModel: UnboxingViewModel by viewModels()
    val mAdapter by lazy { UnboxingListAdapter() }

    override fun initUI() {
        hideActionBar()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        mAdapter.page = 1
        binding.rvLuminar.apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            this.adapter = mAdapter
        }
    }


    override fun initObserver() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.unboxingSectoralListValue.collect { value ->
                when {
                    value.isLoading -> {
                        binding.pgLoading.visibility = View.VISIBLE
                    }
                    value.error.isNotBlank() -> {
                        showToast(value.error)
                        binding.pgLoading.visibility = View.GONE
                    }
                    value.unboxingList.isNotEmpty() -> {
                        mAdapter.addData(value.unboxingList.toMutableList())
                        mAdapter.notifyDataSetChanged()
                        binding.pgLoading.visibility = View.GONE
                        showToast(mAdapter.data.count().toString())
                    }
                }
            }
        }
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