package com.feylabs.feat_ui_movie_reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feylabs.core.helper.wrapper.ResponseState.*
import com.feylabs.movie_genre.domain.uimodel.MovieReviewUiModel
import com.feylabs.movie_genre.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MoviewReviewsViewModel @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModel() {
    private val _movieReviewValues = MutableStateFlow(MovieReviewListState())
    var luminaListValue: StateFlow<MovieReviewListState> = _movieReviewValues
    fun getMovieByGenre(page: Int = 1, movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieUseCase.getMovieReviews(page = page, movieId = movieId).collect {
                when (it) {
                    is Loading -> {
                        Timber.d("apakabar llll ${it.data}")
                        _movieReviewValues.value = MovieReviewListState(
                            isLoading = true
                        )
                    }

                    is Success -> {
                        _movieReviewValues.value = MovieReviewListState(
                            reviewList = it.data ?: emptyList()
                        )
                        Timber.d("apakabar gaesx ${it.data}")
                    }

                    is Error -> {
                        Timber.d("apakabar gaesxeee ${it.data}")
                        _movieReviewValues.value = MovieReviewListState(
                            isLoading = false,
                            error = it.errorResponse?.errorMessage.toString()
                        )
                    }
                }
            }
        }

    }
}

class MovieReviewListState(
    val isLoading: Boolean = false,
    val reviewList: List<MovieReviewUiModel> = emptyList<MovieReviewUiModel>(),
    var error: String = "",
    var isSuccess: Boolean = true,
    var isEmpty: Boolean = false,
    var toBeCleared: Boolean = false,
)