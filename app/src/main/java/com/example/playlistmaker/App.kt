package com.example.playlistmaker

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.utils.Preferences

class App : Application() {
    var darkTheme: Boolean
        get() = preferences.isNightMode
        set(value) {
            preferences.isNightMode = value
        }
    lateinit var preferences: Preferences

    override fun onCreate() {
        super.onCreate()
        preferences = Preferences(this)
        switchTheme(darkTheme)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}