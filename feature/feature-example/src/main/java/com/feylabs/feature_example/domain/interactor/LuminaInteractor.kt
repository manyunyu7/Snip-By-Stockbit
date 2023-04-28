package com.feylabs.feature_example.domain.interactor

import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.feature_example.domain.repository.LuminaRepository
import com.feylabs.feature_example.domain.ui_model.LuminaUIModel
import com.feylabs.feature_example.domain.usecase.LuminaUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LuminaInteractor @Inject constructor(
    private val luminaRepository: LuminaRepository
) : LuminaUseCase {
    override suspend fun getLuminaList(
        limit: Int,
        page: Int
    ): Flow<ResponseState<List<LuminaUIModel>>> {
        return luminaRepository.getAllImage(limit, page)
    }

}