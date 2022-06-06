package com.citibox.mybeerpartner.cloud

import com.citibox.mybeerpartner.cloud.response.ApiResponse
import com.citibox.mybeerpartner.cloud.response.CharacterObject
import com.citibox.mybeerpartner.cloud.response.EpisodeObject
import com.citibox.mybeerpartner.cloud.response.LocationObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Requests to the API entry points
 */
interface RickAndMortyApiService {

    @GET("/api/character")
    fun characters(@Query("page") page: Int) : Call<ApiResponse<CharacterObject>>

    @GET("/api/episode")
    fun episodes(@Query("page") page: Int) : Call<ApiResponse<EpisodeObject>>

    @GET("/api/location")
    fun locations(@Query("page") page: Int): Call<ApiResponse<LocationObject>>
}