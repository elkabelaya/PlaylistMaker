package com.example.playlistmaker.domain.repository

interface ThemeRepository {
    fun switchTheme(darkMode: Boolean)
}