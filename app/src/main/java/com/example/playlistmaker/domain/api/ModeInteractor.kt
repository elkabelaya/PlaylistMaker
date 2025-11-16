package com.example.playlistmaker.domain.api

interface ModeInteractor {
    val darkTheme: Boolean
    fun startTheme()
    fun switchTheme(darkThemeEnabled: Boolean)
}