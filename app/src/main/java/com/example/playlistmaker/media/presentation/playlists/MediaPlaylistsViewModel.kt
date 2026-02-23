package com.example.playlistmaker.media.presentation.playlists

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.presentation.StateFullViewModel
import com.example.playlistmaker.media.domain.model.MediaPlaylistsState

abstract class MediaPlaylistsViewModel:StateFullViewModel<MediaPlaylistsState>, ViewModel() {
    abstract fun select(playlist: Playlist)
    abstract fun create()
}