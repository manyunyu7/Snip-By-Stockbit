package com.feylabs.uikit.listcomponent.movie_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.feylabs.uikit.R
import com.feylabs.uikit.databinding.ItemMovieBinding as AdapterBinding
import com.feylabs.uikit.listcomponent.movie_list.MovieUiKitModel as AdapterModel

class UIKitMovieItemAdapter() :
    RecyclerView.Adapter<UIKitMovieItemAdapter.ViewHolder>() {

    var page = 1
    val data = mutableListOf<AdapterModel>()
    lateinit var itemInterface: ItemInterface

    fun setInterface(itemInterface: ItemInterface) {
        this.itemInterface = itemInterface
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun getLastId(): Int {
        return data.last().id
    }

    inner class ViewHolder(private val binding: AdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: AdapterModel) {
            if (binding.root.isInEditMode.not()) {
                val context = this.binding.root.context
                itemView.setOnClickListener {
                    if (::itemInterface.isInitialized)
                        itemInterface.onClick(data)
                }




                with(binding) {
                    labelMovieTitle.text = data.title
                    labelMovieDesc.text = data.overview
                    labelMovieDate.text = data.releaseDate

                    binding.labelMovieTitle.text = data.title
                    when (data.voteAverage) {
                        in 0.0..2.0 -> {
                            labelMovieRate.text = context.getString(R.string.star1)
                        }

                        in 2.1..4.0 -> {
                            labelMovieRate.text = context.getString(R.string.star2)
                        }

                        in 4.1..7.0 -> {
                            labelMovieRate.text = context.getString(R.string.start3)
                        }

                        in 7.1..8.0 -> {
                            labelMovieRate.text = context.getString(R.string.star4)
                        }

                        in 8.1..10.0 -> {
                            labelMovieRate.text = context.getString(R.string.star5)
                        }
                    }
                    val imageBasePath = "https://image.tmdb.org/t/p/original"
                    Glide
                        .with(context)
                        .load(imageBasePath + data.posterPath)
                        .centerCrop()
                        .placeholder(R.drawable.dark_placeholder)
                        .thumbnail(Glide.with(context).load(R.raw.loading))
                        .dontAnimate()
                        .into(binding.imgCover)
                }
            }
        }
    }

    interface ItemInterface {
        fun onClick(data: AdapterModel)
    }
}