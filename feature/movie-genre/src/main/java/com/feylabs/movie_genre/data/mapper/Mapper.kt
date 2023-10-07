package com.feylabs.movie_genre.data.mapper

import com.feylabs.movie_genre.data.source.local.entity.MovieGenreEntity
import com.feylabs.movie_genre.data.source.remote.dto.movie_genre.MovieGenreResponseDto
import com.feylabs.movie_genre.domain.uimodel.MovieGenreUIModel

object Mapper {

    fun MovieGenreResponseDto.MovieGenreResponseItemDto.toMovieGenreEntity(): MovieGenreEntity {
        return MovieGenreEntity(
            id = this.id?:-0,
            title = this.name.orEmpty(),
        )
    }

    fun MovieGenreEntity.toMovieGenreUIModel(): MovieGenreUIModel {
        return MovieGenreUIModel(
            id = this.id,
            title = this.title.orEmpty(),
        )
    }



}