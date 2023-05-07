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
    private var _snipListValue = MutableStateFlow(SnipsListState())
    val snipListValue: StateFlow<SnipsListState> = _snipListValue

    class SnipsListState(
        val isLoading: Boolean = false,
        val snipList: List<SnipsUIModel> = emptyList<SnipsUIModel>(),
        var error: String = "",
        var toBeCleared: Boolean = false,
    )

    fun getSnip(lastId: Int? = null, categoryId: Int? = null, limit: Int? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            snipUseCase.getAllSnips(categoryId = categoryId, lastId = lastId, limit = limit)
                .collect {
                    when (it) {
                        is Loading -> {
                            _snipListValue.value = SnipsListState(
                                isLoading = true
                            )
                        }
                        is Success -> {
                            _snipListValue.value = SnipsListState(
                                snipList = it.data ?: emptyList()
                            )
                            _snipListValue.value.toBeCleared = it.toBeCleared
                        }
                        is Error -> {
                            _snipListValue.value = SnipsListState(
                                isLoading = false,
                                error = it.errorResponse?.errorMessage.toString()
                            )
                        }
                    }
                }
        }
    }

}

