package com.example.playlistmaker.player.domain.impl

import com.example.playlistmaker.common.domain.repository.NavigatorRepository
import com.example.playlistmaker.player.domain.repository.PlayerNavigatorRepository
import com.example.playlistmaker.player.domain.api.PlayerNavigatorInteractor

class PlayerNavigatorInteractorImpl(
    private val repository: NavigatorRepository,
    private val playerRepository: PlayerNavigatorRepository
): PlayerNavigatorInteractor {

    override fun navigateToNewPlaylist() {
        repository.navigateTo(playerRepository.getNewPlayLst())
    }
}