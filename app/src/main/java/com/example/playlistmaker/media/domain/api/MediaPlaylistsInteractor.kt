package com.example.playlistmaker.media.domain.api

import com.example.playlistmaker.common.domain.api.StateFullInteractor
import com.example.playlistmaker.media.domain.model.MediaPlaylistsState
import com.example.playlistmaker.media.domain.model.Playlist

interface MediaPlaylistsInteractor: StateFullInteractor<MediaPlaylistsState> {
    fun getPlayLists()
    fun add(playlist: Playlist)
}
