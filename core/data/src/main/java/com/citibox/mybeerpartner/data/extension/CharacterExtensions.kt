package com.citibox.mybeerpartner.data.extension

import com.citibox.mybeerpartner.cloud.extension.urlId
import com.citibox.mybeerpartner.cloud.response.CharacterObject
import com.citibox.mybeerpartner.database.entity.CharacterEntity
import com.citibox.mybeerpartner.database.type.CharacterGender
import com.citibox.mybeerpartner.database.type.CharacterStatus
import com.citibox.mybeerpartner.model.CharacterModel

fun CharacterEntity.toModel() = CharacterModel(
    id = characterId,
    name = name,
    species = species,
    type = type,
    location = location,
    image = image
)

fun List<CharacterEntity>.toModel() = map { character -> character.toModel() }

fun CharacterObject.toEntity() = CharacterEntity(
    characterId = id,
    name = name,
    status = CharacterStatus.valueOf(status),
    species = species,
    type = type,
    gender = CharacterGender.valueOf(gender),
    location = location.url.urlId(),
    image = image
)