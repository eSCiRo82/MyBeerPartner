package com.citibox.mybeerpartner.database.extension

import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

fun String.formatDate(dateFormat: SimpleDateFormat): Date {
    val date = dateFormat.parse(this)?.time
    return Date(date ?: Calendar.getInstance().timeInMillis)
}