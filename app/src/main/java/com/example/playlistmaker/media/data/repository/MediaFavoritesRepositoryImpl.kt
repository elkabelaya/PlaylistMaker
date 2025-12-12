package com.example.playlistmaker.media.data.repository

import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.model.Tracks
import com.example.playlistmaker.media.domain.repository.MediaFavoritesRepository

class MediaFavoritesRepositoryImpl: MediaFavoritesRepository {
    override fun getTracks(): Tracks {
        return emptyList()
    }

    override fun add(track: Track) {
        TODO("Not yet implemented")
    }

    override fun remove(track: Track) {
        TODO("Not yet implemented")
    }
}