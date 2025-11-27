package com.example.playlistmaker.settings.domain.impl

import com.example.playlistmaker.common.domain.repository.NavigatorRepository
import com.example.playlistmaker.settings.domain.api.SettingsNavigatorInteractor
import com.example.playlistmaker.settings.domain.repository.SettingsNavigatorRepository

class SettingsNavigatorInteractorImpl(
    val repository: NavigatorRepository,
    val settingsRepository: SettingsNavigatorRepository
): SettingsNavigatorInteractor {
    override fun navigateToShare(){
        repository.shareLink(settingsRepository.getShareUrl())
    }
    override fun navigateToAgreement(){
        repository.openWeb(settingsRepository.getShareUrl())
    }
    override fun navigateToMail(){
        repository.openEmail(settingsRepository.getEmail())
    }
}