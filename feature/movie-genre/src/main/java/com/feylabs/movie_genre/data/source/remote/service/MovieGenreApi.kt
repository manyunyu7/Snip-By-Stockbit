package com.feylabs.movie_genre.data.source.remote.service

import com.feylabs.movie_genre.data.source.remote.dto.movie_genre.MovieGenreResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieGenreApi {

    @GET("genre/movie/list?language=en")
    suspend fun getAllMovieGenre(
    ): Response<MovieGenreResponseDto>

}