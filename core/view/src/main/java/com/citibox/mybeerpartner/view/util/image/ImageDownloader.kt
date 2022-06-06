package com.citibox.mybeerpartner.view.util.image

import android.widget.ImageView
import retrofit2.http.Url
import javax.inject.Inject

class ImageDownloader @Inject constructor(
    private val imageApi: ImageApi
) {

    fun download(@Url url: String, imageView: ImageView?) {
        ImageCache.get(url)?.let { image ->
            imageView?.setImageBitmap(image)
        } ?: run {
            val entryPoint = url.substring("https://rickandmortyapi.com/".length)
            imageApi.downloadImage(entryPoint).enqueue(ImageDownloaderCallback(url, imageView))
        }
    }
}