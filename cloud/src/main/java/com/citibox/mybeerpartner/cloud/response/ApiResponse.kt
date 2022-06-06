package com.citibox.mybeerpartner.cloud.response

import com.google.gson.annotations.SerializedName

/**
 * The API response is divided in two main containers:
 * - Info
 * - Results
 *
 * Resuls can be characters, locations or episodes
 */
data class ApiResponse<T>(
    @SerializedName("info")
    val info: InfoObject,
    @SerializedName("results")
    val results: List<T>
)
