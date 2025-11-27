package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.domain.consumer.ResourceConsumer
import com.example.playlistmaker.domain.model.Tracks
import com.example.playlistmaker.search.domain.repository.TracksRepository
import com.example.playlistmaker.search.domain.use_case.GetTracksUseCase
import java.util.concurrent.Executors

class GetTracksUseCaseImpl(private val repository: TracksRepository) : GetTracksUseCase {
    private val executor = Executors.newCachedThreadPool()

    override fun getTracks(query: String, consumer: ResourceConsumer<Tracks>) {
        executor.execute {
            consumer.consume(repository.getTracks(query))
        }
    }
}