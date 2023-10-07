package com.feylabs.feat_ui_movie_by_genre

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.feylabs.core.base.BaseFragment
import com.feylabs.core.helper.toast.ToastHelper.showToast
import com.feylabs.core.helper.view.ViewUtils.gone
import com.feylabs.core.helper.view.ViewUtils.visible
import com.feylabs.feat_ui_movie_by_genre.databinding.FragmentMovieByGenreBinding
import com.feylabs.feat_ui_movie_by_genre.databinding.FragmentMovieDetailBinding
import com.feylabs.movie_genre.domain.uimodel.MovieUiModel
import com.feylabs.uikit.listcomponent.movie_list.MovieUiKitModel
import com.feylabs.uikit.listcomponent.movie_list.UIKitMovieList
import com.feylabs.uikit.listcomponent.snip.UIKitSnipList
import com.feylabs.uikit.listcomponent.uikitmodel.UnboxingSectoralUIKitModel
import com.feylabs.uikit.state.UIKitState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(
    FragmentMovieDetailBinding::inflate
) {

    val viewModel: MovieByGenreViewModel by viewModels()
    override fun initData() {
        viewModel.getMovieByGenre(1,getGenreId())
    }

    private fun getGenreId(): Int {
        val genreId = arguments?.getString("genreId")?.toIntOrNull()  ?: -99
        return genreId;
    }


    override fun initObserver() {
    }


    override fun initUI() {

    }



    override fun initAction() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initData()
        initObserver()
        initAction()
    }
}