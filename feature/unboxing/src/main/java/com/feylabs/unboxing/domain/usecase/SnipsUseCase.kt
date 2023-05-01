package com.feylabs.unboxing.domain.usecase

import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.unboxing.domain.uimodel.SnipsUIModel
import kotlinx.coroutines.flow.Flow

interface SnipsUseCase {
    suspend fun getAllSnips(
        categoryId: Int?,
        lastId: Int?,
        limit: Int?
    ): Flow<ResponseState<List<SnipsUIModel>>>
}