package com.feylabs.feature_example.data.source.remote.service

import com.feylabs.feature_example.data.source.remote.dto.lumina_list_DTO.LuminaListDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LuminaApi {

    @GET("gallery/random/{limit}")
    suspend fun getLuminaList(
        @Path("limit") limit: Int,
    ): Response<LuminaListDTO>

}