package com.citibox.mybeerpartner.di

import com.citibox.mybeerpartner.cloud.di.CloudModule
import com.citibox.mybeerpartner.data.di.CoreDataModule
import com.citibox.mybeerpartner.database.di.AppDatabaseModule
import com.citibox.mybeerpartner.view.di.CoreViewModule
import com.citibox.mybeerpartner.view.di.CoreViewSubcomponent
import com.citibox.mybeerpartner.viewmodel.di.CoreViewModelModule
import dagger.Component
import javax.inject.Singleton

/**
 * Main Dagger component of the application that will distribute the dependencies through the app
 */
@Singleton
@Component(modules = [
    ApplicationModule::class,
    AppDatabaseModule::class,
    CloudModule::class,
    CoreDataModule::class,
    CoreViewModule::class,
    CoreViewModelModule::class
])
interface ApplicationComponent {

    fun coreViewSubcomponent(): CoreViewSubcomponent.Factory
}