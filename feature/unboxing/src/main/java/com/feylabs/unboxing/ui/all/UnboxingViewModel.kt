package com.feylabs.unboxing.ui.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feylabs.core.helper.wrapper.ResponseState.*
import com.feylabs.unboxing.domain.uimodel.UnboxingListItemUIModel
import com.feylabs.unboxing.domain.usecase.UnboxingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UnboxingViewModel @Inject constructor(private val unboxingUseCase: UnboxingUseCase) :
    ViewModel() {

    private val _unboxingSectoralListValue = MutableStateFlow(UnboxingState())
    val unboxingSectoralListValue: StateFlow<UnboxingState> = _unboxingSectoralListValue

    private val _unboxingStockListValue = MutableStateFlow(UnboxingState())
    val unboxingStockListValue: StateFlow<UnboxingState> = _unboxingStockListValue

    init {
        fetchData()
    }

    fun fetchData() {
        fetchUnboxingData("sectoral", _unboxingSectoralListValue)
        fetchUnboxingData("stock", _unboxingStockListValue)
    }

    private fun fetchUnboxingData(type: String, state: MutableStateFlow<UnboxingState>) {
        viewModelScope.launch(Dispatchers.IO) {
            unboxingUseCase.getUnboxing(type).collect {
                when (it) {
                    is Loading -> state.value = UnboxingState(isLoading = true)
                    is Success -> state.value = UnboxingState(unboxingList = it.data ?: emptyList())
                    is Error -> state.value = UnboxingState(
                        isLoading = false,
                        error = it.errorResponse?.errorMessage.toString()
                    )
                }
            }
        }
    }
}

data class UnboxingState(
    val isLoading: Boolean = false,
    val unboxingList: List<UnboxingListItemUIModel> = emptyList(),
    val error: String = ""
)