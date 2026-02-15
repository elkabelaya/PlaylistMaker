package com.example.playlistmaker.media.domain.repository

import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Playlists
import kotlinx.coroutines.flow.Flow

interface MediaPlaylistsRepository {
    fun getPlaylists(): Flow<Playlists>
    fun add(playlist: Playlist)
    fun remove(playlist: Playlist)
}