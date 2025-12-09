package com.example.playlistmaker.media.domain.model

import com.example.playlistmaker.common.domain.model.ErrorState

sealed class MediaPlaylistsState {
    data object Loading: MediaPlaylistsState()
    data class Error(val errorState: ErrorState): MediaPlaylistsState()
    data class Playlists(val playlists: Playlists): MediaPlaylistsState()
}