package com.feylabs.uikit.listcomponent.snip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feylabs.core.helper.date.UtilDate.convertIsoDateStringToIndonesianDateString
import com.feylabs.uikit.util.ImageViewUtil.loadImageFromURL
import com.feylabs.uikit.databinding.CustomUikitContentItemBinding as AdapterBinding
import com.feylabs.uikit.listcomponent.uikitmodel.UnboxingSectoralUIKitModel as AdapterModel

class UIKitSnipItemAdapter() :
    RecyclerView.Adapter<UIKitSnipItemAdapter.ViewHolder>() {

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

    inner class ViewHolder(private val binding: AdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: AdapterModel) {
            if (binding.root.isInEditMode.not()) {
                var context = this.binding.root.context
                itemView.setOnClickListener {
                    if (::itemInterface.isInitialized)
                        itemInterface.onClick(data)
                }

                with(binding) {
                    binding.tvDescription.visibility = View.VISIBLE
                    binding.tvDescription.text = data.description
                    binding.tvMain.text = data.title
                    binding.tvSubtitle.text = data.date.convertIsoDateStringToIndonesianDateString()
                    binding.ivMainImage.loadImageFromURL(context, data.image)
                }
            }
        }
    }

    interface ItemInterface {
        fun onClick(data: AdapterModel)
    }
}