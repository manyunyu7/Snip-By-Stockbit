package com.feylabs.movie_genre.domain.usecase

import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.movie_genre.domain.uimodel.MovieGenreUIModel
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getMovieGenre(
    ): Flow<ResponseState<List<MovieGenreUIModel>>>
}