package com.citibox.mybeerpartner.database

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DateExtensionsTest {
    @Test
    fun parse_date() {
        val dateStr = "December 12, 2003"
        val dateFormat = SimpleDateFormat("MMMM d, yyyy", Locale.US)
        val date = dateFormat.parse(dateStr)

        assertNotNull(date)
        assertEquals(dateStr, dateFormat.format(date))
    }
}