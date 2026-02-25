package com.example.playlistmaker.media.domain.impl

import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.repository.NavigatorRepository
import com.example.playlistmaker.media.domain.api.MediaNavigatorInteractor
import com.example.playlistmaker.media.domain.repository.MediaNavigatorRepository

class MediaNavigatorInteractorImpl(
    private val repository: NavigatorRepository,
    private val mediaRepository: MediaNavigatorRepository
): MediaNavigatorInteractor {
    override fun navigateTo(track: Track) {
        repository.navigateTo(mediaRepository.getPlayer(track))
    }

    override fun navigateTo(playlist: Playlist) {
        repository.navigateTo(mediaRepository.getPlaylist(playlist))
    }

    override fun navigateToNewPlaylist() {
        repository.navigateTo(mediaRepository.getNewPlayLst())
    }
}