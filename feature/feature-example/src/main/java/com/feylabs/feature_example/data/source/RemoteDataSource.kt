package com.feylabs.feature_example.data.source

import com.feylabs.feature_example.data.source.remote.dto.lumina_list_DTO.LuminaListDTO
import com.feylabs.feature_example.data.source.remote.service.LuminaApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val luminaApi: LuminaApi
) {
    suspend fun getLuminaList(limit: Int, page: Int): Response<LuminaListDTO> {
        return luminaApi.getLuminaList(limit)
    }
}