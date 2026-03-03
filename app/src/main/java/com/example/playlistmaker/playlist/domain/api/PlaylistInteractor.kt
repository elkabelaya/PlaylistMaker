package com.example.playlistmaker.playlist.domain.api

import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.model.Tracks
import kotlinx.coroutines.flow.Flow

interface PlaylistInteractor {
    suspend fun remove(track: Track)
    suspend fun remove()
    suspend fun getTracks(playlist: Playlist): Flow<Tracks>
    suspend fun getPlaylist(playlist: Playlist): Flow<Playlist>
    fun getDescription(playlist: Playlist, tracks: Tracks): Pair<String?, String?>
}