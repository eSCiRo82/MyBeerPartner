package com.citibox.mybeerpartner.view.extension

import com.citibox.mybeerpartner.model.CharacterModel
import com.citibox.mybeerpartner.view.fragment.list.CharacterItem

fun CharacterModel.toItem() = CharacterItem(
    name = name,
    species = species,
    type = type,
    image = image,
)

fun List<CharacterModel>.toItem() = map { model -> model.toItem() }