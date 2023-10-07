package com.feylabs.feat_ui_movie_reviews

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.feylabs.core.base.BaseFragment
import com.feylabs.core.helper.toast.ToastHelper.showToast
import com.feylabs.core.helper.view.ViewUtils.gone
import com.feylabs.core.helper.view.ViewUtils.visible
import com.feylabs.feat_ui_movie_reviews.databinding.FragmentMovieReviewsBinding
import com.feylabs.uikit.listcomponent.movie_review.MovieReviewUiKitModel
import com.feylabs.uikit.listcomponent.movie_review.UIKitMovieReviewSnipList
import com.feylabs.uikit.state.UIKitState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class MovieReviewsFragment : BaseFragment<FragmentMovieReviewsBinding>(
    FragmentMovieReviewsBinding::inflate
) {

    val viewModel: MoviewReviewsViewModel by viewModels()
    override fun initData() {
        viewModel.getMovieByGenre(1, getMovieId())
    }

    private fun getMovieId(): Int {
        val genreId = arguments?.getString("id")?.toIntOrNull() ?: -99
        return genreId;
    }


    override fun initObserver() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.luminaListValue.collect { value ->
                when {
                    value.isLoading -> {
                        binding.emptyState.gone()
                        binding.reviewList.setUiState(UIKitState.LOADING)
                    }

                    value.error.isNotBlank() -> {
                        binding.emptyState.gone()
                        binding.reviewList.setUiState(UIKitState.ERROR)
                    }

                    value.isEmpty -> {
                        binding.emptyState.visible()
                        binding.reviewList.setUiState(UIKitState.EMPTY)
                    }

                    value.reviewList.isNotEmpty() -> {
                        binding.emptyState.gone()
                        if (value.toBeCleared) {
                            binding.reviewList.clear()
                        }
                        binding.reviewList.addDatas(
                            value.reviewList.map {
                                MovieReviewUiKitModel(
                                    author = it.author.orEmpty(),
                                    authorUsername = it.authorUsername?.orEmpty(),
                                    authorAvatarPath = it.authorAvatarPath?.orEmpty(),
                                    content = it.content.orEmpty(),
                                    createdAt = it.createdAt.orEmpty(),
                                    id = it.id.orEmpty(),
                                    updatedAt = it.updatedAt.orEmpty(),
                                    url = it.url.orEmpty()
                                )
                            }
                        )
                        binding.reviewList.setUiState(UIKitState.SUCCESS)
                    }
                }
            }
        }
    }


    override fun initUI() {

        binding.reviewList.setClickInterface(object :UIKitMovieReviewSnipList.OnSnipListClickInterface{
            override fun onClick(link: String) {

            }

        })


        binding.reviewList.loadMoreListener = object :UIKitMovieReviewSnipList.LoadMoreListener{
            override fun onLoadMore(lastId: Int, calledId: String, isCalled: Boolean) {
                if (isCalled.not()) {
                    viewModel.getMovieByGenre(lastId, getMovieId())
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