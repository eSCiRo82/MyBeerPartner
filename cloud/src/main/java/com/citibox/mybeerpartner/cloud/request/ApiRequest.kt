package com.citibox.mybeerpartner.cloud.request

import com.citibox.mybeerpartner.cloud.response.ApiResponse
import com.citibox.mybeerpartner.cloud.type.ApiResponseError
import com.citibox.mybeerpartner.common.either.Either
import retrofit2.Call

abstract class ApiRequest<T> {
    private var call: Call<ApiResponse<T>>? = null

    protected abstract fun launchRequest(vararg params: String) : Call<ApiResponse<T>>

    /**
     * Make the requests to the API entry point
     */

    fun request(vararg params: String) : Either<ApiResponse<T>, ApiResponseError>  {
        if (call == null || (call?.isExecuted == true || call?.isCanceled == true))
            call = launchRequest(*params)

        call?.execute()?.let { response ->
            if (response.isSuccessful)
                response.body()?.let { body ->
                    return Either.Success(body)
                }
                    ?: return Either.Failure(ApiResponseError.ApiResponseEmpty)
        }
        return Either.Failure(ApiResponseError.ApiResponseCallNotCreated)
    }
}