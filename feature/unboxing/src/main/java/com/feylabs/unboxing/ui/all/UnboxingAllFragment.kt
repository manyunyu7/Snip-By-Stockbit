package com.feylabs.unboxing.ui.all

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.feylabs.core.base.BaseFragment
import com.feylabs.core.helper.toast.ToastHelper.showToast
import com.feylabs.shared_dependencies.R
import com.feylabs.uikit.listcomponent.uikitmodel.UnboxingSectoralUIKitModel
import com.feylabs.uikit.listcomponent.unboxingstock.OnUnboxingStockListOnClickInterface
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
        binding.snipList.setClickInterface(object: OnUnboxingStockListOnClickInterface {
            override fun onClick(volume: String) {
                val itemId = volume
                val deeplink = NavDeepLinkRequest.Builder.fromUri(
                    Uri.parse(
                        getString(R.string.route_snips_content_viewer).replace(
                            "{contentId}",
                            itemId
                        ).replace(
                            "{contentType}",
                            "unboxing-stock"
                        )
                    )
                ).build()
                findNavController().navigate(deeplink)
            }
        })
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
            date = this.date,
            id = this.id ?: -99,
            description = this.description,
            image = this.imageUrl,
            title = this.title,
            feyCover = this.feycover,
            volume = this.volume.toString(),
            contentURL = this.url
        )
    }


}
