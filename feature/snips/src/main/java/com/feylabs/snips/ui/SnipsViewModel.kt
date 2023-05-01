package com.feylabs.snips.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feylabs.core.helper.wrapper.ResponseState.*
import com.feylabs.snips.domain.uimodel.SnipsUIModel
import com.feylabs.snips.domain.usecase.SnipsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SnipsViewModel @Inject constructor(private val snipUseCase: SnipsUseCase) : ViewModel() {
    private val snipListValue = MutableStateFlow(SnipsListState())
    var luminaListValue: StateFlow<SnipsListState> = snipListValue

    fun getImage(lastId: Int? = null, categoryId: Int? = null, limit: Int? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            snipUseCase.getAllSnips(lastId = lastId, categoryId = categoryId, limit = null)
                .collect {
                    when (it) {
                        is Loading -> {
                            snipListValue.value = SnipsListState(
                                isLoading = true
                            )
                        }
                        is Success -> {
                            snipListValue.value = SnipsListState(
                                coinList = it.data ?: emptyList()
                            )
                    }
                    is Error -> {
                        snipListValue.value = SnipsListState(
                            isLoading = false,
                            error = it.errorResponse?.errorMessage.toString()
                        )
                    }
                }
            }
        }

    }
}

class SnipsListState(
    val isLoading: Boolean = false,
    val coinList: List<SnipsUIModel> = emptyList<SnipsUIModel>(),
    var error: String = ""
)