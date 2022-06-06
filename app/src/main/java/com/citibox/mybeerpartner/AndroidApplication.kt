package com.citibox.mybeerpartner

import android.app.Application
import com.citibox.mybeerpartner.common.di.ComponentProvider
import com.citibox.mybeerpartner.common.di.DaggerComponent
import com.citibox.mybeerpartner.di.ApplicationComponent
import com.citibox.mybeerpartner.di.ApplicationModule
import com.citibox.mybeerpartner.di.DaggerApplicationComponent
import com.citibox.mybeerpartner.view.di.CoreViewSubcomponent
import javax.inject.Inject

class AndroidApplication @Inject constructor(): Application(), ComponentProvider {

    override val components: MutableMap<Class<*>, DaggerComponent> = mutableMapOf()

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        // Creation of the main Dagger component
        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()

        addSubcomponents()
    }

    private fun addSubcomponents() {
        applicationComponent.coreViewSubcomponent().create().apply {
            components[CoreViewSubcomponent::class.java] = applicationComponent.coreViewSubcomponent().create()
        }
    }
}