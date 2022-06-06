package com.citibox.mybeerpartner.data.datasource

import com.citibox.mybeerpartner.cloud.request.CharactersRequest
import com.citibox.mybeerpartner.cloud.response.ApiResponse
import com.citibox.mybeerpartner.cloud.response.CharacterObject
import com.citibox.mybeerpartner.common.extension.onSuccess
import javax.inject.Inject

class CharactersRemoteDataSource @Inject constructor(
    private val charactersRequest: CharactersRequest
): PagedDataSource<ApiResponse<CharacterObject>?> {

    override fun get(): ApiResponse<CharacterObject>? {
        charactersRequest.request().onSuccess { response -> return response }
        return null
    }

    override fun get(page: Int): ApiResponse<CharacterObject>? {
        charactersRequest.request(page.toString()).onSuccess { response -> return response }
        return null
    }
}