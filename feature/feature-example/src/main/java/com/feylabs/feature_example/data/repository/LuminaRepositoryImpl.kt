package com.feylabs.feature_example.data.repository

import android.net.ConnectivityManager
import com.feylabs.core.helper.error.ErrorResponse
import com.feylabs.core.helper.error.ResponseExceptionHandler
import com.feylabs.core.helper.network.NetworkInfo.isOnline
import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.feature_example.data.source.RemoteDataSource
import com.feylabs.feature_example.data.source.local.dao.LuminaDAO
import com.feylabs.feature_example.di.LuminaModule.ConnectivityManagerLumina
import com.feylabs.feature_example.domain.repository.LuminaRepository
import com.feylabs.feature_example.domain.ui_model.LuminaUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LuminaRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @ConnectivityManagerLumina private val connectivityManager: ConnectivityManager,
    private val localDatabase: LuminaDAO
) : LuminaRepository {

    override suspend fun getAllImage(
        limit: Int,
        page: Int
    ): Flow<ResponseState<List<LuminaUIModel>>> = flow<ResponseState<List<LuminaUIModel>>> {
        emit(ResponseState.Loading())

        if (isOnline(connectivityManager)) {
            try {
                val response = remoteDataSource.getLuminaList(limit, page)
                if (response.isSuccessful) {
                    val images = response.body()?.data?.images.orEmpty()
                    val entityModels = images.map { it.toLuminaItemEntity() }
                    localDatabase.apply {
                        deleteAll()
                        insertAll(entityModels)
                        emit(ResponseState.Success(getCachedAllImage()))
                    }
                } else {
                    emit(ResponseState.Error())
                }
            } catch (e: Exception) {
                ResponseExceptionHandler.handleException(e, this)
            }
        } else {
            val errorMessage = "Tidak Ada Koneksi Internet"
            if (getCachedAllImage().isNotEmpty()) {
                emit(ResponseState.Error(ErrorResponse(errorMessage = errorMessage)))
                delay(1000)
                emit(ResponseState.Success(getCachedAllImage()))
            } else {
                emit(ResponseState.Error(ErrorResponse(errorMessage = errorMessage)))
            }
        }
    }.flowOn(Dispatchers.IO)

    private fun getCachedAllImage(): List<LuminaUIModel> {

        var localData = localDatabase.getAll()?.map {
            it.toLuminaUIModel()
        }
        if (localData?.isEmpty()?.not() == true) {
            localData = localDatabase.getAll()?.map {
                it.toLuminaUIModel()
            }
        }

        return localData ?: emptyList()

    }
}