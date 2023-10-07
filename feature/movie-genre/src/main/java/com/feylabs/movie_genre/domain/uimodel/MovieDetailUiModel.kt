package com.feylabs.movie_genre.domain.uimodel

data class MovieDetailUiModel(
    val adult: Boolean,
    val backdropPath: String,
    val belongsToCollection: Any?, // Change the type to the actual type if available
    val budget: Int,
    val genres: String,
    val homepage: String,
    val id: Int,
    val imdbId: String,
    val videoUrl :String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: String,
    val productionCountries: String,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val spokenLanguages: String,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
){
    fun getFullPathVideoUrl(): String {
//        "https://www.youtube.com/embed/${matchResult.groupValues[1]}"
        return "https://m.youtube.com/embed/watch?v="+videoUrl;
    }
}