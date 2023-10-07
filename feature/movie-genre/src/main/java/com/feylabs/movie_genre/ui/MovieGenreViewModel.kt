package com.feylabs.movie_genre.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feylabs.core.helper.wrapper.ResponseState.*
import com.feylabs.movie_genre.domain.uimodel.MovieGenreUIModel
import com.feylabs.movie_genre.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieGenreViewModel @Inject constructor(private val luminaUseCase: MovieUseCase) : ViewModel() {
    private val _movieGenreListValue = MutableStateFlow(MovieGenreListState())
    var movieGenreListValue: StateFlow<MovieGenreListState> = _movieGenreListValue

    fun getImage(limit: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            luminaUseCase.getMovieGenre().collect {
                when (it) {
                    is Loading -> {
                        _movieGenreListValue.value = MovieGenreListState(
                            isLoading = true
                        )
                    }
                    is Success -> {
                        _movieGenreListValue.value = MovieGenreListState(
                            coinList = it.data ?: emptyList()
                        )
                    }
                    is Error -> {
                        _movieGenreListValue.value = MovieGenreListState(
                            isLoading = false,
                            error = it.errorResponse?.errorMessage.toString()
                        )
                    }
                }
            }
        }

    }
}

class MovieGenreListState(
    val isLoading: Boolean = false,
    val coinList: List<MovieGenreUIModel> = emptyList<MovieGenreUIModel>(),
    var error: String = ""
)