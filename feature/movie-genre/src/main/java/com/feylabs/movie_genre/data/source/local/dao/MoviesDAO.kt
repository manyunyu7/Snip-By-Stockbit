package com.feylabs.movie_genre.data.source.local.dao

import androidx.room.*
import com.feylabs.movie_genre.data.source.local.entity.MovieEntity
import com.feylabs.movie_genre.data.source.local.entity.MovieGenreEntity

@Dao
interface MoviesDAO {

    @Query("SELECT * FROM movie_items ORDER BY id DESC")
    fun getAllGenres(): List<MovieGenreEntity>?


    // to be shown when user app is offline.
    @Query("SELECT * FROM movie_items WHERE genreIds = :genreId ORDER BY id desc LIMIT 10;")
    fun getMovieByGenre(genreId: Int): List<MovieEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllGenres(items: List<MovieGenreEntity?>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(items: List<MovieEntity?>)

}