package com.feylabs.feat_ui_home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.unboxing.domain.uimodel.UnboxingListItemUIModel
import com.feylabs.unboxing.domain.usecase.UnboxingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SnipsHomeViewModel @Inject constructor(private val unboxingUseCase: UnboxingUseCase) :
    ViewModel() {
    private val _unboxingListValue = MutableStateFlow(UnboxingSectoralListState())
    var unboxingListValue: StateFlow<UnboxingSectoralListState> = _unboxingListValue

    fun geUnboxing(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            unboxingUseCase.getUnboxing(category).collect {
                when (it) {
                    is ResponseState.Loading -> {
                        _unboxingListValue.value = UnboxingSectoralListState(
                            isLoading = true
                        )
                    }
                    is ResponseState.Success -> {
                        _unboxingListValue.value = UnboxingSectoralListState(
                            coinList = it.data ?: emptyList()
                        )
                    }
                    is ResponseState.Error -> {
                        _unboxingListValue.value = UnboxingSectoralListState(
                            isLoading = false,
                            error = it.errorResponse?.errorMessage.toString()
                        )
                    }
                }
            }
        }

    }
}

class UnboxingSectoralListState(
    val isLoading: Boolean = false,
    val coinList: List<UnboxingListItemUIModel> = emptyList<UnboxingListItemUIModel>(),
    var error: String = ""
)