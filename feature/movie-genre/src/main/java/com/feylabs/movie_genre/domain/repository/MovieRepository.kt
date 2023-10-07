package com.feylabs.movie_genre.domain.repository

import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.movie_genre.domain.uimodel.MovieGenreUIModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getAllMovieGenre(
    ): Flow<ResponseState<List<MovieGenreUIModel>>>

}