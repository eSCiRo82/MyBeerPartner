package com.citibox.mybeerpartner.domain.usecase

import app.cash.turbine.test
import com.citibox.mybeerpartner.common.constant.DateFormatPattern
import com.citibox.mybeerpartner.common.extension.onFailure
import com.citibox.mybeerpartner.common.extension.onSuccess
import com.citibox.mybeerpartner.data.repository.RickAndMortyDataRepository
import com.citibox.mybeerpartner.domain.type.UseCaseError
import com.citibox.mybeerpartner.model.CharacterModel
import com.citibox.mybeerpartner.model.EpisodeModel
import com.citibox.mybeerpartner.test.CoroutineTestRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
internal class SearchBeerPartnerUseCaseTest {

    @get:Rule val coroutineTestRule = CoroutineTestRule()

    private lateinit var searchBeerPartnerUseCase: SearchBeerPartnerUseCase

    @MockK internal lateinit var repository: RickAndMortyDataRepository

    private val dateFormat = SimpleDateFormat(DateFormatPattern, Locale.US)

    private val character1 = CharacterModel(1, "Character1", "Human", "Human", 1, "https://image/image1.url.png")
    private val character2 = CharacterModel(2, "Character2", "Ganimedian", "Alien", 3, "https://image/image2.url.png")
    private val character3 = CharacterModel(3, "Character3", "Robot", "Robot", 3, "https://image/image3.url.png")
    private val character4 = CharacterModel(4, "Character4", "Android", "Robot", 3, "https://image/image4.url.png")
    private val character5 = CharacterModel(5, "Character5", "Unknown", "Unknown", 0, "https://image/image5.url.png")

    private val episode1 = EpisodeModel(1, "episode1",
        Date(dateFormat.parse("January 1, 2021")?.time ?: Calendar.getInstance().timeInMillis)
    )
    private val episode2 = EpisodeModel(2, "episode2",
        Date(dateFormat.parse("February 2, 2021")?.time ?: Calendar.getInstance().timeInMillis)
    )
    private val episode3 = EpisodeModel(3, "episode3",
        Date(dateFormat.parse("March 3, 2021")?.time ?: Calendar.getInstance().timeInMillis)
    )
    private val episode4 = EpisodeModel(4, "episode4",
        Date(dateFormat.parse("April 4, 2021")?.time ?: Calendar.getInstance().timeInMillis)
    )
    private val episode5 = EpisodeModel(5, "episode5",
        Date(dateFormat.parse("May 5, 2021")?.time ?: Calendar.getInstance().timeInMillis)
    )

    private val charactersEpisodes = mapOf(
        character1 to listOf(episode1, episode2, episode3, episode4, episode5),
        character2 to listOf(episode2, episode5),
        character3 to listOf(episode1, episode3, episode5),
        character4 to listOf(episode1, episode5),
        character5 to listOf(episode2, episode3, episode4)
    )

    private val charactersEpisodesNoMatches = mapOf(
        character1 to listOf(episode1, episode2, episode3, episode4, episode5),
        character2 to listOf(episode2, episode4),
        character3 to listOf(episode1, episode3, episode5),
        character4 to listOf(episode1, episode5),
        character5 to listOf(episode2, episode3, episode4)
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        searchBeerPartnerUseCase = SearchBeerPartnerUseCase(repository)
    }

    @Test
    fun `character location is unknown, so no partner is avaiable`() = coroutineTestRule.runTest {

        coEvery { repository.getCharactersEpisodes() } answers { charactersEpisodes }

        searchBeerPartnerUseCase.prepare(character5).test {
            val item = awaitItem()
            item.onFailure {
                assertEquals(UseCaseError.WhereAreYouError, it)
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `character does not have any match, so no partner is avaiable`() = coroutineTestRule.runTest {
        coEvery { repository.getCharactersEpisodes() } answers { charactersEpisodesNoMatches }
        coEvery { repository.getCharacterEpisodes(character2) } returns charactersEpisodesNoMatches[character2]!!

        searchBeerPartnerUseCase.prepare(character2).test {
            val item = awaitItem()
            item.onFailure {
                assertEquals(UseCaseError.NoMatchesError, it)
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `beer partner found`() = coroutineTestRule.runTest {
        coEvery { repository.getCharactersEpisodes() } answers { charactersEpisodes }
        coEvery { repository.getCharacterEpisodes(character2) } returns charactersEpisodes[character2]!!
        searchBeerPartnerUseCase.prepare(character2).test {
            val item = awaitItem()
            item.onSuccess {
                assertEquals(character3, it)
            }
            cancelAndIgnoreRemainingEvents()
        }
    }
}