package com.feylabs.movie_genre.data.source.remote.dto.movie_video


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class MovieVideoResponseDto(
    @SerializedName("id")
    @Expose
    val id: Int? = null,
    @SerializedName("results")
    @Expose
    val results: List<MovieVideoResponseItemDto>
) {
    data class MovieVideoResponseItemDto(
        @SerializedName("id")
        @Expose
        val id: String? = null,
        @SerializedName("iso_3166_1")
        @Expose
        val iso31661: String? = null,
        @SerializedName("iso_639_1")
        @Expose
        val iso6391: String? = null,
        @SerializedName("key")
        @Expose
        val key: String? = null,
        @SerializedName("name")
        @Expose
        val name: String? = null,
        @SerializedName("official")
        @Expose
        val official: Boolean? = null,
        @SerializedName("published_at")
        @Expose
        val publishedAt: String? = null,
        @SerializedName("site")
        @Expose
        val site: String? = null,
        @SerializedName("size")
        @Expose
        val size: Int? = null,
        @SerializedName("type")
        @Expose
        val type: String? = null
    )
}