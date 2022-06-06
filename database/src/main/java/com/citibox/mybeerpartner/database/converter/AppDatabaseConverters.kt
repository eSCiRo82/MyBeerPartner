package com.citibox.mybeerpartner.database.converter

import androidx.room.TypeConverter
import com.citibox.mybeerpartner.common.constant.DateFormatPattern
import com.citibox.mybeerpartner.database.extension.formatDate
import com.citibox.mybeerpartner.database.type.CharacterGender
import com.citibox.mybeerpartner.database.type.CharacterStatus
import java.sql.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AppDatabaseConverters {

    private val dateFormat = SimpleDateFormat(DateFormatPattern, Locale.US)

    // Date string format "2017-11-10T12:56:36.618Z"
    @TypeConverter
    fun stringToDate(str: String): Date = str.formatDate(dateFormat)

    @TypeConverter
    fun dateToString(date: Date): String = dateFormat.format(date)

    @TypeConverter
    fun stringToCharacterStatus(str: String): CharacterStatus = CharacterStatus.valueOf(str)

    @TypeConverter
    fun characterStatusToString(status: CharacterStatus): String = status.name

    @TypeConverter
    fun stringToCharacterGender(str: String): CharacterGender = CharacterGender.valueOf(str)

    @TypeConverter
    fun characterGenderToString(gender: CharacterGender): String = gender.name
}