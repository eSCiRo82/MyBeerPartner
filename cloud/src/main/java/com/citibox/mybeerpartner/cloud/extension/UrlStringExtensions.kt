package com.citibox.mybeerpartner.cloud.extension

import java.lang.NumberFormatException

fun String.urlId(): Long = try { split("/").last().toLong() } catch (e: Exception) { 0 }

fun String.urlPage(): Int = try { substring(indexOf("?page=")).split("?").first().toInt() } catch (e: Exception) { 0 }

fun Array<out String>.page() = if (isNotEmpty()) {
    try {
        get(0).toInt()
    } catch (e: NumberFormatException) { 1 }
} else 1
