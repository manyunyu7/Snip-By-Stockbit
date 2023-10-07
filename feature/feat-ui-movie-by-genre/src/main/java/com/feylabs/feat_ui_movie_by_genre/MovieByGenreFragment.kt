package com.feylabs.feat_ui_movie_by_genre

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.feylabs.core.base.BaseFragment
import com.feylabs.core.helper.view.ViewUtils.gone
import com.feylabs.core.helper.view.ViewUtils.visible
import com.feylabs.feat_ui_movie_by_genre.databinding.FragmentMovieByGenreBinding
import com.feylabs.feat_ui_movie_by_genre.util.pref.MyPreference
import com.feylabs.shared_dependencies.R
import com.feylabs.uikit.listcomponent.movie_list.MovieUiKitModel
import com.feylabs.uikit.listcomponent.movie_list.UIKitMovieList
import com.feylabs.uikit.state.UIKitState
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class MovieByGenreFragment : BaseFragment<FragmentMovieByGenreBinding>(
    FragmentMovieByGenreBinding::inflate
) {

    val viewModel: MovieByGenreViewModel by viewModels()
    override fun initData() {
        viewModel.getMovieByGenre(1, getGenreId())
    }

    private fun getGenreId(): Int {
        val genreId = arguments?.getString("genreId")?.toIntOrNull() ?: -99
        return genreId;
    }


    override fun initObserver() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.luminaListValue.collect { value ->
                when {
                    value.isLoading -> {
                        binding.emptyState.gone()
                        binding.snipList.setUiState(UIKitState.LOADING)
                    }

                    value.error.isNotBlank() -> {
                        binding.emptyState.gone()
                        binding.snipList.setUiState(UIKitState.ERROR)
                    }

                    value.isEmpty -> {
                        binding.emptyState.visible()
                        binding.snipList.setUiState(UIKitState.EMPTY)
                    }

                    value.coinList.isNotEmpty() -> {
                        binding.emptyState.gone()
                        if (value.toBeCleared) {
                            binding.snipList.clear()
                        }
                        binding.snipList.addDatas(value.coinList.map {
                            MovieUiKitModel(
                                title = it.title,
                                originalLanguage = it.originalLanguage,
                                id = it.id,
                                genreIds = it.genreIds,
                                adult = it.adult,
                                voteAverage = it.voteAverage,
                                popularity = it.popularity,
                                posterPath = it.posterPath,
                                backdropPath = it.backdropPath,
                                originalTitle = it.originalTitle,
                                overview = it.overview,
                                video = it.video,
                                voteCount = it.voteCount,
                                releaseDate = it.releaseDate
                            )
                        })
                        binding.snipList.setUiState(UIKitState.SUCCESS)
                    }
                }
            }
        }
    }


    override fun initUI() {

        binding.snipList.setClickInterface(object : UIKitMovieList.OnSnipListClickInterface {
            override fun onClick(link: MovieUiKitModel) {

                val data = link;
                val gson = Gson()
                val movieDataEncoded = gson.toJson(data)
                MyPreference(requireContext()).save(MovieDetailFragment.MOVIE_PASS,movieDataEncoded);
                val deepLink = Uri.parse(
                    getString(R.string.route_movies_detail)
                        .replace("{id}", link.id.toString())
                )
                    .buildUpon()
                    .build()
                val navOptions = NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .build()

                findNavController().navigate(deepLink, navOptions)


            }

        })

        binding.snipList.loadMoreListener = object : UIKitMovieList.LoadMoreListener {
            override fun onLoadMore(lastId: Int, calledId: String, isCalled: Boolean) {
                if (isCalled.not()) {
                    viewModel.getMovieByGenre(lastId, getGenreId(), "")
                } else {
                    Timber.d("1688_ $lastId calledId : ${calledId.toString()} + $isCalled")
                }
            }
        }
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