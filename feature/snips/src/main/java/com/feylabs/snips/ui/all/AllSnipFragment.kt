package com.feylabs.snips.ui.all

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.feylabs.core.base.BaseFragment
import com.feylabs.core.helper.toast.ToastHelper.showToast
import com.feylabs.snips.databinding.FragmentSnipModuleTestBinding
import com.feylabs.snips.ui.SnipsViewModel
import com.feylabs.uikit.listcomponent.uikitmodel.UnboxingSectoralUIKitModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AllSnipFragment : BaseFragment<FragmentSnipModuleTestBinding>(
    FragmentSnipModuleTestBinding::inflate
) {

    val viewModel: SnipsViewModel by viewModels()
    override fun initData() {
        viewModel.getSnip(categoryId = 2, limit = 10, lastId = null)
    }


    override fun initObserver() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.snipListValue.collect { value ->
                when {
                    value.isLoading -> {
                        binding.pgLoading.visibility = View.VISIBLE
                    }
                    value.error.isNotBlank() -> {
                        showToast(value.error)
                        binding.pgLoading.visibility = View.GONE
                    }
                    value.snipList.isNotEmpty() -> {
                        binding.snipList.addDatas(value.snipList.map {
                            UnboxingSectoralUIKitModel(
                                id = it.id ?: -99,
                                date = it.created,
                                description = "",
                                image = it.imageUrl,
                                title = it.title,
                                feyCover = it.feyCover.orEmpty()
                            )
                        })
                        binding.pgLoading.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun initUI() {
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