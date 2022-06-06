package com.citibox.mybeerpartner.cloud.request

import com.citibox.mybeerpartner.cloud.RickAndMortyApiService
import com.citibox.mybeerpartner.cloud.extension.page
import com.citibox.mybeerpartner.cloud.response.ApiResponse
import com.citibox.mybeerpartner.cloud.response.EpisodeObject
import retrofit2.Call
import java.lang.NumberFormatException
import javax.inject.Inject

/**
 * Request to the /episode entry point
 */
class EpisodesRequest @Inject constructor(
    private val service: RickAndMortyApiService
) : ApiRequest<EpisodeObject>() {

    override fun launchRequest(vararg params: String): Call<ApiResponse<EpisodeObject>> =
        service.episodes(params.page())
}