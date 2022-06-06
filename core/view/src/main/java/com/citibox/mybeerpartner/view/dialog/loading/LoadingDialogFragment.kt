package com.citibox.mybeerpartner.view.dialog.loading

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.citibox.mybeerpartner.view.R
import com.citibox.mybeerpartner.view.databinding.DialogFragmentLoadingBinding
import com.citibox.mybeerpartner.view.dialog.BaseDialogFragment

class LoadingDialogFragment(
    private val message: CharSequence = ""): BaseDialogFragment(R.layout.dialog_fragment_loading) {
    private lateinit var binding: DialogFragmentLoadingBinding

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DialogFragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }

    override fun setupUI() {
        binding.loadingMessageTextview.text = message
    }

    companion object {
        const val LOADING_DIALOG_TAG = "loading_dialog"

        fun showLoading(fragmentManager: FragmentManager, message: CharSequence) =
            LoadingDialogFragment(message).apply {
                show(fragmentManager, LOADING_DIALOG_TAG)
            }
    }
}