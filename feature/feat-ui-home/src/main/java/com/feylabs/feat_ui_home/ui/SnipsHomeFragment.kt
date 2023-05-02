package com.feylabs.feat_ui_home.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.feylabs.core.base.BaseFragment
import com.feylabs.feat_ui_home.databinding.FragmentSnipsHomeBinding
import com.feylabs.uikit.listcomponent.uikitmodel.UnboxingSectoralUIKitModel
import com.feylabs.unboxing.domain.uimodel.UnboxingListItemUIModel
import com.feylabs.unboxing.ui.UnboxingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SnipsHomeFragment : BaseFragment<FragmentSnipsHomeBinding>(
    FragmentSnipsHomeBinding::inflate
) {

    val viewModel: UnboxingViewModel by viewModels()

    override fun initUI() {
        hideActionBar()
    }

    override fun initObserver() {
    }

    override fun initAction() {
    }

    override fun initData() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.unboxingStockListValue.collect { state ->
                when {
                    state.isLoading -> {
                        // Show loading progress
                    }
                    state.error.isNotBlank() -> {
                        // Show error message
                    }
                    state.unboxingList.isNotEmpty() -> {
                        binding.unboxingStock.addDatas(state.unboxingList.map {
                            it.toUnboxingSectoralUIKit()
                        })
                    }
                }
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            // Observe changes in the unboxing sectoral list state
            viewModel.unboxingSectoralListValue.collect { state ->
                when {
                    state.isLoading -> {
                        // Show loading progress
                    }
                    state.error.isNotBlank() -> {
                        // Show error message
                    }
                    state.unboxingList.isNotEmpty() -> {
                        val idsToFilter = listOf(34, 203, 4, 202, 199) // IDs to filter by
                        binding.unboxingSectoral.addDatas(state.unboxingList.map {
                            it.toUnboxingSectoralUIKit()
                        }.filter {
                            idsToFilter.contains(it.id)
                        }.sortedBy {
                            idsToFilter.indexOf(it.id)
                        })
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObserver()
        initAction()
        initData()
    }


    private fun UnboxingListItemUIModel.toUnboxingSectoralUIKit() =
        UnboxingSectoralUIKitModel(
            date = this.date,
            id = this.id ?: -99,
            description = this.description,
            image = this.imageUrl,
            title = this.title
        )


}

