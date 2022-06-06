package com.citibox.mybeerpartner.model

data class CharacterModel(
    val id: Long,
    val name: CharSequence,
    val species: CharSequence,
    val type: CharSequence,
    val location: Long,
    val image: CharSequence?
)
