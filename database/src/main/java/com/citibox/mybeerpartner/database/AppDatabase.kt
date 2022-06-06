package com.citibox.mybeerpartner.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.citibox.mybeerpartner.database.converter.AppDatabaseConverters
import com.citibox.mybeerpartner.database.dao.CharacterDao
import com.citibox.mybeerpartner.database.dao.EpisodeDao
import com.citibox.mybeerpartner.database.dao.LocationDao
import com.citibox.mybeerpartner.database.dao.ResourceInfoDao
import com.citibox.mybeerpartner.database.entity.*

@Database(entities = [
                        CharacterEntity::class,
                        EpisodeEntity::class,
                        LocationEntity::class,
                        ResourceInfoEntity::class,
                        CharacterLocationRefs::class,
                        CharacterEpisodesRefs::class
                     ], version = 1, exportSchema = false)
@TypeConverters(AppDatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    abstract fun episodeDao(): EpisodeDao

    abstract fun locationDao(): LocationDao

    abstract fun resourceInfoDao(): ResourceInfoDao

    companion object {
        const val APP_DATABASE_NAME = "app_database"
    }
}