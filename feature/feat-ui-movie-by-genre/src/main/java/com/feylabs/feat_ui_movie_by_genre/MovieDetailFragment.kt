package com.feylabs.feat_ui_movie_by_genre

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.feylabs.core.base.BaseFragment
import com.feylabs.feat_ui_movie_by_genre.databinding.FragmentMovieDetailBinding
import com.feylabs.feat_ui_movie_by_genre.util.pref.MyPreference
import com.feylabs.uikit.listcomponent.movie_list.MovieUiKitModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(
    FragmentMovieDetailBinding::inflate
) {


    companion object{
        const val MOVIE_PASS = "168MV"
    }

    val viewModel: MovieByGenreViewModel by viewModels()
    override fun initData() {
        viewModel.getMovieByGenre(1,getGenreId())
    }

    private fun decodeMovieObject(): MovieUiKitModel? {
        val json = MyPreference(requireContext()).getPrefString(MOVIE_PASS)
        if (json?.isNotEmpty() == true) {
            val gson = Gson()
            return gson.fromJson(json, MovieUiKitModel::class.java)
        }
        return null;
    }

    private fun getGenreId(): Int {
        val genreId = arguments?.getString("genreId")?.toIntOrNull()  ?: -99
        return genreId;
    }


    override fun initObserver() {
    }


    override fun initUI() {
        if(decodeMovieObject()!=null){
            var movieData = decodeMovieObject()
            movieData?.let {
                binding.labelMovieTitle.text=movieData.title
                binding.labelMovieDate.text=it.releaseDate
                binding.labelStoryboard.text=it.overview


                Glide
                    .with(requireContext())
                    .load("https://image.tmdb.org/t/p/original/"+ it.backdropPath)
                    .dontAnimate() //so there's no weird crossfade
                    .into(binding.imgBackdrop)


                binding.labelMovieLanguage.text=it.originalLanguage;
                with(binding){
                    when (it.popularity) {
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
                    .load("https://image.tmdb.org/t/p/original/"+ it.posterPath)
                    .centerCrop()
                    .dontAnimate() //so there's no weird crossfade
                    .into(binding.imgCover)

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