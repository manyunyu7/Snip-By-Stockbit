package com.feylabs.movie_genre.data.source.local.dao

import androidx.room.*
import com.feylabs.movie_genre.data.source.local.entity.MovieGenreEntity

@Dao
interface MoviesDAO {

    @Query("SELECT * FROM movie_genre_items ORDER BY id DESC")
    fun getAll(): List<MovieGenreEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<MovieGenreEntity?>)

}