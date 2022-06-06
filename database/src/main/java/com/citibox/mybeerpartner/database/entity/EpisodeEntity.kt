package com.citibox.mybeerpartner.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "episodes")
data class EpisodeEntity(
    @PrimaryKey @ColumnInfo(name = "episode_id") val episodeId: Long = 0,
    val name: String,
    @ColumnInfo(name= "air_date") val airDate: Date,
    val episode: String
)
