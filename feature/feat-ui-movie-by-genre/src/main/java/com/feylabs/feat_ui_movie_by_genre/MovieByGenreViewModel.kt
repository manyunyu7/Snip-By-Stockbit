package com.feylabs.feat_ui_movie_by_genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feylabs.core.helper.wrapper.ResponseState.*
import com.feylabs.movie_genre.domain.uimodel.MovieUiModel
import com.feylabs.movie_genre.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieByGenreViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _luminaListValue = MutableStateFlow(LuminaListState())
    var luminaListValue: StateFlow<LuminaListState> = _luminaListValue
    fun getMovieByGenre(page: Int=1, genreId:Int, query:String="") {
        viewModelScope.launch(Dispatchers.IO) {
            movieUseCase.getMovieByGenre(page = page, genreId = genreId, query =query ).collect {
                when (it) {
                    is Loading -> {
                        _luminaListValue.value = LuminaListState(
                            isLoading = true
                        )
                    }
                    is Success -> {
                        _luminaListValue.value = LuminaListState(
                            coinList = it.data ?: emptyList()
                        )
                    }
                    is Error -> {
                        _luminaListValue.value = LuminaListState(
                            isLoading = false,
                            error = it.errorResponse?.errorMessage.toString()
                        )
                    }
                }
            }
        }

    }
}

class LuminaListState(
    val isLoading: Boolean = false,
    val coinList: List<MovieUiModel> = emptyList<MovieUiModel>(),
    var error: String = ""
)