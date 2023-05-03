package com.feylabs.uikit.listcomponent.unboxingstock

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feylabs.core.helper.date.UtilDate.convertIsoDateStringToIndonesianDateString
import com.feylabs.uikit.util.ImageViewUtil.loadImageFromURL
import com.feylabs.uikit.databinding.CustomUikitInfoCardBinding as AdapterBinding
import com.feylabs.uikit.listcomponent.uikitmodel.UnboxingSectoralUIKitModel as AdapterModel

class UnboxingItemAdapter() :
    RecyclerView.Adapter<UnboxingItemAdapter.ViewHolder>() {

    var page = 1
    val data = mutableListOf<AdapterModel>()
    lateinit var itemInterface: ItemInterface

    var isGrid = false

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

                if (isGrid) {
                    val layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        binding.root.layoutParams.height
                    )
                    binding.root.layoutParams = layoutParams
                }

                with(binding) {
                    binding.tvTitle.text = data.title
                    binding.tvContent.text = data.description
                    binding.tvSecondary.text =
                        data.date.convertIsoDateStringToIndonesianDateString()
                    this.ivThumbnail.loadImageFromURL(context, data.image)
                }
            }
        }
    }

    interface ItemInterface {
        fun onClick(data: AdapterModel)
    }
}