package com.arthurnagy.droidconberlin.model

import com.google.gson.annotations.SerializedName


data class Speaker(
        @SerializedName("name") val name: String,
        @SerializedName("url") val url: String
)