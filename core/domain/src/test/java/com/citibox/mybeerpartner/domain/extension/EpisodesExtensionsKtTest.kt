package com.citibox.mybeerpartner.domain.extension

import com.citibox.mybeerpartner.common.constant.DateFormatPattern
import com.citibox.mybeerpartner.model.CharacterModel
import com.citibox.mybeerpartner.model.EpisodeModel
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

internal class EpisodesExtensionsKtTest {

    private val dateFormat = SimpleDateFormat(DateFormatPattern, Locale.US)

    private val character1 = CharacterModel(1, "Character1", "Human", "Human", 1, "https://image/image1.url.png")
    private val character2 = CharacterModel(2, "Character2", "Ganimedian", "Alien", 2, "https://image/image2.url.png")
    private val character3 = CharacterModel(3, "Character3", "Robot", "Robot", 3, "https://image/image3.url.png")
    private val character4 = CharacterModel(4, "Character4", "Android", "Robot", 3, "https://image/image4.url.png")
    private val character5 = CharacterModel(5, "Character5", "Unknown", "Unknown", 0, "https://image/image5.url.png")

    private val episode1 = EpisodeModel(1, "episode1",
        Date(dateFormat.parse("January 1, 2021")?.time ?: Calendar.getInstance().timeInMillis))
    private val episode2 = EpisodeModel(2, "episode2",
            Date(dateFormat.parse("February 2, 2021")?.time ?: Calendar.getInstance().timeInMillis))
    private val episode3 = EpisodeModel(3, "episode3",
        Date(dateFormat.parse("March 3, 2021")?.time ?: Calendar.getInstance().timeInMillis))
    private val episode4 = EpisodeModel(4, "episode4",
        Date(dateFormat.parse("April 4, 2021")?.time ?: Calendar.getInstance().timeInMillis))
    private val episode5 = EpisodeModel(5, "episode5",
        Date(dateFormat.parse("May 5, 2021")?.time ?: Calendar.getInstance().timeInMillis))

    private val charactersEpisodes = listOf(
        Pair(character1, listOf(episode1, episode2, episode3, episode4, episode5)),
        Pair(character2, listOf(episode2, episode4)),
        Pair(character3, listOf(episode1, episode3, episode5)),
        Pair(character4, listOf(episode1, episode5)),
        Pair(character5, listOf(episode2, episode3, episode4))
    )

    @Test
    fun `get episodes where two characters participate`() {
        val character1Episodes = listOf(episode1, episode2, episode5)
        val character2Episodes = listOf(episode1, episode3, episode5)

        val result = character1Episodes.match(character2Episodes)

        assertEquals(2, result.size)
        assertEquals(episode1, result[0])
        assertEquals(episode5, result[1])
    }

    @Test
    fun `get episodes where two characters participate for all characters`() {
        val characterEpisodes = listOf(episode1, episode5)

        val result = charactersEpisodes.getEpisodesTogether(characterEpisodes)

        assertEquals(3, result.size)
        assertEquals(character1, result[0].first)
        assertEquals(2, result[0].second.size)
        assertEquals(character3, result[1].first)
        assertEquals(2, result[1].second.size)
        assertEquals(character4, result[2].first)
        assertEquals(2, result[2].second.size)
    }

    @Test
    fun `get first episode where two characters participate for all characters`() {
        val charactersEpisodesMatched = listOf(
            Pair(character2, listOf(episode2, episode4)),
            Pair(character4, listOf(episode1, episode5))
        )
        val result = charactersEpisodesMatched.getFirstEpisodeTogether()

        assertEquals(episode1.airDate, result.key)
    }

    @Test
    fun `get last episode where two characters participate for all characters`() {
        val charactersEpisodesMatched = listOf(
            Pair(character2, listOf(episode2, episode4)),
            Pair(character4, listOf(episode1, episode5))
        )
        val result = charactersEpisodesMatched.getLastEpisodeTogether()

        assertEquals(episode4.airDate, result.key)
    }

    @Test
    fun `get the number of episodes where a character participates`() {
        val result = charactersEpisodes.groupCharactersByNumberEpisodes()

        assertEquals(3, result.size)
        assertEquals(1, result[charactersEpisodes[0].second.size]?.size)
        assertEquals(2, result[charactersEpisodes[1].second.size]?.size)
        assertEquals(2, result[charactersEpisodes[2].second.size]?.size)
    }
}