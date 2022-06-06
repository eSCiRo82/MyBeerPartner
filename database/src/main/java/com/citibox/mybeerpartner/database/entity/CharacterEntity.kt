package com.citibox.mybeerpartner.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.citibox.mybeerpartner.database.type.CharacterGender
import com.citibox.mybeerpartner.database.type.CharacterStatus
import java.sql.Date

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey @ColumnInfo(name = "character_id", collate = ColumnInfo.NOCASE) val characterId: Long = 0,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String,
    val gender: CharacterGender,
    @ColumnInfo(name = "location_id") val location: Long,
    val image: String
)