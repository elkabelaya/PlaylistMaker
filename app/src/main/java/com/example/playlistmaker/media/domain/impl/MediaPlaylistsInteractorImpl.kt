package com.example.playlistmaker.media.domain.impl

import com.example.playlistmaker.common.domain.model.ErrorState
import com.example.playlistmaker.media.domain.api.MediaPlaylistsInteractor
import com.example.playlistmaker.media.domain.model.MediaFavoritesState
import com.example.playlistmaker.media.domain.model.MediaPlaylistsState
import com.example.playlistmaker.media.domain.model.Playlist
import com.example.playlistmaker.media.domain.model.Playlists
import com.example.playlistmaker.media.domain.repository.MediaPlaylistsErrorRepository
import com.example.playlistmaker.media.domain.repository.MediaPlaylistsRepository

class MediaPlaylistsInteractorImpl(
    val repository: MediaPlaylistsRepository,
    val errorRepository: MediaPlaylistsErrorRepository
): MediaPlaylistsInteractor {
    private var sendState:((MediaPlaylistsState)-> Unit)? = null
    private var state: MediaPlaylistsState = MediaPlaylistsState.Loading
        set(value) {
            field = value
            sendState?.let {
                it(value)
            }
        }

    override fun getPlayLists() {
        state = MediaPlaylistsState.Error(ErrorState.Empty(errorRepository.getErrorText()))
    }

    override fun add(playlist: Playlist) {
        TODO("Not yet implemented")
    }

    override fun onState(state: (MediaPlaylistsState) -> Unit) {
        sendState = state
    }
}