package com.example.playlistmaker.search.domain.use_case

import com.example.playlistmaker.common.domain.model.Resource
import com.example.playlistmaker.common.domain.model.Tracks
import kotlinx.coroutines.flow.Flow

interface GetTracksUseCase {
    suspend fun getTracks(query: String): Flow<Resource<Tracks>>
}