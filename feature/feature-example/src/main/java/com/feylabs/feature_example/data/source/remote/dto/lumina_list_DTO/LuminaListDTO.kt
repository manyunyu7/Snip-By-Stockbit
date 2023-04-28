package com.feylabs.feature_example.data.source.remote.dto.lumina_list_DTO


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LuminaListDTO(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("err")
    var err: Any? = Any()
)