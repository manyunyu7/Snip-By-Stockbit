package com.feylabs.feature_example.data.source.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.feylabs.feature_example.domain.ui_model.LuminaUIModel

@Keep
@Entity(tableName = "lumina_items")
data class LuminaItemEntity(
    @PrimaryKey val id: String,
    val prompt: String,
    val category: String,
    val url: String,
    val model: String
) {
    fun toLuminaUIModel(): LuminaUIModel {
        return LuminaUIModel(
            id = id,
            prompt = prompt,
            category = category,
            url = url,
            model = this.model
        )
    }
}