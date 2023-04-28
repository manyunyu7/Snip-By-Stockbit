package com.feylabs.feature_example.domain.repository

import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.feature_example.domain.ui_model.LuminaUIModel
import kotlinx.coroutines.flow.Flow


interface LuminaRepository {
    suspend fun getAllImage(limit: Int, page: Int): Flow<ResponseState<List<LuminaUIModel>>>
}