package com.feylabs.movie_genre.data.source.remote.dto.movie_genre


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class MovieGenreResponseDto(
    @SerializedName("genres")
    @Expose
    val genres: List<MovieGenreResponseItemDto> = listOf()
) {
    data class MovieGenreResponseItemDto(
        @SerializedName("id")
        @Expose
        val id: Int? = null,
        @SerializedName("name")
        @Expose
        val name: String? = null
    )
}