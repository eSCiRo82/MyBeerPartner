package com.citibox.mybeerpartner.view.fragment.list

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.citibox.mybeerpartner.common.di.ComponentProvider
import com.citibox.mybeerpartner.model.CharacterModel
import com.citibox.mybeerpartner.view.di.CoreViewSubcomponent
import com.citibox.mybeerpartner.view.util.image.ImageDownloader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersListRecyclerView(context: Context, attrs: AttributeSet? = null)
    : RecyclerView(context, attrs)
{
    @Inject internal lateinit var imageDownloader: ImageDownloader

    private val imagesPerPage: Int = 20

    var characters: List<CharacterModel> = listOf()
        set(value) {
            field = value
            loadImages(0, 2*imagesPerPage-1)
            characterListAdapter.characters = value
        }

    var onLayoutCompleted: (() -> Unit)? = null

    var onItemClick: ((CharacterModel) -> Unit)? = null

    private var characterListAdapter : CharactersListAdapter

    private val linearLayoutManager = object: LinearLayoutManager(context, VERTICAL, false) {
        override fun onLayoutCompleted(state: State?) {
            if ((state?.itemCount ?: 0) > 0) {
                onLayoutCompleted?.invoke()
            }
        }
    }

    init
    {
        ((context.applicationContext as ComponentProvider)
            .components[CoreViewSubcomponent::class.java] as CoreViewSubcomponent)
            .inject(this)

        characterListAdapter = CharactersListAdapter(imageDownloader) { onItemClick?.invoke(it) }

        setItemViewCacheSize(20)

        layoutManager = linearLayoutManager
        adapter = characterListAdapter
        addOnScrollListener(object: OnScrollListener() {
            private var currentDownloaded = 2*imagesPerPage

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (linearLayoutManager.childCount + linearLayoutManager.findFirstVisibleItemPosition() > currentDownloaded - imagesPerPage -1) {
                    val end = currentDownloaded+imagesPerPage
                    loadImages(currentDownloaded, end)
                    currentDownloaded = end
                }
            }
        })
    }

    private fun loadImages(start: Int, end: Int) {
        if (start < characters.size) {
            CoroutineScope(Dispatchers.IO).launch {
                characters.subList(start, if (end >= characters.size) characters.size-1 else end).forEach {
                    imageDownloader.download(it.image.toString(), null)
                }
            }
        }
    }
}