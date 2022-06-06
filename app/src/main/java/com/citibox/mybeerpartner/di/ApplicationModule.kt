package com.citibox.mybeerpartner.di

import android.app.Application
import com.citibox.mybeerpartner.AndroidApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: AndroidApplication) {
    @Provides
    internal fun provideApplication(): Application = application
}