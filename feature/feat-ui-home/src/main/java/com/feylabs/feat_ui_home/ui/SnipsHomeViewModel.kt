package com.feylabs.feat_ui_home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feylabs.core.helper.wrapper.ResponseState
import com.feylabs.movie_genre.domain.uimodel.MovieGenreUIModel
import com.feylabs.movie_genre.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SnipsHomeViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) :
    ViewModel() {

    private var _movieGenreListValue = MutableStateFlow(MovieGenreListState())
    val movieGenreListValue: StateFlow<MovieGenreListState> = _movieGenreListValue


    fun getData(){
        fetchMovieData(_movieGenreListValue)
    }

    class MovieGenreListState(
        val isLoading: Boolean = false,
        val movieList: List<MovieGenreUIModel> = emptyList<MovieGenreUIModel>(),
        var error: String = ""
    )

    fun getMovie() {
        fetchMovieData(_movieGenreListValue)
    }



    private fun fetchMovieData(state: MutableStateFlow<MovieGenreListState>) {
        viewModelScope.launch(Dispatchers.IO) {
            movieUseCase.getMovieGenre().collect {
                when (it) {
                    is ResponseState.Loading -> state.value = MovieGenreListState(isLoading = true)
                    is ResponseState.Success -> state.value =
                        MovieGenreListState(movieList  = it.data ?: emptyList())
                    is ResponseState.Error -> state.value = MovieGenreListState(
                        isLoading = false,
                        error = it.errorResponse?.errorMessage.toString()
                    )
                }
            }
        }
    }



}

