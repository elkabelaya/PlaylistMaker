package com.example.playlistmaker.player.domain.api

import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Playlists
import com.example.playlistmaker.common.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface PlayerPlaylistsInteractor {
    suspend fun get(): Flow<Playlists>
    suspend fun add(track: Track, playlist: Playlist): String
}