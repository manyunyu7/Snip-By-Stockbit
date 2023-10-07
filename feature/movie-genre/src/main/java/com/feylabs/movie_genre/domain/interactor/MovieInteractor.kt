package com.feylabs.movie_genre.domain.interactor

import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.movie_genre.domain.repository.MovieRepository
import com.feylabs.movie_genre.domain.uimodel.MovieGenreUIModel
import com.feylabs.movie_genre.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val movieRepository: MovieRepository
) : MovieUseCase {

    override fun getMovieGenre(): Flow<ResponseState<List<MovieGenreUIModel>>> {
        return movieRepository.getAllMovieGenre(
        )
    }

}