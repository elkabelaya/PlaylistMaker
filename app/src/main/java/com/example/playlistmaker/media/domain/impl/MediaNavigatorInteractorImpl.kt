package com.example.playlistmaker.media.domain.impl

import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.repository.NavigatorRepository
import com.example.playlistmaker.media.domain.api.MediaNavigatorInteractor
import com.example.playlistmaker.media.domain.repository.MediaNavigatorRepository
import com.example.playlistmaker.search.domain.api.SearchNavigatorInteractor
import com.example.playlistmaker.search.domain.repository.SearchNavigatorRepository

class MediaNavigatorInteractorImpl(val repository: NavigatorRepository,
                                   val mediaRepository: MediaNavigatorRepository
): MediaNavigatorInteractor {
    override fun navigateTo(track: Track) {
        repository.navigateTo(mediaRepository.getPlayer(track))
    }
}