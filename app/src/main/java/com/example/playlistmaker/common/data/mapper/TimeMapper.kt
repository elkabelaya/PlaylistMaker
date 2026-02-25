package com.example.playlistmaker.common.data.mapper

import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.abs

class TimeMapper {
    private val dateFormatter = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())

    fun map(time: Long?): String {
        return try {
            dateFormatter.format(time)
        } catch (e: Exception) {
            ""
        }
    }

    fun map(time: String): Long {
        val parts = time.split(TIME_DIVIDER)
        try {
            val minutes = parts[0].toLong()
            val seconds = parts[1].toLong()

            return abs(minutes * 60 + seconds)*1000
        } catch (e: NumberFormatException) {
            return 0
        }
    }

    companion object {
        const val TIME_FORMAT = "mm:ss"
        const val TIME_DIVIDER = ":"
    }
}