package com.example.playlistmaker.media.domain.api

import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Track

interface MediaNavigatorInteractor {
    fun navigateTo(track: Track)
    fun navigateTo(playlist: Playlist)
    fun navigateToNewPlaylist()
}