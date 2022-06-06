package com.citibox.mybeerpartner.data.extension

import com.citibox.mybeerpartner.cloud.response.LocationObject
import com.citibox.mybeerpartner.database.entity.LocationEntity
import com.citibox.mybeerpartner.model.LocationModel

fun LocationEntity.toModel() = LocationModel(id = locationId, name = name)

fun List<LocationEntity>.toModel() = map { location -> location.toModel() }

fun LocationObject.toEntity() =
    LocationEntity(
        locationId = id,
        name = name
    )