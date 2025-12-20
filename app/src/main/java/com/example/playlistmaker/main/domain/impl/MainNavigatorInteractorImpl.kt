package com.example.playlistmaker.main.domain.impl

import com.example.playlistmaker.common.domain.repository.NavigatorRepository
import com.example.playlistmaker.main.domain.api.MainNavigatorInteractor
import com.example.playlistmaker.main.domain.repository.MainNavigatorRepository

class MainNavigatorInteractorImpl(
    val repository: NavigatorRepository,
    val mainRepository: MainNavigatorRepository
): MainNavigatorInteractor {
    override fun navigateToSearch() {
     //   repository.navigateTo(mainRepository.getSearchActivity())
    }

    override fun navigateToMedia() {
     //   repository.navigateTo(mainRepository.getMediaActivity())
    }

    override fun navigateToSettings() {
      //  repository.navigateTo(mainRepository.getSettingsActivity())
    }

}