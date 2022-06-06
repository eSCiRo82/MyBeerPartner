package com.citibox.mybeerpartner.view.dialog.notification

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import com.citibox.mybeerpartner.view.listeners.OnSafeClickListener
import com.citibox.mybeerpartner.view.R
import com.citibox.mybeerpartner.view.databinding.DialogFragmentNotificationBinding
import com.citibox.mybeerpartner.view.dialog.BaseDialogFragment

class NotificationDialogFragment(
    private val title: CharSequence = "",
    private val message: CharSequence = "",
    private val acceptAction: (() -> Unit)? = null,
    private val cancelAction: (() -> Unit)? = null
): BaseDialogFragment(R.layout.dialog_fragment_notification) {
    private lateinit var binding: DialogFragmentNotificationBinding

    private var acceptOrCancel: Boolean = false

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DialogFragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUI() {
        with(binding) {
            notificationDialogLabelTitle.isVisible = title.isNotEmpty()
            notificationDialogLabelTitle.text = title
            notificationDialogLabelMessage.isVisible = message.isNotEmpty()
            notificationDialogLabelMessage.text = message

            acceptButton.isVisible = acceptAction != null
            acceptButton.setOnClickListener(
                OnSafeClickListener(onSafeClick = {
                    acceptOrCancel = true
                    dismiss()
                })
            )
            cancelButton.isVisible = cancelAction != null
            cancelButton.setOnClickListener(
                OnSafeClickListener(onSafeClick = {
                    acceptOrCancel = false
                    dismiss()
                })
            )
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        when (acceptOrCancel) {
            true -> acceptAction?.invoke()
            false -> cancelAction?.invoke()
        }
    }

    companion object
    {
        fun showNotification(
            fragmentManager: FragmentManager,
            title: CharSequence? = null,
            message: CharSequence? = null,
            acceptAction: (() -> Unit)? = null,
            cancelAction: (() -> Unit)? = null
        ) = NotificationDialogFragment(title = title ?: "",
            message = message ?: "",
            acceptAction = acceptAction,
            cancelAction = cancelAction).apply {
                show(fragmentManager, "notification_dialog${title?.let { "_${it}" }}")
            }
    }
}