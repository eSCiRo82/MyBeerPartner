package com.citibox.mybeerpartner.data.datasource

import com.citibox.mybeerpartner.database.dao.EpisodeDao
import com.citibox.mybeerpartner.database.entity.EpisodeEntity
import javax.inject.Inject

class EpisodesLocalDataSource @Inject constructor(
    private val episodeDao: EpisodeDao
): MutableDataSource<@JvmSuppressWildcards EpisodeEntity> {
    override fun get(): List<EpisodeEntity> = episodeDao.get()

    override fun set(element: EpisodeEntity) = episodeDao.insert(element)

    override fun setAll(element: List<EpisodeEntity>) = episodeDao.insert(element)

    override fun count(): Int = episodeDao.count()
}