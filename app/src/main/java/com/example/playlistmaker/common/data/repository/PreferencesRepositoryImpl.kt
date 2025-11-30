package com.example.playlistmaker.common.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmaker.common.data.model.TrackDto
import com.example.playlistmaker.common.data.model.TracksDto
import com.example.playlistmaker.common.domain.model.Tracks
import com.example.playlistmaker.common.domain.repository.PreferencesRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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
            var tracksDto: TracksDto? = null
            try {
                val json = historyPreferences.getString(TRACKS_PREFERENCES_KEY, "")
                val listType = object : TypeToken<TracksDto>() {}.type
                tracksDto = Gson().fromJson(json, listType)
            } catch (ex: Exception) {}

            return mapper.map(tracksDto ?: TracksDto(listOf<TrackDto>()))
        }

        set(value) {
            val json = Gson().toJson(mapper.map(value))
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