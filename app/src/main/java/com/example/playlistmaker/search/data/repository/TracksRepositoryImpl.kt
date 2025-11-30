package com.example.playlistmaker.search.data.repository

import com.example.playlistmaker.common.data.model.TracksDto
import com.example.playlistmaker.common.data.repository.TracksMapper
import com.example.playlistmaker.common.domain.model.Resource
import com.example.playlistmaker.common.domain.model.Tracks
import com.example.playlistmaker.search.domain.repository.TracksRepository

class TracksRepositoryImpl(
    private val tracksNetworkClient: TracksNetworkClient,
    private val mapper: TracksMapper
) : TracksRepository {
    override fun getTracks(query: String): Resource<Tracks> {
        val response = tracksNetworkClient.getTracks(query)
        if (response.data is TracksDto) {
            return Resource.Success(mapper.map(response.data))
        }

        return Resource.Error(response.code)
    }
}