package com.citibox.mybeerpartner.data.datasource

import com.citibox.mybeerpartner.cloud.request.LocationsRequest
import com.citibox.mybeerpartner.cloud.response.ApiResponse
import com.citibox.mybeerpartner.cloud.response.LocationObject
import com.citibox.mybeerpartner.common.extension.onSuccess
import javax.inject.Inject

class LocationsRemoteDataSource @Inject constructor(
    private val locationsRequest: LocationsRequest
): PagedDataSource<ApiResponse<LocationObject>?> {

    override fun get(): ApiResponse<LocationObject>? {
        locationsRequest.request().onSuccess { response -> return response }
        return null
    }

    override fun get(page: Int): ApiResponse<LocationObject>? {
        locationsRequest.request(page.toString()).onSuccess { response -> return response }
        return null
    }
}