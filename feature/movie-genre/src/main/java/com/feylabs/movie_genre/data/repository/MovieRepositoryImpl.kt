package com.feylabs.movie_genre.data.repository

import android.net.ConnectivityManager
import com.feylabs.core.helper.error.ErrorResponse
import com.feylabs.core.helper.error.ResponseExceptionHandler
import com.feylabs.core.helper.network.NetworkInfo
import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.movie_genre.data.mapper.Mapper.toMovieGenreEntity
import com.feylabs.movie_genre.data.mapper.Mapper.toMovieGenreUIModel
import com.feylabs.movie_genre.data.RemoteDataSource
import com.feylabs.movie_genre.data.source.local.dao.MoviesDAO
import com.feylabs.snips.di.MovieModule.ConnectivityManagerSnips
import com.feylabs.movie_genre.domain.repository.MovieRepository
import com.feylabs.movie_genre.domain.uimodel.MovieGenreUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @ConnectivityManagerSnips private val connectivityManager: ConnectivityManager,
    private val movieDatabase: MoviesDAO,
) : MovieRepository {

    override fun getAllMovieGenre() =
        flow<ResponseState<List<MovieGenreUIModel>>> {
//            emit(ResponseState.Success(getCachedSnips(categoryId)))
            emit(ResponseState.Loading())
            if (NetworkInfo.isOnline(connectivityManager)) {
                try {
                    val response = remoteDataSource.getMovieGenre()
                    if (response.isSuccessful) {
                        val entityModels = response.body()?.genres?.map { it.toMovieGenreEntity() }
                        entityModels?.let {
                            movieDatabase.insertAll(it)
                        }
                        delay(1000)
                        ResponseState.Success(entityModels?.map { it.toMovieGenreUIModel() })
                            .let { emit(it) }
                    } else {
                        emit(
                            ResponseState.Error(
                                errorResponse = ErrorResponse(
                                    response.message().toString()
                                )
                            )
                        )
                    }
                } catch (e: Exception) {
                    ResponseExceptionHandler.handleException(e, this)
                }
            } else {
                val errorMessage = "Tidak Ada Koneksi Internet"
                if (getCachedMovieGenre().isNotEmpty()) {
                    emit(ResponseState.Error(ErrorResponse(errorMessage = errorMessage)))
                    delay(1000)
                    emit(ResponseState.Success(getCachedMovieGenre()))
                } else {
                    emit(ResponseState.Error(ErrorResponse(errorMessage = errorMessage)))
                }
            }
        }.flowOn(Dispatchers.IO)


    private fun getCachedMovieGenre(): List<MovieGenreUIModel> {
        val localData = movieDatabase.getAll()?.map {
            it.toMovieGenreUIModel()
        }
        return localData ?: emptyList()
    }

}



