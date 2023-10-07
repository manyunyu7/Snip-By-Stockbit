package com.feylabs.movie_genre.data

import com.feylabs.movie_genre.data.source.remote.dto.movie_by_genre.MovieListByGenreResponseDto
import com.feylabs.movie_genre.data.source.remote.dto.movie_detail.MovieDetailResponseDto
import com.feylabs.movie_genre.data.source.remote.dto.movie_genre.MovieGenreResponseDto
import com.feylabs.movie_genre.data.source.remote.dto.movie_reviews.MovieReviewsResponseDto
import com.feylabs.movie_genre.data.source.remote.dto.movie_video.MovieVideoResponseDto
import com.feylabs.movie_genre.data.source.remote.service.MovieGenreApi
import com.feylabs.snips.di.MovieModule
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    @MovieModule.RetrofitSnips private val api: MovieGenreApi
) {

    suspend fun getMovieGenre(
    ): Response<MovieGenreResponseDto> {
        return api.getAllMovieGenre()
    }

    suspend fun getMovieByGenre(
        genreId:Int,
        page:Int,
    ): Response<MovieListByGenreResponseDto> {
        return api.discoverMovies(
            genres = genreId,
            page = page,
        )
    }

    suspend fun getMovieReview(
        movieId:Int,
        page:Int,
    ): Response<MovieReviewsResponseDto> {
        return api.getMovieReviews(
            movieId = movieId,
            page = page,
        )
    }

    suspend fun getMovieDetail(
        movieId:Int,
    ): Response<MovieDetailResponseDto> {
        return api.getMovieDetails(
            movieId = movieId
        )
    }

    suspend fun getMovieVideos(
        movieId:Int,
    ): Response<MovieVideoResponseDto> {
        return api.getMovieVideos(
            movieId = movieId
        )
    }


}