package com.example.playlistmaker.media.data.repository

import com.example.playlistmaker.media.domain.model.Playlist
import com.example.playlistmaker.media.domain.model.Playlists
import com.example.playlistmaker.media.domain.repository.MediaPlaylistsRepository

class MediaPlaylistsRepositoryImpl(): MediaPlaylistsRepository {
    override fun getPlaylists(): Playlists {
        return emptyList()
    }

    override fun add(playlist: Playlist) {
        TODO("Not yet implemented")
    }

    override fun remove(playlist: Playlist) {
        TODO("Not yet implemented")
    }
}