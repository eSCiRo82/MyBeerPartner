package com.citibox.mybeerpartner.data.repository

import com.citibox.mybeerpartner.data.datasource.*
import com.citibox.mybeerpartner.data.extension.toModel
import com.citibox.mybeerpartner.database.entity.CharacterEntity
import com.citibox.mybeerpartner.database.entity.ResourceInfoEntity
import com.citibox.mybeerpartner.database.type.CharacterGender
import com.citibox.mybeerpartner.database.type.CharacterStatus
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class RickAndMortyDataRepositoryTest {

    @MockK lateinit var resourcesInfoLocalDataSource: ResourcesInfoLocalDataSource
    @MockK lateinit var  charactersLocalDataSource: CharactersLocalDataSource
    @MockK lateinit var  charactersRemoteDataSource: CharactersRemoteDataSource
    @MockK lateinit var  locationsLocalDataSource: LocationsLocalDataSource
    @MockK lateinit var  locationsRemoteDataSource: LocationsRemoteDataSource
    @MockK lateinit var  episodesLocalDataSource: EpisodesLocalDataSource
    @MockK lateinit var  episodesRemoteDataSource: EpisodesRemoteDataSource

    private lateinit var repository: RickAndMortyDataRepository

    private val character1 = CharacterEntity(1, "Character1", CharacterStatus.Alive, "Human", "Human", CharacterGender.Female, 1, "https://image/image1.url.png")
    private val character2 = CharacterEntity(2, "Character2", CharacterStatus.Alive, "Ganimedian", "Alien", CharacterGender.Female, 2, "https://image/image1.url.png")
    private val character3 = CharacterEntity(3, "Character3", CharacterStatus.Alive, "Robot", "Robot", CharacterGender.Female, 3, "https://image/image1.url.png")
    private val character4 = CharacterEntity(4, "Character4", CharacterStatus.Alive, "Android", "Robot", CharacterGender.Female, 3, "https://image/image1.url.png")

    private val characters = listOf(character1, character2, character3, character4)

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        repository = RickAndMortyDataRepository(
            resourcesInfoLocalDataSource,
            charactersLocalDataSource,
            charactersRemoteDataSource,
            locationsLocalDataSource,
            locationsRemoteDataSource,
            episodesLocalDataSource,
            episodesRemoteDataSource
        )
    }

    @Test
    fun `get characters from database`() {
        every { resourcesInfoLocalDataSource.get() } returns listOf(ResourceInfoEntity("characters", 4, 1))
        every { charactersLocalDataSource.get() } returns characters

        val response = repository.getCharacters()

        assertEquals(characters.toModel(), response)
        verify(exactly = 1) { charactersLocalDataSource.get() }
        confirmVerified(charactersLocalDataSource)
    }

    @Test
    fun `get characters from cache`() {
        every { resourcesInfoLocalDataSource.get() } returns listOf(ResourceInfoEntity("characters", 4, 1))
        every { charactersLocalDataSource.get() } returns characters

        // First call to fill the cache and use the charactersLocalDataSource.get()
        repository.getCharacters()
        // Second call that not use charactersLocalDataSource.get()
        val response = repository.getCharacters()

        assertEquals(characters.toModel(), response)
        // Only one use of charactersLocalDataSource.get()
        verify(exactly = 1) { charactersLocalDataSource.get() }
        confirmVerified(charactersLocalDataSource)
    }
}