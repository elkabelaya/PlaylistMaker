package com.example.playlistmaker.media.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.common.presentation.StateFullViewModel
import com.example.playlistmaker.media.domain.model.MediaPlaylistsState
import com.example.playlistmaker.media.domain.model.Playlist

abstract class PlaylistsViewModel:StateFullViewModel<MediaPlaylistsState>, ViewModel() {
    abstract fun select(playlist: Playlist)
    abstract fun create()
}