package com.feylabs.movie_genre.data.source.remote.service

import com.feylabs.movie_genre.data.source.remote.dto.movie_by_genre.MovieListByGenreResponseDto
import com.feylabs.movie_genre.data.source.remote.dto.movie_detail.MovieDetailResponseDto
import com.feylabs.movie_genre.data.source.remote.dto.movie_genre.MovieGenreResponseDto
import com.feylabs.movie_genre.data.source.remote.dto.movie_reviews.MovieReviewsResponseDto
import com.feylabs.movie_genre.data.source.remote.dto.movie_video.MovieVideoResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieGenreApi {

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("with_genres") genres: Int,
        @Query("page") page: Int,
    ): Response<MovieListByGenreResponseDto>

    @GET("genre/movie/list?language=en")
    suspend fun getAllMovieGenre(
    ): Response<MovieGenreResponseDto>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int,): Response<MovieDetailResponseDto>

    @GET("movie/{id}/videos")
    suspend fun getMovieVideos(@Path("id") movieId: Int): Response<MovieVideoResponseDto>

    @GET("movie/{id}/reviews")
    suspend fun getMovieReviews(
        @Path("id") movieId: Int,
        @Query("page") page: Int,
        @Query("language") language: String= "en-US"
    ): Response<MovieReviewsResponseDto>

}