package com.feylabs.unboxing.ui

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
    private val _unboxingListValue = MutableStateFlow(LuminaListState())
    var unboxingListValue: StateFlow<LuminaListState> = _unboxingListValue

    fun geUnboxing(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            unboxingUseCase.getUnboxing(category).collect {
                when (it) {
                    is Loading -> {
                        _unboxingListValue.value = LuminaListState(
                            isLoading = true
                        )
                    }
                    is Success -> {
                        _unboxingListValue.value = LuminaListState(
                            coinList = it.data ?: emptyList()
                        )
                    }
                    is Error -> {
                        _unboxingListValue.value = LuminaListState(
                            isLoading = false,
                            error = it.errorResponse?.errorMessage.toString()
                        )
                    }
                }
            }
        }

    }
}

class LuminaListState(
    val isLoading: Boolean = false,
    val coinList: List<UnboxingListItemUIModel> = emptyList<UnboxingListItemUIModel>(),
    var error: String = ""
)