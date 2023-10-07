package com.feylabs.feat_ui_home.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.feylabs.core.base.BaseFragment
import com.feylabs.core.helper.toast.ToastHelper.showToast
import com.feylabs.shared_dependencies.R as sharedR
import com.feylabs.feat_ui_home.databinding.FragmentSnipsHomeBinding
import com.feylabs.snips.domain.uimodel.SnipsUIModel
import com.feylabs.uikit.listcomponent.movie_genre.MovieGenreItemAdapter
import com.feylabs.uikit.listcomponent.movie_genre.UIKitUnboxingMovieGenreList
import com.feylabs.uikit.listcomponent.snip.UIKitSnipList
import com.feylabs.uikit.listcomponent.uikitmodel.MovieGenreUIKitModel
import com.feylabs.uikit.listcomponent.uikitmodel.UnboxingSectoralUIKitModel
import com.feylabs.uikit.listcomponent.unboxingstock.OnUnboxingStockListOnClickInterface
import com.feylabs.unboxing.domain.uimodel.UnboxingListItemUIModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@AndroidEntryPoint
class SnipsHomeFragment : BaseFragment<FragmentSnipsHomeBinding>(
    FragmentSnipsHomeBinding::inflate
) {

    val viewModel: SnipsHomeViewModel by viewModels()

    override fun initUI() {
        hideActionBar()
    }

    override fun initObserver() {

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.movieGenreListValue.collect { state ->
                when {
                    state.isLoading -> {
                        // Show loading progress
                    }

                    state.error.isNotBlank() -> {
                        // Show error message
                    }

                    state.movieList.isNotEmpty() -> {
                        state.movieList.forEachIndexed { index, unboxingListItemUIModel ->
                        }
                        binding.movieGenre.addDatas(state.movieList.map {
                            MovieGenreUIKitModel(
                                title = it.title,
                                id = it.id,
                                image = it.getImageUrl()
                            )
                        })
                    }
                }
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.unboxingStockListValue.collect { state ->
                when {
                    state.isLoading -> {
                        // Show loading progress
                    }

                    state.error.isNotBlank() -> {
                        // Show error message
                    }

                    state.unboxingList.isNotEmpty() -> {
                        state.unboxingList.forEachIndexed { index, unboxingListItemUIModel ->
                        }
                        binding.unboxingStock.addDatas(state.unboxingList.map {
                            it.toUnboxingSectoralUIKit()
                        })
                    }
                }
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.snipListValue.collect { state ->
                when {
                    state.isLoading -> {
                        // Show loading progress
                    }

                    state.error.isNotBlank() -> {
                        // Show error message
                    }

                    state.snipList.isNotEmpty() -> {
                        binding.snipList.addDatas(state.snipList.take(3).map {
                            it.toUnboxingSectoralUIKit()
                        })
                    }
                }
            }

        }
        CoroutineScope(Dispatchers.Main).launch {
            // Observe changes in the unboxing sectoral list state
            viewModel.unboxingSectoralListValue.collect { state ->
                when {
                    state.isLoading -> {
                        // Show loading progress
                    }

                    state.error.isNotBlank() -> {
                        // Show error message
                    }

                    state.unboxingList.isNotEmpty() -> {
                        val idsToFilter =
                            listOf(206, 205, 34, 203, 4, 202, 199, 204) // IDs to filter by
                        binding.unboxingSectoral.addDatas(state.unboxingList.map {
                            it.toUnboxingSectoralUIKit()
                        }.filter {
                            idsToFilter.contains(it.id)
                        }.sortedBy {
                            idsToFilter.indexOf(it.id)
                        })
                    }
                }
            }
        }
    }

    override fun initAction() {

        binding.menuSnip.onClickListener {
            val deepLink = Uri.parse(getString(sharedR.string.route_movies_home)).buildUpon().build()
            findNavController().navigate(deepLink)
        }

        binding.menuUnboxingSector.onClickListener {
            val deepLink =
                Uri.parse(getString(sharedR.string.route_snips_unboxing_test)).buildUpon().build()
            findNavController().navigate(deepLink)
        }

        binding.menuUnboxingStock.onClickListener {
            val deepLink =
                Uri.parse(getString(sharedR.string.route_snips_unboxing_test)).buildUpon().build()
            findNavController().navigate(deepLink)
        }

        binding.movieGenre.setClickInterface(object :MovieGenreItemAdapter.ItemInterface{
            override fun onClick(string: String) {
                val deepLink = Uri.parse(
                    getString(sharedR.string.route_movie_by_genre)
                        .replace("{genreId}", string)
                )
                    .buildUpon()
                    .build()

                val navOptions = NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .build()

                findNavController().navigate(deepLink, navOptions)
            }

        })
        binding.snipList.setClickInterface(object : UIKitSnipList.OnSnipListClickInterface {
            override fun onClick(link: String) {
                val encodedUrl = URLEncoder.encode(link, StandardCharsets.UTF_8.toString())
                val deepLink = Uri.parse(
                    getString(sharedR.string.route_snips_content_viewer)
                        .replace("{contentId}", encodedUrl)
                        .replace("{contentType}", "snip")
                )
                    .buildUpon()
                    .build()

                val navOptions = NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .build()

                findNavController().navigate(deepLink, navOptions)
            }
        })

        binding.unboxingStock.setClickInterface(object : OnUnboxingStockListOnClickInterface {
            override fun onClick(volume: String) {
                val itemId = volume
                val deeplink = NavDeepLinkRequest.Builder.fromUri(
                    Uri.parse(
                        getString(sharedR.string.route_snips_content_viewer).replace(
                            "{contentId}",
                            itemId
                        ).replace(
                            "{contentType}",
                            "unboxing-stock"
                        )
                    )
                ).build()
                findNavController().navigate(deeplink)
            }
        })

        binding.unboxingSectoral.setClickInterface(object : OnUnboxingStockListOnClickInterface {
            override fun onClick(volume: String) {
                val itemId = volume
                val deeplink = NavDeepLinkRequest.Builder.fromUri(
                    Uri.parse(
                        getString(sharedR.string.route_snips_content_viewer).replace(
                            "{contentId}",
                            itemId
                        ).replace(
                            "{contentType}",
                            "unboxing-sectoral"
                        )
                    )
                ).build()
                findNavController().navigate(deeplink)
            }
        })

    }

    override fun initData() {
        viewModel.getUnboxingStock()
        viewModel.getUnboxingSectoral()
        viewModel.getSnip()
        viewModel.getMovie()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObserver()
        initAction()
        initData()
    }


    private fun UnboxingListItemUIModel.toUnboxingSectoralUIKit() =
        UnboxingSectoralUIKitModel(
            date = this.date,
            id = this.id ?: -99,
            description = this.description,
            image = this.imageUrl,
            title = this.title,
            feyCover = this.feycover,
            volume = this.volume.toString(),
            contentURL = this.url
        )


    private fun SnipsUIModel.toUnboxingSectoralUIKit() =
        UnboxingSectoralUIKitModel(
            date = this.description,
            id = this.id ?: -99,
            description = "",
            image = this.imageUrl,
            title = this.title,
            feyCover = this.imageUrl,
            contentURL = this.url
        )

}


