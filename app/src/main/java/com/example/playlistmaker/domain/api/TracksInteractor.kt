package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.consumer.ResourceConsumer
import com.example.playlistmaker.domain.model.Tracks

interface TracksInteractor {
    fun getTracks(query: String, consumer: ResourceConsumer<Tracks>)
}