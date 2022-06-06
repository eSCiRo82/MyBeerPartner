package com.citibox.mybeerpartner.cloud.response

import com.google.gson.annotations.SerializedName
import retrofit2.http.Url

data class InfoObject(
    @SerializedName("count")
    val count: Int,
    @SerializedName("pages")
    @Url val pages: Int
)