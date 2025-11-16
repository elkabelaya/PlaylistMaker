package com.example.playlistmaker.data.repository

import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.domain.repository.ThemeRepository

class ThemeRepositoryImpl: ThemeRepository {
    override fun switchTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}