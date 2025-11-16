package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.model.TracksDto
import com.example.playlistmaker.data.repository.TracksMapper
import com.example.playlistmaker.domain.model.Resource
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.domain.model.Tracks
import com.example.playlistmaker.domain.repository.TracksRepository

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