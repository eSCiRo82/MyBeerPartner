package com.citibox.mybeerpartner.view.fragment.list

import androidx.recyclerview.widget.RecyclerView
import com.citibox.mybeerpartner.view.R
import com.citibox.mybeerpartner.view.databinding.ItemCharacterBinding
import com.citibox.mybeerpartner.view.listeners.OnSafeClickListener
import com.citibox.mybeerpartner.view.util.image.ImageDownloader

class CharacterViewHolder(
    private val imageDownloader: ImageDownloader,
    private val binding: ItemCharacterBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CharacterItem, onClick: (() -> Unit)? = null) {
        with (binding) {
            root.setOnClickListener(OnSafeClickListener(500) { onClick?.invoke() })
            characterImage.setImageResource(R.drawable.ic_launcher_background)
            characterNameValue.text = item.name
            characterSpeciesValue.text = item.species
            characterTypeValue.text = item.type
            imageDownloader.download(item.image.toString(), characterImage)
        }
    }
}