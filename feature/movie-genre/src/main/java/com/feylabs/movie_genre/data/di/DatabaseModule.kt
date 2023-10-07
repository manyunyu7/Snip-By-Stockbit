package com.feylabs.movie_genre.data.di

import android.content.Context
import androidx.room.Room
import com.feylabs.movie_genre.data.source.local.dao.MoviesDAO
import com.feylabs.movie_genre.data.source.local.room.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSnipsDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, "movies-db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMoviesGenresDao(database: MovieDatabase): MoviesDAO {
        return database.movieGenreDao()
    }
}