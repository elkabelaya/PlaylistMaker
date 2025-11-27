package com.example.playlistmaker.settings.domain.repository

import com.example.playlistmaker.domain.model.Email

interface SettingsNavigatorRepository {
    fun getShareLink(): String
    fun getEmail(): Email
}