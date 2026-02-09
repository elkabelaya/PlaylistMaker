package com.example.playlistmaker.player.domain.api

import com.example.playlistmaker.common.domain.model.Track

interface PlayerFavoriteInteractor {
    suspend fun isFavorite(track: Track): Boolean
    suspend fun toggle(track: Track): Boolean
}