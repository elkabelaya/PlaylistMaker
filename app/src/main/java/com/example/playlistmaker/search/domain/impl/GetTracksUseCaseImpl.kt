package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.common.domain.consumer.ResourceConsumer
import com.example.playlistmaker.common.domain.model.Resource
import com.example.playlistmaker.common.domain.model.Tracks
import com.example.playlistmaker.search.domain.repository.TracksRepository
import com.example.playlistmaker.search.domain.use_case.GetTracksUseCase
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executors

class GetTracksUseCaseImpl(private val repository: TracksRepository) : GetTracksUseCase {

    override suspend fun getTracks(query: String): Flow<Resource<Tracks>> {
        return repository.getTracks(query)
    }
}