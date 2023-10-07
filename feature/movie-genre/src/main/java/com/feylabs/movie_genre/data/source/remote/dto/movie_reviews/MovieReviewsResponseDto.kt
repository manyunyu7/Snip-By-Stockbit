package com.feylabs.movie_genre.data.source.remote.dto.movie_reviews


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class MovieReviewsResponseDto(
    @SerializedName("id")
    @Expose
    val id: Int? = null,
    @SerializedName("page")
    @Expose
    val page: Int? = null,
    @SerializedName("results")
    @Expose
    val results: List<MovieReviewsResponseItemDto> = listOf(),
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int? = null,
    @SerializedName("total_results")
    @Expose
    val totalResults: Int? = null
) {
    data class MovieReviewsResponseItemDto(
        @SerializedName("author")
        @Expose
        val author: String? = null,
        @SerializedName("author_details")
        @Expose
        val authorDetails: AuthorDetails? = null,
        @SerializedName("content")
        @Expose
        val content: String? = null,
        @SerializedName("created_at")
        @Expose
        val createdAt: String? = null,
        @SerializedName("id")
        @Expose
        val id: String? = null,
        @SerializedName("updated_at")
        @Expose
        val updatedAt: String? = null,
        @SerializedName("url")
        @Expose
        val url: String? = null
    ) {
        data class AuthorDetails(
            @SerializedName("avatar_path")
            @Expose
            val avatarPath: String? = null,
            @SerializedName("name")
            @Expose
            val name: String? = null,
            @SerializedName("rating")
            @Expose
            val rating: Double? = null,
            @SerializedName("username")
            @Expose
            val username: String? = null
        )
    }
}