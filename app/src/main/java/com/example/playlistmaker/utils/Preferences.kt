package com.example.playlistmaker.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmaker.models.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Preferences(val context: Context) {
    var isNightMode: Boolean
        get() = themePreferences.getBoolean(NIGHT_PREFERENCES_KEY, false)
        set(value) {
            themePreferences.edit()
                .putBoolean(NIGHT_PREFERENCES_KEY, value)
                .apply()
        }

    var searchHistory: List<Track>
        get() {
            val json = historyPreferences.getString(TRACKS_PREFERENCES_KEY,"")
            val listType = object : TypeToken<List<Track>>(){}.type
            return  Gson().fromJson(json, listType) ?: listOf<Track>()
        }

        set(value) {
            val json = Gson().toJson(value)
            historyPreferences.edit()
                .putString(TRACKS_PREFERENCES_KEY, json)
                .apply()
        }

    private var themePreferences: SharedPreferences
        get() = context.getSharedPreferences(THEME_PREFERENCES_KEY, Context.MODE_PRIVATE)
        set(value) {}

    private var historyPreferences: SharedPreferences
        get() = context.getSharedPreferences(HISTORY_PREFERENCES_KEY, Context.MODE_PRIVATE)
        set(value) {}

    companion object {
        const val THEME_PREFERENCES_KEY = "THEME_PREFERENCES_KEY"
        const val NIGHT_PREFERENCES_KEY = "NIGHT_PREFERENCES_KEY"
        const val HISTORY_PREFERENCES_KEY = "HISTORY_PREFERENCES_KEY"
        const val TRACKS_PREFERENCES_KEY: String = "TRACKS_PREFERENCES_KEY"
    }
}