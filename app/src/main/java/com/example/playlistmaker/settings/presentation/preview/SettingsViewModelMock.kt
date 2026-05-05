package com.example.playlistmaker.settings.presentation.preview

import com.example.playlistmaker.settings.presentation.SettingsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModelMock(darkMode: Boolean) : SettingsViewModel() {
    private val _isDarkMode = MutableStateFlow(false)
    override var isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    init {
        _isDarkMode.value = darkMode
    }

    override fun switch(isDarck: Boolean) {
    }

    override fun share() {
    }

    override fun support() {
    }

    override fun agreement() {
    }
}