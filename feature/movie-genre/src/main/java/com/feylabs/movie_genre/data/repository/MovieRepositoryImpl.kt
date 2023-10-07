package com.feylabs.movie_genre.data.repository

import android.net.ConnectivityManager
import com.feylabs.core.helper.error.ErrorResponse
import com.feylabs.core.helper.error.ResponseExceptionHandler
import com.feylabs.core.helper.network.NetworkInfo
import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.movie_genre.data.mapper.Mapper.toMovieGenreEntity
import com.feylabs.movie_genre.data.mapper.Mapper.toMovieGenreUIModel
import com.feylabs.movie_genre.data.RemoteDataSource
import com.feylabs.movie_genre.data.mapper.Mapper.toMovieDetailUiModel
import com.feylabs.movie_genre.data.mapper.Mapper.toMovieEntity
import com.feylabs.movie_genre.data.mapper.Mapper.toMovieReviewUiModel
import com.feylabs.movie_genre.data.mapper.Mapper.toMovieUiModel
import com.feylabs.movie_genre.data.source.local.dao.MoviesDAO
import com.feylabs.movie_genre.data.source.remote.dto.movie_detail.MovieDetailResponseDto
import com.feylabs.snips.di.MovieModule.ConnectivityManagerSnips
import com.feylabs.movie_genre.domain.repository.MovieRepository
import com.feylabs.movie_genre.domain.uimodel.MovieDetailUiModel
import com.feylabs.movie_genre.domain.uimodel.MovieGenreUIModel
import com.feylabs.movie_genre.domain.uimodel.MovieReviewUiModel
import com.feylabs.movie_genre.domain.uimodel.MovieUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
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
                        if (getCachedMovieGenre().isNotEmpty()) {
                            delay(1000)
                            emit(ResponseState.Success(getCachedMovieGenre()))
                        }
                        emit(
                            ResponseState.Error(
                                errorResponse = ErrorResponse(
                                    response.message().toString()
                                )
                            )
                        )
                    }
                } catch (e: Exception) {
                    if (getCachedMovieGenre().isNotEmpty()) {
                        delay(1000)
                        emit(ResponseState.Success(getCachedMovieGenre()))
                    }
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

    override fun getMovieDetail(movieId: Int): Flow<ResponseState<MovieDetailUiModel>> =
        flow<ResponseState<MovieDetailUiModel>> {
            emit(ResponseState.Loading())
            if (NetworkInfo.isOnline(connectivityManager)) {
                try {
                    val response = remoteDataSource.getMovieDetail(
                        movieId = movieId
                    )
                    if (response.isSuccessful) {
                        var videoUrl = ""
                        val videos = remoteDataSource.getMovieVideos(movieId)

                        if (videos.isSuccessful){
                            videos.body()?.results?.take(1)?.forEachIndexed { index, movieVideoResponseItemDto ->
                                videoUrl = movieVideoResponseItemDto.key.toString()
                                return@forEachIndexed  // exit the loop after processing the first item
                            }
                        }

                        delay(1000)
                        val data = response.body()?.toMovieDetailUiModel(videoUrl)
                        ResponseState.Success( { data })
                            .let {
                                emit((ResponseState.Success(data)))
                            }
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
                    emit(
                        ResponseState.Error(
                            errorResponse = ErrorResponse(
                                "Terjadi Kesalahan"
                            )
                        )
                    )
                }
            } else {
                val errorMessage = "Tidak Ada Koneksi Internet"
                emit(
                    ResponseState.Error(
                        errorResponse = ErrorResponse(
                            errorMessage
                        )
                    )
                )
            }
        }.flowOn(Dispatchers.IO)

    override fun getMovieOnGenre(page: Int, genreId: Int,query:String): Flow<ResponseState<List<MovieUiModel>>> =
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

    override fun getMovieReviews(
        page: Int,
        movieId: Int
    ): Flow<ResponseState<List<MovieReviewUiModel>>> =
        flow<ResponseState<List<MovieReviewUiModel>>> {
            emit(ResponseState.Loading())
            if (NetworkInfo.isOnline(connectivityManager)) {
                try {
                    val response = remoteDataSource.getMovieReview(
                        page = page,
                        movieId = movieId
                    )
                    if (response.isSuccessful) {
                        val entityModels = response.body()?.results?.map { it.toMovieReviewUiModel() }
                        delay(1000)
                        emit(ResponseState.Success(entityModels))
                    } else {
                        val errorResponse = ErrorResponse(
                            response.message().toString(),
                        )
                        Timber.d("xyz error a gan: ${response.message()}, Code: ${response.code()}")
                        emit(ResponseState.Error(errorResponse))
                    }
                } catch (e: Exception) {
                    Timber.d("xyz error b gan ")
                    ResponseExceptionHandler.handleException(e, this)
                }
            } else {
                val errorMessage = "Tidak Ada Koneksi Internet"
                Timber.d("xyz error c gan ")
                emit(ResponseState.Error(ErrorResponse(errorMessage = errorMessage)))
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



