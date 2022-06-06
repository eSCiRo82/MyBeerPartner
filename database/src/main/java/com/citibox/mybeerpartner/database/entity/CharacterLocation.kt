package com.citibox.mybeerpartner.database.entity

import androidx.room.*

@Entity(tableName = "character_locations",
    primaryKeys = ["character_id", "location_id"])
data class CharacterLocationRefs(
    @ColumnInfo(name = "character_id") val characterId: Long,
    @ColumnInfo(name = "location_id") val locationId: Long
)

data class CharacterAndLocationEntity(
    @Embedded val character: CharacterEntity,
    @Relation(
        parentColumn = "character_id",
        entityColumn = "location_id",
        associateBy = Junction(CharacterLocationRefs::class)
    )
    val location: LocationEntity?
)

data class CharactersAtLocationEntity(
    @Embedded val location: LocationEntity,
    @Relation(
        parentColumn = "location_id",
        entityColumn = "character_id",
        associateBy = Junction(CharacterLocationRefs::class)
    )
    val characters: List<CharacterEntity>
)
