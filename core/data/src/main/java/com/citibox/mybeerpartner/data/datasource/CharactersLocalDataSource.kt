package com.citibox.mybeerpartner.data.datasource

import com.citibox.mybeerpartner.database.dao.CharacterDao
import com.citibox.mybeerpartner.database.entity.*
import javax.inject.Inject

interface CharactersDataSource: MutableDataSource<@JvmSuppressWildcards CharacterEntity> {
    fun getLocation(characterId: Long): CharacterAndLocationEntity

    fun getEpisodes(characterId: Long): CharacterAndEpisodesEntity

    fun setLocation(characterId: Long, locationId: Long)

    fun setEpisodes(characterId: Long, episodes: List<Long>)

    fun getCharactersLocations(): List<CharacterAndLocationEntity>

    fun getCharactersEpisodes(): List<CharacterAndEpisodesEntity>

    fun filterByName(name: String): List<CharacterEntity>
}

class CharactersLocalDataSource @Inject constructor(
    private val characterDao: CharacterDao
) : CharactersDataSource {

    override fun count(): Int = characterDao.count()

    override fun get(): List<CharacterEntity> = characterDao.get()

    override fun getLocation(characterId: Long): CharacterAndLocationEntity =
        characterDao.getLocation(characterId)

    override fun getEpisodes(characterId: Long): CharacterAndEpisodesEntity =
        characterDao.getEpisodes(characterId)

    override fun set(element: CharacterEntity) = characterDao.insert(element)

    override fun setAll(element: List<CharacterEntity>) = characterDao.insert(element)

    override fun setLocation(characterId: Long, locationId: Long) =
        characterDao.insert(CharacterLocationRefs(characterId, locationId))

    override fun setEpisodes(characterId: Long, episodes: List<Long>) =
        characterDao.insertEpisodes(
            episodes.map { episodeId -> CharacterEpisodesRefs(characterId, episodeId) }
        )

    override fun getCharactersLocations(): List<CharacterAndLocationEntity> =
        characterDao.getCharactersAndLocations()

    override fun getCharactersEpisodes(): List<CharacterAndEpisodesEntity> =
        characterDao.getCharactersAndEpisodes()

    override fun filterByName(name: String): List<CharacterEntity> = characterDao.filterByName(name)
}