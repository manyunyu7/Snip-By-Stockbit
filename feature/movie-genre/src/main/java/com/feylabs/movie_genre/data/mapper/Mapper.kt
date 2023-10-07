package com.feylabs.movie_genre.data.mapper

import com.feylabs.movie_genre.data.source.local.entity.MovieEntity
import com.feylabs.movie_genre.data.source.local.entity.MovieGenreEntity
import com.feylabs.movie_genre.data.source.remote.dto.movie_by_genre.MovieListByGenreResponseDto
import com.feylabs.movie_genre.data.source.remote.dto.movie_genre.MovieGenreResponseDto
import com.feylabs.movie_genre.domain.uimodel.MovieGenreUIModel
import com.feylabs.movie_genre.domain.uimodel.MovieUiModel

object Mapper {



    fun MovieListByGenreResponseDto.MovieListByGenreResponseItemDto.toMovieEntity(): MovieEntity {
        return MovieEntity(
            id = this.id ?: -1,
            title = this.title.orEmpty(),
            adult = this.adult ?: false,
            backdropPath = this.backdropPath.orEmpty(),
            genreIds = this.genreIds?.first() ?: 0,
            originalLanguage = this.originalLanguage.orEmpty(),
            originalTitle = this.originalTitle.orEmpty(),
            overview = this.overview.orEmpty(),
            popularity = this.popularity ?: 0.0,
            posterPath = this.posterPath.orEmpty(),
            releaseDate = this.releaseDate.orEmpty(),
            video = this.video ?: false,
            voteAverage = this.voteAverage ?: 0.0,
            voteCount = this.voteCount ?: 0
        )
    }

    fun MovieEntity.toMovieUiModel(): MovieUiModel {
        return MovieUiModel(
            id = this.id ?: -1,
            title = this.title.orEmpty(),
            adult = this.adult ?: false,
            backdropPath = this.backdropPath.orEmpty(),
            genreIds = this.genreIds ?: 0,
            originalLanguage = this.originalLanguage.orEmpty(),
            originalTitle = this.originalTitle.orEmpty(),
            overview = this.overview.orEmpty(),
            popularity = this.popularity ?: 0.0,
            posterPath = this.posterPath.orEmpty(),
            releaseDate = this.releaseDate.orEmpty(),
            video = this.video ?: false,
            voteAverage = this.voteAverage ?: 0.0,
            voteCount = this.voteCount ?: 0
        )
    }

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