package com.citibox.mybeerpartner.cloud.response

import com.google.gson.annotations.SerializedName

data class CharacterObject(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("origin")
    val origin: CharacterLocationObject,
    @SerializedName("location")
    val location: CharacterLocationObject,
    @SerializedName("image")
    val image: String,
    @SerializedName("episode")
    val episode: List<String>,
    @SerializedName("url")
    val url: String,
    @SerializedName("created")
    val created: String
)
