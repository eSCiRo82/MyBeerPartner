package com.citibox.mybeerpartner.cloud.di

import com.citibox.mybeerpartner.cloud.RickAndMortyApiService
import com.citibox.mybeerpartner.cloud.request.ApiRequest
import com.citibox.mybeerpartner.cloud.request.CharactersRequest
import com.citibox.mybeerpartner.cloud.request.EpisodesRequest
import com.citibox.mybeerpartner.cloud.request.LocationsRequest
import com.citibox.mybeerpartner.cloud.response.ApiResponse
import com.citibox.mybeerpartner.cloud.response.CharacterObject
import com.citibox.mybeerpartner.cloud.response.EpisodeObject
import com.citibox.mybeerpartner.cloud.response.LocationObject
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class CloudModule {

    @Provides
    @Singleton
    fun provideRickAndMortyApiService(): RickAndMortyApiService =
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RickAndMortyApiService::class.java)

    @Provides
    @Singleton
    fun provideCharactersRequest(request: CharactersRequest): ApiRequest<CharacterObject> = request

    @Provides
    @Singleton
    fun provideLocationsRequest(request: LocationsRequest): ApiRequest<LocationObject> = request

    @Provides
    @Singleton
    fun provideEpisodesRequest(request: EpisodesRequest): ApiRequest<EpisodeObject> = request
}