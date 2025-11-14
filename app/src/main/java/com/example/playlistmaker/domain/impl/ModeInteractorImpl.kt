package com.example.playlistmaker.domain.impl

import com.example.playlistmaker.domain.api.ModeInteractor
import com.example.playlistmaker.domain.repository.PreferencesRepository
import com.example.playlistmaker.domain.repository.ThemeRepository

class ModeInteractorImpl(val prefsrepository: PreferencesRepository,
                         val themeRepository: ThemeRepository): ModeInteractor {
    override var darkTheme: Boolean
        get() = prefsrepository.isNightMode
        set(value) {
            prefsrepository.isNightMode = value
        }

    override fun startTheme() {
        switchTheme(darkTheme)
    }

    override fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        themeRepository.switchTheme(darkThemeEnabled)
    }
}