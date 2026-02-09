package com.example.playlistmaker.common.domain.repository

import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.model.Tracks
import kotlinx.coroutines.flow.Flow

interface FavoriteTracksRepository {

    suspend fun add(track: Track)
    suspend fun delete(trackId: Long)
    suspend fun get(): Flow<Tracks>
    suspend fun find(trackid: Long): Track?
}