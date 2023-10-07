package com.feylabs.movie_genre.data

import com.feylabs.movie_genre.data.source.remote.dto.movie_genre.MovieGenreResponseDto
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

}