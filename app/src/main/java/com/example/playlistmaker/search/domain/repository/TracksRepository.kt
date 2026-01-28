package com.example.playlistmaker.search.domain.repository

import com.example.playlistmaker.common.domain.model.Resource
import com.example.playlistmaker.common.domain.model.Tracks
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun getTracks(query: String): Flow<Resource<Tracks>>
}