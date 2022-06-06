package com.citibox.mybeerpartner.view.util.image

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ImageApi {

    @GET
    fun downloadImage(@Url url: String): Call<ResponseBody>
}