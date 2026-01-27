package com.example.playlistmaker.search.data.repository

import com.example.playlistmaker.common.data.model.TracksDto
import com.example.playlistmaker.common.data.repository.TracksMapper
import com.example.playlistmaker.common.domain.model.Resource
import com.example.playlistmaker.common.domain.model.Tracks
import com.example.playlistmaker.search.domain.repository.TracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TracksRepositoryImpl(
    private val tracksNetworkClient: TracksNetworkClient,
    private val mapper: TracksMapper
) : TracksRepository {
    override suspend fun getTracks(query: String): Flow<Resource<Tracks>>  = flow {
        val response = tracksNetworkClient.getTracks(query)
        if (response.data is TracksDto) {
            emit( Resource.Success(mapper.map(response.data)))
        } else {
            emit(Resource.Error(response.code))
        }
    }
}