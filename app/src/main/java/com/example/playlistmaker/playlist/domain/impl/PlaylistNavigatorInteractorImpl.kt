package com.example.playlistmaker.playlist.domain.impl

import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.repository.ExternalNavigatorRepository
import com.example.playlistmaker.common.domain.repository.NavigatorRepository
import com.example.playlistmaker.playlist.domain.api.PlaylistNavigatorInteractor
import com.example.playlistmaker.playlist.domain.repository.PlaylistDescriptionRepository
import com.example.playlistmaker.playlist.domain.repository.PlaylistNavigatorRepository

class PlaylistNavigatorInteractorImpl(
    private val repository: NavigatorRepository,
    private val playlistRepository: PlaylistNavigatorRepository,
    private val externalNavigatorRepository: ExternalNavigatorRepository
): PlaylistNavigatorInteractor {

    override fun navigateTo(track: Track) {
        repository.navigateTo(playlistRepository.getPlayer(track))
    }

    override fun navigateToEdit(playlist: Playlist) {
        repository.navigateTo(playlistRepository.getPlayListEdit(playlist))
    }

    override fun navigateToShare(description: String) {
        externalNavigatorRepository.share(description)
    }
}