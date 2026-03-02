package com.example.playlistmaker.playlist.domain.model

import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Tracks

data class PlaylistState (
    val playlist: Playlist,
    val tracks: Tracks
)