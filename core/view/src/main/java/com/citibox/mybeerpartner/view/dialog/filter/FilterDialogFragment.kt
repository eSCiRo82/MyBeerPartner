package com.citibox.mybeerpartner.view.dialog.filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.citibox.mybeerpartner.view.R
import com.citibox.mybeerpartner.view.databinding.DialogFilterFragmentBinding
import com.citibox.mybeerpartner.view.dialog.BaseDialogFragment
import com.citibox.mybeerpartner.view.listeners.OnSafeClickListener

class FilterDialogFragment constructor(
    private val filter: CharSequence,
    private val onFilter: (String) -> Unit,
    private val onRemove: () -> Unit
): BaseDialogFragment(R.layout.dialog_filter_fragment) {

    private lateinit var binding: DialogFilterFragmentBinding

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DialogFilterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUI() {
        with(binding) {
            filterEdittext.setText(filter)
            filterButton.setOnClickListener(
                OnSafeClickListener(0) {
                    onFilter(filterEdittext.text.toString())
                    dismiss()
                }
            )

            removeFilterButton.setOnClickListener(
                OnSafeClickListener(0) {
                    onRemove()
                    dismiss()
                }
            )
        }
    }

    companion object
    {
        fun showFilter(
            fragmentManager: FragmentManager,
            filter: CharSequence,
            onFilter: (String) -> Unit,
            onRemove: () -> Unit
        ) = FilterDialogFragment(
            filter = filter,
            onFilter = onFilter,
            onRemove = onRemove
        ).apply {
            show(fragmentManager, "meeting_dialog_fragment")
        }
    }
}