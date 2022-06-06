package com.citibox.mybeerpartner.domain.usecase

import app.cash.turbine.test
import com.citibox.mybeerpartner.data.repository.RickAndMortyDataRepository
import com.citibox.mybeerpartner.test.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
internal class DownloadCharactersUseCaseTest {

    @get:Rule val coroutineTestRule = CoroutineTestRule()

    private lateinit var downloadCharactersUseCase: DownloadCharactersUseCase

    @MockK internal lateinit var repository: RickAndMortyDataRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        downloadCharactersUseCase = DownloadCharactersUseCase(repository)
    }

    @Test
    fun `download first page of characters from the online API`() = coroutineTestRule.runTest {
        coEvery { repository.requestCharacters(any()) } returns RickAndMortyDataRepository.RequestResult.Finished

        downloadCharactersUseCase.prepare(Unit).test {
            val item = awaitItem()
            assertTrue(item)
            awaitComplete()
        }

        coVerify(exactly = 1) { repository.requestCharacters(any()) }
        confirmVerified(repository)
    }

    @Test
    fun `download three pages of characters from the online API`() = coroutineTestRule.runTest {
        coEvery { repository.requestCharacters(1) } returns RickAndMortyDataRepository.RequestResult.Available
        coEvery { repository.requestCharacters(2) } returns RickAndMortyDataRepository.RequestResult.Available
        coEvery { repository.requestCharacters(3) } returns RickAndMortyDataRepository.RequestResult.Finished

        downloadCharactersUseCase.prepare(Unit).test {
            val item = awaitItem()
            assertTrue(item)
            awaitComplete()
        }

        coVerify(exactly = 3) { repository.requestCharacters(any()) }
        confirmVerified(repository)
    }

    @Test
    fun `downloading the list of characters from the online API fails`() = coroutineTestRule.runTest {
        coEvery { repository.requestCharacters(any()) } returns RickAndMortyDataRepository.RequestResult.Failure

        downloadCharactersUseCase.prepare(Unit).test {
            val item = awaitItem()
            assertFalse(item)
            awaitComplete()
        }

        coVerify(exactly = 1) { repository.requestCharacters(any()) }
        confirmVerified(repository)
    }
}