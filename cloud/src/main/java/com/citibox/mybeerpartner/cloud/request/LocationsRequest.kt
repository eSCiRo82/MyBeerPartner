package com.citibox.mybeerpartner.cloud.request

import com.citibox.mybeerpartner.cloud.RickAndMortyApiService
import com.citibox.mybeerpartner.cloud.extension.page
import com.citibox.mybeerpartner.cloud.response.ApiResponse
import com.citibox.mybeerpartner.cloud.response.LocationObject
import retrofit2.Call
import java.lang.NumberFormatException
import javax.inject.Inject

/**
 * Request to the /location entry point
 */
class LocationsRequest @Inject constructor(
    private val service: RickAndMortyApiService
) : ApiRequest<LocationObject>() {

    override fun launchRequest(vararg params: String): Call<ApiResponse<LocationObject>> =
        service.locations(params.page())
}