package com.feylabs.uikit.listcomponent.movie_review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feylabs.core.helper.date.UtilDate.convertIsoDateStringToIndonesianDateString
import com.feylabs.uikit.util.ImageViewUtil.loadImageFromURL
import com.feylabs.uikit.databinding.CustomUikitContentReviewItemsBinding as AdapterBinding
import com.feylabs.uikit.listcomponent.movie_review.MovieReviewUiKitModel as AdapterModel

class UIKitMovieReviewItemAdapter() :
    RecyclerView.Adapter<UIKitMovieReviewItemAdapter.ViewHolder>() {

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
        return data.last().id?.toIntOrNull()?:-99
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
                    binding.tvDescription.visibility = View.VISIBLE
                    binding.tvDescription.text = data.content
                    binding.tvMain.text = data.author
                    binding.tvSubtitle.text = data.createdAt
                    binding.ivMainxImage.loadImageFromURL(context, "https://secure.gravatar.com/avatar/"+data.authorAvatarPath)
                }
            }
        }
    }

    interface ItemInterface {
        fun onClick(data: AdapterModel)
    }
}