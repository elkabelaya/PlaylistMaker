package com.example.playlistmaker.player.domain.impl

import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Playlists
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.repository.PlaylistsRepository
import com.example.playlistmaker.player.domain.api.PlayerPlaylistsInteractor
import com.example.playlistmaker.player.domain.repository.PlayerDefaultsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlayerPlaylistsInteractorImpl(
    val repository: PlaylistsRepository,
    val defaultsRepositoty: PlayerDefaultsRepository): PlayerPlaylistsInteractor {
    override suspend fun get(): Flow<Playlists> = flow {
        repository.get().collect {
            emit(it)
        }
    }

    override suspend fun add(track: Track, playlist: Playlist): String {
        val added = repository.add(track, playlist)
        return defaultsRepositoty.addToastText(added, playlist.name)
    }
}