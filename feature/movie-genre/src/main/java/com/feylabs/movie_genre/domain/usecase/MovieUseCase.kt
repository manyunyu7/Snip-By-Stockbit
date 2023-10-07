package com.feylabs.movie_genre.domain.usecase

import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.movie_genre.domain.uimodel.MovieDetailUiModel
import com.feylabs.movie_genre.domain.uimodel.MovieGenreUIModel
import com.feylabs.movie_genre.domain.uimodel.MovieReviewUiModel
import com.feylabs.movie_genre.domain.uimodel.MovieUiModel
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getMovieGenre(
    ): Flow<ResponseState<List<MovieGenreUIModel>>>

    fun getMovieByGenre(
        genreId:Int,
        page:Int,
        query:String
    ): Flow<ResponseState<List<MovieUiModel>>>

    fun getMovieDetail(
        movieId:Int
    ): Flow<ResponseState<MovieDetailUiModel>>

    fun getMovieReviews(
        movieId:Int,
        page:Int,
    ): Flow<ResponseState<List<MovieReviewUiModel>>>


}