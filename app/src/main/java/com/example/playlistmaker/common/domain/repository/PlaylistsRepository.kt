package com.example.playlistmaker.common.domain.repository

import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Playlists
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.model.Tracks
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {
    suspend fun create(): Long
    suspend fun update(playlist: Playlist)
    suspend fun delete(playlistId: Long)
    suspend fun get(): Flow<Playlists>
    suspend fun add(track: Track, inPlaylist: Playlist): Boolean
    suspend fun remove(track: Track, inPlaylist: Playlist)

    suspend fun getTracks(playlist: Playlist): Flow<Tracks>
    suspend fun getPlaylistUpdates(playlist: Playlist): Flow<Playlist>
}