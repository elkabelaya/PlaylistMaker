package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.domain.model.Tracks

interface HistoryInteractor {
    val elements: Tracks
    fun add(element: Track)
    fun clear()
}