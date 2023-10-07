package com.feylabs.movie_genre.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.feylabs.movie_genre.data.source.local.dao.MoviesDAO
import com.feylabs.movie_genre.data.source.local.entity.MovieEntity
import com.feylabs.movie_genre.data.source.local.entity.MovieGenreEntity


@Database(
    entities = [MovieGenreEntity::class,MovieEntity::class], version = 168
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieGenreDao(): MoviesDAO
}