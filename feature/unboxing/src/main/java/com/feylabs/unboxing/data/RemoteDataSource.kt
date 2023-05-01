package com.feylabs.unboxing.data

import com.feylabs.snips.data.source.remote.service.SnipsAPI
import com.feylabs.snips.di.SnipsModule
import com.feylabs.unboxing.data.source.remote.dto.SnipsResponseDTO
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    @SnipsModule.RetrofitSnips private val api: SnipsAPI
) {
    suspend fun getAllSnips(
        lastId: Int?,
        categoryId: Int?,
        limit: Int?
    ): Response<SnipsResponseDTO> {
        return api.getAllSnips(lastId, categoryId, limit)
    }
}