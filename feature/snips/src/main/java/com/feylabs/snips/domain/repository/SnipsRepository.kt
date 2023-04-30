package com.feylabs.snips.domain.repository

import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.snips.domain.uimodel.SnipsUIModel
import kotlinx.coroutines.flow.Flow

interface SnipsRepository {
    fun getAllSnips(categoryId:Int?,lastId:Int?): Flow<ResponseState<List<SnipsUIModel>>>
}