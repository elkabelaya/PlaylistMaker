package com.example.playlistmaker.media.domain.model

import com.example.playlistmaker.common.domain.model.ErrorState
import com.example.playlistmaker.common.domain.model.Playlists

sealed class MediaPlaylistsState {
    data object Loading: MediaPlaylistsState()
    data class Error(val errorState: ErrorState): MediaPlaylistsState()
    data class Data(val playlists: Playlists): MediaPlaylistsState()
}