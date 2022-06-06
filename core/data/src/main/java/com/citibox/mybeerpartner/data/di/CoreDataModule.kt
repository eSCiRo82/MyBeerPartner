package com.citibox.mybeerpartner.data.di

import com.citibox.mybeerpartner.cloud.response.ApiResponse
import com.citibox.mybeerpartner.cloud.response.CharacterObject
import com.citibox.mybeerpartner.cloud.response.EpisodeObject
import com.citibox.mybeerpartner.cloud.response.LocationObject
import com.citibox.mybeerpartner.data.datasource.*
import com.citibox.mybeerpartner.data.repository.DataRepository
import com.citibox.mybeerpartner.data.repository.RickAndMortyDataRepository
import com.citibox.mybeerpartner.database.entity.CharacterEntity
import com.citibox.mybeerpartner.database.entity.EpisodeEntity
import com.citibox.mybeerpartner.database.entity.LocationEntity
import com.citibox.mybeerpartner.database.entity.ResourceInfoEntity
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface CoreDataModule {

    @Binds
    @Singleton
    fun bindCharactersLocalDataSource(impl: CharactersLocalDataSource): MutableDataSource<CharacterEntity>

    @Binds
    @Singleton
    fun bindCharactersRemoteDataSource(impl: CharactersRemoteDataSource): PagedDataSource<ApiResponse<CharacterObject>?>

    @Binds
    @Singleton
    fun bindLocationsLocalDataSource(impl: LocationsLocalDataSource): MutableDataSource<LocationEntity>

    @Binds
    @Singleton
    fun bindLocationsRemoteDataSource(impl: LocationsRemoteDataSource): PagedDataSource<ApiResponse<LocationObject>?>

    @Binds
    @Singleton
    fun bindEpisodesLocalDataSource(impl: EpisodesLocalDataSource): MutableDataSource<EpisodeEntity>

    @Binds
    @Singleton
    fun bindEpisodesRemoteDataSource(impl: EpisodesRemoteDataSource): PagedDataSource<ApiResponse<EpisodeObject>?>

    @Binds
    @Singleton
    fun bindRickAndMortyDataRepository(impl: RickAndMortyDataRepository): DataRepository

    @Binds
    @Singleton
    fun bindResourceInfoLocalDataSource(impl: ResourcesInfoLocalDataSource): MutableDataSource<ResourceInfoEntity>
}