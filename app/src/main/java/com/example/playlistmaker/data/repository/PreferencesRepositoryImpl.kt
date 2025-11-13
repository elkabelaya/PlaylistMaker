package com.example.playlistmaker.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmaker.domain.model.Tracks
import com.example.playlistmaker.domain.repository.PreferencesRepository

class PreferencesRepositoryImpl(val context: Context, val mapper: TracksMapper): PreferencesRepository {
    override var isNightMode: Boolean
        get() = themePreferences.getBoolean(NIGHT_PREFERENCES_KEY, false)
        set(value) {
            themePreferences.edit()
                .putBoolean(NIGHT_PREFERENCES_KEY, value)
                .apply()
        }

    override var searchHistory: Tracks
        get() {
            val dto = historyPreferences.getString(TRACKS_PREFERENCES_KEY,"")
            return mapper.map(dto)
        }

        set(value) {
            historyPreferences.edit()
                .putString(TRACKS_PREFERENCES_KEY, mapper.map(value))
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