package com.citibox.mybeerpartner.common.di

interface DaggerComponent

interface ComponentProvider {

    val components: MutableMap<Class<*>, DaggerComponent>
        get() = mutableMapOf()
}
