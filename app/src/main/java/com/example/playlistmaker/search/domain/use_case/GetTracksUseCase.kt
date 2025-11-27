package com.example.playlistmaker.search.domain.use_case

import com.example.playlistmaker.domain.consumer.ResourceConsumer
import com.example.playlistmaker.domain.model.Tracks

interface GetTracksUseCase {
    fun getTracks(query: String, consumer: ResourceConsumer<Tracks>)
}