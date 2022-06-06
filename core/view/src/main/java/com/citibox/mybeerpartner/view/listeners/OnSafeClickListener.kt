package com.citibox.mybeerpartner.view.listeners

import android.os.SystemClock
import android.view.View

private const val defaultInterval = 1000L

class OnSafeClickListener(private val interval: Long = defaultInterval, private val onSafeClick: View.OnClickListener) :
    View.OnClickListener
{
    private var lastClick: Long = 0

    override fun onClick(v: View?)
    {
        if (SystemClock.elapsedRealtime() - lastClick < interval) return
        lastClick = SystemClock.elapsedRealtime()
        onSafeClick.onClick(v)
    }
}