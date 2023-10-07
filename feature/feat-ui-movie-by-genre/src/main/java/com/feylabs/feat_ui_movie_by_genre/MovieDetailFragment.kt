package com.feylabs.feat_ui_movie_by_genre

import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.feylabs.core.base.BaseFragment
import com.feylabs.core.helper.toast.ToastHelper.showToast
import com.feylabs.core.helper.view.ViewUtils.gone
import com.feylabs.core.helper.view.ViewUtils.visible
import com.feylabs.feat_ui_movie_by_genre.databinding.FragmentMovieDetailBinding
import com.feylabs.feat_ui_movie_by_genre.util.pref.MyPreference
import com.feylabs.shared_dependencies.R
import com.feylabs.uikit.listcomponent.movie_list.MovieUiKitModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(
    FragmentMovieDetailBinding::inflate
) {

    companion object {
        const val MOVIE_PASS = "168MV"
    }

    val viewModel: MovieDetailViewModel by viewModels()
    override fun initData() {
        viewModel.getMovieDetail(getMovieId())
    }

    private fun getMovieId(): Int {
        if (decodeMovieObject() != null) {
            return decodeMovieObject()?.id ?: -99;
        }
        return -99;
    }


    private fun decodeMovieObject(): MovieUiKitModel? {
        val json = MyPreference(requireContext()).getPrefString(MOVIE_PASS)
        if (json?.isNotEmpty() == true) {
            val gson = Gson()
            return gson.fromJson(json, MovieUiKitModel::class.java)
        }
        return null;
    }
    override fun initObserver() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.movieDetailValue.collect { value ->
                when {
                    value.isLoading -> {
                        binding.loadingIndicator.visible()
                    }

                    value.error.isNotBlank() -> {
                        showToast(value.error)
                    }

                    value.data != null -> {
                        binding.loadingIndicator.gone()
                        val movieData = value.data
                        movieData?.apply {
                            binding.labelMovieTitle.text=title
                            binding.labelMovieDate.text=releaseDate
                            binding.labelProductionHouse.text=productionCompanies
                            binding.labelGenres.text=genres

                            if(videoUrl.isNotEmpty()){
                                binding.trailerContainer.visible()
                                val webView = binding.webView

                                val view = binding.webView
                                view.webViewClient = object : WebViewClient() {
                                    override fun onPageFinished(view: WebView, url: String) {
                                        super.onPageFinished(view, url)
                                        // Inject JavaScript to hide the element with ID "title"
                                        view.loadUrl("javascript:(function() { " +
                                                "var element = document.getElementById('title'); " +
                                                "element.style.display='none'; " +
                                                "})()")
                                        //emulateClick(view)
                                    }
                                }
                                view.settings.setAppCacheEnabled(true)
                                view.settings.javaScriptEnabled = true
                                view.settings.setAppCachePath(requireContext().cacheDir.absolutePath)
                                view.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                                view.settings.mediaPlaybackRequiresUserGesture = false
                                view.loadUrl("${getFullPathVideoUrl()}")

                            }else{
                                binding.trailerContainer.gone()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun emulateClick(webview: WebView?) {
        val delta: Long = 100
        val downTime = SystemClock.uptimeMillis()
        val x = (webview!!.left + webview.width / 2).toFloat() //in the middle of the webview
        val y = (webview.top + webview.height / 2).toFloat()
        val downEvent =
            MotionEvent.obtain(downTime, downTime + delta, MotionEvent.ACTION_DOWN, x, y, 0)
        // change the position of touch event, otherwise, it'll show the menu.
        val upEvent =
            MotionEvent.obtain(downTime, downTime + delta, MotionEvent.ACTION_UP, x + 10, y + 10, 0)
        webview.post {
            if (webview != null) {
                webview.dispatchTouchEvent(downEvent)
                webview.dispatchTouchEvent(upEvent)
            }
        }
    }


    override fun initUI() {
        if (decodeMovieObject() != null) {
            var movieData = decodeMovieObject()
            movieData?.let {
                binding.labelMovieTitle.text = movieData.title
                binding.labelMovieDate.text = it.releaseDate
                binding.labelStoryboard.text = it.overview


                Glide
                    .with(requireContext())
                    .load("https://image.tmdb.org/t/p/original/" + it.backdropPath)
                    .dontAnimate() //so there's no weird crossfade
                    .into(binding.imgBackdrop)


                binding.labelMovieLanguage.text = it.originalLanguage;
                with(binding) {
                        when (it.voteAverage) {
                        in 0.0..2.0 -> {
                            labelMovieRate.text = "⭐"
                        }

                        in 2.1..4.0 -> {
                            labelMovieRate.text = "⭐⭐"
                        }

                        in 4.1..7.0 -> {
                            labelMovieRate.text = "⭐⭐⭐"
                        }

                        in 7.1..8.0 -> {
                            labelMovieRate.text = "⭐⭐⭐⭐"
                        }

                        in 8.1..10.0 -> {
                            labelMovieRate.text = "⭐⭐⭐⭐⭐"
                        }
                    }
                }

                Glide
                    .with(requireContext())
                    .load("https://image.tmdb.org/t/p/original/" + it.posterPath)
                    .centerCrop()
                    .dontAnimate() //so there's no weird crossfade
                    .into(binding.imgCover)

            }
        }
    }


    override fun initAction() {
        binding.labelSeeReview.setOnClickListener {
            val deepLink = Uri.parse(
                getString(R.string.route_movies_reviews)
                    .replace("{id}", getMovieId().toString()))
                .buildUpon()
                .build()
            val navOptions = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .build()

            findNavController().navigate(deepLink, navOptions)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initData()
        initObserver()
        initAction()
    }
}