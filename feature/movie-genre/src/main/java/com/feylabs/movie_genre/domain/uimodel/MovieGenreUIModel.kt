package com.feylabs.movie_genre.domain.uimodel

data class MovieGenreUIModel(
    val title:String,
    val id: Int,
){
    fun getImageUrl(): String {
        return "http://feylabs.my.id/fm/mandiri/$title.png"
    }
}