package com.feylabs.feature_example.data.repository

import android.net.ConnectivityManager
import com.feylabs.core.helper.error.ErrorResponse
import com.feylabs.core.helper.error.ResponseExceptionHandler
import com.feylabs.core.helper.network.NetworkInfo.isOnline
import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.feature_example.data.source.RemoteDataSource
import com.feylabs.feature_example.domain.repository.LuminaRepository
import com.feylabs.feature_example.domain.ui_model.LuminaUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LuminaRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val connectivityManager: ConnectivityManager
) : LuminaRepository {

    override suspend fun getAllImage(
        limit: Int,
        page: Int
    ): Flow<ResponseState<List<LuminaUIModel>>> {
        return flow {
            emit(ResponseState.Loading())
            if (isOnline(connectivityManager)) {
                val res = remoteDataSource.getLuminaList(limit, page)
                try {
                    if (res.isSuccessful) {
                        res.body()?.let {
                            val images: List<LuminaUIModel> = it.data.images.map {
                                it.toLuminaUIModel()
                            }
                            emit(ResponseState.Success(images))
                        }
                    } else {
                        emit(ResponseState.Error())
                    }
                } catch (e: Exception) {
                    ResponseExceptionHandler.handleException(e, this)
                }
            } else {
                emit(ResponseState.Error(ErrorResponse(errorMessage = "Tidak Ada Koneksi Internet")))
            }
        }
    }

}