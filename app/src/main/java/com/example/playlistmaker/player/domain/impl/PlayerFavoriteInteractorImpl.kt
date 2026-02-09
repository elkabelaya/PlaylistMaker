package com.example.playlistmaker.player.domain.impl

import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.repository.FavoriteTracksRepository
import com.example.playlistmaker.player.domain.api.PlayerFavoriteInteractor

class PlayerFavoriteInteractorImpl(val repository: FavoriteTracksRepository):
    PlayerFavoriteInteractor {
    override suspend fun isFavorite(track: Track): Boolean {
        return repository.find(track.trackId) != null
    }

    override suspend fun toggle(track: Track): Boolean {
        if (isFavorite(track)) {
            repository.delete(track.trackId)
            return false
        } else {
            repository.add(track)
            return true
        }
    }
}