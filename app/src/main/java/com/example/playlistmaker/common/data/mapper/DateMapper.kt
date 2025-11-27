package com.example.playlistmaker.common.data.mapper

class DateMapper {
   fun mapDate(date: String?): String {
        return date?.substring(0, YEAR_LENGHT) ?: ""
    }

   fun mapYear(year: String): String {
        return if (year.count() == YEAR_LENGHT)  "01.01.$year" else ""
   }

    companion object {
        const val YEAR_LENGHT = 4
    }
}