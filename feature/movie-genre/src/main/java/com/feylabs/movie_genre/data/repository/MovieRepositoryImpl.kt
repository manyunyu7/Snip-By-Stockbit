package com.feylabs.movie_genre.data.repository

import android.net.ConnectivityManager
import com.feylabs.core.helper.error.ErrorResponse
import com.feylabs.core.helper.error.ResponseExceptionHandler
import com.feylabs.core.helper.network.NetworkInfo
import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.movie_genre.data.mapper.Mapper.toMovieGenreEntity
import com.feylabs.movie_genre.data.mapper.Mapper.toMovieGenreUIModel
import com.feylabs.movie_genre.data.RemoteDataSource
import com.feylabs.movie_genre.data.mapper.Mapper.toMovieEntity
import com.feylabs.movie_genre.data.mapper.Mapper.toMovieUiModel
import com.feylabs.movie_genre.data.source.local.dao.MoviesDAO
import com.feylabs.snips.di.MovieModule.ConnectivityManagerSnips
import com.feylabs.movie_genre.domain.repository.MovieRepository
import com.feylabs.movie_genre.domain.uimodel.MovieGenreUIModel
import com.feylabs.movie_genre.domain.uimodel.MovieUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
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
            emit(ResponseState.Loading())
            if (NetworkInfo.isOnline(connectivityManager)) {
                try {
                    val response = remoteDataSource.getMovieGenre()
                    if (response.isSuccessful) {
                        val entityModels = response.body()?.genres?.map { it.toMovieGenreEntity() }
                        entityModels?.let {
                            movieDatabase.insertAllGenres(it)
                        }
                        delay(1000)
                        emit(ResponseState.Success(entityModels?.map { it.toMovieGenreUIModel() }))
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

    override fun getMovieOnGenre(page: Int, genreId: Int): Flow<ResponseState<List<MovieUiModel>>> =
        flow<ResponseState<List<MovieUiModel>>> {
            emit(ResponseState.Loading())
            if (NetworkInfo.isOnline(connectivityManager)) {
                try {
                    val response = remoteDataSource.getMovieByGenre(
                        page = page,
                        genreId = genreId
                    )
                    if (response.isSuccessful) {
                        val entityModels = response.body()?.results?.map { it.toMovieEntity() }
                        entityModels?.let {
                            movieDatabase.insertAllMovies(entityModels)
                        }
                        delay(1000)
                        ResponseState.Success(entityModels?.map { it.toMovieUiModel() })
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
                if (getCachedMovie(genreId).isNotEmpty()) {
                    emit(ResponseState.Error(ErrorResponse(errorMessage = errorMessage)))
                    delay(1000)
                    emit(ResponseState.Success(getCachedMovie(genreId)))
                } else {
                    emit(ResponseState.Error(ErrorResponse(errorMessage = errorMessage)))
                }
            }
        }.flowOn(Dispatchers.IO)


    private fun getCachedMovieGenre(): List<MovieGenreUIModel> {
        val localData = movieDatabase.getAllGenres()?.map {
            it.toMovieGenreUIModel()
        }
        return localData ?: emptyList()
    }

    private fun getCachedMovie(genreId: Int): List<MovieUiModel> {
        val localData = movieDatabase.getMovieByGenre(genreId)?.map {
            it.toMovieUiModel()
        }
        return localData ?: emptyList()
    }
}



