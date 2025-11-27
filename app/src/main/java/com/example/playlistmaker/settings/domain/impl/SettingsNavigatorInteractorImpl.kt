package com.example.playlistmaker.settings.domain.impl

import com.example.playlistmaker.domain.repository.NavigatorRepository
import com.example.playlistmaker.settings.domain.api.SettingsNavigatorInteractor
import com.example.playlistmaker.settings.domain.repository.SettingsNavigatorRepository

class SettingsNavigatorInteractorImpl(
    val repository: NavigatorRepository,
    val settingsRepository: SettingsNavigatorRepository
): SettingsNavigatorInteractor {
    override fun navigateToShare(){
        repository.shareLink(settingsRepository.getShareLink())
    }
    override fun navigateToAgreement(){

    }
    override fun navigateToMail(){
        repository.openEmail(settingsRepository.getEmail())
    }
}