package com.example.playlistmaker.playlist.domain.impl

import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Playlists
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.model.Tracks
import com.example.playlistmaker.common.domain.repository.NavigatorRepository
import com.example.playlistmaker.common.domain.repository.PlaylistsRepository
import com.example.playlistmaker.player.domain.api.PlayerPlaylistsInteractor
import com.example.playlistmaker.player.domain.repository.PlayerDefaultsRepository
import com.example.playlistmaker.playlist.domain.api.PlaylistInteractor
import com.example.playlistmaker.playlist.domain.repository.PlaylistDescriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistInteractorImpl(
    private val repository: PlaylistsRepository,
    private val descriptionRepository: PlaylistDescriptionRepository,
    private val playlist: Playlist
): PlaylistInteractor{

    override suspend fun remove(track: Track) {
        repository.remove(track, playlist)
    }

    override suspend fun remove() {
        repository.delete(playlist.id)
    }

    override suspend fun getTracks(playlist: Playlist): Flow<Tracks> = flow {
        repository.getTracks(playlist)
            .collect {
                emit(it)
            }
    }
    override suspend fun getPlaylist(playlist: Playlist): Flow<Playlist> = flow {
        repository.getPlaylistUpdates(playlist)
            .collect {
                emit(it)
            }
    }
    override fun getDescription(playlist: Playlist, tracks: Tracks): Pair<String?, String?> {
        if (tracks.count() > 0) {
            return Pair(descriptionRepository.getDescription(playlist, tracks), null)
        } else {
            return Pair(null, descriptionRepository.getEmptyDescription())
        }
    }
}