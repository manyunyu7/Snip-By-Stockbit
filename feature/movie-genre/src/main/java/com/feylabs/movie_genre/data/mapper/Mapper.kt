package com.feylabs.movie_genre.data.mapper

import com.feylabs.movie_genre.data.source.local.entity.MovieEntity
import com.feylabs.movie_genre.data.source.local.entity.MovieGenreEntity
import com.feylabs.movie_genre.data.source.remote.dto.movie_by_genre.MovieListByGenreResponseDto
import com.feylabs.movie_genre.data.source.remote.dto.movie_detail.MovieDetailResponseDto
import com.feylabs.movie_genre.data.source.remote.dto.movie_genre.MovieGenreResponseDto
import com.feylabs.movie_genre.data.source.remote.dto.movie_reviews.MovieReviewsResponseDto
import com.feylabs.movie_genre.domain.uimodel.MovieDetailUiModel
import com.feylabs.movie_genre.domain.uimodel.MovieGenreUIModel
import com.feylabs.movie_genre.domain.uimodel.MovieReviewUiModel
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

    fun MovieDetailResponseDto.toMovieDetailUiModel(videoUrl:String): MovieDetailUiModel {
        return MovieDetailUiModel(
            adult = this.adult ?: false,
            backdropPath = this.backdropPath.orEmpty(),
            belongsToCollection = this.belongsToCollection, // Replace with the actual type
            budget = this.budget ?: 0,
            genres = this.genres?.joinToString { "${it?.name}" } ?: "", // Adjust according to your DTO structure
            homepage = this.homepage.orEmpty(),
            id = this.id ?: -1,
            imdbId = this.imdbId.orEmpty(),
            originalLanguage = this.originalLanguage.orEmpty(),
            originalTitle = this.originalTitle.orEmpty(),
            overview = this.overview.orEmpty(),
            popularity = this.popularity ?: 0.0,
            posterPath = this.posterPath.orEmpty(),
            productionCompanies = this.productionCompanies?.joinToString { "${it?.name} (${it?.id})" } ?: "", // Adjust according to your DTO structure
            productionCountries = this.productionCountries?.joinToString { "${it?.name} " } ?: "", // Adjust according to your DTO structure
            releaseDate = this.releaseDate.orEmpty(),
            revenue = this.revenue ?: 0,
            runtime = this.runtime ?: 0,
            spokenLanguages = this.spokenLanguages?.joinToString { "${it?.name}" } ?: "", // Adjust according to your DTO structure
            status = this.status.orEmpty(),
            tagline = this.tagline.orEmpty(),
            title = this.title.orEmpty(),
            video = this.video ?: false,
            voteAverage = this.voteAverage ?: 0.0,
            voteCount = this.voteCount ?: 0,
            videoUrl = videoUrl
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

    fun MovieReviewsResponseDto.MovieReviewsResponseItemDto.toMovieReviewUiModel(): MovieReviewUiModel {
        return MovieReviewUiModel(
            author = this.author.orEmpty(),
            authorUsername = this.authorDetails?.username.orEmpty(),
            authorAvatarPath = this.authorDetails?.avatarPath.orEmpty(),
            content = this.content.orEmpty(),
            createdAt = this.createdAt.orEmpty(),
            id = this.id.orEmpty(),
            updatedAt = this.updatedAt.orEmpty(),
            url = this.url.orEmpty()
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