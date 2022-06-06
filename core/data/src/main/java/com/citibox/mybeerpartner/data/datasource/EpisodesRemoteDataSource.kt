package com.citibox.mybeerpartner.data.datasource

import com.citibox.mybeerpartner.cloud.request.EpisodesRequest
import com.citibox.mybeerpartner.cloud.response.ApiResponse
import com.citibox.mybeerpartner.cloud.response.EpisodeObject
import com.citibox.mybeerpartner.common.extension.onSuccess
import javax.inject.Inject

class EpisodesRemoteDataSource @Inject constructor(
    private val episodesRequest: EpisodesRequest
): PagedDataSource<ApiResponse<EpisodeObject>?> {

    override fun get(): ApiResponse<EpisodeObject>? {
        episodesRequest.request().onSuccess { response -> return response }
        return null
    }

    override fun get(page: Int): ApiResponse<EpisodeObject>? {
        episodesRequest.request(page.toString()).onSuccess { response -> return response }
        return null
    }
}