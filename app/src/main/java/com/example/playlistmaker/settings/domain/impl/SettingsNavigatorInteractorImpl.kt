package com.example.playlistmaker.settings.domain.impl

import com.example.playlistmaker.common.domain.repository.ExternalNavigatorRepository
import com.example.playlistmaker.settings.domain.api.SettingsNavigatorInteractor
import com.example.playlistmaker.settings.domain.repository.SettingsNavigatorRepository

class SettingsNavigatorInteractorImpl(
    private val repository: ExternalNavigatorRepository,
    private val settingsRepository: SettingsNavigatorRepository
): SettingsNavigatorInteractor {
    override fun navigateToShare(){
        repository.share(settingsRepository.getShareUrl())
    }
    override fun navigateToAgreement(){
        repository.openWeb(settingsRepository.getShareUrl())
    }
    override fun navigateToMail(){
        repository.openEmail(settingsRepository.getEmail())
    }
}