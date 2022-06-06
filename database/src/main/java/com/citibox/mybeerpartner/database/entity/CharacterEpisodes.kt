package com.citibox.mybeerpartner.database.entity

import androidx.room.*

@Entity(tableName = "character_episodes",
    primaryKeys = ["character_id", "episode_id"])
data class CharacterEpisodesRefs(
    @ColumnInfo(name = "character_id") val characterId: Long,
    @ColumnInfo(name = "episode_id") val episodeId: Long
)

data class CharacterAndEpisodesEntity(
    @Embedded val character: CharacterEntity,
    @Relation(
        parentColumn = "character_id",
        entityColumn = "episode_id",
        associateBy = Junction(CharacterEpisodesRefs::class)
    )
    val episodes: List<EpisodeEntity>
)
