package com.feylabs.feature_example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feylabs.core.helper.wrapper.ResponseState.*
import com.feylabs.feature_example.domain.ui_model.LuminaUIModel
import com.feylabs.feature_example.domain.usecase.LuminaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LuminaViewModel @Inject constructor(private val luminaUseCase: LuminaUseCase) : ViewModel() {
    private val _luminaListValue = MutableStateFlow(LuminaListState())
    var luminaListValue: StateFlow<LuminaListState> = _luminaListValue

    fun getImage(limit: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            luminaUseCase.getLuminaList(limit = limit, 0).collect {
                when (it) {
                    is Loading -> {
                        _luminaListValue.value = LuminaListState(
                            isLoading = true
                        )
                    }
                    is Success -> {
                        _luminaListValue.value = LuminaListState(
                            coinList = it.data ?: emptyList()
                        )
                    }
                    is Error -> {
                        _luminaListValue.value = LuminaListState(
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
    val coinList: List<LuminaUIModel> = emptyList<LuminaUIModel>(),
    var error: String = ""
)