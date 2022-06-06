package com.citibox.mybeerpartner.cloud.request

import com.citibox.mybeerpartner.cloud.RickAndMortyApiService
import com.citibox.mybeerpartner.cloud.di.CloudModule
import com.citibox.mybeerpartner.common.either.Either
import com.citibox.mybeerpartner.common.extension.onSuccess
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class CharactersRequestTest {

    private lateinit var service: RickAndMortyApiService

    @Before
    fun setUp() {
        service = CloudModule().provideRickAndMortyApiService()
    }

    @Test
    fun `Request characters list to the API`() {
        val requestCharacters = CharactersRequest(service)

        val response = requestCharacters.request()
        assertTrue(response is Either.Success)
        response.onSuccess {
            assertTrue(it.info.count > 0)
            assertTrue(it.info.pages > 0)
            assertEquals(1 + it.info.count/it.info.pages, it.results.size)
        }
    }

    @Test
    fun `Request locations list to the API`() {
        val requestLocations = LocationsRequest(service)

        val response = requestLocations.request()
        assertTrue(response is Either.Success)
        response.onSuccess {
            assertTrue(it.info.count > 0)
            assertTrue(it.info.pages > 0)
            assertEquals(1 + it.info.count/it.info.pages, it.results.size)
        }
    }

    @Test
    fun `Request episodes list to the API`() {
        val episodesRequest = EpisodesRequest(service)

        val response = episodesRequest.request()
        assertTrue(response is Either.Success)
        response.onSuccess {
            assertTrue(it.info.count > 0)
            assertTrue(it.info.pages > 0)
            assertEquals(1 + it.info.count/it.info.pages, it.results.size)
        }
    }
}
