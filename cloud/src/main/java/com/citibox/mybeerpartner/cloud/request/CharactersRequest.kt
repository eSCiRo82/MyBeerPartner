package com.citibox.mybeerpartner.cloud.request

import com.citibox.mybeerpartner.cloud.RickAndMortyApiService
import com.citibox.mybeerpartner.cloud.extension.page
import com.citibox.mybeerpartner.cloud.response.ApiResponse
import com.citibox.mybeerpartner.cloud.response.CharacterObject
import retrofit2.Call
import java.lang.NumberFormatException
import javax.inject.Inject

/**
 * Request to the /character entry point
 */
class CharactersRequest @Inject constructor(
    private val service: RickAndMortyApiService
) : ApiRequest<CharacterObject>() {

    override fun launchRequest(vararg params: String): Call<ApiResponse<CharacterObject>> =
        service.characters(params.page())
}