package com.feylabs.feat_ui_movie_by_genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feylabs.core.helper.wrapper.ResponseState.*
import com.feylabs.movie_genre.domain.uimodel.MovieDetailUiModel
import com.feylabs.movie_genre.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _movieDetailValue = MutableStateFlow(MovieDetailState())
    var movieDetailValue: StateFlow<MovieDetailState> = _movieDetailValue

    fun getMovieDetail(movieId:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieUseCase.getMovieDetail(movieId).collect {
                when (it) {
                    is Loading -> {
                        _movieDetailValue.value = MovieDetailState(
                            isLoading = true
                        )
                    }
                    is Success -> {
                        _movieDetailValue.value = MovieDetailState(
                            data = it.data
                        )
                    }
                    is Error -> {
                        _movieDetailValue.value = MovieDetailState(
                            isLoading = false,
                            error = it.errorResponse?.errorMessage.toString()
                        )
                    }
                }
            }
        }

    }
}

class MovieDetailState(
    val isLoading: Boolean = false,
    var error: String = "",
    var data : MovieDetailUiModel?=null
)

