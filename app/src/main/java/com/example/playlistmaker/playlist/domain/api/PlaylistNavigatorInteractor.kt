package com.example.playlistmaker.playlist.domain.api

import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Track

interface PlaylistNavigatorInteractor {
    fun navigateTo(track: Track)
    fun navigateToEdit(playlist: Playlist)

    fun navigateToShare(description: String)
}