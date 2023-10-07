package com.feylabs.movie_genre.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feylabs.movie_genre.R
import com.feylabs.movie_genre.databinding.ItemLuminaBinding as Binding
import com.feylabs.movie_genre.domain.uimodel.MovieGenreUIModel as AdapterModel


class LuminaListAdapter : RecyclerView.Adapter<LuminaListAdapter.AdapterViewHolder>() {

    var page = 1

    val data = mutableListOf<AdapterModel>()
    lateinit var itemInterface: ItemInterface


    fun setInterface(itemInterface: ItemInterface) {
        this.itemInterface = itemInterface
    }

    inner class AdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = Binding.bind(view)

        fun bind(data: AdapterModel) {
            itemView.setOnClickListener {
                if (::itemInterface.isInitialized)
                    itemInterface.onclick(data)
            }

            binding.base.title=data.title
            binding.base.content="content"
            binding.base.setImage("")
            binding.base.subtitle="subtitle"
        }
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    fun addData(model: MutableList<AdapterModel>) {
        for (i in 0 until model.size) {
            data.add(model[i])
            notifyItemInserted(data.size - 1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lumina, parent, false)
        return AdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface ItemInterface {
        fun onclick(model: AdapterModel)
    }

}
