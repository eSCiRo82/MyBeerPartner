package com.citibox.mybeerpartner.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resources_info")
data class ResourceInfoEntity(
    @PrimaryKey val resource: String,
    val count: Int,
    val pages: Int
)
