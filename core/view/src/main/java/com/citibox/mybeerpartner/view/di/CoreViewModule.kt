package com.citibox.mybeerpartner.view.di

import com.citibox.mybeerpartner.view.util.image.ImageApi
import com.citibox.mybeerpartner.view.util.image.ImageDownloader
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(subcomponents = [CoreViewSubcomponent::class])
class CoreViewModule {

    @Provides
    @Singleton
    fun provideRickAndMortyApiImageService(): ImageApi =
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImageApi::class.java)

    @Provides
    @Singleton
    fun provideImageDownloader(imageApi: ImageApi): ImageDownloader = ImageDownloader(imageApi)
}