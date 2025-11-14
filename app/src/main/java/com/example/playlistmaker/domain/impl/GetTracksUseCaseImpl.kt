package com.example.playlistmaker.domain.impl

import com.example.playlistmaker.domain.use_case.GetTracksUseCase
import com.example.playlistmaker.domain.consumer.ResourceConsumer
import com.example.playlistmaker.domain.model.Tracks
import com.example.playlistmaker.domain.repository.TracksRepository
import java.util.concurrent.Executors

class GetTracksUseCaseImpl(private val repository: TracksRepository) : GetTracksUseCase {
    private val executor = Executors.newCachedThreadPool()

    override fun getTracks(query: String, consumer: ResourceConsumer<Tracks>) {
        executor.execute {
            consumer.consume(repository.getTracks(query))
        }
    }
}