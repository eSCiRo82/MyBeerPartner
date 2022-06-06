package com.citibox.mybeerpartner.database.dao

import androidx.room.*
import com.citibox.mybeerpartner.database.entity.CharacterAndEpisodesEntity
import com.citibox.mybeerpartner.database.entity.EpisodeEntity

@Dao
interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(episode: EpisodeEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(episodes: List<EpisodeEntity>)

    @Update
    fun update(episode: EpisodeEntity): Int

    @Query("SELECT * FROM episodes")
    fun get(): List<EpisodeEntity>

    @Query("SELECT * FROM episodes WHERE episode_id=:id")
    fun get(id: Long): EpisodeEntity?

    @Query("SELECT * FROM episodes WHERE name=:name")
    fun get(name: String): EpisodeEntity?

    @Query("SELECT COUNT(*) FROM episodes")
    fun count(): Int

    @Query("DELETE FROM episodes")
    fun clear()
}