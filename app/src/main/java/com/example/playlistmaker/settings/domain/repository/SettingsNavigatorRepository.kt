package com.example.playlistmaker.settings.domain.repository

import com.example.playlistmaker.common.domain.model.Email

interface SettingsNavigatorRepository {
    fun getShareUrl(): String
    fun getEmail(): Email
    fun getAgreementUrl(): String
}