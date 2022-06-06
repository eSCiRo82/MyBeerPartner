package com.citibox.mybeerpartner.view.fragment.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.citibox.mybeerpartner.model.CharacterModel
import com.citibox.mybeerpartner.view.databinding.ItemCharacterBinding
import com.citibox.mybeerpartner.view.extension.toItem
import com.citibox.mybeerpartner.view.util.image.ImageDownloader

class CharactersListAdapter(
    private val imageDownloader: ImageDownloader,
    private val onClick: ((CharacterModel) -> Unit)? = null
): RecyclerView.Adapter<CharacterViewHolder>() {

    private val _characters: MutableList<CharacterModel> = mutableListOf()
    var characters: List<CharacterModel>
        get() = _characters
        set(value) {
            val count = _characters.size
            notifyItemRangeRemoved(0, count)
            _characters.clear()
            _characters.addAll(value)
            notifyItemRangeInserted(count, characters.size)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(imageDownloader, binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) =
        holder.bind(characters[position].toItem()) {
            onClick?.invoke(_characters[position])
        }

    override fun getItemCount(): Int = characters.size
}