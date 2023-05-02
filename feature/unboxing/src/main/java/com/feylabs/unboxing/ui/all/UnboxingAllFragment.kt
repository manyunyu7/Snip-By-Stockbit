package com.feylabs.unboxing.ui.all

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.feylabs.core.base.BaseFragment
import com.feylabs.core.helper.toast.ToastHelper.showToast
import com.feylabs.uikit.listcomponent.uikitmodel.UnboxingSectoralUIKitModel
import com.feylabs.unboxing.databinding.FragmentUnboxingHomeTestBinding
import com.feylabs.unboxing.domain.uimodel.UnboxingListItemUIModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UnboxingAllFragment : BaseFragment<FragmentUnboxingHomeTestBinding>(
    FragmentUnboxingHomeTestBinding::inflate
) {

    val viewModel: UnboxingViewModel by viewModels()

    override fun initUI() {
        hideActionBar()
    }

    override fun initObserver() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.unboxingStockListValue.combine(viewModel.unboxingSectoralListValue) { stockValue, sectoralValue ->
                stockValue to sectoralValue
            }.collect { (stockValue, sectoralValue) ->
                val unboxingList = mutableListOf<UnboxingListItemUIModel>()

                if (stockValue.isLoading || sectoralValue.isLoading) {
                    binding.pgLoading.visibility = View.VISIBLE
                } else if (stockValue.error.isNotBlank() || sectoralValue.error.isNotBlank()) {
                    // handle error
                    val errorMessage = if (stockValue.error.isNotBlank()) {
                        stockValue.error
                    } else {
                        sectoralValue.error
                    }
                    showToast(errorMessage)
                } else {
                    // handle success
                    unboxingList.addAll(stockValue.unboxingList)
                    unboxingList.addAll(sectoralValue.unboxingList)
                    // sort the list by id in descending order
                    unboxingList.sortByDescending { it.id }
                    binding.snipList.addDatas(unboxingList.map {
                        it.toUnboxingSectoralUIKitModel()
                    })
                }

                binding.pgLoading.visibility = View.GONE
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

    private fun UnboxingListItemUIModel.toUnboxingSectoralUIKitModel(): UnboxingSectoralUIKitModel {
        return UnboxingSectoralUIKitModel(
            feyCover = this.feycover,
            title = this.title,
            image = this.imageUrl,
            description = this.description,
            date = this.date,
            id = this.id ?: -99
        )
    }


}
