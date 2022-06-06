package com.citibox.mybeerpartner.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.citibox.mybeerpartner.view.dialog.loading.LoadingDialogFragmentManager
import com.citibox.mybeerpartner.common.di.ComponentProvider
import com.citibox.mybeerpartner.common.flow.flowObserverCollector
import com.citibox.mybeerpartner.view.R
import com.citibox.mybeerpartner.view.databinding.FragmentCharactersListBinding
import com.citibox.mybeerpartner.view.di.CoreViewSubcomponent
import com.citibox.mybeerpartner.view.dialog.filter.FilterDialogFragment
import com.citibox.mybeerpartner.view.dialog.notification.NotificationDialogFragment
import com.citibox.mybeerpartner.view.dialog.meeting.MeetingDialogFragment
import com.citibox.mybeerpartner.viewmodel.CharactersListViewModel
import com.citibox.mybeerpartner.viewmodel.factory.ViewModelFactory
import javax.inject.Inject

class CharactersListFragment : Fragment(R.layout.fragment_characters_list) {

    @Inject internal lateinit var viewModelFactory: ViewModelFactory

    @Inject internal lateinit var loadingDialogFragmentManager: LoadingDialogFragmentManager

    private lateinit var binding: FragmentCharactersListBinding

    private val viewModel: CharactersListViewModel by viewModels { viewModelFactory }

    private var searchingDialog: NotificationDialogFragment? = null
    private var notificationDialogFragment: NotificationDialogFragment? = null
    private var filterDialogFragment: FilterDialogFragment? = null
    private var meetingDialogFragment: MeetingDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStateObservers()
        initEventObservers()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCharactersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding.charactersList) {
            onLayoutCompleted = {loadingDialogFragmentManager.hideLoading(this@CharactersListFragment) }
            onItemClick = { selected ->
                searchingDialog = NotificationDialogFragment(message = getString(R.string.searching_beer_partner, selected.name))
                searchingDialog?.show(childFragmentManager, "searching_dialog")
                viewModel.searchBeerPartner(selected)
            }
        }

        viewModel.downloadCharacters()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ((context.applicationContext as ComponentProvider)
            .components[CoreViewSubcomponent::class.java] as CoreViewSubcomponent)
            .inject(this)

        loadingDialogFragmentManager.showLoading(this, getString(R.string.downloading_resources))
    }

    override fun onPause() {
        super.onPause()
        searchingDialog?.dismiss()
        notificationDialogFragment?.dismiss()
        filterDialogFragment?.dismiss()
        meetingDialogFragment?.dismiss()
    }

    private fun initStateObservers() {
        // Waiting for the change of state in the flow
        viewModel.characterStates.flowObserverCollector(this) { state ->
            when (state) {
                is CharactersListViewModel.State.Characters -> {
                    binding.charactersList.characters = state.characters
                }
                else -> Unit
            }
        }
    }

    private fun initEventObservers() {
        viewModel.characterEvents.flowObserverCollector(this) { event ->
            when (event) {
                CharactersListViewModel.Event.DownloadError -> Unit
                CharactersListViewModel.Event.DownloadFinished -> viewModel.getCharacters()
                CharactersListViewModel.Event.NoCharacters -> Unit
                is CharactersListViewModel.Event.BeerPartner -> {
                    searchingDialog?.dismiss()
                    viewModel.makeAppointment(event.character, event.beerPartner)
                }
                is CharactersListViewModel.Event.Meeting -> {
                    meetingDialogFragment = MeetingDialogFragment.showMeeting(childFragmentManager,
                        event.character,
                        event.beerPartner,
                        event.location,
                        event.episodes,
                        event.meetingEpisode,
                        event.lastEpisode)
                }
                CharactersListViewModel.Event.NoBeerPartnerFound -> {
                    searchingDialog?.dismiss()
                    notificationDialogFragment = NotificationDialogFragment.showNotification(
                        fragmentManager = childFragmentManager,
                        title = getString(R.string.no_beer_partner_found_title),
                        message = getString(R.string.no_beer_partner_found_message),
                        acceptAction = { })
                }
                CharactersListViewModel.Event.WhereAreYou -> {
                    searchingDialog?.dismiss()
                    notificationDialogFragment = NotificationDialogFragment.showNotification(
                        fragmentManager = childFragmentManager,
                        title = getString(R.string.where_are_you_title),
                        message = getString(R.string.where_are_you_message),
                        acceptAction = { })
                }
            }
        }
    }

    fun showFilterDialog() {
        filterDialogFragment = FilterDialogFragment.showFilter(
            childFragmentManager,
            viewModel.lastFilter,
            { name -> viewModel.filterCharactersByName(name) },
            { viewModel.getCharacters() }
        )
    }
}