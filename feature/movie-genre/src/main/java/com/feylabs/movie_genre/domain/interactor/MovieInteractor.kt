package com.feylabs.movie_genre.domain.interactor

import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.movie_genre.domain.repository.MovieRepository
import com.feylabs.movie_genre.domain.uimodel.MovieDetailUiModel
import com.feylabs.movie_genre.domain.uimodel.MovieGenreUIModel
import com.feylabs.movie_genre.domain.uimodel.MovieReviewUiModel
import com.feylabs.movie_genre.domain.uimodel.MovieUiModel
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
    override fun getMovieByGenre(
        genreId: Int,
        page: Int,
        query: String
    ): Flow<ResponseState<List<MovieUiModel>>> {
        return movieRepository.getMovieOnGenre(
            page=page,
            genreId=genreId,
            query=query
        )
    }

    override fun getMovieDetail(movieId: Int): Flow<ResponseState<MovieDetailUiModel>> {
        return movieRepository.getMovieDetail(movieId)
    }

    override fun getMovieReviews(
        movieId: Int,
        page: Int
    ): Flow<ResponseState<List<MovieReviewUiModel>>> {
        return movieRepository.getMovieReviews(
            page = page,movieId=movieId
        )
    }

}