package com.feylabs.uikit.listcomponent.uikitmodel

data class UnboxingSectoralUIKitModel(
    val id: Int,
    val image: String,
    val title: String,
    val description: String,
    val date: String,
    val feyCover: String = "",
    val contentURL: String = "",
    val volume: String = ""
) {

}