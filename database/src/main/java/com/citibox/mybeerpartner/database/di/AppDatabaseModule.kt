package com.citibox.mybeerpartner.database.di

import android.app.Application
import androidx.room.Room
import com.citibox.mybeerpartner.database.AppDatabase
import com.citibox.mybeerpartner.database.dao.CharacterDao
import com.citibox.mybeerpartner.database.dao.EpisodeDao
import com.citibox.mybeerpartner.database.dao.LocationDao
import com.citibox.mybeerpartner.database.dao.ResourceInfoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(application,
            AppDatabase::class.java,
            AppDatabase.APP_DATABASE_NAME).build()
    
    @Provides
    @Singleton
    fun provideCharacterDao(appDatabase: AppDatabase): CharacterDao = appDatabase.characterDao()
    
    @Provides
    @Singleton
    fun provideLocationDao(appDatabase: AppDatabase): LocationDao = appDatabase.locationDao()
    
    @Provides
    @Singleton
    fun provideEpisodeDao(appDatabase: AppDatabase): EpisodeDao = appDatabase.episodeDao()

    @Provides
    @Singleton
    fun provideResourceInfoDao(appDatabase: AppDatabase):
        ResourceInfoDao = appDatabase.resourceInfoDao()
}