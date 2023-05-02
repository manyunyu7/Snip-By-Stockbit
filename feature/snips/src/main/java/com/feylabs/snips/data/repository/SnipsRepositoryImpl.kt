package com.feylabs.snips.data.repository

import android.net.ConnectivityManager
import com.feylabs.core.helper.error.ErrorResponse
import com.feylabs.core.helper.error.ResponseExceptionHandler
import com.feylabs.core.helper.network.NetworkInfo
import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.snips.data.RemoteDataSource
import com.feylabs.snips.data.mapper.Mapper.toSnipsEntity
import com.feylabs.snips.data.mapper.Mapper.toSnipsUIModel
import com.feylabs.snips.data.source.local.dao.SnipsDAO
import com.feylabs.snips.di.SnipsModule.ConnectivityManagerSnips
import com.feylabs.snips.domain.repository.SnipsRepository
import com.feylabs.snips.domain.uimodel.SnipsUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SnipsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @ConnectivityManagerSnips private val connectivityManager: ConnectivityManager,
    private val localDatabase: SnipsDAO
) : SnipsRepository {

    override fun getAllSnips(categoryId: Int?, lastId: Int?, limit: Int?) =
        flow<ResponseState<List<SnipsUIModel>>> {
            emit(ResponseState.Loading())
            if (NetworkInfo.isOnline(connectivityManager)) {
                try {
                    val response = remoteDataSource.getAllSnips(lastId, categoryId, limit)
                    if (response.isSuccessful) {
                        val entityModels = response.body()?.data?.map { it.toSnipsEntity() }
                        localDatabase.apply {
                            deleteAll()
                            entityModels?.apply {
                                insertAll(entityModels)
                            }
                            emit(ResponseState.Success(getCachedSnips()))
                        }
                    } else {
                        emit(ResponseState.Error())
                    }
                } catch (e: Exception) {
                    ResponseExceptionHandler.handleException(e, this)
                }
            } else {
                val errorMessage = "Tidak Ada Koneksi Internet"
                if (getCachedSnips().isNotEmpty()) {
                    emit(ResponseState.Error(ErrorResponse(errorMessage = errorMessage)))
                    delay(1000)
                    emit(ResponseState.Success(getCachedSnips()))
                } else {
                    emit(ResponseState.Error(ErrorResponse(errorMessage = errorMessage)))
                }
            }
        }.flowOn(Dispatchers.IO)

//    override fun getSnipsCache(
//        lastId: Int?, categoryId: Int?, limit: Int?
//    ): Flow<ResponseState<List<SnipsUIModel>>> {
//        return flow<ResponseState<List<SnipsUIModel>>> {
//            emit(ResponseState.Loading())
////            val categoryString = getCategoryLabelFromNumber(categoryId)
////            val dataUI = mutableListOf<SnipsUIModel>()
////
////            if (getCachedSnips().isEmpty()) {
////                getAllSnips(null, null, null)
////            }
////
////            if (categoryString == "all") {
////                dataUI.addAll(localDatabase.getAll()?.map {
////                    it.toSnipsUIModel()
////                } ?: listOf())
////            } else {
////                dataUI.addAll(localDatabase.getPaginateCategory(
////                    lastValue = lastId ?: -99, categoryString
////                )?.map {
////                    it.toSnipsUIModel()
////                } ?: listOf())
////            }
////            emit(ResponseState.Success(dataUI))
//        }.flowOn(Dispatchers.IO)
//    }

    private fun getCategoryLabelFromNumber(categoryId: Int?): String {
        categoryId?.let {
            if (it == 3) {
                return "Academy"
            }
            if (it == 1) {
                return "Snips"
            }
            if (it == 2) {
                return "Events"
            }
            if (it == 4) {
                return "Unboxing"
            }
        }
        return "all";
    }


    private fun getCachedSnips(): List<SnipsUIModel> {
        var localData = localDatabase.getAll()?.map {
            it.toSnipsUIModel()
        }
        if (localData?.isEmpty()?.not() == true) {
            localData = localDatabase.getAll()?.map {
                it.toSnipsUIModel()
            }
        }

        return localData ?: emptyList()

    }
}



