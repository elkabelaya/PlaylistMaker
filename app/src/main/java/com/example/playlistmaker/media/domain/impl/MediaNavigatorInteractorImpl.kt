package com.example.playlistmaker.media.domain.impl

import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.repository.NavigatorRepository
import com.example.playlistmaker.media.domain.api.MediaNavigatorInteractor
import com.example.playlistmaker.media.domain.repository.MediaNavigatorRepository

class MediaNavigatorInteractorImpl(val repository: NavigatorRepository,
                                   val mediaRepository: MediaNavigatorRepository
): MediaNavigatorInteractor {
    override fun navigateTo(track: Track) {
        repository.navigateTo(mediaRepository.getPlayer(track))
    }
    override fun navigateToNewPlaylist() {
        repository.navigateTo(mediaRepository.getNewPlayLst())
    }
}