package com.example.playlistmaker.media.domain.repository

import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.model.Tracks
import com.example.playlistmaker.media.domain.model.MediaFavoritesState

interface MediaFavoritesRepository {
    fun getTracks(): Tracks
    fun add(track: Track)
    fun remove(track: Track)
}