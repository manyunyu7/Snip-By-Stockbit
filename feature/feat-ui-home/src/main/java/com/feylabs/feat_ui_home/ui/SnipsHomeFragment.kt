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
import com.feylabs.uikit.listcomponent.movie_genre.MovieGenreItemAdapter
import com.feylabs.uikit.listcomponent.movie_genre.UIKitUnboxingMovieGenreList
import com.feylabs.uikit.listcomponent.snip.UIKitSnipList
import com.feylabs.uikit.listcomponent.uikitmodel.MovieGenreUIKitModel
import com.feylabs.uikit.listcomponent.uikitmodel.UnboxingSectoralUIKitModel
import com.feylabs.uikit.listcomponent.unboxingstock.OnUnboxingStockListOnClickInterface
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
        viewModel.getMovie()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObserver()
        initAction()
        initData()
    }







}


