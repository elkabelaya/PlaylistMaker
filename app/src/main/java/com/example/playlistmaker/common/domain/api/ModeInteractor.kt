package com.example.playlistmaker.common.domain.api

interface ModeInteractor {
    val darkTheme: Boolean
    fun startTheme()
    fun switchTheme(darkThemeEnabled: Boolean)
}