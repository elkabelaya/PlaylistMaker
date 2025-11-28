package com.example.playlistmaker.common.data.mapper

import java.text.SimpleDateFormat
import java.util.Locale

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
        return try {
            dateFormatter.parse(time).time
        } catch (e: Exception) {
            0
        }
    }

    companion object {
        const val TIME_FORMAT = "mm:ss"
    }
}