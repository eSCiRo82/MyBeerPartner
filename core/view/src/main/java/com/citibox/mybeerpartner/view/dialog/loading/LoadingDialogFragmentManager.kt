package com.citibox.mybeerpartner.view.dialog.loading

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import javax.inject.Inject

interface DialogFragmentManager

class LoadingDialogFragmentManager @Inject constructor(): DialogFragmentManager
{
    private var job: Job? = null

    fun showLoading(fragment: Fragment, message: CharSequence)
    {
        job?.cancel()
        job = fragment.lifecycleScope.launchWhenCreated {
            try
            {
                if (!hasVisibleDialog(fragment)) {
                    if (job?.isActive == true) {
                        LoadingDialogFragment.showLoading(fragment.childFragmentManager, message)
                    }
                }
            }
            catch (e: Exception) {  }
        }
    }

    fun hideLoading(fragment: Fragment)
    {
        job?.cancel()
        fragment.lifecycleScope.launchWhenCreated {
            try
            {
                (fragment.childFragmentManager.findFragmentByTag(
                    LoadingDialogFragment.LOADING_DIALOG_TAG
                ) as? DialogFragment)?.dismissAllowingStateLoss()
            }
            catch (e: Exception) {  }
        }
    }

    private fun hasVisibleDialog(fragment: Fragment): Boolean {
        if (fragment.activity != null && fragment.isAdded && fragment.childFragmentManager.fragments.isNotEmpty()) {
            return fragment.childFragmentManager.fragments.any { it is DialogFragment && fragment.isVisible }
        }
        return false
    }
}