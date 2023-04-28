package com.feylabs.feature_example.domain.usecase

import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.feature_example.domain.ui_model.LuminaUIModel
import kotlinx.coroutines.flow.Flow

interface LuminaUseCase {
    suspend fun getLuminaList(limit: Int, page: Int): Flow<ResponseState<List<LuminaUIModel>>>
}