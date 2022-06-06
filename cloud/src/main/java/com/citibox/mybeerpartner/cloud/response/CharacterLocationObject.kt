package com.citibox.mybeerpartner.cloud.response

import com.google.gson.annotations.SerializedName

data class CharacterLocationObject(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)