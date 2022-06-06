package com.citibox.mybeerpartner.cloud.type

sealed class ApiResponseError {
    object ApiResponseEmpty : ApiResponseError()
    object ApiResponseCallNotCreated : ApiResponseError()
}
