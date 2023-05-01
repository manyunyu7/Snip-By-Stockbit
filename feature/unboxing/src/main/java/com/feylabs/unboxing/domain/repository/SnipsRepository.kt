package com.feylabs.unboxing.domain.repository

import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.unboxing.domain.uimodel.SnipsUIModel
import kotlinx.coroutines.flow.Flow

interface SnipsRepository {
    fun getAllSnips(
        categoryId: Int?,
        lastId: Int?,
        limit: Int?
    ): Flow<ResponseState<List<SnipsUIModel>>>
}