package com.feylabs.movie_genre.data.source.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "movie_genre_items")
data class MovieGenreEntity(
    @PrimaryKey val id: Int,
    val title:String,
)
