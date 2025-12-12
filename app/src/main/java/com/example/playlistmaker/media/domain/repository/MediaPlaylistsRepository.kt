package com.example.playlistmaker.media.domain.repository

import com.example.playlistmaker.media.domain.model.Playlist
import com.example.playlistmaker.media.domain.model.Playlists

interface MediaPlaylistsRepository {
    fun getPlaylists(): Playlists
    fun add(playlist: Playlist)
    fun remove(playlist: Playlist)
}