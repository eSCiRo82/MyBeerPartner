package com.citibox.mybeerpartner.data.datasource

import com.citibox.mybeerpartner.database.dao.CharacterDao
import com.citibox.mybeerpartner.database.entity.CharacterEntity
import com.citibox.mybeerpartner.database.type.CharacterGender
import com.citibox.mybeerpartner.database.type.CharacterStatus
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class CharactersLocalDataSourceTest {

    @MockK private lateinit var characterDao: CharacterDao

    private val character1 = CharacterEntity(1, "Character1", CharacterStatus.Alive, "Human", "Human", CharacterGender.Female, 1, "https://image/image1.url.png")
    private val character2 = CharacterEntity(2, "Character2", CharacterStatus.Alive, "Ganimedian", "Alien", CharacterGender.Female, 2, "https://image/image1.url.png")
    private val character3 = CharacterEntity(3, "Character3", CharacterStatus.Alive, "Robot", "Robot", CharacterGender.Female, 3, "https://image/image1.url.png")
    private val character4 = CharacterEntity(4, "Character4", CharacterStatus.Alive, "Android", "Robot", CharacterGender.Female, 3, "https://image/image1.url.png")

    private val characters = listOf(character1, character2, character3, character4)

    private lateinit var dataSource: CharactersLocalDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        dataSource = CharactersLocalDataSource(characterDao)
    }

    @Test
    fun `get characters from database`() {
        every { characterDao.get() } returns characters

        val list = dataSource.get()

        assertEquals(characters.size, list.size)
        assertTrue(list.contains(character1))
        assertTrue(list.contains(character2))
        assertTrue(list.contains(character3))
        assertTrue(list.contains(character4))

        verify(exactly = 1) { characterDao.get() }
        confirmVerified(characterDao)
    }

    @Test
    fun `count characters in database`() {
        every { characterDao.count() } returns characters.size

        val list = dataSource.count()

        assertEquals(characters.size, list)

        verify(exactly = 1) { characterDao.count() }
        confirmVerified(characterDao)
    }
}