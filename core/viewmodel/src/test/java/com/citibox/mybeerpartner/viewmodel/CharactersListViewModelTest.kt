package com.citibox.mybeerpartner.viewmodel

import app.cash.turbine.test
import com.citibox.mybeerpartner.common.constant.DateFormatPattern
import com.citibox.mybeerpartner.common.either.Either
import com.citibox.mybeerpartner.domain.type.UseCaseError
import com.citibox.mybeerpartner.domain.usecase.*
import com.citibox.mybeerpartner.model.CharacterModel
import com.citibox.mybeerpartner.model.EpisodeModel
import com.citibox.mybeerpartner.test.CoroutineTestRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
internal class CharactersListViewModelTest {

    @get:Rule val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: CharactersListViewModel

    @MockK internal lateinit var arrangeMeetingUseCase: ArrangeMeetingUseCase
    @MockK internal lateinit var downloadCharactersUseCase: DownloadCharactersUseCase
    @MockK internal lateinit var downloadEpisodesUseCase: DownloadEpisodesUseCase
    @MockK internal lateinit var downloadLocationsUseCase: DownloadLocationsUseCase
    @MockK internal lateinit var filterCharactersByNameUseCase: FilterCharactersByNameUseCase
    @MockK internal lateinit var getCharactersUseCase: GetCharactersUseCase
    @MockK internal lateinit var searchBeerPartnerUseCase: SearchBeerPartnerUseCase

    private val character1 = CharacterModel(1, "Character1", "Human", "Human", 1, "https://image/image1.url.png")
    private val character2 = CharacterModel(2, "Character2", "Ganimedian", "Alien", 3, "https://image/image2.url.png")
    private val character3 = CharacterModel(3, "Character3", "Robot", "Robot", 3, "https://image/image3.url.png")
    private val character4 = CharacterModel(4, "Character4", "Android", "Robot", 3, "https://image/image4.url.png")
    private val character5 = CharacterModel(5, "Character5", "Unknown", "Unknown", 0, "https://image/image5.url.png")

    private val characters = listOf(character1, character2, character3, character4, character5)

    private val dateFormat = SimpleDateFormat(DateFormatPattern, Locale.US)

    private val episode1 = EpisodeModel(1, "episode1",
        Date(dateFormat.parse("January 1, 2021")?.time ?: Calendar.getInstance().timeInMillis)
    )
    private val episode2 = EpisodeModel(2, "episode2",
        Date(dateFormat.parse("February 2, 2021")?.time ?: Calendar.getInstance().timeInMillis)
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = CharactersListViewModel(arrangeMeetingUseCase,
            downloadCharactersUseCase,
            downloadEpisodesUseCase,
            downloadLocationsUseCase,
            filterCharactersByNameUseCase,
            getCharactersUseCase,
            searchBeerPartnerUseCase)
    }

    @Test
    fun `download characters is success`() = coroutineTestRule.runTest {

        coEvery { downloadCharactersUseCase.prepare(Unit) } answers { flow { emit(true) } }

        viewModel.characterEvents.test {
            viewModel.downloadCharacters()
            val item = awaitItem()
            assertTrue(item is CharactersListViewModel.Event.DownloadFinished)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { downloadCharactersUseCase.prepare(Unit) }
        confirmVerified(downloadCharactersUseCase)
    }

    @Test
    fun `download characters fails`() = coroutineTestRule.runTest {

        coEvery { downloadCharactersUseCase.prepare(Unit) } answers { flow { emit(false) } }

        viewModel.characterEvents.test {
            viewModel.downloadCharacters()
            val item = awaitItem()
            assertTrue(item is CharactersListViewModel.Event.DownloadError)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { downloadCharactersUseCase.prepare(Unit) }
        confirmVerified(downloadCharactersUseCase)
    }

    @Test
    fun `get characters is success`() = coroutineTestRule.runTest {

        coEvery { getCharactersUseCase.prepare(Unit) } answers { flow { emit(Either.Success(characters)) } }

        viewModel.characterStates.test {
            viewModel.getCharacters()
            assertTrue(awaitItem() is CharactersListViewModel.State.Idle)
            val item = awaitItem()
            assertTrue(item is CharactersListViewModel.State.Characters)
            assertEquals(characters, (item as CharactersListViewModel.State.Characters).characters)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { getCharactersUseCase.prepare(Unit) }
        confirmVerified(getCharactersUseCase)
    }

    @Test
    fun `get characters fails`() = coroutineTestRule.runTest {

        coEvery { getCharactersUseCase.prepare(Unit) } answers { flow { emit(Either.Failure(UseCaseError.NoDataError)) } }

        viewModel.characterEvents.test {
            viewModel.getCharacters()
            val item = awaitItem()
            assertTrue(item is CharactersListViewModel.Event.NoCharacters)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { getCharactersUseCase.prepare(Unit) }
        confirmVerified(getCharactersUseCase)
    }

    @Test
    fun `filter by name`() = coroutineTestRule.runTest {

        coEvery { filterCharactersByNameUseCase.prepare("character") } answers { flow { emit(characters) } }

        viewModel.characterStates.test {
            viewModel.filterCharactersByName("character")
            assertTrue(awaitItem() is CharactersListViewModel.State.Idle)
            val item = awaitItem()
            assertTrue(item is CharactersListViewModel.State.Characters)
            assertEquals(characters, (item as CharactersListViewModel.State.Characters).characters)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { filterCharactersByNameUseCase.prepare("character") }
        confirmVerified(filterCharactersByNameUseCase)
    }

    @Test
    fun `beer partner found`() = coroutineTestRule.runTest {

        coEvery { searchBeerPartnerUseCase.prepare(character1) } answers { flow { emit(Either.Success(character2)) } }

        viewModel.characterEvents.test {
            viewModel.searchBeerPartner(character1)
            val item = awaitItem()
            assertTrue(item is CharactersListViewModel.Event.BeerPartner)
            assertEquals(character2, (item as CharactersListViewModel.Event.BeerPartner).beerPartner)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { searchBeerPartnerUseCase.prepare(character1) }
        confirmVerified(searchBeerPartnerUseCase)
    }

    @Test
    fun `the character is not in any location`() = coroutineTestRule.runTest {

        coEvery { searchBeerPartnerUseCase.prepare(character5) } answers { flow { emit(Either.Failure(UseCaseError.WhereAreYouError)) } }

        viewModel.characterEvents.test {
            viewModel.searchBeerPartner(character5)
            val item = awaitItem()
            assertTrue(item is CharactersListViewModel.Event.WhereAreYou)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { searchBeerPartnerUseCase.prepare(character5) }
        confirmVerified(searchBeerPartnerUseCase)
    }

    @Test
    fun `unfortunately, the character does not have any beer partner`() = coroutineTestRule.runTest {

        coEvery { searchBeerPartnerUseCase.prepare(character2) } answers { flow { emit(Either.Failure(UseCaseError.NoMatchesError)) } }

        viewModel.characterEvents.test {
            viewModel.searchBeerPartner(character2)
            val item = awaitItem()
            assertTrue(item is CharactersListViewModel.Event.NoBeerPartnerFound)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { searchBeerPartnerUseCase.prepare(character2) }
        confirmVerified(searchBeerPartnerUseCase)
    }

    @Test
    fun `let's arrange a meeting!`() = coroutineTestRule.runTest {

        coEvery { arrangeMeetingUseCase.prepare(ArrangeMeetingUseCase.Input(character1, character3)) } answers {
            flow { emit(Either.Success(ArrangeMeetingUseCase.Output(
                mockk {
                    every { name } returns "Earth"
                }, listOf(episode1, episode2))))
            }
        }

        viewModel.characterEvents.test {
            viewModel.makeAppointment(character1, character3)
            val item = awaitItem()
            assertTrue(item is CharactersListViewModel.Event.Meeting)
            (item as CharactersListViewModel.Event.Meeting).apply {
                assertEquals("Earth", location.name)
                assertEquals(2, episodes)
            }
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { arrangeMeetingUseCase.prepare(ArrangeMeetingUseCase.Input(character1, character3)) }
        confirmVerified(arrangeMeetingUseCase)
    }
}