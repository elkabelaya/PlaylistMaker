package com.example.playlistmaker.media.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.model.Tracks
import com.example.playlistmaker.media.domain.model.Playlist
import com.example.playlistmaker.media.domain.model.Playlists

abstract class PlaylistsViewModel: ViewModel() {
    abstract fun observePlayLists(): LiveData<Playlists>
    abstract fun select(playlist: Playlist)
    abstract fun create()
}