package com.citibox.mybeerpartner.view.util.image

import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Url

class ImageDownloaderCallback(@Url private val url: String,
                              private val imageView: ImageView?): Callback<ResponseBody> {
    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        if (response.isSuccessful) {
            response.body()?.apply {
                val bitmap = BitmapFactory.decodeStream(byteStream())
                ImageCache.set(url, bitmap)
                imageView?.setImageBitmap(bitmap)
            }
        }
        else
            Log.e("FAILURE", "Rick y Morty failure")
    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) = Unit
}