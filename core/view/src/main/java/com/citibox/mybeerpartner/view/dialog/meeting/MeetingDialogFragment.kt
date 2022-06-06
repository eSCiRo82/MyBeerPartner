package com.citibox.mybeerpartner.view.dialog.meeting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.citibox.mybeerpartner.common.constant.DateFormatPattern
import com.citibox.mybeerpartner.common.di.ComponentProvider
import com.citibox.mybeerpartner.model.CharacterModel
import com.citibox.mybeerpartner.model.LocationModel
import com.citibox.mybeerpartner.view.R
import com.citibox.mybeerpartner.view.databinding.DialogFragmentBeerPartnerBinding
import com.citibox.mybeerpartner.view.di.CoreViewSubcomponent
import com.citibox.mybeerpartner.view.dialog.BaseDialogFragment
import com.citibox.mybeerpartner.view.util.image.ImageDownloader
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MeetingDialogFragment constructor(
    private val character: CharacterModel,
    private val beerPartner: CharacterModel,
    private val location: LocationModel,
    private val episodes: Int,
    private val meetingEpisode: Date,
    private val lastEpisode: Date
): BaseDialogFragment(R.layout.dialog_fragment_beer_partner) {

    @Inject internal lateinit var imageDownloader: ImageDownloader

    private lateinit var binding: DialogFragmentBeerPartnerBinding

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DialogFragmentBeerPartnerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ((context.applicationContext as ComponentProvider)
            .components[CoreViewSubcomponent::class.java] as CoreViewSubcomponent)
            .inject(this)
    }

    override fun setupUI() {
        val dateFormat = SimpleDateFormat(DateFormatPattern, Locale.US)
        with(binding) {
            selectedNameTextview.text = character.name
            partnerNameTextview.text = beerPartner.name
            locationTextview.text = location.name
            descriptionContendersTextview.text = getString(R.string.description_of_the_contenders,
                dateFormat.format(meetingEpisode),
                dateFormat.format(lastEpisode),
                episodes
            )
            beersOutButton.setOnClickListener { dismiss() }
            imageDownloader.download(character.image.toString(), selectedCharacterImage)
            imageDownloader.download(beerPartner.image.toString(), partnerCharacterImage)
        }
    }

    companion object
    {
        fun showMeeting(
            fragmentManager: FragmentManager,
            character: CharacterModel,
            beerPartner: CharacterModel,
            location: LocationModel,
            episodes: Int,
            meetingEpisode: Date,
            lastEpisode: Date
        ) = MeetingDialogFragment(
            character = character,
            beerPartner = beerPartner,
            location = location,
            episodes = episodes,
            meetingEpisode = meetingEpisode,
            lastEpisode = lastEpisode
        ).apply {
            show(fragmentManager, "meeting_dialog_fragment")
        }
    }
}