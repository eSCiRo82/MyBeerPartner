package com.citibox.mybeerpartner.viewmodel.di

import androidx.lifecycle.ViewModel
import com.citibox.mybeerpartner.viewmodel.CharactersListViewModel
import com.citibox.mybeerpartner.viewmodel.factory.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CoreViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CharactersListViewModel::class)
    fun bindCharacterListViewModel(viewModel: CharactersListViewModel): ViewModel
}