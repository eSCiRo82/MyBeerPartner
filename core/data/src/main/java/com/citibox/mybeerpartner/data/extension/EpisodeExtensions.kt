package com.citibox.mybeerpartner.data.extension

import com.citibox.mybeerpartner.cloud.response.EpisodeObject
import com.citibox.mybeerpartner.database.entity.EpisodeEntity
import com.citibox.mybeerpartner.database.extension.formatDate
import com.citibox.mybeerpartner.model.EpisodeModel
import java.text.SimpleDateFormat

fun EpisodeEntity.toModel() = EpisodeModel(id = episodeId, name = name, airDate = airDate)

fun List<EpisodeEntity>.toModel() = map { location -> location.toModel() }

fun EpisodeObject.toEntity(dateFormat: SimpleDateFormat) =
    EpisodeEntity(
        episodeId = id,
        name = name,
        airDate = airDate.formatDate(dateFormat),
        episode = episode
    )