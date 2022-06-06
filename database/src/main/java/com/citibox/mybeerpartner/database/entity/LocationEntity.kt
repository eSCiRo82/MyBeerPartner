package com.citibox.mybeerpartner.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey @ColumnInfo(name = "location_id") val locationId: Long = 0,
    val name: String
)
