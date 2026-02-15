package com.example.playlistmaker.media.data.repository

import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Playlists
import com.example.playlistmaker.media.domain.repository.MediaPlaylistsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MediaPlaylistsRepositoryImpl(): MediaPlaylistsRepository {
    override fun getPlaylists(): Flow<Playlists> = flow {
        emit(emptyList())
    }

    override fun add(playlist: Playlist) {
        TODO("Not yet implemented")
    }

    override fun remove(playlist: Playlist) {
        TODO("Not yet implemented")
    }
}