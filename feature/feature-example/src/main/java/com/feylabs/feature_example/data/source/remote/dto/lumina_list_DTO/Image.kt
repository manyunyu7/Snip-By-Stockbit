package com.feylabs.feature_example.data.source.remote.dto.lumina_list_DTO


import androidx.annotation.Keep
import com.feylabs.feature_example.data.source.local.entity.LuminaItemEntity
import com.feylabs.feature_example.domain.ui_model.LuminaUIModel
import com.google.gson.annotations.SerializedName

@Keep
data class Image(
    @SerializedName("category")
    var category: String = "",
    @SerializedName("cfg")
    var cfg: Int = 0,
    @SerializedName("createdAt")
    var createdAt: String = "",
    @SerializedName("height")
    var height: Int = 0,
    @SerializedName("_id")
    var id: String = "",
    @SerializedName("isFavourite")
    var isFavourite: Boolean = false,
    @SerializedName("isUpscaled")
    var isUpscaled: Boolean? = false,
    @SerializedName("link")
    var link: String = "",
    @SerializedName("model")
    var model: String = "",
    @SerializedName("negativePrompt")
    var negativePrompt: String = "",
    @SerializedName("prompt")
    var prompt: String = "",
    @SerializedName("seed")
    var seed: Long? = 0,
    @SerializedName("steps")
    var steps: Int = 0,
    @SerializedName("subCategory")
    var subCategory: String = "",
    @SerializedName("updatedAt")
    var updatedAt: String = "",
    @SerializedName("url")
    var url: String = "",
    @SerializedName("width")
    var width: Int = 0
) {
    fun toLuminaUIModel(): LuminaUIModel {
        return LuminaUIModel(
            id = this.id,
            prompt = this.prompt,
            url = this.url,
            category = this.category,
            model = this.model,
        )
    }

    fun toLuminaItemEntity(): LuminaItemEntity {
        return LuminaItemEntity(
            id = this.id,
            prompt = this.prompt,
            url = this.url,
            category = this.category,
            model = this.model
        )
    }
}