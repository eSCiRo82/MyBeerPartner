package com.citibox.mybeerpartner.view.util.image

import android.graphics.Bitmap

object ImageCache {

    private val cache: MutableMap<CharSequence, Bitmap> = mutableMapOf()

    fun set(key: CharSequence, image: Bitmap) = cache.put(key, image)

    fun get(key: CharSequence) : Bitmap? = cache[key]

    fun clear() = cache.clear()
}