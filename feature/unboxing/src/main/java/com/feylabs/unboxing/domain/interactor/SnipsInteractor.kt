package com.feylabs.unboxing.domain.interactor

import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.unboxing.domain.repository.SnipsRepository
import com.feylabs.unboxing.domain.uimodel.SnipsUIModel
import com.feylabs.unboxing.domain.usecase.SnipsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SnipsInteractor @Inject constructor(
    private val snipsRepository: SnipsRepository
) : SnipsUseCase {

    override suspend fun getAllSnips(
        categoryId: Int?,
        lastId: Int?,
        limit: Int?
    ): Flow<ResponseState<List<SnipsUIModel>>> {
        return snipsRepository.getAllSnips(categoryId, lastId, limit)
    }

}