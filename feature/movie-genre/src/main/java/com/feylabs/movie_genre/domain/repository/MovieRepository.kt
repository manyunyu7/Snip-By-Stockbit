package com.feylabs.movie_genre.domain.repository

import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.movie_genre.data.source.remote.dto.movie_detail.MovieDetailResponseDto
import com.feylabs.movie_genre.domain.uimodel.MovieDetailUiModel
import com.feylabs.movie_genre.domain.uimodel.MovieGenreUIModel
import com.feylabs.movie_genre.domain.uimodel.MovieReviewUiModel
import com.feylabs.movie_genre.domain.uimodel.MovieUiModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getAllMovieGenre(
    ): Flow<ResponseState<List<MovieGenreUIModel>>>

    fun getMovieDetail(
        movieId:Int
    ): Flow<ResponseState<MovieDetailUiModel>>

    fun getMovieOnGenre(
        page:Int,
        genreId:Int,
        query:String
    ): Flow<ResponseState<List<MovieUiModel>>>


    fun getMovieReviews(
        page:Int,
        movieId: Int,
    ): Flow<ResponseState<List<MovieReviewUiModel>>>


}