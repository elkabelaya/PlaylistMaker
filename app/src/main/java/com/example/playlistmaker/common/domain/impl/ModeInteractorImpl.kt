package com.example.playlistmaker.common.domain.impl

import com.example.playlistmaker.common.domain.api.ModeInteractor
import com.example.playlistmaker.common.domain.repository.PreferencesRepository
import com.example.playlistmaker.common.domain.repository.ThemeRepository

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