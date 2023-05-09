package com.feylabs.snips.ui.all

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.feylabs.core.base.BaseFragment
import com.feylabs.core.helper.view.ViewUtils.gone
import com.feylabs.core.helper.view.ViewUtils.visible
import com.feylabs.snips.databinding.FragmentSnipModuleTestBinding
import com.feylabs.uikit.listcomponent.snip.UIKitSnipList
import com.feylabs.uikit.listcomponent.uikitmodel.UnboxingSectoralUIKitModel
import com.feylabs.uikit.state.UIKitState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class AllSnipFragment : BaseFragment<FragmentSnipModuleTestBinding>(
    FragmentSnipModuleTestBinding::inflate
) {

    val viewModel: SnipsViewModel by viewModels()
    override fun initData() {
        binding.snipList.clear()
        lifecycleScope.launch {
            viewModel.getSnip(categoryId = 1, limit = 20, lastId = null)
        }
    }


    override fun initObserver() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.snipListValue.collect { value ->
                when {
                    value.isLoading -> {
                        binding.emptyState.gone()
                        binding.snipList.setUiState(UIKitState.LOADING)
                    }
                    value.error.isNotBlank() -> {
                        binding.emptyState.gone()
                        binding.snipList.setUiState(UIKitState.ERROR)
                    }
                    value.isEmpty -> {
                        binding.emptyState.visible()
                        binding.snipList.setUiState(UIKitState.EMPTY)
                    }
                    value.snipList.isNotEmpty() -> {
                        binding.emptyState.gone()
                        if (value.toBeCleared) {
                            binding.snipList.clear()
                        }
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
                        binding.snipList.setUiState(UIKitState.SUCCESS)
                    }
                }
            }
        }
    }

    override fun initUI() {
        binding.snipList.loadMoreListener = object : UIKitSnipList.LoadMoreListener {
            override fun onLoadMore(lastId: Int, calledId: String, isCalled: Boolean) {
                if (isCalled.not()) {
                    viewModel.getSnip(lastId, 1, 20)
                    Timber.d("168_ $lastId calledId : ${calledId.toString()} + $isCalled")
                } else {
                    Timber.d("1688_ $lastId calledId : ${calledId.toString()} + $isCalled")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.snipList.clear()
    }


    override fun initAction() {
        binding.toolbar.setOnClickListener {
            binding.snipList.clear()
        }

        binding.btnRefresh.setOnClickListener {
            initData()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initData()
        initObserver()
        initAction()
    }
}