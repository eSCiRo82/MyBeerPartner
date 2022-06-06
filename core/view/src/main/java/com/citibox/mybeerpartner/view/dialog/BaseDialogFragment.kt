package com.citibox.mybeerpartner.view.dialog

import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment(@LayoutRes layout: Int) : DialogFragment(layout)
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        dialog?.window?.apply {
            requestFeature(Window.FEATURE_NO_TITLE)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            isCancelable = false
        }
        return getRootView(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    override fun onStart()
    {
        super.onStart()

        dialog?.window?.apply {
            setLayout((0.9*(if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.R)
            {
                val size = Point()
                windowManager.defaultDisplay.getSize(size)
                size.x.toFloat()
            } else
                windowManager?.currentWindowMetrics?.bounds?.width() ?: 0).toFloat()).toInt(),
                ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    protected abstract fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View

    protected abstract fun setupUI()
}