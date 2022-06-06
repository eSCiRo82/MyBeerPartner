package com.citibox.mybeerpartner.view.di

import com.citibox.mybeerpartner.common.di.DaggerComponent
import com.citibox.mybeerpartner.view.dialog.meeting.MeetingDialogFragment
import com.citibox.mybeerpartner.view.fragment.CharactersListFragment
import com.citibox.mybeerpartner.view.fragment.list.CharactersListRecyclerView
import dagger.Subcomponent

@Subcomponent
interface CoreViewSubcomponent : DaggerComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(): CoreViewSubcomponent
    }

    fun inject(fragment: CharactersListFragment)

    fun inject(dialog: MeetingDialogFragment)

    fun inject(view: CharactersListRecyclerView)
}